package socialmediaparser.common;

import lombok.Getter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
public class DelayedItem<T> implements Delayed {

    private final T item;
    private final long startTime;

    public DelayedItem(T item, long delayInMilliseconds) {
        this.item = item;
        this.startTime = TimeUnit.NANOSECONDS.convert(delayInMilliseconds, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        if (d == 0)
            return 0;

        return (d > 0) ? 1 : -1;
    }
}
