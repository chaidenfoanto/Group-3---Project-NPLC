package com.restfulnplc.nplcrestful.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.model.MatchStatus;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Singlematch;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.repository.SinglematchRepository;

@Service
public class SinglematchService {

    @Autowired
    private SinglematchRepository singlematchRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ListKartuService listKartuService;

    @Autowired
    private PanitiaService panitiaService;

    public int getPointsByTeam(String teamID) {
        Optional<Team> optionalTeam = teamService.getTeamById(teamID);
        int totalPoin = 0;
        ArrayList<Object> insertedPoints = new ArrayList<Object>();
        ArrayList<Object> insertedBooth = new ArrayList<Object>();
        if (optionalTeam.isPresent()) {
            for (Singlematch singlematch : getSinglematchesByUser(teamID)) {
                if (insertedBooth.contains(singlematch.getBoothGames())) {
                    int index = insertedBooth.indexOf(singlematch.getBoothGames());
                    if ((int) insertedPoints.get(index) < singlematch.getTotalPoin()) {
                        insertedPoints.set(index, singlematch.getTotalPoin());
                    }
                } else {
                    insertedBooth.add(singlematch.getBoothGames());
                    insertedPoints.add(singlematch.getTotalPoin());
                }
            }
            for (Object point : insertedPoints) {
                totalPoin += (int) point;
            }
        }
        return totalPoin;
    }

    public Optional<Singlematch> startSingleMatch(SinglematchDTO singlematchDTO, Boothgames boothgame,
            String panitiaId) {
        if (getAvailableTeamPerBoothList(boothgame.getIdBooth())
                .contains(teamService.getTeamById(singlematchDTO.getIdTeam()).get())) {
            Singlematch singlematch = new Singlematch();
            singlematch.setNoMatch(getNextMatchID());
            singlematch.setWaktuMulai(LocalTime.now());
            LocalTime waktuSelesai = LocalTime.now().plusNanos(boothgame.getDurasiPermainan() * 1_000_000);
            singlematch.setTeam(teamService.getTeamById(singlematchDTO.getIdTeam()).get());
            singlematch.setInputBy(panitiaService.getPanitiaById(panitiaId).get());
            singlematch.setMatchStatus(MatchStatus.STARTED);

            if (!getAvailableRepeatTeamPerBooth(boothgame.getIdBooth()).contains(singlematch.getTeam())) {
                if (singlematchDTO.getNoKartu() != null) {
                    Optional<ListKartu> listKartu = listKartuService.getListKartuById(singlematchDTO.getNoKartu());
                    if (listKartu.isPresent() && !listKartu.get().getIsUsed()
                            && listKartu.get().getOwnedBy() != null
                            && !listKartu.get().getCardSkill().getIdCard().equals("D4")) {
                        if (listKartu.get().getOwnedBy().equals(singlematch.getTeam())) {
                            singlematch.setListKartu(listKartu.get());
                            listKartuService.useCard(singlematch.getListKartu().getNoKartu());
                            if (listKartu.get().getCardSkill().getIdCard().equals("B2")) {
                                waktuSelesai = waktuSelesai.plusNanos(TimeUnit.MINUTES.toMillis(3) * 1_000_000);
                            } else if (listKartu.get().getCardSkill().getIdCard().equals("C3")) {
                                waktuSelesai = waktuSelesai.plusNanos(TimeUnit.MINUTES.toMillis(5) * 1_000_000);
                            }
                        }
                    }
                }
            } else {
                singlematch.setListKartu(
                        listKartuService.getCardsByTeamIdAndCardID(singlematch.getTeam().getIdTeam(), "D4").get(0));
                listKartuService.useCard(singlematch.getListKartu().getNoKartu());
            }
            singlematch.setWaktuSelesai(waktuSelesai);

            singlematch.setBoothGames(boothgame);

            return Optional.of(singlematchRepository.save(singlematch));
        }
        return Optional.empty();
    }

    public Singlematch stopSingleMatch(Singlematch singlematch) {
        if (LocalTime.now().until(singlematch.getWaktuSelesai(),
                ChronoUnit.SECONDS) > 0) {
            singlematch.setWaktuSelesai(LocalTime.now().withNano(0));
        }
        singlematch.setMatchStatus(MatchStatus.FINISHED);
        return singlematchRepository.save(singlematch);
    }

    public Optional<Singlematch> getGameRunning(Boothgames boothgame) {
        ArrayList<Singlematch> matches = getAllSinglematches();
        Optional<Singlematch> runningMatch = Optional.empty();
        for (Singlematch match : matches) {
            if (match.getBoothGames().equals(boothgame) && !match.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                runningMatch = Optional.of(match);
            }
        }
        return runningMatch;
    }

    public Singlematch submitSingleMatch(Singlematch singlematch, int totalBintang, Panitia panitia) {
        singlematch.setTotalBintang(totalBintang);
        int totalPoin;
        switch (totalBintang) {
            case 0:
                totalPoin = 0;
                break;
            case 1:
                totalPoin = 30;
                break;
            case 2:
                totalPoin = 60;
                break;
            case 3:
                totalPoin = 100;
                break;
            default:
                totalPoin = 0;
        }
        if (singlematch.getListKartu() != null) {
            if (singlematch.getListKartu().getCardSkill().getIdCard().equals("A1")) {
                totalPoin *= 2;
            }
        }
        singlematch.setTotalPoin(totalPoin);
        singlematch.setInputBy(panitia);
        singlematch.setMatchStatus(MatchStatus.SUBMITTED);
        return singlematchRepository.save(singlematch);
    }

    public Optional<Singlematch> getCurrentSingleMatch(Boothgames boothgames) {
        for (Singlematch singlematch : getAllSinglematches()) {
            if (singlematch.getBoothGames().equals(boothgames)
                    && (singlematch.getMatchStatus().equals(MatchStatus.STARTED)
                            || singlematch.getMatchStatus().equals(MatchStatus.FINISHED))) {
                if (LocalTime.now().isAfter(singlematch.getWaktuSelesai())
                        && singlematch.getMatchStatus().equals(MatchStatus.STARTED)) {
                    singlematch.setMatchStatus(MatchStatus.FINISHED);
                    singlematch = singlematchRepository.save(singlematch);
                }
                return Optional.of(singlematch);
            }
        }
        return Optional.empty();
    }

    public ArrayList<Singlematch> getSinglematchesByUser(String idTeam) {
        ArrayList<Singlematch> singlematchArray = new ArrayList<Singlematch>();
        for (Singlematch singlematch : getAllSinglematches()) {
            if (singlematch.getTeam().getIdTeam().equals(idTeam)) {
                singlematchArray.add(singlematch);
            }
        }
        return singlematchArray;
    }

    public int getTotalMatchByUser(String idTeam) {
        ArrayList<Boothgames> boothgamesArray = new ArrayList<Boothgames>();
        if (teamService.checkTeam(idTeam)) {
            for (Singlematch singlematch : getAllSinglematches()) {
                if (singlematch.getTeam().getIdTeam().equals(idTeam)
                        && !boothgamesArray.contains(singlematch.getBoothGames())) {
                    boothgamesArray.add(singlematch.getBoothGames());
                }
            }
        }
        return boothgamesArray.size();
    }

    public ArrayList<Team> getTeamPerBooth(String id) {
        ArrayList<Team> teamsPlayed = new ArrayList<Team>();
        for (Singlematch singlematch : getAllSinglematches()) {
            if (singlematch.getBoothGames().getIdBooth().equals(id)) {
                teamsPlayed.add(singlematch.getTeam());
            }
        }
        return teamsPlayed;
    }

    public ArrayList<Object> getAvailableTeamPerBooth(String id) {
        ArrayList<Team> teamList = teamService.getAllTeam();
        ArrayList<Team> teamPerBooth = getTeamPerBooth(id);
        if (teamPerBooth.size() > 0) {
            teamList.removeAll(teamPerBooth);
        }
        ArrayList<Team> secondChanceTeam = getAvailableRepeatTeamPerBooth(id);
        ArrayList<Object> availableTeams = new ArrayList<Object>();

        for (Team team : teamList) {
            availableTeams.add(Map.of(
                    "idTeam", team.getIdTeam(),
                    "namaTeam", team.getNama(),
                    "usernameTeam", team.getUsername(),
                    "asalSekolah", team.getAsalSekolah(),
                    "chanceRoll", team.getChanceRoll(),
                    "secondChance", false));
        }
        for (Team team : secondChanceTeam) {
            availableTeams.add(Map.of(
                    "idTeam", team.getIdTeam(),
                    "namaTeam", team.getNama(),
                    "usernameTeam", team.getUsername(),
                    "asalSekolah", team.getAsalSekolah(),
                    "chanceRoll", team.getChanceRoll(),
                    "secondChance", true));
        }
        return availableTeams;
    }

    public ArrayList<Team> getAvailableTeamPerBoothList(String id) {
        ArrayList<Team> teamList = teamService.getAllTeam();
        ArrayList<Team> teamPerBooth = getTeamPerBooth(id);
        if (teamPerBooth.size() > 0) {
            teamList.removeAll(teamPerBooth);
        }
        ArrayList<Team> secondChanceTeam = getAvailableRepeatTeamPerBooth(id);
        ArrayList<Team> availableTeams = new ArrayList<Team>();

        availableTeams.addAll(teamList);
        availableTeams.addAll(secondChanceTeam);

        return availableTeams;
    }

    public ArrayList<Team> getAvailableRepeatTeamPerBooth(String boothId) {
        ArrayList<ListKartu> listSecondChance = new ArrayList<ListKartu>();
        ArrayList<Team> cleanList = new ArrayList<Team>();
        for (ListKartu listKartu : listKartuService.getAllListKartu()) {
            if (!listKartu.getIsUsed() && listKartu.getCardSkill().getIdCard().equals("D4")
                    && listKartu.getOwnedBy() != null) {
                listSecondChance.add(listKartu);
            }
        }

        if (listSecondChance.size() > 0) {
            ArrayList<Team> allowedTeams = new ArrayList<Team>();
            ArrayList<Team> unallowedTeams = new ArrayList<Team>();
            ArrayList<Team> repeatTeam = getTeamPerBooth(boothId);
            for (Team team : repeatTeam) {
                if (!allowedTeams.contains(team)) {
                    allowedTeams.add(team);
                } else {
                    unallowedTeams.add(team);
                }
            }

            allowedTeams.removeAll(unallowedTeams);

            for (Team team : allowedTeams) {
                for (ListKartu listKartu : listSecondChance) {
                    if (listKartu.getOwnedBy().equals(team) && !cleanList.contains(team)) {
                        cleanList.add(team);
                        break;
                    }
                }
            }
        }
        return cleanList;
    }

    public ArrayList<Singlematch> getSinglematchesByUserAndBooth(String userId, String boothId) {
        ArrayList<Singlematch> singlematches = new ArrayList<Singlematch>();
        if (teamService.checkTeam(userId)) {
            for (Singlematch singlematch : getAllSinglematches()) {
                if (singlematch.getTeam().getIdTeam().equals(userId)
                        && singlematch.getBoothGames().getIdBooth().equals(boothId)) {
                    singlematches.add(singlematch);
                }
            }
        }
        return singlematches;
    }

    public Optional<Singlematch> getSinglematchResultByUserAndBooth(String userId, String boothId) {
        if (teamService.checkTeam(userId)) {
            ArrayList<Singlematch> singlematches = getSinglematchesByUserAndBooth(userId, boothId);
            if (singlematches.size() > 0) {
                Singlematch singlematch = singlematches.get(0);
                for (Singlematch singlematchCheck : singlematches) {
                    if (singlematchCheck.getTotalPoin() > singlematch.getTotalPoin()) {
                        singlematch = singlematchCheck;
                    }
                }
                return Optional.of(singlematch);
            }
        }
        return Optional.empty();
    }

    public ArrayList<Object> getBoothHistory(String boothId) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Singlematch singlematch : getAllSinglematches()) {
            if (singlematch.getBoothGames().getIdBooth().equals(boothId)
                    && singlematch.getMatchStatus().equals(MatchStatus.SUBMITTED)) {
                Long durationSecond = singlematch.getWaktuMulai().until(singlematch.getWaktuSelesai(),
                        ChronoUnit.SECONDS);
                result.add(Map.of(
                        "noMatch", singlematch.getNoMatch(),
                        "waktuMulai", singlematch.getWaktuMulai(),
                        "waktuSelesai", singlematch.getWaktuSelesai(),
                        "cardUsed", (singlematch.getListKartu() != null) ? (Map.of(
                                "cardName",
                                singlematch.getListKartu().getCardSkill().getNamaKartu(),
                                "cardNumber", singlematch.getListKartu().getNoKartu(),
                                "cardId", singlematch.getListKartu().getCardSkill().getIdCard()))
                                : "None",
                        "team", Map.of(
                                "namaTeam", singlematch.getTeam().getNama(),
                                "idTeam", singlematch.getTeam().getIdTeam()),
                        "inputBy", singlematch.getInputBy().getNama(),
                        "totalBintang", singlematch.getTotalBintang(),
                        "durasi", Map.of(
                                "detik", durationSecond % 60,
                                "menit", durationSecond / 60),
                        "totalPoin", singlematch.getTotalPoin()));
            }
        }
        return result;
    }

    public ArrayList<Singlematch> getAllSinglematches() {
        ArrayList<Singlematch> singlematchList = new ArrayList<Singlematch>();
        for (Singlematch singlematch : singlematchRepository.findAll()) {
            singlematchList.add(singlematch);
        }
        return singlematchList;
    }

    public Optional<Singlematch> getSinglematchById(String id) {
        return singlematchRepository.findById(id);
    }

    public boolean deleteSinglematch(String id) {
        if (singlematchRepository.existsById(id)) {
            singlematchRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void reset() {
        singlematchRepository.deleteAll();
    }

    public String getNextMatchID() {
        List<Singlematch> singlematches = singlematchRepository.findAll();
        int highestID = 0;
        for (Singlematch singlematch : singlematches) {
            int newID = Integer.parseInt(singlematch.getNoMatch().split("SINGLEMATCH")[1]);
            if (newID > highestID)
                highestID = newID;
        }
        return "SINGLEMATCH" + (highestID + 1);
    }
}