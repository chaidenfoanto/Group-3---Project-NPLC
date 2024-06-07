package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.Singlematch;
import com.restfulnplc.nplcrestful.service.SinglematchService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/singlematch")
public class SinglematchController {

    @Autowired
    private SinglematchService singlematchService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<Singlematch> listSingleMatches = Collections.<Singlematch>emptyList();

    @PostMapping("/addSingleMatch")
    public ResponseEntity<Response> addSinglematch(HttpServletRequest request,
            @RequestBody SinglematchDTO singlematchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Single Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Singlematch newSinglematch = singlematchService.addSinglematch(singlematchDTO);
                    listSingleMatches.add(newSinglematch);
                    response.setMessage("Single Match Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listSingleMatches.stream().map(singleMatch -> Map.of(
                            "noMatch", singleMatch.getNoMatch(),
                            "idTeam", singleMatch.getTeam(),
                            "waktuMulai", singleMatch.getWaktuMulai(),
                            "waktuSelesai", singleMatch.getWaktuSelesai(),
                            "noKartu", singleMatch.getListKartu(),
                            "inputBy", singleMatch.getInputBy(),
                            "totalPoin", singleMatch.getTotalPoin(),
                            "boothgames", singleMatch.getBoothGames())).collect(Collectors.toList()));
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
        listSingleMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllSingleMatches(HttpServletRequest request) {
        response.setService("Get All Single Matches");
        String sessionToken = request.getHeader("Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Singlematch> singleMatchList = singlematchService.getAllSinglematches();
                if (singleMatchList.size() > 0) {
                    response.setMessage("All Single Matches Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(singleMatchList.stream().map(singleMatch -> Map.of(
                            "noMatch", singleMatch.getNoMatch(),
                            "idTeam", singleMatch.getTeam(),
                            "waktuMulai", singleMatch.getWaktuMulai(),
                            "waktuSelesai", singleMatch.getWaktuSelesai(),
                            "noKartu", singleMatch.getListKartu(),
                            "inputBy", singleMatch.getInputBy(),
                            "totalPoin", singleMatch.getTotalPoin(),
                            "boothgames", singleMatch.getBoothGames())).collect(Collectors.toList()));
                } else {
                    response.setMessage("No Single Matches Found");
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listSingleMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleMatchById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Single Match By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Singlematch> singleMatchOptional = singlematchService.getSinglematchById(id);
                if (singleMatchOptional.isPresent()) {
                    listSingleMatches.add(singleMatchOptional.get());
                    response.setMessage("Single Match Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(listSingleMatches.stream().map(singleMatch -> Map.of(
                            "noMatch", singleMatch.getNoMatch(),
                            "idTeam", singleMatch.getTeam(),
                            "waktuMulai", singleMatch.getWaktuMulai(),
                            "waktuSelesai", singleMatch.getWaktuSelesai(),
                            "noKartu", singleMatch.getListKartu(),
                            "inputBy", singleMatch.getInputBy(),
                            "totalPoin", singleMatch.getTotalPoin(),
                            "boothgames", singleMatch.getBoothGames())).collect(Collectors.toList()));
                } else {
                    response.setMessage("Single Match Not Found");
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listSingleMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateSinglematch(HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody SinglematchDTO singlematchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Single Match");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<Singlematch> updatedSingleMatch = singlematchService.updateSinglematch(id, singlematchDTO);
                    if (updatedSingleMatch.isPresent()) {
                        listSingleMatches.add(updatedSingleMatch.get());
                        response.setMessage("Single Match Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(listSingleMatches.stream().map(singleMatch -> Map.of(
                                "noMatch", singleMatch.getNoMatch(),
                                "idTeam", singleMatch.getTeam(),
                                "waktuMulai", singleMatch.getWaktuMulai(),
                                "waktuSelesai", singleMatch.getWaktuSelesai(),
                                "noKartu", singleMatch.getListKartu(),
                                "inputBy", singleMatch.getInputBy(),
                                "totalPoin", singleMatch.getTotalPoin(),
                                "boothgames", singleMatch.getBoothGames())).collect(Collectors.toList()));
                    } else {
                        response.setMessage("Single Match Not Found");
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listSingleMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteSingleMatch(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Single Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = singlematchService.deleteSinglematch(id);
                    if (isDeleted) {
                        response.setMessage("Single Match Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Single Match Not Found");
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
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listSingleMatches.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
