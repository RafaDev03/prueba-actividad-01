package com.prueba.spring.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prueba.spring.entity.Proyecto;
import com.prueba.spring.persistence.IProyectoDAO;
import com.prueba.spring.repository.ProyectoRepository;

@Component
public class ProyectoDAOImpl implements IProyectoDAO {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public List<Proyecto> findAll() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    @Override
    public Optional<Proyecto> findById(Long id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public Optional<Proyecto> findByCodigoProyecto(String codProyecto) {
        return proyectoRepository.findByCodigoProyecto(codProyecto);
    }

    @Override
    public void save(Proyecto proyecto) {
        proyectoRepository.save(proyecto);
    }

}
