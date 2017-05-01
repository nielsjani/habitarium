package niels.jani.habitarium.infrastructure.exception;

import java.util.UUID;

public class HabitariumException extends RuntimeException {

    private final String id;

    protected HabitariumException(String message, Throwable throwable, String id) {
        super(message, throwable);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static HabitariumException habitariumException(Throwable throwable) {
        String id = UUID.randomUUID().toString();
        return new HabitariumException(throwable.getMessage(), throwable, id);
    }

    public static HabitariumException habitariumExceptionWithIdAsMessage(Throwable throwable) {
        String id = UUID.randomUUID().toString();
        return new HabitariumException(id, throwable, id);
    }
}
