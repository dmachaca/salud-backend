package com.pe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personal_salud", schema = "salud")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonalSalud extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_personal_salud")
    @SequenceGenerator(
            name = "seq_personal_salud",
            sequenceName = "salud.seq_personal_salud_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    // =========================
    // RELACIONES
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    @JsonIgnore
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private Establecimiento establecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_personal_id")
    private TipoPersonal tipoPersonal;

    // =========================
    // CAMPOS PROPIOS
    // =========================

    @Column(name = "colegiatura", length = 50, unique = true)
    private String colegiatura;

}