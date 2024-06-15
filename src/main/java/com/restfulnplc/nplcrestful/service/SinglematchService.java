package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.*;
import com.restfulnplc.nplcrestful.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private BoothgamesService boothGamesService;

    public Singlematch addSinglematch(SinglematchDTO singlematchDTO) {
        Singlematch singlematch = new Singlematch();
        singlematch.setNoMatch(singlematchDTO.getNoMatch());
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

        Optional<Boothgames> boothGames = boothGamesService.getBoothgameById(singlematchDTO.getIdBooth());
        if (boothGames.isPresent()) {
            singlematch.setBoothGames(boothGames.get());
        }

        return singlematchRepository.save(singlematch);
    }

    public ArrayList<Singlematch> getSinglematchesByUser(String userId) {
        ArrayList<Singlematch> singlematchArray = new ArrayList<Singlematch>();
        for(Singlematch singlematch : getAllSinglematches()) {
            if(singlematch.getTeam().getIdTeam().equals(userId)) {
                singlematchArray.add(singlematch);
            }
        }
        return singlematchArray;
    }

    public Optional<Singlematch> getSinglematchesByUserAndBooth(String userId, String boothId) {
        for(Singlematch singlematch : getAllSinglematches()) {
            if(singlematch.getTeam().getIdTeam().equals(userId) && singlematch.getBoothGames().getIdBooth().equals(boothId)) {
                return Optional.of(singlematch);
            }
        }
        return Optional.empty();
    }

    public List<Singlematch> getAllSinglematches() {
        return singlematchRepository.findAll();
    }

    public Optional<Singlematch> getSinglematchById(String id) {
        return singlematchRepository.findById(id);
    }

    public Optional<Singlematch> updateSinglematch(String id, SinglematchDTO singlematchDTO) {
        Optional<Singlematch> singlematchData = singlematchRepository.findById(id);
        if (singlematchData.isPresent()) {
            Singlematch singlematch = singlematchData.get();
            singlematch.setNoMatch(singlematchDTO.getNoMatch());
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

            Optional<Boothgames> boothGames = boothGamesService.getBoothgameById(singlematchDTO.getIdBooth());
            if (boothGames.isPresent()) {
                singlematch.setBoothGames(boothGames.get());
            }

            return Optional.of(singlematchRepository.save(singlematch));
        }
        return Optional.empty();
    }

    public boolean deleteSinglematch(String id) {
        if (singlematchRepository.existsById(id)) {
            singlematchRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public void reset()
    {
        singlematchRepository.deleteAll();
    }
}