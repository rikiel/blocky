package eu.ba30.re.blocky.service.impl.spark.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

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
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.impl.spark.SparkAttachmentImpl;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.spark.SparkTransactionManager;

import static org.apache.spark.sql.functions.max;

@Service
public class SparkAttachmentsRepositoryImpl implements AttachmentsRepository, Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkAttachmentsRepositoryImpl.class);
    private static final AttachmentMapper MAPPER = new AttachmentMapper();

    private static final int ID_STARTS = 10;
    private static final String TABLE_NAME = "T_ATTACHMENTS";

    @Autowired
    private SparkTransactionManager transactionManager;

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private String jdbcConnectionUrl;
    @Autowired
    private Properties jdbcConnectionProperties;

    private int nextId;

    @PostConstruct
    private void init() {
        final int maxId = getActualDataset().agg(max("ID")).head().getInt(0);
        nextId = maxId > ID_STARTS ? maxId + 1 : ID_STARTS;
    }

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
                new SparkTransactionManager.Transaction() {
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
                                    getActualDataset().where(new Column("ID").isin(MAPPER.ids(attachments)))));
                        }
                    }
                });
    }

    @Override
    public void removeAttachments(@Nonnull List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        transactionManager.newTransaction(
                new SparkTransactionManager.Transaction() {
                    final List<SparkAttachmentImpl> actualDatabaseSnapshot = map(getActualDataset()).collectAsList();
                    boolean wasRemoved = false;

                    @Override
                    public void onCommit() {
                        final Dataset<Row> toRemove = getActualAttachmentsFromDb(attachments);
                        Validate.equals(toRemove.count(), attachments.size(),
                                String.format("Record count does not match for removing. Actual %s, expected %s", toRemove.count(), attachments.size()));

                        updateDatabase(getActualDataset().except(toRemove));
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
        return getActualDataset().where(new Column("ID").isin(MAPPER.ids(attachments)));
    }

    @Nonnull
    private Dataset<Row> getActualDataset() {
        final Dataset<Row> dataset = sparkSession.createDataFrame(sparkSession
                        .read()
                        .jdbc(jdbcConnectionUrl, TABLE_NAME, jdbcConnectionProperties).rdd(),
                MAPPER.getDbStructure());
        dataset.show();
        return dataset;
    }

    @Nonnull
    private Dataset<SparkAttachmentImpl> map(Dataset<Row> dataset) {
        return dataset.map((MapFunction<Row, SparkAttachmentImpl>) MAPPER::mapRow, Encoders.bean(SparkAttachmentImpl.class));
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
                .map(MAPPER::mapRow)
                .collect(Collectors.toList());

        final Dataset<Row> dataFrame = sparkSession.createDataFrame(newRows, MAPPER.getDbStructure());
        dataFrame.show();
        return dataFrame;
    }

    private void updateDatabase(@Nonnull Dataset<Row> dataset) {
        dataset.show();
        dataset
                .write()
                .mode(SaveMode.Overwrite)
                .jdbc(jdbcConnectionUrl, TABLE_NAME, jdbcConnectionProperties);
    }

    private static class AttachmentMapper implements Serializable {
        @Nonnull
        SparkAttachmentImpl mapRow(@Nonnull Row row) {
            final SparkAttachmentImpl attachment = new SparkAttachmentImpl();

            attachment.setId(row.getInt(row.fieldIndex("ID")));
            attachment.setInvoiceId(row.getInt(row.fieldIndex("INVOICE_ID")));
            attachment.setName(row.getString(row.fieldIndex("NAME")));
            attachment.setFileName(row.getString(row.fieldIndex("FILE_NAME")));
            attachment.setMimeType(row.getString(row.fieldIndex("MIME_TYPE")));
            attachment.setAttachmentType(AttachmentType.forId(row.getInt(row.fieldIndex("TYPE"))));
            attachment.setContent((byte[]) row.get(row.fieldIndex("FILE_CONTENT")));

            log.debug("Loaded attachment: {}", attachment);
            return attachment;
        }

        @Nonnull
        Row mapRow(@Nonnull SparkAttachmentImpl attachment) {
            return RowFactory.create(attachment.getId(),
                    attachment.getInvoiceId(),
                    attachment.getName(),
                    attachment.getFileName(),
                    attachment.getMimeType(),
                    attachment.getAttachmentTypeId(),
                    attachment.getContent());
        }

        @Nonnull
        StructType getDbStructure() {
            return DataTypes.createStructType(Lists.newArrayList(
                    DataTypes.createStructField("ID", DataTypes.IntegerType, false),
                    DataTypes.createStructField("INVOICE_ID", DataTypes.IntegerType, false),
                    DataTypes.createStructField("NAME", DataTypes.StringType, false),
                    DataTypes.createStructField("FILE_NAME", DataTypes.StringType, false),
                    DataTypes.createStructField("MIME_TYPE", DataTypes.StringType, false),
                    DataTypes.createStructField("TYPE", DataTypes.IntegerType, false),
                    DataTypes.createStructField("FILE_CONTENT", DataTypes.BinaryType, false)
            ));
        }

        Object[] ids(@Nonnull final List<? extends Attachment> attachments) {
            return attachments.stream().map(Attachment::getId).distinct().toArray();
        }
    }
}
