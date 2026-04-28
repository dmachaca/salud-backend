package com.pe.rest.controller;

import com.pe.model.dto.request.auth.LoginInputDto;
import com.pe.model.dto.request.auth.RefreshInputDto;
import com.pe.model.dto.response.GenericResponse;
import com.pe.security.service.SecurityService;
import com.pe.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final IAuthService service;
    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(
            @RequestBody @Valid LoginInputDto request) {

        return handleRequest(() -> {
            var result = service.login(request);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setData(result);

            return response;
        });
    }

    @PostMapping("/refresh")
    public ResponseEntity<GenericResponse> refresh(
            @RequestBody @Valid RefreshInputDto request) {

        return handleRequest(() -> {

            var result = service.refresh(request);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setData(result);

            return response;
        });
    }

    @PostMapping("/logout")
    public ResponseEntity<GenericResponse> logout(
            @RequestBody @Valid RefreshInputDto request) {

        return handleRequest(() -> {

            service.logout(request.refreshToken());

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Logout exitoso");

            return response;
        });
    }

    @PostMapping("/logout-all")
    public ResponseEntity<GenericResponse> logoutAll() {

        return handleRequest(() -> {

            Long userId = securityService.obtenerIdUsuarioActual();

            service.logoutAll(userId);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Logout global exitoso");

            return response;
        });
    }
}