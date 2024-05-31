package com.restfulnplc.nplcrestful.dto;

public class PlayersDTO {

    private String idPlayer;
    private String nama;
    private byte[] foto;
    private String idTeam;

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }
}
