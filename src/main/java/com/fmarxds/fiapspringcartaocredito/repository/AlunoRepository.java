package com.fmarxds.fiapspringcartaocredito.repository;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlunoRepository extends MongoRepository<Aluno, String> {
}
