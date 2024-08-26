package com.prueba.spring.persistence.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prueba.spring.entity.Usuario;
import com.prueba.spring.persistence.IUsuarioDAO;
import com.prueba.spring.repository.UsuarioRepository;

@Component
public class UsuarioDAOImpl implements IUsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

}
