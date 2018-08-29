package eu.ba30.re.blocky.service.impl.jdbctemplate;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceServiceImplTest;

@ContextConfiguration(classes = { JdbcTemplateServiceTestConfiguration.class })
public class JdbcTemplateInvoiceServiceImplTest extends AbstractInvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.JDBC_TEMPLATE);
    }
}