package com.prueba.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import com.prueba.spring.controller.dto.UsuarioGetDTO;
import com.prueba.spring.controller.dto.UsuarioPostDTO;
import com.prueba.spring.entity.Cargo;
import com.prueba.spring.entity.Usuario;
import com.prueba.spring.persistence.IUsuarioDAO;
import com.prueba.spring.repository.CargoRepository;
import com.prueba.spring.service.IUsuarioService;
import com.prueba.spring.util.UsuarioUtils;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

        @Autowired
        private IUsuarioDAO usuarioDAO;

        @Autowired
        private CargoRepository cargoRepository;

        @Autowired
        private UsuarioUtils usuarioUtils;

        @Override
        public UsuarioGetDTO findById(Long id) {
                Usuario usuario = usuarioDAO.findById(id)
                                .orElseThrow(() -> new NoSuchElementException("El usuario no se encuentra disponible"));
                return new UsuarioGetDTO(usuario.getUsuarioId(), usuario.getEmail(), usuario.getNombres(),
                                usuario.getApellidos(),
                                usuario.getNombreEmpresa(), usuario.getTelefono(), usuario.getFotoPerfil(),
                                usuario.getCargo().getNombreCargo().name());
        }

        @Override
        public UsuarioGetDTO update(UsuarioPostDTO usuarioPostDTO, Long id) {

                Usuario usuario = usuarioDAO.findById(id).orElseThrow(() -> new NoSuchElementException(
                                "El usuario con id" + id + " no se encuentra disponible"));

                Cargo cargo = cargoRepository.findById(usuarioPostDTO.cargoId())
                                .orElseThrow(() -> new NoSuchElementException(
                                                "El cargo con el id " + usuarioPostDTO.cargoId()
                                                                + " no se encuentra disponible"));

                usuario.setNombres(usuarioPostDTO.nombres());
                usuario.setApellidos(usuarioPostDTO.apellidos());
                usuario.setEmail(usuarioPostDTO.email());
                usuario.setNombreEmpresa(usuarioPostDTO.nombreEmpresa());
                usuario.setCargo(cargo);
                usuario.setTelefono(usuarioPostDTO.telefono());

                usuarioDAO.save(usuario);
                return usuarioUtils.retornarUsuario(usuario);
        }

}
