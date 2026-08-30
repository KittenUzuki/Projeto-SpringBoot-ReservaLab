package com.example.SistemaReservaLaboratorioSalas.repository;

import com.example.SistemaReservaLaboratorioSalas.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer>
{
    Optional<Laboratorio> findByCodigo(String codigo);
}
