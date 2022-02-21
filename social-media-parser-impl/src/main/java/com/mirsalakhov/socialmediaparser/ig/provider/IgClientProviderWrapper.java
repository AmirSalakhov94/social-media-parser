package com.mirsalakhov.socialmediaparser.ig.provider;

import com.github.instagram4j.instagram4j.IGClient;
import com.mirsalakhov.socialmediaparser.ig.service.IgClientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class IgClientProviderWrapper {

    private final IgClientProvider igClientProvider;
    private final IgClientService igClientService;
    private final AtomicInteger numberFallenClients = new AtomicInteger();

    @SneakyThrows
    public IGClient getClient() {
        int currentNumberFallenClients = numberFallenClients.get();
        if (currentNumberFallenClients == 0) {
            while (igClientProvider.clientsQueueIsEmpty()) {
                log.info("All clients are busy so far");
                Thread.sleep(1000);
            }
        } else {
            igClientService.getFreeClients(currentNumberFallenClients)
                    .forEach(igClientProvider::addClient);
        }

        return igClientProvider.getClient();
    }

    public void addClient(IGClient client) {
        igClientProvider.addClient(client);
    }

    public void incrementFallenClient() {
        numberFallenClients.incrementAndGet();
    }
}
