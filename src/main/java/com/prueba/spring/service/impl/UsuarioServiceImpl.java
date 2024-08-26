package com.prueba.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import com.prueba.spring.controller.dto.UsuarioDTO;
import com.prueba.spring.entity.Usuario;
import com.prueba.spring.persistence.IUsuarioDAO;
import com.prueba.spring.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Override
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("El usuario no se encuentra disponible"));
        return new UsuarioDTO(usuario.getUsuarioId(), usuario.getEmail(), usuario.getNombres(), usuario.getApellidos(),
                usuario.getNombreEmpresa(), usuario.getTelefono(), usuario.getFotoPerfil(),
                usuario.getCargo().getNombreCargo().name());
    }

    @Override
    public void save(Usuario usuario) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
