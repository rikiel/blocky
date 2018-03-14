package eu.ba30.re.blocky.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.cst.Category;

public class Invoice {
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

    public void setAttachments(List<Attachment> attachments) {
        this.attachments.clear();
        this.attachments.addAll(attachments);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("category", category)
                .add("details", details)
                .add("creationDate", creationDate)
                .add("modificationDate", modificationDate)
                .add("attachments", attachments)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        final Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
