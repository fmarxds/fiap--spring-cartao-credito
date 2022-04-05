package com.fmarxds.fiapspringcartaocredito.controller;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import com.fmarxds.fiapspringcartaocredito.service.ExtratoService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ExtratoControllerTest {

    @Mock
    ExtratoService extratoService;
    @InjectMocks
    ExtratoController controller;

    MockMvc mockMvc;

    private final String BASE_URL = "/v1/extratos/matriculas";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Buscar extrato")
    void downloadExtradoPdf() throws Exception {
        String searchOneUrl = String.format("%s/{matricula}", BASE_URL);
        String mockedMatricula = "12345";

        given(extratoService.gerarExtratoPdf(anyString())).willReturn(new byte[10]);

        mockMvc.perform(get(searchOneUrl, mockedMatricula)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Endpoint inválido")
    void endpointNotFound() throws Exception {
        String wrongEndpoint = String.format("%s/%s", BASE_URL, "notMapped /12345");

        mockMvc.perform(get(wrongEndpoint))
                .andExpect(status().isNotFound());
    }
}
