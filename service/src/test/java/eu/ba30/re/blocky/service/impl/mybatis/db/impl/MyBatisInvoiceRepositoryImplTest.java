package eu.ba30.re.blocky.service.impl.mybatis.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.mybatis.MyBatisRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class MyBatisInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Configuration
    public static class InvoiceRepositoryConfiguration extends MyBatisRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList(
                    "db/repositoryTests/test-data-invoices.sql",
                    "db/repositoryTests/test-data-cst-category.sql");
        }
    }
}