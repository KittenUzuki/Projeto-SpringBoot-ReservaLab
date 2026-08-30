package com.example.SistemaReservaLaboratorioSalas.repository;

import com.example.SistemaReservaLaboratorioSalas.model.Recurso;
import com.example.SistemaReservaLaboratorioSalas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>, JpaSpecificationExecutor<Reserva>
{
    boolean existsByRecurso(Recurso recurso);

    boolean existsByUsuario(com.example.SistemaReservaLaboratorioSalas.model.Usuario usuario);

    // Verifica se já existe alguma reserva (com status LIVRE excluído, ou seja,
    // OCUPADO/RESERVADO/BLOQUEADO) que conflite em data e horário para o mesmo
    // recurso. Usado para impedir reservas duplicadas/sobrepostas.
    // ?1 = recurso, ?2 = dataInicial nova, ?3 = dataFinal nova,
    // ?4 = horaInicial nova, ?5 = horaFinal nova, ?6 = id da reserva a ignorar (update)
    @Query("select r from Reserva r " +
            "where r.recurso = ?1 " +
            "and r.status.codigo <> 'LIVRE' " +
            "and r.dataInicial <= ?3 and r.dataFinal >= ?2 " +
            "and r.horaInicial < ?5 and r.horaFinal > ?4 " +
            "and (?6 is null or r.id <> ?6)")
    List<Reserva> buscarConflitos(Recurso recurso,
                                   LocalDate dataInicial,
                                   LocalDate dataFinal,
                                   LocalTime horaInicial,
                                   LocalTime horaFinal,
                                   Integer idReservaIgnorar);
}
