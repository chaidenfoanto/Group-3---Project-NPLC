package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.CardSkillDTO;
import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.service.CardSkillService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/cardskills")
public class CardSkillController {

    @Autowired
    private CardSkillService cardSkillService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<CardSkill> listCardSkills = Collections.<CardSkill>emptyList();

    @PostMapping
    public ResponseEntity<Response> addCardSkill(HttpServletRequest request,
            @RequestBody CardSkillDTO cardSkillDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Card Skill");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    CardSkill newCardSkill = cardSkillService.addCardSkill(cardSkillDTO);
                    listCardSkills.add(newCardSkill);
                    response.setMessage("Card Skill Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listCardSkills.stream().map(cardSkill -> Map.of(
                            "idCard", cardSkill.getIdCard(),
                            "namaKartu", cardSkill.getNamaKartu(),
                            "rules", cardSkill.getRules(),
                            "totalKartu", cardSkill.getTotalKartu(),
                            "gambarKartu", cardSkill.getGambarKartu())).collect(Collectors.toList()));
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
        listCardSkills.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllCardSkills(HttpServletRequest request) {
        response.setService("Get All CardSkills");
        String sessionToken = request.getHeader("Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<CardSkill> cardSkillList = cardSkillService.getAllCardSkills();
                if (cardSkillList.size() > 0) {
                    response.setMessage("All Card Skills Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(cardSkillList.stream().map(cardSkill -> Map.of(
                            "idCard", cardSkill.getIdCard(),
                            "namaKartu", cardSkill.getNamaKartu(),
                            "rules", cardSkill.getRules(),
                            "totalKartu", cardSkill.getTotalKartu(),
                            "gambarKartu", cardSkill.getGambarKartu())).collect(Collectors.toList()));
                } else {
                    response.setMessage("No Card Skills Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.NO_CONTENT);
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
        listCardSkills.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCardSkillById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Card Skill By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<CardSkill> cardSkillOptional = cardSkillService.getCardSkillById(id);
                if (cardSkillOptional.isPresent()) {
                    listCardSkills.add(cardSkillOptional.get());
                    response.setMessage("Card Skill Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(listCardSkills.stream().map(cardSkill -> Map.of(
                            "idCard", cardSkill.getIdCard(),
                            "namaKartu", cardSkill.getNamaKartu(),
                            "rules", cardSkill.getRules(),
                            "totalKartu", cardSkill.getTotalKartu(),
                            "gambarKartu", cardSkill.getGambarKartu())).collect(Collectors.toList()));
                } else {
                    response.setMessage("Card Skill Not Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.NO_CONTENT);
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
        listCardSkills.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCardSkill(HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody CardSkillDTO cardSkillDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Card Skill");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<CardSkill> updatedCardSkill = cardSkillService.updateCardSkill(id, cardSkillDTO);
                    if (updatedCardSkill.isPresent()) {
                        listCardSkills.add(updatedCardSkill.get());
                        response.setMessage("Card Skill Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(listCardSkills.stream().map(cardSkill -> Map.of(
                                "idCard", cardSkill.getIdCard(),
                                "namaKartu", cardSkill.getNamaKartu(),
                                "rules", cardSkill.getRules(),
                                "totalKartu", cardSkill.getTotalKartu(),
                                "gambarKartu", cardSkill.getGambarKartu())).collect(Collectors.toList()));
                    } else {
                        response.setMessage("Card Skill Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.NO_CONTENT);
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
        listCardSkills.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCardSkill(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Card Skill");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = cardSkillService.deleteCardSkill(id);
                    if (isDeleted) {
                        response.setMessage("Card Skill Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Card Skill Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.NO_CONTENT);
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
        listCardSkills.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
