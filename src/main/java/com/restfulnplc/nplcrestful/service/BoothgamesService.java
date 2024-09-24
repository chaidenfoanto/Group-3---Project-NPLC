package com.restfulnplc.nplcrestful.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.Divisi;
import com.restfulnplc.nplcrestful.model.Lokasi;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Tipegame;
import com.restfulnplc.nplcrestful.repository.BoothgamesRepository;

@Service
public class BoothgamesService {

    @Autowired
    private BoothgamesRepository boothgamesRepository;

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private LokasiService lokasiService;

    private Long durasiMin = TimeUnit.SECONDS.toMillis(1);
    private Long durasiMax = TimeUnit.SECONDS.toMillis(60*30);

    public Optional<Boothgames> addBoothgame(BoothgamesDTO boothgamesDTO) {
        String durasi = boothgamesDTO.getDurasiPermainan();
        if (durasi.matches("^([0-9]{1,2})(:)([0-9]{1,2})$")) {
            int menit = Integer.parseInt(durasi.split(":")[0]);
            int detik = Integer.parseInt(durasi.split(":")[1]);
            Long durasiTotal = TimeUnit.SECONDS.toMillis(menit * 60 + detik);
            if(durasiTotal < durasiMin) durasiTotal = durasiMin;
            if(durasiTotal > durasiMax) durasiTotal = durasiMax;

            Boothgames newBoothgame = new Boothgames();
            newBoothgame.setIdBooth(getNextBoothgameID());
            newBoothgame.setNama(boothgamesDTO.getNama());
            newBoothgame.setIdPenjaga1(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga1()).get());
            if (boothgamesDTO.getIdPenjaga2() != null) {
                newBoothgame.setIdPenjaga2(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga2()).get());
            }
            newBoothgame.setSopGames(boothgamesDTO.getSopGames());
            newBoothgame.setLokasi(lokasiService.getLokasiById(boothgamesDTO.getLokasi()).get());
            newBoothgame.setTipegame(boothgamesDTO.getTipeGameClass());
            newBoothgame.setDurasiPermainan(durasiTotal);
            newBoothgame.setFotoBooth(boothgamesDTO.getFotoBooth()); // update
            return Optional.of(boothgamesRepository.save(newBoothgame));
        }
        return Optional.empty();
    }

    public List<Boothgames> getAllBoothgames() {
        return boothgamesRepository.findAll();
    }

    public Object getAvailableDatas() {
        ArrayList<Object> listAvailablePanitia = new ArrayList<Object>();
        ArrayList<Object> listAvailableLokasi = new ArrayList<Object>();
        for (Panitia panitia : panitiaService.getAllPanitia()) {
            if (!getBoothgameByPanitia(panitia.getIdPanitia()).isPresent() && !panitia.getIsAdmin()
                    && !panitia.getDivisi().equals(Divisi.KETUAACARA)) {
                listAvailablePanitia.add(
                        Map.of(
                                "idPanitia", panitia.getIdPanitia(),
                                "namaPanitia", panitia.getNama()));
            }
        }

        for (Lokasi lokasi : lokasiService.getAllLokasi()) {
            if (!getBoothgameByLokasi(lokasi.getNoRuangan()).isPresent()) {
                listAvailableLokasi.add(
                        Map.of(
                                "noRuangan", lokasi.getNoRuangan()));
            }
        }

        Object result = Map.of(
                "listPanitia", listAvailablePanitia,
                "listLokasi", listAvailableLokasi);
        return result;
    }

    public Optional<Boothgames> getBoothgameById(String id) {
        return boothgamesRepository.findById(id);
    }

    public Optional<Boothgames> getBoothgameByPanitia(String id) {
        Optional<Panitia> panitiaOptional = panitiaService.getPanitiaById(id);
        if (panitiaOptional.isPresent()) {
            Panitia panitia = panitiaOptional.get();
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
        }
        return Optional.empty();
    }

    public Optional<Boothgames> getBoothgameByLokasi(String noruangan) {
        Lokasi lokasi = lokasiService.getLokasiById(noruangan).get();
        for (Boothgames boothgame : getAllBoothgames()) {
            if (boothgame.getLokasi().equals(lokasi)) {
                return Optional.of(boothgame);
            }
        }
        return Optional.empty();
    }

    public ArrayList<Boothgames> getSingleGames() {
        ArrayList<Boothgames> result = new ArrayList<Boothgames>();
        for (Boothgames boothgame : getAllBoothgames()) {
            if (boothgame.getTipegame().equals(Tipegame.SINGLE)) {
                result.add(boothgame);
            }
        }
        return result;
    }

    public ArrayList<Boothgames> getDuelGames() {
        ArrayList<Boothgames> result = new ArrayList<Boothgames>();
        for (Boothgames boothgame : getAllBoothgames()) {
            if (boothgame.getTipegame().equals(Tipegame.DUEL)) {
                result.add(boothgame);
            }
        }
        return result;
    }

    public Object getGameStats() {
        ArrayList<Boothgames> duelGames = new ArrayList<Boothgames>();
        ArrayList<Boothgames> singleGames = new ArrayList<Boothgames>();
        for (Boothgames boothgame : getAllBoothgames()) {
            if (boothgame.getTipegame().equals(Tipegame.DUEL)) {
                duelGames.add(boothgame);
            } else {
                singleGames.add(boothgame);
            }
        }
        return Map.of(
                "totalGame", duelGames.size() + singleGames.size(),
                "singleGame", singleGames.size(),
                "duelGame", duelGames.size());
    }

    public Optional<Boothgames> updateBoothgame(String id, BoothgamesDTO boothgamesDTO) {
        String durasi = boothgamesDTO.getDurasiPermainan();
        if (durasi.matches("/^([0-9]{1,2})(:)([0-9]{1,2})/gm")) {
            int menit = Integer.parseInt(durasi.split(":")[0]);
            int detik = Integer.parseInt(durasi.split(":")[1]);
            Long durasiTotal = TimeUnit.SECONDS.toMillis(menit * 60 + detik);
            if(durasiTotal < durasiMin) durasiTotal = durasiMin;
            if(durasiTotal > durasiMax) durasiTotal = durasiMax;
            Optional<Boothgames> optionalBoothgame = boothgamesRepository.findById(id);
            if (optionalBoothgame.isPresent()) {
                Boothgames existingBoothgame = optionalBoothgame.get();
                existingBoothgame.setNama(boothgamesDTO.getNama());
                existingBoothgame.setIdPenjaga1(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga1()).get());
                if (boothgamesDTO.getIdPenjaga2() != null) {
                    existingBoothgame.setIdPenjaga2(panitiaService.getPanitiaById(boothgamesDTO.getIdPenjaga2()).get());
                }
                existingBoothgame.setSopGames(boothgamesDTO.getSopGames());
                existingBoothgame.setLokasi(lokasiService.getLokasiById(boothgamesDTO.getLokasi()).get());
                existingBoothgame.setTipegame(boothgamesDTO.getTipeGameClass());
                existingBoothgame.setDurasiPermainan(durasiTotal);
                existingBoothgame.setFotoBooth(boothgamesDTO.getFotoBooth()); // update
                boothgamesRepository.save(existingBoothgame);
                return Optional.of(existingBoothgame);
            }
        }
        return Optional.empty();
    }

    public Optional<Boothgames> updateSOP(String id, BoothgamesDTO boothgamesDTO) {
        Optional<Boothgames> optionalBoothgame = boothgamesRepository.findById(id);
        if (optionalBoothgame.isPresent()) {
            Boothgames existingBoothgame = optionalBoothgame.get();
            existingBoothgame.setSopGames(boothgamesDTO.getSopGames());
            boothgamesRepository.save(existingBoothgame);
            return Optional.of(existingBoothgame);
        }
        return Optional.empty();
    }

    public ArrayList<Boothgames> searchBoothgame(String namaBooth, String lantai, String tipegame) {
        ArrayList<Boothgames> tempArray = new ArrayList<Boothgames>();
        ArrayList<Boothgames> resultArray = new ArrayList<Boothgames>();
        ArrayList<Boothgames> deleteArray;
        for (Boothgames boothgame : getAllBoothgames()) {
            if (tipegame != null && !tipegame.matches("^[\s]*$") && !tipegame.equals("")) {
                if (boothgame.getTipegame().equals(Tipegame.fromString(tipegame))) {
                    resultArray.add(boothgame);
                }
            } else {
                resultArray.add(boothgame);
            }
        }
        if (lantai != null && !lantai.matches("^[\s]*$") && !lantai.equals("")) {
            for (Boothgames boothgame : resultArray) {
                if (String.valueOf(boothgame.getLokasi().getLantai()).equals(lantai)) {
                    tempArray.add(boothgame);
                }
            }
            deleteArray = new ArrayList<Boothgames>(resultArray);
            deleteArray.removeAll(tempArray);
            resultArray.removeAll(deleteArray);
            tempArray.removeAll(tempArray);
        }
        if (namaBooth != null && !namaBooth.matches("^[\s]*$") && !namaBooth.equals("")) {
            for (Boothgames boothgame : resultArray) {
                if (boothgame.getNama().toLowerCase().contains(namaBooth.toLowerCase())) {
                    tempArray.add(boothgame);
                }
            }
            deleteArray = new ArrayList<Boothgames>(resultArray);
            deleteArray.removeAll(tempArray);
            resultArray.removeAll(deleteArray);
            tempArray.removeAll(tempArray);
        }
        return resultArray;
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
