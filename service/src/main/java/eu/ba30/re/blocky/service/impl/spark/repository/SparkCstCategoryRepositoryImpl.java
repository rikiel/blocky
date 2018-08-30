package eu.ba30.re.blocky.service.impl.spark.repository;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.other.cst.CategoryImpl;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class SparkCstCategoryRepositoryImpl implements CstCategoryRepository {
    private static final String TABLE_NAME = "T_CST_CATEGORY";

    @Autowired
    private SparkSession sparkSession;

    private Dataset<CategoryImpl> categoryDataset;

    @PostConstruct
    private void init() {
        updateDataset(sparkSession
                .sql("SELECT * FROM global_temp." + TABLE_NAME)
                .as(Encoders.bean(CategoryImpl.class)));
    }

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Lists.newArrayList(categoryDataset.collectAsList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        final Dataset<CategoryImpl> byId = categoryDataset
                .where(new Column("ID").equalTo(categoryId));

        Validate.equals(byId.count(), 1, String.format("Should exist 1 element with id %s. Found %s", categoryId, byId.count()));
        return byId.first();
    }

    private void updateDataset(@Nonnull final Dataset<CategoryImpl> categoryDataset) {
        Validate.notNull(categoryDataset);
        categoryDataset.createOrReplaceGlobalTempView(TABLE_NAME);
        this.categoryDataset = categoryDataset;
    }
}
