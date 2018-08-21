package eu.ba30.re.blocky.service.impl.mybatis;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.MyBatisServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.InvoiceServiceImplTest;

@ContextConfiguration(classes = { MyBatisServiceTestConfiguration.class })
public class MyBatisInvoiceServiceImplTest extends InvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder();
    }
}