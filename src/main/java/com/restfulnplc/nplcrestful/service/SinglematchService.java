package com.restfulnplc.nplcrestful.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.ListKartu;
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
    private PanitiaService panitiaService;

    @Autowired
    private ListKartuService listKartuService;

    public Singlematch startSinglematch(SinglematchDTO singlematchDTO, Boothgames boothgames) {
        Singlematch singlematch = new Singlematch();
        singlematch.setNoMatch(getNextMatchID());
        singlematch.setWaktuMulai(singlematchDTO.getWaktuMulai());
        singlematch.setWaktuSelesai(singlematchDTO.getWaktuSelesai());
        singlematch.setInputBy(panitiaService.getPanitiaById(singlematchDTO.getInputBy()).get());
        singlematch.setTotalPoin(singlematchDTO.getTotalPoin());
        singlematch.setTotalBintang(singlematchDTO.getTotalBintang());

        Optional<Team> team = teamService.getTeamById(singlematchDTO.getIdTeam());
        if (team.isPresent()) {
            singlematch.setTeam(team.get());
        }

        Optional<ListKartu> listKartu = listKartuService.getListKartuById(singlematchDTO.getNoKartu());
        if (listKartu.isPresent()) {
            singlematch.setListKartu(listKartu.get());
        }

        singlematch.setBoothGames(boothgames);

        return singlematchRepository.save(singlematch);
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

    public ArrayList<Team> getTeamPerBooth(String id) {
        ArrayList<Team> teamsPlayed = new ArrayList<Team>();
        for (Singlematch singlematch : getAllSinglematches()) {
            if (singlematch.getBoothGames().getIdBooth().equals(id)) {
                teamsPlayed.add(singlematch.getTeam());
            }
        }
        return teamsPlayed;
    }

    public ArrayList<Team> getAvailableTeamPerBooth(String id) {
        ArrayList<Team> teamList = teamService.getAllTeam();
        ArrayList<Team> teamPerBooth = getTeamPerBooth(id);
        if (teamPerBooth.size() > 0) {
            teamList.removeAll(teamPerBooth);
        }
        return teamList;
    }

    public Optional<Singlematch> getSinglematchesByUserAndBooth(String userId, String boothId) {
        for (Singlematch singlematch : getAllSinglematches()) {
            if (singlematch.getTeam().getIdTeam().equals(userId)
                    && singlematch.getBoothGames().getIdBooth().equals(boothId)) {
                return Optional.of(singlematch);
            }
        }
        return Optional.empty();
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
        List<Singlematch> singlematch = singlematchRepository.findAll();
        if (singlematch.size() > 0)
            return "SINGLEMATCH"
                    + (Integer.parseInt(singlematch.get(singlematch.size() - 1).getNoMatch().split("SINGLEMATCH")[1])
                            + 1);
        return "SINGLEMATCH1";
    }
}