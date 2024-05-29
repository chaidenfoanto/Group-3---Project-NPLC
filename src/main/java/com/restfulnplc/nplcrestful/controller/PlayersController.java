package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.PlayersDTO;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.service.PlayersService;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/players")
public class PlayersController {

    @Autowired
    private PlayersService playersService;

    @PostMapping("/addPlayer")
    public ResponseEntity<Response<Players>> addPlayer(@RequestBody PlayersDTO playersDTO) {
        Response<Players> response = new Response<>();
        response.setService("Add Player");
        Players newPlayer = playersService.addPlayer(playersDTO);
        response.setMessage("Player Successfully Added");
        response.setData(newPlayer);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Players>>> getAllPlayers() {
        Response<List<Players>> response = new Response<>();
        response.setService("Get All Players");
        List<Players> playersList = playersService.getAllPlayers();
        response.setData(playersList);
        response.setMessage("All Players Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Players>> getPlayerById(@PathVariable("id") String id) {
        Response<Players> response = new Response<>();
        response.setService("Get Player By ID");
        Optional<Players> player = playersService.getPlayerById(id);
        if (player.isPresent()) {
            response.setData(player.get());
            response.setMessage("Player Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Player Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Players>> updatePlayer(@PathVariable("id") String id, @RequestBody PlayersDTO playersDTO) {
        Response<Players> response = new Response<>();
        response.setService("Update Player");
        Optional<Players> updatedPlayer = playersService.updatePlayer(id, playersDTO);
        if (updatedPlayer.isPresent()) {
            response.setData(updatedPlayer.get());
            response.setMessage("Player Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Player Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deletePlayer(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete Player");
        boolean isDeleted = playersService.deletePlayer(id);
        if (isDeleted) {
            response.setMessage("Player Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Player Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}
