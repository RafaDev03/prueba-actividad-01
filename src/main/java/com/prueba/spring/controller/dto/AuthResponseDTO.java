package com.prueba.spring.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "correo", "message", "accessToken", "refreshToken", "status" })
public record AuthResponseDTO(String correo, String mensaje, String accessToken, boolean status) {

}
