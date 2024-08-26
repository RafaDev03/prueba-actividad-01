package com.prueba.spring.service;

import com.prueba.spring.controller.dto.UsuarioGetDTO;
import com.prueba.spring.controller.dto.UsuarioPostDTO;

public interface IUsuarioService {
    UsuarioGetDTO findById(Long id);

    UsuarioGetDTO update(UsuarioPostDTO usuarioPostDTO, Long id);

}
