package com.example.SistemaReservaLaboratorioSalas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "laboratorio")
@Data
@EqualsAndHashCode(callSuper = true)
public class Laboratorio extends Recurso
{
    @Override
    public String getTipo() { return "LABORATORIO"; }
}
