package eu.ba30.re.blocky.service.impl.spark.mapper;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;

@Service
public class SparkCategoryMapper implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkCategoryMapper.class);

    @Nonnull
    public SparkCategoryImpl mapRow(@Nonnull Row row) {
        final SparkCategoryImpl category = new SparkCategoryImpl();

        category.setId(row.getInt(row.fieldIndex("ID")));
        category.setName(row.getString(row.fieldIndex("NAME")));
        category.setDescription(row.getString(row.fieldIndex("DESCR")));

        log.debug("Loaded category: {}", category);
        return category;
    }

    @Nonnull
    public StructType getDbStructure() {
        return DataTypes.createStructType(Lists.newArrayList(
                DataTypes.createStructField("ID", DataTypes.IntegerType, false),
                DataTypes.createStructField("NAME", DataTypes.StringType, false),
                DataTypes.createStructField("DESCR", DataTypes.StringType, false)
        ));
    }
}
