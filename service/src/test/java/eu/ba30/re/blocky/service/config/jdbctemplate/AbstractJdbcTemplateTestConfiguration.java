package eu.ba30.re.blocky.service.config.jdbctemplate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.jdbctemplate" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class AbstractJdbcTemplateTestConfiguration extends AbstractTestConfiguration {
}
