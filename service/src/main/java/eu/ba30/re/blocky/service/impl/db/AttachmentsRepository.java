package eu.ba30.re.blocky.service.impl.db;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Attachment;

public interface AttachmentsRepository {
    /**
     * @param invoiceId id of invoice that attachments should be searched for
     * @return all attachments for invoice
     */
    @Nonnull
    List<Attachment> getAttachmentList(int invoiceId);

    /**
     * @param invoiceId   id of invoice that attachment is created for
     * @param attachments list of attachments to be created
     */
    void createAttachments(int invoiceId, @Nonnull List<Attachment> attachments);

    /**
     * @param attachments list of attachments to be removed
     */
    void removeAttachments(@Nonnull List<Attachment> attachments);

    /**
     * @return next id that should be used as attachmentId in DB
     */
    int getNextItemId();

    /**
     * @return all attachments stored in DB
     */
    @Nonnull
    List<Attachment> getAllAttachments();
}
