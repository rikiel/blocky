package eu.ba30.re.blocky.service.impl.spark;

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
import eu.ba30.re.blocky.service.CstManager;

@Service
public class SparkCstManagerImpl implements CstManager {
    @Autowired
    private SparkSession sparkSession;

    private Dataset<CategoryImpl> categoryDataset;

    @PostConstruct
    private void init() {
        categoryDataset = sparkSession.sql("SELECT * FROM global_temp.T_CST_CATEGORY").as(Encoders.bean(CategoryImpl.class));
    }

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Lists.newArrayList(categoryDataset.collectAsList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        final List<CategoryImpl> categoryByIdList = categoryDataset
                .where(new Column("ID").equalTo(categoryId))
                .collectAsList();
        Validate.equals(categoryByIdList.size(), 1, "Should return 1 element with ID = " + categoryId);

        return categoryByIdList.get(0);
    }
}
