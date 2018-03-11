package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;

@Service
public class AttachmentRepositoryImpl implements AttachmentsRepository {
    @Nonnull
    @Override
    public List<Attachment> getAttachmentList(int invoiceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createAttachments(int invoiceId, @Nonnull List<Attachment> attachments) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeAttachments(@Nonnull List<Integer> invoiceIds) {
        throw new UnsupportedOperationException();

    }
}
