package eu.ba30.re.blocky.service.impl.mybatis.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.mybatis.repository.mapper.MyBatisAttachmentMapper;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;

@Service
public class MyBatisAttachmentsRepositoryImpl implements AttachmentsRepository {
    @Autowired
    private MyBatisAttachmentMapper attachmentMapper;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentsByInvoiceId(final int invoiceId) {
        return Validate.validateResult(attachmentMapper.getAttachmentsByInvoiceId(invoiceId));
    }

    @Override
    public void createAttachmentsForInvoice(final int invoiceId, @Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);
        attachments.forEach(item ->
                Validate.notNull(item.getId(), item.getAttachmentType(), item.getContent()));

        final int rowsAffected = attachmentMapper.createAttachmentsForInvoice(invoiceId, attachments);
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
    public List<Attachment> getAttachmentList() {
        return Validate.validateResult(attachmentMapper.getAttachmentList());
    }
}
