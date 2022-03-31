package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.service.CartaoService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtratoServiceImplTest {

    private static final String PATH_EXTRATO_TEMPLATE_HTML = "classpath:templates/extrato_template.html";
    
    @Mock CartaoService cartaoService;

    @InjectMocks ExtratoServiceImpl service;

    @Test
    void gerarExtratoPdf() {
        List<Cartao> mockedCards = new ArrayList<>();

        when(cartaoService.buscarCartoes(anyString())).thenReturn(mockedCards);

        byte[] extratoPDF = service.gerarExtratoPdf("12345");

        assertNotNull(extratoPDF);
    }
}