package niels.jani.habitarium.test.builder;

import niels.jani.habitarium.infrastructure.ddd.AggregateRoot;
import org.springframework.context.ApplicationContext;

public abstract class PersistableTestBuilder<T extends AggregateRoot<?>> extends TestBuilder<T> {

    @Override
    protected final T persistIfNecessary(T object, ApplicationContext context) {
        return persistObject(object, context);
    }

    protected abstract T persistObject(T object, ApplicationContext context);
}
