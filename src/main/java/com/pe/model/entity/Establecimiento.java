package com.pe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "establecimiento", schema = "salud")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Establecimiento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_establecimiento")
    @SequenceGenerator(
            name = "seq_establecimiento",
            sequenceName = "salud.seq_establecimiento_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    // =========================
    // CAMPOS PROPIOS
    // =========================

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    // =========================
    // RELACIONES
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_establecimiento_id")
    private TipoEstablecimiento tipoEstablecimiento;

    // =========================
    // AUDITORÍA (FK)
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por_id")
    private Usuario creadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actualizado_por_id")
    private Usuario actualizadoPor;
}