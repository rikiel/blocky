package eu.ba30.re.blocky.service.impl.mybatis.repository.annotation;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.mybatis.annotation.MyBatisRepositoryAnnotationTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisAnnotationInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class MyBatisAnnotationInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Configuration
    public static class InvoiceRepositoryConfiguration extends MyBatisRepositoryAnnotationTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList(
                    "db/repositoryTests/test-data-invoices.sql",
                    "db/repositoryTests/test-data-cst-category.sql");
        }
    }
}