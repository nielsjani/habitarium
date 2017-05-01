package niels.jani.habitarium.specification;

import niels.jani.habitarium.infrastructure.specification.Specification;

public class SpecificationTestUtil {

    private SpecificationTestUtil(){}

    public static TrueSpecification trueSpecification(){
        return new TrueSpecification();
    }

    public static FalseSpecification falseSpecification(){
        return new FalseSpecification();
    }

    private static class TrueSpecification implements Specification<String> {
        @Override
        public boolean isSatisfiedBy(String s) {
            return true;
        }
    }

    private static class FalseSpecification implements Specification<String> {
        @Override
        public boolean isSatisfiedBy(String s) {
            return false;
        }
    }
}
