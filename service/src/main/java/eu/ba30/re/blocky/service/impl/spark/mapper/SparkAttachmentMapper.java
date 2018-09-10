package eu.ba30.re.blocky.service.impl.spark.mapper;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.impl.spark.SparkAttachmentImpl;

@Service
public class SparkAttachmentMapper implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkAttachmentMapper.class);

    @Nonnull
    public SparkAttachmentImpl mapRow(@Nonnull Row row) {
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
    public Row mapRow(@Nonnull SparkAttachmentImpl attachment) {
        return RowFactory.create(attachment.getId(),
                attachment.getInvoiceId(),
                attachment.getName(),
                attachment.getFileName(),
                attachment.getMimeType(),
                attachment.getAttachmentTypeId(),
                attachment.getContent());
    }

    @Nonnull
    public StructType getDbStructure() {
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

    public Object[] ids(@Nonnull final List<? extends Attachment> attachments) {
        return attachments.stream().map(Attachment::getId).distinct().toArray();
    }
}
