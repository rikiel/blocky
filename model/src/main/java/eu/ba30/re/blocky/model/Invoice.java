package eu.ba30.re.blocky.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.cst.Category;

@Entity
@Table(name = "T_INVOICES")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DUAL_INVOICE_ID")
    @SequenceGenerator(name = "DUAL_INVOICE_ID", sequenceName = "S_INVOICE_ID", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "CREATION")
    private LocalDate creationDate;
    @Column(name = "LAST_MODIFICATION")
    private LocalDate modificationDate;
    @Column(name = "ATTACHMENT_ID")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", orphanRemoval = true, fetch = FetchType.EAGER)
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
}
