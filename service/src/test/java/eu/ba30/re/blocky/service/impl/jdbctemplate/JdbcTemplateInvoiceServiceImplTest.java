package eu.ba30.re.blocky.service.impl.jdbctemplate;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.jdbctemplate.db.JdbcTemplateAttachmentsRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { JdbcTemplateServiceTestConfiguration.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcTemplateInvoiceServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private JdbcTemplateAttachmentsRepository attachmentsRepository;

    @Test
    public void getInvoices() {
        invoiceService.getInvoices();
    }

    @Test
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

    @Test
    public void updateWithoutAttachments() {
        final Invoice actualInvoice = new TestObjectsBuilder().category2().invoice2().invoiceId(TestObjectsBuilder.INVOICE_ID_1).buildSingleInvoice();

        invoiceService.update(actualInvoice);

        final Invoice updatedInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, actualInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + actualInvoice));

        assertReflectionEquals(actualInvoice, updatedInvoice);
    }

    @Test
    public void updateWithAttachments() {
        final Invoice actualInvoice = new TestObjectsBuilder().category2()
                .attachment2()
                .attachmentWithoutId()
                .invoice2()
                .invoiceId(TestObjectsBuilder.INVOICE_ID_1)
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

    @Test
    public void remove() {
        invoiceService.remove(invoiceService.getInvoices());
        assertEquals(invoiceService.getInvoices().size(), 0);
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
    private Object[][] failTransactionForCreateDataProvider() {
        return new Object[][] {
                { null },
                // name is null
                { new Invoice() },
                // fail for id=1
                { new TestObjectsBuilder().attachment1().invoice1().buildSingleInvoice() },
                // fail for existing attachment
                { new TestObjectsBuilder().attachmentNull().attachmentNull().invoice1().invoiceId(null).buildSingleInvoice() },
                { new TestObjectsBuilder().attachment1().invoice1().invoiceId(null).buildSingleInvoice() },
                };
    }

    @DataProvider
    private Object[][] failTransactionForRemoveDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { Lists.newArrayList(new Invoice()) },
                // nonexisting id
                { new TestObjectsBuilder(true).invoice1().invoiceId(999).buildInvoices() },
                // null id for second invoice
                { Lists.newArrayList(new TestObjectsBuilder(true).attachment1().category1().invoice1().buildSingleInvoice(), new Invoice()) },
                };
    }

    @DataProvider
    private Object[][] failTransactionForUpdateDataProvider() {
        return new Object[][] {
                { null },
                // null id
                { new Invoice() },
                // nonexisting id
                { new TestObjectsBuilder().invoice1().invoiceId(null).buildSingleInvoice() },
                { new TestObjectsBuilder().invoice1().invoiceId(999).buildSingleInvoice() },
                };
    }
}