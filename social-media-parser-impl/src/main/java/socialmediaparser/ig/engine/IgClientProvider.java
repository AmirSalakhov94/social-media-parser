package socialmediaparser.ig.engine;

import com.github.instagram4j.instagram4j.IGClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import socialmediaparser.common.DelayedItem;

import java.util.Queue;

@Slf4j
@Component
@RequiredArgsConstructor
public class IgClientProvider {

    private static final int DELAYED_TIME_IN_MILL = 5000;

    private final Queue<DelayedItem<IGClient>> clients;

    public IGClient getClient() {
        while (clients.isEmpty()) {
            log.info("All clients are busy so far");
        }

        return clients.poll().getItem();
    }

    public void addClient(IGClient client) {
        clients.add(new DelayedItem<>(client, DELAYED_TIME_IN_MILL));
    }
}
