package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import com.example.SistemaReservaLaboratorioSalas.model.Recurso;

// DTO de resposta usado na consulta unificada de recursos (sala + laboratório)
public record RecursoDTO(
        Integer id,
        String tipo,
        String codigo,
        String nome,
        Integer capacidade,
        String localizacao)
{
    public static RecursoDTO deEntidade(Recurso recurso)
    {
        return new RecursoDTO(
                recurso.getId(),
                recurso.getTipo(),
                recurso.getCodigo(),
                recurso.getNome(),
                recurso.getCapacidade(),
                recurso.getLocalizacao());
    }
}
