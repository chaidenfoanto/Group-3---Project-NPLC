package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.TeamDTO;
import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.service.DuelMatchService;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.model.Tipegame;
import com.restfulnplc.nplcrestful.service.TeamService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.SinglematchService;
import com.restfulnplc.nplcrestful.service.BoothgamesService;
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
@RequestMapping(value = "/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SinglematchService singlematchService;

    @Autowired
    private DuelMatchService duelMatchService;

    @Autowired
    private BoothgamesService boothgamesService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addTeam(HttpServletRequest request, @RequestBody TeamDTO teamDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Team");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    if (!teamService.checkUsernameExists(teamDTO.getUsername())) {
                        Team newTeam = teamService.addTeam(teamDTO);
                        response.setMessage("Team Successfully Created");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.CREATED);
                        ArrayList<Object> playerList = new ArrayList<Object>();
                        for(Players player : newTeam.getPlayers()) {
                            playerList.add(Map.of(
                                "idPlayer", player.getIdPlayer(),
                                "nama", player.getNama(),
                                "foto", player.getFoto()
                            ));
                        }
                        response.setData(Map.of(
                                "idTeam", newTeam.getIdTeam(),
                                "namaTeam", newTeam.getNama(),
                                "usernameTeam", newTeam.getUsername(),
                                "asalSekolah", newTeam.getAsalSekolah(),
                                "kategoriTeam", newTeam.getKategoriTeam().toString(),
                                "chanceRoll", newTeam.getChanceRoll(),
                                "totalPoin", newTeam.getTotalPoin(),
                                "players", playerList));
                    } else {
                        response.setMessage("Username Exists");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.CONFLICT);
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
    public ResponseEntity<Response> getAllTeams(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Teams");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Team> teamList = teamService.getAllTeam();
                if (teamList.size() > 0) {
                    response.setMessage("All Teams Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (Team team : teamList) {
                        ArrayList<Object> playerList = new ArrayList<Object>();
                        for(Players player : team.getPlayers()) {
                            playerList.add(Map.of(
                                "idPlayer", player.getIdPlayer(),
                                "nama", player.getNama(),
                                "foto", player.getFoto()
                            ));
                        }
                        listData.add(Map.of(
                                "idTeam", team.getIdTeam(),
                                "namaTeam", team.getNama(),
                                "usernameTeam", team.getUsername(),
                                "asalSekolah", team.getAsalSekolah(),
                                "kategoriTeam", team.getKategoriTeam().toString(),
                                "chanceRoll", team.getChanceRoll(),
                                "totalPoin", team.getTotalPoin(),
                                "players", playerList));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Teams Found");
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

    @GetMapping("/getTeamGeneral")
    public ResponseEntity<Response> getTeamGeneral(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Team General Info");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                String id = loginService.getLoginSession(sessionToken).getIdUser();
                Optional<Team> teamOptional = teamService.getTeamById(id);
                if (teamOptional.isPresent()) {
                    Team team = teamOptional.get();
                    response.setMessage("Team Chane Roll Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "idTeam", team.getIdTeam(),
                            "chanceRoll", team.getChanceRoll(),
                            "totalPoin", team.getTotalPoin()));
                } else {
                    response.setMessage("Team Not Found");
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
    public ResponseEntity<Response> getTeamById(HttpServletRequest request, @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Team By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Team> teamOptional = teamService.getTeamById(id);
                if (teamOptional.isPresent()) {
                    Team team = teamOptional.get();
                    response.setMessage("Team Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> playerList = new ArrayList<Object>();
                        for(Players player : team.getPlayers()) {
                            playerList.add(Map.of(
                                "idPlayer", player.getIdPlayer(),
                                "nama", player.getNama(),
                                "foto", player.getFoto()
                            ));
                        }
                    response.setData(Map.of(
                            "idTeam", team.getIdTeam(),
                            "namaTeam", team.getNama(),
                            "usernameTeam", team.getUsername(),
                            "asalSekolah", team.getAsalSekolah(),
                            "kategoriTeam", team.getKategoriTeam().toString(),
                            "chanceRoll", team.getChanceRoll(),
                            "totalPoin", team.getTotalPoin(),
                            "players", playerList));
                } else {
                    response.setMessage("Team Not Found");
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

    @GetMapping("/getTeamPerGame")
    public ResponseEntity<Response> getTeamPerGame(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Team For Boothgame Data");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionLOGame(sessionToken)) {
                    String id = loginService.getLoginSession(sessionToken).getIdUser();
                    Boothgames boothGame = boothgamesService.getBoothgameByPanitia(id).get();
                    Tipegame tipeGame = boothGame.getTipegame();
                    ArrayList<Object> listData = new ArrayList<>();
                    ArrayList<Team> teamList = new ArrayList<Team>();
                    if (tipeGame.equals(Tipegame.SINGLE)) {
                        teamList = singlematchService.getAvailableTeamPerBooth(boothGame.getIdBooth());
                    } else {
                        teamList = duelMatchService.getAvailableTeamPerBooth(boothGame.getIdBooth());
                    }
                    if (teamList.size() > 0) {
                        for (Team team : teamList) {
                            listData.add(Map.of(
                                    "idTeam", team.getIdTeam(),
                                    "namaTeam", team.getNama(),
                                    "usernameTeam", team.getUsername(),
                                    "asalSekolah", team.getAsalSekolah(),
                                    "chanceRoll", team.getChanceRoll(),
                                    "totalPoin", team.getTotalPoin()));
                        }
                        response.setMessage("Team Datas Retrieved Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(listData);
                    } else {
                        response.setMessage("All Teams Have Played!");
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