package com.fmarxds.fiapspringcartaocredito.batch;

import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class AlunoItemProcessor implements ItemProcessor<Aluno, Aluno> {

    @Override
    public Aluno process(Aluno item) throws Exception {
        Aluno aluno = new Aluno(item.getMatricula(), WordUtils.capitalizeFully(item.getNome().toLowerCase()), item.getTurma());
        log.info("Processando aluno {}", aluno);
        return aluno;
    }

}
