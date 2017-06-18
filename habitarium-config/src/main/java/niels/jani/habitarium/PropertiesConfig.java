package niels.jani.habitarium;

import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:habitarium.default.properties", ignoreResourceNotFound = false),
        @PropertySource(value = "classpath:${HABIT_ENV}.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${PROPERTY_FILE_LOCATION}", ignoreResourceNotFound = true)
})
public class PropertiesConfig {

    @Bean
    public static PropertyResourceConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
