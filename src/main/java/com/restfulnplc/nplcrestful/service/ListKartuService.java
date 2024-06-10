package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.ListKartuDTO;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.repository.ListKartuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
public class ListKartuService {

    @Autowired
    private ListKartuRepository listKartuRepository;

    @Autowired
    private CardSkillService cardSkillService;

    @Autowired
    private TeamService teamService;

    public ListKartu addListKartu(ListKartuDTO listKartuDTO) {
        ListKartu listKartu = new ListKartu();
        listKartu.setNoKartu(listKartuDTO.getNoKartu());
        listKartu.setCardSkill(cardSkillService.getCardSkillById(listKartuDTO.getCardSkillId()).get());
        return listKartuRepository.save(listKartu);
    }

    public ListKartu updateListKartu(String id, ListKartuDTO listKartuDTO) {
        ListKartu listKartu = getListKartuById(id).get();
        listKartu.setNoKartu(listKartuDTO.getNoKartu());
        listKartu.setCardSkill(cardSkillService.getCardSkillById(listKartuDTO.getCardSkillId()).get());
        return listKartuRepository.save(listKartu);
    }

    public List<ListKartu> getAllListKartu() {
        return listKartuRepository.findAll();
    }

    public Optional<ListKartu> getListKartuById(String id) {
        return listKartuRepository.findById(id);
    }

    public ListKartu teamGetCard(String idTeam) {
        Random rand = new Random();
        List<ListKartu> listAvailables = getAvailableCard();
        ListKartu selectedCard = listAvailables.get(rand.nextInt((listAvailables.size() - 1) - 0 + 1) + 0);
        selectedCard.setOwnedBy(teamService.getTeamById(idTeam).get());
        listKartuRepository.save(selectedCard);
        return selectedCard;
    }

    public List<ListKartu> getAvailableCard() {
        List<ListKartu> listKartu = Collections.<ListKartu>emptyList();
        for(ListKartu kartu : getAllListKartu()) {
            if(kartu.getOwnedBy() == null) {
                listKartu.add(kartu);
            }
        }
        return listKartu;
    }

    public Optional<ListKartu> useCard(String id) {
        Optional<ListKartu> optionalListKartu = listKartuRepository.findById(id);
        if (optionalListKartu.isPresent()) {
            ListKartu listKartu = optionalListKartu.get();
            listKartu.setIsUsed(true);
            return Optional.of(listKartuRepository.save(listKartu));
        }
        return Optional.empty();
    }

    public Optional<ListKartu> getCardByTeamId(String id) {
        for(ListKartu kartu : getAllListKartu()) {
            if(kartu.getOwnedBy().equals(teamService.getTeamById(id).get())) {
                return Optional.of(kartu);
            }
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

    public void reset()
    {
        for(ListKartu kartu : getAllListKartu()) {
            kartu.setIsUsed(false);
            kartu.setOwnedBy(null);
        }
    }
}