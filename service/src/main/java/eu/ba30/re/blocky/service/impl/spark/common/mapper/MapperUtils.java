package eu.ba30.re.blocky.service.impl.spark.common.mapper;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.HasId;
import scala.collection.JavaConversions;

public class MapperUtils {
    @Nonnull
    public static Dataset<Row> rename(@Nonnull Dataset<Row> dataset, @Nonnull String addPrefix) {
        Validate.notNull(dataset, addPrefix);
        final String prefix = addPrefix + ".";
        for (StructField field : JavaConversions.seqAsJavaList(dataset.schema())) {
            dataset = dataset.withColumnRenamed(field.name(), prefix + field.name());
        }
        return dataset;
    }

    public static int getColumnIndex(@Nonnull Row row, @Nonnull TableColumn column) {
        return row.fieldIndex(column.getName());
    }

    @Nonnull
    public static Column column(@Nonnull TableColumn column) {
        return new Column(column.getColumnName());
    }

    @Nonnull
    public static StructField createRequiredDbStructField(@Nonnull TableColumn column, DataType dataType) {
        return DataTypes.createStructField(column.getColumnName(), dataType, false);
    }

    @Nonnull
    public static Object[] getIds(@Nonnull final List<? extends HasId> ids) {
        return ids.stream().map(HasId::getId).distinct().toArray();
    }

    public interface TableColumn {
        String getName();

        String getColumnName();
    }
}
