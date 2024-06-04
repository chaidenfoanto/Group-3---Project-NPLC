package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.TeamDTO;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.service.TeamService;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/team")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private Response response;

    List<Team> listTeam = Collections.<Team>emptyList();

    @PostMapping("addTeam")
    public ResponseEntity<Response> addTeam(@RequestBody TeamDTO teamDTO) {
        response.setService("Team Creation");
        Team newTeam = teamService.addTeam(teamDTO);
        listTeam.add(newTeam);
        response.setMessage("Team Successfully Created");
        response.setError(false);
        response.setHttpCode(HTTPCode.OK);
        response.setData(listTeam.stream().map(team -> Map.of(
                "idTeam", team.getIdTeam(),
                "namaTeam", team.getNama(),
                "usernameTeam", team.getUsername(),
                "asalSekolah", team.getAsalSekolah(),
                "kategoriTeam", team.getKategori().toString(),
                "chanceRoll", team.getChanceRoll(),
                "totalPoin", team.getTotalPoin(),
                "players", team.getPlayers()
            )).collect(Collectors.toList()));
        return ResponseEntity
            .status(response.getHttpCode().getStatus())
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }
    
}
