package habitarium.domain.spring;

import niels.jani.habitarium.infrastructure.ddd.RepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        value = DomainConfig.DOMAIN_BASE_PACKAGE,
        excludeFilters = @ComponentScan.Filter(type = ANNOTATION, value = Configuration.class))
@EnableJpaRepositories(basePackages = DomainConfig.DOMAIN_BASE_PACKAGE, repositoryFactoryBeanClass = RepositoryFactoryBean.class)
@EnableJpaAuditing
public class DomainConfig {

    public static final String DOMAIN_BASE_PACKAGE = "habitarium.domain";

}
