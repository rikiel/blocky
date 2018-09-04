package eu.ba30.re.blocky.model.impl.other;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;

public class AttachmentImpl extends Attachment {
    private Integer id;
    private String name;
    private String fileName;
    private String mimeType;
    private AttachmentType attachmentType;
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
}
