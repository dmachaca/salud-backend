package com.pe.service.impl;

import com.pe.exception.UsuarioException;
import com.pe.model.dto.request.UsuarioRequest;
import com.pe.model.dto.request.usuario.UsuarioInputDto;
import com.pe.model.dto.response.usuario.UsuarioOutputDto;
import com.pe.model.entity.Persona;
import com.pe.model.entity.Usuario;
import com.pe.model.mapper.PersonaMapper;
import com.pe.model.mapper.UsuarioMapper;
import com.pe.repository.PersonaRepository;
import com.pe.repository.UsuarioRepository;
import com.pe.service.IUsuarioService;
import com.pe.utils.SaludMensajesExcepcion;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
 @RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

        private final UsuarioRepository usuarioRepository;
        private final PersonaRepository personaRepository;
        private final PasswordEncoder passwordEncoder;
        private final UsuarioMapper usuarioMapper;
        private final PersonaMapper personaMapper;

        @Override
        public UsuarioOutputDto registrarUsuario(UsuarioRequest request) {

            // =========================
            // VALIDACIONES (PRO)
            // =========================
            if (personaRepository.existsByDni(request.persona().dni())) {
                throw new UsuarioException(SaludMensajesExcepcion.MSG_DNI_YA_REGISTRADO);
            }

            if (usuarioRepository.existsByNombreUsuario(request.usuario().nombreUsuario())) {
                throw new UsuarioException(SaludMensajesExcepcion.MSG_USUARIO_YA_EXISTE);
            }

            // =========================
            // 1. GUARDAR PERSONA
            // =========================
            Persona persona = personaMapper.toEntity(request.persona());
            persona.setActivo(true);

            persona = personaRepository.save(persona);

            // =========================
            // 2. CREAR USUARIO
            // =========================
            Usuario usuario = usuarioMapper.toEntity(request.usuario());

            usuario.setPersona(persona); //relación correcta
            usuario.setClaveHash(passwordEncoder.encode(request.usuario().clave()));
            usuario.setActivo(true);

            usuario = usuarioRepository.save(usuario);

            // =========================
            // 3. RESPONSE
            // =========================
            return usuarioMapper.toOutputDto(usuario);
        }
    @Override
    public UsuarioOutputDto actualizarUsuario(UsuarioInputDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException(SaludMensajesExcepcion.MSG_USUARIO_NO_ENCONTRADO));

        usuario.setNombreUsuario(dto.nombreUsuario());
        usuario.setCorreo(dto.correo());
        usuario.setActivo(dto.activo());

        if (dto.clave() != null && !dto.clave().isBlank()) {
            usuario.setClaveHash(passwordEncoder.encode(dto.clave()));
        }

        usuarioRepository.save(usuario);

        return usuarioMapper.toOutputDto(usuario);
    }

    @Override
    public UsuarioOutputDto obtenerUsuarioPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(usuarioMapper::toOutputDto)
                .orElseThrow(() -> new RuntimeException(SaludMensajesExcepcion.MSG_USUARIO_NO_ENCONTRADO));
    }

    @Override
    public List<UsuarioOutputDto> listarUsuarios() {

        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toOutputDto)
                .toList();
    }

    @Override
    public void desactivarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(SaludMensajesExcepcion.MSG_USUARIO_NO_ENCONTRADO));

        usuario.setActivo(false);

        usuarioRepository.save(usuario);
    }
}

