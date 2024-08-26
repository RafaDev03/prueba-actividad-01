package com.prueba.spring.persistence;

import java.util.List;
import java.util.Optional;

import com.prueba.spring.entity.Proyecto;

public interface IProyectoDAO {
    List<Proyecto> findAll();

    Optional<Proyecto> findById(Long id);

    Optional<Proyecto> findByCodigoProyecto(String codProyecto);

    void save(Proyecto proyecto);
}
