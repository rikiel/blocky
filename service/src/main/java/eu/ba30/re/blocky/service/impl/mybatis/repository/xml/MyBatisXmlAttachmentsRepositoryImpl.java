package eu.ba30.re.blocky.service.impl.mybatis.repository.xml;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper.MyBatisXmlAttachmentMapper;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;

@Service
public class MyBatisXmlAttachmentsRepositoryImpl implements AttachmentsRepository {
    @Autowired
    private MyBatisXmlAttachmentMapper attachmentMapper;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentsByInvoiceId(final int invoiceId) {
        return Lists.newArrayList(Validate.validateResult(attachmentMapper.getAttachmentsByInvoiceId(invoiceId)));
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
    public int getNextAttachmentId() {
        return attachmentMapper.getNextAttachmentId();
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList() {
        return Lists.newArrayList(Validate.validateResult(attachmentMapper.getAttachmentList()));
    }
}
