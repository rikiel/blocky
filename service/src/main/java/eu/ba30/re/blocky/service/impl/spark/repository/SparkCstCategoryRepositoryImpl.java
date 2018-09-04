package eu.ba30.re.blocky.service.impl.spark.repository;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class SparkCstCategoryRepositoryImpl implements CstCategoryRepository, Serializable {
    private static final String TABLE_NAME = "T_CST_CATEGORY";

    @Autowired
    private SparkSession sparkSession;

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Lists.newArrayList(sparkSession
                .sql("SELECT * FROM global_temp." + TABLE_NAME)
                .as(Encoders.bean(SparkCategoryImpl.class))
                .collectAsList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        final Dataset<SparkCategoryImpl> byId = sparkSession
                .sql("SELECT * FROM global_temp." + TABLE_NAME)
                .where(new Column("ID").equalTo(categoryId))
                .as(Encoders.bean(SparkCategoryImpl.class));

        Validate.equals(byId.count(), 1, String.format("Should exist 1 element with id %s. Found %s", categoryId, byId.count()));
        return byId.first();
    }
}
