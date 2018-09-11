package eu.ba30.re.blocky.service.impl.spark.common.mapper;

import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Nonnull;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.spark.SparkInvoiceImpl;
import eu.ba30.re.blocky.service.impl.spark.common.SparkUtils;

@Service
public class SparkInvoiceMapper implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkInvoiceMapper.class);

    public static final String TABLE_NAME = "T_INVOICES";

    @Autowired
    private SparkCategoryMapper categoryMapper;

    @Nonnull
    public SparkInvoiceImpl mapRow(@Nonnull Row row) {
        final SparkInvoiceImpl invoice = new SparkInvoiceImpl();

        invoice.setId(row.getInt(SparkUtils.getColumnIndex(row, Columns.ID)));
        invoice.setName(row.getString(SparkUtils.getColumnIndex(row, Columns.NAME)));
        invoice.setCategory(categoryMapper.mapRow(row));
        invoice.setDetails(row.getString(SparkUtils.getColumnIndex(row, Columns.DETAILS)));
        invoice.setCreationDate(row.getDate(SparkUtils.getColumnIndex(row, Columns.CREATION_DATE)).toLocalDate());
        invoice.setModificationDate(row.getDate(SparkUtils.getColumnIndex(row, Columns.LAST_MODIFICATION_DATE)).toLocalDate());

        log.debug("Loaded invoice: {}", invoice);
        return invoice;
    }

    @Nonnull
    public Row mapRow(@Nonnull Invoice invoice) {
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
    public Dataset<SparkInvoiceImpl> map(@Nonnull Dataset<Row> dataset) {
        return dataset.map((MapFunction<Row, SparkInvoiceImpl>) this::mapRow, Encoders.javaSerialization(SparkInvoiceImpl.class));
    }

    @Nonnull
    public StructType getDbStructure() {
        return new SparkUtils.FieldBuilder()
                .addRequiredField(Columns.ID, DataTypes.IntegerType)
                .addRequiredField(Columns.NAME, DataTypes.StringType)
                .addRequiredField(Columns.CATEGORY, DataTypes.IntegerType)
                .addRequiredField(Columns.DETAILS, DataTypes.StringType)
                .addRequiredField(Columns.CREATION_DATE, DataTypes.DateType)
                .addRequiredField(Columns.LAST_MODIFICATION_DATE, DataTypes.DateType)
                .build();
    }

    public enum Columns implements SparkUtils.TableColumn {
        ID("ID"),
        NAME("NAME"),
        CATEGORY("CATEGORY_ID"),
        DETAILS("DETAILS"),
        CREATION_DATE("CREATION"),
        LAST_MODIFICATION_DATE("LAST_MODIFICATION");

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
