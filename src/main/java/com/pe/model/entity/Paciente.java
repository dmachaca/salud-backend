package com.pe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paciente", schema = "salud",
        uniqueConstraints = {
                @UniqueConstraint(name = "paciente_persona_id_key", columnNames = "persona_id")
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Paciente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paciente")
    @SequenceGenerator(
            name = "seq_paciente",
            sequenceName = "salud.seq_paciente_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    // =========================
    // RELACIÓN CON PERSONA
    // =========================
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "persona_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_paciente_persona")
    )
    private Persona persona;

}