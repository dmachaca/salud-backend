package com.pe.repository;

import com.pe.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByDni(String dni);
}
