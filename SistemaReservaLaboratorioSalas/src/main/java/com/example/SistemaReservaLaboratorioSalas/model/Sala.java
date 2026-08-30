package com.example.SistemaReservaLaboratorioSalas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "sala")
@Data
@EqualsAndHashCode(callSuper = true)
public class Sala extends Recurso
{
    @Override
    public String getTipo() { return "SALA"; }
}
