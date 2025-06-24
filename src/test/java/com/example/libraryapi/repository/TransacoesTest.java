package com.example.libraryapi.repository;

import com.example.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransacoesTest {
    @Autowired
    TransacaoService transacaoService;

    /**
     * COMMIT - Salva as alterações
     * ROLLBACK - Desfaz as alterações
     */
    @Test
    void transacoesTest() {
    transacaoService.executar();
    }

    @Test
    void transacoesSemAtualizarTest() {
        transacaoService.executarSemAtualizar();
    }
}
