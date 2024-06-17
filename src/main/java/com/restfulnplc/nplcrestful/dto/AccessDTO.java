package com.restfulnplc.nplcrestful.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccessDTO {
    private String idUser;
    private String role;
    private boolean isAdmin;
    private String divisi;

    public AccessDTO(String idUser, String role, boolean isAdmin, String divisi) {
        this.idUser = idUser;
        this.role = role;
        this.isAdmin = isAdmin;
        this.divisi = divisi;
    }
}