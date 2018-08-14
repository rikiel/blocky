package eu.ba30.re.blocky.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service", "eu.ba30.re.blocky.aspects" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public abstract class AbstractTestConfiguration {
    @Nonnull
    protected abstract List<String> getSqlScripts();

    @Bean
    public DataSource dataSource() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScript("db/test-data-db-schema.sql");
        getSqlScripts().forEach(builder::addScript);
        return builder.build();
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Autowired
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory() {
        // TODO skusit aj XML konfiguraciu
        return buildByJava();
        //        return buildByXml();
    }

    private SqlSessionFactory buildByJava() {
        final org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(new Environment("test",
                new JdbcTransactionFactory(),
                dataSource()));
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private SqlSessionFactory buildByXml() {
        try {
            final InputStream inputStream = Resources.getResourceAsStream("mybatis/configuration.xml");
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
