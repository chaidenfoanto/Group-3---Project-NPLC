package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.CardSkillDTO;
import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.service.CardSkillService;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/cardskill")
public class CardSkillController {

    @Autowired
    private CardSkillService cardSkillService;

    @PostMapping("/addCardSkill")
    public ResponseEntity<Response<CardSkill>> addCardSkill(@RequestBody CardSkillDTO cardSkillDTO) {
        Response<CardSkill> response = new Response<>();
        response.setService("Add Card Skill");
        CardSkill newCardSkill = cardSkillService.addCardSkill(cardSkillDTO);
        response.setMessage("Card Skill Successfully Added");
        response.setData(newCardSkill);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<CardSkill>>> getAllCardSkills() {
        Response<List<CardSkill>> response = new Response<>();
        response.setService("Get All CardSkills");
        List<CardSkill> cardSkillList = cardSkillService.getAllCardSkills();
        response.setData(cardSkillList);
        response.setMessage("All CardSkills Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CardSkill>> getCardSkillById(@PathVariable("id") String id) {
        Response<CardSkill> response = new Response<>();
        response.setService("Get CardSkill By ID");
        Optional<CardSkill> cardSkill = cardSkillService.getCardSkillById(id);
        if (cardSkill.isPresent()) {
            response.setData(cardSkill.get());
            response.setMessage("CardSkill Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("CardSkill Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CardSkill>> updateCardSkill(@PathVariable("id") String id, @RequestBody CardSkillDTO cardSkillDTO) {
        Response<CardSkill> response = new Response<>();
        response.setService("Update CardSkill");
        Optional<CardSkill> updatedCardSkill = cardSkillService.updateCardSkill(id, cardSkillDTO);
        if (updatedCardSkill.isPresent()) {
            response.setData(updatedCardSkill.get());
            response.setMessage("CardSkill Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("CardSkill Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteCardSkill(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete CardSkill");
        boolean isDeleted = cardSkillService.deleteCardSkill(id);
        if (isDeleted) {
            response.setMessage("CardSkill Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("CardSkill Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}
