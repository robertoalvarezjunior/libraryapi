package com.example.libraryapi.service;

import com.example.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final LivroRepository livroRepository;

    private final AutorValidator validator;


    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

   public void atualizar(Autor autor) {
        if (autor.getId() == null){
            throw new IllegalArgumentException("Id não pode ser alterado");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("O autor possui livros cadastrados");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null) {
            return repository.findByNome(nome);
        }
        if(nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

    public List<Autor> pesquisaExemple(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample   = Example.of(autor, exampleMatcher);

        return repository.findAll(autorExample);
    }


    private boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}