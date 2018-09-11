package eu.ba30.re.blocky.service.impl.spark.csv.repository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.spark.SparkInvoiceImpl;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;
import eu.ba30.re.blocky.service.impl.spark.common.SparkTransactionManager;
import eu.ba30.re.blocky.service.impl.spark.common.SparkUtils;
import eu.ba30.re.blocky.service.impl.spark.common.mapper.SparkCategoryMapper;
import eu.ba30.re.blocky.service.impl.spark.common.mapper.SparkInvoiceMapper;

@Service
public class SparkCsvInvoiceRepositoryImpl implements InvoiceRepository, Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkCsvInvoiceRepositoryImpl.class);

    private int nextId = 10;

    @Autowired
    private SparkTransactionManager transactionManager;
    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private String invoiceCsvFileName;
    @Autowired
    private String categoryCsvFileName;
    @Autowired
    private SparkInvoiceMapper invoiceMapper;
    @Autowired
    private SparkCategoryMapper categoryMapper;

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        return Lists.newArrayList(invoiceMapper.map(getActualDataset()).collectAsList());
    }

    @Override
    public void remove(@Nonnull List<Invoice> invoices) {
        transactionManager.newTransaction(
                new SparkTransactionManager.Transaction() {
                    final List<SparkInvoiceImpl> actualDatabaseSnapshot = invoiceMapper.map(getActualDataset()).collectAsList();
                    boolean wasRemoved = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> toRemove = getActualInvoicesFromDb(invoices);
                        Validate.equals(toRemove.count(), invoices.size(),
                                String.format("Record count does not match for removing. Actual %s, expected %s", toRemove.count(), invoices.size()));

                        updateDatabase(createDbRows(actualDatabaseSnapshot).except(createDbRows(invoiceMapper.map(toRemove).collectAsList())));
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
                new SparkTransactionManager.Transaction() {
                    final List<Invoice> invoices = Lists.newArrayList(invoice);
                    boolean wasInserted = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> actualRows = getActualInvoicesFromDb(invoices);
                        Validate.equals(actualRows.count(),
                                0,
                                String.format("Should not exist any invoice that is being created. Found %s", actualRows.count()));

                        final Dataset<Row> insertedData = createDbRows(invoices);
                        updateDatabase(createDbRows(invoiceMapper.map(getActualDataset()).collectAsList()).union(insertedData));
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
        return getActualDataset().where(SparkUtils.column(SparkInvoiceMapper.Columns.ID).isin(SparkUtils.getIds(invoices)));
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        final Dataset<Row> invoiceDataset = SparkUtils.loadCsv(sparkSession, invoiceMapper.getDbStructure(), invoiceCsvFileName, null);

        final Dataset<Row> categoryDataset = SparkUtils.loadCsv(sparkSession, categoryMapper.getDbStructure(), categoryCsvFileName, null);

        return SparkUtils.join(invoiceDataset,
                categoryDataset,
                SparkInvoiceMapper.Columns.CATEGORY,
                SparkCategoryMapper.Columns.ID,
                SparkInvoiceMapper.Columns.ID);
    }

    @Nonnull
    private Dataset<Row> createDbRows(@Nonnull List<? extends Invoice> invoices) {
        final List<Row> newRows = invoices.stream()
                .map(invoiceMapper::mapRow)
                .collect(Collectors.toList());

        final Dataset<Row> dataFrame = sparkSession.createDataFrame(newRows, invoiceMapper.getDbStructure());
        log.debug("New DB rows created");
        dataFrame.show();
        return dataFrame;
    }

    private void updateDatabase(@Nonnull Dataset<Row> dataset) {
        SparkUtils.saveCsv(sparkSession, dataset, invoiceCsvFileName);
    }
}
