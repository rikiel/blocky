package eu.ba30.re.blocky.service.impl.hibernate;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class HibernateInvoiceServiceImpl implements InvoiceService {
    @PersistenceContext
    private EntityManager entityManager;

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public List<Invoice> getInvoices() {
        final CriteriaQuery<Invoice> query = entityManager.getCriteriaBuilder().createQuery(Invoice.class);
        query.select(query.from(Invoice.class));
        return Validate.validateResult(entityManager.createQuery(query).getResultList());
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachments() {
        final CriteriaQuery<Attachment> query = entityManager.getCriteriaBuilder().createQuery(Attachment.class);
        query.select(query.from(Attachment.class));
        return Validate.validateResult(entityManager.createQuery(query).getResultList());
    }

    @Transactional
    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        invoices.forEach(invoice -> {
            Validate.notNull(invoice.getId());
            entityManager.remove(invoice);
        });
    }

    @Transactional
    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.isNull(invoice.getId(), "ID should NOT BE filled on new instances!");

        entityManager.persist(invoice);
    }

    @Nonnull
    @Transactional
    @Override
    public Invoice update(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId(), "ID should BE filled for update instances!");

        for (Attachment attachment : invoice.getAttachments()) {
            attachment.setInvoice(invoice);
        }
        return Validate.validateResult(entityManager.merge(invoice));
    }
}
