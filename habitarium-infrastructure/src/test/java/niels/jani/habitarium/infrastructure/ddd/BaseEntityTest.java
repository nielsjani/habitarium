package niels.jani.habitarium.infrastructure.ddd;

import niels.jani.habitarium.test.UnitTest;
import org.junit.Test;

import static java.util.UUID.randomUUID;
import static niels.jani.habitarium.infrastructure.ddd.BaseEntityTest.OtherTestBaseEntity.otherTestBaseEntity;
import static niels.jani.habitarium.infrastructure.ddd.BaseEntityTest.TestBaseEntity.testBaseEntity;
import static niels.jani.habitarium.infrastructure.ddd.BaseEntityTest.TestId.testId;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest extends UnitTest {

    @Test
    public void equals_GivenObjectenHebbenZelfdeId_ThenGeeftTrueTerug() {
        TestId id = testId();
        TestBaseEntity baseEntityEen = testBaseEntity(id, "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(id, "y");

        boolean actual = baseEntityEen.equals(baseEntityTwee);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeIds_ThenGeeftFalseTerug() {
        TestBaseEntity baseEntityEen = testBaseEntity(testId(), "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(testId(), "x");

        boolean actual = baseEntityEen.equals(baseEntityTwee);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenZelfdeObject_ThenGeeftTrueTerug() {
        TestBaseEntity baseEntity = testBaseEntity(testId(), "x");

        boolean actual = baseEntity.equals(baseEntity);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeIds_ThenGeeftTrueTerugInBeideRichtingen() {
        TestId id = testId();
        TestBaseEntity baseEntityEen = testBaseEntity(id, "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(id, "y");

        boolean actualEenTwee = baseEntityEen.equals(baseEntityTwee);
        boolean actualTweeEen = baseEntityTwee.equals(baseEntityEen);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeEen).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeIds_ThenGeeftFalseTerugInBeideRichtingen() {
        TestBaseEntity baseEntityEen = testBaseEntity(testId(), "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(testId(), "x");

        boolean actualEenTwee = baseEntityEen.equals(baseEntityTwee);
        boolean actualTweeEen = baseEntityTwee.equals(baseEntityEen);

        assertThat(actualEenTwee).isFalse();
        assertThat(actualTweeEen).isFalse();
    }

    @Test
    public void equals_GivenDrieObjectenHebbenZelfdeIds_ThenGeeftTransitiefTrueTerug() {
        TestId id = testId();
        TestBaseEntity baseEntityEen = testBaseEntity(id, "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(id, "y");
        TestBaseEntity baseEntityDrie = testBaseEntity(id, "z");

        boolean actualEenTwee = baseEntityEen.equals(baseEntityTwee);
        boolean actualTweeDrie = baseEntityTwee.equals(baseEntityDrie);
        boolean actualEenDrie = baseEntityEen.equals(baseEntityDrie);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeDrie).isTrue();
        assertThat(actualEenDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeIds_ThenGeeftConsistentTrueTerug() {
        TestId id = testId();
        TestBaseEntity baseEntityEen = testBaseEntity(id, "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(id, "y");

        boolean actualEen = baseEntityEen.equals(baseEntityTwee);
        boolean actualTwee = baseEntityEen.equals(baseEntityTwee);
        boolean actualDrie = baseEntityEen.equals(baseEntityTwee);

        assertThat(actualEen).isTrue();
        assertThat(actualTwee).isTrue();
        assertThat(actualDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeIds_ThenGeeftConsistentFalseTerug() {
        TestBaseEntity baseEntityEen = testBaseEntity(testId(), "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(testId(), "x");

        boolean actualEen = baseEntityEen.equals(baseEntityTwee);
        boolean actualTwee = baseEntityEen.equals(baseEntityTwee);
        boolean actualDrie = baseEntityEen.equals(baseEntityTwee);

        assertThat(actualEen).isFalse();
        assertThat(actualTwee).isFalse();
        assertThat(actualDrie).isFalse();
    }

    @Test
    public void equals_GivenObjectMetIdNull_ThenGeeftFalseTerug() {
        TestBaseEntity baseEntityNull = testBaseEntity(null, "x");
        TestBaseEntity baseEntity = testBaseEntity(testId(), "x");

        boolean actual = baseEntityNull.equals(baseEntity);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenAnderObjectMetIdNull_ThenGeeftFalseTerug() {
        TestBaseEntity baseEntity = testBaseEntity(testId(), "x");
        TestBaseEntity baseEntityNull = testBaseEntity(null, "x");

        boolean actual = baseEntity.equals(baseEntityNull);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenObjectVergelijkenMetNull_ThenGeeftFalseTerug() {
        TestBaseEntity baseEntity = testBaseEntity(testId(), "x");

        boolean actual = baseEntity.equals(null);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenObjectVergelijkenMetAndereKlasse_ThenGeeftFalseTerug() {
        TestBaseEntity baseEntity = testBaseEntity(testId(), "x");
        OtherTestBaseEntity otherBaseEntity = otherTestBaseEntity(testId(), "x");

        boolean actual = baseEntity.equals(otherBaseEntity);

        assertThat(actual).isFalse();
    }

    @Test
    public void hashCode_GivenObject_ThenGeeftConsistentZelfdeHashCodeTerug() {
        TestBaseEntity baseEntity = testBaseEntity(testId(), "x");

        int hashCodeEen = baseEntity.hashCode();
        int hashCodeTwee = baseEntity.hashCode();

        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void hashCode_GivenObjectenZijnEquals_ThenGeeftZelfdeHashCodeTerug() {
        TestId id = testId();
        TestBaseEntity baseEntityEen = testBaseEntity(id, "x");
        TestBaseEntity baseEntityTwee = testBaseEntity(id, "y");

        boolean isEqual = baseEntityEen.equals(baseEntityTwee);
        int hashCodeEen = baseEntityEen.hashCode();
        int hashCodeTwee = baseEntityTwee.hashCode();

        assertThat(isEqual).isTrue();
        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void toString_ThenGeeftStringMetKlasseEnId() {
        TestId id = testId();
        TestBaseEntity baseEntity = testBaseEntity(id, "x");

        String actual = baseEntity.toString();

        assertThat(actual).isEqualTo("TestBaseEntity: " + id.getValue());
    }

    static class TestId extends Id {

        private TestId() {
            super(randomUUID().toString());
        }

        public static TestId testId() {
            return new TestId();
        }

    }

    static class TestBaseEntity extends BaseEntity<TestId> {

        private TestId id;
        private String naam;

        private TestBaseEntity(TestId id, String naam) {
            this.id = id;
            this.naam = naam;
        }

        @Override
        public TestId getId() {
            return id;
        }

        public static TestBaseEntity testBaseEntity(TestId id, String naam) {
            return new TestBaseEntity(id, naam);
        }
    }

    static class OtherTestBaseEntity extends BaseEntity<TestId> {

        private TestId id;
        private String naam;

        private OtherTestBaseEntity(TestId id, String naam) {
            this.id = id;
            this.naam = naam;
        }

        @Override
        public TestId getId() {
            return id;
        }

        public static OtherTestBaseEntity otherTestBaseEntity(TestId id, String naam) {
            return new OtherTestBaseEntity(id, naam);
        }
    }
}
