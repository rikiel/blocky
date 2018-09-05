package eu.ba30.re.blocky.service.impl.spark.repositorytest;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Row;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.SparkRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;
import eu.ba30.re.blocky.service.impl.spark.repository.SparkCstCategoryRepositoryImpl;
import mockit.Capturing;
import mockit.Expectations;

@ContextConfiguration(classes = { SparkInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class SparkInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Capturing
    private SparkCstCategoryRepositoryImpl.CategoryMapper categoryMapper;

    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }

    @Override
    protected void initCstExpectations() {
        new Expectations() {{
            categoryMapper.mapRow((Row) any);
            result = new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK).category1().buildSingleCategory();
        }};
    }

    @Configuration
    public static class InvoiceRepositoryConfiguration extends SparkRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList(
                    "db/repositoryTests/test-data-invoices.sql",
                    "db/repositoryTests/test-data-cst-category.sql");
        }
    }
}