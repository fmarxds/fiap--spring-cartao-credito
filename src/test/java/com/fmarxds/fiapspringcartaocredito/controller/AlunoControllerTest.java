package com.fmarxds.fiapspringcartaocredito.controller;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import com.fmarxds.fiapspringcartaocredito.service.AlunoService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AlunoControllerTest {

    @Mock
    AlunoService alunoService;
    @InjectMocks
    AlunoController controller;

    MockMvc mockMvc;

    private final String BASE_URL = "/v1/alunos";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Buscar lista")
    void buscarAlunos() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse());
    }

    @Test
    @DisplayName("Endpoint inválido")
    void endpointNotFound() throws Exception {
        String wrongEndpoint = String.format("%s/%s", BASE_URL, "notMapped");

        mockMvc.perform(get(wrongEndpoint))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Buscar aluno")
    void buscarAluno() throws Exception {
        String searchOneUrl = String.format("%s/{matricula}", BASE_URL);
        String mockedMatricula = "12345";

        Aluno mockedFoundAluno = new Aluno();

        given(alunoService.buscarAluno(anyString())).willReturn(Optional.of(mockedFoundAluno));

        mockMvc.perform(get(searchOneUrl, mockedMatricula))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Cadastrar aluno")
    void cadastrarAluno() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matricula", "123212");
        json.put("nome", "John Doe");
        json.put("turma", "Mocked turma");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar aluno sem matricula")
    void cadastrarAlunoWithoutMatricula() throws Exception {
        JSONObject json = new JSONObject();

        json.put("nome", "John Doe");
        json.put("turma", "Mocked turma");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar aluno sem nome")
    void cadastrarAlunoWithoutNome() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matricula", "123212");
        json.put("turma", "Mocked turma");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar aluno sem turma")
    void cadastrarAlunoWithoutTurma() throws Exception {
        JSONObject json = new JSONObject();

        json.put("matricula", "123212");
        json.put("nome", "John Doe");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar aluno sem nada")
    void cadastrarAlunoWithNothing() throws Exception {
        mockMvc.perform(post(BASE_URL))
                .andExpect(status().isBadRequest());
    }
}