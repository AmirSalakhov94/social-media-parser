package socialmediaparser.ig.batch.step;

import dto.ig.IgProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import socialmediaparser.ig.service.IgParser;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static socialmediaparser.ig.batch.consts.ParseBatchConsts.PARSED_PROFILES;
import static socialmediaparser.ig.batch.consts.ParseBatchConsts.TRANSACTION_ID;

@RequiredArgsConstructor
public record ProfilesParseLauncher(IgParser igParser, Map<String, List<String>> profilesMap) implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, @Nullable ChunkContext chunkContext) {
        StepExecution stepExecution = contribution.getStepExecution();
        JobExecution jobExecution = stepExecution.getJobExecution();
        JobParameters jobParameters = jobExecution.getJobParameters();
        String transactionId = (String) jobParameters.getParameters().get(TRANSACTION_ID).getValue();

        Set<IgProfile> parsedProfiles = profilesMap.remove(transactionId)
                .stream()
                .map(igParser::getProfileInfo)
                .collect(Collectors.toSet());

        ExecutionContext executionContext = jobExecution.getExecutionContext();
        executionContext.put(PARSED_PROFILES, parsedProfiles);
        contribution.setExitStatus(ExitStatus.COMPLETED);

        return RepeatStatus.FINISHED;
    }
}
