package com.fmarxds.fiapspringcartaocredito.controller;

import com.fmarxds.fiapspringcartaocredito.service.ExtratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/extratos")
public class ExtratoController {

    private final ExtratoService extratoService;

    @GetMapping(
            value = "/matriculas/{matricula}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    byte[] downloadExtratoPdf(
            @PathVariable("matricula") String matricula,
            HttpServletResponse httpServletResponse
    ) {

        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"extrato.pdf\"");
        return extratoService.gerarExtratoPdf(matricula);

    }

}
