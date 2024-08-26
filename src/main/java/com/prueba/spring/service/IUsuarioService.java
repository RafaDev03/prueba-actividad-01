package com.prueba.spring.service;

import com.prueba.spring.controller.dto.UsuarioDTO;
import com.prueba.spring.entity.Usuario;

public interface IUsuarioService {
    UsuarioDTO findById(Long id);

    void save(Usuario usuario);
}
