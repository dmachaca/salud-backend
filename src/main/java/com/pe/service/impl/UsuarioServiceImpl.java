package com.pe.service.impl;

import com.pe.model.dto.request.usuario.UsuarioInputDto;
import com.pe.model.dto.response.usuario.UsuarioOutputDto;
import com.pe.model.entity.Usuario;
import com.pe.repository.UsuarioRepository;
import com.pe.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioOutputDto registrarUsuario(UsuarioInputDto usuarioInputDto) {

        return null;
    }

}