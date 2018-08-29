package eu.ba30.re.blocky.service.impl.spark.coder;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.impl.other.AttachmentImpl;
import eu.ba30.re.blocky.service.impl.spark.model.AttachmentDb;

@Service
public class AttachmentDecoder {
    @Nonnull
    public List<Attachment> decodeAll(@Nonnull final List<AttachmentDb> records) {
        return records.stream().map(this::decodeFromDb).collect(Collectors.toList());
    }

    @Nonnull
    private Attachment decodeFromDb(@Nonnull final AttachmentDb attachment) {
        Validate.notNull(attachment);
        attachment.validate();
        final Attachment result = new AttachmentImpl();
        result.setId(attachment.getId());
        result.setName(attachment.getName());
        result.setFileName(attachment.getFileName());
        result.setMimeType(attachment.getMimeType());
        result.setAttachmentType(AttachmentType.forId(attachment.getAttachmentTypeId()));
        result.setContent(attachment.getContent());
        result.setInvoiceId(attachment.getInvoiceId());
        return result;
    }
}
