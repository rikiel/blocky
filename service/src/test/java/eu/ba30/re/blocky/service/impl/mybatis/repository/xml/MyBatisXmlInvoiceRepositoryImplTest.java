package eu.ba30.re.blocky.service.impl.mybatis.repository.xml;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.xml.MyBatisRepositoryXmlTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractInvoiceRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisXmlInvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class MyBatisXmlInvoiceRepositoryImplTest extends AbstractInvoiceRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.MY_BATIS);
    }

    @Configuration
    public static class InvoiceRepositoryConfiguration extends MyBatisRepositoryXmlTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList(
                    "db/repositoryTests/test-data-invoices.sql",
                    "db/repositoryTests/test-data-cst-category.sql");
        }
    }
}