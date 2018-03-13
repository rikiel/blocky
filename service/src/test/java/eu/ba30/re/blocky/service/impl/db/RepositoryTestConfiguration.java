package eu.ba30.re.blocky.service.impl.db;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import eu.ba30.re.blocky.service.ServiceTestConfiguration;

@Configuration
public abstract class RepositoryTestConfiguration extends ServiceTestConfiguration {
    @Nonnull
    protected abstract List<String> getSqlScripts();

    @Bean
    public JdbcTemplate jdbcTemplate() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        getSqlScripts().forEach(builder::addScript);
        return new JdbcTemplate(builder.build());
    }
}
