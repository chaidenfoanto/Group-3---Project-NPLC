package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna")
public class Qna {

    @Id
    @Column(name = "idpertanyaan", nullable = false)
    private String idPertanyaan;

    @Column(name = "pertanyaan", columnDefinition = "TEXT", nullable = false)
    private String pertanyaan;

    @Column(name = "waktuinput", nullable = false)
    private LocalDateTime waktuInput;

    @ManyToOne
    @JoinColumn(name = "idteam", referencedColumnName = "idteam", nullable = false, foreignKey = @ForeignKey(name = "fk_idteamqna"))
    private Team team;

    @Column(name = "jawaban", columnDefinition = "TEXT", nullable = true)
    private String jawaban;

    @ManyToOne
    @JoinColumn(name = "idpanitia", referencedColumnName = "idpanitia", nullable = true, foreignKey = @ForeignKey(name = "fk_idpanitiaqna"))
    private Panitia panitia;

    public String getIdPertanyaan() {
        return this.idPertanyaan;
    }

    public void setIdPertanyaan(String idPertanyaan) {
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

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
