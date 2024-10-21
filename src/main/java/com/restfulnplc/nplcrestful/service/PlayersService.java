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
        int highestID = 0;
        for (Players player : players) {
            int newID = Integer.parseInt(player.getIdPlayer().split("PLAYERS")[1]);
            if (newID > highestID)
                highestID = newID;
        }
        return "PLAYERS" + (highestID + 1);
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
    
    public void reset()
    {
        playersRepository.deleteAll();
    }
}
