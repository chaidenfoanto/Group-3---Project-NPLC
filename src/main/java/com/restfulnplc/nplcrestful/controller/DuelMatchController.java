package com.restfulnplc.nplcrestful.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfulnplc.nplcrestful.dto.DuelMatchDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.DuelMatch;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
import com.restfulnplc.nplcrestful.service.DuelMatchService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/duelmatch")
public class DuelMatchController {

    @Autowired
    private DuelMatchService duelMatchService;

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

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
                                "team1", duelMatch.getTeam1().getNama(),
                                "team2", duelMatch.getTeam2().getNama(),
                                "waktuMulai", duelMatch.getWaktuMulai(),
                                "waktuSelesai", duelMatch.getWaktuSelesai(),
                                "inputBy", duelMatch.getInputBy().getNama(),
                                "timMenang", duelMatch.getTimMenang().getNama(),
                                "boothgames", duelMatch.getBoothGames()));
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

    @GetMapping("/getSelfBoothDuel")
    public ResponseEntity<Response> getAllDuelMatchesByUser(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Self Duel Matches");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        ArrayList<DuelMatch> duelMatchList = duelMatchService
                                .getDuelMatchByBooth(boothgame.getIdBooth());
                        ArrayList<Object> listData = new ArrayList<Object>();
                        if (duelMatchList.size() > 0) {
                            response.setMessage("Duel Match History Retrieved");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            for (DuelMatch duelMatch : duelMatchList) {
                                listData.add(Map.of(
                                        "noMatch", duelMatch.getNoMatch(),
                                        "team1", duelMatch.getTeam1().getNama(),
                                        "team2", duelMatch.getTeam2().getNama(),
                                        "waktuMulai", duelMatch.getWaktuMulai(),
                                        "waktuSelesai", duelMatch.getWaktuSelesai(),
                                        "inputBy", duelMatch.getInputBy().getNama(),
                                        "timMenang", duelMatch.getTimMenang().getNama(),
                                        "boothgames", duelMatch.getBoothGames()));
                            }
                        } else {
                            response.setMessage("Duel Match History Empty");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.OK);
                        }
                        response.setData(listData);
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

    @GetMapping("/getByTeam/{id}")
    public ResponseEntity<Response> getDuelMatchByTeamId(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get DuelMatch By Team ID");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                ArrayList<DuelMatch> duelMatchList = duelMatchService.getDuelMatchesByUser(id);
                if (duelMatchList.size() > 0) {
                    response.setMessage("DuelMatch Data Retrieved");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (DuelMatch duelMatch : duelMatchList) {
                        listData.add(Map.of(
                                "noMatch", duelMatch.getNoMatch(),
                                "team1", duelMatch.getTeam1(),
                                "team2", duelMatch.getTeam2(),
                                "waktuMulai", duelMatch.getWaktuMulai(),
                                "waktuSelesai", duelMatch.getWaktuSelesai(),
                                "inputBy", duelMatch.getInputBy(),
                                "timMenang", duelMatch.getTimMenang(),
                                "isWinningTeam", duelMatch.getTimMenang().getIdTeam().equals(id),
                                "boothgames", duelMatch.getBoothGames()));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Duel Matches With That ID Found");
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
                        Optional<DuelMatch> duelMatchOptional = duelMatchService.getCurrentDuelMatch(boothgame);
                        if (duelMatchOptional.isPresent()) {
                            DuelMatch duelMatch = duelMatchOptional.get();
                            response.setMessage("Game Data Retrieved!");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(Map.of(
                                    "gameStatus", duelMatch.getMatchStatus().toString(),
                                    "gameData", Map.of(
                                            "team1", duelMatch.getTeam1(),
                                            "team2", duelMatch.getTeam2(),
                                            "startTime", duelMatch.getWaktuMulai(),
                                            "finishTime", duelMatch.getWaktuSelesai(),
                                            "boothnName", duelMatch.getBoothGames().getNama())));
                        } else {
                            response.setMessage("No Current Game Running");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.OK);
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

    @PostMapping("/start")
    public ResponseEntity<Response> getBoothgamesByPanitia(HttpServletRequest request, DuelMatchDTO duelMatchDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Start Duel Match");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String userid = loginService.getLoginSession(sessionToken).getIdUser();
                    Optional<Boothgames> boothgamesOptional = boothgamesService.getBoothgameByPanitia(userid);
                    if (boothgamesOptional.isPresent()) {
                        Boothgames boothgame = boothgamesOptional.get();
                        DuelMatch duelMatch = duelMatchService.startDuelMatch(duelMatchDTO, boothgame);
                        response.setMessage("Game Started!");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "gameStatus", "Game Started",
                                "gameData", Map.of(
                                        "team1", duelMatch.getTeam1(),
                                        "team2", duelMatch.getTeam2(),
                                        "startTime", duelMatch.getWaktuMulai(),
                                        "finishTime", duelMatch.getWaktuSelesai(),
                                        "boothnName", duelMatch.getBoothGames().getNama())));
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
}
