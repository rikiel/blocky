package eu.ba30.re.blocky.service.impl.spark.db.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nonnull;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;
import eu.ba30.re.blocky.service.impl.spark.mapper.SparkCategoryMapper;

@Service
public class SparkDbCstCategoryRepositoryImpl implements CstCategoryRepository, Serializable {
    private static final String TABLE_NAME = "T_CST_CATEGORY";

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private String jdbcConnectionUrl;
    @Autowired
    private Properties jdbcConnectionProperties;

    @Autowired
    private SparkCategoryMapper categoryMapper;

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Lists.newArrayList(map(getActualDataset()).collectAsList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        final Dataset<SparkCategoryImpl> byId = map(getActualDataset()
                .where(new Column("ID").equalTo(categoryId)));

        Validate.equals(byId.count(), 1, String.format("Should exist 1 element with id %s. Found %s", categoryId, byId.count()));
        return byId.first();
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        final Dataset<Row> dataset = sparkSession.createDataFrame(sparkSession
                        .read()
                        .jdbc(jdbcConnectionUrl, TABLE_NAME, jdbcConnectionProperties).rdd(),
                categoryMapper.getDbStructure());
        dataset.show();
        return dataset;
    }

    @Nonnull
    private Dataset<SparkCategoryImpl> map(Dataset<Row> dataset) {
        return dataset.map((MapFunction<Row, SparkCategoryImpl>) categoryMapper::mapRow, Encoders.bean(SparkCategoryImpl.class));
    }

}
