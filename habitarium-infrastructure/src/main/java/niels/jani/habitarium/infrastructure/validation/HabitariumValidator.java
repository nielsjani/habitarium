package niels.jani.habitarium.infrastructure.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.validation.Validation.buildDefaultValidatorFactory;

public class HabitariumValidator {

    private static HabitariumValidator instance;

    private Validator validator;

    private HabitariumValidator() {
        validator = buildDefaultValidatorFactory().getValidator();
    }

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(
                constraintViolationErrorMessage(violations),
                new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    private String constraintViolationErrorMessage(Set<ConstraintViolation<Object>> violations) {
        Set<String> violationMessages = violations.stream()
            .map(violation -> String.format("%s#%s can not have value %s because %s", violation.getRootBeanClass(), violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()))
            .collect(toSet());
        return StringUtils.join(violationMessages, ", ");
    }

    public static HabitariumValidator validator() {
        if (instance == null) {
            instance = new HabitariumValidator();
        }
        return instance;
    }
}
