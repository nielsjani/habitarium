package niels.jani.habitarium.test.poller;

import java.util.concurrent.TimeUnit;

public class Poller {

    private static final int POLLING_INTERVAL_MS = 100;
    private static final long TIMEOUT_MS = 100 * 1000;

    private int pollingInterval = POLLING_INTERVAL_MS;
    private long timeout = TIMEOUT_MS;
    private int initialWait = 0;
    private Condition condition;

    private Poller() {
    }

    public static Poller aPoller() {
        return new Poller();
    }

    public void doAssert(Assertion assertion) {
        withCondition(new Condition(assertion)).poll();
    }

    public void doAssert(Runnable assertion) {
        withCondition(new Condition(assertion)).poll();
    }

    public void execute(Runnable runnable) {
        withCondition(new Condition(runnable)).poll();
    }

    public Poller withCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public Poller withTimeout(long timeout, TimeUnit unit) {
        this.timeout = unit.toMillis(timeout);
        return this;
    }

    public Poller withInitialWait(int initialWait) {
        this.initialWait = initialWait;
        return this;
    }

    public Poller withPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public void poll() {
        Sleeper.sleep(initialWait);

        long startTime = System.currentTimeMillis();
        while (!condition.validate() && getDurationSince(startTime) < timeout) {
            Sleeper.sleep(pollingInterval);
        }
        if (getDurationSince(startTime) >= timeout) {
            throwException(condition.throwableToThrowAfterTimeout());
        }
    }

    private void throwException(Throwable throwable) {
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        throw new RuntimeException(throwable);
    }

    private long getDurationSince(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

}
