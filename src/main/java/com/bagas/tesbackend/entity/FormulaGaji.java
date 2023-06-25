package com.bagas.tesbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "formula_gaji")
public class FormulaGaji {

    @Id
    private String id;

    @Column(name = "tarif_ptkp")
    private Long tarifPTKP;

    @Column(name = "tarif_pajak")
    private Long tarifPajak;

    private String country;
}
