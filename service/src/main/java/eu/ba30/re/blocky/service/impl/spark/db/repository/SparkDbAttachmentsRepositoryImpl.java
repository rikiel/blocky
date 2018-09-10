package eu.ba30.re.blocky.service.impl.spark.db.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.impl.spark.SparkAttachmentImpl;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbTransactionManager;
import eu.ba30.re.blocky.service.impl.spark.mapper.SparkAttachmentMapper;

@Service
public class SparkDbAttachmentsRepositoryImpl implements AttachmentsRepository, Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkDbAttachmentsRepositoryImpl.class);

    private static final String TABLE_NAME = "T_ATTACHMENTS";

    @Autowired
    private SparkDbTransactionManager transactionManager;

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private String jdbcConnectionUrl;
    @Autowired
    private Properties jdbcConnectionProperties;

    @Autowired
    private SparkAttachmentMapper attachmentMapper;

    private int nextId = 10;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentsByInvoiceId(int invoiceId) {
        return Lists.newArrayList(
                map(getActualDataset().where(new Column("INVOICE_ID").equalTo(invoiceId)))
                        .collectAsList());
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList() {
        return Lists.newArrayList(
                map(getActualDataset()).collectAsList());
    }

    @Override
    public void createAttachmentsForInvoice(int invoiceId, @Nonnull List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        transactionManager.newTransaction(
                new SparkDbTransactionManager.Transaction() {
                    boolean wasInserted = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> actualRows = getActualAttachmentsFromDb(attachments);
                        Validate.equals(actualRows.count(),
                                0,
                                String.format("Should not exist any attachment that is being created. Found %s", actualRows.count()));

                        updateDatabase(createDbRows(map(getActualDataset()).collectAsList(), false, null).union(createDbRows(attachments, true, invoiceId)));
                        wasInserted = true;
                    }

                    @Override
                    public void onRollback() {
                        if (wasInserted) {
                            updateDatabase(getActualDataset().except(
                                    getActualDataset().where(new Column("ID").isin(attachmentMapper.ids(attachments)))));
                        }
                    }
                });
    }

    @Override
    public void removeAttachments(@Nonnull List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        transactionManager.newTransaction(
                new SparkDbTransactionManager.Transaction() {
                    final List<SparkAttachmentImpl> actualDatabaseSnapshot = map(getActualDataset()).collectAsList();
                    boolean wasRemoved = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> toRemove = getActualAttachmentsFromDb(attachments);
                        Validate.equals(toRemove.count(), attachments.size(),
                                String.format("Record count does not match for removing. Actual %s, expected %s", toRemove.count(), attachments.size()));

                        updateDatabase(createDbRows(actualDatabaseSnapshot, false, null).except(createDbRows(map(toRemove).collectAsList(), false, null)));
                        wasRemoved = true;
                    }

                    @Override
                    public void onRollback() {
                        if (wasRemoved) {
                            updateDatabase(createDbRows(actualDatabaseSnapshot, false, null).union(createDbRows(attachments, false, null)));
                        }
                    }
                });
    }

    @Override
    public int getNextAttachmentId() {
        return nextId++;
    }

    @Nonnull
    private Dataset<Row> getActualAttachmentsFromDb(@Nonnull final List<Attachment> attachments) {
        return getActualDataset().where(new Column("ID").isin(attachmentMapper.ids(attachments)));
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        final Dataset<Row> dataset = sparkSession.createDataFrame(sparkSession
                        .read()
                        .jdbc(jdbcConnectionUrl, TABLE_NAME, jdbcConnectionProperties).rdd(),
                attachmentMapper.getDbStructure());
        dataset.show();
        return dataset;
    }

    @Nonnull
    private Dataset<SparkAttachmentImpl> map(Dataset<Row> dataset) {
        return dataset.map((MapFunction<Row, SparkAttachmentImpl>) attachmentMapper::mapRow, Encoders.bean(SparkAttachmentImpl.class));
    }

    @Nonnull
    private Dataset<Row> createDbRows(@Nonnull List<? extends Attachment> attachments, boolean addId, Integer invoiceId) {
        final List<Row> newRows = attachments.stream()
                .map(attachment -> (SparkAttachmentImpl) attachment)
                .peek(attachment -> {
                    if (addId) {
                        attachment.setInvoiceId(invoiceId);
                    }
                })
                .map(attachmentMapper::mapRow)
                .collect(Collectors.toList());

        final Dataset<Row> dataFrame = sparkSession.createDataFrame(newRows, attachmentMapper.getDbStructure());
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

}
