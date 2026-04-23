package com.pe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_personal", schema = "salud")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoPersonal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_personal")
    @SequenceGenerator(
            name = "seq_tipo_personal",
            sequenceName = "salud.seq_tipo_personal_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    // =========================
    // CAMPOS
    // =========================

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

}
