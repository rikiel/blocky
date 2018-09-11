package eu.ba30.re.blocky.service.impl.spark.common;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
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
                                    @Nonnull TableColumn column2,
                                    @Nonnull TableColumn sortBy) {
        Validate.notNull(dataset1, dataset2, column1, column2, sortBy);

        log.debug("Joining tables!. Result after join:");
        final Dataset<Row> joined = dataset1.join(dataset2,
                column(column1).equalTo(column(column2)))
                .sort(column(sortBy).asc());
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

    @Nonnull
    public static Dataset<Row> loadCsv(@Nonnull SparkSession sparkSession,
                                       @Nonnull StructType databaseStructure,
                                       @Nonnull String csvFileName,
                                       @Nullable TableColumn sortBy) {
        Validate.notNull(sparkSession, databaseStructure, csvFileName);

        Dataset<Row> dataset = sparkSession.read()
                .option("mode", "FAILFAST")
                .schema(databaseStructure)
                .csv(csvFileName);
        if (sortBy != null) {
            dataset = dataset.orderBy(SparkUtils.column(sortBy).asc());
        }

        log.debug("Loaded file {}", csvFileName);
        dataset.printSchema();
        dataset.show();
        return dataset;

    }

    @Nonnull
    public static Dataset<Row> loadJdbc(@Nonnull SparkSession sparkSession,
                                        @Nonnull String jdbcConnectionUrl,
                                        @Nonnull Properties jdbcConnectionProperties,
                                        @Nonnull String tableName,
                                        @Nonnull StructType databaseStructure,
                                        @Nullable TableColumn sortBy) {
        Validate.notNull(sparkSession, jdbcConnectionUrl, jdbcConnectionProperties, tableName, databaseStructure);

        Dataset<Row> dataset = sparkSession.createDataFrame(sparkSession
                        .read()
                        .jdbc(jdbcConnectionUrl, tableName, jdbcConnectionProperties)
                        .collectAsList(),
                databaseStructure);
        if (sortBy != null) {
            dataset = dataset.orderBy(SparkUtils.column(sortBy).asc());
        }
        dataset.show();
        return dataset;
    }

    public static void saveCsv(@Nonnull SparkSession sparkSession,
                               @Nonnull Dataset<Row> dataset,
                               @Nonnull String csvFileName) {
        Validate.notNull(sparkSession, csvFileName);

        log.debug("Updating database");
        dataset.show();
        dataset
                .write()
                .option("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false")
                .mode(SaveMode.Overwrite)
                .csv(csvFileName);
    }

    public static void saveJdbc(@Nonnull SparkSession sparkSession,
                                @Nonnull Dataset<Row> dataset,
                                @Nonnull String jdbcConnectionUrl,
                                @Nonnull Properties jdbcConnectionProperties,
                                @Nonnull String tableName) {
        Validate.notNull(sparkSession, dataset, jdbcConnectionUrl, jdbcConnectionProperties, tableName);

        log.debug("Updating database");
        dataset.show();
        dataset
                .write()
                .mode(SaveMode.Overwrite)
                .jdbc(jdbcConnectionUrl, tableName, jdbcConnectionProperties);
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
