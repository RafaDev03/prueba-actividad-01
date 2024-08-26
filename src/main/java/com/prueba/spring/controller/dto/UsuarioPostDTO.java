package com.prueba.spring.controller.dto;

public record UsuarioPostDTO(String nombres, String apellidos, String email, String nombreEmpresa, String telefono,
        Long cargoId) {

}
