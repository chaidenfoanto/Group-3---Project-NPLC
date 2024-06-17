package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "players")
public class Players implements Serializable {

    @Id
    @Column(name = "idplayer", length = 20, nullable = false)
    private String idPlayer;

    @Column(name = "nama", length = 50, nullable = false)
    private String nama;

    @Column(name = "foto",  columnDefinition = "MEDIUMBLOB", nullable = false)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "idteam", referencedColumnName = "idteam", nullable = false)
    private Team team;


    public String getIdPlayer() {
        return this.idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
}

