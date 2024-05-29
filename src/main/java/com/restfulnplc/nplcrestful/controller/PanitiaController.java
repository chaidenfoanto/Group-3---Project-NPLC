package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.PanitiaDTO;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.Response;

@RestController
@RequestMapping(value = "/api/panitia")
public class PanitiaController {
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private PanitiaService panitiaService;

    @PostMapping("/addPanitia/{password}")
    public ResponseEntity<Response<Panitia>> addPanitia(@PathVariable("password") String password, @RequestBody PanitiaDTO panitiaDTO) {
        Response<Panitia> response = new Response<Panitia>();
        response.setService("Add Panitia");
        if(loginService.checkAdminPassword(password)){
            Panitia newPanitia = panitiaService.addPanitia(panitiaDTO);
            response.setMessage("Panitia Successfully Added");
            response.setData(newPanitia);
            return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
        response.setMessage("Admin Password Doesn't Match");
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

}
