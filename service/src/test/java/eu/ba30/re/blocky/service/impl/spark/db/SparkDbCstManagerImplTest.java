package eu.ba30.re.blocky.service.impl.spark.db;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.db.SparkDbServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstManagerImplTest;

@ContextConfiguration(classes = { SparkDbServiceTestConfiguration.class })
public class SparkDbCstManagerImplTest extends AbstractCstManagerImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }
}