package com.example.SistemaReservaLaboratorioSalas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Data
public class Reserva
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inicial", nullable = false)
    private LocalDate dataInicial;

    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;

    @Column(name = "hora_inicial", nullable = false)
    private LocalTime horaInicial;

    @Column(name = "hora_final", nullable = false)
    private LocalTime horaFinal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    public void setDataInicial(LocalDate d) { dataInicial = d; }
    public void setDataFinal(LocalDate d) { dataFinal = d; }
    public void setHoraInicial(LocalTime h) { horaInicial = h; }
    public void setHoraFinal(LocalTime h) { horaFinal = h; }
    public void setUsuario(Usuario u) { usuario = u; }
    public void setRecurso(Recurso r) { recurso = r; }
    public void setStatus(Status s) { status = s; }

    public Integer getId() { return id; }
    public LocalDate getDataInicial() { return dataInicial; }
    public LocalDate getDataFinal() { return dataFinal; }
    public LocalTime getHoraInicial() { return horaInicial; }
    public LocalTime getHoraFinal() { return horaFinal; }
    public Usuario getUsuario() { return usuario; }
    public Recurso getRecurso() { return recurso; }
    public Status getStatus() { return status; }
}
