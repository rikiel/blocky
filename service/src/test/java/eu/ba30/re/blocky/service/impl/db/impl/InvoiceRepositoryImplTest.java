package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.RepositoryTestConfiguration;
import mockit.Capturing;
import mockit.Expectations;

import static eu.ba30.re.blocky.service.TestUtils.getDbAttachment;
import static eu.ba30.re.blocky.service.TestUtils.getDbCategory;
import static eu.ba30.re.blocky.service.TestUtils.getDbInvoice;
import static eu.ba30.re.blocky.service.TestUtils.getMockedAttachment2;
import static eu.ba30.re.blocky.service.TestUtils.getNewInvoice;
import static org.testng.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { InvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class InvoiceRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Capturing
    private AttachmentsRepository attachmentsRepository;

    @Capturing
    private CstManager cstManager;

    @Autowired
    private InvoiceRepositoryImpl invoiceRepository;

    @Test(priority = 1)
    public void getInvoices() {
        initDbInvoiceExpectations();

        assertReflectionEquals(Lists.newArrayList(getDbInvoice()),
                invoiceRepository.getInvoices());
    }

    @Test(priority = 2)
    public void create() {
        initDbInvoiceExpectations();
        new Expectations() {{
            // getList
            attachmentsRepository.getAttachmentList(2);
            result = Lists.newArrayList(getMockedAttachment2());
        }};
        invoiceRepository.create(getNewInvoice());

        assertReflectionEquals(Lists.newArrayList(getDbInvoice(), getNewInvoice()),
                invoiceRepository.getInvoices());
    }

    @Test(priority = 3)
    public void remove() {
        initDbInvoiceExpectations();
        invoiceRepository.remove(Lists.newArrayList(getNewInvoice()));

        assertReflectionEquals(Lists.newArrayList(getDbInvoice()),
                invoiceRepository.getInvoices());
    }

    @Test
    public void getNextItemId() {
        assertEquals(invoiceRepository.getNextItemId(), 1);
        assertEquals(invoiceRepository.getNextItemId(), 2);
    }

    private void initDbInvoiceExpectations() {
        new Expectations() {{
            attachmentsRepository.getAttachmentList(1);
            result = Lists.newArrayList(getDbAttachment());

            cstManager.getCategory(123);
            result = getDbCategory();
        }};
    }

    @Configuration
    public static class InvoiceRepositoryConfiguration extends RepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-invoices.sql");
        }
    }
}