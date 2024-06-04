// package com.restfulnplc.nplcrestful.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.restfulnplc.nplcrestful.dto.QnaDTO;
// import com.restfulnplc.nplcrestful.model.Qna;
// import com.restfulnplc.nplcrestful.service.QnaService;
// import com.restfulnplc.nplcrestful.util.Response;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @CrossOrigin
// @RequestMapping(value = "/api/qna")
// public class QnaController {

//     @Autowired
//     private QnaService qnaService;

//     @PostMapping("/addQna")
//     public ResponseEntity<Response<Qna>> addQna(@RequestBody QnaDTO qnaDTO) {
//         Response<Qna> response = new Response<>();
//         response.setService("Add QnA");
//         Qna newQna = qnaService.addQna(qnaDTO);
//         response.setMessage("QnA Successfully Added");
//         response.setData(newQna);
//         return ResponseEntity
//                 .status(HttpStatus.OK)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping
//     public ResponseEntity<Response<List<Qna>>> getAllQnas() {
//         Response<List<Qna>> response = new Response<>();
//         response.setService("Get All QnAs");
//         List<Qna> qnaList = qnaService.getAllQnas();
//         response.setData(qnaList);
//         response.setMessage("All QnAs Retrieved Successfully");
//         return ResponseEntity
//                 .status(HttpStatus.OK)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Response<Qna>> getQnaById(@PathVariable("id") int id) {
//         Response<Qna> response = new Response<>();
//         response.setService("Get QnA By ID");
//         Optional<Qna> qna = qnaService.getQnaById(id);
//         if (qna.isPresent()) {
//             response.setData(qna.get());
//             response.setMessage("QnA Retrieved Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("QnA Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Response<Qna>> updateQna(@PathVariable("id") int id, @RequestBody QnaDTO qnaDTO) {
//         Response<Qna> response = new Response<>();
//         response.setService("Update QnA");
//         Optional<Qna> updatedQna = qnaService.updateQna(id, qnaDTO);
//         if (updatedQna.isPresent()) {
//             response.setData(updatedQna.get());
//             response.setMessage("QnA Updated Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("QnA Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Response<String>> deleteQna(@PathVariable("id") int id) {
//         Response<String> response = new Response<>();
//         response.setService("Delete QnA");
//         boolean isDeleted = qnaService.deleteQna(id);
//         if (isDeleted) {
//             response.setMessage("QnA Deleted Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("QnA Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }
// }