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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/addTeam")
    public ResponseEntity<Response<Team>> addTeam(@RequestBody TeamDTO teamDTO) {
        Response<Team> response = new Response<>();
        response.setService("Add Team");
        Team newTeam = teamService.addTeam(teamDTO);
        response.setMessage("Team Successfully Added");
        response.setData(newTeam);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Team>>> getAllTeams() {
        Response<List<Team>> response = new Response<>();
        response.setService("Get All Teams");
        List<Team> teamList = teamService.getAllTeams();
        response.setData(teamList);
        response.setMessage("All Teams Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Team>> getTeamById(@PathVariable("id") String id) {
        Response<Team> response = new Response<>();
        response.setService("Get Team By ID");
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isPresent()) {
            response.setData(team.get());
            response.setMessage("Team Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Team Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Team>> updateTeam(@PathVariable("id") String id, @RequestBody TeamDTO teamDTO) {
        Response<Team> response = new Response<>();
        response.setService("Update Team");
        Optional<Team> updatedTeam = teamService.updateTeam(id, teamDTO);
        if (updatedTeam.isPresent()) {
            response.setData(updatedTeam.get());
            response.setMessage("Team Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Team Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteTeam(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete Team");
        boolean isDeleted = teamService.deleteTeam(id);
        if (isDeleted) {
            response.setMessage("Team Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Team Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}