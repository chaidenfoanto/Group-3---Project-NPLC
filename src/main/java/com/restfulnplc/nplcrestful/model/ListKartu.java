package com.restfulnplc.nplcrestful.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "listkartu")
public class ListKartu implements Serializable {

    @Id
    @Column(name = "nokartu", length = 4, nullable = false)
    private String noKartu;

    @ManyToOne
    @JoinColumn(name = "idcard", referencedColumnName = "idcard", foreignKey = @ForeignKey(name = "fk_idcard"), nullable = false)
    private CardSkill cardSkill;

    @ManyToOne
    @JoinColumn(name = "ownedBy", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_ownedBy"))
    private Team ownedBy;

    @Column(name = "isUsed", nullable = false)
    private boolean isUsed;


    public String getNoKartu() {
        return this.noKartu;
    }

    public void setNoKartu(String noKartu) {
        this.noKartu = noKartu;
    }

    public CardSkill getCardSkill() {
        return this.cardSkill;
    }

    public void setCardSkill(CardSkill cardSkill) {
        this.cardSkill = cardSkill;
    }

    public Team getOwnedBy() {
        return this.ownedBy;
    }

    public void setOwnedBy(Team ownedBy) {
        this.ownedBy = ownedBy;
    }

    public boolean isIsUsed() {
        return this.isUsed;
    }

    public boolean getIsUsed() {
        return this.isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    
}
