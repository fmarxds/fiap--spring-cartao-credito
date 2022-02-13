package com.fmarxds.fiapspringcartaocredito.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "aluno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Aluno {

    @MongoId(FieldType.STRING)
    @Field("matricula")
    @EqualsAndHashCode.Include
    private String matricula;

    @Field("nome")
    private String nome;

    @Field("turma")
    private String turma;

}
