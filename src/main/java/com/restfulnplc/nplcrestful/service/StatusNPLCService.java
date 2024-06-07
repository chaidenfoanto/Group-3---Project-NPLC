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

    public StatusNPLC getStatusNPLC() {
        return statusNPLCRepository.findAll().get(0);
    }

    public StatusNPLC updateStatusNPLC(String condition) {
        StatusNPLC statusNPLC = getStatusNPLC();
        switch(condition) {
            case "Start": statusNPLC.setStatusGame(StatusGame.INPROGRESS); break;
            case "Stop": statusNPLC.setStatusGame(StatusGame.DONE); break;
            default:
            statusNPLC.setStatusGame(StatusGame.NOTSTARTED);
            break;
        }
        statusNPLCRepository.save(statusNPLC);
        return statusNPLC;
    }

    public void setNPLCTime(TimeDTO timeDTO) {
        StatusNPLC statusNPLC = getStatusNPLC();
        statusNPLC.setWaktuSelesai(timeDTO.getWaktuSelesai());
    }
}
