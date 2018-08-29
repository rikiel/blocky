package eu.ba30.re.blocky.service.impl.spark.coder;

public class AttachmentDb {
    private Integer id;
    private String name;
    private String fileName;
    private String mimeType;
    private Integer attachmentTypeId;
    private byte[] content;
    private Integer invoiceId;

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

    public Integer getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Integer attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }
}
