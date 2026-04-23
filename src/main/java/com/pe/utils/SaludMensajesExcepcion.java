package com.pe.utils;

public class SaludMensajesExcepcion {
    private SaludMensajesExcepcion() {
        throw new IllegalStateException("Utility class");
    }


    public static final String MSG_USUARIO_NO_ENCONTRADO = "Usuario no encontrado.";
    public static final String MSG_DNI_YA_REGISTRADO = "El DNI ya se encuentra registrado.";
    public static final String MSG_USUARIO_YA_EXISTE = "El usuario ya existe.";

}
