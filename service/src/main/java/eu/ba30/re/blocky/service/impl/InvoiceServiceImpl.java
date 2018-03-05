package eu.ba30.re.blocky.service.impl;

import com.google.common.collect.Lists;
import eu.ba30.re.blocky.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Nonnull
    @Override
    public List<Invoice> getInvoices() {
        // TODO BLOCKY-3 Detaily poloziek
        final Invoice item = new Invoice();
        item.setName("itemName");
        item.setCategory("itemCategory");
        item.setCreationDate(LocalDate.now().minusMonths(1));
        return Lists.newArrayList(item);
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Objects.requireNonNull(invoices);

        throw new UnsupportedOperationException();
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Objects.requireNonNull(invoice);

        throw new UnsupportedOperationException();
    }
}
