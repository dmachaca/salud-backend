package com.pe.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "atencion", schema = "salud")
public class Atencion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atencion")
    @SequenceGenerator(
            name = "seq_atencion",
            sequenceName = "salud.seq_atencion_pk",
            allocationSize = 1
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(name = "diagnostico_cie10", length = 10)
    private String diagnosticoCie10;

    @Column(name = "diagnostico_descripcion")
    private String diagnosticoDescripcion;

    @Column(name = "plan_tratamiento")
    private String planTratamiento;
}