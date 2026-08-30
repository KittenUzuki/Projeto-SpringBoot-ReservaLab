package com.example.SistemaReservaLaboratorioSalas.repository;

import com.example.SistemaReservaLaboratorioSalas.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer>
{
    Optional<Status> findByCodigo(String codigo);
    List<Status> findByNome(String nome);
    List<Status> findByCodigoAndNome(String codigo, String nome);
}
