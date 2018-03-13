package eu.ba30.re.blocky.service.impl.db.impl;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.ServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import mockit.Capturing;
import mockit.Expectations;

import static org.testng.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class InvoiceRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Capturing
    private AttachmentsRepository attachmentsRepository;

    @Capturing
    private CstManager cstManager;

    @Autowired
    private InvoiceRepositoryImpl invoiceRepository;

    @Test
    public void getInvoices() {
        new Expectations() {{
            attachmentsRepository.getAttachmentList(1);
            result = Lists.newArrayList(getMockedAttachment());

            cstManager.getCategory(1);
            result = getMockedCategory();
        }};
        final List<Invoice> invoices = invoiceRepository.getInvoices();
        assertEquals(invoices.size(), 1);
        assertReflectionEquals(getExpectedInvoiceList(), invoices);
    }

    @Test
    public void remove() {
    }

    @Test
    public void create() {
    }

    @Nonnull
    private List<Invoice> getExpectedInvoiceList() {
        final Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setName("Nazov#1");
        invoice.setCategory(getMockedCategory());
        invoice.setDetails("Detail#1");
        invoice.setCreationDate(LocalDate.parse("2018-03-11"));
        invoice.setModificationDate(LocalDate.parse("2018-03-11"));
        invoice.setAttachments(Lists.newArrayList(getMockedAttachment()));

        return Lists.newArrayList(invoice);
    }

    @Nonnull
    private static Category getMockedCategory() {
        final Category category = new Category();
        category.setId(123);
        category.setName("MockedCategory");
        category.setDescription("MockedCategoryDescription");
        return category;
    }

    @Nonnull
    private static Attachment getMockedAttachment() {
        final Attachment attachment = new Attachment();
        attachment.setName("MockedAttachment");
        attachment.setType(Attachment.Type.PDF);
        attachment.setFileName("MockedFileName");
        return attachment;
    }
}