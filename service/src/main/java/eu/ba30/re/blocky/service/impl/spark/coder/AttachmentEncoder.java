package eu.ba30.re.blocky.service.impl.spark.coder;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.spark.model.AttachmentDb;

@Service
public class AttachmentEncoder {
    @Nonnull
    public List<AttachmentDb> encodeAll(int invoiceId, @Nonnull final List<Attachment> records) {
        return records.stream().map(attachment -> encodeToDb(invoiceId, attachment)).collect(Collectors.toList());
    }

    @Nonnull
    private AttachmentDb encodeToDb(int invoiceId, @Nonnull final Attachment attachment) {
        Validate.notNull(attachment);
        final AttachmentDb result = new AttachmentDb();
        result.setId(attachment.getId());
        result.setName(attachment.getName());
        result.setFileName(attachment.getFileName());
        result.setMimeType(attachment.getMimeType());
        result.setAttachmentTypeId(attachment.getAttachmentType().getId());
        result.setContent(attachment.getContent());
        result.setInvoiceId(invoiceId);
        result.validate();
        return result;
    }
}
