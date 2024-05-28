package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpertanyaan", nullable = false)
    private int idPertanyaan;

    @Column(name = "pertanyaan", columnDefinition = "TEXT", nullable = false)
    private String pertanyaan;

    @Column(name = "waktuinput", nullable = false)
    private LocalDateTime waktuInput;

    @ManyToOne
    @JoinColumn(name = "idplayer", referencedColumnName = "idplayer", nullable = false, foreignKey = @ForeignKey(name = "fk_idplayerqna"))
    private Players player;

    @Column(name = "jawaban", length = 5000, nullable = false)
    private String jawaban;

    @ManyToOne
    @JoinColumn(name = "idpanitia", referencedColumnName = "idpanitia", nullable = false, foreignKey = @ForeignKey(name = "fk_idpanitiaqna"))
    private Panitia panitia;


    public int getIdPertanyaan() {
        return this.idPertanyaan;
    }

    public void setIdPertanyaan(int idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public String getPertanyaan() {
        return this.pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public LocalDateTime getWaktuInput() {
        return this.waktuInput;
    }

    public void setWaktuInput(LocalDateTime waktuInput) {
        this.waktuInput = waktuInput;
    }

    public Players getPlayer() {
        return this.player;
    }

    public void setPlayer(Players player) {
        this.player = player;
    }

    public String getJawaban() {
        return this.jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public Panitia getPanitia() {
        return this.panitia;
    }

    public void setPanitia(Panitia panitia) {
        this.panitia = panitia;
    }
    
}

