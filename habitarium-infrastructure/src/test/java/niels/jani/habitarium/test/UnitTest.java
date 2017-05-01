package niels.jani.habitarium.test;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.fail;
import static niels.jani.habitarium.test.matcher.ConstraintViolationExceptionMatcher.constraintViolationExceptionContainingViolationMessage;

@RunWith(MockitoJUnitRunner.class)
public abstract class UnitTest {

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

    protected void expectIllegalArgumentExceptionWithMessage(String message) {
        expectExceptionWithMessage(IllegalArgumentException.class, message);
    }

    protected void expectIllegalStateExceptionWithMessage(String message) {
        expectExceptionWithMessage(IllegalStateException.class, message);
    }

    protected void expectConstraintViolationExceptionWithMessages(String... violations){
        expectedException.expect(constraintViolationExceptionContainingViolationMessage(violations));
    }

    protected void assertExceptionWithMessage(Runnable r, Class<?> exceptionType, String expectedMessagePart) {
        try {
            r.run();
        } catch (Exception e) {
            if (!e.getClass().equals(exceptionType)) {
                e.printStackTrace();
                fail(String.format("Expected exception of type '%s' but was '%s'", exceptionType, e.getClass()));
            }
            if (expectedMessagePart != null && !e.getMessage().contains(expectedMessagePart)) {
                e.printStackTrace();
                fail(String.format("Expected message containing '%s' but was '%s'", expectedMessagePart, e.getMessage()));
            }
            return;
        }
        fail(String.format("Expected exception of type '%s'", exceptionType));
    }

    protected void assertException(Runnable r, Class<?> exceptionType) {
        assertExceptionWithMessage(r, exceptionType, null);
    }
}
