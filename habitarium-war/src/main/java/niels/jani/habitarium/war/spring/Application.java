package niels.jani.habitarium.war.spring;

import habitarium.domain.spring.DomainConfig;
import habitarium.rest.spring.RestConfig;
import habitarium.service.ServiceConfig;
import niels.jani.habitarium.PropertiesConfig;
import niels.jani.habitarium.infrastructure.spring.InfrastructureConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import({
        WarConfig.class,
        RestConfig.class,
        ServiceConfig.class,
        DomainConfig.class,
        InfrastructureConfig.class,
        PropertiesConfig.class
})
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}