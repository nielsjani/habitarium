package niels.jani.habitarium.infrastructure.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static java.util.Optional.ofNullable;
import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        value = InfrastructureConfig.INFRASTRUCTURE_BASE_PACKAGE,
        excludeFilters = @ComponentScan.Filter(type = ANNOTATION, value = Configuration.class))
public class InfrastructureConfig {

    public static final String INFRASTRUCTURE_BASE_PACKAGE = "niels.jani.habitarium.infrastructure";
    public static final String HIBERNATE_ENTITY_DOMAIN_PACKAGES = "niels.jani.habitarium.domain";
    public static final boolean SHOW_SQL = false;
    public static final Logger LOGGER = LoggerFactory.getLogger(InfrastructureConfig.class);

    @Inject
    private Environment environment;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(
                mapper.getSerializationConfig().getDefaultVisibilityChecker()
                        .withFieldVisibility(ANY)
                        .withGetterVisibility(NONE)
                        .withIsGetterVisibility(NONE)
                        .withSetterVisibility(NONE)
                        .withCreatorVisibility(NONE)
        );
        return mapper;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan(HIBERNATE_ENTITY_DOMAIN_PACKAGES);
        factory.setJpaProperties(jpaProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public DataSource dataSource() {
        if (isJndiDataSource()) {
            LOGGER.info("Using jndi datasource '" + jndiDataSourceName() +"'");
            return jndiDatasource();
        } else {
            LOGGER.info("Using local datasource");
            return localDatasource();
        }
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(PostgreSQL9Dialect.class.getName());
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(SHOW_SQL);
        return vendorAdapter;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.default_schema", environment.getProperty("db.schema"));
        return properties;
    }

    private boolean isJndiDataSource() {
        return ofNullable(jndiDataSourceName()).isPresent();
    }

    private DataSource jndiDatasource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        return jndiDataSourceLookup.getDataSource(jndiDataSourceName());
    }

    private String jndiDataSourceName() {
        return environment.getProperty("spring.datasource.jndi-name");
    }

    private DataSource localDatasource() {
        PoolConfiguration poolConfiguration = new PoolProperties();
        poolConfiguration.setDriverClassName(environment.getProperty("db.driver"));
        poolConfiguration.setUrl(environment.getProperty("db.url"));
        poolConfiguration.setUsername(environment.getProperty("db.username"));
        poolConfiguration.setPassword(environment.getProperty("db.password"));
        return new org.apache.tomcat.jdbc.pool.DataSource(poolConfiguration);
    }
}
