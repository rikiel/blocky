package eu.ba30.re.blocky.model.impl.hibernate;

import java.time.LocalDate;
import java.util.List;

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

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.hibernate.cst.HibernateCategoryImpl;

@Entity
@Table(name = "T_INVOICES")
public class HibernateInvoiceImpl extends Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DUAL_INVOICE_ID")
    @SequenceGenerator(name = "DUAL_INVOICE_ID", sequenceName = "S_INVOICE_ID", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @ManyToOne(targetEntity = HibernateCategoryImpl.class)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "CREATION")
    private LocalDate creationDate;
    @Column(name = "LAST_MODIFICATION")
    private LocalDate modificationDate;
    @Column(name = "ATTACHMENT_ID")
    @OneToMany(cascade = CascadeType.ALL, targetEntity = HibernateAttachmentImpl.class, mappedBy = "invoice", orphanRemoval = true, fetch = FetchType.EAGER)
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
