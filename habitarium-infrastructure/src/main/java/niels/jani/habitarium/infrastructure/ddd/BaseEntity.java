package niels.jani.habitarium.infrastructure.ddd;

import com.google.common.base.Objects;

public abstract class BaseEntity<ID extends Id> {

    public abstract ID getId();

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass() || this.getId() == null)
            return false;
        return this.getId().equals(((BaseEntity<?>) other).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getId().getValue();
    }
}

