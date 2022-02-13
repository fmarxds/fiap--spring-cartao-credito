package com.fmarxds.fiapspringcartaocredito.service;

import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;

import java.util.List;
import java.util.Optional;

public interface CartaoService {

    Cartao cadastrarCartao(Cartao cartao);

    List<Cartao> buscarCartoes(String matricula);

    Optional<Cartao> buscarCartao(String cartaoId);

    Transacao registrarTransacao(String cartaoId, Transacao transacao);

    List<Transacao> buscarTransacoes(String cartaoId);

}
