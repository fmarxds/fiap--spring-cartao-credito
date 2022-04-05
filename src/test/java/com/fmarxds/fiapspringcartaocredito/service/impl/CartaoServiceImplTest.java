package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.exception.CartaoNaoEncontradoException;
import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;
import com.fmarxds.fiapspringcartaocredito.repository.CartaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartaoServiceImplTest {

    @Mock CartaoRepository cartaoRepository;

    @InjectMocks CartaoServiceImpl service;

    @Test
    void cadastrarCartao() {
        Cartao mockedCartao = new Cartao();

        when(cartaoRepository.save(any(Cartao.class))).thenReturn(mockedCartao);

        Cartao cartaoSalvo = service.cadastrarCartao(new Cartao());

        assertNotNull(cartaoSalvo, "Cartão não está nulo");

        verify(cartaoRepository, atMost(1)).save(any(Cartao.class));
    }

    @Test
    void buscarCartoes() {
        Cartao mockedCartao = new Cartao();
        List<Cartao> mockedList = new ArrayList<>();

        mockedList.add(mockedCartao);

        when(cartaoRepository.findAllByMatriculaAlunoEquals(anyString())).thenReturn(mockedList);

        List<Cartao> cartoesEncontrados = service.buscarCartoes("12345");

        assertNotNull(cartoesEncontrados);
        assertTrue(cartoesEncontrados.size() > 0, "Cartões vazios");

        verify(cartaoRepository, atMost(1)).findAllByMatriculaAlunoEquals(anyString());
    }

    @Test
    void whenIdValid_buscarCartao() {
        Cartao mockedCartao = new Cartao();

        when(cartaoRepository.findById(anyString())).thenReturn(Optional.of(mockedCartao));

        Cartao cartaoEncontrado = service.buscarCartao("54728312").orElse(null);

        assertNotNull(cartaoEncontrado);

        verify(cartaoRepository, atMost(1)).findById(anyString());
    }

    @Test
    void whenIdInvalid_buscarCartao() {
        when(cartaoRepository.findById(anyString())).thenReturn(null);

        assertNull(service.buscarCartao("54728312"), "Foi encontrado um cartão com esse id");

        verify(cartaoRepository, atMost(1)).findById(anyString());
    }

    @Test
    void whenIdValid_registrarTransacao() {
        Cartao mockedCartao = new Cartao();
        Cartao newMockedCartao = new Cartao();
        Transacao mockedTransacao = new Transacao();

        when(cartaoRepository.findById(anyString())).thenReturn(Optional.of(mockedCartao));
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(newMockedCartao);

        Transacao novaTransacao = service.registrarTransacao("123456", mockedTransacao);

        assertNotNull(novaTransacao, "Transação não executada com sucesso");

        verify(cartaoRepository, atMost(1)).findById(anyString());
        verify(cartaoRepository, atMost(1)).save(any(Cartao.class));
    }

    @Test
    void whenIdInvalid_registrarTransacao() {
        Transacao mockedTransacao = new Transacao();

        when(cartaoRepository.findById(anyString())).thenThrow(CartaoNaoEncontradoException.class);

        assertThrows(CartaoNaoEncontradoException.class,
                () -> service.registrarTransacao("123456", mockedTransacao));

        verify(cartaoRepository, atMost(1)).findById(anyString());
        verify(cartaoRepository, atMost(1)).save(any(Cartao.class));
    }

    @Test
    void whenIdValid_buscarTransacoes() {
        Cartao mockedCartao = new Cartao();
        Transacao mockedTransacao = new Transacao();
        List<Transacao> mockedList = new ArrayList<>();

        mockedList.add(mockedTransacao);

        mockedCartao.setTransacoes(mockedList);

        when(cartaoRepository.findById(anyString())).thenReturn(Optional.of(mockedCartao));

        List<Transacao> transacoesEncontradas = service.buscarTransacoes("123456");

        assertNotNull(transacoesEncontradas, "Transações não encontradas");
        assertTrue(transacoesEncontradas.size() > 0, "Transações vazias");

        verify(cartaoRepository, atMost(1)).findById(anyString());
    }

    @Test
    void whenIdInvalid_buscarTransacoes() {
        when(cartaoRepository.findById(anyString())).thenThrow(CartaoNaoEncontradoException.class);

        assertThrows(CartaoNaoEncontradoException.class, () -> service.buscarTransacoes("123456"));

        verify(cartaoRepository, atMost(1)).findById(anyString());
    }
}