package com.mirsalakhov.socialmediaparser.ig.provider;

import com.github.instagram4j.instagram4j.IGClient;
import com.mirsalakhov.socialmediaparser.common.DelayedItem;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.DelayQueue;

@Component
public class IgClientProvider {

    private static final int DELAYED_TIME_IN_MILL = 5000;

    private final Queue<DelayedItem<IGClient>> clientsQueue = new DelayQueue<>();

    public IGClient getClient() {
        if (clientsQueue.isEmpty()) {
            throw new NullPointerException();
        }

        return clientsQueue.poll().getItem();
    }

    public void addClient(IGClient client) {
        clientsQueue.add(new DelayedItem<>(client, DELAYED_TIME_IN_MILL));
    }

    public boolean clientsQueueIsEmpty() {
        return clientsQueue.isEmpty();
    }
}
