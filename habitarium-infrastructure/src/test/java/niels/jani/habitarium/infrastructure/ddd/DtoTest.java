package niels.jani.habitarium.infrastructure.ddd;

import niels.jani.habitarium.test.UnitTest;
import org.junit.Test;

import static niels.jani.habitarium.infrastructure.ddd.DtoTest.TestDto.testDto;
import static org.assertj.core.api.Assertions.assertThat;

public class DtoTest extends UnitTest {

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftTrueTerug() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("William", "Doe");

        boolean actual = dto1.equals(dto2);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftFalseTerug() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("Maeva", "Doe");

        boolean actual = dto1.equals(dto2);

        assertThat(actual).isFalse();
    }

    @Test
    public void equals_GivenZelfdeObject_ThenGeeftTrueTerug() {
        TestDto valueObject = testDto("William", "Doe");

        boolean actual = valueObject.equals(valueObject);

        assertThat(actual).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftTrueTerugInBeideRichtingen() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("William", "Doe");

        boolean actualEenTwee = dto1.equals(dto2);
        boolean actualTweeEen = dto2.equals(dto1);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeEen).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftFalseTerugInBeideRichtingen() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("Maeva", "Doe");

        boolean actualEenTwee = dto1.equals(dto2);
        boolean actualTweeEen = dto2.equals(dto1);

        assertThat(actualEenTwee).isFalse();
        assertThat(actualTweeEen).isFalse();
    }

    @Test
    public void equals_GivenDrieObjectenHebbenZelfdeVelden_ThenGeeftTransitiefTrueTerug() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("William", "Doe");
        TestDto valueObjectDrie = testDto("William", "Doe");

        boolean actualEenTwee = dto1.equals(dto2);
        boolean actualTweeDrie = dto2.equals(valueObjectDrie);
        boolean actualEenDrie = dto1.equals(valueObjectDrie);

        assertThat(actualEenTwee).isTrue();
        assertThat(actualTweeDrie).isTrue();
        assertThat(actualEenDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenZelfdeVelden_ThenGeeftConsistentTrueTerug() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("William", "Doe");

        boolean actualEen = dto1.equals(dto2);
        boolean actualTwee = dto1.equals(dto2);
        boolean actualDrie = dto1.equals(dto2);

        assertThat(actualEen).isTrue();
        assertThat(actualTwee).isTrue();
        assertThat(actualDrie).isTrue();
    }

    @Test
    public void equals_GivenObjectenHebbenVerschillendeVelden_ThenGeeftConsistentFalseTerug() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("Maeva", "Doe");

        boolean actualEen = dto1.equals(dto2);
        boolean actualTwee = dto1.equals(dto2);
        boolean actualDrie = dto1.equals(dto2);

        assertThat(actualEen).isFalse();
        assertThat(actualTwee).isFalse();
        assertThat(actualDrie).isFalse();
    }

    @Test
    public void equals_GivenObjectVergelijkenMetNull_ThenGeeftFalseTerug() {
        TestDto valueObject = testDto("William", "Doe");

        boolean actual = valueObject.equals(null);

        assertThat(actual).isFalse();
    }

    @Test
    public void hashCode_GivenObject_ThenGeeftConsistentZelfdeHashCodeTerug() {
        TestDto valueObject = testDto("William", "Doe");

        int hashCodeEen = valueObject.hashCode();
        int hashCodeTwee = valueObject.hashCode();

        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void hashCode_GivenObjectenZijnEquals_ThenGeeftZelfdeHashCodeTerug() {
        TestDto dto1 = testDto("William", "Doe");
        TestDto dto2 = testDto("William", "Doe");

        boolean isEqual = dto1.equals(dto2);
        int hashCodeEen = dto1.hashCode();
        int hashCodeTwee = dto2.hashCode();

        assertThat(isEqual).isTrue();
        assertThat(hashCodeEen).isEqualTo(hashCodeTwee);
    }

    @Test
    public void toString_ThenGeeftStringMetAllVeldenTerugInShortPrefixStyle() {
        TestDto valueObject = testDto("William", "Doe");

        String actual = valueObject.toString();

        assertThat(actual).contains(valueObject.getClass().getSimpleName());
        assertThat(actual).contains("TestDto[veld1=William,veld2=Doe]");
    }

    static class TestDto extends Dto {

        private String veld1;
        private String veld2;

        TestDto(String veld1, String veld2) {
            this.veld1 = veld1;
            this.veld2 = veld2;
        }

        static TestDto testDto(String veld1, String veld2) {
            return new TestDto(veld1, veld2);
        }
    }
}
