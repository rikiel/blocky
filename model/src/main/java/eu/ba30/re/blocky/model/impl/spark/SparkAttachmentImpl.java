package eu.ba30.re.blocky.model.impl.spark;

import java.io.Serializable;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;

public class SparkAttachmentImpl extends Attachment implements Serializable {
    private Integer id;
    private Integer invoiceId;
    private String name;
    private String fileName;
    private String mimeType;
    private Integer attachmentTypeId;
    private byte[] content;

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
        return attachmentTypeId == null ? null : AttachmentType.forId(attachmentTypeId);
    }

    @Override
    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentTypeId = attachmentType == null ? null : attachmentType.getId();
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Integer attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }
}
