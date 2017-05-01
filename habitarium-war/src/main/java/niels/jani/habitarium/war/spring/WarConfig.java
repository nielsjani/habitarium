package niels.jani.habitarium.war.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        value = WarConfig.WAR_BASE_PACKAGE,
        excludeFilters = @ComponentScan.Filter(type = ANNOTATION, value = Configuration.class))
public class WarConfig {

    public static final String WAR_BASE_PACKAGE = "niels.jani.habitarium.war";

    @Inject
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
