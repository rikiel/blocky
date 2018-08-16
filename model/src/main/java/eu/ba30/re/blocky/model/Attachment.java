package eu.ba30.re.blocky.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.cst.AttachmentType;

@Entity
@Table(name = "T_ATTACHMENTS")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_INVOICE_ID")
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Invoice invoice;

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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("fileName", fileName)
                .add("mimeType", mimeType)
                .add("attachmentType", attachmentType)
                .add("invoice.id", invoice == null ? null : invoice.getId())
                .toString();
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
