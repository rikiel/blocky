package eu.ba30.re.blocky.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;

public interface InvoiceService {
    /**
     * @return all invoices stored in DB
     */
    @Nonnull
    List<Invoice> getInvoiceList();

    /**
     * @return all attachments stored in DB
     */
    @Nonnull
    List<Attachment> getAttachmentList();

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
    default Invoice getInvoiceById(final int id) {
        final List<Invoice> invoices = getInvoiceList().stream()
                .filter(invoice -> Objects.equals(invoice.getId(), id))
                .collect(Collectors.toList());
        Validate.equals(invoices.size(), 1, String.format("Expecting 1 invoice with id=%s. Found %s", id, invoices));
        return Validate.validateResult(invoices.get(0));
    }
}
