package eu.ba30.re.blocky.service.impl.spark.mapper;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.spark.SparkInvoiceImpl;

@Service
public class SparkInvoiceMapper implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkInvoiceMapper.class);

    @Autowired
    private SparkCategoryMapper categoryMapper;

    @Nonnull
    public SparkInvoiceImpl mapRow(@Nonnull Row row) {
        final SparkInvoiceImpl invoice = new SparkInvoiceImpl();

        // renamed field
        invoice.setId(row.getInt(row.fieldIndex("INVOICE_ID")));
        // renamed field
        invoice.setName(row.getString(row.fieldIndex("INVOICE_NAME")));
        // mapping category
        invoice.setCategory(categoryMapper.mapRow(row));

        invoice.setDetails(row.getString(row.fieldIndex("DETAILS")));
        invoice.setCreationDate(row.getDate(row.fieldIndex("CREATION")).toLocalDate());
        invoice.setModificationDate(row.getDate(row.fieldIndex("LAST_MODIFICATION")).toLocalDate());

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
    public StructType getDbStructure() {
        return DataTypes.createStructType(Lists.newArrayList(
                DataTypes.createStructField("ID", DataTypes.IntegerType, false),
                DataTypes.createStructField("NAME", DataTypes.StringType, false),
                DataTypes.createStructField("CATEGORY_ID", DataTypes.IntegerType, false),
                DataTypes.createStructField("DETAILS", DataTypes.StringType, false),
                DataTypes.createStructField("CREATION", DataTypes.DateType, false),
                DataTypes.createStructField("LAST_MODIFICATION", DataTypes.DateType, false)
        ));
    }

    public Object[] ids(@Nonnull final List<? extends Invoice> invoices) {
        return invoices.stream().map(Invoice::getId).distinct().toArray();
    }
}
