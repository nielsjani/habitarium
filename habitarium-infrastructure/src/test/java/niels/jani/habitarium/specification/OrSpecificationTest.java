package niels.jani.habitarium.specification;

import niels.jani.habitarium.test.UnitTest;
import org.junit.Test;

import static niels.jani.habitarium.infrastructure.specification.OrSpecification.or;
import static niels.jani.habitarium.specification.SpecificationTestUtil.falseSpecification;
import static niels.jani.habitarium.specification.SpecificationTestUtil.trueSpecification;
import static org.assertj.core.api.Assertions.assertThat;


public class OrSpecificationTest extends UnitTest {

    @Test
    public void or_GivenLeftTrueRightFalse_ThenShouldReturnTrue() {
        assertThat(or(trueSpecification(), falseSpecification()).isSatisfiedBy("")).isTrue();
    }

    @Test
    public void or_GivenLeftFalseRightTrue_ThenShouldReturnTrue() {
        assertThat(or(falseSpecification(), trueSpecification()).isSatisfiedBy("")).isTrue();
    }

    @Test
    public void or_GivenLeftTrueRightTrue_ThenShouldReturnTrue() {
        assertThat(or(trueSpecification(), trueSpecification()).isSatisfiedBy("")).isTrue();
    }

    @Test
    public void or_GivenLeftFalseRightFalse_ThenShouldReturnFalse() {
        assertThat(or(falseSpecification(), falseSpecification()).isSatisfiedBy("")).isFalse();
    }
}
