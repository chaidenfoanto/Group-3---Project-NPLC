package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.restfulnplc.nplcrestful.enums.Role;

@Entity
@Table(name = "login_session")
public class Login {
    @Column(name = "idUser")
    private String idUser;

    @Id
    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Login(){}

    public Login(String idUser, String token, Role role) {
        this.idUser = idUser;
        this.token = token;
        this.role = role;
    }

    public String getIdUser() {
        return this.idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
