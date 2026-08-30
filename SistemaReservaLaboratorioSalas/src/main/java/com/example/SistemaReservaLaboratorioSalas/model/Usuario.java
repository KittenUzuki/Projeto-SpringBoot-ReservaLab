package com.example.SistemaReservaLaboratorioSalas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
public class Usuario
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome_completo", length = 100, nullable = false)
    private String nomeCompleto;

    @Column(name = "data_aniversario", nullable = false)
    private LocalDate dataAniversario;

    @Column(name = "celular", length = 15, nullable = false)
    private String celular;

    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @Column(name = "login", length = 30, nullable = false, unique = true)
    private String login;

    // OBS: em um projeto de produção a senha deveria ser armazenada com hash
    // (ex: BCrypt). Mantido em texto puro aqui para simplificar o escopo do projeto.
    @Column(name = "senha", length = 100, nullable = false)
    private String senha;

    public void setCpf(String c) { cpf = c; }
    public void setNomeCompleto(String n) { nomeCompleto = n; }
    public void setDataAniversario(LocalDate d) { dataAniversario = d; }
    public void setCelular(String c) { celular = c; }
    public void setEmail(String e) { email = e; }
    public void setLogin(String l) { login = l; }
    public void setSenha(String s) { senha = s; }

    public Integer getId() { return id; }
    public String getCpf() { return cpf; }
    public String getNomeCompleto() { return nomeCompleto; }
    public LocalDate getDataAniversario() { return dataAniversario; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
}
