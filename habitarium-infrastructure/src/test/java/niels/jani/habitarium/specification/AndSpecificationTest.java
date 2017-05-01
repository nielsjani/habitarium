package niels.jani.habitarium.specification;

import niels.jani.habitarium.test.UnitTest;
import org.junit.Test;

import static niels.jani.habitarium.infrastructure.specification.AndSpecification.and;
import static niels.jani.habitarium.specification.SpecificationTestUtil.falseSpecification;
import static niels.jani.habitarium.specification.SpecificationTestUtil.trueSpecification;
import static org.assertj.core.api.Assertions.assertThat;


public class AndSpecificationTest extends UnitTest {

    @Test
    public void and_GivenLeftTrueAndRightTrue_ThenReturnTrue(){
        assertThat(and(trueSpecification(), trueSpecification()).isSatisfiedBy("")).isTrue();
    }

    @Test
    public void and_GivenLeftTrueAndRightFalse_ThenReturnFalse(){
        assertThat(and(trueSpecification(), falseSpecification()).isSatisfiedBy("")).isFalse();
    }

    @Test
    public void and_GivenLeftFalseAndRightTrue_ThenReturnFalse(){
        assertThat(and(falseSpecification(), trueSpecification()).isSatisfiedBy("")).isFalse();
    }

    @Test
    public void and_GivenLeftFalseAndRightfalse_ThenReturnFalse(){
        assertThat(and(falseSpecification(), falseSpecification()).isSatisfiedBy("")).isFalse();
    }
}
