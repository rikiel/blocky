package eu.ba30.re.blocky.service.jdbctemplate.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { JdbcTemplateServiceTestConfiguration.class })
public class JdbcTemplateInvoiceServiceTransactionsTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test(dataProvider = "failTransactionForCreateDataProvider")
    public void failTransactionForCreate(Invoice invoice) {
        try {
            invoiceService.create(invoice);
            fail("create should not pass!");
        } catch (Exception e) {
            assertEquals(invoiceService.getInvoices().size(), 1);
            assertEquals(attachmentsRepository.getAllAttachments().size(), 3);
        }
    }

    @Test(dataProvider = "failTransactionForRemoveDataProvider")
    public void failTransactionForRemove(Invoice invoice) {
        try {
            invoiceService.remove(Lists.newArrayList(invoice));
            fail("remove should not pass!");
        } catch (Exception e) {
            assertEquals(invoiceService.getInvoices().size(), 1);
            assertEquals(attachmentsRepository.getAllAttachments().size(), 3);
        }
    }

    @Test(dataProvider = "failTransactionForUpdateDataProvider")
    public void failTransactionForUpdate(Invoice invoice) {
        final List<Invoice> invoicesStart = invoiceService.getInvoices();
        List<Attachment> attachmentsStart = attachmentsRepository.getAllAttachments();
        try {
            invoiceService.update(invoice);
            fail("update should not pass!");
        } catch (Exception e) {
            assertReflectionEquals(invoicesStart,
                    invoiceService.getInvoices());
            assertReflectionEquals(attachmentsStart,
                    attachmentsRepository.getAllAttachments());
        }
    }

    @DataProvider
    private Object[][] failTransactionForCreateDataProvider() {
        final Attachment attachmentWithId = new TestObjectsBuilder().attachment1().buildSingleAttachment();
        return new Object[][] {
                { null },
                // name is null
                { new Invoice() },
                // fail for id=1
                { createInvoice(1, attachmentWithId) },
                // fail for existing attachment
                { createInvoice(null, null, null) },
                { createInvoice(null, attachmentWithId) },
                };
    }

    @DataProvider
    private Object[][] failTransactionForRemoveDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { new Invoice() },
                // nonexisting id
                { createInvoice(999) },
                };
    }

    @DataProvider
    private Object[][] failTransactionForUpdateDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { new Invoice() },
                // nonexisting id
                { createInvoice(null) },
                { createInvoice(999) },
                };
    }

    private static Invoice createInvoice(Integer id, Attachment... attachments) {
        final Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setName("Name");
        invoice.setDetails("Details");
        invoice.setAttachments(Lists.newArrayList(attachments));
        return invoice;
    }
}
