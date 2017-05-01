package niels.jani.habitarium.infrastructure.ddd;

import com.google.common.annotations.VisibleForTesting;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@MappedSuperclass
public abstract class AuditableValueObject extends ValueObject {

    @Valid
    @NotNull
    private final String id = randomUUID().toString();

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other, "id");
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this, "id");
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

    @VisibleForTesting
    public String getId() {
        return id;
    }
}
