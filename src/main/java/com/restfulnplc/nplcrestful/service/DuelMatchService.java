package com.restfulnplc.nplcrestful.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.DuelMatchDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.model.MatchStatus;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.repository.DuelMatchRepository;

@Service
public class DuelMatchService {

    @Autowired
    private DuelMatchRepository duelMatchRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PanitiaService panitiaService;

    public int getPointsByTeam(String teamID){
        Optional<Team> optionalTeam = teamService.getTeamById(teamID);
        int totalPoin = 0;
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            for(DuelMatch duelMatch : getAllDuelMatches()){
                if(duelMatch.getTimMenang().equals(team)){
                    totalPoin += 100;
                }
            }
        }
        return totalPoin;
    }

    public Optional<DuelMatch> startDuelMatch(DuelMatchDTO duelMatchDTO, Boothgames boothgame, String panitiaId) {
        ArrayList<Team> availableTeams = getAvailableTeamPerBoothList(boothgame.getIdBooth());
        if (availableTeams.contains(teamService.getTeamById(duelMatchDTO.getTeam1()).get())
                && availableTeams.contains(teamService.getTeamById(duelMatchDTO.getTeam2()).get())) {
            DuelMatch duelMatch = new DuelMatch();
            duelMatch.setNoMatch(getNextMatchID());
            duelMatch.setTeam1(teamService.getTeamById(duelMatchDTO.getTeam1()).get());
            duelMatch.setTeam2(teamService.getTeamById(duelMatchDTO.getTeam2()).get());
            duelMatch.setWaktuMulai(LocalTime.now());
            duelMatch.setWaktuSelesai(LocalTime.now().plusNanos(boothgame.getDurasiPermainan() * 1_000_000));
            duelMatch.setBoothGames(boothgame);
            duelMatch.setInputBy(panitiaService.getPanitiaById(panitiaId).get());
            duelMatch.setMatchStatus(MatchStatus.STARTED);
            duelMatch.setTimMenang(duelMatch.getTeam1());
            return Optional.of(duelMatchRepository.save(duelMatch));
        }
        return Optional.empty();
    }

    public DuelMatch stopDuelMatch(DuelMatch duelMatch) {
        if (LocalTime.now().until(duelMatch.getWaktuSelesai(),
                ChronoUnit.SECONDS) > 0) {
            duelMatch.setWaktuSelesai(LocalTime.now().withNano(0));
        }
        duelMatch.setMatchStatus(MatchStatus.FINISHED);
        return duelMatchRepository.save(duelMatch);
    }

    public DuelMatch submitDuelMatch(DuelMatch duelMatch, String idTeamMenang, Panitia panitia) {
        duelMatch.setTimMenang(teamService.getTeamById(idTeamMenang).get());
        duelMatch.setInputBy(panitia);
        duelMatch.setMatchStatus(MatchStatus.SUBMITTED);
        return duelMatchRepository.save(duelMatch);
    }

    public Optional<DuelMatch> getCurrentDuelMatch(Boothgames boothgames) {
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getBoothGames().equals(boothgames)
                    && (duelMatch.getMatchStatus().equals(MatchStatus.STARTED)
                            || duelMatch.getMatchStatus().equals(MatchStatus.FINISHED))) {
                if (LocalTime.now().isAfter(duelMatch.getWaktuSelesai())
                        && duelMatch.getMatchStatus().equals(MatchStatus.STARTED)) {
                    duelMatch.setMatchStatus(MatchStatus.FINISHED);
                    duelMatch = duelMatchRepository.save(duelMatch);
                }
                return Optional.of(duelMatch);
            }
        }
        return Optional.empty();
    }

    public ArrayList<DuelMatch> getAllDuelMatches() {
        ArrayList<DuelMatch> duelMatchList = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : duelMatchRepository.findAll()) {
            duelMatchList.add(duelMatch);
        }
        return duelMatchList;
    }

    public Optional<DuelMatch> getDuelMatchById(String id) {
        return duelMatchRepository.findById(id);
    }

    public ArrayList<Team> getWinningTeams() {
        ArrayList<Team> winningTeams = new ArrayList<Team>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                winningTeams.add(duelMatch.getTimMenang());
            }
        }
        return winningTeams;
    }

    public ArrayList<Team> getWinningTeamsByBooth(String id) {
        ArrayList<Team> winningTeams = new ArrayList<Team>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getBoothGames().getIdBooth().equals(id)
                    && duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                winningTeams.add(duelMatch.getTimMenang());
            }
        }
        return winningTeams;
    }

    public ArrayList<Object> getBoothHistory(String boothId) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getBoothGames().getIdBooth().equals(boothId)
                    && duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                Long durationSecond = duelMatch.getWaktuMulai().until(duelMatch.getWaktuSelesai(),
                        ChronoUnit.SECONDS);
                result.add(Map.of(
                        "noMatch", duelMatch.getNoMatch(),
                        "waktuMulai", duelMatch.getWaktuMulai(),
                        "waktuSelesai", duelMatch.getWaktuSelesai(),
                        "team1", Map.of(
                                "namaTeam", duelMatch.getTeam1().getNama(),
                                "idTeam", duelMatch.getTeam1().getIdTeam()),
                        "team2", Map.of(
                                "namaTeam", duelMatch.getTeam2().getNama(),
                                "idTeam", duelMatch.getTeam2().getIdTeam()),
                        "inputBy", duelMatch.getInputBy().getNama(),
                        "teamMenang", Map.of(
                            "namaTeam", duelMatch.getTimMenang().getNama(),
                            "idTeam", duelMatch.getTimMenang().getIdTeam()),
                        "durasi", Map.of(
                                "detik", durationSecond % 60,
                                "menit", durationSecond / 60)));
            }
        }
        return result;
    }

    public ArrayList<DuelMatch> getDuelMatchByBooth(String id) {
        ArrayList<DuelMatch> duelMatches = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getBoothGames().getIdBooth().equals(id)
                    && duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                duelMatches.add(duelMatch);
            }
        }
        return duelMatches;
    }

    public ArrayList<Object> getAvailableTeamPerBooth(String id) {
        ArrayList<Object> availableTeam = new ArrayList<Object>();
        ArrayList<Team> teamList = teamService.getAllTeam();
        teamList.removeAll(getWinningTeamsByBooth(id));
        for (Team team : teamList) {
            if (getDuelMatchesByUser(team.getIdTeam()).size() < 2) {
                availableTeam.add(Map.of(
                        "idTeam", team.getIdTeam(),
                        "namaTeam", team.getNama(),
                        "usernameTeam", team.getUsername(),
                        "asalSekolah", team.getAsalSekolah(),
                        "chanceRoll", team.getChanceRoll()));
            }
        }
        return availableTeam;
    }

    public ArrayList<Team> getAvailableTeamPerBoothList(String id) {
        // ArrayList<Team> availableTeam = new ArrayList<Team>();
        ArrayList<Team> teamList = teamService.getAllTeam();
        teamList.removeAll(getWinningTeamsByBooth(id));
        return teamList;
    }

    public ArrayList<DuelMatch> getDuelMatchesByUser(String idTeam) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getTeam1().getIdTeam().equals(idTeam) || duelMatch.getTeam2().getIdTeam().equals(idTeam)) {
                duelMatchArray.add(duelMatch);
            }
        }
        return duelMatchArray;
    }

    
    public int getTotalMatchByUser(String idTeam) {
        ArrayList<Boothgames> boothgamesArray = new ArrayList<Boothgames>();
        if(teamService.checkTeam(idTeam)){
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if ((duelMatch.getTeam1().getIdTeam().equals(idTeam) || duelMatch.getTeam2().getIdTeam().equals(idTeam)) && !boothgamesArray.contains(duelMatch.getBoothGames())) {
                boothgamesArray.add(duelMatch.getBoothGames());
            }
        }
    }
        return boothgamesArray.size();
    }

    public ArrayList<DuelMatch> getWinningDuelMatchesByUser(String idTeam) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getTimMenang().getIdTeam().equals(idTeam)
                    && duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                duelMatchArray.add(duelMatch);
            }
        }
        return duelMatchArray;
    }

    public Object getDuelMatchesStatByUser(String idTeam) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        ArrayList<DuelMatch> duelMatchWinArray = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getTeam1().getIdTeam().equals(idTeam) || duelMatch.getTeam2().getIdTeam().equals(idTeam)
                    && duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                duelMatchArray.add(duelMatch);
            }
            if (duelMatch.getTimMenang().getIdTeam().equals(idTeam)
                    && duelMatch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                duelMatchWinArray.add(duelMatch);
            }
        }
        return Map.of(
                "duelMatchPlayed", duelMatchArray.size(),
                "duelMatchWon", duelMatchWinArray.size());
    }

    public ArrayList<DuelMatch> getDuelMatchesByUserAndBooth(String userId, String boothId) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if ((duelMatch.getTeam1().getIdTeam().equals(userId) || duelMatch.getTeam2().getIdTeam().equals(userId))
                    && duelMatch.getBoothGames().getIdBooth().equals(boothId)) {
                duelMatchArray.add(duelMatch);
            }
        }
        return duelMatchArray;
    }

    public boolean deleteDuelMatch(String id) {
        Optional<DuelMatch> optionalDuelMatch = duelMatchRepository.findById(id);
        if (optionalDuelMatch.isPresent()) {
            duelMatchRepository.delete(optionalDuelMatch.get());
            return true;
        }
        return false;
    }

    public void reset() {
        duelMatchRepository.deleteAll();
    }

    public String getNextMatchID() {
        List<DuelMatch> duelmatch = duelMatchRepository.findAll();
        if (duelmatch.size() > 0)
            return "DUELMATCH"
                    + (Integer.parseInt(duelmatch.get(duelmatch.size() - 1).getNoMatch().split("DUELMATCH")[1]) + 1);
        return "DUELMATCH1";
    }
}
