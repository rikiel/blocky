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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractInvoiceServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CstManager cstManager;

    protected abstract TestObjectsBuilder createBuilder();

    @Test
    public void getInvoices() {
        assertReflectionEquals(createBuilder().category1().attachment1().invoice1().buildInvoices(),
                invoiceService.getInvoices());
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

        final Invoice createdInvoice = invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_2);
        assertReflectionEquals(newInvoice, createdInvoice);
    }

    @Test
    public void updateWithoutAttachments() {
        final Invoice actualInvoice = invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_1);
        actualInvoice.setDetails("updateWithoutAttachments: Details");
        actualInvoice.setName("updateWithoutAttachments: Name");
        actualInvoice.setCategory(cstManager.getCategory(1));
        actualInvoice.setCreationDate(LocalDate.now());
        actualInvoice.setModificationDate(LocalDate.now());
        actualInvoice.setAttachments(Lists.newArrayList());

        final Invoice actualUpdatedInvoice = invoiceService.update(actualInvoice);
        assertEquals(actualUpdatedInvoice.getId(), (Integer) TestObjectsBuilder.INVOICE_ID_1);

        final Invoice updatedInvoice = invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_1);

        assertReflectionEquals(actualInvoice, actualUpdatedInvoice);
        assertReflectionEquals(actualUpdatedInvoice, updatedInvoice);
    }

    @Test
    public void updateWithAttachments() {
        final Invoice actualInvoice = invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_1);
        actualInvoice.setDetails("updateWithAttachments: Details");
        actualInvoice.setName("updateWithAttachments: Name");
        actualInvoice.setCategory(cstManager.getCategory(2));
        actualInvoice.setCreationDate(LocalDate.now().plusDays(1));
        actualInvoice.setModificationDate(LocalDate.now().plusWeeks(1));
        actualInvoice.setAttachments(createBuilder().attachment2().attachmentWithoutId().attachment3().attachmentWithoutId().buildAttachments());

        final Invoice actualUpdatedInvoice = invoiceService.update(actualInvoice);
        assertEquals(actualUpdatedInvoice.getId(), (Integer) TestObjectsBuilder.INVOICE_ID_1);

        final Invoice updatedInvoice = invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_1);

        actualInvoice.getAttachments().get(0).setId(TestObjectsBuilder.ATTACHMENT_ID_2);
        actualInvoice.getAttachments().get(1).setId(TestObjectsBuilder.ATTACHMENT_ID_3);

        assertReflectionEquals(actualInvoice, actualUpdatedInvoice);
        assertReflectionEquals(actualUpdatedInvoice, updatedInvoice);
    }

    @Test
    public void remove() {
        invoiceService.create(createBuilder().category2().attachment2().attachmentWithoutId().invoice2().invoiceId(null).buildSingleInvoice());
        final int size = invoiceService.getInvoices().size();
        invoiceService.remove(Lists.newArrayList(invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_1)));
        try {
            final Invoice removedInvoice = invoiceService.getInvoice(TestObjectsBuilder.INVOICE_ID_1);
            fail("Removed invoice was found: " + removedInvoice);
        } catch (Exception e) {
            // continue
        }
        final List<Invoice> invoices = invoiceService.getInvoices();
        assertFalse(invoices.isEmpty());
        assertEquals(invoices.size(), size - 1);
        invoiceService.remove(invoices);
        assertTrue(invoiceService.getInvoices().isEmpty());
    }

    @Test(dataProvider = "failTransactionForCreateDataProvider")
    public void failTransactionForCreate(Invoice invoice) {
        final List<Invoice> originalInvoices = invoiceService.getInvoices();
        final List<Attachment> originalAttachments = invoiceService.getAttachments();
        try {
            invoiceService.create(invoice);
            fail("create should not pass!");
        } catch (Exception e) {
            assertReflectionEquals(originalInvoices, invoiceService.getInvoices());
            assertReflectionEquals(originalAttachments, invoiceService.getAttachments());
        }
    }

    @Test(dataProvider = "failTransactionForRemoveDataProvider")
    public void failTransactionForRemove(List<Invoice> invoices) {
        final List<Invoice> originalInvoices = invoiceService.getInvoices();
        final List<Attachment> originalAttachments = invoiceService.getAttachments();
        try {
            invoiceService.remove(invoices);
            fail("remove should not pass!");
        } catch (Exception e) {
            assertReflectionEquals(originalInvoices, invoiceService.getInvoices());
            assertReflectionEquals(originalAttachments, invoiceService.getAttachments());
        }
    }

    @Test(dataProvider = "failTransactionForUpdateDataProvider")
    public void failTransactionForUpdate(Invoice invoice) {
        final List<Invoice> originalInvoices = invoiceService.getInvoices();
        final List<Attachment> originalAttachments = invoiceService.getAttachments();
        try {
            invoiceService.update(invoice);
            fail("update should not pass!");
        } catch (Exception e) {
            assertReflectionEquals(originalInvoices, invoiceService.getInvoices());
            assertReflectionEquals(originalAttachments, invoiceService.getAttachments());
        }
    }

    @DataProvider
    protected Object[][] failTransactionForCreateDataProvider() {
        return new Object[][] {
                { null },
                // name is null
                { new Invoice() },
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
                { Lists.newArrayList(new Invoice()) },
                // nonexisting id
                { createBuilder().invoice1().invoiceId(999).buildInvoices() },
                // null id for second invoice
                { Lists.newArrayList(createBuilder().attachment1().category1().invoice1().buildSingleInvoice(), new Invoice()) },
                };
    }

    @DataProvider
    protected Object[][] failTransactionForUpdateDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { new Invoice() },
                // nonexisting id
                { createBuilder().invoice1().invoiceId(null).buildSingleInvoice() },
                { createBuilder().invoice1().invoiceId(999).buildSingleInvoice() },
                };
    }
}
