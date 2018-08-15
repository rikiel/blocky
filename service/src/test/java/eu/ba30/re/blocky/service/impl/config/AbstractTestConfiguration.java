package eu.ba30.re.blocky.service.impl.config;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@ComponentScan({ "eu.ba30.re.blocky.service", "eu.ba30.re.blocky.aspects" })
@MapperScan("eu.ba30.re.blocky.service.impl.db.impl.mapper")
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
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        final HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);

        return hibernateTransactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("eu.ba30.re.blocky.model");
        sessionFactory.setHibernateProperties(new Properties() {{
            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            setProperty("hibernate.hbm2ddl.auto", "validate");
        }});
        return sessionFactory;
    }
}
