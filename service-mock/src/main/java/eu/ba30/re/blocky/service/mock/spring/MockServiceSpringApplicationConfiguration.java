package eu.ba30.re.blocky.service.mock.spring;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.mock" })
public class MockServiceSpringApplicationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MockServiceSpringApplicationConfiguration.class);

    public MockServiceSpringApplicationConfiguration() {
        log.info("Setting up mocks");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(MockDb mockDb) {
        return mockDb.getJdbcTemplate();
    }

    @Bean
    @Autowired
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Autowired
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
}