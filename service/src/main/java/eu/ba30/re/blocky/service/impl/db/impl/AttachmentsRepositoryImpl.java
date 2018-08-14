package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.impl.mapper.AttachmentMapper;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class AttachmentsRepositoryImpl implements AttachmentsRepository {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList(final int invoiceId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final AttachmentMapper mapper = session.getMapper(AttachmentMapper.class);
            return Validate.validateResult(mapper.getAttachmentsByInvoiceId(invoiceId));
        }
    }

    @Override
    public void createAttachments(final int invoiceId, @Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);
        attachments.forEach(item ->
                Validate.notNull(item.getId(), item.getAttachmentType(), item.getContent()));

        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final AttachmentMapper mapper = session.getMapper(AttachmentMapper.class);
            final int rowsAffected = mapper.createAttachmentsForInvoiceId(invoiceId, attachments);
            Validate.equals(rowsAffected, attachments.size(), "Rows count does not match!");
        }
    }

    @Override
    public void removeAttachments(@Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final AttachmentMapper mapper = session.getMapper(AttachmentMapper.class);
            final int rowsAffected = mapper.removeAttachments(attachments);
            Validate.equals(rowsAffected, attachments.size(), "Rows count does not match!");
        }
    }

    @Override
    public int getNextItemId() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final AttachmentMapper mapper = session.getMapper(AttachmentMapper.class);
            return mapper.getNextId();
        }
    }

    @Nonnull
    @Override
    public List<Attachment> getAllAttachments() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final AttachmentMapper mapper = session.getMapper(AttachmentMapper.class);
            return Validate.validateResult(mapper.getAllAttachments());
        }
    }
}
