package com.prueba.spring.util;

import org.springframework.stereotype.Component;

import com.prueba.spring.controller.dto.UsuarioGetDTO;
import com.prueba.spring.entity.Usuario;

@Component
public class UsuarioUtils {

    public UsuarioGetDTO retornarUsuario(Usuario usuario) {
        return new UsuarioGetDTO(usuario.getUsuarioId(), usuario.getEmail(), usuario.getNombres(),
                usuario.getApellidos(), usuario.getNombreEmpresa(), usuario.getTelefono(), usuario.getFotoPerfil(),
                usuario.getCargo().getNombreCargo().name());
    }
}
