package com.example.SistemaReservaLaboratorioSalas.service;

import com.example.SistemaReservaLaboratorioSalas.model.Usuario;
import com.example.SistemaReservaLaboratorioSalas.repository.UsuarioRepository;
import com.example.SistemaReservaLaboratorioSalas.validator.UsuarioValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService
{
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioValidator usuarioValidator)
    {
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
    }

    public Usuario inserir(Usuario usuario)
    {
        usuarioValidator.validar(usuario);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Integer id)
    {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizar(Usuario usuario)
    {
        usuarioValidator.validar(usuario);
        return usuarioRepository.save(usuario);
    }

    public void excluirPorId(Integer id)
    {
        usuarioRepository.deleteById(id);
    }

    // consulta por e-mail (parcial) e/ou data de aniversário
    public List<Usuario> pesquisar(String email, LocalDate dataAniversario)
    {
        if (email != null && dataAniversario != null)
        {
            return usuarioRepository.findByEmailContainingIgnoreCaseAndDataAniversario(email, dataAniversario);
        }
        if (email != null)
        {
            return usuarioRepository.findByEmailContainingIgnoreCase(email);
        }
        if (dataAniversario != null)
        {
            return usuarioRepository.findByDataAniversario(dataAniversario);
        }
        return usuarioRepository.findAll();
    }

    // usado na tela de Login de Acesso
    public Optional<Usuario> autenticar(String login, String senha)
    {
        return usuarioRepository.findByLogin(login)
                .filter(usuario -> usuario.getSenha().equals(senha));
    }
}
