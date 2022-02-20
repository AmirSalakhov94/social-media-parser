package socialmediaparser.ig.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import socialmediaparser.ig.batch.listener.ProfilesParserJobListener;
import socialmediaparser.ig.batch.step.ProfilesParseLauncher;
import socialmediaparser.ig.batch.step.ProfilesWriter;
import socialmediaparser.ig.service.IgParser;
import socialmediaparser.service.ProfileService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
public class ParseBatchConfig {

    private final IgParser igParser;
    private final ProfileService profileService;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Map<String, List<String>> profilesMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ProfilesParseLauncher profilesParseLauncher() {
        return new ProfilesParseLauncher(igParser, profilesMap());
    }

    @Bean
    public ProfilesWriter profilesWriter() {
        return new ProfilesWriter(profileService);
    }

    @Bean
    public Step profilesParseLauncherStep() {
        return stepBuilderFactory
                .get("profilesParseLauncherStep")
                .tasklet(profilesParseLauncher())
                .build();
    }

    @Bean
    public Step profilesWriterStep() {
        return stepBuilderFactory
                .get("profilesWriterStep")
                .tasklet(profilesWriter())
                .build();
    }

    @Bean
    public Job profilesParserJob() {
        return jobBuilderFactory
                .get("profilesParserJob")
                .listener(new ProfilesParserJobListener())
                .start(profilesParseLauncherStep()).on("COMPLETED").to(profilesWriterStep())
                .end().build();
    }

    @Bean
    public IntegrationFlow integrationFlow(ConnectionFactory connectionFactory) {
        return null;
    }
}
