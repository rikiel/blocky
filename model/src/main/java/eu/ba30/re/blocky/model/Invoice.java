package eu.ba30.re.blocky.model;

import java.time.LocalDate;
import java.util.List;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.cst.Category;

public class Invoice {
    private Integer id;
    private String name;
    private Category category;
    private String details;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private List<Attachment> attachements;

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

    public List<Attachment> getAttachements() {
        return attachements;
    }

    public void setAttachements(List<Attachment> attachements) {
        this.attachements = attachements;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("category", category)
                .add("creationDate", creationDate)
                .add("modificationDate", modificationDate)
                .add("attachements", attachements)
                .toString();
    }
}
