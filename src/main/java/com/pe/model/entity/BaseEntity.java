package com.pe.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private OffsetDateTime fechaActualizacion;

    @Column(name = "creado_por_id")
    private Long creadoPorId;

    @Column(name = "actualizado_por_id")
    private Long actualizadoPorId;

    @PrePersist
    public void prePersist() {
        if (activo == null) activo = true;
        if (fechaCreacion == null) fechaCreacion = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = OffsetDateTime.now();
    }
}