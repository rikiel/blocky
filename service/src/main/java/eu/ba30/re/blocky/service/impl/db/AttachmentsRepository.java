package eu.ba30.re.blocky.service.impl.db;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Attachment;

public interface AttachmentsRepository {
    @Nonnull
    List<Attachment> getAttachmentList(int invoiceId);

    void createAttachments(int invoiceId, @Nonnull List<Attachment> attachments);

    void removeAttachments(@Nonnull List<Attachment> attachments);

    int getNextItemId();
}
