package eu.ba30.re.blocky.service.impl.spark;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.SparkServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceServiceImplTest;

@ContextConfiguration(classes = { SparkServiceTestConfiguration.class })
public class SparkInvoiceServiceImplTest extends AbstractInvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }
}