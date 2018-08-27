package eu.ba30.re.blocky.service.impl.mybatis.repository.xml;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper.MyBatisXmlInvoiceMapper;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;

@Service
public class MyBatisXmlInvoiceRepositoryImpl implements InvoiceRepository {
    @Autowired
    private MyBatisXmlInvoiceMapper invoiceMapper;

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        return Lists.newArrayList(Validate.validateResult(invoiceMapper.getInvoiceList()));
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        final int rowsAffected = invoiceMapper.remove(invoices);
        Validate.equals(rowsAffected, invoices.size(), "Rows count does not match!");
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId(), invoice.getName(), invoice.getCreationDate());

        final int rowsAffected = invoiceMapper.create(invoice);
        Validate.equals(rowsAffected, 1, "Rows count does not match!");
    }

    @Override
    public int getNextInvoiceId() {
        return invoiceMapper.getNextInvoiceId();
    }
}
