package com.restfulnplc.nplcrestful.controller;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.Singlematch;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.service.SinglematchService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/singlematch")
public class SinglematchController {

    @Autowired
    private SinglematchService singlematchService;

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

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

    @GetMapping("/getCurrentGame")
    public ResponseEntity<Response> getCurrentGame(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Current Game Status");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        Optional<Singlematch> singlematchOptional = singlematchService.getCurrentSingleMatch(boothgame);
                        if (singlematchOptional.isPresent()) {
                            Singlematch singlematch = singlematchOptional.get();
                            Long durationSecond = singlematch.getWaktuMulai().until(singlematch.getWaktuSelesai(),
                                    ChronoUnit.SECONDS);
                            Long sisaWaktuSecond = LocalTime.now().until(singlematch.getWaktuSelesai(),
                                    ChronoUnit.SECONDS);
                            if (sisaWaktuSecond <= 0) {
                                singlematch = singlematchService.stopSingleMatch(singlematch);
                                sisaWaktuSecond = (long) 0;
                            }
                            response.setMessage("Game Data Retrieved!");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(Map.of(
                                    "gameStatus", singlematch.getMatchStatus().toString(),
                                    "gameData", Map.of(
                                            "cardUsed", (singlematch.getListKartu() != null) ? (Map.of(
                                                    "cardName",
                                                    singlematch.getListKartu().getCardSkill().getNamaKartu(),
                                                    "cardNumber", singlematch.getListKartu().getNoKartu(),
                                                    "cardId", singlematch.getListKartu().getCardSkill().getIdCard()))
                                                    : "None",
                                            "team", Map.of(
                                                    "namaTeam", singlematch.getTeam().getNama(),
                                                    "idTeam", singlematch.getTeam().getIdTeam()),
                                            "startTime", singlematch.getWaktuMulai(),
                                            "finishTime", singlematch.getWaktuSelesai(),
                                            "durasi", Map.of(
                                                    "detik", durationSecond % 60,
                                                    "menit", durationSecond / 60),
                                            "sisaWaktu", Map.of(
                                                    "detik", sisaWaktuSecond % 60,
                                                    "menit", sisaWaktuSecond / 60),
                                            "boothName", singlematch.getBoothGames().getNama())));
                        } else {
                            response.setMessage("No Current Game Running");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.BAD_REQUEST);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("BoothGame Not Found");
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

    @PostMapping("/start")
    public ResponseEntity<Response> startGame(HttpServletRequest request, @RequestBody SinglematchDTO singlematchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Start Single Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        if (!singlematchService.getGameRunning(boothgame).isPresent()) {
                            Optional<Singlematch> singlematchOptional = singlematchService.startSingleMatch(
                                    singlematchDTO, boothgame,
                                    userid);
                            if (singlematchOptional.isPresent()) {
                                Singlematch singlematch = singlematchOptional.get();
                                Long durationSecond = singlematch.getWaktuMulai().until(singlematch.getWaktuSelesai(),
                                        ChronoUnit.SECONDS);
                                response.setMessage("Game Started!");
                                response.setError(false);
                                response.setHttpCode(HTTPCode.OK);
                                response.setData(Map.of(
                                        "gameStatus", "Game Started",
                                        "gameData", Map.of(
                                                "team", Map.of(
                                                        "namaTeam", singlematch.getTeam().getNama(),
                                                        "idTeam", singlematch.getTeam().getIdTeam()),
                                                "cardUsed", (singlematch.getListKartu() != null) ? (Map.of(
                                                        "cardName",
                                                        singlematch.getListKartu().getCardSkill().getNamaKartu(),
                                                        "cardNumber", singlematch.getListKartu().getNoKartu(),
                                                        "cardId",
                                                        singlematch.getListKartu().getCardSkill().getIdCard()))
                                                        : "None",
                                                "startTime", singlematch.getWaktuMulai(),
                                                "finishTime", singlematch.getWaktuSelesai(),
                                                "durasi", Map.of(
                                                        "detik", durationSecond % 60,
                                                        "menit", durationSecond / 60),
                                                "boothName", singlematch.getBoothGames().getNama())));
                            } else {
                                response.setMessage("Invalid Team!");
                                response.setError(true);
                                response.setHttpCode(HTTPCode.BAD_REQUEST);
                                response.setData(new ErrorMessage(response.getHttpCode()));
                            }
                        } else {
                            response.setMessage("Game Started!");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.BAD_REQUEST);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("BoothGame Not Found");
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

    @PostMapping("/finish")
    public ResponseEntity<Response> finishGame(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Stop Duel Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        Optional<Singlematch> currentSinglematch = singlematchService.getCurrentSingleMatch(boothgame);
                        if (currentSinglematch.isPresent()) {
                            Singlematch singlematch = singlematchService.stopSingleMatch(currentSinglematch.get());
                            response.setMessage("Game Stopped!");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            Long durationSecond = singlematch.getWaktuMulai().until(singlematch.getWaktuSelesai(),
                                    ChronoUnit.SECONDS);
                            response.setData(Map.of(
                                    "gameStatus", "Game Stopped",
                                    "gameData", Map.of(
                                            "team", Map.of(
                                                    "namaTeam", singlematch.getTeam().getNama(),
                                                    "idTeam", singlematch.getTeam().getIdTeam()),
                                            "cardUsed", (singlematch.getListKartu() != null) ? (Map.of(
                                                    "cardName",
                                                    singlematch.getListKartu().getCardSkill().getNamaKartu(),
                                                    "cardNumber", singlematch.getListKartu().getNoKartu(),
                                                    "cardId", singlematch.getListKartu().getCardSkill().getIdCard()))
                                                    : "None",
                                            "startTime", singlematch.getWaktuMulai(),
                                            "finishTime", singlematch.getWaktuSelesai(),
                                            "durasi", Map.of(
                                                    "detik", durationSecond % 60,
                                                    "menit", durationSecond / 60),
                                            "boothName", singlematch.getBoothGames().getNama())));
                        } else {
                            response.setMessage("Match Not Found");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.BAD_REQUEST);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("BoothGame Not Found");
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

    @PostMapping("/submit")
    public ResponseEntity<Response> submitGame(HttpServletRequest request, @RequestBody SinglematchDTO singlematchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Submit Single Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        Optional<Singlematch> currentSinglematch = singlematchService.getCurrentSingleMatch(boothgame);
                        if (currentSinglematch.isPresent()) {
                            Singlematch singlematch = singlematchService.submitSingleMatch(currentSinglematch.get(),
                                    singlematchDTO.getTotalBintang(), panitiaService.getPanitiaById(userid).get());
                            Long durationSecond = singlematch.getWaktuMulai().until(singlematch.getWaktuSelesai(),
                                    ChronoUnit.SECONDS);
                            response.setMessage("Game Submitted!");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(Map.of(
                                    "gameStatus", "Game Submitted",
                                    "gameData", Map.of(
                                            "cardUsed", (singlematch.getListKartu() != null) ? (Map.of(
                                                    "cardName",
                                                    singlematch.getListKartu().getCardSkill().getNamaKartu(),
                                                    "cardNumber", singlematch.getListKartu().getNoKartu(),
                                                    "cardId", singlematch.getListKartu().getCardSkill().getIdCard()))
                                                    : "None",
                                            "noMatch", singlematch.getNoMatch(),
                                            "team", Map.of(
                                                    "namaTeam", singlematch.getTeam().getNama(),
                                                    "idTeam", singlematch.getTeam().getIdTeam()),
                                            "durasi", Map.of(
                                                    "detik", durationSecond % 60,
                                                    "menit", durationSecond / 60),
                                            "waktuMulai", singlematch.getWaktuMulai(),
                                            "waktuSelesai", singlematch.getWaktuSelesai(),
                                            "inputBy", singlematch.getInputBy().getNama(),
                                            "totalBintang", singlematch.getTotalBintang(),
                                            "totalPoin", singlematch.getTotalPoin(),
                                            "boothgames", singlematch.getBoothGames().getNama())));
                        } else {
                            response.setMessage("Match Not Found");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.BAD_REQUEST);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("BoothGame Not Found");
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

    @GetMapping("/getHistory")
    public ResponseEntity<Response> getHistory(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Game History");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        response.setMessage("Game History Retrieved!");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(singlematchService.getBoothHistory(boothgame.getIdBooth()));
                    } else {
                        response.setMessage("BoothGame Not Found");
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
}
