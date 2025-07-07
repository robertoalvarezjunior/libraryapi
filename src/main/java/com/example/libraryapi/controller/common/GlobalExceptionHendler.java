package com.example.libraryapi.controller.common;

import com.example.libraryapi.controller.dto.ErrorCampo;
import com.example.libraryapi.controller.dto.ErrorResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHendler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorCampo> listErros = fieldErrors.stream().map(fieldError -> new ErrorCampo(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return new ErrorResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", listErros);
    }

}
