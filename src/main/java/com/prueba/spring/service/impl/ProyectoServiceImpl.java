package com.prueba.spring.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.prueba.spring.controller.dto.ProyectoGetDTO;
import com.prueba.spring.controller.dto.ProyectoPostDTO;
import com.prueba.spring.entity.Categoria;
import com.prueba.spring.entity.EstadoProyecto;
import com.prueba.spring.entity.Proyecto;
import com.prueba.spring.entity.Usuario;
import com.prueba.spring.persistence.IProyectoDAO;
import com.prueba.spring.repository.CategoriaRepository;
import com.prueba.spring.repository.EstadoProyectoRepository;
import com.prueba.spring.repository.UsuarioRepository;
import com.prueba.spring.service.IProyectoService;

@Service
public class ProyectoServiceImpl implements IProyectoService {

        @Autowired
        private IProyectoDAO proyectoDAO;

        @Autowired
        private EstadoProyectoRepository estadoProyectoRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private CategoriaRepository categoriaRepository;

        @Override
        public List<ProyectoGetDTO> findAll() {
                List<Proyecto> listaProyecto = proyectoDAO.findAll();
                return listaProyecto.stream()
                                .map(proyecto -> new ProyectoGetDTO(
                                                proyecto.getProyectoId(),
                                                proyecto.getCodigoProyecto(),
                                                proyecto.getNombre(),
                                                proyecto.getDescripcion(),
                                                proyecto.getFechaInicio(),
                                                proyecto.getFechaFinal(),
                                                proyecto.getLogo(),
                                                proyecto.getCategoria().getNombreCategoria(),
                                                proyecto.getEstado().getNombreEstado().name()))
                                .collect(Collectors.toList());
        }

        @Override
        public Optional<Proyecto> findById(Long id) {
                return proyectoDAO.findById(id);
        }

        @Override
        public ProyectoGetDTO findByCodigoProyecto(String codProyecto) {
                Proyecto proyecto = proyectoDAO.findByCodigoProyecto(codProyecto)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "El proyecto con código " + codProyecto
                                                                + " no se encuentra disponible"));
                return new ProyectoGetDTO(
                                proyecto.getProyectoId(),
                                proyecto.getCodigoProyecto(),
                                proyecto.getNombre(),
                                proyecto.getDescripcion(),
                                proyecto.getFechaInicio(),
                                proyecto.getFechaFinal(),
                                proyecto.getLogo(),
                                proyecto.getCategoria().getNombreCategoria(),
                                proyecto.getEstado().getNombreEstado().name());
        }

        @Override
        public void save(ProyectoPostDTO proyectoPostDTO) {
                EstadoProyecto estadoProyecto = estadoProyectoRepository.findById(proyectoPostDTO.estado())
                                .orElseThrow(() -> new NoSuchElementException("Estado de proyecto no encontrado"));

                Categoria categoria = categoriaRepository.findById(proyectoPostDTO.categoria())
                                .orElseThrow(() -> new NoSuchElementException("Categoría no disponible"));

                String email = SecurityContextHolder.getContext().getAuthentication().getName();

                Usuario usuarioAlta = usuarioRepository.findByEmail(email)
                                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

                Proyecto proyecto = Proyecto.builder()
                                .codigoProyecto(proyectoPostDTO.codigoProyecto())
                                .nombre(proyectoPostDTO.nombre())
                                .descripcion(proyectoPostDTO.descripcion())
                                .fechaInicio(proyectoPostDTO.fechaInicio())
                                .fechaFinal(proyectoPostDTO.fichaFinal())
                                .logo(proyectoPostDTO.logo())
                                .estado(estadoProyecto)
                                .categoria(categoria)
                                .usuario(usuarioAlta)
                                .build();

                proyectoDAO.save(proyecto);
        }

}
