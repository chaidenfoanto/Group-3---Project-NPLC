package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.TimeDTO;
import com.restfulnplc.nplcrestful.model.StatusGame;
import com.restfulnplc.nplcrestful.model.StatusNPLC;
import com.restfulnplc.nplcrestful.repository.StatusNPLCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusNPLCService {

    @Autowired
    private StatusNPLCRepository statusNPLCRepository;

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private DuelMatchService duelMatchService;

    @Autowired
    private SinglematchService singlematchService;

    @Autowired
    private ListKartuService listKartuService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private PlayersService playersService;

    @Autowired
    private QnaService qnaService;

    @Autowired
    private TeamService teamService;

    public StatusNPLC getStatusNPLC() {
        return statusNPLCRepository.findAll().get(0);
    }

    public StatusNPLC updateStatusNPLC(String condition) {
        StatusNPLC statusNPLC = getStatusNPLC();
        switch (condition) {
            case "Start":
                statusNPLC.setStatusGame(StatusGame.INPROGRESS);
                break;
            case "Stop":
                statusNPLC.setStatusGame(StatusGame.DONE);
                break;
            default:
                statusNPLC.setStatusGame(StatusGame.NOTSTARTED);
                statusNPLC.setNplcGen(statusNPLC.getNplcGen() + 1);
                boothgamesService.reset();
                duelMatchService.reset();
                singlematchService.reset();
                listKartuService.reset();
                loginService.reset();
                panitiaService.reset();
                playersService.reset();
                qnaService.reset();
                teamService.reset();
                break;
        }
        statusNPLCRepository.save(statusNPLC);
        return statusNPLC;
    }

    public void setNPLCTime(TimeDTO timeDTO) {
        StatusNPLC statusNPLC = getStatusNPLC();
        statusNPLC.setWaktuSelesai(timeDTO.getWaktu());
        statusNPLCRepository.save(statusNPLC);
    }

    public StatusNPLC setNPLCGen(int gen) {
        StatusNPLC statusNPLC = getStatusNPLC();
        statusNPLC.setNplcGen(gen);
        statusNPLCRepository.save(statusNPLC);
        return statusNPLC;
    }
}
