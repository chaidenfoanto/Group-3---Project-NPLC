package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.QnaDTO;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.model.Qna;
import com.restfulnplc.nplcrestful.repository.PanitiaRepository;
import com.restfulnplc.nplcrestful.repository.PlayersRepository;
import com.restfulnplc.nplcrestful.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service
// public class QnaService {

//     @Autowired
//     private QnaRepository qnaRepository;

//     @Autowired
//     private PlayersRepository playersRepository;

//     @Autowired
//     private PanitiaRepository panitiaRepository;

//     public Qna addQna(QnaDTO qnaDTO) {
//         Qna qna = new Qna();
//         qna.setPertanyaan(qnaDTO.getPertanyaan());
//         qna.setWaktuInput(qnaDTO.getWaktuInput());
//         qna.setJawaban(qnaDTO.getJawaban());

//         Optional<Players> player = playersRepository.findById(qnaDTO.getIdPlayer());
//         if (player.isPresent()) {
//             qna.setPlayer(player.get());
//         }

//         Optional<Panitia> panitia = panitiaRepository.findById(qnaDTO.getIdPanitia());
//         if (panitia.isPresent()) {
//             qna.setPanitia(panitia.get());
//         }

//         return qnaRepository.save(qna);
//     }

//     public List<Qna> getAllQnas() {
//         return qnaRepository.findAll();
//     }

//     public Optional<Qna> getQnaById(int id) {
//         return qnaRepository.findById(id);
//     }

//     public Optional<Qna> updateQna(int id, QnaDTO qnaDTO) {
//         Optional<Qna> qnaData = qnaRepository.findById(id);
//         if (qnaData.isPresent()) {
//             Qna qna = qnaData.get();
//             qna.setPertanyaan(qnaDTO.getPertanyaan());
//             qna.setWaktuInput(qnaDTO.getWaktuInput());
//             qna.setJawaban(qnaDTO.getJawaban());

//             Optional<Players> player = playersRepository.findById(qnaDTO.getIdPlayer());
//             if (player.isPresent()) {
//                 qna.setPlayer(player.get());
//             }

//             Optional<Panitia> panitia = panitiaRepository.findById(qnaDTO.getIdPanitia());
//             if (panitia.isPresent()) {
//                 qna.setPanitia(panitia.get());
//             }

//             return Optional.of(qnaRepository.save(qna));
//         }
//         return Optional.empty();
//     }

//     public boolean deleteQna(int id) {
//         if (qnaRepository.existsById(id)) {
//             qnaRepository.deleteById(id);
//             return true;
//         }
//         return false;
//     }
// }



@Service
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    private PanitiaRepository panitiaRepository;

    public Qna addQuestion(QnaDTO qnaDTO) {
        Qna qna = new Qna();
        qna.setPertanyaan(qnaDTO.getPertanyaan());
        qna.setWaktuInput(qnaDTO.getWaktuInput());

        Optional<Players> player = playersRepository.findById(qnaDTO.getIdPlayer());
        if (player.isPresent()) {
            qna.setPlayer(player.get());
        }

        return qnaRepository.save(qna);
    }

    public Qna addAnswer(int id, QnaDTO qnaDTO) {
        Optional<Qna> qnaData = qnaRepository.findById(id);
        if (qnaData.isPresent()) {
            Qna qna = qnaData.get();
            qna.setJawaban(qnaDTO.getJawaban());

            Optional<Panitia> panitia = panitiaRepository.findById(qnaDTO.getIdPanitia());
            if (panitia.isPresent()) {
                qna.setPanitia(panitia.get());
            }

            return qnaRepository.save(qna);
        }
        return null;
    }

    public List<Qna> getAllQuestions() {
        return qnaRepository.findAll();
    }

    public Optional<Qna> getQuestionById(int id) {
        return qnaRepository.findById(id);
    }

    public boolean deleteQuestion(int id) {
        if (qnaRepository.existsById(id)) {
            qnaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
