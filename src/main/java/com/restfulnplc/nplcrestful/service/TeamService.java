package com.restfulnplc.nplcrestful.service;

import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;

import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.dto.TeamDTO;
import com.restfulnplc.nplcrestful.repository.TeamRepository;
import com.restfulnplc.nplcrestful.util.PasswordHasherMatcher;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PasswordHasherMatcher passwordMaker;

    @Autowired
    private PlayersService playersService;

    public Optional<Team> findTeamByUsername(String username)
    {
        List<Team> listTeam = teamRepository.findAll();
        for(Team Team : listTeam){
            if(Team.getUsername().equals(username)){
                return Optional.of(Team);
            }
        }
        return Optional.empty();
    }

    public Optional<Team> getTeamByID(String id)
    {
        if(teamRepository.findById(id).isPresent()) {
            return teamRepository.findById(id);
        }
        return Optional.empty();
    }

    public Team addTeam(TeamDTO teamDTO)
    {
        String teamID = getNextTeamID();
        Team newTeam = new Team();
        newTeam.setIdTeam(teamID);
        newTeam.setNama(teamDTO.getNama());
        newTeam.setUsername(teamDTO.getUsername());
        newTeam.setPassUsr(passwordMaker.hashPassword(teamDTO.getPassUsr()));
        newTeam.setAsalSekolah(teamDTO.getAsalSekolah());
        newTeam.setKategoriTeam(teamDTO.getKategoriTeamClass());
        newTeam.setChanceRoll(0);
        newTeam.setTotalPoin(0);

        Set<Players> newPlayers = Collections.<Players>emptySet();
        String playerID = "";
        
        if(teamDTO.checkPlayer(1)){
            playerID = playersService.getNextPlayerID();
            Players newPlayer1 = new Players();
            newPlayer1.setIdPlayer(playerID);
            newPlayer1.setTeam(newTeam);
            newPlayer1.setNama(teamDTO.getNamaPlayer1());
            newPlayer1.setFoto(Base64.getDecoder().decode(teamDTO.getFotoPlayer1()));
            newPlayers.add(newPlayer1);
        }

        if(teamDTO.checkPlayer(2)){
            playerID = playersService.getNextPlayerID();
            Players newPlayer2 = new Players();
            newPlayer2.setIdPlayer(playerID);
            newPlayer2.setTeam(newTeam);
            newPlayer2.setNama(teamDTO.getNamaPlayer2());
            newPlayer2.setFoto(Base64.getDecoder().decode(teamDTO.getFotoPlayer2()));
            newPlayers.add(newPlayer2);
        }

        if(teamDTO.checkPlayer(3)){
            playerID = playersService.getNextPlayerID();
            Players newPlayer3 = new Players();
            newPlayer3.setIdPlayer(playerID);
            newPlayer3.setTeam(newTeam);
            newPlayer3.setNama(teamDTO.getNamaPlayer3());
            newPlayer3.setFoto(Base64.getDecoder().decode(teamDTO.getFotoPlayer3()));
            newPlayers.add(newPlayer3);
        }

        newTeam.setPlayers(newPlayers);
        teamRepository.save(newTeam);

        return newTeam;
    }

    public String getNextTeamID()
    {
        List<Team> teams = teamRepository.findAll();
        if(teams.size() > 0) return "TEAM" + (Integer.parseInt(teams.get(teams.size()-1).getIdTeam().split("TEAM")[1]) + 1);
        return "TEAM1";
    }

    public boolean checkTeam(String id) {
        return teamRepository.findById(id).isPresent();
    }

    public List<Team> getAllTeam()
    {
        return teamRepository.findAll();
    }
    
    public boolean checkUsernameExists(String username) {
        for(Team team : getAllTeam()) {
            if(team.getUsername().equals(username)) return true;
        }
        return false;
    }
    
}