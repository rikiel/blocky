package eu.ba30.re.blocky.model.impl.other;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;

public class InvoiceImpl extends Invoice {
    private Integer id;
    private String name;
    private Category category;
    private String details;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private final List<Attachment> attachments = Lists.newArrayList();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public LocalDate getModificationDate() {
        return modificationDate;
    }

    @Override
    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    @Nonnull
    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public void setAttachments(@Nonnull List<Attachment> attachments) {
        this.attachments.clear();
        this.attachments.addAll(attachments);
    }
}
