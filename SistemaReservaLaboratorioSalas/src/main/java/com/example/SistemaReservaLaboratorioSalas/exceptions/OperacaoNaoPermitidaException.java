package com.example.SistemaReservaLaboratorioSalas.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException
{
    public OperacaoNaoPermitidaException(String mensagem)
    {
        super(mensagem);
    }
}
