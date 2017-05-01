package niels.jani.habitarium.infrastructure.messaging;

public interface CommandHandler{

    boolean canHandle(Command<?> command);

    void handle(Command<?> command);
}
