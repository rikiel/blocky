package eu.ba30.re.blocky.model;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.utils.Validate;

public class Attachment {
    private String name;
    private String fileName;
    private String mimeType;
    private Type type;
    private byte[] content;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("fileName", fileName)
                .add("mimeType", mimeType)
                .add("type", type)
                .toString();
    }

    public enum Type {
        IMAGE,
        PDF,
        TEXT,
        UNKNOWN;

        @Nonnull
        public static Type forMime(@Nonnull final String mimeType) {
            Validate.notNull(mimeType);
            final String mime = mimeType.toLowerCase();
            if (mime.contains("pdf")) {
                return PDF;
            }
            if (mime.contains("image")) {
                return IMAGE;
            }
            if (mime.contains("text")) {
                return TEXT;
            }
            return UNKNOWN;
        }
    }
}
