package com.pe.rest.controller;

import com.pe.model.dto.request.cita.CrearCitaInputDto;
import com.pe.model.dto.response.GenericResponse;
import com.pe.service.ICitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/v1/api/citas")
public class CitaController extends BaseController {

    private final ICitaService citaService;

    @GetMapping("/mis-citas")
    public ResponseEntity<GenericResponse> listarMisCitas() {
        return handleRequest(() -> ok(citaService.listarMisCitas()));
    }

    @GetMapping("/tipos-atencion")
    public ResponseEntity<GenericResponse> listarTiposAtencion() {
        return handleRequest(() -> ok(citaService.listarTiposAtencion()));
    }

    @GetMapping("/pacientes")
    public ResponseEntity<GenericResponse> listarPacientes() {
        return handleRequest(() -> ok(citaService.listarPacientes()));
    }

    @GetMapping("/especialidades")
    public ResponseEntity<GenericResponse> listarEspecialidades() {
        return handleRequest(() -> ok(citaService.listarEspecialidades()));
    }

    @GetMapping("/centros-medicos")
    public ResponseEntity<GenericResponse> listarCentrosMedicos(
            @RequestParam(required = false) Long especialidadId) {
        return handleRequest(() -> ok(citaService.listarCentrosMedicos(especialidadId)));
    }

    @GetMapping("/medicos")
    public ResponseEntity<GenericResponse> listarMedicos(
            @RequestParam(required = false) Long especialidadId,
            @RequestParam(required = false) Long centroMedicoId) {
        return handleRequest(() -> ok(citaService.listarMedicos(especialidadId, centroMedicoId)));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<GenericResponse> listarDisponibilidad(
            @RequestParam(required = false) Long medicoId) {
        return handleRequest(() -> ok(citaService.listarDisponibilidad(medicoId)));
    }

    @PostMapping("/confirmar")
    public ResponseEntity<GenericResponse> confirmarCita(
            @RequestBody @Valid CrearCitaInputDto request) {
        return handleRequest(() -> ok(citaService.confirmarCita(request)));
    }

    private GenericResponse ok(Object data) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.TRUE);
        response.setData(data);
        return response;
    }
}

