package com.example.SistemaReservaLaboratorioSalas.model;

import jakarta.persistence.*;
import lombok.Data;

// Classe base para os recursos que podem ser reservados: Laboratorio e Sala.
// Usa herança JOINED: os campos comuns ficam na tabela "recurso" e cada
// subtipo tem sua própria tabela ligada por id (permite consultas unificadas
// de "recurso" e, ao mesmo tempo, endpoints separados por tipo).
@Entity
@Table(name = "recurso")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Recurso
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", length = 20, nullable = false, unique = true)
    private String codigo;

    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

    @Column(name = "localizacao", length = 60, nullable = false)
    private String localizacao;

    public void setCodigo(String c) { codigo = c; }
    public void setNome(String n) { nome = n; }
    public void setCapacidade(Integer c) { capacidade = c; }
    public void setLocalizacao(String l) { localizacao = l; }

    public Integer getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public Integer getCapacidade() { return capacidade; }
    public String getLocalizacao() { return localizacao; }

    // usado para identificar o tipo do recurso nas consultas unificadas
    public abstract String getTipo();
}
