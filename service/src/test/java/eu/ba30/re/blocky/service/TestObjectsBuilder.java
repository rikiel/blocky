package eu.ba30.re.blocky.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.hibernate.HibernateAttachmentImpl;
import eu.ba30.re.blocky.model.impl.hibernate.HibernateInvoiceImpl;
import eu.ba30.re.blocky.model.impl.hibernate.cst.HibernateCategoryImpl;
import eu.ba30.re.blocky.model.impl.other.AttachmentImpl;
import eu.ba30.re.blocky.model.impl.other.InvoiceImpl;
import eu.ba30.re.blocky.model.impl.other.cst.CategoryImpl;
import eu.ba30.re.blocky.model.impl.spark.SparkAttachmentImpl;
import eu.ba30.re.blocky.model.impl.spark.SparkInvoiceImpl;
import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;

public class TestObjectsBuilder {
    public static final int INVOICE_ID_1 = 1;
    public static final int INVOICE_ID_2 = 10;
    public static final int ATTACHMENT_ID_1 = 1;
    public static final int ATTACHMENT_ID_2 = 10;
    public static final int ATTACHMENT_ID_3 = 11;

    private final FrameworkType frameworkType;

    private List<Invoice> invoices = Lists.newArrayList();
    private List<Attachment> attachments = Lists.newArrayList();
    private List<Category> categories = Lists.newArrayList();

    public TestObjectsBuilder(@Nonnull final FrameworkType frameworkType) {
        this.frameworkType = frameworkType;
    }

    public TestObjectsBuilder invoiceEmpty() {
        invoices.add(invoice());
        return this;
    }

    public TestObjectsBuilder invoice1() {
        final Invoice invoice = invoice();
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
        attachments.stream().filter(Objects::nonNull).forEach(attachment -> {
            if (attachment instanceof HibernateAttachmentImpl) {
                ((HibernateAttachmentImpl) attachment).setInvoice(invoice);
            } else if (attachment instanceof SparkAttachmentImpl) {
                ((SparkAttachmentImpl) attachment).setInvoiceId(invoice.getId());
            }
        });
        invoice.setAttachments(attachments);
        categories = Lists.newArrayList();
        attachments = Lists.newArrayList();
        return this;
    }

    public TestObjectsBuilder invoice2() {
        final Invoice invoice = invoice();
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
        attachments.stream().filter(Objects::nonNull).forEach(attachment -> {
            if (attachment instanceof HibernateAttachmentImpl) {
                ((HibernateAttachmentImpl) attachment).setInvoice(invoice);
            } else if (attachment instanceof SparkAttachmentImpl) {
                ((SparkAttachmentImpl) attachment).setInvoiceId(invoice.getId());
            }
        });
        invoice.setAttachments(attachments);
        categories = Lists.newArrayList();
        attachments = Lists.newArrayList();
        return this;
    }

    public TestObjectsBuilder invoiceId(Integer id) {
        invoices.get(invoices.size() - 1).setId(id);
        return this;
    }

    public TestObjectsBuilder category1() {
        final Category category = category();
        categories.add(category);
        category.setId(1);
        category.setName("CategoryName#1");
        category.setDescription("CategoryDescription#1");
        return this;
    }

    public TestObjectsBuilder category2() {
        final Category category = category();
        categories.add(category);
        category.setId(2);
        category.setName("CategoryName#2");
        category.setDescription("CategoryDescription#2");
        return this;
    }

    public TestObjectsBuilder attachmentNull() {
        attachments.add(null);
        return this;
    }

    public TestObjectsBuilder attachmentEmpty() {
        attachments.add(attachment());
        return this;
    }

    public TestObjectsBuilder attachment1() {
        final Attachment attachment = attachment();
        attachments.add(attachment);
        attachment.setId(ATTACHMENT_ID_1);
        attachment.setName("Name#1");
        attachment.setMimeType("MimeType#1");
        attachment.setAttachmentType(AttachmentType.IMAGE);
        attachment.setFileName("FileName#1");
        attachment.setContent("AHOJ1".getBytes());
        if (invoices.size() == 1) {
            if (attachment instanceof HibernateAttachmentImpl) {
                ((HibernateAttachmentImpl) attachment).setInvoice(invoices.get(0));
            } else if (attachment instanceof SparkAttachmentImpl) {
                ((SparkAttachmentImpl) attachment).setInvoiceId(invoices.get(0).getId());
            }
        }
        return this;
    }

    public TestObjectsBuilder attachment2() {
        final Attachment attachment = attachment();
        attachments.add(attachment);
        attachment.setId(ATTACHMENT_ID_2);
        attachment.setName("Name#2");
        attachment.setMimeType("MimeType#2");
        attachment.setAttachmentType(AttachmentType.PDF);
        attachment.setFileName("FileName#2");
        attachment.setContent("AHOJ2".getBytes());
        if (invoices.size() == 1) {
            if (attachment instanceof HibernateAttachmentImpl) {
                ((HibernateAttachmentImpl) attachment).setInvoice(invoices.get(0));
            } else if (attachment instanceof SparkAttachmentImpl) {
                ((SparkAttachmentImpl) attachment).setInvoiceId(invoices.get(0).getId());
            }
        }
        return this;
    }

    public TestObjectsBuilder attachment3() {
        final Attachment attachment = attachment();
        attachments.add(attachment);
        attachment.setId(ATTACHMENT_ID_3);
        attachment.setName("Name#3");
        attachment.setMimeType("MimeType3");
        attachment.setAttachmentType(AttachmentType.TEXT);
        attachment.setFileName("FileName#3");
        attachment.setContent("AHOJ3".getBytes());
        if (invoices.size() == 1) {
            if (attachment instanceof HibernateAttachmentImpl) {
                ((HibernateAttachmentImpl) attachment).setInvoice(invoices.get(0));
            } else if (attachment instanceof SparkAttachmentImpl) {
                ((SparkAttachmentImpl) attachment).setInvoiceId(invoices.get(0).getId());
            }
        }
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

    @Nonnull
    private Invoice invoice() {
        switch (frameworkType) {
            case JDBC:
            case JDBC_TEMPLATE:
            case MY_BATIS:
                return new InvoiceImpl();
            case HIBERNATE:
                return new HibernateInvoiceImpl();
            case SPARK:
                return new SparkInvoiceImpl();
            default:
                throw new IllegalStateException("Not known framework " + frameworkType);
        }
    }

    @Nonnull
    private Attachment attachment() {
        switch (frameworkType) {
            case JDBC:
            case JDBC_TEMPLATE:
            case MY_BATIS:
                return new AttachmentImpl();
            case HIBERNATE:
                return new HibernateAttachmentImpl();
            case SPARK:
                return new SparkAttachmentImpl();
            default:
                throw new IllegalStateException("Not known framework " + frameworkType);
        }
    }

    @Nonnull
    private Category category() {
        switch (frameworkType) {
            case JDBC:
            case JDBC_TEMPLATE:
            case MY_BATIS:
                return new CategoryImpl();
            case HIBERNATE:
                return new HibernateCategoryImpl();
            case SPARK:
                return new SparkCategoryImpl();
            default:
                throw new IllegalStateException("Not known framework " + frameworkType);
        }
    }

    public enum FrameworkType {
        JDBC,
        JDBC_TEMPLATE,
        MY_BATIS,
        HIBERNATE,
        SPARK
    }
}
