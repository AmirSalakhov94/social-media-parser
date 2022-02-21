package com.mirsalakhov.socialmediaparser.ig.config;

import com.mirsalakhov.socialmediaparser.ig.provider.IgClientProvider;
import com.mirsalakhov.socialmediaparser.ig.service.IgClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IgConfig {

    private final static int COUNT_CLIENT = 1;

    private final IgClientService igClientService;

    @Bean
    public IgClientProvider igClientProvider() {
        IgClientProvider igClientProvider = new IgClientProvider();
        igClientService.getFreeClients(COUNT_CLIENT)
                .forEach(igClientProvider::addClient);

        return igClientProvider;
    }
}
