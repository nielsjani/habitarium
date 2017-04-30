package niels.jani.habitarium;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class TestProperties {

    private static final String HABIT_ENV = "HABIT_ENV";

    public static String propertyValue(String propertyName) {
        String env = System.getProperty(HABIT_ENV, "dev");
        String filename = String.format("/%s.properties", env);
        try (InputStream is = TestProperties.class.getResourceAsStream(filename)) {
            if (is == null) {
                throw new MissingPropertyException("missing properties file " + filename);
            }
            Properties properties = new Properties();
            properties.load(is);
            String value = properties.getProperty(propertyName);
            if (value == null) {
                throw new MissingPropertyException(String.format("missing property %s in %s", propertyName, filename));
            }
            return value;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}