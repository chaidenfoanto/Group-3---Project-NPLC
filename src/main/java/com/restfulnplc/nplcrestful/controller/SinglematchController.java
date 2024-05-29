package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.SinglematchDTO;
import com.restfulnplc.nplcrestful.model.Singlematch;
import com.restfulnplc.nplcrestful.service.SinglematchService;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/singlematch")
public class SinglematchController {

    @Autowired
    private SinglematchService singlematchService;

    @PostMapping("/addSinglematch")
    public ResponseEntity<Response<Singlematch>> addSinglematch(@RequestBody SinglematchDTO singlematchDTO) {
        Response<Singlematch> response = new Response<>();
        response.setService("Add Singlematch");
        Singlematch newSinglematch = singlematchService.addSinglematch(singlematchDTO);
        response.setMessage("Singlematch Successfully Added");
        response.setData(newSinglematch);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Singlematch>>> getAllSinglematches() {
        Response<List<Singlematch>> response = new Response<>();
        response.setService("Get All Singlematches");
        List<Singlematch> singlematchList = singlematchService.getAllSinglematches();
        response.setData(singlematchList);
        response.setMessage("All Singlematches Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Singlematch>> getSinglematchById(@PathVariable("id") String id) {
        Response<Singlematch> response = new Response<>();
        response.setService("Get Singlematch By ID");
        Optional<Singlematch> singlematch = singlematchService.getSinglematchById(id);
        if (singlematch.isPresent()) {
            response.setData(singlematch.get());
            response.setMessage("Singlematch Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Singlematch Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Singlematch>> updateSinglematch(@PathVariable("id") String id, @RequestBody SinglematchDTO singlematchDTO) {
        Response<Singlematch> response = new Response<>();
        response.setService("Update Singlematch");
        Optional<Singlematch> updatedSinglematch = singlematchService.updateSinglematch(id, singlematchDTO);
        if (updatedSinglematch.isPresent()) {
            response.setData(updatedSinglematch.get());
            response.setMessage("Singlematch Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Singlematch Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteSinglematch(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete Singlematch");
        boolean isDeleted = singlematchService.deleteSinglematch(id);
        if (isDeleted) {
            response.setMessage("Singlematch Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Singlematch Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}
