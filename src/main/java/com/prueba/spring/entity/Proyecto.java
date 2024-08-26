package com.prueba.spring.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyecto_id")
    private Long proyectoId;

    @Column(name = "codigo_proyecto")
    private String codigoProyecto;

    private String nombre;

    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;

    private String logo;

    @ManyToOne(targetEntity = EstadoProyecto.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_proyecto_id", nullable = false)
    private EstadoProyecto estado;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "uruario_alta_id", nullable = false)
    private Usuario usuario;

}
