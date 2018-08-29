package eu.ba30.re.blocky.service.impl.spark.model;

import java.sql.Date;

import eu.ba30.re.blocky.common.utils.Validate;

public class InvoiceDb {
    private Integer id;
    private String name;
    private Integer categoryId;
    private String details;
    private Date creationDate;
    private Date modificationDate;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void validate() {
        Validate.notNull(id, name, categoryId, details, creationDate, modificationDate);
    }
}
