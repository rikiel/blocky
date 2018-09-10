package eu.ba30.re.blocky.service.impl.spark.csv.repositorytest;

import org.apache.spark.sql.Row;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.csv.SparkCsvRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;
import eu.ba30.re.blocky.service.impl.spark.common.mapper.SparkCategoryMapper;
import mockit.Capturing;
import mockit.Expectations;

@ContextConfiguration(classes = { SparkCsvInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class SparkCsvInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Capturing
    private SparkCategoryMapper categoryMapper;

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
    public static class InvoiceRepositoryConfiguration extends SparkCsvRepositoryTestConfiguration {
        @Bean
        public String invoiceCsvFileName() {
            return copyResourceToFile("csv/test-data-invoices.csv");
        }

        @Bean
        public String categoryCsvFileName() {
            return copyResourceToFile("csv/test-data-cst-category.csv");
        }
    }
}