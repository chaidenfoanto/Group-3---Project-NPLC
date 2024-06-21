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

@Setter
@Getter
@Entity
@Table(name = "singlematch")
public class Singlematch {

    @Id
    @Column(name = "nomatch", nullable = false)
    private String noMatch;

    @ManyToOne
    @JoinColumn(name = "idteam", referencedColumnName = "idteam", nullable = false, foreignKey = @ForeignKey(name = "fk_idteamsingle"))
    private Team team;

    @Column(name = "waktumulai")
    private LocalTime waktuMulai;

    @Column(name = "waktuselesai")
    private LocalTime waktuSelesai;

    @ManyToOne
    @JoinColumn(name = "nokartu", referencedColumnName = "nokartu", foreignKey = @ForeignKey(name = "fk_nokartu"))
    private ListKartu listKartu;

    @ManyToOne
    @JoinColumn(name = "inputby", referencedColumnName = "idpanitia", foreignKey = @ForeignKey(name = "fk_inputby"), nullable = false)
    private Panitia inputBy;

    @Column(name = "totalpoin", nullable = false)
    private int totalPoin;

    @Column(name = "totalBintang", nullable = false)
    private int totalBintang;

    @ManyToOne
    @JoinColumn(name = "idbooth", referencedColumnName = "idbooth", nullable = false, foreignKey = @ForeignKey(name = "fk_idboothsingle"))
    private Boothgames boothGames;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MatchStatus matchStatus;
}
