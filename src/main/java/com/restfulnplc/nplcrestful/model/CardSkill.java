package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "cardskill")
public class CardSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcard", length = 11, nullable = false)
    private int idCard;

    @Column(name = "namakartu", length = 20, nullable = false)
    private String namaKartu;

    @Lob
    @Column(name = "rules", nullable = false)
    private String rules;

    @Column(name = "totalkartu", nullable = false)
    private int totalKartu;

    @Lob
    @Column(name = "gambarkartu")
    private byte[] gambarKartu;


    public int getIdCard() {
        return this.idCard;
    }

    public void setIdCard(int idCard) {
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
