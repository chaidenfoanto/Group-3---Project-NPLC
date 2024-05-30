package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.CardSkillDTO;
import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.repository.CardSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardSkillService {

    @Autowired
    private CardSkillRepository cardSkillRepository;

    public CardSkill addCardSkill(CardSkillDTO cardSkillDTO) {
        CardSkill cardSkill = new CardSkill();
        cardSkill.setNamaKartu(cardSkillDTO.getNamaKartu());
        cardSkill.setRules(cardSkillDTO.getRules());
        cardSkill.setTotalKartu(cardSkillDTO.getTotalKartu());
        cardSkill.setGambarKartu(cardSkillDTO.getGambarKartu());
        return cardSkillRepository.save(cardSkill);
    }

    public List<CardSkill> getAllCardSkills() {
        return cardSkillRepository.findAll();
    }

    public Optional<CardSkill> getCardSkillById(String id) {
        return cardSkillRepository.findById(Integer.parseInt(id));
    }

    public Optional<CardSkill> updateCardSkill(String id, CardSkillDTO cardSkillDTO) {
        Optional<CardSkill> optionalCardSkill = cardSkillRepository.findById(Integer.parseInt(id));
        if (optionalCardSkill.isPresent()) {
            CardSkill cardSkill = optionalCardSkill.get();
            cardSkill.setNamaKartu(cardSkillDTO.getNamaKartu());
            cardSkill.setRules(cardSkillDTO.getRules());
            cardSkill.setTotalKartu(cardSkillDTO.getTotalKartu());
            cardSkill.setGambarKartu(cardSkillDTO.getGambarKartu());
            return Optional.of(cardSkillRepository.save(cardSkill));
        }
        return Optional.empty();
    }

    public boolean deleteCardSkill(String id) {
        Optional<CardSkill> optionalCardSkill = cardSkillRepository.findById(Integer.parseInt(id));
        if (optionalCardSkill.isPresent()) {
            cardSkillRepository.delete(optionalCardSkill.get());
            return true;
        }
        return false;
    }
}
