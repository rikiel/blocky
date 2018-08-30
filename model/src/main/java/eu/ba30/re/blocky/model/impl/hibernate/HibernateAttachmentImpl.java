package eu.ba30.re.blocky.model.impl.hibernate;

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

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.AttachmentType;

@Entity
@Table(name = "T_ATTACHMENTS")
public class HibernateAttachmentImpl extends Attachment {
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
    @OneToOne(targetEntity = HibernateInvoiceImpl.class)
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    @Override
    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
