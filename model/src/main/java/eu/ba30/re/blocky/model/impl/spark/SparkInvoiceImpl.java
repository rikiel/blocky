package eu.ba30.re.blocky.model.impl.spark;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;

public class SparkInvoiceImpl extends Invoice {
    private Integer id;
    private String name;
    private Category category;
    private String details;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private final List<Attachment> attachments = Lists.newArrayList();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Nonnull
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(@Nonnull List<Attachment> attachments) {
        this.attachments.clear();
        this.attachments.addAll(attachments);
    }
}
