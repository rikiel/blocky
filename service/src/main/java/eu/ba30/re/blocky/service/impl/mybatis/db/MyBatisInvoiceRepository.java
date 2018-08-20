package eu.ba30.re.blocky.service.impl.mybatis.db;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Invoice;

public interface MyBatisInvoiceRepository {
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
     * @return next id that should be used as invoiceId in DB
     */
    int getNextItemId();
}
