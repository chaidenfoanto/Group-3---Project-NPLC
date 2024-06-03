package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.StatusNPLCDTO;
import com.restfulnplc.nplcrestful.model.StatusNPLC;
import com.restfulnplc.nplcrestful.repository.StatusNPLCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusNPLCService {

    @Autowired
    private StatusNPLCRepository statusNPLCRepository;

    public List<StatusNPLC> getAllStatusNPLC() {
        return statusNPLCRepository.findAll();
    }

    public StatusNPLC updateStatusNPLC(int id, StatusNPLCDTO statusNPLCDTO) {
        StatusNPLC statusNPLC = statusNPLCRepository.findAll().getLast();
        statusNPLC.setWaktuSelesai(statusNPLCDTO.getWaktuSelesai());
        statusNPLC.setStatusGame(statusNPLCDTO.getStatusGame());
        statusNPLCRepository.save(statusNPLC);
        return statusNPLC;
    }
}
