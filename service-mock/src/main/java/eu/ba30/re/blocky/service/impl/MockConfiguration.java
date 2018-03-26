package eu.ba30.re.blocky.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MockConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(MockDb mockDb) {
        mockDb.initDb();
        return mockDb.getJdbcTemplate();
    }
}
