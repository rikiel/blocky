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

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentDecoder;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentEncoder;
import eu.ba30.re.blocky.service.impl.spark.model.AttachmentDb;

import static org.apache.spark.sql.functions.max;

@Service
public class SparkAttachmentsRepositoryImpl implements AttachmentsRepository {
    private static final int ID_STARTS = 10;
    private static final String TABLE_NAME = "T_ATTACHMENTS";

    private int nextId;

    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private AttachmentEncoder attachmentEncoder;
    @Autowired
    private AttachmentDecoder attachmentDecoder;

    private Dataset<AttachmentDb> attachmentDataset;

    @PostConstruct
    private void init() {
        updateDataset(sparkSession
                .sql("SELECT * FROM global_temp." + TABLE_NAME)
                .as(Encoders.bean(AttachmentDb.class)));

        final int maxId = attachmentDataset.agg(max("ID")).head().getInt(0);
        nextId = maxId > ID_STARTS ? maxId + 1 : ID_STARTS;
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentsByInvoiceId(int invoiceId) {
        return attachmentDecoder.decodeAll(
                attachmentDataset
                        .where(new Column("invoiceId").equalTo(invoiceId))
                        .collectAsList());
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList() {
        return attachmentDecoder.decodeAll(
                attachmentDataset.collectAsList());
    }

    @Override
    public void createAttachmentsForInvoice(int invoiceId, @Nonnull List<Attachment> attachments) {
        Validate.notEmpty(attachments);
        // TODO BLOCKY-16 odstranit INVOICE_ID z attachmentu
        attachments.forEach(attachment -> attachment.setInvoiceId(invoiceId));

        final Dataset<AttachmentDb> actualRows = getActualAttachmentsFromDb(attachments);
        Validate.equals(actualRows.count(), 0, String.format("Should not exist any attachment that is being created. Found %s", actualRows.count()));

        final Dataset<AttachmentDb> newRows = sparkSession.createDataset(attachmentEncoder.encodeAll(attachments), Encoders.bean(AttachmentDb.class));

        updateDataset(attachmentDataset.union(newRows));
    }

    @Override
    public void removeAttachments(@Nonnull List<Attachment> attachments) {
        final Dataset<AttachmentDb> toRemove = getActualAttachmentsFromDb(attachments);
        Validate.equals(toRemove.count(), attachments.size(),
                String.format("Record count does not match for removing. Actual %s, expected %s", toRemove.count(), attachments.size()));
        final Dataset<AttachmentDb> rowsAfterRemove = attachmentDataset.except(toRemove);

        updateDataset(rowsAfterRemove);
    }

    @Override
    public int getNextAttachmentId() {
        return nextId++;
    }

    private void updateDataset(@Nonnull final Dataset<AttachmentDb> attachmentDataset) {
        Validate.notNull(attachmentDataset);
        attachmentDataset.createOrReplaceGlobalTempView(TABLE_NAME);
        this.attachmentDataset = attachmentDataset;
    }

    @Nonnull
    private Dataset<AttachmentDb> getActualAttachmentsFromDb(@Nonnull final List<Attachment> attachments) {
        final Object[] ids = attachments.stream().map(Attachment::getId).toArray();
        return attachmentDataset.where(new Column("ID").isin(ids));
    }
}
