package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(String nome, String nacionalidade, LocalDate dataNascimento) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        autor.setDataNascimento(this.dataNascimento);
        return autor;
    }
}
