package eu.ba30.re.blocky.service.impl.jdbctemplate.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;
import mockit.Capturing;
import mockit.Expectations;

@ContextConfiguration(classes = { JdbcTemplateInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class JdbcTemplateInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Capturing
    private CstManager cstManager;

    @Override
    protected void initCstExpectations() {
        new Expectations() {{
            cstManager.getCategory(1);
            result = new TestObjectsBuilder().category1().buildSingleCategory();
        }};
    }

    @Configuration
    public static class InvoiceRepositoryConfiguration extends JdbcTemplateRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-invoices.sql");
        }
    }
}