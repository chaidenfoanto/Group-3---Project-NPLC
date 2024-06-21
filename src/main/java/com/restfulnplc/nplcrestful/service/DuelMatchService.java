package com.restfulnplc.nplcrestful.service;

import java.time.LocalTime;
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
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.repository.DuelMatchRepository;

@Service
public class DuelMatchService {

    @Autowired
    private DuelMatchRepository duelMatchRepository;

    @Autowired
    private TeamService teamService;

    public DuelMatch startDuelMatch(DuelMatchDTO duelMatchDTO, Boothgames boothgame) {
        DuelMatch duelMatch = new DuelMatch();
        duelMatch.setNoMatch(getNextMatchID());
        duelMatch.setTeam1(teamService.getTeamById(duelMatchDTO.getTeam1()).get());
        duelMatch.setTeam2(teamService.getTeamById(duelMatchDTO.getTeam2()).get());
        duelMatch.setWaktuMulai(LocalTime.now());
        duelMatch.setWaktuSelesai(LocalTime.now().plusNanos(boothgame.getDurasiPermainan() * 1_000_000));
        duelMatch.setBoothGames(boothgame);
        duelMatch.setMatchStatus(MatchStatus.STARTED);
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
            winningTeams.add(duelMatch.getTimMenang());
        }
        return winningTeams;
    }

    public ArrayList<Team> getWinningTeamsByBooth(String id) {
        ArrayList<Team> winningTeams = new ArrayList<Team>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getBoothGames().getIdBooth().equals(id)) {
                winningTeams.add(duelMatch.getTimMenang());
            }
        }
        return winningTeams;
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

    public ArrayList<Team> getAvailableTeamPerBooth(String id) {
        ArrayList<Team> availableTeam = new ArrayList<Team>();
        ArrayList<Team> teamList = teamService.getAllTeam();
        teamList.removeAll(getWinningTeamsByBooth(id));
        for (Team team : teamList) {
            if (getDuelMatchesByUser(team.getIdTeam()).size() < 2) {
                availableTeam.add(team);
            }
        }
        return availableTeam;
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

    public ArrayList<DuelMatch> getWinningDuelMatchesByUser(String idTeam) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getTimMenang().getIdTeam().equals(idTeam)) {
                duelMatchArray.add(duelMatch);
            }
        }
        return duelMatchArray;
    }

    public Object getDuelMatchesStatByUser(String idTeam) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        ArrayList<DuelMatch> duelMatchWinArray = new ArrayList<DuelMatch>();
        for (DuelMatch duelMatch : getAllDuelMatches()) {
            if (duelMatch.getTeam1().getIdTeam().equals(idTeam) || duelMatch.getTeam2().getIdTeam().equals(idTeam)) {
                duelMatchArray.add(duelMatch);
            }
            if (duelMatch.getTimMenang().getIdTeam().equals(idTeam)) {
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
