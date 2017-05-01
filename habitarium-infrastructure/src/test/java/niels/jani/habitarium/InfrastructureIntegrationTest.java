package niels.jani.habitarium;

import niels.jani.habitarium.infrastructure.spring.InfrastructureConfig;
import niels.jani.habitarium.test.RollingBackIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;

@ContextConfiguration(classes = {
        //TODO: HABIT-0
//        PropertiesConfig.class,
    InfrastructureConfig.class
})
public abstract class InfrastructureIntegrationTest extends RollingBackIntegrationTest {

    @Inject
    protected ApplicationContext context;
}
