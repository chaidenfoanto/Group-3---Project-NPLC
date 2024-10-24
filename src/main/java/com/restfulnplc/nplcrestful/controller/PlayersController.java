package com.restfulnplc.nplcrestful.controller;

import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.PlayersService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/players")
public class PlayersController {

    @Autowired
    private PlayersService playersService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @GetMapping("/getByTeam/{idTeam}")
    public ResponseEntity<Response> getPlayersByTeam(HttpServletRequest request,
            @PathVariable("idTeam") String idTeam) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Players By Team ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Players> playersList = playersService.getPlayersByTeam(idTeam);
                if (playersList.size() > 0) {
                    response.setMessage("Player List Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(playersList);
                } else {
                    response.setMessage("No Players Found");
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
}