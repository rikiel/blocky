package eu.ba30.re.blocky.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.TestObjectsBuilder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractInvoiceServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CstManager cstManager;

    protected abstract TestObjectsBuilder createBuilder();

    @Test
    public void getInvoiceList() {
        assertReflectionEquals(createBuilder().category1().attachment1().invoice1().buildInvoices(),
                invoiceService.getInvoiceList());
    }

    @Test
    public void getAttachmentList() {
        assertReflectionEquals(createBuilder().attachment1().category1().invoice1().attachment1().buildAttachments(),
                invoiceService.getAttachmentList());
    }

    @Test
    public void create() {
        final Invoice newInvoice = createBuilder().category2()
                .attachment2()
                .attachmentWithoutId()
                .attachment3()
                .attachmentWithoutId()
                .invoice2()
                .invoiceId(null)
                .buildSingleInvoice();
        invoiceService.create(newInvoice);
        assertEquals(newInvoice.getId(), (Integer) TestObjectsBuilder.INVOICE_ID_2);

        final Invoice createdInvoice = invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_2);
        assertReflectionEquals(newInvoice, createdInvoice);
    }

    @Test
    public void updateWithoutAttachments() {
        final Invoice actualInvoice = invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_1);
        actualInvoice.setDetails("updateWithoutAttachments: Details");
        actualInvoice.setName("updateWithoutAttachments: Name");
        actualInvoice.setCategory(cstManager.getCategoryById(1));
        actualInvoice.setCreationDate(LocalDate.now());
        actualInvoice.setModificationDate(LocalDate.now());
        actualInvoice.setAttachments(Lists.newArrayList());

        final Invoice actualUpdatedInvoice = invoiceService.update(actualInvoice);
        assertEquals(actualUpdatedInvoice.getId(), (Integer) TestObjectsBuilder.INVOICE_ID_1);

        final Invoice updatedInvoice = invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_1);

        assertReflectionEquals(actualInvoice, actualUpdatedInvoice);
        assertReflectionEquals(actualUpdatedInvoice, updatedInvoice);
    }

    @Test
    public void updateWithAttachments() {
        final Invoice actualInvoice = invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_1);
        actualInvoice.setDetails("updateWithAttachments: Details");
        actualInvoice.setName("updateWithAttachments: Name");
        actualInvoice.setCategory(cstManager.getCategoryById(2));
        actualInvoice.setCreationDate(LocalDate.now().plusDays(1));
        actualInvoice.setModificationDate(LocalDate.now().plusWeeks(1));
        actualInvoice.setAttachments(createBuilder().attachment2().attachmentWithoutId().attachment3().attachmentWithoutId().buildAttachments());

        final Invoice actualUpdatedInvoice = invoiceService.update(actualInvoice);
        assertEquals(actualUpdatedInvoice.getId(), (Integer) TestObjectsBuilder.INVOICE_ID_1);

        final Invoice updatedInvoice = invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_1);

        actualInvoice.getAttachments().get(0).setId(TestObjectsBuilder.ATTACHMENT_ID_2);
        actualInvoice.getAttachments().get(1).setId(TestObjectsBuilder.ATTACHMENT_ID_3);

        assertReflectionEquals(actualInvoice, actualUpdatedInvoice);
        assertReflectionEquals(actualUpdatedInvoice, updatedInvoice);
    }

    @Test
    public void remove() {
        invoiceService.create(createBuilder().category2().attachment2().attachmentWithoutId().invoice2().invoiceId(null).buildSingleInvoice());
        final int size = invoiceService.getInvoiceList().size();
        invoiceService.remove(Lists.newArrayList(invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_1)));
        try {
            final Invoice removedInvoice = invoiceService.getInvoiceById(TestObjectsBuilder.INVOICE_ID_1);
            fail("Removed invoice was found: " + removedInvoice);
        } catch (Exception e) {
            // continue
            logger.debug("Exception was expected to be thrown");
        }
        final List<Invoice> invoices = invoiceService.getInvoiceList();
        assertFalse(invoices.isEmpty());
        assertEquals(invoices.size(), size - 1);
        invoiceService.remove(invoices);
        assertTrue(invoiceService.getInvoiceList().isEmpty());
    }

    @Test(dataProvider = "failTransactionForCreateDataProvider")
    public void failTransactionForCreate(Invoice invoice) {
        final List<Invoice> originalInvoices = invoiceService.getInvoiceList();
        final List<Attachment> originalAttachments = invoiceService.getAttachmentList();
        try {
            invoiceService.create(invoice);
            fail("create should not pass!");
        } catch (Exception e) {
            logger.debug("Exception was expected to be thrown");
            assertReflectionEquals(originalInvoices, invoiceService.getInvoiceList());
            assertReflectionEquals(originalAttachments, invoiceService.getAttachmentList());
        }
    }

    @Test(dataProvider = "failTransactionForRemoveDataProvider")
    public void failTransactionForRemove(List<Invoice> invoices) {
        final List<Invoice> originalInvoices = invoiceService.getInvoiceList();
        final List<Attachment> originalAttachments = invoiceService.getAttachmentList();
        try {
            invoiceService.remove(invoices);
            fail("remove should not pass!");
        } catch (Exception e) {
            logger.debug("Exception was expected to be thrown");
            assertReflectionEquals(originalInvoices, invoiceService.getInvoiceList());
            assertReflectionEquals(originalAttachments, invoiceService.getAttachmentList());
        }
    }

    @Test(dataProvider = "failTransactionForUpdateDataProvider")
    public void failTransactionForUpdate(Invoice invoice) {
        final List<Invoice> originalInvoices = invoiceService.getInvoiceList();
        final List<Attachment> originalAttachments = invoiceService.getAttachmentList();
        try {
            invoiceService.update(invoice);
            fail("update should not pass!");
        } catch (Exception e) {
            logger.debug("Exception was expected to be thrown");
            assertReflectionEquals(originalInvoices, invoiceService.getInvoiceList());
            assertReflectionEquals(originalAttachments, invoiceService.getAttachmentList());
        }
    }

    @DataProvider
    protected Object[][] failTransactionForCreateDataProvider() {
        return new Object[][] {
                { null },
                // name is null
                { createBuilder().invoiceEmpty().buildSingleInvoice() },
                // fail for id=1
                { createBuilder().attachment1().invoice1().buildSingleInvoice() },
                // fail for existing attachment
                { createBuilder().attachmentNull().attachmentNull().invoice1().invoiceId(null).buildSingleInvoice() },
                { createBuilder().attachment1().invoice1().invoiceId(null).buildSingleInvoice() },
                };
    }

    @DataProvider
    protected Object[][] failTransactionForRemoveDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { createBuilder().invoiceEmpty().buildInvoices() },
                // nonexisting id
                { createBuilder().invoice1().invoiceId(999).buildInvoices() },
                // null id for second invoice
                { createBuilder().attachment1().category1().invoice1().invoiceEmpty().buildInvoices() },
                };
    }

    @DataProvider
    protected Object[][] failTransactionForUpdateDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { createBuilder().invoiceEmpty().buildSingleInvoice() },
                // nonexisting id
                { createBuilder().invoice1().invoiceId(null).buildSingleInvoice() },
                { createBuilder().invoice1().invoiceId(999).buildSingleInvoice() },
                };
    }
}
