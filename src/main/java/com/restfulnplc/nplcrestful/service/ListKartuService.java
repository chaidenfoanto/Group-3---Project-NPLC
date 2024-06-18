package com.restfulnplc.nplcrestful.service;

import com.restfulnplc.nplcrestful.dto.ListKartuDTO;
import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.repository.ListKartuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

    public Optional<ListKartu> teamGetCard(String idTeam) {
        Random rand = new Random();
        ArrayList<ListKartu> listAvailables = getAvailableCard();
        Team team = teamService.getTeamById(idTeam).get();
        if ((listAvailables.size() - getAvailableZonks().size()) > 0) {
            int randVal = rand.nextInt(listAvailables.size() - 1); // Randoming card in list
            ListKartu selectedCard = listAvailables.get(randVal); // Getting result card
            selectedCard.setOwnedBy(team);
            selectedCard.setIsUsed(false);
            listKartuRepository.save(selectedCard);
            teamService.teamRolled(idTeam);
            return Optional.of(selectedCard);
        }
        return Optional.empty();
    }

    public ArrayList<ListKartu> getAvailableCard() {
        ArrayList<ListKartu> listKartu = new ArrayList<ListKartu>();
        for (ListKartu kartu : getAllListKartu()) {
            if (!(kartu.getOwnedBy() != null)) {
                listKartu.add(kartu);
            }
        }
        return listKartu;
    }

    public ArrayList<ListKartu> getAvailableZonks() {
        ArrayList<ListKartu> listKartu = new ArrayList<ListKartu>();
        for (ListKartu kartu : getAllListKartu()) {
            if ((!(kartu.getOwnedBy() != null)) && kartu.getCardSkill().getIdCard().equals("ZONK")) {
                listKartu.add(kartu);
            }
        }
        return listKartu;
    }

    public ArrayList<ListKartu> getUsedCards() {
        ArrayList<ListKartu> listKartu = new ArrayList<ListKartu>();
        for (ListKartu kartu : getAllListKartu()) {
            if (kartu.getIsUsed()) {
                listKartu.add(kartu);
            }
        }
        return listKartu;
    }

    public Optional<ListKartu> useCard(String id) {
        Optional<ListKartu> optionalListKartu = listKartuRepository.findById(id);
        if (optionalListKartu.isPresent()) {
            ListKartu listKartu = optionalListKartu.get();
            if (!listKartu.getCardSkill().getIdCard().equals("ZONK")) {
                listKartu.setIsUsed(true);
                return Optional.of(listKartuRepository.save(listKartu));
            }
        }
        return Optional.empty();
    }

    public ArrayList<ListKartu> getCardsByTeamId(String id) {
        ArrayList<ListKartu> listKartu = new ArrayList<ListKartu>();
        Team team = teamService.getTeamById(id).get();
        for (ListKartu kartu : getAllListKartu()) {
            if (kartu.getOwnedBy().equals(team)) {
                listKartu.add(kartu);
            }
        }
        return listKartu;
    }

    public ArrayList<ListKartu> getCardsByTeamIdAndCardID(String id, String cardid) {
        ArrayList<ListKartu> listKartu = new ArrayList<ListKartu>();
        Team team = teamService.getTeamById(id).get();
        for (ListKartu kartu : getAllListKartu()) {
            if (kartu.getOwnedBy() != null) {
                if (kartu.getOwnedBy().equals(team) && kartu.getCardSkill().getIdCard().equals(cardid)) {
                    listKartu.add(kartu);
                }
            }
        }
        return listKartu;
    }

    public ArrayList<ListKartu> getUsedCardsByTeamIdAndCardID(String id, String cardid) {
        ArrayList<ListKartu> listKartu = new ArrayList<ListKartu>();
        Team team = teamService.getTeamById(id).get();
        for (ListKartu kartu : getAllListKartu()) {
            if (kartu.getOwnedBy() != null) {
                if (kartu.getOwnedBy().equals(team) && kartu.getCardSkill().getIdCard().equals(cardid)
                        && kartu.getIsUsed()) {
                    listKartu.add(kartu);
                }
            }
        }
        return listKartu;
    }

    public Object getCardStatsByTeam(String id) {
        ArrayList<Object> listUsed = new ArrayList<Object>();
        ArrayList<Object> listUnused = new ArrayList<Object>();
        for (CardSkill cardSkill : cardSkillService.getAllCardSkills()) {
            if (!cardSkill.getIdCard().equals("ZONK")) {
                ArrayList<Object> cardNumbersUsed = new ArrayList<Object>();
                ArrayList<Object> cardNumbersUnused = new ArrayList<Object>();
                for (ListKartu listKartu : getCardsByTeamIdAndCardID(id, cardSkill.getIdCard())) {
                    if (listKartu.getIsUsed()) {
                        cardNumbersUsed.add(
                                Map.of(
                                        "cardNumber", listKartu.getNoKartu()));
                    } else {
                        cardNumbersUnused.add(
                                Map.of(
                                        "cardNumber", listKartu.getNoKartu()));
                    }
                }
                if (cardNumbersUsed.size() > 0) {
                    listUsed.add(Map.of(
                            "cardSkill", Map.of(
                                "namaKartu", cardSkill.getNamaKartu()
                            ),
                            "total", cardNumbersUsed.size(),
                            "cardNumbers", cardNumbersUsed));
                }
                if (cardNumbersUnused.size() > 0) {
                    listUnused.add(Map.of(
                            "cardSkill", Map.of(
                                "namaKartu", cardSkill.getNamaKartu()
                            ),
                            "total", cardNumbersUnused.size(),
                            "cardNumbers", cardNumbersUnused));
                }
            }
        }
        if (listUsed.size() > 0 || listUnused.size() > 0) {
            return Map.of(
                    "usedCards", listUsed,
                    "availableCards", listUnused);
        }
        return null;
    }

    public boolean deleteListKartu(String id) {
        Optional<ListKartu> optionalListKartu = listKartuRepository.findById(id);
        if (optionalListKartu.isPresent()) {
            listKartuRepository.delete(optionalListKartu.get());
            return true;
        }
        return false;
    }

    public void reset() {
        for (ListKartu kartu : getAllListKartu()) {
            kartu.setIsUsed(false);
            kartu.setOwnedBy(null);
        }
    }
}