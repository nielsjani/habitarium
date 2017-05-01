package niels.jani.habitarium.infrastructure.ddd;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.empty;

@Named
public abstract class JdbcRepository {

    @Inject
    protected NamedParameterJdbcTemplate jdbcTemplate;
    @Inject
    protected Environment environment;

    protected java.sql.Date convertToSqlDate(Date datum) {
        return new java.sql.Date(datum.getTime());
    }

    protected String lokaalproject() {
        return lokaalproject(null);
    }

    protected String lokaalproject(String alias) {
        return toFromQueryString("LOKAALPROJECT", Optional.ofNullable(alias));
    }

    protected String vdabprojectmedewerker(String alias) {
        return toFromQueryString("VDABPROJECTMEDEWERKER", Optional.ofNullable(alias));
    }

    protected String projectonderdeel() {
        return toFromQueryString("PROJECTONDERDEEL");
    }

    protected String projectonderdeel(String alias) {
        return toFromQueryString("PROJECTONDERDEEL", Optional.of(alias));
    }

    protected String kostenpost() {
        return schema() + ".KOSTENPOST ";
    }

    protected String btw() {
        return schema() + ".BTW ";
    }

    private String toFromQueryString(String column) {
        return toFromQueryString(column, empty());
    }

    private String toFromQueryString(String column, Optional<String> alias) {
        return schema() + "." + column + " " + alias.orElse("") + " ";
    }

    private String schema() {
        return environment.getProperty("db.schema");
    }

}
