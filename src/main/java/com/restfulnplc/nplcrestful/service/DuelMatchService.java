package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.DuelMatchDTO;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.repository.DuelMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DuelMatchService {

    @Autowired
    private DuelMatchRepository duelMatchRepository;

    public DuelMatch addDuelMatch(DuelMatchDTO duelMatchDTO) {
        DuelMatch duelMatch = new DuelMatch();
        duelMatch.setNoMatch(duelMatchDTO.getNoMatch());
        duelMatch.setTeam1(duelMatchDTO.getTeam1());
        duelMatch.setTeam2(duelMatchDTO.getTeam2());
        duelMatch.setWaktuMulai(duelMatchDTO.getWaktuMulai());
        duelMatch.setWaktuSelesai(duelMatchDTO.getWaktuSelesai());
        duelMatch.setInputBy(duelMatchDTO.getInputBy());
        duelMatch.setTimMenang(duelMatchDTO.getTimMenang());
        duelMatch.setBoothGames(duelMatchDTO.getBoothGames());
        return duelMatchRepository.save(duelMatch);
    }

    public List<DuelMatch> getAllDuelMatches() {
        return duelMatchRepository.findAll();
    }

    public Optional<DuelMatch> getDuelMatchById(String id) {
        return duelMatchRepository.findById(id);
    }

    public Optional<DuelMatch> updateDuelMatch(String id, DuelMatchDTO duelMatchDTO) {
        Optional<DuelMatch> optionalDuelMatch = duelMatchRepository.findById(id);
        if (optionalDuelMatch.isPresent()) {
            DuelMatch duelMatch = optionalDuelMatch.get();
            duelMatch.setTeam1(duelMatchDTO.getTeam1());
            duelMatch.setTeam2(duelMatchDTO.getTeam2());
            duelMatch.setWaktuMulai(duelMatchDTO.getWaktuMulai());
            duelMatch.setWaktuSelesai(duelMatchDTO.getWaktuSelesai());
            duelMatch.setInputBy(duelMatchDTO.getInputBy());
            duelMatch.setTimMenang(duelMatchDTO.getTimMenang());
            duelMatch.setBoothGames(duelMatchDTO.getBoothGames());
            return Optional.of(duelMatchRepository.save(duelMatch));
        }
        return Optional.empty();
    }

    public boolean deleteDuelMatch(String id) {
        Optional<DuelMatch> optionalDuelMatch = duelMatchRepository.findById(id);
        if (optionalDuelMatch.isPresent()) {
            duelMatchRepository.delete(optionalDuelMatch.get());
            return true;
        }
        return false;
    }
}
