package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.LokasiDTO;
import com.restfulnplc.nplcrestful.model.Lokasi;
import com.restfulnplc.nplcrestful.repository.LokasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LokasiService {

    @Autowired
    private LokasiRepository lokasiRepository;

    public Lokasi addLokasi(LokasiDTO lokasiDTO) {
        Lokasi lokasi = new Lokasi();
        lokasi.setNoRuangan(lokasiDTO.getNoRuangan());
        lokasi.setLantai(lokasiDTO.getLantai());
        return lokasiRepository.save(lokasi);
    }

    public List<Lokasi> getAllLokasi() {
        return lokasiRepository.findAll();
    }

    public Optional<Lokasi> getLokasiById(String id) {
        return lokasiRepository.findById(id);
    }

    public Optional<Lokasi> updateLokasi(String id, LokasiDTO lokasiDTO) {
        Optional<Lokasi> optionalLokasi = lokasiRepository.findById(id);
        if (optionalLokasi.isPresent()) {
            Lokasi lokasi = optionalLokasi.get();
            lokasi.setLantai(lokasiDTO.getLantai());
            return Optional.of(lokasiRepository.save(lokasi));
        }
        return Optional.empty();
    }

    public boolean deleteLokasi(String id) {
        Optional<Lokasi> optionalLokasi = lokasiRepository.findById(id);
        if (optionalLokasi.isPresent()) {
            lokasiRepository.delete(optionalLokasi.get());
            return true;
        }
        return false;
    }
}
