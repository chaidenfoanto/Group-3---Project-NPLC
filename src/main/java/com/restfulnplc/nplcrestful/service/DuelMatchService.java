package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.DuelMatchDTO;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.repository.DuelMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private PanitiaService panitiaService;

    public DuelMatch addDuelMatch(DuelMatchDTO duelMatchDTO) {
        DuelMatch duelMatch = new DuelMatch();
        duelMatch.setNoMatch(duelMatchDTO.getNoMatch());
        duelMatch.setTeam1(teamService.getTeamById(duelMatchDTO.getTeam1()).get());
        duelMatch.setTeam2(teamService.getTeamById(duelMatchDTO.getTeam2()).get());
        duelMatch.setWaktuMulai(duelMatchDTO.getWaktuMulai());
        duelMatch.setWaktuSelesai(duelMatchDTO.getWaktuSelesai());
        duelMatch.setInputBy(panitiaService.getPanitiaById(duelMatchDTO.getInputBy()).get());
        duelMatch.setTimMenang(teamService.getTeamById(duelMatchDTO.getTimMenang()).get());
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
            duelMatch.setTeam1(teamService.getTeamById(duelMatchDTO.getTeam1()).get());
            duelMatch.setTeam2(teamService.getTeamById(duelMatchDTO.getTeam2()).get());
            duelMatch.setWaktuMulai(duelMatchDTO.getWaktuMulai());
            duelMatch.setWaktuSelesai(duelMatchDTO.getWaktuSelesai());
            duelMatch.setInputBy(panitiaService.getPanitiaById(duelMatchDTO.getInputBy()).get());
            duelMatch.setTimMenang(teamService.getTeamById(duelMatchDTO.getTimMenang()).get());
            duelMatch.setBoothGames(boothgamesService.getBoothgameById(duelMatchDTO.getBoothGames()).get());
            return Optional.of(duelMatchRepository.save(duelMatch));
        }
        return Optional.empty();
    }

    public ArrayList<DuelMatch> getDuelMatchesByUser(String userId) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        for(DuelMatch duelMatch : getAllDuelMatches()) {
            if(duelMatch.getTeam1().getIdTeam().equals(userId) || duelMatch.getTeam2().getIdTeam().equals(userId)) {
                duelMatchArray.add(duelMatch);
            }
        }
        return duelMatchArray;
    }

    public ArrayList<DuelMatch> getDuelMatchesByUserAndBooth(String userId, String boothId) {
        ArrayList<DuelMatch> duelMatchArray = new ArrayList<DuelMatch>();
        for(DuelMatch duelMatch : getAllDuelMatches()) {
            if((duelMatch.getTeam1().getIdTeam().equals(userId) || duelMatch.getTeam2().getIdTeam().equals(userId)) && duelMatch.getBoothGames().getIdBooth().equals(boothId)) {
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
    
    public void reset()
    {
        duelMatchRepository.deleteAll();
    }
}
