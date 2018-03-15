package eu.ba30.re.blocky.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class MockConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScripts(
                "db/mock-data-db-schema.sql",
                "db/mock-data-attachments.sql",
                "db/mock-data-cst-category.sql",
                "db/mock-data-invoices.sql"
        );
        return new JdbcTemplate(builder.build());
    }
}
