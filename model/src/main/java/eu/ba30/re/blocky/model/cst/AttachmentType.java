package eu.ba30.re.blocky.model.cst;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.utils.Validate;

public enum AttachmentType {
    IMAGE(1) {
        @Override
        public boolean accepts(@Nonnull String mimeType) {
            return mimeType.contains("image");
        }
    },
    PDF(2) {
        @Override
        public boolean accepts(@Nonnull String mimeType) {
            return mimeType.contains("pdf");
        }
    },
    TEXT(3) {
        @Override
        public boolean accepts(@Nonnull String mimeType) {
            return mimeType.contains("text");
        }
    },
    UNKNOWN(999) {
        @Override
        public boolean accepts(@Nonnull String mimeType) {
            return true;
        }
    };

    private final int id;

    AttachmentType(int id) {
        this.id = id;
    }

    public abstract boolean accepts(@Nonnull final String mimeType);

    public int getId() {
        return id;
    }

    @Nonnull
    public static AttachmentType forMime(@Nonnull final String mimeType) {
        Validate.notNull(mimeType);
        final String mime = mimeType.toLowerCase();
        for (AttachmentType type : values()) {
            if (type.accepts(mime)) {
                return type;
            }
        }
        throw new IllegalStateException("Should not happen. Could not find type for mime " + mime);
    }

    @Nonnull
    public static AttachmentType forId(final int id) {
        for (AttachmentType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("AttachmentType does not exist for id " + id);
    }
}
