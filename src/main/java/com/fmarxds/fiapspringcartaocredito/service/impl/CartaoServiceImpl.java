package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.exception.CartaoNaoEncontradoException;
import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;
import com.fmarxds.fiapspringcartaocredito.repository.CartaoRepository;
import com.fmarxds.fiapspringcartaocredito.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;

    @Override
    public Cartao cadastrarCartao(Cartao cartao) {
        cartao.setId(UUID.randomUUID().toString());
        return cartaoRepository.save(cartao);
    }

    @Override
    public List<Cartao> buscarCartoes(String matricula) {
        return cartaoRepository.findAllByMatriculaAlunoEquals(matricula);
    }

    @Override
    public Optional<Cartao> buscarCartao(String cartaoId) {
        return cartaoRepository.findById(cartaoId);
    }

    @Override
    public Transacao registrarTransacao(String cartaoId, Transacao transacao) {

        Cartao cartao = this.buscarCartao(cartaoId)
                .orElseThrow(() -> new CartaoNaoEncontradoException("O cartao informado nao existe"));

        cartao.getTransacoes().add(transacao);

        cartaoRepository.save(cartao);

        return transacao;

    }

    @Override
    public List<Transacao> buscarTransacoes(String cartaoId) {

        return this.buscarCartao(cartaoId)
                .orElseThrow(() -> new CartaoNaoEncontradoException("O cartao informado nao existe"))
                .getTransacoes();

    }

}
