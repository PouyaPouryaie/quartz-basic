package ir.bigz.spring.quratz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@PropertySources(
        {@PropertySource(value = "classpath:quartz.properties", encoding = "UTF-8")}
)
@Configuration
public class ApplicationProperties {

    @Autowired
    private Environment environment;

    public String getProperty(String name) {
        return environment.getProperty(name);
    }

    public Integer getCode(String name) {
        return Integer.parseInt(environment.getProperty(name));
    }
}
