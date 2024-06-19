package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.Lokasi;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.repository.BoothgamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;

@Service
public class BoothgamesService {

    @Autowired
    private BoothgamesRepository boothgamesRepository;

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private LokasiService lokasiService;

    public Boothgames addBoothgame(BoothgamesDTO boothgamesDTO) {
        Boothgames newBoothgame = new Boothgames();
        newBoothgame.setIdBooth(getNextBoothgameID());
        newBoothgame.setNama(boothgamesDTO.getNama());
        newBoothgame.setIdPenjaga1(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga1()).get());
        newBoothgame.setIdPenjaga2(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga2()).get());
        newBoothgame.setSopGames(boothgamesDTO.getSopGames());
        newBoothgame.setLokasi(lokasiService.getLokasiById(boothgamesDTO.getLokasi()).get());
        newBoothgame.setTipegame(boothgamesDTO.getTipeGameClass());
        newBoothgame.setDurasiPermainan(boothgamesDTO.getDurasiPermainan());
        newBoothgame.setFotoBooth(boothgamesDTO.getFotoBooth()); // update
        return boothgamesRepository.save(newBoothgame);
    }

    public List<Boothgames> getAllBoothgames() {
        return boothgamesRepository.findAll();
    }

    public Object getAvailableDatas() {
        ArrayList<Object> listAvailablePanitia = new ArrayList<Object>();
        ArrayList<Object> listAvailableLokasi = new ArrayList<Object>();
        for(Panitia panitia : panitiaService.getAllPanitia()) {
            if(!getBoothgameByPanitia(panitia.getIdPanitia()).isPresent()) {
                listAvailablePanitia.add(
                    Map.of(
                        "namaPanitia", panitia.getNama()
                    )
                );
            }
        }

        for(Lokasi lokasi : lokasiService.getAllLokasi()) {
            if(!getBoothgameByLokasi(lokasi.getNoRuangan()).isPresent()) {
                listAvailableLokasi.add(
                    Map.of(
                        "noRuangan", lokasi.getNoRuangan()
                    )
                );
            }
        }

        Object result = Map.of(
            "listPanitia", listAvailablePanitia,
            "listLokasi", listAvailableLokasi
        );
        return result;
    }

    public Optional<Boothgames> getBoothgameById(String id) {
        return boothgamesRepository.findById(id);
    }

    public Optional<Boothgames> getBoothgameByPanitia(String id) {
        Panitia panitia = panitiaService.getPanitiaById(id).get();
        for (Boothgames boothgame : getAllBoothgames()) {
            if (boothgame.getIdPenjaga2() != null) {
                if (boothgame.getIdPenjaga1().equals(panitia) || boothgame.getIdPenjaga2().equals(panitia)) {
                    return Optional.of(boothgame);
                }
            } else {
                if (boothgame.getIdPenjaga1().equals(panitia)) {
                    return Optional.of(boothgame);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Boothgames> getBoothgameByLokasi(String noruangan) {
        Lokasi lokasi = lokasiService.getLokasiById(noruangan).get();
        for (Boothgames boothgame : getAllBoothgames()) {
            if(boothgame.getLokasi().equals(lokasi)) {
                return Optional.of(boothgame);
            }
        }
        return Optional.empty();
    }

    public Optional<Boothgames> updateBoothgame(String id, BoothgamesDTO boothgamesDTO) {
        Optional<Boothgames> optionalBoothgame = boothgamesRepository.findById(id);
        if (optionalBoothgame.isPresent()) {
            Boothgames existingBoothgame = optionalBoothgame.get();
            existingBoothgame.setNama(boothgamesDTO.getNama());
            existingBoothgame.setIdPenjaga1(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga1()).get());
            existingBoothgame.setIdPenjaga2(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga2()).get());
            existingBoothgame.setSopGames(boothgamesDTO.getSopGames());
            existingBoothgame.setLokasi(lokasiService.getLokasiById(boothgamesDTO.getLokasi()).get());
            existingBoothgame.setTipegame(boothgamesDTO.getTipeGameClass());
            existingBoothgame.setDurasiPermainan(boothgamesDTO.getDurasiPermainan());
            existingBoothgame.setFotoBooth(boothgamesDTO.getFotoBooth()); // update
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

    public String getNextBoothgameID() {
        List<Boothgames> boothgames = boothgamesRepository.findAll();
        if (boothgames.size() > 0)
            return "BOOTHGAME"
                    + (Integer.parseInt(boothgames.get(boothgames.size() - 1).getIdBooth().split("BOOTHGAME")[1]) + 1);
        return "BOOTHGAME1";
    }

    public void reset() {
        boothgamesRepository.deleteAll();
    }
}
