package com.prueba.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prueba.spring.entity.Proyecto;

@Repository
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {
    Optional<Proyecto> findByCodigoProyecto(String codProyecto);
}
