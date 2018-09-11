package eu.ba30.re.blocky.service.impl.spark.common.mapper;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.impl.spark.cst.SparkCategoryImpl;
import eu.ba30.re.blocky.service.impl.spark.common.SparkUtils;

@Service
public class SparkCategoryMapper implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkCategoryMapper.class);

    public static final String TABLE_NAME = "T_CST_CATEGORY";

    @Nonnull
    public SparkCategoryImpl mapRow(@Nonnull Row row) {
        final SparkCategoryImpl category = new SparkCategoryImpl();

        category.setId(row.getInt(SparkUtils.getColumnIndex(row, Columns.ID)));
        category.setName(row.getString(SparkUtils.getColumnIndex(row, Columns.NAME)));
        category.setDescription(row.getString(SparkUtils.getColumnIndex(row, Columns.DESCRIPTION)));

        log.debug("Loaded category: {}", category);
        return category;
    }

    @Nonnull
    public StructType getDbStructure() {
        return new SparkUtils.FieldBuilder()
                .addRequiredField(Columns.ID, DataTypes.IntegerType)
                .addRequiredField(Columns.NAME, DataTypes.StringType)
                .addRequiredField(Columns.DESCRIPTION, DataTypes.StringType)
                .build();
    }

    @Nonnull
    public Dataset<SparkCategoryImpl> map(@Nonnull Dataset<Row> dataset) {
        return dataset.map((MapFunction<Row, SparkCategoryImpl>) this::mapRow, Encoders.bean(SparkCategoryImpl.class));
    }

    public enum Columns implements SparkUtils.TableColumn {
        ID("ID"),
        NAME("NAME"),
        DESCRIPTION("DESCR");

        private final String name;

        Columns(String name) {
            this.name = name;
        }

        @Override
        @Nonnull
        public String getFullColumnName() {
            return TABLE_NAME + DELIMITER + name;
        }
    }
}
