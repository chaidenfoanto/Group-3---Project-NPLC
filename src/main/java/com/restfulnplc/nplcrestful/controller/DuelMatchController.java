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

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/duelmatch")
public class DuelMatchController {

    @Autowired
    private DuelMatchService duelMatchService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addDuelMatch(HttpServletRequest request,
            @RequestBody DuelMatchDTO duelMatchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Duel Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    DuelMatch newDuelMatch = duelMatchService.addDuelMatch(duelMatchDTO);
                    response.setMessage("Duel Match Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "noMatch", newDuelMatch.getNoMatch(),
                            "team1", newDuelMatch.getTeam1(),
                            "team2", newDuelMatch.getTeam2(),
                            "waktuMulai", newDuelMatch.getWaktuMulai(),
                            "waktuSelesai", newDuelMatch.getWaktuSelesai(),
                            "inputBy", newDuelMatch.getInputBy(),
                            "timMenang", newDuelMatch.getTimMenang(),
                            "boothgames", newDuelMatch.getBoothGames()));
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllDuelMatches(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Duel Matches");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<DuelMatch> duelMatchList = duelMatchService.getAllDuelMatches();
                if (duelMatchList.size() > 0) {
                    response.setMessage("All Duel Matches Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for (DuelMatch duelMatch : duelMatchList) {
                        listData.add(Map.of(
                                "noMatch", duelMatch.getNoMatch(),
                                "team1", duelMatch.getTeam1(),
                                "team2", duelMatch.getTeam2(),
                                "waktuMulai", duelMatch.getWaktuMulai(),
                                "waktuSelesai", duelMatch.getWaktuSelesai(),
                                "inputBy", duelMatch.getInputBy(),
                                "timMenang", duelMatch.getTimMenang(),
                                "boothgames", duelMatch.getBoothGames()
                        ));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Duel Matches Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(new ErrorMessage(response.getHttpCode()));
                }
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.BAD_REQUEST);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDuelMatchById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Duel Match By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<DuelMatch> duelMatchOptional = duelMatchService.getDuelMatchById(id);
                if (duelMatchOptional.isPresent()) {
                    DuelMatch duelMatch = duelMatchOptional.get();
                    response.setMessage("Duel Match Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "noMatch", duelMatch.getNoMatch(),
                            "team1", duelMatch.getTeam1(),
                            "team2", duelMatch.getTeam2(),
                            "waktuMulai", duelMatch.getWaktuMulai(),
                            "waktuSelesai", duelMatch.getWaktuSelesai(),
                            "inputBy", duelMatch.getInputBy(),
                            "timMenang", duelMatch.getTimMenang(),
                            "boothgames", duelMatch.getBoothGames()));
                } else {
                    response.setMessage("Duel Match Not Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(new ErrorMessage(response.getHttpCode()));
                }
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.BAD_REQUEST);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDuelMatch(HttpServletRequest request,
            @PathVariable("id") String id, @RequestBody DuelMatchDTO duelMatchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Duel Match");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<DuelMatch> updatedDuelMatch = duelMatchService.updateDuelMatch(id, duelMatchDTO);
                    if (updatedDuelMatch.isPresent()) {
                        DuelMatch duelMatch = updatedDuelMatch.get();
                        response.setMessage("Duel Match Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "noMatch", duelMatch.getNoMatch(),
                                "team1", duelMatch.getTeam1(),
                                "team2", duelMatch.getTeam2(),
                                "waktuMulai", duelMatch.getWaktuMulai(),
                                "waktuSelesai", duelMatch.getWaktuSelesai(),
                                "inputBy", duelMatch.getInputBy(),
                                "timMenang", duelMatch.getTimMenang(),
                                "boothgames", duelMatch.getBoothGames()));
                    } else {
                        response.setMessage("Duel Match Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.OK);
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteDuelMatch(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Duel Match");
        try {
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
                        response.setHttpCode(HTTPCode.OK);
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
