package niels.jani.habitarium;

import org.junit.Test;

import static niels.jani.habitarium.TestProperties.propertyValue;
import static org.assertj.core.api.Assertions.assertThat;

public class TestPropertiesTest {

    @Test
    public void getExistingProperty() {
        assertThat(propertyValue("testproperties.test")).isEqualTo("property value");
    }

    @Test
    public void getNonExistingProperty() {
        try {
            propertyValue("non-existing-property");
        } catch (MissingPropertyException exc) {
            assertThat(exc.getMessage())
                    .contains("missing property")
                    .contains("non-existing-property");
        }
    }
}