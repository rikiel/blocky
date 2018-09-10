package eu.ba30.re.blocky.service.impl.mybatis.annotation;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.annotation.MyBatisServiceAnnotationTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceServiceImplTest;

@ContextConfiguration(classes = { MyBatisServiceAnnotationTestConfiguration.class })
public class MyBatisAnnotationInvoiceServiceImplTest extends AbstractInvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.MY_BATIS);
    }
}