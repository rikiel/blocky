package eu.ba30.re.blocky.service;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Invoice;

public interface InvoiceService {
    /**
     * @return all invoices stored in DB
     */
    @Nonnull
    List<Invoice> getInvoices();

    /**
     * @param invoices invoice to be removed
     */
    void remove(@Nonnull List<Invoice> invoices);

    /**
     * @param invoice invoice be created
     */
    void create(@Nonnull Invoice invoice);

    /**
     * @param invoice invoice to be updated in DB
     */
    void update(@Nonnull Invoice invoice);
}
