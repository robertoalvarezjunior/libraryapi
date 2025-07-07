package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.dto.ErrorResposta;
import com.example.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.example.libraryapi.exceptions.RegistroDuplicadoException;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor) {
        try {
        Autor autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(
                autorEntidade.getId()).toUri();


        return ResponseEntity.created(location).build();

        }catch (RegistroDuplicadoException e){
            ErrorResposta conflito = ErrorResposta.conflito(e.getMessage());
            return ResponseEntity.status(conflito.status()).body(conflito);
        }
    }

    @GetMapping("{id}")
    public  ResponseEntity<AutorDTO> obterAutor(@PathVariable String id){
        UUID idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id){
        try {
            UUID            idAutor       = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound()
                                     .build();
            }

            service.deletar(autorOptional.get());

            return ResponseEntity.noContent()
                                 .build();
        } catch (OperacaoNaoPermitidaException e) {
            ErrorResposta erro = ErrorResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value="nome", required = false) String nome ,
     @RequestParam(value="nacionalidade", required = false)                                               String nacionalidade){
        List<Autor> autores = service.pesquisaExemple(nome, nacionalidade);

        List<AutorDTO> lista = autores.stream().map(autor -> new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getNacionalidade(),
                autor.getDataNascimento()
        )).toList();

        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorDTO autor){
        try {
            UUID            idAutor       = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound()
                                     .build();
            }

            Autor autorEntidade = autor.mapearParaAutor();
            autorEntidade.setId(idAutor);
            service.atualizar(autorEntidade);

            return ResponseEntity.noContent()
                                 .build();
        }catch (RegistroDuplicadoException e){
            ErrorResposta conflito = ErrorResposta.conflito(e.getMessage());
            return ResponseEntity.status(conflito.status()).body(conflito);
        }
    }

}