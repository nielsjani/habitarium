package niels.jani.habitarium.test;

import niels.jani.habitarium.infrastructure.exception.HabitariumEntityBestaatNietException;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static niels.jani.habitarium.test.matcher.ConstraintViolationExceptionMatcher.constraintViolationExceptionContainingViolationMessage;


public abstract class RollingBackIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected void expectNoException() {
    }

    protected void expectException(Class<? extends Throwable> expectedException) {
        this.expectedException.expect(expectedException);
    }

    protected void expectExceptionWithMessage(Class<? extends Throwable> expectedException, String message) {
        this.expectedException.expect(expectedException);
        this.expectedException.expectMessage(message);
    }

    protected void expectExceptionWithCause(Class<? extends Throwable> expectedException, Matcher<? extends Throwable> cause) {
        this.expectedException.expect(expectedException);
        this.expectedException.expectCause(cause);
    }

    protected void expectIllegalArgumentExceptionWithMessage(String message) {
        expectExceptionWithMessage(IllegalArgumentException.class, message);
    }

    protected void expectIllegalStateExceptionWithMessage(String message) {
        expectExceptionWithMessage(IllegalStateException.class, message);
    }

    protected void expectConstraintViolationExceptionWithMessages(String... violations){
        expectedException.expect(constraintViolationExceptionContainingViolationMessage(violations));
    }

    protected void expectHabitariumEntityBestaatNietExceptionWithMessage(String message) {
        expectExceptionWithMessage(HabitariumEntityBestaatNietException.class, message);
    }
}
