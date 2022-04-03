package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.TipoTransacaoEnum;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;
import com.fmarxds.fiapspringcartaocredito.service.CartaoService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtratoServiceImplTest {

    @Mock
    CartaoService cartaoService;

    @Mock
    ResourceLoader resourceLoader;

    @InjectMocks
    ExtratoServiceImpl service;

    @Test
    void gerarExtratoPdf() throws Exception {

        Cartao mockedCard = Cartao.builder()
                .id("123456")
                .matriculaAluno("123456")
                .transacoes(List.of(Transacao.builder()
                        .tipo(TipoTransacaoEnum.CREDITO)
                        .dataHora(LocalDateTime.MAX)
                        .estabelecimento("Posto Ipanema")
                        .parcelas(2)
                        .valor(BigDecimal.TEN)
                        .build()))
                .build();

        List<Cartao> mockedCards = List.of(mockedCard);

        Path pathExtratoTemplate = Paths.get("src/test/resources/templates/extrato_template.html");
        Path pathExtratoLinhaTemplate = Paths.get("src/test/resources/templates/extrato_linha_template.html");
        Path pathExtratoCartaoTemplate = Paths.get("src/test/resources/templates/extrato_cartao_template.html");

        Resource resourceMockExtratoTemplate = mock(Resource.class);
        Resource resourceMockExtratoLinhaTemplate = mock(Resource.class);
        Resource resourceMockExtratoCartaoTemplate = mock(Resource.class);

        when(resourceMockExtratoTemplate.getInputStream()).thenReturn(Files.newInputStream(pathExtratoTemplate));
        when(resourceMockExtratoLinhaTemplate.getInputStream()).thenReturn(Files.newInputStream(pathExtratoLinhaTemplate));
        when(resourceMockExtratoCartaoTemplate.getInputStream()).thenReturn(Files.newInputStream(pathExtratoCartaoTemplate));

        when(resourceLoader.getResource(eq("classpath:templates/extrato_template.html"))).thenReturn(resourceMockExtratoTemplate);
        when(resourceLoader.getResource(eq("classpath:templates/extrato_linha_template.html"))).thenReturn(resourceMockExtratoLinhaTemplate);
        when(resourceLoader.getResource(eq("classpath:templates/extrato_cartao_template.html"))).thenReturn(resourceMockExtratoCartaoTemplate);

        when(cartaoService.buscarCartoes(anyString())).thenReturn(mockedCards);

        byte[] extratoPDF = service.gerarExtratoPdf("12345");

        assertNotNull(extratoPDF);

    }

}