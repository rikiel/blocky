package eu.ba30.re.blocky.service.impl.spark.db.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;
import eu.ba30.re.blocky.service.impl.spark.common.SparkUtils;
import eu.ba30.re.blocky.service.impl.spark.common.mapper.SparkCategoryMapper;

@Service
public class SparkDbCstCategoryRepositoryImpl implements CstCategoryRepository, Serializable {
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
        return Lists.newArrayList(categoryMapper.map(getActualDataset()).collectAsList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        final Dataset<SparkCategoryImpl> byId = categoryMapper.map(getActualDataset()
                .where(SparkUtils.column(SparkCategoryMapper.Columns.ID).equalTo(categoryId)));

        Validate.equals(byId.count(), 1, String.format("Should exist 1 element with id %s. Found %s", categoryId, byId.count()));
        return byId.first();
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        return SparkUtils.loadJdbc(sparkSession,
                jdbcConnectionUrl,
                jdbcConnectionProperties,
                SparkCategoryMapper.TABLE_NAME,
                categoryMapper.getDbStructure(),
                SparkCategoryMapper.Columns.ID);
    }
}
