package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.impl.mapper.AttachmentMapper;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class AttachmentsRepositoryImpl implements AttachmentsRepository {
    @Autowired
    private AttachmentMapper attachmentMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList(final int invoiceId) {
        return Validate.validateResult(attachmentMapper.getAttachmentsByInvoiceId(invoiceId));

        //        return Validate.validateResult(entityManager
        //                .createQuery("SELECT a FROM Attachment a WHERE a.INVOICE_ID = :invoiceId")
        //                .setParameter("invoiceId", invoiceId)
        //                .getResultList());

        //        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //        final CriteriaQuery<Attachment> query = criteriaBuilder.createQuery(Attachment.class);
        //        final Root<Attachment> queryRoot = query.from(Attachment.class);
        //        query.select(queryRoot);
        //        query.where(criteriaBuilder.equal(queryRoot.get("INVOICE_ID"), invoiceId));
        //        return Validate.validateResult(entityManager.createQuery(query).getResultList());
    }

    @Override
    public void createAttachments(final int invoiceId, @Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);
        attachments.forEach(item ->
                Validate.notNull(item.getId(), item.getAttachmentType(), item.getContent()));

        final int rowsAffected = attachmentMapper.createAttachmentsForInvoiceId(invoiceId, attachments);
        Validate.equals(rowsAffected, attachments.size(), "Rows count does not match!");
    }

    @Override
    public void removeAttachments(@Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        final int rowsAffected = attachmentMapper.removeAttachments(attachments);
        Validate.equals(rowsAffected, attachments.size(), "Rows count does not match!");
    }

    @Override
    public int getNextItemId() {
        return attachmentMapper.getNextId();
    }

    @Nonnull
    @Override
    public List<Attachment> getAllAttachments() {
        return Validate.validateResult(attachmentMapper.getAllAttachments());
    }
}
