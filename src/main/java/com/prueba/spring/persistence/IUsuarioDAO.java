package com.prueba.spring.persistence;

import java.util.Optional;

import com.prueba.spring.entity.Usuario;

public interface IUsuarioDAO {
    Optional<Usuario> findById(Long id);

    void save(Usuario usuario);
}
