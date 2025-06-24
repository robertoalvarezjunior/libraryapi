package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Beltrano");
        autor.setNacionalidade("Argentino");
        autor.setDataNascimento(LocalDate.of(1990, 1, 1));

        autorRepository.save(autor);

    }

    @Test
    void atualizarTest(){
        UUID uuid = UUID.fromString("8d62c508-083c-4586-9065-275b49aff2e2");

        Optional<Autor> possivelAutor = autorRepository.findById(uuid);

        if(possivelAutor.isPresent()) {
            Autor autor = possivelAutor.get();

            autor.setDataNascimento(LocalDate.of(1960, 1, 30));

            autorRepository.save(autor);

        }
    }

    @Test
    void listarTest(){
        autorRepository.findAll().forEach(System.out::println);
    }

    @Test
    void deletarTest(){
        UUID uuid = UUID.fromString("d9ba988b-7e2c-41e9-a158-56d18e8d3e0a");
        autorRepository.deleteById(uuid);
    }

    @Test
    void salvarAutorComLivroTest(){
        Autor autor = new Autor();
        autor.setNome("asdsadas");
        autor.setNacionalidade("brasil");
        autor.setDataNascimento(LocalDate.of(1970, 8, 5));

        Livro livro = new Livro();
        livro.setAutor(autor);
        livro.setTitulo("Livro c");
        livro.setIsbn("1234");
        livro.setDataPublicacao(LocalDate.of(2022, 1, 1));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setPreco(BigDecimal.valueOf(100));

        Livro livro2 = new Livro();
        livro2.setAutor(autor);
        livro2.setTitulo("Livro d");
        livro2.setPreco(BigDecimal.valueOf(100));
        livro2.setIsbn("1234");
        livro2.setDataPublicacao(LocalDate.of(2022, 1, 1));
        livro2.setGenero(GeneroLivro.BIOGRAFIA);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosDoAutor(){
        UUID uuid = UUID.fromString("eeb89663-0e9e-40cc-af6c-c64978557860");
        Optional<Autor> autor = autorRepository.findById(uuid);


        autor.ifPresent(validadoAutor -> {
            List<Livro> livrosLista = livroRepository.findByAutor(validadoAutor);
            validadoAutor.setLivros(livrosLista);
            validadoAutor.getLivros().forEach(System.out::println);
        });

        if (autor.isPresent()){
            Autor validadoAutor = autor.get();
            List<Livro> livrosLista = livroRepository.findByAutor(validadoAutor);

            validadoAutor.setLivros(livrosLista);
            validadoAutor.getLivros().forEach(System.out::println);
        }
    }
}
