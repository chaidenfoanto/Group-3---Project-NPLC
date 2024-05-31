package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.*;
import com.restfulnplc.nplcrestful.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SinglematchService {

    @Autowired
    private SinglematchRepository singlematchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ListKartuRepository listKartuRepository;

    @Autowired
    private BoothgamesRepository boothGamesRepository;

    public Singlematch addSinglematch(SinglematchDTO singlematchDTO) {
        Singlematch singlematch = new Singlematch();
        singlematch.setNoMatch(singlematchDTO.getNoMatch());
        singlematch.setWaktuMulai(singlematchDTO.getWaktuMulai());
        singlematch.setWaktuSelesai(singlematchDTO.getWaktuSelesai());
        singlematch.setInputBy(singlematchDTO.getInputBy());
        singlematch.setTotalPoin(singlematchDTO.getTotalPoin());

        Optional<Team> team = teamRepository.findById(singlematchDTO.getIdTeam());
        if (team.isPresent()) {
            singlematch.setTeam(team.get());
        }

        Optional<ListKartu> listKartu = listKartuRepository.findById(singlematchDTO.getNoKartu());
        if (listKartu.isPresent()) {
            singlematch.setListKartu(listKartu.get());
        }

        Optional<Boothgames> boothGames = boothGamesRepository.findById(singlematchDTO.getIdBooth());
        if (boothGames.isPresent()) {
            singlematch.setBoothGames(boothGames.get());
        }

        return singlematchRepository.save(singlematch);
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
            singlematch.setInputBy(singlematchDTO.getInputBy());
            singlematch.setTotalPoin(singlematchDTO.getTotalPoin());

            Optional<Team> team = teamRepository.findById(singlematchDTO.getIdTeam());
            if (team.isPresent()) {
                singlematch.setTeam(team.get());
            }

            Optional<ListKartu> listKartu = listKartuRepository.findById(singlematchDTO.getNoKartu());
            if (listKartu.isPresent()) {
                singlematch.setListKartu(listKartu.get());
            }

            Optional<Boothgames> boothGames = boothGamesRepository.findById(singlematchDTO.getIdBooth());
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
}