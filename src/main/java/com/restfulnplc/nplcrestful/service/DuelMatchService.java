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

    @Autowired
    private TeamService teamService;

    @Autowired
    private BoothgamesService boothgamesService;

    public DuelMatch addDuelMatch(DuelMatchDTO duelMatchDTO) {
        DuelMatch duelMatch = new DuelMatch();
        duelMatch.setNoMatch(duelMatchDTO.getNoMatch());
        duelMatch.setTeam1(teamService.getTeamByID(duelMatchDTO.getTeam1()).get());
        duelMatch.setTeam2(teamService.getTeamByID(duelMatchDTO.getTeam2()).get());
        duelMatch.setWaktuMulai(duelMatchDTO.getWaktuMulai());
        duelMatch.setWaktuSelesai(duelMatchDTO.getWaktuSelesai());
        duelMatch.setInputBy(duelMatchDTO.getInputBy());
        duelMatch.setTimMenang(teamService.getTeamByID(duelMatchDTO.getTimMenang()).get());
        duelMatch.setBoothGames(boothgamesService.getBoothgameById(duelMatchDTO.getBoothGames()).get());
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
            duelMatch.setTeam1(teamService.getTeamByID(duelMatchDTO.getTeam1()).get());
            duelMatch.setTeam2(teamService.getTeamByID(duelMatchDTO.getTeam2()).get());
            duelMatch.setWaktuMulai(duelMatchDTO.getWaktuMulai());
            duelMatch.setWaktuSelesai(duelMatchDTO.getWaktuSelesai());
            duelMatch.setInputBy(duelMatchDTO.getInputBy());
            duelMatch.setTimMenang(teamService.getTeamByID(duelMatchDTO.getTimMenang()).get());
            duelMatch.setBoothGames(boothgamesService.getBoothgameById(duelMatchDTO.getBoothGames()).get());
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