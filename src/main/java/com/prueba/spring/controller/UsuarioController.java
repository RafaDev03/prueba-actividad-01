package com.prueba.spring.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.spring.controller.dto.MensajeResponse;
import com.prueba.spring.controller.dto.UsuarioGetDTO;
import com.prueba.spring.controller.dto.UsuarioPostDTO;
import com.prueba.spring.service.IUsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            UsuarioGetDTO usuarioDTO = usuarioService.findById(id);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Perfil disponible", usuarioDTO);
            return ResponseEntity.ok().body(mensajeResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(false, e.getMessage(), null));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MensajeResponse> updateUsuario(@PathVariable Long id,
            @RequestBody UsuarioPostDTO usuarioPostDTO) {
        try {
            UsuarioGetDTO usuarioGetDTO = usuarioService.update(usuarioPostDTO, id);
            MensajeResponse mensajeResponse = new MensajeResponse(true, "Perfil actualizado con éxito", usuarioGetDTO);
            return ResponseEntity.ok().body(mensajeResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(false, e.getMessage(), null));
        }
    }

}
