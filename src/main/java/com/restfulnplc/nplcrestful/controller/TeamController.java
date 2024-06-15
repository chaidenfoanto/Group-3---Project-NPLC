package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.TeamDTO;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.service.TeamService;
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
@RequestMapping(value = "/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private LoginService loginService;

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
                        response.setData(Map.of(
                                "idTeam", newTeam.getIdTeam(),
                                "namaTeam", newTeam.getNama(),
                                "usernameTeam", newTeam.getUsername(),
                                "asalSekolah", newTeam.getAsalSekolah(),
                                "kategoriTeam", newTeam.getKategoriTeam().toString(),
                                "chanceRoll", newTeam.getChanceRoll(),
                                "totalPoin", newTeam.getTotalPoin(),
                                "players", newTeam.getPlayers()));
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
                        listData.add(Map.of(
                                "idTeam", team.getIdTeam(),
                                "namaTeam", team.getNama(),
                                "usernameTeam", team.getUsername(),
                                "asalSekolah", team.getAsalSekolah(),
                                "kategoriTeam", team.getKategoriTeam().toString(),
                                "chanceRoll", team.getChanceRoll(),
                                "totalPoin", team.getTotalPoin(),
                                "players", team.getPlayers()));
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
                    response.setData(Map.of(
                            "idTeam", team.getIdTeam(),
                            "namaTeam", team.getNama(),
                            "usernameTeam", team.getUsername(),
                            "asalSekolah", team.getAsalSekolah(),
                            "kategoriTeam", team.getKategoriTeam().toString(),
                            "chanceRoll", team.getChanceRoll(),
                            "totalPoin", team.getTotalPoin(),
                            "players", team.getPlayers()));
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

//     @PutMapping("/{id}")
//     public ResponseEntity<Response> updateTeam(HttpServletRequest request,
//             @PathVariable("id") String id, @RequestBody TeamDTO teamDTO) {
//         String sessionToken = request.getHeader("Token");
//         response.setService("Update Team");
//         try {
//             if (loginService.checkSessionPanitia(sessionToken)) {
//                 if (loginService.checkSessionAdmin(sessionToken)) {
//                     Optional<Team> updatedTeam = teamService.updateTeam(id, teamDTO);
//                     if (updatedTeam.isPresent()) {
//                         Team team = updatedTeam.get();
//                         response.setMessage("Team Updated Successfully");
//                         response.setError(false);
//                         response.setHttpCode(HTTPCode.OK);
//                         response.setData(Map.of(
//                                 "idTeam", team.getIdTeam(),
//                                 "namaTeam", team.getNama(),
//                                 "usernameTeam", team.getUsername(),
//                                 "asalSekolah", team.getAsalSekolah(),
//                                 "kategoriTeam", team.getKategoriTeam().toString(),
//                                 "chanceRoll", team.getChanceRoll(),
//                                 "totalPoin", team.getTotalPoin(),
//                                 "players", team.getPlayers()));
//                     } else {
//                         response.setMessage("Team Not Found");
//                         response.setError(true);
//                         response.setHttpCode(HTTPCode.OK);
//                         response.setData(new ErrorMessage(response.getHttpCode()));
//                     }
//                 } else {
//                     response.setMessage("Access Denied");
//                     response.setError(true);
//                     response.setHttpCode(HTTPCode.FORBIDDEN);
//                     response.setData(new ErrorMessage(response.getHttpCode()));
//                 }
//             } else {
//                 response.setMessage("Authorization Failed");
//                 response.setError(true);
//                 response.setHttpCode(HTTPCode.BAD_REQUEST);
//                 response.setData(new ErrorMessage(response.getHttpCode()));
//             }
//         } catch (Exception e) {
//             response.setMessage(e.getMessage());
//             response.setError(true);
//             response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Response> deleteTeam(HttpServletRequest request, @PathVariable("id") String id) {
//         String sessionToken = request.getHeader("Token");
//         response.setService("Delete Team");
//         try {
//             if (loginService.checkSessionAlive(sessionToken)) {
//                 if (loginService.checkSessionAdmin(sessionToken)) {
//                     boolean isDeleted = teamService.deleteTeam(id);
//                     if (isDeleted) {
//                         response.setMessage("Team Deleted Successfully");
//                         response.setError(false);
//                         response.setHttpCode(HTTPCode.OK);
//                     } else {
//                         response.setMessage("Team Not Found");
//                         response.setError(true);
//                         response.setHttpCode(HTTPCode.OK);
//                         response.setData(new ErrorMessage(response.getHttpCode()));
//                     }
//                 } else {
//                     response.setMessage("Access Denied");
//                     response.setError(true);
//                     response.setHttpCode(HTTPCode.FORBIDDEN);
//                     response.setData(new ErrorMessage(response.getHttpCode()));
//                 }

//             } else {
//                 response.setMessage("Authorization Failed");
//                 response.setError(true);
//                 response.setHttpCode(HTTPCode.BAD_REQUEST);
//                 response.setData(new ErrorMessage(response.getHttpCode()));
//             }
//         } catch (Exception e) {
//             response.setMessage(e.getMessage());
//             response.setError(true);
//             response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }
}