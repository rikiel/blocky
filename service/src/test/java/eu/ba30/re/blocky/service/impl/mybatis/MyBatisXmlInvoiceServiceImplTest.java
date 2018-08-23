package eu.ba30.re.blocky.service.impl.mybatis;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.xml.MyBatisServiceXmlTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceServiceImplTest;

@ContextConfiguration(classes = { MyBatisServiceXmlTestConfiguration.class })
public class MyBatisXmlInvoiceServiceImplTest extends AbstractInvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder();
    }
}