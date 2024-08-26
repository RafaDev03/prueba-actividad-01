package com.prueba.spring.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.spring.controller.dto.MensajeResponse;
import com.prueba.spring.controller.dto.ProyectoGetDTO;
import com.prueba.spring.controller.dto.ProyectoPostDTO;

import com.prueba.spring.service.IProyectoService;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

    @Autowired
    private IProyectoService proyectoService;

    @GetMapping("/findAll")
    public ResponseEntity<MensajeResponse> findAll() {
        try {
            List<ProyectoGetDTO> listaProyectoDTO = proyectoService.findAll();
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Listado de proyectos", listaProyectoDTO);
            return ResponseEntity.ok(mensajeResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/findByCodProyecto/{codigo}")
    public ResponseEntity<MensajeResponse> mandarMensaje(@PathVariable String codigo) {
        try {
            ProyectoGetDTO proyectoDTO = proyectoService.findByCodigoProyecto(codigo);
            return ResponseEntity.ok(new MensajeResponse(true, "Proyecto encontrado", proyectoDTO));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse(false, "El proyecto no se encuentra disponible", null));
        }

    }

    @PostMapping("/save")
    public ResponseEntity<MensajeResponse> saveProject(@RequestBody ProyectoPostDTO proyectoPostDTO) {
        if (proyectoPostDTO == null) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "proyectoDTO no puede ser nulo", null));
        }
        try {
            proyectoService.save(proyectoPostDTO);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Proyecto Almacenado Correctamente",
                    null);
            URI location = URI.create(String.format("/api/proyecto/%s", proyectoPostDTO.codigoProyecto()));
            return ResponseEntity.created(location).body(mensajeResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(false, "Error al almacenar el proyecto", null));
        }
    }

}
