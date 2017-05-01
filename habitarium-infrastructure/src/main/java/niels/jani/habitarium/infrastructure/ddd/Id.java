package niels.jani.habitarium.infrastructure.ddd;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Access;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static javax.persistence.AccessType.FIELD;

@MappedSuperclass
@Access(FIELD)
public abstract class Id extends ValueObject implements Serializable{

    @NotNull
    private final String value;

    protected Id() {
        this.value = null;
    }

    protected Id(String value) {
        this.value = value;
        Preconditions.checkArgument(StringUtils.isNotBlank(value), "Value moet ingevuld zijn.");
    }

    public String getValue() {
        return value;
    }
}
