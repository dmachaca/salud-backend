package com.pe.rest.controller;

import com.pe.model.dto.request.auth.LoginInputDto;
import com.pe.model.dto.request.auth.RefreshInputDto;
import com.pe.model.dto.response.GenericResponse;
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
}