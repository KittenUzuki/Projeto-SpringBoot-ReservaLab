package com.example.SistemaReservaLaboratorioSalas.repository;

import com.example.SistemaReservaLaboratorioSalas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer>
{
    Optional<Sala> findByCodigo(String codigo);
}
