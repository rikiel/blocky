package eu.ba30.re.blocky.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.service.InvoiceService;

import static eu.ba30.re.blocky.service.TestUtils.getDbCategory2;
import static eu.ba30.re.blocky.service.TestUtils.getNewInvoice;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class InvoiceServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;

    @Test(priority = 1)
    public void getInvoices()  {
        invoiceService.getInvoices();
    }

    @Test(priority = 2)
    public void create()  {
        final Invoice newInvoice = getNewInvoice();
        newInvoice.setId(null);
        invoiceService.create(newInvoice);
        assertNotNull(newInvoice.getId());

        final Invoice createdInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, newInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + newInvoice));
        assertReflectionEquals(newInvoice, createdInvoice);
    }

    @Test(priority = 3)
    public void updateWithoutAttachments()  {
        final Invoice actualInvoice = getNewInvoice();
        actualInvoice.setDetails("NewDetail");
        actualInvoice.setName("NewName");
        actualInvoice.setCategory(getDbCategory2());
        actualInvoice.setAttachments(Lists.newArrayList());

        invoiceService.update(actualInvoice);

        final Invoice updatedInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, actualInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + actualInvoice));

        assertReflectionEquals(actualInvoice, updatedInvoice);
    }

    @Test(priority = 3)
    public void updateWithAttachments()  {
        final Invoice actualInvoice = getNewInvoice();
        actualInvoice.setDetails("NewDetailWithAttachment");
        actualInvoice.setName("NewNameWithAttachment");
        actualInvoice.setCategory(getDbCategory2());

        final Attachment attachment = new Attachment();
        attachment.setMimeType("newMime");
        attachment.setContent("newAttachment".getBytes());
        attachment.setType(AttachmentType.PDF);
        attachment.setFileName("newFileName");
        attachment.setName("newName");
        actualInvoice.setAttachments(Lists.newArrayList(attachment));

        invoiceService.update(actualInvoice);

        assertNotNull(attachment.getId());

        final Invoice updatedInvoice = invoiceService.getInvoices()
                .stream()
                .filter(invoice -> Objects.equals(invoice, actualInvoice))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find invoice " + actualInvoice));

        assertReflectionEquals(actualInvoice, updatedInvoice);
    }

    @Test(priority = 4)
    public void remove()  {
        int size = invoiceService.getInvoices().size();
        invoiceService.remove(Lists.newArrayList(getNewInvoice()));
        assertEquals(invoiceService.getInvoices().size(), size - 1);
        invoiceService.remove(invoiceService.getInvoices());
        assertEquals(invoiceService.getInvoices().size(), 0);
    }
}