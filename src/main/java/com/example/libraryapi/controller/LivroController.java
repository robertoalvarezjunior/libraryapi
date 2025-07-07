package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ErrorResposta;
import com.example.libraryapi.exceptions.RegistroDuplicadoException;
import com.example.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody
                                         @Valid
                                         CadastroLivroDTO dto) {
        try {

            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e) {
            ErrorResposta conflito = ErrorResposta.conflito(e.getMessage());
            return ResponseEntity.status(conflito.status()).body(conflito);
        }
    }
}
