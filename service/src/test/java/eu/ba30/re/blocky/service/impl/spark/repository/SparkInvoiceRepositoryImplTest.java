package eu.ba30.re.blocky.service.impl.spark.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.SparkRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;
import eu.ba30.re.blocky.service.impl.spark.SparkCstManagerImpl;
import eu.ba30.re.blocky.service.impl.spark.SparkTransactionManager;
import mockit.Capturing;
import mockit.Expectations;

@ContextConfiguration(classes = { SparkInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
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

    @Configuration
    public static class InvoiceRepositoryConfiguration extends SparkRepositoryTestConfiguration {
        // TODO BLOCKY-16 beany cez komponent scan
        @Bean
        public SparkInvoiceRepositoryImpl sparkInvoiceRepository() {
            return new SparkInvoiceRepositoryImpl();
        }

        @Bean
        public SparkTransactionManager sparkTransactionManager() {
            return new SparkTransactionManager();
        }

        @Bean
        public CstManager cstManager() {
            return new SparkCstManagerImpl();
        }

        @Bean
        public SparkCstCategoryRepositoryImpl cstCategoryRepository() {
            return new SparkCstCategoryRepositoryImpl();
        }

        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-invoices.sql");
        }
    }
}