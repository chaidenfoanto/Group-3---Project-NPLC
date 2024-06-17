package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.ListKartuDTO;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.service.ListKartuService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.TeamService;
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
@RequestMapping(value = "/api/listkartu")
public class ListKartuController {

    @Autowired
    private ListKartuService listKartuService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addListKartu(HttpServletRequest request,
            @RequestBody ListKartuDTO listKartuDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add List Kartu");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    ListKartu newListKartu = listKartuService.addListKartu(listKartuDTO);
                    response.setMessage("List Kartu Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "noKartu", newListKartu.getNoKartu(),
                            "cardSkill", newListKartu.getCardSkill(),
                            "ownedBy", newListKartu.getOwnedBy(),
                            "isUsed", newListKartu.getIsUsed()));
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
    public ResponseEntity<Response> getAllListKartu(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All List Kartu");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<ListKartu> listKartuList = listKartuService.getAllListKartu();
                if (listKartuList.size() > 0) {
                    response.setMessage("All List Kartu Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for (ListKartu listKartu : listKartuList) {
                        listData.add(Map.of(
                                "noKartu", listKartu.getNoKartu(),
                                "cardSkill", listKartu.getCardSkill(),
                                "ownedBy", listKartu.getOwnedBy(),
                                "isUsed", listKartu.getIsUsed()));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No List Kartu Found");
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
    public ResponseEntity<Response> getListKartuById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get List Kartu By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<ListKartu> listKartuOptional = listKartuService.getListKartuById(id);
                if (listKartuOptional.isPresent()) {
                    ListKartu listKartu = listKartuOptional.get();
                    response.setMessage("List Kartu Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "noKartu", listKartu.getNoKartu(),
                            "cardSkill", listKartu.getCardSkill(),
                            "ownedBy", listKartu.getOwnedBy(),
                            "isUsed", listKartu.getIsUsed()));
                } else {
                    response.setMessage("List Kartu Not Found");
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

    @GetMapping("/cardStat")
    public ResponseEntity<Response> getListKartuStat(HttpServletRequest request) {
        response.setService("Card Stats");
        try {
            int totalCards = listKartuService.getAllListKartu().size();
            int availableCards = listKartuService.getAvailableCard().size();
            int usedCards = listKartuService.getUsedCards().size();
            response.setMessage("Card Stats Retrieved Successfully");
            response.setError(false);
            response.setHttpCode(HTTPCode.OK);
            response.setData(Map.of(
                    "cardTotal", totalCards,
                    "cardUsed", usedCards,
                    "cardAvailable", availableCards,
                    "cardTaken", totalCards - availableCards));
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

    @GetMapping("/roll")
    public ResponseEntity<Response> getListKartuRoll(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Roll Card");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionTeam(sessionToken)) {
                    String idUser = loginService.getLoginSession(sessionToken).getIdUser();
                    Team team = teamService.getTeamById(idUser).get();
                    if (team.getChanceRoll() > 0) {
                        Optional<ListKartu> listKartuOptional = listKartuService.teamGetCard(idUser);
                        if (listKartuOptional.isPresent()) {
                            ListKartu listKartu = listKartuOptional.get();
                            response.setMessage("List Kartu Retrieved Successfully");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(Map.of(
                                    "noKartu", listKartu.getNoKartu(),
                                    "cardSkill", listKartu.getCardSkill()));
                        } else {
                            response.setMessage("All Cards Are Taken!");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("Chance Roll Insufficient");
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

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateListKartu(HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody ListKartuDTO listKartuDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update List Kartu");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<ListKartu> updatedKartu = listKartuService.getListKartuById(id);
                    if (updatedKartu.isPresent()) {
                        ListKartu listKartu = listKartuService.updateListKartu(id, listKartuDTO);
                        response.setMessage("List Kartu Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "noKartu", listKartu.getNoKartu(),
                                "cardSkill", listKartu.getCardSkill(),
                                "ownedBy", listKartu.getOwnedBy(),
                                "isUsed", listKartu.getIsUsed()));
                    } else {
                        response.setMessage("List Kartu Not Found");
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteListKartu(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete List Kartu");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = listKartuService.deleteListKartu(id);
                    if (isDeleted) {
                        response.setMessage("List Kartu Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("List Kartu Not Found");
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
