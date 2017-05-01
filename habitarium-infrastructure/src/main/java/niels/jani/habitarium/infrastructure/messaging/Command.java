package niels.jani.habitarium.infrastructure.messaging;


import niels.jani.habitarium.infrastructure.ddd.Id;
import niels.jani.habitarium.infrastructure.ddd.ValueObject;

import javax.validation.constraints.NotNull;

public abstract class Command<ID extends Id> extends ValueObject {

    @NotNull
    private final ID aggregateId;
    @NotNull
    private final Integer versie;

    protected Command(ID aggregateId, int versie) {
        this.aggregateId = aggregateId;
        this.versie = versie;
    }

    public ID getAggregateId() {
        return aggregateId;
    }

    public int getVersie() {
        return versie;
    }
}
