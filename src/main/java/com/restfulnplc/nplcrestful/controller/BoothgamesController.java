package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
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
@RequestMapping(value = "/api/boothgames")
public class BoothgamesController {

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<Boothgames> listBoothGames = Collections.<Boothgames>emptyList();

    @PostMapping
    public ResponseEntity<Response> addBoothgame(HttpServletRequest request,
            @RequestBody BoothgamesDTO boothgamesDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Boothgame");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Boothgames newBoothgame = boothgamesService.addBoothgame(boothgamesDTO);
                    listBoothGames.add(newBoothgame);
                    response.setMessage("Boothgame Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listBoothGames.stream().map(boothgame -> Map.of(
                            "idBoothGame", boothgame.getIdBooth(),
                            "namaBoothGame", boothgame.getNama(),
                            "panitia1", boothgame.getIdPenjaga1(),
                            "panitia2", boothgame.getIdPenjaga2(),
                            "sopGame", boothgame.getSopGames(),
                            "lokasi", boothgame.getLokasi(),
                            "tipeGame", boothgame.getTipegame().toString(),
                            "durasiPermainan", boothgame.getDurasiPermainan(),
                            "fotoBooth", boothgame.getFotoBooth())).collect(Collectors.toList()));
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
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllBoothgames(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Boothgames");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Boothgames> boothgamesList = boothgamesService.getAllBoothgames();
                if (boothgamesList.size() > 0) {
                    response.setMessage("All Boothgames Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(boothgamesList.stream().map(boothgame -> Map.of(
                            "idBoothGame", boothgame.getIdBooth(),
                            "namaBoothGame", boothgame.getNama(),
                            "panitia1", boothgame.getIdPenjaga1(),
                            "panitia2", boothgame.getIdPenjaga2(),
                            "sopGame", boothgame.getSopGames(),
                            "lokasi", boothgame.getLokasi(),
                            "tipeGame", boothgame.getTipegame().toString(),
                            "durasiPermainan", boothgame.getDurasiPermainan(),
                            "fotoBooth", boothgame.getFotoBooth())).collect(Collectors.toList()));
                } else {
                    response.setMessage("No Boothgames Found");
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
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBoothgameById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Boothgame By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Boothgames> boothgameOptional = boothgamesService.getBoothgameById(id);
                if (boothgameOptional.isPresent()) {
                    listBoothGames.add(boothgameOptional.get());
                    response.setMessage("Boothgame Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listBoothGames.stream().map(boothgame -> Map.of(
                            "idBoothGame", boothgame.getIdBooth(),
                            "namaBoothGame", boothgame.getNama(),
                            "panitia1", boothgame.getIdPenjaga1(),
                            "panitia2", boothgame.getIdPenjaga2(),
                            "sopGame", boothgame.getSopGames(),
                            "lokasi", boothgame.getLokasi(),
                            "tipeGame", boothgame.getTipegame().toString(),
                            "durasiPermainan", boothgame.getDurasiPermainan(),
                            "fotoBooth", boothgame.getFotoBooth())).collect(Collectors.toList()));
                } else {
                    response.setMessage("Boothgame Not Found");
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
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateBoothgame(HttpServletRequest request,
            @PathVariable("id") String id, @RequestBody BoothgamesDTO boothgamesDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Boothgame");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<Boothgames> updatedBoothgame = boothgamesService.updateBoothgame(id, boothgamesDTO);
                    if (updatedBoothgame.isPresent()) {
                        listBoothGames.add(updatedBoothgame.get());
                        response.setMessage("Boothgame Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(listBoothGames.stream().map(boothgame -> Map.of(
                                "idBoothGame", boothgame.getIdBooth(),
                                "namaBoothGame", boothgame.getNama(),
                                "panitia1", boothgame.getIdPenjaga1(),
                                "panitia2", boothgame.getIdPenjaga2(),
                                "sopGame", boothgame.getSopGames(),
                                "lokasi", boothgame.getLokasi(),
                                "tipeGame", boothgame.getTipegame().toString(),
                                "durasiPermainan", boothgame.getDurasiPermainan(),
                                "fotoBooth", boothgame.getFotoBooth())).collect(Collectors.toList())); //update
                    } else {
                        response.setMessage("Boothgame Not Found");
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
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBoothgame(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Boothgame");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = boothgamesService.deleteBoothgame(id);
                    if (isDeleted) {
                        response.setMessage("Boothgame Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Boothgame Not Found");
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
        listBoothGames.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}