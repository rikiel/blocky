package eu.ba30.re.blocky.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
@ComponentScan("eu.ba30.re.blocky.service")
public abstract class AbstractTestConfiguration {
    @Nonnull
    protected abstract List<String> getSqlScripts();

    @Bean
    public JdbcTemplate jdbcTemplate() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScript("db/test-data-db-schema.sql");
        getSqlScripts().forEach(builder::addScript);
        return new JdbcTemplate(builder.build());
    }
}
