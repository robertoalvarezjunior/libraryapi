package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setTitulo("Livro 1");
        livro.setIsbn("1234");
        livro.setDataPublicacao(LocalDate.of(2022, 1, 1));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(100));

        Autor autor = autorRepository.findById(UUID.fromString("4aba2cf0-ddef-4418-9142-ef2639419fa0")).orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setTitulo("Livro 2");
        livro.setIsbn("1234");
        livro.setDataPublicacao(LocalDate.of(2022, 1, 1));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(100));

        Autor autor = new Autor();
        autor.setNome("roberto");
        autor.setNacionalidade("bostil");
        autor.setDataNascimento(LocalDate.of(1990, 1, 1));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorLivroTest() {
        UUID idLivro = UUID.fromString("4a6ff562-395c-42a0-b5e8-084c1a378746");
        Livro livro = livroRepository.findById(idLivro).orElse(null);

        UUID idAutor = UUID.fromString("b7424efe-93c7-4e76-bb0e-9194e2ca213a");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livro.setAutor(autor);
        livroRepository.save(livro);

    }

    @Test
    void deletarLivroTest() {
        UUID idLivro = UUID.fromString("4a6ff562-395c-42a0-b5e8-084c1a378746");
        livroRepository.deleteById(idLivro);
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("Livro 1");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloLikeTest(){
        List<Livro> lista = livroRepository.findByTituloStartsWithIgnoreCase("livro");
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryTest(){
        List<Livro> resultado = livroRepository.listarTodos();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosTest(){
        List<Autor> resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileirosTest(){
        List<String> resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGeneroTest(){
        List<Livro> resultado = livroRepository.findByGenero(GeneroLivro.BIOGRAFIA, "preco");
        resultado.forEach(System.out::println);
    }
    @Test
    void listarLivrosPorGeneroPosicionalTest(){
        List<Livro> resultado = livroRepository.findByGeneroPosicional(GeneroLivro.BIOGRAFIA, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletarLivrosPorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.FANTASIA);
    }
    @Test
    void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(2025, 6,11));
    }

    @Test
    void listarLivrosAutorTest(){
        UUID idAutor = UUID.fromString("4aba2cf0-ddef-4418-9142-ef2639419fa0");
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);
        autor.getLivros().forEach(System.out::println);
    }
}