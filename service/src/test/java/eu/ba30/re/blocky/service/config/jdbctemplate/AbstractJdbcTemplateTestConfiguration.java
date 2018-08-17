package eu.ba30.re.blocky.service.config.jdbctemplate;

import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.jdbctemplate" })
abstract class AbstractJdbcTemplateTestConfiguration extends AbstractTestConfiguration {
}
