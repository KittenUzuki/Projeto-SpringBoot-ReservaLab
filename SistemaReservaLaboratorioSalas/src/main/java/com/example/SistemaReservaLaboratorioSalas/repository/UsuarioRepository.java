package com.example.SistemaReservaLaboratorioSalas.repository;

import com.example.SistemaReservaLaboratorioSalas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>
{
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByLogin(String login);

    List<Usuario> findByEmailContainingIgnoreCase(String email);
    List<Usuario> findByDataAniversario(LocalDate dataAniversario);
    List<Usuario> findByEmailContainingIgnoreCaseAndDataAniversario(String email, LocalDate dataAniversario);
}
