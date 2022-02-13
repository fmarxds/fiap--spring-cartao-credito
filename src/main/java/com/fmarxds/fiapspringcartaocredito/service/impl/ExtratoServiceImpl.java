package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import com.fmarxds.fiapspringcartaocredito.model.Transacao;
import com.fmarxds.fiapspringcartaocredito.service.CartaoService;
import com.fmarxds.fiapspringcartaocredito.service.ExtratoService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtratoServiceImpl implements ExtratoService {

    private final ResourceLoader resourceLoader;
    private final CartaoService cartaoService;

    private static final String PATH_EXTRATO_TEMPLATE_HTML = "classpath:templates/extrato_template.html";
    private static final String PATH_EXTRATO_CARTAO_TEMPLATE_HTML = "classpath:templates/extrato_cartao_template.html";
    private static final String PATH_EXTRATO_LINHA_TEMPLATE_HTML = "classpath:templates/extrato_linha_template.html";

    private static final String PLACEHOLDER_MATRICULA = "{{matricula}}";
    private static final String PLACEHOLDER_CARTAO_ID = "{{cartao_id}}";
    private static final String PLACEHOLDER_EXTRATO_CARTAO_TEMPLATE = "{{extrato_cartao_template}}";
    private static final String PLACEHOLDER_EXTRATO_LINHAS_TEMPLATE = "{{extrato_linhas_template}}";
    private static final String PLACEHOLDER_LINHA_NUMERO_LINHA = "{{numero_linha}}";
    private static final String PLACEHOLDER_LINHA_ESTABELECIMENTO = "{{estabelecimento}}";
    private static final String PLACEHOLDER_LINHA_VALOR = "{{valor}}";
    private static final String PLACEHOLDER_LINHA_TIPO = "{{tipo}}";
    private static final String PLACEHOLDER_LINHA_PARCELAS = "{{parcelas}}";
    private static final String PLACEHOLDER_LINHA_DATA_HORA = "{{data_hora}}";

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    @SneakyThrows
    public byte[] gerarExtratoPdf(String matricula) {

        Resource templateExtratoHtml = resourceLoader.getResource(PATH_EXTRATO_TEMPLATE_HTML);

        try (
                var templateHtmlInputStream = templateExtratoHtml.getInputStream();
                var outputStream = new ByteArrayOutputStream();
                var pdfWriter = new PdfWriter(outputStream);
                var pdfDocument = new PdfDocument(pdfWriter);
        ) {

            List<Cartao> cartoes = cartaoService.buscarCartoes(matricula);

            String extratoHtml = construirExtratoHtml(matricula, templateHtmlInputStream, cartoes);

            HtmlConverter.convertToPdf(extratoHtml, pdfDocument, new ConverterProperties());

            return outputStream.toByteArray();

        }

    }

    private String construirExtratoHtml(String matricula, InputStream templateHtmlInputStream, List<Cartao> cartoes) throws IOException {

        String templateHtml = IOUtils.toString(templateHtmlInputStream, Charset.defaultCharset());

        templateHtml = templateHtml.replace(PLACEHOLDER_MATRICULA, matricula);
        templateHtml = templateHtml.replace(PLACEHOLDER_EXTRATO_CARTAO_TEMPLATE, construirHtmlCartoes(cartoes));

        return templateHtml;

    }

    private String construirHtmlCartoes(List<Cartao> cartoes) throws IOException {

        StringBuilder sbCartoes = new StringBuilder();

        for (Cartao cartao : cartoes) {

            String linhasCartaoHtml = construirHtmlLinhasCartao(cartao);
            Resource templateCartaoHtml = resourceLoader.getResource(PATH_EXTRATO_CARTAO_TEMPLATE_HTML);

            try (var conteudoCartaoHtml = templateCartaoHtml.getInputStream()) {
                String cartaoHtml = IOUtils.toString(conteudoCartaoHtml, Charset.defaultCharset());
                cartaoHtml = cartaoHtml.replace(PLACEHOLDER_CARTAO_ID, cartao.getId());
                cartaoHtml = cartaoHtml.replace(PLACEHOLDER_EXTRATO_LINHAS_TEMPLATE, linhasCartaoHtml);
                sbCartoes.append(cartaoHtml);
            }

        }

        return sbCartoes.toString();
    }

    private String construirHtmlLinhasCartao(Cartao cartao) throws IOException {

        StringBuilder sbLinhas = new StringBuilder();

        for (int i = 0; i < cartao.getTransacoes().size(); i++) {

            Transacao transacao = cartao.getTransacoes().get(i);
            Resource templateLinhaHtml = resourceLoader.getResource(PATH_EXTRATO_LINHA_TEMPLATE_HTML);

            try (var conteudoLinhaHtml = templateLinhaHtml.getInputStream()) {
                String linhaHtml = IOUtils.toString(conteudoLinhaHtml, Charset.defaultCharset());
                linhaHtml = linhaHtml.replace(PLACEHOLDER_LINHA_NUMERO_LINHA, String.valueOf(i + 1));
                linhaHtml = linhaHtml.replace(PLACEHOLDER_LINHA_ESTABELECIMENTO, transacao.getEstabelecimento());
                linhaHtml = linhaHtml.replace(PLACEHOLDER_LINHA_VALOR, transacao.getValor().toPlainString());
                linhaHtml = linhaHtml.replace(PLACEHOLDER_LINHA_TIPO, transacao.getTipo().name());
                linhaHtml = linhaHtml.replace(PLACEHOLDER_LINHA_PARCELAS, transacao.getParcelas().toString());
                linhaHtml = linhaHtml.replace(PLACEHOLDER_LINHA_DATA_HORA, transacao.getDataHora().format(FORMATO_DATA_HORA));
                sbLinhas.append(linhaHtml);
            }
        }

        return sbLinhas.toString();
    }

}
