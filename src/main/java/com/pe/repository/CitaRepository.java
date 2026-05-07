package com.pe.repository;

import com.pe.model.dto.request.cita.CitaFiltroInputDto;
import com.pe.model.dto.response.cita.CitasOutputDto;
import com.pe.model.entity.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("""
        SELECT new com.pe.model.dto.response.cita.CitasOutputDto(
            c.id,
            CONCAT( per.nombres, ' ', per.apellidoPaterno, ' ', COALESCE(per.apellidoMaterno, '')
            ),
            CONCAT(
                medPer.nombres,
                ' ',
                medPer.apellidoPaterno
            ),
            est.nombre,
            ec.nombre,
            tc.nombre,
            c.fechaHora
        )
        FROM Cita c
        INNER JOIN c.paciente pa
        INNER JOIN pa.persona per
        INNER JOIN c.personal ps
        INNER JOIN ps.persona medPer
        INNER JOIN c.establecimiento est
        INNER JOIN c.estadoCita ec
        INNER JOIN c.tipoCita tc
        INNER JOIN Usuario u
            ON u.persona.id = per.id
        WHERE u.id = :usuarioId
        AND c.activo = true
        AND (
            :#{#filtro.paciente} IS NULL
            OR UPPER(
                CONCAT(
                    per.nombres,
                    ' ',
                    per.apellidoPaterno,
                    ' ',
                    COALESCE(per.apellidoMaterno, '')
                )
            ) LIKE UPPER(CONCAT('%', :#{#filtro.paciente}, '%'))
        )
        AND (
            :#{#filtro.estadoCitaId} IS NULL
            OR ec.id = :#{#filtro.estadoCitaId}
        )
        AND (
            :#{#filtro.tipoCitaId} IS NULL
            OR tc.id = :#{#filtro.tipoCitaId}
        )
        AND (
            :#{#filtro.establecimientoId} IS NULL
            OR est.id = :#{#filtro.establecimientoId}
        )
        AND (
            :#{#filtro.fechaInicio} IS NULL
            OR FUNCTION('DATE', c.fechaHora) >= :#{#filtro.fechaInicio}
        )
        AND (
            :#{#filtro.fechaFin} IS NULL
            OR FUNCTION('DATE', c.fechaHora) <= :#{#filtro.fechaFin}
        )
        ORDER BY c.fechaHora DESC
    """)
    Page<CitasOutputDto> obtenerMisCitas(
            Long usuarioId,
            CitaFiltroInputDto filtro,
            Pageable pageable
    );
}