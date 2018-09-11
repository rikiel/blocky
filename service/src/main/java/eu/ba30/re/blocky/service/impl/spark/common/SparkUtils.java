package eu.ba30.re.blocky.service.impl.spark.common;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.HasId;

public class SparkUtils {
    private static final Logger log = LoggerFactory.getLogger(SparkUtils.class);

    public static int getColumnIndex(@Nonnull Row row, @Nonnull TableColumn column) {
        return row.fieldIndex(column.getFullColumnName());
    }

    @Nonnull
    public static Dataset<Row> join(@Nonnull Dataset<Row> dataset1,
                                    @Nonnull Dataset<Row> dataset2,
                                    @Nonnull TableColumn column1,
                                    @Nonnull TableColumn column2) {
        log.debug("Joining tables:");
        dataset1.printSchema();
        dataset1.show();

        dataset2.printSchema();
        dataset2.show();

        log.debug("Result after join:");
        final Dataset<Row> joined = dataset1.join(dataset2,
                new Column(column1.getFullColumnName()).equalTo(new Column(column2.getFullColumnName())));
        joined.printSchema();
        joined.show();

        return joined;
    }

    @Nonnull
    public static Column column(@Nonnull TableColumn column) {
        return new Column(column.getFullColumnName());
    }

    @Nonnull
    public static Object[] getIds(@Nonnull final List<? extends HasId> ids) {
        return ids.stream().map(HasId::getId).distinct().toArray();
    }

    public interface TableColumn {
        String DELIMITER = "-";

        String getFullColumnName();
    }

    public static class FieldBuilder {
        private final List<StructField> fields = Lists.newArrayList();

        @Nonnull
        public FieldBuilder addRequiredField(@Nonnull TableColumn column, DataType dataType) {
            fields.add(DataTypes.createStructField(column.getFullColumnName(), dataType, false));
            return this;
        }

        @Nonnull
        public StructType build() {
            return DataTypes.createStructType(fields);
        }
    }
}
