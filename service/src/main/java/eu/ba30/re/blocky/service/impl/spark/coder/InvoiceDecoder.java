package eu.ba30.re.blocky.service.impl.spark.coder;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.other.InvoiceImpl;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.spark.model.InvoiceDb;

@Service
public class InvoiceDecoder {
    @Autowired
    private CstManager cstManager;

    @Nonnull
    public List<Invoice> decodeAll(@Nonnull final List<InvoiceDb> records) {
        return records.stream().map(this::decodeFromDb).collect(Collectors.toList());
    }

    @Nonnull
    private Invoice decodeFromDb(@Nonnull final InvoiceDb invoice) {
        Validate.notNull(invoice);
        invoice.validate();
        final Invoice result = new InvoiceImpl();
        result.setId(invoice.getId());
        result.setName(invoice.getName());
        result.setCategory(cstManager.getCategoryById(invoice.getCategoryId()));
        result.setDetails(invoice.getDetails());
        result.setCreationDate(invoice.getCreationDate().toLocalDate());
        result.setModificationDate(invoice.getModificationDate().toLocalDate());
        return result;
    }
}
