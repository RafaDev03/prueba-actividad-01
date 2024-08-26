package com.prueba.spring.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.spring.controller.dto.UploadResponseDTO;
import com.prueba.spring.entity.Proyecto;
import com.prueba.spring.entity.Usuario;
import com.prueba.spring.repository.ProyectoRepository;
import com.prueba.spring.repository.UsuarioRepository;
import com.prueba.spring.service.IUploadFilesService;
import com.prueba.spring.util.UploadUtils;

@Service
public class UploadFilesServiceImpl implements IUploadFilesService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public UploadResponseDTO actualizarImagen(Long id, MultipartFile file, String tipoImagen) throws IOException {
        UploadResponseDTO uploadResponseDTO = null;
        switch (tipoImagen) {
            case "usuario":
                Usuario usuario = usuarioRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
                uploadUtils.eliminarImagen(usuario.getFotoPerfil(), "usuario");
                uploadResponseDTO = uploadUtils.subirImagen(file, tipoImagen);
                usuario.setFotoPerfil(uploadResponseDTO.fileName());
                usuarioRepository.save(usuario);

                break;

            case "proyecto":
                Proyecto proyecto = proyectoRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Proyecto no encontrado"));
                uploadUtils.eliminarImagen(proyecto.getLogo(), "proyecto");
                uploadResponseDTO = uploadUtils.subirImagen(file, tipoImagen);
                proyecto.setLogo(uploadResponseDTO.fileName());
                proyectoRepository.save(proyecto);
                break;
            default:
                break;
        }
        return uploadResponseDTO;
    }

    @Override
    public Resource cargarImagen(String fileName, String tipoImagen) {
        try {
            String uploadDir = uploadUtils.rutaImagen(tipoImagen);
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Imagen no encontrada: " + fileName);
            }
        } catch (FileNotFoundException | MalformedURLException e) {
            throw new RuntimeException("Error al cargar la imagen: " + fileName, e);
        }
    }

}
