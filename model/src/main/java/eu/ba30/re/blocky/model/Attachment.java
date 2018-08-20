package eu.ba30.re.blocky.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.cst.AttachmentType;

@Entity
@Table(name = "T_ATTACHMENTS")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DUAL_ATTACHMENT_ID")
    @SequenceGenerator(name = "DUAL_ATTACHMENT_ID", sequenceName = "S_ATTACHMENT_ID", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "MIME_TYPE")
    private String mimeType;
    @Column(name = "TYPE")
    @Convert(converter = AttachmentType.AttachmentTypeConverter.class)
    private AttachmentType attachmentType;
    @Column(name = "FILE_CONTENT")
    @Lob
    private byte[] content;
    @OneToOne
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    transient private boolean inToString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        if (inToString) {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("name", name)
                    .add("fileName", fileName)
                    .add("mimeType", mimeType)
                    .add("attachmentType", attachmentType)
                    .add("invoice.id", invoice == null ? null : invoice.getId())
                    .toString();
        } else {
            inToString = true;
            final String result = MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("name", name)
                    .add("fileName", fileName)
                    .add("mimeType", mimeType)
                    .add("attachmentType", attachmentType)
                    .add("invoice", invoice)
                    .toString();
            inToString = false;
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        final Attachment that = (Attachment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
