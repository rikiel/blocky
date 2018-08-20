package eu.ba30.re.blocky.service;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;

public interface InvoiceService {
    /**
     * @return all invoices stored in DB
     */
    @Nonnull
    List<Invoice> getInvoices();

    /**
     * @param id ID of invoice to be found
     * @return Invoice
     */
    @Nonnull
    Invoice getInvoice(final int id);

    /**
     * @return all attachments stored in DB
     */
    @Nonnull
    List<Attachment> getAttachments();

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
     * @return Updated invoice. Argument object should not be used anymore.
     */
    @Nonnull
    Invoice update(@Nonnull Invoice invoice);
}
