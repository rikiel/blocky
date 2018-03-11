package eu.ba30.re.blocky.service;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Invoice;

public interface InvoiceService {
    @Nonnull
    List<Invoice> getInvoices();

    void remove(@Nonnull List<Invoice> invoices);

    void create(@Nonnull Invoice invoice);

    void update(@Nonnull Invoice invoice);
}
