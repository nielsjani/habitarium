package niels.jani.habitarium.war.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import({
        WarConfig.class,
//        ApiAsConfig.class,
//        BatchConfig.class,
//        MigratieConfig.class,
//        ServiceConfig.class,
//        DomainConfig.class,
//        SecurityConfig.class,
//        InterfacesJmxConfig.class,
//        InterfacesConfig.class,
//        InfrastructureConfig.class,
//        PropertiesConfig.class
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