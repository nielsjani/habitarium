package niels.jani.habitarium.test.poller;

public class Sleeper {

    public static final int DEFAULT_SLEEP_TIME = 5000;

    public static void sleep() {
        sleep(DEFAULT_SLEEP_TIME);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
