package eu.ba30.re.blocky.model;

import java.util.Objects;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.cst.AttachmentType;

public abstract class Attachment {
    transient private boolean inToString;

    public abstract Integer getId();

    public abstract void setId(Integer id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getFileName();

    public abstract void setFileName(String fileName);

    public abstract String getMimeType();

    public abstract void setMimeType(String mimeType);

    public abstract AttachmentType getAttachmentType();

    public abstract void setAttachmentType(AttachmentType attachmentType);

    public abstract byte[] getContent();

    public abstract void setContent(byte[] content);

    public abstract Invoice getInvoice();

    public abstract void setInvoice(Invoice invoice);

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        final Attachment that = (Attachment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public final String toString() {
        if (inToString) {
            return MoreObjects.toStringHelper(this)
                    .add("id", getId())
                    .add("name", getName())
                    .add("fileName", getFileName())
                    .add("mimeType", getMimeType())
                    .add("attachmentType", getAttachmentType())
                    .add("invoice.id", getInvoice() == null ? null : getInvoice().getId())
                    .toString();
        } else {
            inToString = true;
            final String result = MoreObjects.toStringHelper(this)
                    .add("id", getId())
                    .add("name", getName())
                    .add("fileName", getFileName())
                    .add("mimeType", getMimeType())
                    .add("attachmentType", getAttachmentType())
                    .add("invoice", getInvoice())
                    .toString();
            inToString = false;
            return result;
        }
    }
}
