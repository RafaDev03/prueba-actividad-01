package com.prueba.spring.controller.dto;

import java.time.LocalDate;

public record ProyectoPostDTO(Long id, String codigoProyecto, String nombre, String descripcion, LocalDate fechaInicio,
        LocalDate fichaFinal, String logo, Long estado) {

}
