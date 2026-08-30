package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import com.example.SistemaReservaLaboratorioSalas.model.Usuario;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioDTO(
        Integer id,
        @NotBlank(message = "Campo Obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
        String cpf,
        @NotBlank(message = "Campo Obrigatório")
        String nomeCompleto,
        @NotNull(message = "Campo Obrigatório")
        @Past(message = "Data de aniversário deve ser uma data passada")
        LocalDate dataAniversario,
        @NotBlank(message = "Campo Obrigatório")
        String celular,
        @NotBlank(message = "Campo Obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "Campo Obrigatório")
        String login,
        @NotBlank(message = "Campo Obrigatório")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha)
{
    public Usuario mapearParaEntidade()
    {
        Usuario usuario = new Usuario();
        usuario.setCpf(this.cpf);
        usuario.setNomeCompleto(this.nomeCompleto);
        usuario.setDataAniversario(this.dataAniversario);
        usuario.setCelular(this.celular);
        usuario.setEmail(this.email);
        usuario.setLogin(this.login);
        usuario.setSenha(this.senha);
        return usuario;
    }

    // resposta sem o campo senha, para nunca devolver a senha em uma consulta
    public static UsuarioRespostaDTO paraResposta(Usuario usuario)
    {
        return new UsuarioRespostaDTO(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNomeCompleto(),
                usuario.getDataAniversario(),
                usuario.getCelular(),
                usuario.getEmail(),
                usuario.getLogin());
    }
}
