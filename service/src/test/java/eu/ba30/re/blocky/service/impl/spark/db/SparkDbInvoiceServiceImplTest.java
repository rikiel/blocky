package eu.ba30.re.blocky.service.impl.spark.db;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.db.SparkDbServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceServiceImplTest;

@ContextConfiguration(classes = { SparkDbServiceTestConfiguration.class })
public class SparkDbInvoiceServiceImplTest extends AbstractInvoiceServiceImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }
}