package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lokasi")
public class Lokasi {

    @Id
    @Column(name = "noruangan", length = 10, nullable = false)
    private String noRuangan;

    @Column(name = "lantai")
    private int lantai;


    public String getNoRuangan() {
        return this.noRuangan;
    }

    public void setNoRuangan(String noRuangan) {
        this.noRuangan = noRuangan;
    }

    public int getLantai() {
        return this.lantai;
    }

    public void setLantai(int lantai) {
        this.lantai = lantai;
    }
    
}
