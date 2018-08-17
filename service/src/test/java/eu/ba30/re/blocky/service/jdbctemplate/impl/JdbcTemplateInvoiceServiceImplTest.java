package eu.ba30.re.blocky.service.jdbctemplate.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.TestObjectsBuilder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { JdbcTemplateServiceTestConfiguration.class })
public class JdbcTemplateInvoiceServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;

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
}