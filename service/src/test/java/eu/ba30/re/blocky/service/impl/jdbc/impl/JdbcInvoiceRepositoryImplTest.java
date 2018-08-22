package eu.ba30.re.blocky.service.impl.jdbc.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbc.JdbcRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;
import mockit.Capturing;
import mockit.Expectations;

@ContextConfiguration(classes = { JdbcInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class JdbcInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Capturing
    private CstManager cstManager;

    @Override
    protected void initCstExpectations() {
        new Expectations() {{
            cstManager.getCategoryById(1);
            result = new TestObjectsBuilder().category1().buildSingleCategory();
        }};
    }

    @Configuration
    public static class InvoiceRepositoryConfiguration extends JdbcRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-invoices.sql");
        }
    }
}