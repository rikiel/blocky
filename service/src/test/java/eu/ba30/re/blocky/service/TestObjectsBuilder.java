package eu.ba30.re.blocky.service;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.utils.Validate;

public class TestObjectsBuilder {
    public static final int INVOICE_ID_1 = 1;
    public static final int INVOICE_ID_2 = 10;
    public static final int INVOICE_ID_3 = 11;
    private List<Invoice> invoices = Lists.newArrayList();
    private List<Attachment> attachments = Lists.newArrayList();
    private List<Category> categories = Lists.newArrayList();

    public TestObjectsBuilder invoice1() {
        final Invoice invoice = new Invoice();
        invoices.add(invoice);
        invoice.setId(INVOICE_ID_1);
        invoice.setName("Nazov#1");
        invoice.setDetails("Detail#1");
        invoice.setCreationDate(LocalDate.parse("2018-03-11"));
        invoice.setModificationDate(LocalDate.parse("2018-03-11"));

        if (!categories.isEmpty()) {
            Validate.equals(categories.size(), 1, "Expected 1 category");
            invoice.setCategory(categories.get(0));
        }
        attachments.forEach(attachment -> attachment.setInvoice(invoice));
        invoice.setAttachments(attachments);
        categories = Lists.newArrayList();
        attachments = Lists.newArrayList();
        return this;
    }

    public TestObjectsBuilder invoice2() {
        final Invoice invoice = new Invoice();
        invoices.add(invoice);
        invoice.setId(INVOICE_ID_2);
        invoice.setName("Nazov#2");
        invoice.setDetails("Detail#2");
        invoice.setCreationDate(LocalDate.parse("2018-03-13"));
        invoice.setModificationDate(LocalDate.parse("2018-03-13"));

        if (!categories.isEmpty()) {
            Validate.equals(categories.size(), 1, "Expected 1 category");
            invoice.setCategory(categories.get(0));
        }
        invoice.setAttachments(attachments);
        categories = Lists.newArrayList();
        attachments = Lists.newArrayList();
        return this;
    }

    public TestObjectsBuilder invoice3() {
        final Invoice invoice = new Invoice();
        invoices.add(invoice);
        invoice.setId(INVOICE_ID_3);
        invoice.setName("Nazov#3");
        invoice.setDetails("Detail#3");
        invoice.setCreationDate(LocalDate.parse("2018-03-13"));
        invoice.setModificationDate(LocalDate.parse("2018-03-13"));

        if (!categories.isEmpty()) {
            Validate.equals(categories.size(), 1, "Expected 1 category");
            invoice.setCategory(categories.get(0));
        }
        invoice.setAttachments(attachments);
        categories = Lists.newArrayList();
        attachments = Lists.newArrayList();
        return this;
    }

    public TestObjectsBuilder invoiceId(int id) {
        invoices.get(invoices.size() - 1).setId(id);
        return this;
    }

    public TestObjectsBuilder category1() {
        final Category category = new Category();
        categories.add(category);
        category.setId(1);
        category.setName("CategoryName#1");
        category.setDescription("CategoryDescription#1");
        return this;
    }

    public TestObjectsBuilder category2() {
        final Category category = new Category();
        categories.add(category);
        category.setId(2);
        category.setName("CategoryName#2");
        category.setDescription("CategoryDescription#2");
        return this;
    }

    public TestObjectsBuilder attachment1() {
        final Attachment attachment = new Attachment();
        attachments.add(attachment);
        attachment.setId(1);
        attachment.setName("Name#1");
        attachment.setMimeType("MimeType#1");
        attachment.setAttachmentType(AttachmentType.IMAGE);
        attachment.setFileName("FileName#1");
        attachment.setContent("AHOJ1".getBytes());
        return this;
    }

    public TestObjectsBuilder attachment2() {
        final Attachment attachment = new Attachment();
        attachments.add(attachment);
        attachment.setId(2);
        attachment.setName("Name#2");
        attachment.setMimeType("MimeType#2");
        attachment.setAttachmentType(AttachmentType.PDF);
        attachment.setFileName("FileName#2");
        attachment.setContent("AHOJ2".getBytes());
        return this;
    }

    public TestObjectsBuilder attachment3() {
        final Attachment attachment = new Attachment();
        attachments.add(attachment);
        attachment.setId(3);
        attachment.setName("Name#3");
        attachment.setMimeType("MimeType3");
        attachment.setAttachmentType(AttachmentType.TEXT);
        attachment.setFileName("FileName#3");
        attachment.setContent("AHOJ3".getBytes());
        return this;
    }

    public TestObjectsBuilder attachmentWithoutId() {
        attachments.get(attachments.size() - 1).setId(null);
        return this;
    }

    @Nonnull
    public List<Invoice> buildInvoices() {
        return invoices;
    }

    @Nonnull
    public Invoice buildSingleInvoice() {
        Validate.equals(invoices.size(), 1, "Single invoice is expected");
        return invoices.get(0);
    }

    @Nonnull
    public List<Attachment> buildAttachments() {
        return attachments;
    }

    @Nonnull
    public Attachment buildSingleAttachment() {
        Validate.equals(attachments.size(), 1, "Single attachment is expected");
        return attachments.get(0);
    }

    @Nonnull
    public Category buildSingleCategory() {
        Validate.equals(categories.size(), 1, "Single attachment is expected");
        return categories.get(0);
    }

    @Nonnull
    public List<Category> buildCategories() {
        return categories;
    }
}
