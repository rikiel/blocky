package eu.ba30.re.blocky.service.impl;

import java.util.List;
import java.util.Objects;

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
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class InvoiceServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test(priority = 1)
    public void getInvoices() {
        invoiceService.getInvoices();
    }

    @Test(priority = 2)
    public void create() {
        final Invoice newInvoice = new TestObjectsBuilder().category1().attachment1().attachmentWithoutId().invoice2().buildSingleInvoice();
        newInvoice.setId(null);
        invoiceService.create(newInvoice);
        assertEquals(newInvoice.getId(), (Integer) TestObjectsBuilder.INVOICE_ID_2);

        final Invoice createdInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, newInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + newInvoice));
        assertReflectionEquals(newInvoice, createdInvoice);
    }

    @Test(priority = 3)
    public void updateWithoutAttachments() {
        final Invoice actualInvoice = new TestObjectsBuilder().category2().invoice2().buildSingleInvoice();

        invoiceService.update(actualInvoice);

        final Invoice updatedInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, actualInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + actualInvoice));

        assertReflectionEquals(actualInvoice, updatedInvoice);
    }

    @Test(priority = 3)
    public void updateWithAttachments() {
        final Invoice actualInvoice = new TestObjectsBuilder().category1()
                .attachment3()
                .attachmentWithoutId()
                .invoice3()
                .invoiceId(TestObjectsBuilder.INVOICE_ID_2)
                .buildSingleInvoice();

        invoiceService.update(actualInvoice);

        assertNotNull(actualInvoice.getAttachments().get(0).getId());

        final Invoice updatedInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, actualInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + actualInvoice));

        assertReflectionEquals(actualInvoice, updatedInvoice);
    }

    @Test(priority = 4)
    public void remove() {
        int size = invoiceService.getInvoices().size();
        List<Invoice> invoices = new TestObjectsBuilder().category1().attachment3().invoice3().invoiceId(TestObjectsBuilder.INVOICE_ID_2).buildInvoices();
        invoiceService.remove(invoices);
        assertEquals(invoiceService.getInvoices().size(), size - 1);
        invoiceService.remove(invoiceService.getInvoices());
        assertEquals(invoiceService.getInvoices().size(), 0);
    }

    @Test(priority = 5, dataProvider = "failTransactionForCreateDataProvider")
    public void failTransactionForCreate(Invoice invoice) {
        final int originalInvoicesCount = invoiceService.getInvoices().size();
        final int originalAttachmentsCount = attachmentsRepository.getAllAttachments().size();
        try {
            invoiceService.create(invoice);
            fail("create should not pass!");
        } catch (Exception e) {
            assertEquals(invoiceService.getInvoices().size(), originalInvoicesCount);
            assertEquals(attachmentsRepository.getAllAttachments().size(), originalAttachmentsCount);
        }
    }

    @Test(priority = 5, dataProvider = "failTransactionForRemoveDataProvider")
    public void failTransactionForRemove(Invoice invoice) {
        final int originalInvoicesCount = invoiceService.getInvoices().size();
        final int originalAttachmentsCount = attachmentsRepository.getAllAttachments().size();
        try {
            invoiceService.remove(Lists.newArrayList(invoice));
            fail("remove should not pass!");
        } catch (Exception e) {
            assertEquals(invoiceService.getInvoices().size(), originalInvoicesCount);
            assertEquals(attachmentsRepository.getAllAttachments().size(), originalAttachmentsCount);
        }
    }

    @Test(priority = 5, dataProvider = "failTransactionForUpdateDataProvider")
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