package eu.ba30.re.blocky.service.impl.jdbctemplate;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.InvoiceServiceImplTest;

@ContextConfiguration(classes = { JdbcTemplateServiceTestConfiguration.class })
public class JdbcTemplateInvoiceServiceImplTest extends InvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder();
    }
}