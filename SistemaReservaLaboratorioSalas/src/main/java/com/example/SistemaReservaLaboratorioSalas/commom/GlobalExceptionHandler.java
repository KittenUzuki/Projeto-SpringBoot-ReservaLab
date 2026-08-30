package com.example.SistemaReservaLaboratorioSalas.commom;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.ErroCampo;
import com.example.SistemaReservaLaboratorioSalas.controller.dto.ErroResposta;
import com.example.SistemaReservaLaboratorioSalas.exceptions.OperacaoNaoPermitidaException;
import com.example.SistemaReservaLaboratorioSalas.exceptions.RegistroDuplicadoException;
import com.example.SistemaReservaLaboratorioSalas.exceptions.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// Centraliza a tradução de exceções em respostas HTTP padronizadas (ErroResposta),
// para não precisar repetir try/catch em cada endpoint dos controllers.
@RestControllerAdvice
public class GlobalExceptionHandler
{
    // erros de validação dos campos anotados com @Valid nos DTOs (ex: @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarCamposInvalidos(MethodArgumentNotValidException ex)
    {
        List<ErroCampo> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(ErroResposta.camposInvalidos(erros));
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErroResposta> tratarRegistroDuplicado(RegistroDuplicadoException ex)
    {
        var erro = ErroResposta.conflito(ex.getMessage());
        return ResponseEntity.status(erro.status()).body(erro);
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<ErroResposta> tratarOperacaoNaoPermitida(OperacaoNaoPermitidaException ex)
    {
        var erro = ErroResposta.respostaPadrao(ex.getMessage());
        return ResponseEntity.status(erro.status()).body(erro);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarRegistroNaoEncontrado(RegistroNaoEncontradoException ex)
    {
        var erro = ErroResposta.naoEncontrado(ex.getMessage());
        return ResponseEntity.status(erro.status()).body(erro);
    }

    // qualquer outra exceção não esperada vira um 500 com mensagem genérica,
    // evitando expor detalhes internos/stacktrace para quem consome a API
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroGenerico(Exception ex)
    {
        var erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado no servidor",
                List.of());
        return ResponseEntity.status(erro.status()).body(erro);
    }
}
