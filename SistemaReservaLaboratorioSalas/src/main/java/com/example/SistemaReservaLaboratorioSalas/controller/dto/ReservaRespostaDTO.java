package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import com.example.SistemaReservaLaboratorioSalas.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

// DTO de resposta para Reserva, já trazendo os dados descritivos do
// usuário, do recurso e do status (evita que quem consome a API precise
// fazer uma consulta extra para saber esses nomes).
public record ReservaRespostaDTO(
        Integer id,
        LocalDate dataInicial,
        LocalDate dataFinal,
        LocalTime horaInicial,
        LocalTime horaFinal,
        Integer idUsuario,
        String nomeUsuario,
        Integer idRecurso,
        String tipoRecurso,
        String codigoRecurso,
        String nomeRecurso,
        Integer idStatus,
        String codigoStatus,
        String nomeStatus)
{
    public static ReservaRespostaDTO deEntidade(Reserva reserva)
    {
        return new ReservaRespostaDTO(
                reserva.getId(),
                reserva.getDataInicial(),
                reserva.getDataFinal(),
                reserva.getHoraInicial(),
                reserva.getHoraFinal(),
                reserva.getUsuario().getId(),
                reserva.getUsuario().getNomeCompleto(),
                reserva.getRecurso().getId(),
                reserva.getRecurso().getTipo(),
                reserva.getRecurso().getCodigo(),
                reserva.getRecurso().getNome(),
                reserva.getStatus().getId(),
                reserva.getStatus().getCodigo(),
                reserva.getStatus().getNome());
    }
}
