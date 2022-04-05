package com.fmarxds.fiapspringcartaocredito.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.TipoTransacaoEnum;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;
import com.fmarxds.fiapspringcartaocredito.service.CartaoService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    @Mock
    CartaoService cartaoService;
    @InjectMocks
    CartaoController controller;

    MockMvc mockMvc;

    private final String BASE_URL = "/v1/cartoes";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Cadastrar Cartao")
    void cadastrarCartao() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matriculaAluno", "John Doe");
        json.put("dataHora", "03/04/2022 23:27:20");
        json.put("estabelecimento", "Escola Teste");
        json.put("parcelas", 2 );
        json.put("tipo", "CREDITO");
        json.put("valor", 1250 );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar Cartao sem Matricula Aluno")
    void cadastrarCartaoithoutMatriculaAluno() throws Exception {
        JSONObject json = new JSONObject();

        json.put("dataHora", "03/04/2022 23:27:20");
        json.put("estabelecimento", "Escola Teste");
        json.put("parcelas", 2 );
        json.put("tipo", "CREDITO");
        json.put("valor", 1250 );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Cadastrar Cartao sem Data e Hora")
    void cadastrarCartaoithoutDataHora() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matriculaAluno", "John Doe");
        json.put("estabelecimento", "Escola Teste");
        json.put("parcelas", 2 );
        json.put("tipo", "CREDITO");
        json.put("valor", 1250 );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar Cartao sem Estabelecimento")
    void cadastrarCartaoithoutEstabelecimento() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matriculaAluno", "John Doe");
        json.put("dataHora", "03/04/2022 23:27:20");
        json.put("parcelas", 2 );
        json.put("tipo", "CREDITO");
        json.put("valor", 1250 );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Cadastrar Cartao sem Parcelas")
    void cadastrarCartaoithoutParcelas() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matriculaAluno", "John Doe");
        json.put("dataHora", "03/04/2022 23:27:20");
        json.put("estabelecimento", "Escola Teste");
        json.put("tipo", "CREDITO");
        json.put("valor", 1250 );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar Cartao sem Tipo")
    void cadastrarCartaoithoutTipo() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matriculaAluno", "John Doe");
        json.put("dataHora", "03/04/2022 23:27:20");
        json.put("estabelecimento", "Escola Teste");
        json.put("parcelas", 2 );
        json.put("valor", 1250 );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar Cartao sem Valor")
    void cadastrarCartaoithoutValor() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matriculaAluno", "John Doe");
        json.put("dataHora", "03/04/2022 23:27:20");
        json.put("estabelecimento", "Escola Teste");
        json.put("parcelas", 2 );
        json.put("tipo", "CREDITO");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar Cartao sem nada")
    void cadastrarAlunoWithNothing() throws Exception {
        mockMvc.perform(post(BASE_URL))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Endpoint inválido")
    void endpointNotFound() throws Exception {
        String wrongEndpoint = String.format("%s/%s", BASE_URL, "notMapped");

        mockMvc.perform(get(wrongEndpoint))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Buscar Cartao Id")
    void buscarCartaoId() throws Exception {
        String searchOneUrl = String.format("%s/{cartaoId}", BASE_URL);
        String mockedCartao = "68c57a52-21b0-4fd9-9d1b-27179ce255c1";

        Cartao mockedFoundCartao;

        ArrayList<Transacao> transacoes = new ArrayList<Transacao>();
        Transacao transacao = new Transacao();
        DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        transacao.setEstabelecimento ("Escola Teste");
        transacao.setValor (BigDecimal.valueOf(1250));
        transacao.setTipo (TipoTransacaoEnum.valueOf("CREDITO"));
        transacao.setParcelas(2);
        transacao.setDataHora(LocalDateTime.from(dtf.parse("03/04/2022 23:27:20")));

        transacoes.add(transacao);
        mockedFoundCartao = new Cartao("68c57a52-21b0-4fd9-9d1b-27179ce255c1", "John Doe", transacoes);

        given(cartaoService.buscarCartao(anyString())).willReturn(Optional.of(mockedFoundCartao));

        mockMvc.perform(get(searchOneUrl, mockedCartao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Buscar Transacao")
    void buscarTransacao() throws Exception {
        String searchOneUrl = String.format("%s/{cartaoId}/transacoes", BASE_URL);
        String mockedCartao = "68c57a52-21b0-4fd9-9d1b-27179ce255c1";

        Cartao mockedFoundCartao;

        ArrayList<Transacao> transacoes = new ArrayList<Transacao>();
        Transacao transacao = new Transacao();
        DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        transacao.setEstabelecimento ("Escola Teste");
        transacao.setValor (BigDecimal.valueOf(1250));
        transacao.setTipo (TipoTransacaoEnum.valueOf("CREDITO"));
        transacao.setParcelas(2);
        transacao.setDataHora(LocalDateTime.from(dtf.parse("03/04/2022 23:27:20")));

        transacoes.add(transacao);
        mockedFoundCartao = new Cartao("68c57a52-21b0-4fd9-9d1b-27179ce255c1", "John Doe", transacoes);

        given(cartaoService.buscarCartao(anyString())).willReturn(Optional.of(mockedFoundCartao));

        mockMvc.perform(get(searchOneUrl, mockedCartao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}