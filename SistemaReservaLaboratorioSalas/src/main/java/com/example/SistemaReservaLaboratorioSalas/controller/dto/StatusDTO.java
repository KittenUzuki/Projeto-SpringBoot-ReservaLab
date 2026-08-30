package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import com.example.SistemaReservaLaboratorioSalas.model.Status;
import jakarta.validation.constraints.NotBlank;

public record StatusDTO(
        Integer id,
        @NotBlank(message = "Campo Obrigatório")
        String codigo,
        @NotBlank(message = "Campo Obrigatório")
        String nome)
{
    public Status mapearParaEntidade()
    {
        Status status = new Status();
        status.setCodigo(this.codigo);
        status.setNome(this.nome);
        return status;
    }

    public static StatusDTO deEntidade(Status status)
    {
        return new StatusDTO(status.getId(), status.getCodigo(), status.getNome());
    }
}
