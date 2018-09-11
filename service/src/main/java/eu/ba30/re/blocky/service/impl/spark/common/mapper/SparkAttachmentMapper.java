package eu.ba30.re.blocky.service.impl.spark.common.mapper;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.impl.spark.SparkAttachmentImpl;
import eu.ba30.re.blocky.service.impl.spark.common.SparkUtils;

@Service
public class SparkAttachmentMapper implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkAttachmentMapper.class);

    public static final String TABLE_NAME = "T_ATTACHMENTS";

    @Autowired
    private ContentType contentType;

    @Nonnull
    public SparkAttachmentImpl mapRow(@Nonnull Row row) {
        final SparkAttachmentImpl attachment = new SparkAttachmentImpl();

        attachment.setId(row.getInt(SparkUtils.getColumnIndex(row, Columns.ID)));
        attachment.setInvoiceId(row.getInt(SparkUtils.getColumnIndex(row, Columns.INVOICE)));
        attachment.setName(row.getString(SparkUtils.getColumnIndex(row, Columns.NAME)));
        attachment.setFileName(row.getString(SparkUtils.getColumnIndex(row, Columns.FILE_NAME)));
        attachment.setMimeType(row.getString(SparkUtils.getColumnIndex(row, Columns.MIME_TYPE)));
        attachment.setAttachmentType(AttachmentType.forId(row.getInt(SparkUtils.getColumnIndex(row, Columns.ATTACHMENT_TYPE))));
        attachment.setContent(contentType.getContent(row, SparkUtils.getColumnIndex(row, Columns.CONTENT)));

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
                contentType.getContentForDb(attachment.getContent()));
    }

    @Nonnull
    public Dataset<SparkAttachmentImpl> map(@Nonnull Dataset<Row> dataset) {
        return dataset.map((MapFunction<Row, SparkAttachmentImpl>) this::mapRow, Encoders.bean(SparkAttachmentImpl.class));
    }

    @Nonnull
    public StructType getDbStructure() {
        return new SparkUtils.FieldBuilder()
                .addRequiredField(Columns.ID, DataTypes.IntegerType)
                .addRequiredField(Columns.INVOICE, DataTypes.IntegerType)
                .addRequiredField(Columns.NAME, DataTypes.StringType)
                .addRequiredField(Columns.FILE_NAME, DataTypes.StringType)
                .addRequiredField(Columns.MIME_TYPE, DataTypes.StringType)
                .addRequiredField(Columns.ATTACHMENT_TYPE, DataTypes.IntegerType)
                .addRequiredField(Columns.CONTENT, contentType.getDataType())
                .build();
    }

    public enum ContentType {
        STRING {
            @Nonnull
            @Override
            DataType getDataType() {
                return DataTypes.StringType;
            }

            @Nonnull
            @Override
            byte[] getContent(@Nonnull Row row, int column) {
                return row.getString(column).getBytes();
            }

            @Override
            Object getContentForDb(@Nonnull byte[] bytes) {
                return new String(bytes);
            }
        },
        BLOB {
            @Nonnull
            @Override
            DataType getDataType() {
                return DataTypes.BinaryType;
            }

            @Nonnull
            @Override
            byte[] getContent(@Nonnull Row row, int column) {
                return (byte[]) row.get(column);
            }

            @Override
            Object getContentForDb(@Nonnull byte[] bytes) {
                return bytes;
            }
        };

        @Nonnull
        abstract DataType getDataType();

        @Nonnull
        abstract byte[] getContent(@Nonnull Row row, int column);

        abstract Object getContentForDb(@Nonnull byte[] bytes);
    }

    public enum Columns implements SparkUtils.TableColumn {
        ID("ID"),
        INVOICE("INVOICE_ID"),
        NAME("NAME"),
        FILE_NAME("FILE_NAME"),
        MIME_TYPE("MIME_TYPE"),
        ATTACHMENT_TYPE("ATTACHMENT_TYPE"),
        CONTENT("FILE_CONTENT");

        private final String name;

        Columns(String name) {
            this.name = name;
        }

        @Override
        @Nonnull
        public String getFullColumnName() {
            return TABLE_NAME + DELIMITER + name;
        }
    }
}
