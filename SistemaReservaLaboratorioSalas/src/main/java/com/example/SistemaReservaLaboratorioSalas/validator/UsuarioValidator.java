package com.example.SistemaReservaLaboratorioSalas.validator;

import com.example.SistemaReservaLaboratorioSalas.exceptions.RegistroDuplicadoException;
import com.example.SistemaReservaLaboratorioSalas.model.Usuario;
import com.example.SistemaReservaLaboratorioSalas.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioValidator
{
    private final UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository)
    {
        this.usuarioRepository = usuarioRepository;
    }

    public void validar(Usuario usuario)
    {
        verificarDuplicidade(usuario.getId(), usuarioRepository.findByCpf(usuario.getCpf()),
                "Já existe um Usuário cadastrado com esse CPF");
        verificarDuplicidade(usuario.getId(), usuarioRepository.findByEmail(usuario.getEmail()),
                "Já existe um Usuário cadastrado com esse e-mail");
        verificarDuplicidade(usuario.getId(), usuarioRepository.findByLogin(usuario.getLogin()),
                "Já existe um Usuário cadastrado com esse login");
    }

    private void verificarDuplicidade(Integer idAtual, Optional<Usuario> encontrado, String mensagem)
    {
        boolean duplicado = idAtual == null
                ? encontrado.isPresent()
                : encontrado.isPresent() && !encontrado.get().getId().equals(idAtual);

        if (duplicado)
        {
            throw new RegistroDuplicadoException(mensagem);
        }
    }
}
