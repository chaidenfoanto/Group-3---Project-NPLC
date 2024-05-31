package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.StatusNPLCDTO;
import com.restfulnplc.nplcrestful.model.StatusNPLC;
import com.restfulnplc.nplcrestful.repository.StatusNPLCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusNPLCService {

    @Autowired
    private StatusNPLCRepository statusNPLCRepository;

    public StatusNPLC addStatusNPLC(StatusNPLCDTO statusNPLCDTO) {
        StatusNPLC statusNPLC = new StatusNPLC();
        statusNPLC.setWaktuSelesai(statusNPLCDTO.getWaktuSelesai());
        statusNPLC.setStatusGame(statusNPLCDTO.getStatusGame());
        return statusNPLCRepository.save(statusNPLC);
    }

    public List<StatusNPLC> getAllStatusNPLC() {
        return statusNPLCRepository.findAll();
    }

    public Optional<StatusNPLC> getStatusNPLCById(int id) {
        return statusNPLCRepository.findById(id);
    }

    public Optional<StatusNPLC> updateStatusNPLC(int id, StatusNPLCDTO statusNPLCDTO) {
        Optional<StatusNPLC> statusNPLCData = statusNPLCRepository.findById(id);
        if (statusNPLCData.isPresent()) {
            StatusNPLC statusNPLC = statusNPLCData.get();
            statusNPLC.setWaktuSelesai(statusNPLCDTO.getWaktuSelesai());
            statusNPLC.setStatusGame(statusNPLCDTO.getStatusGame());
            return Optional.of(statusNPLCRepository.save(statusNPLC));
        }
        return Optional.empty();
    }

    public boolean deleteStatusNPLC(int id) {
        if (statusNPLCRepository.existsById(id)) {
            statusNPLCRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
