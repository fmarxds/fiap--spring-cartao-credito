package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import com.fmarxds.fiapspringcartaocredito.repository.AlunoRepository;
import com.fmarxds.fiapspringcartaocredito.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    @Override
    public List<Aluno> buscarAlunos() {
        return alunoRepository.findAll();
    }

    @Override
    public Optional<Aluno> buscarAluno(String matricula) {
        return alunoRepository.findById(matricula);
    }

    @Override
    public Aluno cadastrarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

}
