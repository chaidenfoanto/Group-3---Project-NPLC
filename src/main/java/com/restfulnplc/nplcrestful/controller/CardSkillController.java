package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.CardSkillDTO;
import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.service.CardSkillService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.Response;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/cardskill")
public class CardSkillController {

    @Autowired
    private CardSkillService cardSkillService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private Response response;

    List<CardSkill> listCardSkill = Collections.<CardSkill>emptyList();

    @PostMapping("/addCardSkill")
    public ResponseEntity<Response> addCardSkill(@RequestHeader("Token") String sessionToken, @RequestBody CardSkillDTO cardskillDTO){
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                response.setService("Add Card Skill");
                CardSkill newCardSkill = cardSkillService.addCardSkill(cardskillDTO);
                listCardSkill.add(newCardSkill);
                response.setMessage("Card Skill Successfully Added");
                response.setError(false);
                response.setHttpCode(HTTPCode.CREATED);
                response.setData(listCardSkill.stream().map(cardskill -> Map.of(
                    "idCard",cardskill.getIdCard(),
                    "namakartu",cardskill.getNamaKartu(),
                    "rules",cardskill.getRules(),
                    "totalkartu",cardskill.getTotalKartu(),
                    "gambarkartu", cardskill.getGambarKartu())).collect(Collectors.toList()));
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.FORBIDDEN);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listCardSkill.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        
    }

    @GetMapping
    public ResponseEntity<Response> getAllCardSkill(@RequestHeader("Token") String sessionToken) {
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Get All Card Skill");
            List<CardSkill> cardSkillList = cardSkillService.getAllCardSkills();
            if (cardSkillList.size() > 0) {
                response.setMessage("All Card Skill Retrieved Successfully");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(cardSkillList.stream().map(cardskill -> Map.of(
                    "idCard",cardskill.getIdCard(),
                    "namakartu",cardskill.getNamaKartu(),
                    "rules",cardskill.getRules(),
                    "totalkartu",cardskill.getTotalKartu(),
                    "gambarkartu", cardskill.getGambarKartu())).collect(Collectors.toList()));
            } else {
                response.setMessage("Card Skill Not Found");
                response.setError(true);
                response.setHttpCode(HTTPCode.NO_CONTENT);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.FORBIDDEN);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listCardSkill.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCardSkillById(@RequestHeader("Token") String sessionToken, @PathVariable("id") String id) {
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Get Card Skill By ID");
            Optional<CardSkill> cardSkillOptional = cardSkillService.getCardSkillById(id);
            if (cardSkillOptional.isPresent()) {
                listCardSkill.add(cardSkillOptional.get());
                response.setMessage("Card Skill Retrieved Successfully");
                response.setError(false);
                response.setHttpCode(HTTPCode.CREATED);
                response.setData(listCardSkill.stream().map(cardskill -> Map.of(
                    "idCard",cardskill.getIdCard(),
                    "namakartu",cardskill.getNamaKartu(),
                    "rules",cardskill.getRules(),
                    "totalkartu",cardskill.getTotalKartu(),
                    "gambarkartu", cardskill.getGambarKartu())).collect(Collectors.toList()));
            } else {
                response.setMessage("Card Skill Not Found");
                response.setError(true);
                response.setHttpCode(HTTPCode.NO_CONTENT);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.FORBIDDEN);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listCardSkill.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCardSkill(@RequestHeader("Token") String sessionToken, @PathVariable("id") String id, @RequestBody CardSkillDTO cardSkillDTO) {
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Update Card Skill");
            Optional<CardSkill> updatedCardSkill = cardSkillService.updateCardSkill(id, cardSkillDTO);
            if (updatedCardSkill.isPresent()) {
                listCardSkill.add(updatedCardSkill.get());
                response.setMessage("Card Skill Updated Successfully");
                response.setError(false);
                response.setHttpCode(HTTPCode.CREATED);
                response.setData(listCardSkill.stream().map(cardskill -> Map.of(
                    "idCard",cardskill.getIdCard(),
                    "namakartu",cardskill.getNamaKartu(),
                    "rules",cardskill.getRules(),
                    "totalkartu",cardskill.getTotalKartu(),
                    "gambarkartu", cardskill.getGambarKartu())).collect(Collectors.toList()));
            } else {
                response.setMessage("Card Skill Not Found");
                response.setError(true);
                response.setHttpCode(HTTPCode.NO_CONTENT);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.FORBIDDEN);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listCardSkill.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCardSkill(@RequestHeader("Token") String sessionToken, @PathVariable("id") String id){
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Delete Card Skill");
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
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.FORBIDDEN);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listCardSkill.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
