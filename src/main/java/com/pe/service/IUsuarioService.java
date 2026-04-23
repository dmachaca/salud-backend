package com.pe.service;

import com.pe.model.dto.request.usuario.UsuarioInputDto;
import com.pe.model.dto.response.usuario.UsuarioOutputDto;

import java.util.List;

public interface IUsuarioService {
    UsuarioOutputDto registrarUsuario(UsuarioInputDto usuarioInputDto);
    UsuarioOutputDto actualizarUsuario(UsuarioInputDto usuarioInputDto);
    UsuarioOutputDto obtenerUsuarioPorId(Long id);
    List<UsuarioOutputDto> listarUsuarios();
    void desactivarUsuario(Long id);
}