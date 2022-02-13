package com.fmarxds.fiapspringcartaocredito.service;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;

import java.util.List;
import java.util.Optional;

public interface AlunoService {

    List<Aluno> buscarAlunos();

    Optional<Aluno> buscarAluno(String matricula);

    Aluno cadastrarAluno(Aluno aluno);

}
