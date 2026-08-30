package com.example.SistemaReservaLaboratorioSalas.repository;

import com.example.SistemaReservaLaboratorioSalas.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repositório da classe base Recurso: usado para consultas unificadas
// (sala + laboratório), verificação de código único entre os dois tipos,
// e para resolver o "recurso" de uma Reserva a partir do id informado.
public interface RecursoRepository extends JpaRepository<Recurso, Integer>
{
    Optional<Recurso> findByCodigo(String codigo);

    List<Recurso> findByNomeContainingIgnoreCase(String nome);
    List<Recurso> findByCapacidade(Integer capacidade);
    List<Recurso> findByLocalizacaoContainingIgnoreCase(String localizacao);

    List<Recurso> findByNomeContainingIgnoreCaseAndCapacidadeAndLocalizacaoContainingIgnoreCase(
            String nome, Integer capacidade, String localizacao);

    List<Recurso> findByNomeContainingIgnoreCaseAndCapacidade(String nome, Integer capacidade);
    List<Recurso> findByNomeContainingIgnoreCaseAndLocalizacaoContainingIgnoreCase(String nome, String localizacao);
    List<Recurso> findByCapacidadeAndLocalizacaoContainingIgnoreCase(Integer capacidade, String localizacao);
}
