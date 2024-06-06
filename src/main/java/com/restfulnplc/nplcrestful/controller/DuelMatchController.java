package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.DuelMatchDTO;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.service.DuelMatchService;
import com.restfulnplc.nplcrestful.service.LoginService;
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
@RequestMapping(value = "/api/duelmatch")
public class DuelMatchController {

    @Autowired
    private DuelMatchService duelMatchService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<DuelMatch> listDuelMatches = Collections.<DuelMatch>emptyList();

    @PostMapping("/addDuelMatch")
    public ResponseEntity<Response> addDuelMatch(@RequestHeader("Token") String sessionToken,
            @RequestBody DuelMatchDTO duelMatchDTO) {
        response.setService("Add Duel Match");
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                DuelMatch newDuelMatch = duelMatchService.addDuelMatch(duelMatchDTO);
                listDuelMatches.add(newDuelMatch);
                response.setMessage("Duel Match Successfully Added");
                response.setError(false);
                response.setHttpCode(HTTPCode.CREATED);
                response.setData(listDuelMatches.stream().map(duelMatch -> Map.of(
                        "noMatch", duelMatch.getNoMatch(),
                        "team1", duelMatch.getTeam1(),
                        "team2", duelMatch.getTeam2(),
                        "waktuMulai", duelMatch.getWaktuMulai(),
                        "waktuSelesai", duelMatch.getWaktuSelesai(),
                        "inputBy", duelMatch.getInputBy(),
                        "timMenang", duelMatch.getTimMenang(),
                        "boothgames", duelMatch.getBoothGames())).collect(Collectors.toList()));
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listDuelMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllDuelMatches(@RequestHeader("Token") String sessionToken) {
        response.setService("Get All Duel Matches");
        if (loginService.checkSessionAlive(sessionToken)) {
            List<DuelMatch> duelMatchList = duelMatchService.getAllDuelMatches();
            if (duelMatchList.size() > 0) {
                response.setMessage("All Duel Matches Retrieved Successfully");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(duelMatchList.stream().map(duelMatch -> Map.of(
                        "noMatch", duelMatch.getNoMatch(),
                        "team1", duelMatch.getTeam1(),
                        "team2", duelMatch.getTeam2(),
                        "waktuMulai", duelMatch.getWaktuMulai(),
                        "waktuSelesai", duelMatch.getWaktuSelesai(),
                        "inputBy", duelMatch.getInputBy(),
                        "timMenang", duelMatch.getTimMenang(),
                        "boothGames", duelMatch.getBoothGames())).collect(Collectors.toList()));
            } else {
                response.setMessage("No Duel Matches Found");
                response.setError(true);
                response.setHttpCode(HTTPCode.NO_CONTENT);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listDuelMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDuelMatchById(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id) {
        response.setService("Get Duel Match By ID");
        if (loginService.checkSessionAlive(sessionToken)) {
            Optional<DuelMatch> duelMatchOptional = duelMatchService.getDuelMatchById(id);
            if (duelMatchOptional.isPresent()) {
                listDuelMatches.add(duelMatchOptional.get());
                response.setMessage("Duel Match Retrieved Successfully");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(listDuelMatches.stream().map(duelMatch -> Map.of(
                        "noMatch", duelMatch.getNoMatch(),
                        "team1", duelMatch.getTeam1(),
                        "team2", duelMatch.getTeam2(),
                        "waktuMulai", duelMatch.getWaktuMulai(),
                        "waktuSelesai", duelMatch.getWaktuSelesai(),
                        "inputBy", duelMatch.getInputBy(),
                        "timMenang", duelMatch.getTimMenang(),
                        "boothGames", duelMatch.getBoothGames())).collect(Collectors.toList()));
            } else {
                response.setMessage("Duel Match Not Found");
                response.setError(true);
                response.setHttpCode(HTTPCode.NO_CONTENT);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listDuelMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDuelMatch(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id,
            @RequestBody DuelMatchDTO duelMatchDTO) {
        response.setService("Update Duel Match");
        if (loginService.checkSessionPanitia(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                Optional<DuelMatch> updatedDuelMatch = duelMatchService.updateDuelMatch(id, duelMatchDTO);
                if (updatedDuelMatch.isPresent()) {
                    listDuelMatches.add(updatedDuelMatch.get());
                    response.setMessage("Duel Match Updated Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(listDuelMatches.stream().map(duelMatch -> Map.of(
                            "noMatch", duelMatch.getNoMatch(),
                            "team1", duelMatch.getTeam1(),
                            "team2", duelMatch.getTeam2(),
                            "waktuMulai", duelMatch.getWaktuMulai(),
                            "waktuSelesai", duelMatch.getWaktuSelesai(),
                            "inputBy", duelMatch.getInputBy(),
                            "timMenang", duelMatch.getTimMenang(),
                            "boothGames", duelMatch.getBoothGames())).collect(Collectors.toList()));
                } else {
                    response.setMessage("Duel Match Not Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.NO_CONTENT);
                    response.setData(new ErrorMessage(response.getHttpCode()));
                }
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listDuelMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteDuelMatch(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id) {
        response.setService("Delete Duel Match");
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                boolean isDeleted = duelMatchService.deleteDuelMatch(id);
                if (isDeleted) {
                    response.setMessage("Duel Match Deleted Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                } else {
                    response.setMessage("Duel Match Not Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.NO_CONTENT);
                    response.setData(new ErrorMessage(response.getHttpCode()));
                }
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
