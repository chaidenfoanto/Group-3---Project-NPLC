package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/boothgames")
public class BoothgamesController {

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private Response response;

    @PostMapping("/addBoothgame")
    public ResponseEntity<Response> addBoothgame(@RequestBody BoothgamesDTO boothgamesDTO) {
        List<Boothgames> listBoothGames = Collections.<Boothgames>emptyList();
        response.setService("Add Boothgame");
        Boothgames newBoothgame = boothgamesService.addBoothgame(boothgamesDTO);
        listBoothGames.add(newBoothgame);
        response.setMessage("Boothgame Successfully Added");
        response.setData(listBoothGames.stream().map(boothgame -> Map.of(
            "idBoothGame", boothgame.getIdBooth(),
            "namaBoothGame", boothgame.getNama()
            )).collect(Collectors.toList()));
        response.setError(false);
        response.setHttpCode(HTTPCode.OK);
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Boothgames>>> getAllBoothgames() {
        Response<List<Boothgames>> response = new Response<>();
        response.setService("Get All Boothgames");
        List<Boothgames> boothgamesList = boothgamesService.getAllBoothgames();
        response.setData(boothgamesList);
        response.setMessage("All Boothgames Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Boothgames>> getBoothgameById(@PathVariable("id") int id) {
        Response<Boothgames> response = new Response<>();
        response.setService("Get Boothgame By ID");
        Optional<Boothgames> boothgame = boothgamesService.getBoothgameById(id);
        if (boothgame.isPresent()) {
            response.setData(boothgame.get());
            response.setMessage("Boothgame Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Boothgame Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Boothgames>> updateBoothgame(@PathVariable("id") int id, @RequestBody BoothgamesDTO boothgamesDTO) {
        Response<Boothgames> response = new Response<>();
        response.setService("Update Boothgame");
        Optional<Boothgames> updatedBoothgame = boothgamesService.updateBoothgame(id, boothgamesDTO);
        if (updatedBoothgame.isPresent()) {
            response.setData(updatedBoothgame.get());
            response.setMessage("Boothgame Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Boothgame Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteBoothgame(@PathVariable("id") int id) {
        Response<String> response = new Response<>();
        response.setService("Delete Boothgame");
        boolean isDeleted = boothgamesService.deleteBoothgame(id);
        if (isDeleted) {
            response.setMessage("Boothgame Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Boothgame Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}