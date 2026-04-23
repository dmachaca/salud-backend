package com.pe.utils;

public class ErrorMensajes {

    private ErrorMensajes() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OK = "La operación se ejecutó correctamente.";
    public static final String ERROR = "Ocurrió un error durante la operación.";
}
