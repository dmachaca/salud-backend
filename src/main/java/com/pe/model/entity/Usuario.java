package com.pe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "salud",
        uniqueConstraints = {
                @UniqueConstraint(name = "usuario_nombre_usuario_key", columnNames = "nombre_usuario"),
                @UniqueConstraint(name = "usuario_correo_key", columnNames = "correo"),
                @UniqueConstraint(name = "usuario_persona_id_key", columnNames = "persona_id")
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(
            name = "seq_usuario",
            sequenceName = "salud.seq_usuario_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    // =========================
    // RELACIÓN CON PERSONA
    // =========================
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", foreignKey = @ForeignKey(name = "fk_usuario_persona"))
    private Persona persona;

    // =========================
    // DATOS DE LOGIN
    // =========================
    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;

    @Column(name = "clave_hash", nullable = false)
    private String claveHash;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles;

}

