package niels.jani.habitarium.infrastructure.ddd;

import niels.jani.habitarium.test.UnitTest;
import org.junit.Test;

import static niels.jani.habitarium.infrastructure.ddd.ValueObjectTest.TestValueObject.testValueObject;
import static org.assertj.core.api.Assertions.assertThat;

public class ValueObjectTest extends UnitTest {

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftTrueTerug() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jos");

        boolean actual = valueObjectEen.equals(valueObjectTwee);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftFalseTerug() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jef");

        boolean actual = valueObjectEen.equals(valueObjectTwee);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenZelfdeObject_ThenGeeftTrueTerug() {
        TestValueObject valueObject = testValueObject("Jos");

        boolean actual = valueObject.equals(valueObject);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftTrueTerugInBeideRichtingen() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jos");

        boolean actualEenTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualTweeEen = valueObjectTwee.equals(valueObjectEen);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeEen).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftFalseTerugInBeideRichtingen() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jef");

        boolean actualEenTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualTweeEen = valueObjectTwee.equals(valueObjectEen);

        assertThat(actualEenTwee).isFalse();
        assertThat(actualTweeEen).isFalse();
    }

    @Test
    public void equals_GivenDrieObjectenHebbenZelfdeVelden_ThenGeeftTransitiefTrueTerug() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jos");
        TestValueObject valueObjectDrie = testValueObject("Jos");

        boolean actualEenTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualTweeDrie = valueObjectTwee.equals(valueObjectDrie);
        boolean actualEenDrie = valueObjectEen.equals(valueObjectDrie);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeDrie).isTrue();
        assertThat(actualEenDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftConsistentTrueTerug() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jos");

        boolean actualEen = valueObjectEen.equals(valueObjectTwee);
        boolean actualTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualDrie = valueObjectEen.equals(valueObjectTwee);

        assertThat(actualEen).isTrue();
        assertThat(actualTwee).isTrue();
        assertThat(actualDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftConsistentFalseTerug() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jef");

        boolean actualEen = valueObjectEen.equals(valueObjectTwee);
        boolean actualTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualDrie = valueObjectEen.equals(valueObjectTwee);

        assertThat(actualEen).isFalse();
        assertThat(actualTwee).isFalse();
        assertThat(actualDrie).isFalse();
    }

    @Test
    public void equals_GivenObjectVergelijkenMetNull_ThenGeeftFalseTerug() {
        TestValueObject valueObject = testValueObject("Jos");

        boolean actual = valueObject.equals(null);

        assertThat(actual).isFalse();
    }

    @Test
    public void hashCode_GivenObject_ThenGeeftConsistentZelfdeHashCodeTerug() {
        TestValueObject valueObject = testValueObject("Jos");

        int hashCodeEen = valueObject.hashCode();
        int hashCodeTwee = valueObject.hashCode();

        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void hashCode_GivenObjectenZijnEquals_ThenGeeftZelfdeHashCodeTerug() {
        TestValueObject valueObjectEen = testValueObject("Jos");
        TestValueObject valueObjectTwee = testValueObject("Jos");

        boolean isEqual = valueObjectEen.equals(valueObjectTwee);
        int hashCodeEen = valueObjectEen.hashCode();
        int hashCodeTwee = valueObjectTwee.hashCode();

        assertThat(isEqual).isTrue();
        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void toString_ThenGeeftStringMetAllVeldenTerug() {
        TestValueObject valueObject = testValueObject("Jos");

        String actual = valueObject.toString();

        assertThat(actual).contains(valueObject.getClass().getName());
        assertThat(actual).contains("naam=Jos");
    }

    public static class TestValueObject extends ValueObject {

        private String naam;

        private TestValueObject(String naam){
            this.naam = naam;
        }

        public static TestValueObject testValueObject(String naam) {
            return new TestValueObject(naam);
        }
    }
}
