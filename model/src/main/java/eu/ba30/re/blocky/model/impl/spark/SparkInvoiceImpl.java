package eu.ba30.re.blocky.model.impl.spark;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;

public class SparkInvoiceImpl extends Invoice implements Serializable {
    private Integer id;
    private String name;
    private Category category;
    private String details;
    private Date creationDateSql;
    private Date modificationDateSql;
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
        return creationDateSql.toLocalDate();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDateSql = Date.valueOf(creationDate);
    }

    public LocalDate getModificationDate() {
        return modificationDateSql.toLocalDate();
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDateSql = Date.valueOf(modificationDate);
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
