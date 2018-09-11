package eu.ba30.re.blocky.service.impl.spark.csv.repositorytest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.csv.SparkCsvRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;

@ContextConfiguration(classes = { SparkCsvInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class SparkCsvInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
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