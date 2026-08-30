package com.example.SistemaReservaLaboratorioSalas.exceptions;

// usada quando uma referência (FK) informada no JSON de entrada
// (ex: id do usuario, id do recurso, id do status) não existe no banco
public class RegistroNaoEncontradoException extends RuntimeException
{
    public RegistroNaoEncontradoException(String mensagem)
    {
        super(mensagem);
    }
}
