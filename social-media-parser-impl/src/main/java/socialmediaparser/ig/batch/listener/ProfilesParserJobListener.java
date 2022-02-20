package socialmediaparser.ig.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.UUID;

import static socialmediaparser.ig.batch.consts.ParseBatchConsts.TRANSACTION_ID;

public record ProfilesParserJobListener() implements JobExecutionListener {

    public void beforeJob(JobExecution jobExecution) {
        long jobId = jobExecution.getJobInstance().getId();
        UUID transactionId = (UUID) jobExecution.getJobParameters().getParameters().get(TRANSACTION_ID).getValue();
    }

    public void afterJob(JobExecution jobExecution) {
        UUID transactionId = (UUID) jobExecution.getJobParameters().getParameters().get(TRANSACTION_ID).getValue();
    }
}