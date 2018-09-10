package eu.ba30.re.blocky.service.impl.spark.csv;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.spark.mapper.SparkCategoryMapper;

@Service
public class SparkCsvCstManagerImpl implements CstManager, Serializable {
    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private String categoryCsvFileName;

    @Autowired
    private SparkCategoryMapper categoryMapper;

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Lists.newArrayList(categoryMapper.map(getActualDataset())
                .collectAsList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        final Dataset<SparkCategoryImpl> byId = categoryMapper.map(getActualDataset()
                .where(new Column("ID").equalTo(categoryId)));

        Validate.equals(byId.count(), 1, String.format("Should exist 1 element with id %s. Found %s", categoryId, byId.count()));
        return byId.first();
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        final Dataset<Row> dataset = sparkSession.read()
                .option("mode", "FAILFAST")
                .schema(categoryMapper.getDbStructure())
                .csv(categoryCsvFileName);
        dataset.show();
        return dataset;
    }
}
