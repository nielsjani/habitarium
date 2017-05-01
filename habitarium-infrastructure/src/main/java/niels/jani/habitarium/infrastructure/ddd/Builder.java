package niels.jani.habitarium.infrastructure.ddd;


import static niels.jani.habitarium.infrastructure.validation.HabitariumValidator.validator;

public abstract class Builder<T> {

    protected abstract T buildInternal();

    public final T build() {
        T object = buildInternal();
        validator().validate(object);
        return object;
    }
}
