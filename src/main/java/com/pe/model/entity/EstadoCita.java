package com.pe.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado_cita", schema = "salud")
public class EstadoCita extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estado_cita")
    @SequenceGenerator(
            name = "seq_estado_cita",
            sequenceName = "salud.seq_estado_cita_pk",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "nombre", unique = true, length = 20)
    private String nombre;
}