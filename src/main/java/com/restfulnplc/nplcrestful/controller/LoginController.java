package com.restfulnplc.nplcrestful.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.dto.AccessDTO;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.service.TeamService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import com.restfulnplc.nplcrestful.model.Login;
import com.restfulnplc.nplcrestful.model.Role;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PanitiaService panitiaService;

    private Response response = new Response();

    @PostMapping("/process_login_panitia")
    public ResponseEntity<Response> process_login_panitia(@RequestBody LoginDTO loginDTO) {
        response.setService("Login Panitia");
        try {
            Optional<Login> sessionOptional = loginService.LoginPanitia(loginDTO);
            if (sessionOptional.isPresent()) {
                Login sessionData = sessionOptional.get();
                response.setMessage("Login Success");
                response.setData(Map.of(
                    "userId", sessionData.getIdUser(),
                    "token", sessionData.getToken(),
                    "role", sessionData.getRole().toString()
                ));
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
            } else {
                response.setMessage("Login Failed. User or Password Invalid");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
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

    @PostMapping("/process_login_players")
    public ResponseEntity<Response> process_login_player(@RequestBody LoginDTO loginDTO) {
        response.setService("Login Player");
        try {
            Optional<Login> sessionOptional = loginService.LoginPlayer(loginDTO);
            if (sessionOptional.isPresent()) {
                Login sessionData = sessionOptional.get();
                response.setMessage("Login Success");
                response.setData(Map.of(
                    "userId", sessionData.getIdUser(),
                    "token", sessionData.getToken(),
                    "role", sessionData.getRole().toString()
                ));
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
            } else {
                response.setMessage("Login Failed. User or Password Invalid");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
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

    @GetMapping("/getSession")
    public ResponseEntity<Response> getSession(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Auth Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Login sessionActive = loginService.getLoginSession(sessionToken);
                response.setMessage("Authorization Success");
                String namaUser;
                if(sessionActive.getRole().equals(Role.PLAYERS)){
                    namaUser = teamService.getTeamById(sessionActive.getIdUser()).get().getNama();
                } else {
                    namaUser = panitiaService.getPanitiaById(sessionActive.getIdUser()).get().getNama();
                }
                response.setData(Map.of(
                    "userId", sessionActive.getIdUser(),
                    "token", sessionActive.getToken(),
                    "role", sessionActive.getRole().toString(),
                    "namaUser", namaUser
                ));
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
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

    @GetMapping("/getAccess")
    public ResponseEntity<Response> getAccess(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Auth Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                AccessDTO accessDetails = loginService.getAccessDetails(sessionToken);
                response.setMessage("Authorization Success");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(accessDetails);
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
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

    @GetMapping("/check/{role}")
    public ResponseEntity<Response> getAccess(HttpServletRequest request, @PathVariable("role") String role) {
        String sessionToken = request.getHeader("Token");
        response.setService("Checking Access For " + role.toUpperCase());
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                boolean access = false;
                switch(role.toLowerCase()) {
                    case "admin":
                        access = loginService.checkSessionAdmin(sessionToken);
                        break;
                    case "penjaga single":
                        access = loginService.checkSessionLOSingle(sessionToken);
                        break;
                    case "penjaga duel":
                        access = loginService.checkSessionLODuel(sessionToken);
                        break;
                    case "player":
                        access = loginService.checkSessionTeam(sessionToken);
                        break;
                    case "ketua":
                        access = loginService.checkSessionKetua(sessionToken);
                        break;
                    default:
                        response.setError(true);
                }
                response.setMessage(response.isError() ? "Role Unrecognized" : (access ? "Access Granted" : "Access Denied"));
                response.setData(Map.of(
                    "accessGranted", access
                ));
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
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
