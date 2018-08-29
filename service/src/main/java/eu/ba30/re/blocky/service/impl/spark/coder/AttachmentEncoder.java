package eu.ba30.re.blocky.service.impl.spark.coder;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;

@Service
public class AttachmentEncoder {
    @Nonnull
    public List<AttachmentDb> encodeAll(@Nonnull final List<Attachment> records) {
        return records.stream().map(this::encodeToDb).collect(Collectors.toList());
    }

    @Nonnull
    private AttachmentDb encodeToDb(@Nonnull final Attachment attachment) {
        Validate.notNull(attachment);
        final AttachmentDb result = new AttachmentDb();
        result.setId(attachment.getId());
        result.setName(attachment.getName());
        result.setFileName(attachment.getFileName());
        result.setMimeType(attachment.getMimeType());
        result.setAttachmentTypeId(attachment.getAttachmentType().getId());
        result.setContent(attachment.getContent());
        result.setInvoiceId(attachment.getInvoiceId());
        return result;
    }
}
