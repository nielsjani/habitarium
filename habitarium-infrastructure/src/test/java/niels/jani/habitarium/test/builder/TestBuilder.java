package niels.jani.habitarium.test.builder;

import org.springframework.context.ApplicationContext;

public abstract class TestBuilder<T> {

    protected AnnotationResolver annotationResolver = new AnnotationResolver();

    public final T build() {
        annotationResolver.resolveForBuilding(this);
        return buildObject();
    }

    public final T persist(ApplicationContext context) {
        annotationResolver.resolveForPersisting(this, context);
        T object = buildObject();
        return persistIfNecessary(object, context);
    }

    protected abstract T buildObject();

    protected T persistIfNecessary(T object, ApplicationContext context) {
        return object;
    }
}
