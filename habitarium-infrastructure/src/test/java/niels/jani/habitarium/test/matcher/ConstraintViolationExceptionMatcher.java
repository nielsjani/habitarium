package niels.jani.habitarium.test.matcher;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toSet;

public class ConstraintViolationExceptionMatcher extends TypeSafeMatcher<ConstraintViolationException> {

    private Set<String> expectedViolations;

    private ConstraintViolationExceptionMatcher(String... expectedViolations) {
        this.expectedViolations = newHashSet(expectedViolations);
    }

    @Override
    protected boolean matchesSafely(ConstraintViolationException item) {
        Set<String> actualViolations = item.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(toSet());
        return actualViolations.equals(expectedViolations);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("violation messages " + StringUtils.join(expectedViolations, ", "));
    }

    public static Matcher<ConstraintViolationException> constraintViolationExceptionContainingViolationMessage(String... expectedViolations) {
        return new ConstraintViolationExceptionMatcher(expectedViolations);
    }
}
