package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.ListKartuDTO;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.service.ListKartuService;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/listkartu")
public class ListKartuController {

    @Autowired
    private ListKartuService listKartuService;

    @PostMapping("/addListKartu")
    public ResponseEntity<Response<ListKartu>> addListKartu(@RequestBody ListKartuDTO listKartuDTO) {
        Response<ListKartu> response = new Response<>();
        response.setService("Add ListKartu");
        ListKartu newListKartu = listKartuService.addListKartu(listKartuDTO);
        response.setMessage("ListKartu Successfully Added");
        response.setData(newListKartu);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<ListKartu>>> getAllListKartu() {
        Response<List<ListKartu>> response = new Response<>();
        response.setService("Get All ListKartu");
        List<ListKartu> listKartuList = listKartuService.getAllListKartu();
        response.setData(listKartuList);
        response.setMessage("All ListKartu Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ListKartu>> getListKartuById(@PathVariable("id") String id) {
        Response<ListKartu> response = new Response<>();
        response.setService("Get ListKartu By ID");
        Optional<ListKartu> listKartu = listKartuService.getListKartuById(id);
        if (listKartu.isPresent()) {
            response.setData(listKartu.get());
            response.setMessage("ListKartu Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("ListKartu Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ListKartu>> updateListKartu(@PathVariable("id") String id, @RequestBody ListKartuDTO listKartuDTO) {
        Response<ListKartu> response = new Response<>();
        response.setService("Update ListKartu");
        Optional<ListKartu> updatedListKartu = listKartuService.updateListKartu(id, listKartuDTO);
        if (updatedListKartu.isPresent()) {
            response.setData(updatedListKartu.get());
            response.setMessage("ListKartu Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("ListKartu Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteListKartu(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete ListKartu");
        boolean isDeleted = listKartuService.deleteListKartu(id);
        if (isDeleted) {
            response.setMessage("ListKartu Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("ListKartu Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}
