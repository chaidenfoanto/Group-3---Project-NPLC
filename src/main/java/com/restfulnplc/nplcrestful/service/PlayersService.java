package com.restfulnplc.nplcrestful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.repository.PlayersRepository;

import java.util.Collections;

@Service
public class PlayersService {
    @Autowired
    private PlayersRepository playersRepository;

    public String getNextPlayerID()
    {
        List<Players> players = playersRepository.findAll();
        if(players.size() > 0) return "PLAYER" + (Integer.parseInt(players.get(players.size()-1).getIdPlayer().split("PLAYER")[1]) + 1);
        return "PLAYER1";
    }

    public List<Players> getPlayersByTeam(String teamID)
    {
        List<Players> players = Collections.<Players>emptyList();
        for(Players player : playersRepository.findAll()){
            if(player.getTeam().getIdTeam().equals(teamID)){
                players.add(player);
            }
        }
        return players;
    }
}
