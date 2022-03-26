package com.fmarxds.fiapspringcartaocredito.config;

import com.fmarxds.fiapspringcartaocredito.batch.AlunoItemProcessor;
import com.fmarxds.fiapspringcartaocredito.batch.listener.JobCompletionNotificationListener;
import com.fmarxds.fiapspringcartaocredito.model.Aluno;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Aluno> reader() {
        return new FlatFileItemReaderBuilder<Aluno>()
                .name("alunoItemReader")
                .resource(new ClassPathResource("batch/lista_alunos.csv"))
                .delimited()
                .names(new String[]{"nome", "matricula", "turma"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Aluno.class);
                }})
                .build();
    }

    @Bean
    public AlunoItemProcessor processor() {
        return new AlunoItemProcessor();
    }

    @Bean
    public MongoItemWriter<Aluno> writer(MongoOperations mongoOperations) {
        return new MongoItemWriterBuilder<Aluno>()
                .collection("aluno")
                .template(mongoOperations)
                .build();

    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importAlunoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(MongoItemWriter<Aluno> writer) {
        return stepBuilderFactory.get("step1")
                .<Aluno, Aluno>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}
