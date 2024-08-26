package com.prueba.spring.util;

import java.io.File;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;

import com.prueba.spring.controller.dto.UploadResponseDTO;

@Component
public class UploadUtils {

    @Value("${direccion.imagen.usuarios}")
    private String USER_UPLOAD_DIR;

    @Value("${direccion.imagen.proyectos}")
    private String PROJECT_UPLOAD_DIR;

    public UploadResponseDTO subirImagen(MultipartFile file, String tipoImagen) throws IOException {

        final long MAX_FILE_SIZE = 10 * 1024 * 1024;

        final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");

        String fileOriginalName = file.getOriginalFilename();

        if (fileOriginalName == null) {
            return new UploadResponseDTO(false, "El nombre del archivo no puede ser nulo", null, 0);
        }

        long fileSize = file.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            return new UploadResponseDTO(false, "El tamaño de la imagen superó los límites permitidos", null, fileSize);
        }

        if (ALLOWED_EXTENSIONS.stream().noneMatch(fileOriginalName::endsWith)) {
            return new UploadResponseDTO(false, "No es una extensión permitida", null, fileSize);
        }

        String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString().concat(fileExtension);

        String uploadDir = tipoImagen.equals("usuario") ? USER_UPLOAD_DIR : PROJECT_UPLOAD_DIR;

        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Path path = Paths.get(uploadDir + newFileName);
        Files.write(path, file.getBytes());

        return new UploadResponseDTO(true, "Imagen subida correctamente", newFileName, fileSize);
    }

    public UploadResponseDTO eliminarImagen(String fileName, String tipoImagen) {
        try {
            String uploadDir = tipoImagen.equals("usuario") ? USER_UPLOAD_DIR : PROJECT_UPLOAD_DIR;
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            File file = filePath.toFile();

            if (file.exists()) {

                if (file.delete()) {
                    return new UploadResponseDTO(true, "Imagen eliminada correctamente", fileName, 0);
                } else {
                    return new UploadResponseDTO(false, "No se pudo eliminar la imagen", fileName, 0);
                }
            } else {
                return new UploadResponseDTO(false, "Imagen no encontrada: " + fileName, fileName, 0);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la imagen: " + fileName, e);
        }
    }

    public String rutaImagen(String tipoImagen) {
        return tipoImagen.equals("usuario") ? USER_UPLOAD_DIR : PROJECT_UPLOAD_DIR;
    }
}
