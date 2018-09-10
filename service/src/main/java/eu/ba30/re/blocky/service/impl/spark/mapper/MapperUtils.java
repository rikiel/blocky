package eu.ba30.re.blocky.service.impl.spark.mapper;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructField;

import eu.ba30.re.blocky.common.utils.Validate;
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
}
