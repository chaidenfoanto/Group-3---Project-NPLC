package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.CardSkillDTO;
import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.service.CardSkillService;
import com.restfulnplc.nplcrestful.service.ListKartuService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/cardskills")
public class CardSkillController {

    @Autowired
    private CardSkillService cardSkillService;
    
    @Autowired
    private ListKartuService listKartuService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addCardSkill(HttpServletRequest request,
            @RequestBody CardSkillDTO cardSkillDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Card Skill");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    CardSkill newCardSkill = cardSkillService.addCardSkill(cardSkillDTO);
                    response.setMessage("Card Skill Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "idCard", newCardSkill.getIdCard(),
                            "namaKartu", newCardSkill.getNamaKartu(),
                            "rules", newCardSkill.getRules(),
                            "totalKartu", newCardSkill.getTotalKartu(),
                            "gambarKartu", newCardSkill.getGambarKartu()
                    ));
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
    public ResponseEntity<Response> getAllCardSkills(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All CardSkills");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<CardSkill> cardSkillList = cardSkillService.getAllCardSkills();
                if (cardSkillList.size() > 0) {
                    response.setMessage("All Card Skills Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (CardSkill cardSkill : cardSkillList) {
                        listData.add(Map.of(
                                "idCard", cardSkill.getIdCard(),
                                "namaKartu", cardSkill.getNamaKartu(),
                                "rules", cardSkill.getRules(),
                                "totalKartu", cardSkill.getTotalKartu(),
                                "gambarKartu", cardSkill.getGambarKartu()
                        ));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Card Skills Found");
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

    @GetMapping("/getWithUser")
    public ResponseEntity<Response> getAllCardSkillsAndUser(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All CardSkills");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                String userid = loginService.getLoginSession(sessionToken).getIdUser();
                List<CardSkill> cardSkillList = cardSkillService.getAllCardSkills();
                if (cardSkillList.size() > 0) {
                    response.setMessage("All Card Skills With User Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (CardSkill cardSkill : cardSkillList) {
                        listData.add(Map.of(
                                "idCard", cardSkill.getIdCard(),
                                "namaKartu", cardSkill.getNamaKartu(),
                                "rules", cardSkill.getRules(),
                                "totalKartu", cardSkill.getTotalKartu(),
                                "gambarKartu", cardSkill.getGambarKartu(),
                                "totalCard", listKartuService.getCardsByTeamIdAndCardID(userid, cardSkill.getIdCard()).size(),
                                "totalUsed", listKartuService.getUsedCardsByTeamIdAndCardID(userid, cardSkill.getIdCard()).size()
                        ));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Card Skills Data Found");
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
    public ResponseEntity<Response> getCardSkillById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Card Skill By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<CardSkill> cardSkillOptional = cardSkillService.getCardSkillById(id);
                if (cardSkillOptional.isPresent()) {
                    CardSkill cardSkill = cardSkillOptional.get();
                    response.setMessage("Card Skill Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "idCard", cardSkill.getIdCard(),
                            "namaKartu", cardSkill.getNamaKartu(),
                            "rules", cardSkill.getRules(),
                            "totalKartu", cardSkill.getTotalKartu(),
                            "gambarKartu", cardSkill.getGambarKartu()
                    ));
                } else {
                    response.setMessage("Card Skill Not Found");
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
                        CardSkill cardSkill = updatedCardSkill.get();
                        response.setMessage("Card Skill Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "idCard", cardSkill.getIdCard(),
                                "namaKartu", cardSkill.getNamaKartu(),
                                "rules", cardSkill.getRules(),
                                "totalKartu", cardSkill.getTotalKartu(),
                                "gambarKartu", cardSkill.getGambarKartu()
                        ));
                    } else {
                        response.setMessage("Card Skill Not Found");
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