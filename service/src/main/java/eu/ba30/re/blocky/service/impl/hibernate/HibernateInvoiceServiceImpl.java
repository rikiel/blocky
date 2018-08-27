package eu.ba30.re.blocky.service.impl.hibernate;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.impl.hibernate.HibernateAttachmentImpl;
import eu.ba30.re.blocky.model.impl.hibernate.HibernateInvoiceImpl;
import eu.ba30.re.blocky.service.InvoiceService;

@Service
public class HibernateInvoiceServiceImpl implements InvoiceService {
    @PersistenceContext
    private EntityManager entityManager;

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public List<Invoice> getInvoiceList() {
        final CriteriaQuery<HibernateInvoiceImpl> query = entityManager.getCriteriaBuilder().createQuery(HibernateInvoiceImpl.class);
        query.select(query.from(HibernateInvoiceImpl.class));
        return Lists.newArrayList(Validate.validateResult(entityManager.createQuery(query).getResultList()));
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList() {
        final CriteriaQuery<HibernateAttachmentImpl> query = entityManager.getCriteriaBuilder().createQuery(HibernateAttachmentImpl.class);
        query.select(query.from(HibernateAttachmentImpl.class));
        return Lists.newArrayList(Validate.validateResult(entityManager.createQuery(query).getResultList()));
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
