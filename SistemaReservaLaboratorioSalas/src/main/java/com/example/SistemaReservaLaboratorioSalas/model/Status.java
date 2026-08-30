package com.example.SistemaReservaLaboratorioSalas.model;

import jakarta.persistence.*;
import lombok.Data;

// Status do recurso/reserva: LIVRE, OCUPADO, BLOQUEADO, RESERVADO
@Entity
@Table(name = "status")
@Data
public class Status
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", length = 20, nullable = false)
    private String codigo;

    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    public void setCodigo(String c) { codigo = c; }
    public void setNome(String n) { nome = n; }

    public Integer getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
}
