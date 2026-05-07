package com.pe.service.impl;

import com.pe.exception.NotFoundException;
import com.pe.model.dto.request.cita.CitaFiltroInputDto;
import com.pe.model.dto.request.cita.CrearCitaInputDto;
import com.pe.model.dto.response.cita.*;
import com.pe.repository.CitaRepository;
import com.pe.security.service.SecurityService;
import com.pe.service.ICitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements ICitaService {

    private final CitaRepository citaRepository;
    private final SecurityService securityService;

    private final AtomicLong citaSequence = new AtomicLong(1);
    private final List<CitaOutputDto> citasConfirmadas = new ArrayList<>();

    private final List<TipoAtencionOutputDto> tiposAtencion = List.of(
            new TipoAtencionOutputDto(1L, "PRESENCIAL", "Cita presencial", "Agenda una cita con un especialista"),
            new TipoAtencionOutputDto(2L, "VIRTUAL", "Cita virtual", "Agenda una cita con un especialista"),
            new TipoAtencionOutputDto(3L, "CHEQUEO", "Chequeo médico", "Agenda una cita para tu chequeo")
    );

    private final List<PacienteCitaOutputDto> pacientes = List.of(
            new PacienteCitaOutputDto(1L, "Densy Machaca Chipana"),
            new PacienteCitaOutputDto(2L, "Gabriela Mirian Mamani Mamani"),
            new PacienteCitaOutputDto(3L, "Emma Anthonella Machaca Mamani")
    );

    private final List<EspecialidadOutputDto> especialidades = List.of(
            new EspecialidadOutputDto(1L, "Oftalmología"),
            new EspecialidadOutputDto(2L, "Medicina interna"),
            new EspecialidadOutputDto(3L, "Cardiología"),
            new EspecialidadOutputDto(4L, "Dermatología"),
            new EspecialidadOutputDto(5L, "Pediatría")
    );

    private final List<CentroMedicoOutputDto> centros = List.of(
            new CentroMedicoOutputDto(1L, "Clínica El Golf", "Av. Aurelio Miró Quesada 1030 - San Isidro"),
            new CentroMedicoOutputDto(2L, "Centro Clínico Cajamarca", "Jr. Cruz de Piedra 595 - Cajamarca"),
            new CentroMedicoOutputDto(3L, "Centro Clínico Talara - Piura", "Av. Grau 1420 - Talara"),
            new CentroMedicoOutputDto(4L, "Clínica San Borja", "Av. Guardia Civil 337 - San Borja"),
            new CentroMedicoOutputDto(5L, "Centro Clínico La Molina", "Av. Raúl Ferrero 1256 - La Molina")
    );

    private final List<MedicoOutputDto> medicos = List.of(
            new MedicoOutputDto(1L, "Velasquez Lopez, Julio Cesar", "Oftalmología", "04/05/2026 - 08:40hs"),
            new MedicoOutputDto(2L, "Fontenla Serna, David Ivan", "Oftalmología", "04/05/2026 - 15:00hs"),
            new MedicoOutputDto(3L, "Quiroz Franckowiak, Annette Marie", "Oftalmología", "05/05/2026 - 09:00hs"),
            new MedicoOutputDto(4L, "Salazar Torres, Milagros", "Medicina interna", "06/05/2026 - 10:20hs"),
            new MedicoOutputDto(5L, "Rojas Mendoza, Carlos Alberto", "Cardiología", "07/05/2026 - 11:30hs")
    );

    private final List<DisponibilidadOutputDto> disponibilidades = List.of(
            new DisponibilidadOutputDto(1L, "Lunes 04/05/2026", "08:40hs", "Lunes 04/05/2026 - 08:40hs"),
            new DisponibilidadOutputDto(2L, "Lunes 04/05/2026", "15:00hs", "Lunes 04/05/2026 - 15:00hs"),
            new DisponibilidadOutputDto(3L, "Martes 05/05/2026", "09:00hs", "Martes 05/05/2026 - 09:00hs"),
            new DisponibilidadOutputDto(4L, "Miércoles 06/05/2026", "10:20hs", "Miércoles 06/05/2026 - 10:20hs"),
            new DisponibilidadOutputDto(5L, "Jueves 07/05/2026", "11:30hs", "Jueves 07/05/2026 - 11:30hs")
    );

    @Override
    public List<CitaOutputDto> listarMisCitas() {
        return citasConfirmadas;
    }

    @Override
    public List<TipoAtencionOutputDto> listarTiposAtencion() {
        return tiposAtencion;
    }

    @Override
    public List<PacienteCitaOutputDto> listarPacientes() {
        return pacientes;
    }

    @Override
    public List<EspecialidadOutputDto> listarEspecialidades() {
        return especialidades;
    }

    @Override
    public List<CentroMedicoOutputDto> listarCentrosMedicos(Long especialidadId) {
        return centros;
    }

    @Override
    public List<MedicoOutputDto> listarMedicos(Long especialidadId, Long centroMedicoId) {
        if (especialidadId == null) {
            return medicos;
        }

        String especialidad = buscarEspecialidad(especialidadId).nombre();
        List<MedicoOutputDto> filtrados = medicos.stream()
                .filter(medico -> medico.especialidad().equalsIgnoreCase(especialidad))
                .toList();

        return filtrados.isEmpty() ? medicos : filtrados;
    }

    @Override
    public List<DisponibilidadOutputDto> listarDisponibilidad(Long medicoId) {
        return disponibilidades;
    }

    @Override
    public CitaOutputDto confirmarCita(CrearCitaInputDto request) {
        TipoAtencionOutputDto tipoAtencion = buscarTipoAtencion(request.tipoAtencionId());
        PacienteCitaOutputDto paciente = buscarPaciente(request.pacienteId());
        EspecialidadOutputDto especialidad = buscarEspecialidad(request.especialidadId());
        CentroMedicoOutputDto centro = buscarCentro(request.centroMedicoId());
        MedicoOutputDto medico = buscarMedico(request.medicoId());
        DisponibilidadOutputDto disponibilidad = buscarDisponibilidad(request.disponibilidadId());

        CitaOutputDto cita = new CitaOutputDto(
                citaSequence.getAndIncrement(),
                tipoAtencion.nombre(),
                disponibilidad.fecha(),
                disponibilidad.hora(),
                centro.nombre(),
                centro.direccion(),
                especialidad.nombre(),
                medico.nombreCompleto(),
                paciente.nombreCompleto(),
                "CONFIRMADA"
        );

        citasConfirmadas.add(cita);
        return cita;
    }

    private TipoAtencionOutputDto buscarTipoAtencion(Long id) {
        return tiposAtencion.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Tipo de atención no encontrado"));
    }

    private PacienteCitaOutputDto buscarPaciente(Long id) {
        return pacientes.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));
    }

    private EspecialidadOutputDto buscarEspecialidad(Long id) {
        return especialidades.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Especialidad no encontrada"));
    }

    private CentroMedicoOutputDto buscarCentro(Long id) {
        return centros.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Centro médico no encontrado"));
    }

    private MedicoOutputDto buscarMedico(Long id) {
        return medicos.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Médico no encontrado"));
    }

    private DisponibilidadOutputDto buscarDisponibilidad(Long id) {
        return disponibilidades.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Disponibilidad no encontrada"));
    }

    @Override
    public Page<CitasOutputDto> obtenerMisCitas(
            CitaFiltroInputDto filtroInputDto,
            Pageable pageable
    ) {

        Long usuarioId = securityService.obtenerIdUsuarioActual();

        return citaRepository.obtenerMisCitas(
                usuarioId,
                filtroInputDto,
                pageable
        );
    }
}
