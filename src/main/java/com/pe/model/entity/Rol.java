package com.pe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rol", schema = "salud",
        uniqueConstraints = {
                @UniqueConstraint(name = "rol_nombre_key", columnNames = "nombre")
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rol extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rol")
    @SequenceGenerator(
            name = "seq_rol",
            sequenceName = "salud.seq_rol_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo = true;

}