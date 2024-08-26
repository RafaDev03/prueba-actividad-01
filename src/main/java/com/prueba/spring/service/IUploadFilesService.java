package com.prueba.spring.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.spring.controller.dto.UploadResponseDTO;

public interface IUploadFilesService {
    UploadResponseDTO actualizarImagen(Long id, MultipartFile file, String tipoImagen) throws IOException;

    Resource cargarImagen(String fileName, String tipoImagen);

}
