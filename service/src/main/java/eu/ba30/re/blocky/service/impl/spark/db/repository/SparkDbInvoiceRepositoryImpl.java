package eu.ba30.re.blocky.service.impl.spark.db.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.spark.SparkInvoiceImpl;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbTransactionManager;

@Service
public class SparkDbInvoiceRepositoryImpl implements InvoiceRepository, Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkDbInvoiceRepositoryImpl.class);

    private static final String TABLE_NAME = "T_INVOICES";
    private static final String CATEGORY_TABLE_NAME = "T_CST_CATEGORY";

    private final InvoiceRowMapper MAPPER = new InvoiceRowMapper();
    private int nextId = 10;

    @Autowired
    private SparkDbTransactionManager transactionManager;

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private String jdbcConnectionUrl;
    @Autowired
    private Properties jdbcConnectionProperties;

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        return Lists.newArrayList(
                map(getActualDataset()).collectAsList());
    }

    @Override
    public void remove(@Nonnull List<Invoice> invoices) {
        transactionManager.newTransaction(
                new SparkDbTransactionManager.Transaction() {
                    final List<SparkInvoiceImpl> actualDatabaseSnapshot = map(getActualDataset()).collectAsList();
                    boolean wasRemoved = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> toRemove = getActualInvoicesFromDb(invoices);
                        Validate.equals(toRemove.count(), invoices.size(),
                                String.format("Record count does not match for removing. Actual %s, expected %s", toRemove.count(), invoices.size()));

                        updateDatabase(createDbRows(actualDatabaseSnapshot).except(createDbRows(map(toRemove).collectAsList())));
                        wasRemoved = true;
                    }

                    @Override
                    public void onRollback() {
                        if (wasRemoved) {
                            updateDatabase(createDbRows(actualDatabaseSnapshot).union(createDbRows(invoices)));
                        }
                    }
                });
    }

    @Override
    public void create(@Nonnull Invoice invoice) {
        Validate.notNull(invoice);

        transactionManager.newTransaction(
                new SparkDbTransactionManager.Transaction() {
                    final List<Invoice> invoices = Lists.newArrayList(invoice);
                    boolean wasInserted = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> actualRows = getActualInvoicesFromDb(invoices);
                        Validate.equals(actualRows.count(),
                                0,
                                String.format("Should not exist any invoice that is being created. Found %s", actualRows.count()));

                        final Dataset<Row> insertedData = createDbRows(invoices);
                        updateDatabase(createDbRows(map(getActualDataset()).collectAsList()).union(insertedData));
                    }

                    @Override
                    public void onRollback() {
                        if (wasInserted) {
                            updateDatabase(getActualDataset().except(getActualInvoicesFromDb(invoices)));
                        }
                    }
                });
    }

    @Override
    public int getNextInvoiceId() {
        return nextId++;
    }

    @Nonnull
    private Dataset<Row> getActualInvoicesFromDb(@Nonnull final List<? extends Invoice> invoices) {
        return getActualDataset().where(new Column("ID").isin(MAPPER.ids(invoices)));
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        return sparkSession.createDataFrame(sparkSession
                        .read()
                        .jdbc(jdbcConnectionUrl, TABLE_NAME, jdbcConnectionProperties).rdd(),
                MAPPER.getDbStructure());
    }

    @Nonnull
    private Dataset<Row> getCategoryDataset() {
        return sparkSession
                .read()
                .jdbc(jdbcConnectionUrl, CATEGORY_TABLE_NAME, jdbcConnectionProperties);
    }

    @Nonnull
    private Dataset<SparkInvoiceImpl> map(Dataset<Row> dataset) {
        dataset = dataset
                .withColumnRenamed("ID", "INVOICE_ID")
                .withColumnRenamed("NAME", "INVOICE_NAME");
        final Dataset<Row> categoryDataset = getCategoryDataset();
        final Dataset<Row> joined = dataset.join(categoryDataset, dataset.col("CATEGORY_ID").equalTo(categoryDataset.col("ID")));
        joined.show();
        return joined.map((MapFunction<Row, SparkInvoiceImpl>) MAPPER::mapRow, Encoders.javaSerialization(SparkInvoiceImpl.class));
    }

    @Nonnull
    private Dataset<Row> createDbRows(@Nonnull List<? extends Invoice> invoices) {
        final List<Row> newRows = invoices.stream()
                .map(MAPPER::mapRow)
                .collect(Collectors.toList());

        final Dataset<Row> dataFrame = sparkSession.createDataFrame(newRows, MAPPER.getDbStructure());
        log.debug("New DB rows created");
        dataFrame.show();
        return dataFrame;
    }

    private void updateDatabase(@Nonnull Dataset<Row> dataset) {
        log.debug("Updating database");
        dataset.show();
        dataset
                .write()
                .mode(SaveMode.Overwrite)
                .jdbc(jdbcConnectionUrl, TABLE_NAME, jdbcConnectionProperties);
        dataset.show();
    }

    private class InvoiceRowMapper implements Serializable {
        @Nonnull
        SparkInvoiceImpl mapRow(@Nonnull Row row) {
            final SparkInvoiceImpl invoice = new SparkInvoiceImpl();

            // renamed field
            invoice.setId(row.getInt(row.fieldIndex("INVOICE_ID")));
            // renamed field
            invoice.setName(row.getString(row.fieldIndex("INVOICE_NAME")));
            // mapping category
            invoice.setCategory(SparkDbCstCategoryRepositoryImpl.MAPPER.mapRow(row));

            invoice.setDetails(row.getString(row.fieldIndex("DETAILS")));
            invoice.setCreationDate(row.getDate(row.fieldIndex("CREATION")).toLocalDate());
            invoice.setModificationDate(row.getDate(row.fieldIndex("LAST_MODIFICATION")).toLocalDate());

            log.debug("Loaded invoice: {}", invoice);
            return invoice;
        }

        @Nonnull
        Row mapRow(@Nonnull Invoice invoice) {
            return RowFactory.create(
                    invoice.getId(),
                    invoice.getName(),
                    invoice.getCategory().getId(),
                    invoice.getDetails(),
                    Date.valueOf(invoice.getCreationDate()),
                    Date.valueOf(invoice.getModificationDate())
            );
        }

        @Nonnull
        StructType getDbStructure() {
            return DataTypes.createStructType(Lists.newArrayList(
                    DataTypes.createStructField("ID", DataTypes.IntegerType, false),
                    DataTypes.createStructField("NAME", DataTypes.StringType, false),
                    DataTypes.createStructField("CATEGORY_ID", DataTypes.IntegerType, false),
                    DataTypes.createStructField("DETAILS", DataTypes.StringType, false),
                    DataTypes.createStructField("CREATION", DataTypes.DateType, false),
                    DataTypes.createStructField("LAST_MODIFICATION", DataTypes.DateType, false)
            ));
        }

        Object[] ids(@Nonnull final List<? extends Invoice> invoices) {
            return invoices.stream().map(Invoice::getId).distinct().toArray();
        }
    }
}
