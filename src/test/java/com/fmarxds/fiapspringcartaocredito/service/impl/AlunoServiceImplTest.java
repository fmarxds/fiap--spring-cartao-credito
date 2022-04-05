package com.fmarxds.fiapspringcartaocredito.service.impl;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import com.fmarxds.fiapspringcartaocredito.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceImplTest {

    @Mock AlunoRepository alunoRepository;

    @InjectMocks AlunoServiceImpl service;

    @Test
    void buscarAlunos() {
        Aluno mockedAluno = new Aluno();
        List<Aluno> mockedLista = new ArrayList<>();

        mockedLista.add(mockedAluno);

        when(alunoRepository.findAll()).thenReturn(mockedLista);

        List<Aluno> alunosEncontrados = service.buscarAlunos();

        assertNotNull(alunosEncontrados, "Alunos não encontrados");

        verify(alunoRepository, atMost(1)).findAll();
    }

    @Test
    void whenIdValid_buscarAluno() {
        Aluno mockedAluno = new Aluno();

        when(alunoRepository.findById(anyString())).thenReturn(Optional.of(mockedAluno));

        Aluno alunoEncontrado = service.buscarAluno("12345").orElse(null);

        assertNotNull(alunoEncontrado);

        verify(alunoRepository, atMost(1)).findById(anyString());
    }

    @Test
    void whenIdInvalid_buscarAluno() {
        when(alunoRepository.findById(anyString())).thenReturn(null);

        assertNull(service.buscarAluno("23456"));

        verify(alunoRepository, atMost(1)).findById(anyString());
    }

    @Test
    void cadastrarAluno() {
        Aluno mockedAluno = new Aluno();

        when(alunoRepository.save(any(Aluno.class))).thenReturn(mockedAluno);

        Aluno alunoSalvo = service.cadastrarAluno(new Aluno());

        assertNotNull(alunoSalvo);

        verify(alunoRepository, atMost(1)).save(any(Aluno.class));
    }
}