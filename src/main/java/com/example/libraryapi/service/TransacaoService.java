package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar() {

        Autor autor = new Autor();
        autor.setNome("novo autor");
        autor.setNacionalidade("brasil");
        autor.setDataNascimento(LocalDate.of(1970, 8, 5));

        autorRepository.save(autor);


        Livro livro = new Livro();
        livro.setTitulo("brascubas");
        livro.setIsbn("1234");
        livro.setDataPublicacao(LocalDate.of(2022, 1, 1));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(100));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Fulano"))
            throw new RuntimeException("Erro ao salvar autor");
    }

    @Transactional
    public void executarSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("6db94714-d4bc-45c8-ae9f-6a5d31366206")).orElse(null);
        livro.setGenero(GeneroLivro.ROMANCE);

//        livroRepository.save(livro);
    }
}
