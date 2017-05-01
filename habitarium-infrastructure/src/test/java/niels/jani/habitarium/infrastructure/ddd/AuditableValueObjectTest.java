package niels.jani.habitarium.infrastructure.ddd;

import niels.jani.habitarium.test.UnitTest;
import org.junit.Test;

import static niels.jani.habitarium.infrastructure.ddd.AuditableValueObjectTest.AuditableTestValueObject.auditableTestValueObject;
import static org.assertj.core.api.Assertions.assertThat;

public class AuditableValueObjectTest extends UnitTest {

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftTrueTerug() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jos");

        boolean actual = valueObjectEen.equals(valueObjectTwee);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftFalseTerug() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jef");

        boolean actual = valueObjectEen.equals(valueObjectTwee);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenZelfdeObject_ThenGeeftTrueTerug() {
        AuditableTestValueObject valueObject = auditableTestValueObject("Jos");

        boolean actual = valueObject.equals(valueObject);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftTrueTerugInBeideRichtingen() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jos");

        boolean actualEenTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualTweeEen = valueObjectTwee.equals(valueObjectEen);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeEen).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftFalseTerugInBeideRichtingen() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jef");

        boolean actualEenTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualTweeEen = valueObjectTwee.equals(valueObjectEen);

        assertThat(actualEenTwee).isFalse();
        assertThat(actualTweeEen).isFalse();
    }

    @Test
    public void equals_GivenDrieObjectenHebbenZelfdeVelden_ThenGeeftTransitiefTrueTerug() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectDrie = auditableTestValueObject("Jos");

        boolean actualEenTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualTweeDrie = valueObjectTwee.equals(valueObjectDrie);
        boolean actualEenDrie = valueObjectEen.equals(valueObjectDrie);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeDrie).isTrue();
        assertThat(actualEenDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftConsistentTrueTerug() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jos");

        boolean actualEen = valueObjectEen.equals(valueObjectTwee);
        boolean actualTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualDrie = valueObjectEen.equals(valueObjectTwee);

        assertThat(actualEen).isTrue();
        assertThat(actualTwee).isTrue();
        assertThat(actualDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftConsistentFalseTerug() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jef");

        boolean actualEen = valueObjectEen.equals(valueObjectTwee);
        boolean actualTwee = valueObjectEen.equals(valueObjectTwee);
        boolean actualDrie = valueObjectEen.equals(valueObjectTwee);

        assertThat(actualEen).isFalse();
        assertThat(actualTwee).isFalse();
        assertThat(actualDrie).isFalse();
    }

    @Test
    public void equals_GivenObjectVergelijkenMetNull_ThenGeeftFalseTerug() {
        AuditableTestValueObject valueObject = auditableTestValueObject("Jos");

        boolean actual = valueObject.equals(null);

        assertThat(actual).isFalse();
    }

    @Test
    public void hashCode_GivenObject_ThenGeeftConsistentZelfdeHashCodeTerug() {
        AuditableTestValueObject valueObject = auditableTestValueObject("Jos");

        int hashCodeEen = valueObject.hashCode();
        int hashCodeTwee = valueObject.hashCode();

        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void hashCode_GivenObjectenZijnEquals_ThenGeeftZelfdeHashCodeTerug() {
        AuditableTestValueObject valueObjectEen = auditableTestValueObject("Jos");
        AuditableTestValueObject valueObjectTwee = auditableTestValueObject("Jos");

        boolean isEqual = valueObjectEen.equals(valueObjectTwee);
        int hashCodeEen = valueObjectEen.hashCode();
        int hashCodeTwee = valueObjectTwee.hashCode();

        assertThat(isEqual).isTrue();
        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void toString_ThenGeeftStringMetAllVeldenTerug() {
        AuditableTestValueObject valueObject = auditableTestValueObject("Jos");

        String actual = valueObject.toString();

        assertThat(actual).contains(valueObject.getClass().getName());
        assertThat(actual).contains("naam=Jos");
    }

    public static class AuditableTestValueObject extends AuditableValueObject{

        private String naam;

        private AuditableTestValueObject(String naam){
            this.naam = naam;
        }

        public static AuditableTestValueObject auditableTestValueObject(String naam) {
            return new AuditableTestValueObject(naam);
        }
    }
}
