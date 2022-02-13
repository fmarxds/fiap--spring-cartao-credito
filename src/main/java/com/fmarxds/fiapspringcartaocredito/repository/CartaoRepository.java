package com.fmarxds.fiapspringcartaocredito.repository;

import com.fmarxds.fiapspringcartaocredito.model.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartaoRepository extends MongoRepository<Cartao, String> {

    List<Cartao> findAllByMatriculaAlunoEquals(String matricula);

}
