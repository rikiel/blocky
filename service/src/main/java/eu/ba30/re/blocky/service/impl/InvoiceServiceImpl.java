package eu.ba30.re.blocky.service.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.db.InvoiceRepository;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Nonnull
    @Override
    public List<Invoice> getInvoices() {
        return invoiceRepository.getInvoices();
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);
        invoiceRepository.remove(invoices);
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);

        // TODO BLOCKY-7 domapovat fieldy
        invoiceRepository.create(invoice);
    }

    @Override
    public void update(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);

        // TODO BLOCKY-7 domapovat fieldy
        invoiceRepository.remove(Lists.newArrayList(invoice));
        invoiceRepository.create(invoice);
    }
}
