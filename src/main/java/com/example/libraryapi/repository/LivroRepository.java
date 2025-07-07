package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloStartsWithIgnoreCase(String titulo);

    //JPQL -> referencia as entidades e as propriedades
    //select l.* from livro as l order by l.titulo
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodos();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'bostil'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    // parametros nomeados
    @Query("select l from Livro l where l.genero = :generoLivro order by :param")
    List<Livro> findByGenero(@Param("generoLivro") GeneroLivro generoLivro, @Param("param") String param);

    // parametros posicional
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPosicional(GeneroLivro generoLivro, String param);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);
}