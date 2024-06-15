package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.model.Singlematch;
import com.restfulnplc.nplcrestful.model.Tipegame;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
import com.restfulnplc.nplcrestful.service.DuelMatchService;
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
@RequestMapping(value = "/api/boothgames")
public class BoothgamesController {

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private SinglematchService singlematchService;

    @Autowired
    private DuelMatchService duelMatchService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addBoothgame(HttpServletRequest request,
            @RequestBody BoothgamesDTO boothgamesDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Boothgame");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Boothgames newBoothgame = boothgamesService.addBoothgame(boothgamesDTO);
                    response.setMessage("Boothgame Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "idBoothGame", newBoothgame.getIdBooth(),
                            "namaBoothGame", newBoothgame.getNama(),
                            "panitia1", newBoothgame.getIdPenjaga1().getIdPanitia(),
                            "panitia2", newBoothgame.getIdPenjaga2().getIdPanitia(),
                            "sopGame", newBoothgame.getSopGames(),
                            "lokasi", newBoothgame.getLokasi(),
                            "tipeGame", newBoothgame.getTipegame().toString(),
                            "durasiPermainan", newBoothgame.getDurasiPermainan(),
                            "fotoBooth", newBoothgame.getFotoBooth()));
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
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for(Boothgames boothgame : boothgamesList) {
                        listData.add(Map.of(
                            "idBoothGame", boothgame.getIdBooth(),
                            "namaBoothGame", boothgame.getNama(),
                            "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                            "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                            "sopGame", boothgame.getSopGames(),
                            "lokasi", boothgame.getLokasi(),
                            "tipeGame", boothgame.getTipegame().toString(),
                            "durasiPermainan", boothgame.getDurasiPermainan(),
                            "fotoBooth", boothgame.getFotoBooth()
                        ));
                    }
                    response.setData(listData);
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/getWithResult")
    public ResponseEntity<Response> getAllBoothgamesResult(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Boothgames");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                String userid = loginService.getLoginSession(sessionToken).getIdUser();
                List<Boothgames> boothgamesList = boothgamesService.getAllBoothgames();
                if (boothgamesList.size() > 0) {
                    response.setMessage("All Boothgames Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for(Boothgames boothgame : boothgamesList) {
                        Object resultData;
                        if(boothgame.getTipegame().equals(Tipegame.SINGLE)){
                            Optional<Singlematch> singlematchDataOptional = singlematchService.getSinglematchesByUserAndBooth(userid, boothgame.getIdBooth());
                            int totalBintang = 0;
                            int totalPoin = 0;
                            if(singlematchDataOptional.isPresent()){
                                totalBintang = singlematchDataOptional.get().getTotalBintang();
                                totalPoin = singlematchDataOptional.get().getTotalPoin();
                            }
                            resultData = Map.of(
                                "totalBintang", totalBintang,
                                "totalPoin", totalPoin
                            );
                        } else {
                            ArrayList<DuelMatch> duelMatches = duelMatchService.getDuelMatchesByUserAndBooth(userid, boothgame.getIdBooth());
                            String match1 = "-";
                            String match2 = "-";
                            if(duelMatches.size() > 0) {
                                match1 = (duelMatches.get(0).getTimMenang().getIdTeam().equals(userid)) ? "Menang" : "Kalah";
                            }
                            if(duelMatches.size() > 1) {
                                match2 = (duelMatches.get(1).getTimMenang().getIdTeam().equals(userid)) ? "Menang" : "Kalah";
                            }
                            resultData = Map.of(
                                "match1", match1,
                                "match2", match2
                            );
                        }

                        listData.add(Map.of(
                            "idBoothGame", boothgame.getIdBooth(),
                            "namaBoothGame", boothgame.getNama(),
                            "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                            "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                            "sopGame", boothgame.getSopGames(),
                            "lokasi", boothgame.getLokasi(),
                            "tipeGame", boothgame.getTipegame().toString(),
                            "durasiPermainan", boothgame.getDurasiPermainan(),
                            "fotoBooth", boothgame.getFotoBooth(),
                            "gameResult", resultData
                        ));
                    }
                    response.setData(listData);
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
                    Boothgames boothgame = boothgameOptional.get();
                    response.setMessage("Boothgame Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "idBoothGame", boothgame.getIdBooth(),
                            "namaBoothGame", boothgame.getNama(),
                            "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                            "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                            "sopGame", boothgame.getSopGames(),
                            "lokasi", boothgame.getLokasi(),
                            "tipeGame", boothgame.getTipegame().toString(),
                            "durasiPermainan", boothgame.getDurasiPermainan(),
                            "fotoBooth", boothgame.getFotoBooth()));
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
                        Boothgames boothgame = updatedBoothgame.get();
                        response.setMessage("Boothgame Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "idBoothGame", boothgame.getIdBooth(),
                                "namaBoothGame", boothgame.getNama(),
                                "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                "sopGame", boothgame.getSopGames(),
                                "lokasi", boothgame.getLokasi(),
                                "tipeGame", boothgame.getTipegame().toString(),
                                "durasiPermainan", boothgame.getDurasiPermainan(),
                                "fotoBooth", boothgame.getFotoBooth())); //update
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}