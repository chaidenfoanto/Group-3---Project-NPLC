package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.model.Qna;
import com.restfulnplc.nplcrestful.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PanitiaService panitiaService;

    public Qna addQuestion(String pertanyaan, String idTeam) {
        Qna qna = new Qna();
        qna.setIdPertanyaan(getNextQnaID());
        qna.setTeam(teamService.getTeamById(idTeam).get());
        qna.setPertanyaan(pertanyaan);
        qna.setWaktuInput(LocalDateTime.now());

        return qnaRepository.save(qna);
    }

    public Qna answerQuestion(String id, String jawaban, String idAdmin) {
        Optional<Qna> qnaData = getQuestionById(id);
        if (qnaData.isPresent()) {
            Qna qna = qnaData.get();
            qna.setJawaban(jawaban);
            qna.setPanitia(panitiaService.getPanitiaById(idAdmin).get());

            return qnaRepository.save(qna);
        }
        return null;
    }

    public List<Qna> getAllQuestions() {
        return qnaRepository.findAll();
    }

    public Optional<Qna> getQuestionById(String id) {
        return qnaRepository.findById(id);
    }

    public boolean deleteQuestion(String id) {
        if (qnaRepository.existsById(id)) {
            qnaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String getNextQnaID()
    {
        List<Qna> Qnas = qnaRepository.findAll();
        int highestID = 0;
        for (Qna qna : Qnas) {
            int newID = Integer.parseInt(qna.getIdPertanyaan().split("QNA")[1]);
            if (newID > highestID)
                highestID = newID;
        }
        return "QNA" + (highestID + 1);
    }
    
    public void reset()
    {
        qnaRepository.deleteAll();
    }
}
