package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.ListKartuDTO;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.repository.ListKartuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListKartuService {

    @Autowired
    private ListKartuRepository listKartuRepository;

    public ListKartu addListKartu(ListKartuDTO listKartuDTO) {
        ListKartu listKartu = new ListKartu();
        listKartu.setNoKartu(listKartuDTO.getNoKartu());
        listKartu.setCardSkill(listKartuDTO.getCardSkill());
        listKartu.setOwnedBy(listKartuDTO.getOwnedBy());
        listKartu.setIsUsed(listKartuDTO.isIsUsed());
        return listKartuRepository.save(listKartu);
    }

    public List<ListKartu> getAllListKartu() {
        return listKartuRepository.findAll();
    }

    public Optional<ListKartu> getListKartuById(String id) {
        return listKartuRepository.findById(id);
    }

    public Optional<ListKartu> updateListKartu(String id, ListKartuDTO listKartuDTO) {
        Optional<ListKartu> optionalListKartu = listKartuRepository.findById(id);
        if (optionalListKartu.isPresent()) {
            ListKartu listKartu = optionalListKartu.get();
            listKartu.setCardSkill(listKartuDTO.getCardSkill());
            listKartu.setOwnedBy(listKartuDTO.getOwnedBy());
            listKartu.setIsUsed(listKartuDTO.isIsUsed());
            return Optional.of(listKartuRepository.save(listKartu));
        }
        return Optional.empty();
    }

    public boolean deleteListKartu(String id) {
        Optional<ListKartu> optionalListKartu = listKartuRepository.findById(id);
        if (optionalListKartu.isPresent()) {
            listKartuRepository.delete(optionalListKartu.get());
            return true;
        }
        return false;
    }
}