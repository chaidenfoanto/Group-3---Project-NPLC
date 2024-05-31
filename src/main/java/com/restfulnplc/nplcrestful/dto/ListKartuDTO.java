package com.restfulnplc.nplcrestful.dto;

import com.restfulnplc.nplcrestful.model.CardSkill;
import com.restfulnplc.nplcrestful.model.Team;

public class ListKartuDTO {

    private String noKartu;
    private CardSkill cardSkill;
    private Team ownedBy;
    private boolean isUsed;

    public String getNoKartu() {
        return noKartu;
    }

    public void setNoKartu(String noKartu) {
        this.noKartu = noKartu;
    }

    public CardSkill getCardSkill() {
        return cardSkill;
    }

    public void setCardSkill(CardSkill cardSkill) {
        this.cardSkill = cardSkill;
    }

    public Team getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Team ownedBy) {
        this.ownedBy = ownedBy;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
