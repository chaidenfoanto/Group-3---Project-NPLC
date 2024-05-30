package com.restfulnplc.nplcrestful.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.PanitiaDTO;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.repository.PanitiaRepository;
import com.restfulnplc.nplcrestful.util.PasswordHasherMatcher;

@Service
public class PanitiaService {
    @Autowired
    private PanitiaRepository panitiaRepository;

    @Autowired
    private PasswordHasherMatcher passwordMaker;

    public Optional<Panitia> findPanitiaByUsername(String username) {
        List<Panitia> listPanitia = panitiaRepository.findAll();
        for (Panitia panitia : listPanitia) {
            if (panitia.getUsername().equals(username)) {
                return Optional.of(panitia);
            }
        }
        return Optional.empty();
    }

    public Panitia addPanitia(PanitiaDTO panitiaDTO) {
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

    // Menambahkan semua metode sesuai dengan controller

    public List<Panitia> getAllPanitia() {
        return panitiaRepository.findAll();
    }

    public Optional<Panitia> getPanitiaById(String id) {
        return panitiaRepository.findById(id);
    }

    public Optional<Panitia> updatePanitia(String id, PanitiaDTO panitiaDTO) {
        Optional<Panitia> optionalPanitia = panitiaRepository.findById(id);
        if (optionalPanitia.isPresent()) {
            Panitia existingPanitia = optionalPanitia.get();
            existingPanitia.setUsername(panitiaDTO.getUsername());
            existingPanitia.setNama(panitiaDTO.getNama());
            if (!panitiaDTO.getPassword().isEmpty()) {
                existingPanitia.setPassword(passwordMaker.hashPassword(panitiaDTO.getPassword()));
            }
            existingPanitia.setAngkatan(panitiaDTO.getAngkatan());
            existingPanitia.setSpesialisasi(panitiaDTO.getSpesialisasiClass());
            existingPanitia.setIsAdmin(panitiaDTO.getIsAdmin());
            existingPanitia.setDivisi(panitiaDTO.getDivisiClass());
            panitiaRepository.save(existingPanitia);
            return Optional.of(existingPanitia);
        }
        return Optional.empty();
    }

    public boolean deletePanitia(String id) {
        Optional<Panitia> optionalPanitia = panitiaRepository.findById(id);
        if (optionalPanitia.isPresent()) {
            panitiaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
