package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
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
    private PanitiaService panitiaService;

    @Autowired
    private Response response;

    List<Boothgames> listBoothGames = Collections.<Boothgames>emptyList();

    @PostMapping("/addBoothgame")
    public ResponseEntity<Response> addBoothgame(@RequestBody BoothgamesDTO boothgamesDTO) {
        response.setService("Add Boothgame");
        Boothgames newBoothgame = boothgamesService.addBoothgame(boothgamesDTO);
        listBoothGames.add(newBoothgame);
        response.setMessage("Boothgame Successfully Added");
        response.setError(false);
        response.setHttpCode(HTTPCode.CREATED);
        response.setData(listBoothGames.stream().map(boothgame -> Map.of(
            "idBoothGame", boothgame.getIdBooth(),
            "namaBoothGame", boothgame.getNama(),
            "panitia1", panitiaService.getPanitiaById(boothgame.getIdPenjaga1()),
            "panitia2", panitiaService.getPanitiaById(boothgame.getIdPenjaga2()),
            "sopGame", boothgame.getSopGames(),
            "lokasi", boothgame.getLokasi(),
            "tipeGame", boothgame.getTipegame().toString(),
            "durasiPermainan", boothgame.getDurasiPermainan()
            )).collect(Collectors.toList()));
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllBoothgames() {
        response.setService("Get All Boothgames");
        List<Boothgames> boothgamesList = boothgamesService.getAllBoothgames();
        if(boothgamesList.size() > 0) {
            response.setMessage("All Boothgames Retrieved Successfully");
            response.setError(false);
            response.setHttpCode(HTTPCode.OK);
            response.setData(boothgamesList.stream().map(boothgame -> Map.of(
                "idBoothGame", boothgame.getIdBooth(),
                "namaBoothGame", boothgame.getNama(),
                "panitia1", panitiaService.getPanitiaById(boothgame.getIdPenjaga1()),
                "panitia2", panitiaService.getPanitiaById(boothgame.getIdPenjaga2()),
                "sopGame", boothgame.getSopGames(),
                "lokasi", boothgame.getLokasi(),
                "tipeGame", boothgame.getTipegame().toString(),
                "durasiPermainan", boothgame.getDurasiPermainan()
                )).collect(Collectors.toList()));
        } else {
            response.setMessage("No Boothgames Found");
            response.setError(true);
            response.setHttpCode(HTTPCode.NO_CONTENT);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBoothgameById(@PathVariable("id") String id) {
        response.setService("Get Boothgame By ID");
        Optional<Boothgames> boothgameOptional = boothgamesService.getBoothgameById(id);
        if (boothgameOptional.isPresent()) {
            listBoothGames.add(boothgameOptional.get());
            response.setMessage("Boothgame Retrieved Successfully");
            response.setError(false);
            response.setHttpCode(HTTPCode.CREATED);
            response.setData(listBoothGames.stream().map(boothgame -> Map.of(
            "idBoothGame", boothgame.getIdBooth(),
            "namaBoothGame", boothgame.getNama(),
            "panitia1", panitiaService.getPanitiaById(boothgame.getIdPenjaga1()),
            "panitia2", panitiaService.getPanitiaById(boothgame.getIdPenjaga2()),
            "sopGame", boothgame.getSopGames(),
            "lokasi", boothgame.getLokasi(),
            "tipeGame", boothgame.getTipegame().toString(),
            "durasiPermainan", boothgame.getDurasiPermainan()
            )).collect(Collectors.toList()));
        } else {
            response.setMessage("Boothgame Not Found");
            response.setError(true);
            response.setHttpCode(HTTPCode.NO_CONTENT);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateBoothgame(@PathVariable("id") String id, @RequestBody BoothgamesDTO boothgamesDTO) {
        response.setService("Update Boothgame");
        Optional<Boothgames> updatedBoothgame = boothgamesService.updateBoothgame(id, boothgamesDTO);
        if (updatedBoothgame.isPresent()) {
            listBoothGames.add(updatedBoothgame.get());
            response.setMessage("Boothgame Updated Successfully");
            response.setError(false);
            response.setHttpCode(HTTPCode.OK);
            response.setData(listBoothGames.stream().map(boothgame -> Map.of(
            "idBoothGame", boothgame.getIdBooth(),
            "namaBoothGame", boothgame.getNama(),
            "panitia1", panitiaService.getPanitiaById(boothgame.getIdPenjaga1()),
            "panitia2", panitiaService.getPanitiaById(boothgame.getIdPenjaga2()),
            "sopGame", boothgame.getSopGames(),
            "lokasi", boothgame.getLokasi(),
            "tipeGame", boothgame.getTipegame().toString(),
            "durasiPermainan", boothgame.getDurasiPermainan()
            )).collect(Collectors.toList()));
        } else {
            response.setMessage("Boothgame Not Found");
            response.setError(true);
            response.setHttpCode(HTTPCode.NO_CONTENT);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBoothgame(@PathVariable("id") String id) {
        response.setService("Delete Boothgame");
        boolean isDeleted = boothgamesService.deleteBoothgame(id);
        if (isDeleted) {
            response.setMessage("Boothgame Deleted Successfully");
            response.setError(false);
            response.setHttpCode(HTTPCode.OK);
        } else {
            response.setMessage("Boothgame Not Found");
            response.setError(true);
            response.setHttpCode(HTTPCode.NO_CONTENT);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}