package com.restfulnplc.nplcrestful.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "duelmatch")
public class DuelMatch {

    @Id
    @Column(name = "nomatch", length = 15, nullable = false)
    private String noMatch;

    @ManyToOne
    @JoinColumn(name = "idteam1", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_idteam1"), nullable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "idteam2", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_idteam2"), nullable = false)
    private Team team2;

    @Column(name = "waktumulai")
    private LocalTime waktuMulai;

    @Column(name = "waktuselesai")
    private LocalTime waktuSelesai;

    @ManyToOne
    @JoinColumn(name = "inputby", referencedColumnName = "idpanitia", foreignKey = @ForeignKey(name = "fk_inputby"), nullable = false)
    private Panitia inputBy;

    @ManyToOne
    @JoinColumn(name = "timmenang", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_timmenang"), nullable = false)
    private Team timMenang;

    @ManyToOne
    @JoinColumn(name = "idbooth", referencedColumnName = "idbooth", foreignKey = @ForeignKey(name = "fk_idboothduel"), nullable = false)
    private Boothgames boothGames;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MatchStatus matchStatus;
}