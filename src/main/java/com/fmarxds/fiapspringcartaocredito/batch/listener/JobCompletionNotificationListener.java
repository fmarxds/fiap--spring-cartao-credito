package com.fmarxds.fiapspringcartaocredito.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() != BatchStatus.COMPLETED) {
            return;
        }

        log.info("### PROCESSO BATCH FINALIZADO: IMPORTAÇÃO DE ALUNOS ###");

    }

}
