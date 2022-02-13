package com.fmarxds.fiapspringcartaocredito.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "cartao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cartao {

    @MongoId(FieldType.STRING)
    @Field("id")
    @EqualsAndHashCode.Include
    private String id;

    @Field("matricula_aluno")
    private String matriculaAluno;

    @Field("transacoes")
    private List<Transacao> transacoes = new ArrayList<>();

}
