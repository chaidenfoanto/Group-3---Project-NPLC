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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/singlematch")
public class SinglematchController {

    @Autowired
    private SinglematchService singlematchService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addSinglematch(HttpServletRequest request,
            @RequestBody SinglematchDTO singlematchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Single Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Singlematch newSinglematch = singlematchService.addSinglematch(singlematchDTO);
                    response.setMessage("Single Match Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "noMatch", newSinglematch.getNoMatch(),
                            "idTeam", newSinglematch.getTeam(),
                            "waktuMulai", newSinglematch.getWaktuMulai(),
                            "waktuSelesai", newSinglematch.getWaktuSelesai(),
                            "noKartu", newSinglematch.getListKartu(),
                            "inputBy", newSinglematch.getInputBy(),
                            "totalPoin", newSinglematch.getTotalPoin(),
                            "totalBintang", newSinglematch.getTotalBintang(),
                            "boothgames", newSinglematch.getBoothGames()));
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
    public ResponseEntity<Response> getAllSingleMatches(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Single Matches");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Singlematch> singleMatchList = singlematchService.getAllSinglematches();
                if (singleMatchList.size() > 0) {
                    response.setMessage("All Single Matches Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (Singlematch singleMatch : singleMatchList) {
                        listData.add(Map.of(
                                "noMatch", singleMatch.getNoMatch(),
                                "idTeam", singleMatch.getTeam(),
                                "waktuMulai", singleMatch.getWaktuMulai(),
                                "waktuSelesai", singleMatch.getWaktuSelesai(),
                                "noKartu", singleMatch.getListKartu(),
                                "inputBy", singleMatch.getInputBy(),
                                "totalPoin", singleMatch.getTotalPoin(),
                                "totalBintang", singleMatch.getTotalBintang(),
                                "boothgames", singleMatch.getBoothGames()));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Single Matches Found");
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
    public ResponseEntity<Response> getSingleMatchById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Single Match By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Singlematch> singleMatchOptional = singlematchService.getSinglematchById(id);
                if (singleMatchOptional.isPresent()) {
                    Singlematch singleMatch = singleMatchOptional.get();
                    response.setMessage("Single Match Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "noMatch", singleMatch.getNoMatch(),
                            "idTeam", singleMatch.getTeam(),
                            "waktuMulai", singleMatch.getWaktuMulai(),
                            "waktuSelesai", singleMatch.getWaktuSelesai(),
                            "noKartu", singleMatch.getListKartu(),
                            "inputBy", singleMatch.getInputBy(),
                            "totalPoin", singleMatch.getTotalPoin(),
                            "totalBintang", singleMatch.getTotalBintang(),
                            "boothgames", singleMatch.getBoothGames()));
                } else {
                    response.setMessage("Single Match Not Found");
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
                        Singlematch singleMatch = updatedSingleMatch.get();
                        response.setMessage("Single Match Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "noMatch", singleMatch.getNoMatch(),
                                "idTeam", singleMatch.getTeam(),
                                "waktuMulai", singleMatch.getWaktuMulai(),
                                "waktuSelesai", singleMatch.getWaktuSelesai(),
                                "noKartu", singleMatch.getListKartu(),
                                "inputBy", singleMatch.getInputBy(),
                                "totalPoin", singleMatch.getTotalPoin(),
                                "totalBintang", singleMatch.getTotalBintang(),
                                "boothgames", singleMatch.getBoothGames()));
                    } else {
                        response.setMessage("Single Match Not Found");
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

    @GetMapping("/getByTeam/{id}")
    public ResponseEntity<Response> getSingleMatchByTeamId(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get SingleMatch By Team ID");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                ArrayList<Singlematch> singleMatchList = singlematchService.getSinglematchesByUser(id);
                if (singleMatchList.size() > 0) {
                    response.setMessage("SingleMatch Data Retrieved");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (Singlematch singleMatch : singleMatchList) {
                        listData.add(Map.of(
                                "noMatch", singleMatch.getNoMatch(),
                                "idTeam", singleMatch.getTeam(),
                                "waktuMulai", singleMatch.getWaktuMulai(),
                                "waktuSelesai", singleMatch.getWaktuSelesai(),
                                "noKartu", singleMatch.getListKartu(),
                                "inputBy", singleMatch.getInputBy(),
                                "totalPoin", singleMatch.getTotalPoin(),
                                "totalBintang", singleMatch.getTotalBintang(),
                                "boothgames", singleMatch.getBoothGames()));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Single Matches With That ID Found");
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
        } catch (

        Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity.status(response.getHttpCode().getStatus()).contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteSingleMatch(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Single Match");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<Singlematch> singleMatch = singlematchService.getSinglematchById(id);
                    if (singleMatch.isPresent()) {
                        if (singlematchService.deleteSinglematch(id)) {
                            response.setMessage("Single Match Deleted Successfully");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(Map.of(
                                    "noMatch", singleMatch.get().getNoMatch(),
                                    "idTeam", singleMatch.get().getTeam(),
                                    "waktuMulai", singleMatch.get().getWaktuMulai(),
                                    "waktuSelesai", singleMatch.get().getWaktuSelesai(),
                                    "noKartu", singleMatch.get().getListKartu(),
                                    "inputBy", singleMatch.get().getInputBy(),
                                    "totalPoin", singleMatch.get().getTotalPoin(),
                                    "totalBintang", singleMatch.get().getTotalBintang(),
                                    "boothgames", singleMatch.get().getBoothGames()));
                        } else {
                            response.setMessage("Single Match Deletion Failed");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.NOT_IMPLEMENTED);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("Single Match Not Found");
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
