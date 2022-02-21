package com.mirsalakhov.socialmediaparser.ig.provider;

import com.github.instagram4j.instagram4j.IGClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.mirsalakhov.socialmediaparser.common.DelayedItem;

import java.util.Queue;
import java.util.concurrent.DelayQueue;

@Slf4j
@Component
public class IgClientProvider {

    private static final int DELAYED_TIME_IN_MILL = 5000;

    private final Queue<DelayedItem<IGClient>> clientsQueue = new DelayQueue<>();

    public IGClient getClient() {
        while (clientsQueue.isEmpty()) {
            log.info("All clients are busy so far");
        }

        return clientsQueue.poll().getItem();
    }

    public void addClient(IGClient client) {
        clientsQueue.add(new DelayedItem<>(client, DELAYED_TIME_IN_MILL));
    }
}
