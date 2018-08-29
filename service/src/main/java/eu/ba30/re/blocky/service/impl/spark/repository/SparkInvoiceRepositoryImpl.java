package eu.ba30.re.blocky.service.impl.spark.repository;

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
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceDecoder;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceEncoder;
import eu.ba30.re.blocky.service.impl.spark.model.InvoiceDb;

import static org.apache.spark.sql.functions.max;

@Service
public class SparkInvoiceRepositoryImpl implements InvoiceRepository {
    private static final int ID_STARTS = 10;
    private static final String TABLE_NAME = "T_INVOICES";

    private int nextId;

    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private InvoiceEncoder invoiceEncoder;
    @Autowired
    private InvoiceDecoder invoiceDecoder;

    private Dataset<InvoiceDb> invoiceDataset;

    @PostConstruct
    private void init() {
        updateDataset(sparkSession
                .sql("SELECT * FROM global_temp." + TABLE_NAME)
                .as(Encoders.bean(InvoiceDb.class)));

        final int maxId = invoiceDataset.agg(max("ID")).head().getInt(0);
        nextId = maxId > ID_STARTS ? maxId + 1 : ID_STARTS;
    }

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        return invoiceDecoder.decodeAll(invoiceDataset.collectAsList());
    }

    @Override
    public void remove(@Nonnull List<Invoice> invoices) {
        final Dataset<InvoiceDb> toRemove = getActualInvoicesFromDb(invoices);
        Validate.equals(toRemove.count(), invoices.size(),
                String.format("Record count does not match for removing. Actual %s, expected %s", toRemove.count(), invoices.size()));
        final Dataset<InvoiceDb> rowsAfterRemove = invoiceDataset.except(toRemove);

        updateDataset(rowsAfterRemove);
    }

    @Override
    public void create(@Nonnull Invoice invoice) {
        Validate.notNull(invoice);

        final Dataset<InvoiceDb> actualRows = getActualInvoicesFromDb(Lists.newArrayList(invoice));
        Validate.equals(actualRows.count(), 0, String.format("Should not exist any invoice that is being created. Found %s", actualRows.count()));

        final Dataset<InvoiceDb> newRows = sparkSession.createDataset(invoiceEncoder.encodeAll(Lists.newArrayList(invoice)), Encoders.bean(InvoiceDb.class));

        updateDataset(invoiceDataset.union(newRows));
    }

    @Override
    public int getNextInvoiceId() {
        return nextId++;
    }

    private void updateDataset(@Nonnull final Dataset<InvoiceDb> invoiceDataset) {
        Validate.notNull(invoiceDataset);
        invoiceDataset.createOrReplaceGlobalTempView(TABLE_NAME);
        this.invoiceDataset = invoiceDataset;
    }

    @Nonnull
    private Dataset<InvoiceDb> getActualInvoicesFromDb(@Nonnull final List<Invoice> invoices) {
        final Object[] ids = invoices.stream().map(Invoice::getId).toArray();
        return invoiceDataset.where(new Column("ID").isin(ids));
    }
}
