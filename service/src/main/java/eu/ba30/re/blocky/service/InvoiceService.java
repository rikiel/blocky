package eu.ba30.re.blocky.service;

import eu.ba30.re.blocky.Invoice;

import javax.annotation.Nonnull;
import java.util.List;

public interface InvoiceService {
    @Nonnull
    List<Invoice> getInvoices();

    void remove(@Nonnull List<Invoice> invoices);

    void create(@Nonnull Invoice invoice);
}
