package com.restfulnplc.nplcrestful.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restfulnplc.nplcrestful.model.Qna;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.QnaService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping("/ask")
    public ResponseEntity<Response> askQuestion(HttpServletRequest request,
            @RequestParam(name = "pertanyaan") String pertanyaan) {
        String sessionToken = request.getHeader("Token");
        response.setService("Ask Question");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionTeam(sessionToken)) {
                    String userId = loginService.getLoginSession(sessionToken).getIdUser();
                    Qna newQna = qnaService.addQuestion(pertanyaan, userId);
                    response.setMessage("Question Asked Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "idPertanyaan", newQna.getIdPertanyaan(),
                            "pertanyaan", newQna.getPertanyaan(),
                            "waktuInput", newQna.getWaktuInput(),
                            "jawaban", newQna.getJawaban(),
                            "namaPanitia", newQna.getPanitia().getNama(),
                            "namaTeam", newQna.getTeam().getNama()));
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

    @PostMapping("/answer/{id}")
    public ResponseEntity<Response> answerQuestion(HttpServletRequest request,
            @RequestParam(name = "jawaban") String jawaban,
            @PathVariable String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Answer Question");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    if (qnaService.getQuestionById(id).isPresent()) {
                        String userId = loginService.getLoginSession(sessionToken).getIdUser();
                        Qna answeredQna = qnaService.answerQuestion(id, jawaban, userId);
                        response.setMessage("Question Answered Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.CREATED);
                        response.setData(Map.of(
                                "idPertanyaan", answeredQna.getIdPertanyaan(),
                                "pertanyaan", answeredQna.getPertanyaan(),
                                "waktuInput", answeredQna.getWaktuInput(),
                                "jawaban", answeredQna.getJawaban(),
                                "namaPanitia", answeredQna.getPanitia().getNama(),
                                "namaTeam", answeredQna.getTeam().getNama()));
                    } else {
                        response.setMessage("Question Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.NOT_FOUND);
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

    @GetMapping("/questions")
    public ResponseEntity<Response> getAllQuestions(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Questions");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Qna> questionsList = qnaService.getAllQuestions();
                if (!questionsList.isEmpty()) {
                    response.setMessage("All Questions Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    List<Object> listData = new ArrayList<>();
                    for (Qna qna : questionsList) {
                        listData.add(Map.of(
                                "idPertanyaan", qna.getIdPertanyaan(),
                                "pertanyaan", qna.getPertanyaan(),
                                "waktuInput", qna.getWaktuInput(),
                                "jawaban", qna.getJawaban(),
                                "namaPanitia", qna.getPanitia().getNama(),
                                "namaTeam", qna.getTeam().getNama()));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Questions Found");
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteQuestion(HttpServletRequest request, @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Question");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = qnaService.deleteQuestion(id);
                    if (isDeleted) {
                        response.setMessage("Question Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Question Not Found");
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
