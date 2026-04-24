package com.pe.model.entity;

import com.pe.config.AuditoriaContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private OffsetDateTime fechaActualizacion;

    @Column(name = "creado_por_id", updatable = false)
    private Long creadoPorId;

    @Column(name = "actualizado_por_id")
    private Long actualizadoPorId;

    private static final ZoneOffset ZONA = ZoneOffset.UTC;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }

        if (fechaCreacion == null) {
            fechaCreacion = OffsetDateTime.now(ZONA);
        }

        //  si usas contexto de usuario
        Long userId = AuditoriaContext.getCurrentUserId();
        if (userId != null) {
            creadoPorId = userId;
        }
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = OffsetDateTime.now(ZONA);

        Long userId = AuditoriaContext.getCurrentUserId();
        if (userId != null) {
            actualizadoPorId = userId;
        }
    }
}