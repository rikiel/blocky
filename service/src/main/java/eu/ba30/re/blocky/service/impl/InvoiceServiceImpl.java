package eu.ba30.re.blocky.service.impl;

import com.google.common.collect.Lists;
import eu.ba30.re.blocky.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    // TODO BLOCKY-4 Akcie: Pridat novy/upravit/zmazat
    private List<Invoice> invoiceList;

    @PostConstruct
    private void init() {
        invoiceList = Lists.newArrayList();
        for (int i = 0; i < 10; ++i) {
            final Invoice item = new Invoice();
            item.setId(i);
            item.setName("itemName#" + i);
            item.setCategory("itemCategory#" + i);
            item.setCreationDate(LocalDate.now().minusMonths(1));
            invoiceList.add(item);
        }
    }

    @Nonnull
    @Override
    public List<Invoice> getInvoices() {
        return invoiceList;
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Objects.requireNonNull(invoices);

        invoiceList.removeAll(invoices);
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Objects.requireNonNull(invoice);

        invoiceList.add(invoice);
    }
}
