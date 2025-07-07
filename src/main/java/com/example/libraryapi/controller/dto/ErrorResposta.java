package com.example.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResposta(int status, String mensagem, List<ErrorCampo> erros) {
    public static ErrorResposta respostaPadrao(String mensagem) {
        return new ErrorResposta(HttpStatus.BAD_GATEWAY.value(), mensagem, List.of());
    }

    public static ErrorResposta conflito(String mensagem) {
        return new ErrorResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}