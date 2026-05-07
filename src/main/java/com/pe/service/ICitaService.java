package com.pe.service;

import com.pe.model.dto.request.cita.CitaFiltroInputDto;
import com.pe.model.dto.request.cita.CrearCitaInputDto;
import com.pe.model.dto.response.cita.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICitaService {
    List<CitaOutputDto> listarMisCitas();
    List<TipoAtencionOutputDto> listarTiposAtencion();
    List<PacienteCitaOutputDto> listarPacientes();
    List<EspecialidadOutputDto> listarEspecialidades();
    List<CentroMedicoOutputDto> listarCentrosMedicos(Long especialidadId);
    List<MedicoOutputDto> listarMedicos(Long especialidadId, Long centroMedicoId);
    List<DisponibilidadOutputDto> listarDisponibilidad(Long medicoId);
    CitaOutputDto confirmarCita(CrearCitaInputDto request);

    Page<CitasOutputDto> obtenerMisCitas(
            CitaFiltroInputDto filtroInputDto,
            Pageable pageable
    );
}
