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

    @Column(name = "totalkartu", nullable = false)
    private int totalKartu;

    @Column(name = "gambarkartu",  columnDefinition = "BLOB", nullable = false)
    private byte[] gambarKartu;


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

    public int getTotalKartu() {
        return this.totalKartu;
    }

    public void setTotalKartu(int totalKartu) {
        this.totalKartu = totalKartu;
    }

    public byte[] getGambarKartu() {
        return this.gambarKartu;
    }

    public void setGambarKartu(byte[] gambarKartu) {
        this.gambarKartu = gambarKartu;
    }
    
}
