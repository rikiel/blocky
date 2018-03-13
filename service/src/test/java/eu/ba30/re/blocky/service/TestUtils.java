package eu.ba30.re.blocky.service;

import java.time.LocalDate;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;

public class TestUtils {
    private TestUtils() {
    }

    @Nonnull
    public static Invoice createDbInvoice() {
        final Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setName("Nazov#1");
        invoice.setCategory(getMockedCategory());
        invoice.setDetails("Detail#1");
        invoice.setCreationDate(LocalDate.parse("2018-03-11"));
        invoice.setModificationDate(LocalDate.parse("2018-03-11"));
        invoice.setAttachments(Lists.newArrayList(getMockedAttachment()));
        return invoice;
    }

    @Nonnull
    public static Invoice createNewInvoice() {
        final Invoice invoice = new Invoice();
        invoice.setId(2);
        invoice.setName("Nazov#2");
        invoice.setCategory(getMockedCategory());
        invoice.setDetails("Detail#2");
        invoice.setCreationDate(LocalDate.parse("2018-03-13"));
        invoice.setModificationDate(LocalDate.parse("2018-03-13"));
        invoice.setAttachments(Lists.newArrayList(getMockedAttachment()));
        return invoice;
    }

    @Nonnull
    public static Category getMockedCategory() {
        final Category category = new Category();
        category.setId(123);
        category.setName("MockedCategory");
        category.setDescription("MockedCategoryDescription");
        return category;
    }

    @Nonnull
    public static Attachment getMockedAttachment() {
        final Attachment attachment = new Attachment();
        attachment.setId(2);
        attachment.setName("MockedAttachment");
        attachment.setType(Attachment.Type.PDF);
        attachment.setFileName("MockedFileName");
        attachment.setContent("AHOJ".getBytes());
        return attachment;
    }
}
