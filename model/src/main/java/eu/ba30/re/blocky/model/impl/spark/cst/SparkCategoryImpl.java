package eu.ba30.re.blocky.model.impl.spark.cst;

import java.io.Serializable;

import eu.ba30.re.blocky.model.cst.Category;

public class SparkCategoryImpl extends Category implements Serializable {
    private Integer id;
    private String name;
    private String description;

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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
