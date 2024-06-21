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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "boothgames")
public class Boothgames {

    @Id
    @Column(name = "idbooth", nullable = false)
    private String idBooth;

    @Column(name = "nama", length = 50, nullable = false, unique = true)
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

    @Column(name = "durasipermainan", nullable = false, columnDefinition = "MEDIUMINT")
    private Long durasiPermainan;

    @Column(name = "fotobooth", columnDefinition = "MEDIUMBLOB", nullable = false)
    private String fotoBooth;
}
