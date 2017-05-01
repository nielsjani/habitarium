package niels.jani.habitarium.spring;

import niels.jani.habitarium.InfrastructureIntegrationTest;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaIntegrationTest extends InfrastructureIntegrationTest {

    @Inject
    private DataSource dataSource;

    @Test
    public void dataSource_GivenEenQuery_ThenGeeftEenResultaat() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Integer actual = jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(1);
    }
}
