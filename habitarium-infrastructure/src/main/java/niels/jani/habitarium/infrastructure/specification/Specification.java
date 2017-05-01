package niels.jani.habitarium.infrastructure.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T t);

    default Specification<T> en(Specification<T> other){
        return AndSpecification.and(this, other);
    }

    default Specification<T> of(Specification<T> other) {
        return OrSpecification.or(this, other);
    }
}
