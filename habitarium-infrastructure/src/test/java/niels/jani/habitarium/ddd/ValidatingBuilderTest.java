package niels.jani.habitarium.ddd;

import niels.jani.habitarium.infrastructure.ddd.BaseEntity;
import niels.jani.habitarium.infrastructure.ddd.Builder;
import niels.jani.habitarium.infrastructure.ddd.Id;
import niels.jani.habitarium.test.UnitTest;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import static java.util.UUID.randomUUID;
import static niels.jani.habitarium.ddd.ValidatingBuilderTest.TestBaseEntity.TestBuilder.testEntity;

public class ValidatingBuilderTest extends UnitTest {

    @Test(expected = ConstraintViolationException.class)
    public void builderValideertJavaxValidationAnnotations() {
        testEntity().withNaam("naam").build();
    }

    @Test(expected = ConstraintViolationException.class)
    public void builderValideertHibernateAnnotations() {
        testEntity().withId(TestId.testId()).build();
    }

    @Test
    public void builderKanValidObjectsBuilden() {
        TestBaseEntity testObject = testEntity().withId(TestId.testId()).withNaam("naam").build();
        Assertions.assertThat(testObject).isNotNull();
    }

    public static class TestId extends Id {

        private TestId() {
            super(randomUUID().toString());
        }

        public static TestId testId() {
            return new TestId();
        }

    }

    static class TestBaseEntity extends BaseEntity<TestId> {

        @NotNull
        private TestId id;
        @NotEmpty(message = "Naam moet ingevuld zijn.") // hibernate validation
        private String naam;

        private TestBaseEntity(TestBuilder builder) {
            this.id = builder.id;
            this.naam = builder.naam;
        }

        @Override
        public TestId getId() {
            return id;
        }

        static class TestBuilder extends Builder<TestBaseEntity> {

            private TestId id;
            private String naam;

            private TestBuilder() {
            }

            public static TestBuilder testEntity() {
                return new TestBuilder();
            }

            @Override
            protected TestBaseEntity buildInternal() {
                return new TestBaseEntity(this);
            }

            public TestBuilder withId(TestId id) {
                this.id = id;
                return this;
            }

            public TestBuilder withNaam(String naam) {
                this.naam = naam;
                return this;
            }
        }
    }
}
