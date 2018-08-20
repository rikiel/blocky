package eu.ba30.re.blocky.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public class MockConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MockConfiguration.class);

    public MockConfiguration() {
        log.info("Setting up mocks");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(MockDb mockDb) {
        mockDb.initDb();
        return mockDb.getJdbcTemplate();
    }
}
