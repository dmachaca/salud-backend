package com.pe.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_cita", schema = "salud")
public class TipoCita extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_cita")
    @SequenceGenerator(
            name = "seq_tipo_cita",
            sequenceName = "salud.seq_tipo_cita_pk",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "nombre", unique = true, length = 30)
    private String nombre;
}