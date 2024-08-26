package com.prueba.spring.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.spring.controller.dto.MensajeResponse;
import com.prueba.spring.controller.dto.UsuarioDTO;
import com.prueba.spring.service.IUsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.findById(id);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Usuario disponible", usuarioDTO);
            return ResponseEntity.ok().body(mensajeResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(false, e.getMessage(), null));
        }

    }
}
