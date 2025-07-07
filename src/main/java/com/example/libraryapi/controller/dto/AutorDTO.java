package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       @NotBlank(message = "O campo é obrigatório")
                       @Size(min = 2, max = 100, message = "O campo deve ter no máximo 100 caracteres") String nome,
                       @NotBlank(message = "O campo é obrigatório") String nacionalidade,
                       @NotNull(message = "O campo é obrigatório")
                       @Past(message = "O campo deve ser uma data no passado")
                       @Size(min = 2, max = 50, message = "O campo deve ter no máximo 50 caracteres") LocalDate dataNascimento) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }
}