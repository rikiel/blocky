package eu.ba30.re.blocky.service.impl.jdbc;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.config.jdbc.JdbcServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.CstManagerImplTest;

@ContextConfiguration(classes = { JdbcServiceTestConfiguration.class })
public class JdbcCstManagerImplTest extends CstManagerImplTest {
}