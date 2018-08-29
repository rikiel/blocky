package eu.ba30.re.blocky.service.impl.spark.repository;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.SparkRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;
import mockit.Capturing;
import mockit.Expectations;

@ContextConfiguration(classes = { SparkRepositoryTestConfiguration.class })
public class SparkInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Capturing
    private CstManager cstManager;

    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }

    @Override
    protected void initCstExpectations() {
        new Expectations() {{
            cstManager.getCategoryById(1);
            result = new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK).category1().buildSingleCategory();
        }};
    }
}