package com.prueba.spring.service;

import java.util.List;
import java.util.Optional;

import com.prueba.spring.controller.dto.ProyectoGetDTO;
import com.prueba.spring.controller.dto.ProyectoPostDTO;
import com.prueba.spring.entity.Proyecto;

public interface IProyectoService {
    List<ProyectoGetDTO> findAll();

    Optional<Proyecto> findById(Long id);

    ProyectoGetDTO findByCodigoProyecto(String codProyecto);

    void save(ProyectoPostDTO proyectoPostDTO);
}
