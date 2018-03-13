package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.RepositoryTestConfiguration;
import mockit.Capturing;
import mockit.Expectations;

import static eu.ba30.re.blocky.service.TestUtils.createDbInvoice;
import static eu.ba30.re.blocky.service.TestUtils.createNewInvoice;
import static eu.ba30.re.blocky.service.TestUtils.getMockedAttachment;
import static eu.ba30.re.blocky.service.TestUtils.getMockedCategory;
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
        new Expectations() {{
            attachmentsRepository.getAttachmentList(1);
            result = Lists.newArrayList(getMockedAttachment());

            cstManager.getCategory(1);
            result = getMockedCategory();
        }};
        final List<Invoice> invoices = invoiceRepository.getInvoices();
        assertEquals(invoices.size(), 1);
        assertReflectionEquals(Lists.newArrayList(createDbInvoice()), invoices);
    }

    @Test(priority = 2)
    public void create() {
        new Expectations() {{
            attachmentsRepository.createAttachments(2, (List<Attachment>) any);
            result = null;
        }};
        invoiceRepository.create(createNewInvoice());

        assertEquals(invoiceRepository.getInvoices().size(), 2);
    }

    @Test(priority = 3)
    public void remove() {
        new Expectations() {{
            attachmentsRepository.removeAttachments(Lists.newArrayList(1));
            result = null;
        }};
        invoiceRepository.remove(Lists.newArrayList(createDbInvoice()));

        assertEquals(invoiceRepository.getInvoices().size(), 1);
    }


    @Configuration
    public static class InvoiceRepositoryConfiguration extends RepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/test-data-invoices.sql");
        }
    }
}