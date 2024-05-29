package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.TeamDTO;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.service.TeamService;
import com.restfulnplc.nplcrestful.util.Response;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/team")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    @PostMapping("addTeam")
    public ResponseEntity<Response<Team>> addTeam(@RequestBody TeamDTO teamDTO) {
        Response<Team> response = new Response<Team>();
        response.setService("Team Creation");
        Team newTeam = teamService.addTeam(teamDTO);
        response.setMessage("Team Successfully Created");
        response.setData(newTeam);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }
    
}
