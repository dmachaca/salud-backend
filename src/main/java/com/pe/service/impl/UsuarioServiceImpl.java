package com.pe.service.impl;

import com.pe.exception.UsuarioException;
import com.pe.model.dto.request.UsuarioRequest;
import com.pe.model.dto.request.usuario.UsuarioInputDto;
import com.pe.model.dto.response.usuario.UsuarioOutputDto;
import com.pe.model.entity.*;
import com.pe.model.mapper.PersonaMapper;
import com.pe.model.mapper.UsuarioMapper;
import com.pe.repository.*;
import com.pe.service.IUsuarioService;
import com.pe.transversal.enums.RolEnum;
import com.pe.utils.SaludMensajesExcepcion;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PacienteRepository pacienteRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PasswordEncoder passwordEncoder;

    private final UsuarioMapper usuarioMapper;
    private final PersonaMapper personaMapper;

    @Override
    @Transactional
    public UsuarioOutputDto registrarUsuario(UsuarioRequest request) {
        validarRegistro(request);
        Persona persona = crearPersona(request);
        Usuario usuario = crearUsuario(request, persona);
        asignarRol(usuario);
        crearPaciente(persona);
        return usuarioMapper.toOutputDto(usuario);
    }

    private void validarRegistro(UsuarioRequest request) {

        if (personaRepository.existsByDni(request.persona().dni())) {
            throw new UsuarioException(SaludMensajesExcepcion.MSG_DNI_YA_REGISTRADO);
        }

        if (usuarioRepository.existsByNombreUsuario(request.usuario().nombreUsuario())) {
            throw new UsuarioException(SaludMensajesExcepcion.MSG_USUARIO_YA_EXISTE);
        }

        if (usuarioRepository.existsByCorreo(request.usuario().correo())) {
            throw new UsuarioException("El correo ya está registrado");
        }
    }

    private Persona crearPersona(UsuarioRequest request) {

        Persona persona = personaMapper.toEntity(request.persona());
        persona.setActivo(true);

        return personaRepository.save(persona);
    }

    private Usuario crearUsuario(UsuarioRequest request, Persona persona) {

        Usuario usuario = usuarioMapper.toEntity(request.usuario());

        usuario.setPersona(persona);
        usuario.setClaveHash(passwordEncoder.encode(request.usuario().clave()));
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    private void asignarRol(Usuario usuario) {

        Rol rolPaciente = rolRepository.findById(RolEnum.PACIENTE.getCodigo())
                .orElseThrow(() -> new UsuarioException("Rol PACIENTE no encontrado"));

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rolPaciente);

        usuarioRolRepository.save(usuarioRol);
    }

    private void crearPaciente(Persona persona) {

        Paciente paciente = new Paciente();
        paciente.setPersona(persona);
        paciente.setActivo(true);

        pacienteRepository.save(paciente);
    }

    @Override
    public UsuarioOutputDto actualizarUsuario(UsuarioInputDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.id())
                .orElseThrow(() ->
                        new UsuarioException(SaludMensajesExcepcion.MSG_USUARIO_NO_ENCONTRADO));

        usuario.setNombreUsuario(dto.nombreUsuario());
        usuario.setCorreo(dto.correo());
        usuario.setActivo(dto.activo());

        if (dto.clave() != null && !dto.clave().isBlank()) {
            usuario.setClaveHash(passwordEncoder.encode(dto.clave()));
        }

        usuario = usuarioRepository.save(usuario);

        return usuarioMapper.toOutputDto(usuario);
    }

    @Override
    public UsuarioOutputDto obtenerUsuarioPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(usuarioMapper::toOutputDto)
                .orElseThrow(() ->
                        new UsuarioException(SaludMensajesExcepcion.MSG_USUARIO_NO_ENCONTRADO));
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
                .orElseThrow(() ->
                        new UsuarioException(SaludMensajesExcepcion.MSG_USUARIO_NO_ENCONTRADO));

        usuario.setActivo(false);

        usuarioRepository.save(usuario);
    }
}