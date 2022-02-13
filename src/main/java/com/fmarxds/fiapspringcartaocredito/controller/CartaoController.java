package com.fmarxds.fiapspringcartaocredito.controller;

import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;
import com.fmarxds.fiapspringcartaocredito.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Cartao> cadastrarCartao(
            @RequestBody Cartao cartao
    ) {

        return new ResponseEntity<>(cartaoService.cadastrarCartao(cartao), HttpStatus.CREATED);

    }

    @GetMapping("/{cartaoId}")
    public ResponseEntity<Cartao> buscarCartao(
            @PathVariable("cartaoId") String cartaoId
    ) {

        return ResponseEntity.of(cartaoService.buscarCartao(cartaoId));

    }


    @GetMapping("/matriculas/{matricula}")
    public ResponseEntity<List<Cartao>> buscarCartoes(
            @PathVariable("matricula") String matricula
    ) {

        return ResponseEntity.ok(cartaoService.buscarCartoes(matricula));

    }

    @PostMapping("/{cartaoId}/transacoes")
    public ResponseEntity<Transacao> registrarTransacao(
            @PathVariable("cartaoId") String cartaoId,
            @RequestBody Transacao transacao
    ) {

        return new ResponseEntity<>(cartaoService.registrarTransacao(cartaoId, transacao), HttpStatus.CREATED);

    }

    @GetMapping("/{cartaoId}/transacoes")
    public ResponseEntity<Collection<Transacao>> buscarTransacoes(
            @PathVariable("cartaoId") String cartaoId
    ) {

        return ResponseEntity.ok(cartaoService.buscarTransacoes(cartaoId));

    }

}
