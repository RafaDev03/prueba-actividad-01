package com.prueba.spring.controller.dto;

public record UploadResponseDTO(boolean estado, String mensaje, String fileName, long fileSize) {

}
