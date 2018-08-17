package eu.ba30.re.blocky.service.jdbctemplate.impl.db;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.AbstractTestConfiguration;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.jdbctemplate" })
public abstract class JdbcTemplateRepositoryTestConfiguration extends AbstractTestConfiguration {
}
