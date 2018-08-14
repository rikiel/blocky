package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.impl.db.InvoiceRepository;
import eu.ba30.re.blocky.service.impl.db.impl.mapper.InvoiceCategoryHandler;
import eu.ba30.re.blocky.service.impl.db.impl.mapper.InvoiceMapper;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class InvoiceRepositoryImpl implements InvoiceRepository {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private InvoiceCategoryHandler invoiceCategoryHandler;

    @PostConstruct
    private void init() {
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().register(invoiceCategoryHandler);
        sqlSessionFactory.getConfiguration().addMapper(InvoiceMapper.class);
    }

    @Nonnull
    @Override
    public List<Invoice> getInvoices() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final InvoiceMapper mapper = session.getMapper(InvoiceMapper.class);
            return Validate.validateResult(mapper.getAllInvoices());
        }
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final InvoiceMapper mapper = session.getMapper(InvoiceMapper.class);
            final int rowsAffected = mapper.remove(invoices);
            Validate.equals(rowsAffected, invoices.size(), "Rows count does not match!");
        }
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId(), invoice.getName(), invoice.getCreationDate());

        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final InvoiceMapper mapper = session.getMapper(InvoiceMapper.class);
            final int rowsAffected = mapper.create(invoice);
            Validate.equals(rowsAffected, 1, "Rows count does not match!");
        }
    }

    @Override
    public int getNextItemId() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final InvoiceMapper mapper = session.getMapper(InvoiceMapper.class);
            return mapper.getNextId();
        }
    }
}
