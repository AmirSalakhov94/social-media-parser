package com.mirsalakhov.socialmediaparser.ig.batch.step;

import com.mirsalakhov.socialmediaparser.ig.dto.IgProfile;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import com.mirsalakhov.socialmediaparser.service.ProfileService;

import java.util.List;

import static com.mirsalakhov.socialmediaparser.ig.batch.consts.ParseBatchConsts.PARSED_PROFILES;

public record ProfilesWriter(ProfileService profileService) implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, @Nullable ChunkContext chunkContext) {
        StepExecution stepExecution = contribution.getStepExecution();
        JobExecution jobExecution = stepExecution.getJobExecution();

        List<IgProfile> igProfiles = (List<IgProfile>) jobExecution.getExecutionContext().get(PARSED_PROFILES);
        profileService.saveAll(igProfiles);

        contribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }
}
