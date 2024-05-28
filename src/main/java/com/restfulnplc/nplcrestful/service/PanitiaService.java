package com.restfulnplc.nplcrestful.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.repository.PanitiaRepository;

@Service
public class PanitiaService {
    @Autowired
    private PanitiaRepository panitiaRepository;

    public Optional<Panitia> findPanitiaByUsername(String username)
    {
        List<Panitia> listPanitia = panitiaRepository.findAll();
        for(Panitia panitia : listPanitia){
            if(panitia.getUsername().equals(username)){
                return Optional.of(panitia);
            }
        }
        return Optional.empty();
    }

    public Panitia addPanitia(PanitiaDTO panitiaDTO)
    {
        Panitia newPanitia = new Panitia(
            panitiaDTO.getIdPanitia(),
            panitiaDTO.getUsername(),
            panitiaDTO.getNama(),
            passwordMaker.hashPassword(panitiaDTO.getPassword()),
            panitiaDTO.getAngkatan(),
            panitiaDTO.getSpesialisasiClass(),
            panitiaDTO.getIsAdmin(),
            panitiaDTO.getDivisiClass()
        );
        panitiaRepository.save(newPanitia);
        return newPanitia;
    }
}
