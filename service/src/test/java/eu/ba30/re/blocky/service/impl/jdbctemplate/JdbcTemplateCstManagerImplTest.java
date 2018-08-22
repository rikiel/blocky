package eu.ba30.re.blocky.service.impl.jdbctemplate;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstManagerImplTest;

@ContextConfiguration(classes = { JdbcTemplateServiceTestConfiguration.class })
public class JdbcTemplateCstManagerImplTest extends AbstractCstManagerImplTest {
}