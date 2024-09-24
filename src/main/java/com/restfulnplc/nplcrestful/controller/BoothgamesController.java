package com.restfulnplc.nplcrestful.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.model.Singlematch;
import com.restfulnplc.nplcrestful.model.Tipegame;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
import com.restfulnplc.nplcrestful.service.DuelMatchService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.SinglematchService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

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
                    Optional<Boothgames> newBoothgameOptional = boothgamesService.addBoothgame(boothgamesDTO);
                    if (newBoothgameOptional.isPresent()) {
                        Boothgames newBoothgame = newBoothgameOptional.get();
                        Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(newBoothgame.getDurasiPermainan());
                        response.setMessage("Boothgame Successfully Added");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.CREATED);
                        if (newBoothgame.getIdPenjaga2() != null) {
                            response.setData(Map.of(
                                    "idBoothGame", newBoothgame.getIdBooth(),
                                    "namaBoothGame", newBoothgame.getNama(),
                                    "panitia1", newBoothgame.getIdPenjaga1().getIdPanitia(),
                                    "panitia2", newBoothgame.getIdPenjaga2().getIdPanitia(),
                                    "sopGame", newBoothgame.getSopGames(),
                                    "lokasi", newBoothgame.getLokasi(),
                                    "tipeGame", newBoothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", newBoothgame.getFotoBooth()));
                        } else {
                            response.setData(Map.of(
                                    "idBoothGame", newBoothgame.getIdBooth(),
                                    "namaBoothGame", newBoothgame.getNama(),
                                    "panitia1", newBoothgame.getIdPenjaga1().getIdPanitia(),
                                    "sopGame", newBoothgame.getSopGames(),
                                    "lokasi", newBoothgame.getLokasi(),
                                    "tipeGame", newBoothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", newBoothgame.getFotoBooth()));
                        }
                    } else {
                        response.setMessage("Boothgame Add Failed");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.BAD_REQUEST);
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
                    for (Boothgames boothgame : boothgamesList) {
                        Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                        if (boothgame.getIdPenjaga2() != null) {
                            listData.add(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                    "sopGame", boothgame.getSopGames(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", boothgame.getFotoBooth()));
                        } else {
                            listData.add(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "sopGame", boothgame.getSopGames(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", boothgame.getFotoBooth()));
                        }
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

    @GetMapping("/getGeneral")
    public ResponseEntity<Response> getAllBoothgamesGeneral(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Boothgames General Data");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Boothgames> boothgamesList = boothgamesService.getAllBoothgames();
                if (boothgamesList.size() > 0) {
                    response.setMessage("All Boothgames General Data Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for (Boothgames boothgame : boothgamesList) {
                        Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                        if (boothgame.getIdPenjaga2() != null) {
                            listData.add(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60))
                                            ));
                        } else {
                            listData.add(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60))
                                            ));
                        }
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

    @PostMapping("/searchBoothgame")
    public ResponseEntity<Response> searchBoothgameData(HttpServletRequest request,
            @RequestBody Map<String, String> body) {
        String nama = (String) body.get("nama");
        String lantai = (String) body.get("lantai");
        String tipegame = (String) body.get("tipegame");
        String sessionToken = request.getHeader("Token");
        response.setService("Search Boothgame Data");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                ArrayList<Boothgames> boothgamesList = boothgamesService.searchBoothgame(nama, lantai, tipegame);
                if (boothgamesList.size() > 0) {
                    response.setMessage("Boothgame Search Results Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for (Boothgames boothgame : boothgamesList) {
                        Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                        if (boothgame.getIdPenjaga2() != null) {
                            listData.add(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60))
                                            ));
                        } else {
                            listData.add(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60))
                                            ));
                        }
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
                if (loginService.checkSessionTeam(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    List<Boothgames> boothgamesList = boothgamesService.getAllBoothgames();
                    if (boothgamesList.size() > 0) {
                        response.setMessage("All Boothgames Retrieved Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        ArrayList<Object> listData = new ArrayList<Object>();
                        for (Boothgames boothgame : boothgamesList) {
                            Object resultData;
                            if (boothgame.getTipegame().equals(Tipegame.SINGLE)) {
                                Optional<Singlematch> singlematchDataOptional = singlematchService
                                        .getSinglematchesByUserAndBooth(userid, boothgame.getIdBooth());
                                int totalBintang = 0;
                                int totalPoin = 0;
                                if (singlematchDataOptional.isPresent()) {
                                    totalBintang = singlematchDataOptional.get().getTotalBintang();
                                    totalPoin = singlematchDataOptional.get().getTotalPoin();
                                }
                                resultData = Map.of(
                                        "totalBintang", totalBintang,
                                        "totalPoin", totalPoin);
                            } else {
                                ArrayList<DuelMatch> duelMatches = duelMatchService.getDuelMatchesByUserAndBooth(userid,
                                        boothgame.getIdBooth());
                                String match1 = "-";
                                String match2 = "-";
                                if (duelMatches.size() > 0) {
                                    match1 = (duelMatches.get(0).getTimMenang().getIdTeam().equals(userid)) ? "Menang"
                                            : "Kalah";
                                }
                                if (duelMatches.size() > 1) {
                                    match2 = (duelMatches.get(1).getTimMenang().getIdTeam().equals(userid)) ? "Menang"
                                            : "Kalah";
                                }
                                resultData = Map.of(
                                        "match1", match1,
                                        "match2", match2);
                            }

                            Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                            if (boothgame.getIdPenjaga2() != null) {
                                listData.add(Map.of(
                                        "idBoothGame", boothgame.getIdBooth(),
                                        "namaBoothGame", boothgame.getNama(),
                                        "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                        "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                        "sopGame", boothgame.getSopGames(),
                                        "lokasi", boothgame.getLokasi(),
                                        "tipeGame", boothgame.getTipegame().toString(),
                                        "durasiPermainan", Map.of(
                                                "menit", (durasiDetik / 60),
                                                "detik", (durasiDetik % 60)),
                                        "fotoBooth", boothgame.getFotoBooth(),
                                        "gameResult", resultData));
                            } else {
                                listData.add(Map.of(
                                        "idBoothGame", boothgame.getIdBooth(),
                                        "namaBoothGame", boothgame.getNama(),
                                        "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                        "sopGame", boothgame.getSopGames(),
                                        "lokasi", boothgame.getLokasi(),
                                        "tipeGame", boothgame.getTipegame().toString(),
                                        "durasiPermainan", Map.of(
                                                "menit", (durasiDetik / 60),
                                                "detik", (durasiDetik % 60)),
                                        "fotoBooth", boothgame.getFotoBooth(),
                                        "gameResult", resultData));
                            }
                        }
                        response.setData(listData);
                    } else {
                        response.setMessage("Access Denied");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.FORBIDDEN);
                        response.setData(new ErrorMessage(response.getHttpCode()));
                    }
                } else {
                    response.setMessage("Boothgames Data Empty");
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

    @GetMapping("/getAvailableDatas")
    public ResponseEntity<Response> getAvailableDatas(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Boothgame Available Datas for Input");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    response.setMessage("Data Successfully Retrieved");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(boothgamesService.getAvailableDatas());
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

    @GetMapping("/getById/{id}")
    public ResponseEntity<Response> getBoothgameById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Boothgame By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Boothgames> boothgameOptional = boothgamesService.getBoothgameById(id);
                if (boothgameOptional.isPresent()) {
                    Boothgames boothgame = boothgameOptional.get();
                    Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                    response.setMessage("Boothgame Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    if (boothgame.getIdPenjaga2() != null) {
                        response.setData(Map.of(
                                "idBoothGame", boothgame.getIdBooth(),
                                "namaBoothGame", boothgame.getNama(),
                                "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                "sopGame", boothgame.getSopGames(),
                                "lokasi", boothgame.getLokasi(),
                                "tipeGame", boothgame.getTipegame().toString(),
                                "durasiPermainan", Map.of(
                                        "menit", (durasiDetik / 60),
                                        "detik", (durasiDetik % 60)),
                                "fotoBooth", boothgame.getFotoBooth()));
                    } else {
                        response.setData(Map.of(
                                "idBoothGame", boothgame.getIdBooth(),
                                "namaBoothGame", boothgame.getNama(),
                                "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                "sopGame", boothgame.getSopGames(),
                                "lokasi", boothgame.getLokasi(),
                                "tipeGame", boothgame.getTipegame().toString(),
                                "durasiPermainan", Map.of(
                                        "menit", (durasiDetik / 60),
                                        "detik", (durasiDetik % 60)),
                                "fotoBooth", boothgame.getFotoBooth()));
                    }
                } else {
                    response.setMessage("Boothgame With That ID Not Found");
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

    @GetMapping("/getSelfBooth")
    public ResponseEntity<Response> getBoothgamesByPanitia(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Boothgames By Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                        response.setMessage("Boothgame Retrieved Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        if (boothgame.getIdPenjaga2() != null) {
                            response.setData(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getNama(),
                                    "panitia2", boothgame.getIdPenjaga2().getNama(),
                                    "sopGame", boothgame.getSopGames(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", boothgame.getFotoBooth()));
                        } else {
                            response.setData(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getNama(),
                                    "sopGame", boothgame.getSopGames(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", boothgame.getFotoBooth()));
                        }
                    } else {
                        response.setMessage("BoothGame Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.FORBIDDEN);
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
                        Long durasiDetik = TimeUnit.MILLISECONDS.toSeconds(boothgame.getDurasiPermainan());
                        response.setMessage("Boothgame Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        if (boothgame.getIdPenjaga2() != null) {
                            response.setData(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "panitia2", boothgame.getIdPenjaga2().getIdPanitia(),
                                    "sopGame", boothgame.getSopGames(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", boothgame.getFotoBooth()));
                        } else {
                            response.setData(Map.of(
                                    "idBoothGame", boothgame.getIdBooth(),
                                    "namaBoothGame", boothgame.getNama(),
                                    "panitia1", boothgame.getIdPenjaga1().getIdPanitia(),
                                    "sopGame", boothgame.getSopGames(),
                                    "lokasi", boothgame.getLokasi(),
                                    "tipeGame", boothgame.getTipegame().toString(),
                                    "durasiPermainan", Map.of(
                                            "menit", (durasiDetik / 60),
                                            "detik", (durasiDetik % 60)),
                                    "fotoBooth", boothgame.getFotoBooth()));
                        }
                    } else {
                        response.setMessage("Boothgame Not Found or Invalid Data");
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

    @PutMapping("/updateSOP")
    public ResponseEntity<Response> updateSOP(HttpServletRequest request,
            @RequestBody BoothgamesDTO boothgamesDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update SOP Boothgame");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String id = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgameOptional = boothgamesService.getBoothgameByPanitia(id);
                    if (boothgameOptional.isPresent()) {
                        String idBooth = boothgameOptional.get().getIdBooth();
                        Boothgames boothgame = boothgamesService.updateSOP(idBooth, boothgamesDTO).get();
                        response.setMessage("SOP Boothgame Berhasil Diupdate");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "idBoothGame", boothgame.getIdBooth(),
                                "sopGame", boothgame.getSopGames()));
                    } else {
                        response.setMessage("Boothgame Tidak Ditemukan");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.NOT_FOUND);
                        response.setData(new ErrorMessage(response.getHttpCode()));
                    }
                } else {
                    response.setMessage("Akses Ditolak");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.FORBIDDEN);
                    response.setData(new ErrorMessage(response.getHttpCode()));
                }
            } else {
                response.setMessage("Autorisasi Gagal");
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