package eu.ba30.re.blocky.service;

import java.time.LocalDate;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.cst.Category;

public class TestUtils {
    private TestUtils() {
    }

    @Nonnull
    public static Invoice createDbInvoice() {
        final Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setName("Nazov#1");
        invoice.setCategory(getDbCategory());
        invoice.setDetails("Detail#1");
        invoice.setCreationDate(LocalDate.parse("2018-03-11"));
        invoice.setModificationDate(LocalDate.parse("2018-03-11"));
        invoice.setAttachments(Lists.newArrayList(getDbAttachment()));
        return invoice;
    }

    @Nonnull
    public static Invoice createNewInvoice() {
        final Invoice invoice = new Invoice();
        invoice.setId(2);
        invoice.setName("Nazov#2");
        invoice.setCategory(getDbCategory());
        invoice.setDetails("Detail#2");
        invoice.setCreationDate(LocalDate.parse("2018-03-13"));
        invoice.setModificationDate(LocalDate.parse("2018-03-13"));
        invoice.setAttachments(Lists.newArrayList(getMockedAttachment2()));
        return invoice;
    }

    @Nonnull
    public static Category getDbCategory() {
        final Category category = new Category();
        category.setId(123);
        category.setName("CategoryName");
        category.setDescription("CategoryDescription");
        return category;
    }

    public static Attachment getDbAttachment() {
        final Attachment attachment = new Attachment();
        attachment.setId(1);
        attachment.setName("Name#1");
        attachment.setMimeType("MimeType");
        attachment.setType(AttachmentType.IMAGE);
        attachment.setFileName("FileName#1");
        attachment.setContent("AHOJ".getBytes());
        return attachment;
    }

    @Nonnull
    public static Attachment getMockedAttachment2() {
        final Attachment attachment = new Attachment();
        attachment.setId(2);
        attachment.setName("MockedAttachment#2");
        attachment.setType(AttachmentType.PDF);
        attachment.setFileName("MockedFileName");
        attachment.setContent("AHOJ".getBytes());
        return attachment;
    }

    @Nonnull
    public static Attachment getMockedAttachment3() {
        final Attachment attachment = new Attachment();
        attachment.setId(3);
        attachment.setName("MockedAttachment#3");
        attachment.setType(AttachmentType.TEXT);
        attachment.setFileName("MockedFileName");
        attachment.setContent("AHOJ".getBytes());
        return attachment;
    }
}
