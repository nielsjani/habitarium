package niels.jani.habitarium.infrastructure.exception;


import niels.jani.habitarium.infrastructure.ddd.Id;

import java.util.UUID;

public class HabitariumEntityBestaatNietException extends HabitariumException {

    private final String entityId;

    private HabitariumEntityBestaatNietException(String id, Id entityId) {
        super(String.format("Het opgezochte element (met %s %s) bestaat niet (meer).", entityId.getClass().getSimpleName(), entityId.getValue()), null, id);
        this.entityId = entityId.getValue();
    }

    public String getEntityId() {
        return entityId;
    }

    public static HabitariumEntityBestaatNietException habitariumEntityBestaatNietException(Id entityId) {
        return new HabitariumEntityBestaatNietException(UUID.randomUUID().toString(), entityId);
    }

}
