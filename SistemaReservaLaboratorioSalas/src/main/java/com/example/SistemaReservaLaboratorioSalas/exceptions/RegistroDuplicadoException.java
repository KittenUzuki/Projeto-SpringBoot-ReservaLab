package com.example.SistemaReservaLaboratorioSalas.exceptions;

public class RegistroDuplicadoException extends RuntimeException
{
    public RegistroDuplicadoException(String mensagem)
    {
        super(mensagem);
    }
}
