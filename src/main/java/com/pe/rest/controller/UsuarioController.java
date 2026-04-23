package com.pe.rest.controller;

import com.pe.model.dto.request.UsuarioRequest;
import com.pe.model.dto.request.usuario.UsuarioInputDto;
import com.pe.model.dto.response.GenericResponse;
import com.pe.model.dto.response.usuario.UsuarioOutputDto;
import com.pe.service.IUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/usuario")
public class UsuarioController extends BaseController {

    private final IUsuarioService usuarioService;
    //private final JWTTokenProvider jwtTokenProvider;

    // =========================
    // REGISTRAR USUARIO
    // =========================
    @PostMapping("/registrar")
    public ResponseEntity<GenericResponse> registrarUsuario(
            @RequestBody @Valid UsuarioRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {

        return handleRequest(() -> {

            UsuarioOutputDto result = usuarioService.registrarUsuario(request);

            GenericResponse response = new GenericResponse();
            response.setSuccess(Boolean.TRUE);
            response.setData(result);

            return response;
        });
    }

    
}