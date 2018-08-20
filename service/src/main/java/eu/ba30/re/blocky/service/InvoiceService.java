package eu.ba30.re.blocky.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.transaction.annotation.Transactional;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.utils.Validate;

public interface InvoiceService {
    /**
     * @return all invoices stored in DB
     */
    @Nonnull
    List<Invoice> getInvoices();

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

    /**
     * @param id ID of invoice to be found
     * @return Invoice
     */
    @Nonnull
    @Transactional(readOnly = true)
    default Invoice getInvoice(final int id) {
        final List<Invoice> invoices = getInvoices().stream()
                .filter(invoice -> Objects.equals(invoice.getId(), id))
                .collect(Collectors.toList());
        Validate.equals(invoices.size(), 1, String.format("Expecting 1 invoice with id=%s. Found %s", id, invoices));
        return Validate.validateResult(invoices.get(0));
    }
}
