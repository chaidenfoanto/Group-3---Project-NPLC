package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cardskill")
public class CardSkill {

    @Id
    @Column(name = "idcard", length = 11, nullable = false)
    private String idCard;

    @Column(name = "namakartu", length = 20, nullable = false, unique = true)
    private String namaKartu;

    @Column(name = "rules", columnDefinition = "TINYTEXT",nullable = false)
    private String rules;

    @Column(name = "gambarkartu",  columnDefinition = "MEDIUMBLOB", nullable = false)
    private String gambarKartu;


    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNamaKartu() {
        return this.namaKartu;
    }

    public void setNamaKartu(String namaKartu) {
        this.namaKartu = namaKartu;
    }

    public String getRules() {
        return this.rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getGambarKartu() {
        return this.gambarKartu;
    }

    public void setGambarKartu(String gambarKartu) {
        this.gambarKartu = gambarKartu;
    }
    
}
