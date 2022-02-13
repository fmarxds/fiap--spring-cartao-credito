package com.fmarxds.fiapspringcartaocredito.controller;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import com.fmarxds.fiapspringcartaocredito.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<Collection<Aluno>> buscarAlunos() {

        return ResponseEntity.ok(alunoService.buscarAlunos());

    }

    @GetMapping("/{matricula} ")
    public ResponseEntity<Aluno> buscarAluno(
            @PathVariable("matricula") String matricula
    ) {

        return ResponseEntity.of(alunoService.buscarAluno(matricula));

    }

    @PostMapping
    public ResponseEntity<Aluno> cadastrarAluno(
            @RequestBody Aluno aluno
    ) {

        return new ResponseEntity<>(alunoService.cadastrarAluno(aluno), HttpStatus.CREATED);

    }

}
