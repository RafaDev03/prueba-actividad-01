package com.prueba.spring.controller.dto;

public record UsuarioDTO(Long id, String email, String nombres, String apellidos, String nombreEmpresa,
        String telefono, String fotoPerfil, String cargo) {

}
