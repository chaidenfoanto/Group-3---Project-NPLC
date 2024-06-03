package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.sql.Time;

@Entity
@Table(name = "boothgames")
public class Boothgames {

    @Id
    @Column(name = "idbooth", nullable = false)
    private String idBooth;

    @Column(name = "nama", length = 50, nullable = false)
    private String nama;

    @Column(name = "idpenjaga1", length = 14, nullable = false)
    private String idPenjaga1;

    @Column(name = "idpenjaga2", length = 14)
    private String idPenjaga2;

    @Lob
    @Column(name = "sopgames", nullable = false)
    private String sopGames;

    @Column(name = "lokasi", length = 5, nullable = false)
    private String lokasi;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipegame", nullable = false)
    private TipeGame Tipegame;

    @Column(name = "durasipermainan", nullable = false)
    private Time durasiPermainan;


    public String getIdBooth() {
        return this.idBooth;
    }

    public void setIdBooth(String idBooth) {
        this.idBooth = idBooth;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdPenjaga1() {
        return this.idPenjaga1;
    }

    public void setIdPenjaga1(String idPenjaga1) {
        this.idPenjaga1 = idPenjaga1;
    }

    public String getIdPenjaga2() {
        return this.idPenjaga2;
    }

    public void setIdPenjaga2(String idPenjaga2) {
        this.idPenjaga2 = idPenjaga2;
    }

    public String getSopGames() {
        return this.sopGames;
    }

    public void setSopGames(String sopGames) {
        this.sopGames = sopGames;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public TipeGame getTipegame() {
        return this.Tipegame;
    }

    public void setTipegame(TipeGame Tipegame) {
        this.Tipegame = Tipegame;
    }

    public Time getDurasiPermainan() {
        return this.durasiPermainan;
    }

    public void setDurasiPermainan(Time durasiPermainan) {
        this.durasiPermainan = durasiPermainan;
    }
    

    public enum TipeGame {
        Single, Duel
    }
}
