package eu.ba30.re.blocky.service.impl.jdbc;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbc.JdbcServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.InvoiceServiceImplTest;

@ContextConfiguration(classes = { JdbcServiceTestConfiguration.class })
public class JdbcInvoiceServiceImplTest extends InvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder();
    }
}