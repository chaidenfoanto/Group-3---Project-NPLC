package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.DuelMatchDTO;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.service.DuelMatchService;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/duelmatch")
public class DuelMatchController {

    @Autowired
    private DuelMatchService duelMatchService;

    @PostMapping("/addDuelMatch")
    public ResponseEntity<Response<DuelMatch>> addDuelMatch(@RequestBody DuelMatchDTO duelMatchDTO) {
        Response<DuelMatch> response = new Response<>();
        response.setService("Add DuelMatch");
        DuelMatch newDuelMatch = duelMatchService.addDuelMatch(duelMatchDTO);
        response.setMessage("DuelMatch Successfully Added");
        response.setData(newDuelMatch);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<DuelMatch>>> getAllDuelMatches() {
        Response<List<DuelMatch>> response = new Response<>();
        response.setService("Get All DuelMatches");
        List<DuelMatch> duelMatchList = duelMatchService.getAllDuelMatches();
        response.setData(duelMatchList);
        response.setMessage("All DuelMatches Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<DuelMatch>> getDuelMatchById(@PathVariable("id") String id) {
        Response<DuelMatch> response = new Response<>();
        response.setService("Get DuelMatch By ID");
        Optional<DuelMatch> duelMatch = duelMatchService.getDuelMatchById(id);
        if (duelMatch.isPresent()) {
            response.setData(duelMatch.get());
            response.setMessage("DuelMatch Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("DuelMatch Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<DuelMatch>> updateDuelMatch(@PathVariable("id") String id, @RequestBody DuelMatchDTO duelMatchDTO) {
        Response<DuelMatch> response = new Response<>();
        response.setService("Update DuelMatch");
        Optional<DuelMatch> updatedDuelMatch = duelMatchService.updateDuelMatch(id, duelMatchDTO);
        if (updatedDuelMatch.isPresent()) {
            response.setData(updatedDuelMatch.get());
            response.setMessage("DuelMatch Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("DuelMatch Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteDuelMatch(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete DuelMatch");
        boolean isDeleted = duelMatchService.deleteDuelMatch(id);
        if (isDeleted) {
            response.setMessage("DuelMatch Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("DuelMatch Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}
