package eu.ba30.re.blocky.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
@ComponentScan("eu.ba30.re.blocky.service")
public class ServiceTestConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScript("db/test-data-invoices.sql");
        return new JdbcTemplate(builder.build());
    }
}
