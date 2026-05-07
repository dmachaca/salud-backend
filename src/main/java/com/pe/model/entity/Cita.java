package com.pe.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "cita", schema = "salud")
public class Cita extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cita")
    @SequenceGenerator(
            name = "seq_cita",
            sequenceName = "salud.seq_cita_pk",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id")
    private PersonalSalud personal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private Establecimiento establecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_cita_id")
    private EstadoCita estadoCita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_cita_id")
    private TipoCita tipoCita;

    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;
}