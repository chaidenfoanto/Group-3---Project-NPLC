package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
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

    @OneToOne
    @JoinColumn(name = "penjaga1", referencedColumnName = "idpanitia", foreignKey = @ForeignKey(name = "fk_penjaga1"), nullable = false)
    private Panitia idPenjaga1;

    @OneToOne
    @JoinColumn(name = "penjaga2", referencedColumnName = "idpanitia", foreignKey = @ForeignKey(name = "fk_penjaga2"), nullable = true)
    private Panitia idPenjaga2;

    @Lob
    @Column(name = "sopgames", nullable = false, columnDefinition = "TEXT")
    private String sopGames;

    @OneToOne
    @JoinColumn(name = "lokasi", referencedColumnName = "noruangan", foreignKey = @ForeignKey(name = "fk_lokasi"), nullable = false)
    private Lokasi lokasi;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipegame", nullable = false)
    private Tipegame tipegame;

    @Column(name = "durasipermainan", nullable = false)
    private Time durasiPermainan;

    @Lob
    @Column(name = "fotobooth", nullable = false)
    private byte[] fotobooth;


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

    public Panitia getIdPenjaga1() {
        return this.idPenjaga1;
    }

    public void setIdPenjaga1(Panitia idPenjaga1) {
        this.idPenjaga1 = idPenjaga1;
    }

    public Panitia getIdPenjaga2() {
        return this.idPenjaga2;
    }

    public void setIdPenjaga2(Panitia idPenjaga2) {
        this.idPenjaga2 = idPenjaga2;
    }

    public String getSopGames() {
        return this.sopGames;
    }

    public void setSopGames(String sopGames) {
        this.sopGames = sopGames;
    }

    public Lokasi getLokasi() {
        return this.lokasi;
    }

    public void setLokasi(Lokasi lokasi) {
        this.lokasi = lokasi;
    }

    public Tipegame getTipegame() {
        return this.tipegame;
    }

    public void setTipegame(Tipegame tipegame) {
        this.tipegame = tipegame;
    }

    public Time getDurasiPermainan() {
        return this.durasiPermainan;
    }

    public void setDurasiPermainan(Time durasiPermainan) {
        this.durasiPermainan = durasiPermainan;
    }
    
    public byte[] getFotoBooth() {
        return this.fotobooth;
    }

    public void setFotoBooth(byte[] fotobooth) {
        this.fotobooth = fotobooth;
    }
}
