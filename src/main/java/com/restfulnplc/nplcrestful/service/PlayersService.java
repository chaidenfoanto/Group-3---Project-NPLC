package com.restfulnplc.nplcrestful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.repository.PlayersRepository;

@Service
public class PlayersService {
    @Autowired
    private PlayersRepository playersRepository;

    public String getNextPlayerID()
    {
        List<Players> players = playersRepository.findAll();
        if(players.size() > 0) return "PLAYER" + (Integer.parseInt(players.getLast().getIdPlayer().split("PLAYER")[1]) + 1);
        return "PLAYER1";
    }
}
