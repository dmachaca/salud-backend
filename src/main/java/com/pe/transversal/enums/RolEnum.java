package com.pe.transversal.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RolEnum {

    ADMIN(1, "Administrador del sistema"),
    PACIENTE(2, "Usuario paciente"),
    DOCTOR(3, "Médico del sistema"),
    ENFERMERO(4, "Personal de enfermería");

    private final int codigo;
    private final String descripcion;

    RolEnum(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public static RolEnum findByCodigo(int codigo) {
        return Arrays.stream(values())
                .filter(r -> r.codigo == codigo)
                .findFirst()
                .orElseThrow();
    }
}