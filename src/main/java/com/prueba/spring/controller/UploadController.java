package com.prueba.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.spring.controller.dto.MensajeResponse;
import com.prueba.spring.controller.dto.UploadResponseDTO;
import com.prueba.spring.service.IUploadFilesService;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private IUploadFilesService uploadFilesService;

    @Value("${direccion.imagen.usuarios}")
    private String USER_UPLOAD_DIR;

    @Value("${direccion.imagen.proyectos}")
    private String PROJECT_UPLOAD_DIR;

    @PostMapping("/picture/{tipo}/{id}")
    public ResponseEntity<?> subirImagen(@RequestParam("file") MultipartFile file, @PathVariable String tipo,
            @PathVariable Long id) {
        try {
            UploadResponseDTO uploadResponseDTO = uploadFilesService.actualizarImagen(id, file, tipo);
            MensajeResponse mensajeResponse = new MensajeResponse(uploadResponseDTO.estado(),
                    uploadResponseDTO.mensaje(), uploadResponseDTO.fileName());
            return ResponseEntity.status(uploadResponseDTO.estado() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(mensajeResponse);
        } catch (IOException e) {
            MensajeResponse mensajeResponse = new MensajeResponse(false, "Error al subir la imagen: " + e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeResponse);
        }
    }

    @GetMapping("/picture/{tipo}/{fileName:.+}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String fileName, @PathVariable String tipo) {
        Resource resource = uploadFilesService.cargarImagen(fileName, tipo);
        String contentType;
        try {
            String uploadDir = tipo.equals("usuario") ? USER_UPLOAD_DIR : PROJECT_UPLOAD_DIR;
            contentType = Files.probeContentType(Paths.get(uploadDir).resolve(fileName));
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }
}
