package com.pe.security.service;

import com.pe.security.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public Long obtenerIdUsuarioActual() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal principal)) {
            throw new RuntimeException("Usuario no autenticado");
        }

        return principal.getUsuario().getId();
    }

    public UserPrincipal obtenerUsuarioActual() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal principal)) {
            throw new RuntimeException("Usuario no autenticado");
        }

        return principal;
    }

    public String obtenerUsername() {
        return obtenerUsuarioActual().getUsername();
    }

    public boolean estaAutenticado() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        return auth != null
                && auth.isAuthenticated()
                && auth.getPrincipal() instanceof UserPrincipal;
    }
}