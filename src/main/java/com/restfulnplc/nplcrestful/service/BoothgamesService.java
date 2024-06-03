package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.repository.BoothgamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoothgamesService {

    @Autowired
    private BoothgamesRepository boothgamesRepository;

    public Boothgames addBoothgame(BoothgamesDTO boothgamesDTO) {
        Boothgames newBoothgame = new Boothgames();
        newBoothgame.setIdBooth(getNextBoothgameID());
        newBoothgame.setNama(boothgamesDTO.getNama());
        newBoothgame.setIdPenjaga1(boothgamesDTO.getIdPenjaga1());
        newBoothgame.setIdPenjaga2(boothgamesDTO.getIdPenjaga2());
        newBoothgame.setSopGames(boothgamesDTO.getSopGames());
        newBoothgame.setLokasi(boothgamesDTO.getLokasi());
        newBoothgame.setTipegame(boothgamesDTO.getTipeGame());
        newBoothgame.setDurasiPermainan(boothgamesDTO.getDurasiPermainan());
        return boothgamesRepository.save(newBoothgame);
    }

    public List<Boothgames> getAllBoothgames() {
        return boothgamesRepository.findAll();
    }

    public Optional<Boothgames> getBoothgameById(String id) {
        return boothgamesRepository.findById(id);
    }

    public Optional<Boothgames> updateBoothgame(String id, BoothgamesDTO boothgamesDTO) {
        Optional<Boothgames> optionalBoothgame = boothgamesRepository.findById(id);
        if (optionalBoothgame.isPresent()) {
            Boothgames existingBoothgame = optionalBoothgame.get();
            existingBoothgame.setNama(boothgamesDTO.getNama());
            existingBoothgame.setIdPenjaga1(boothgamesDTO.getIdPenjaga1());
            existingBoothgame.setIdPenjaga2(boothgamesDTO.getIdPenjaga2());
            existingBoothgame.setSopGames(boothgamesDTO.getSopGames());
            existingBoothgame.setLokasi(boothgamesDTO.getLokasi());
            existingBoothgame.setTipegame(boothgamesDTO.getTipeGame());
            existingBoothgame.setDurasiPermainan(boothgamesDTO.getDurasiPermainan());
            boothgamesRepository.save(existingBoothgame);
            return Optional.of(existingBoothgame);
        }
        return Optional.empty();
    }

    public boolean deleteBoothgame(String id) {
        Optional<Boothgames> optionalBoothgame = boothgamesRepository.findById(id);
        if (optionalBoothgame.isPresent()) {
            boothgamesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String getNextBoothgameID()
    {
        List<Boothgames> boothgames = boothgamesRepository.findAll();
        if(boothgames.size() > 0) return "BOOTHGAME" + (Integer.parseInt(boothgames.getLast().getIdBooth().split("BOOTHGAME")[1]) + 1);
        return "BOOTHGAME1";
    }
}
