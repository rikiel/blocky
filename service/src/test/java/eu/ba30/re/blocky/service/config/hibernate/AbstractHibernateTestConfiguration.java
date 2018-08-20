package eu.ba30.re.blocky.service.config.hibernate;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.DirtiesContext;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.hibernate" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class AbstractHibernateTestConfiguration extends AbstractTestConfiguration {
    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager();
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
