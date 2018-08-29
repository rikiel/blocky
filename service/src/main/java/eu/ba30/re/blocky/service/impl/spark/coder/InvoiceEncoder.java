package eu.ba30.re.blocky.service.impl.spark.coder;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.impl.spark.model.InvoiceDb;

@Service
public class InvoiceEncoder {
    @Nonnull
    public List<InvoiceDb> encodeAll(@Nonnull final List<Invoice> records) {
        return records.stream().map(this::encodeToDb).collect(Collectors.toList());
    }

    @Nonnull
    private InvoiceDb encodeToDb(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        final InvoiceDb result = new InvoiceDb();
        result.setId(invoice.getId());
        result.setName(invoice.getName());
        result.setCategoryId(invoice.getCategory().getId());
        result.setDetails(invoice.getDetails());
        result.setCreationDate(Date.valueOf(invoice.getCreationDate()));
        result.setModificationDate(Date.valueOf(invoice.getModificationDate()));
        result.validate();
        return result;
    }
}
