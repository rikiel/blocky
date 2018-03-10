package eu.ba30.re.blocky.view.common.mvc.view.components;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.utils.Validate;

public class AttachmentPreview {
    private static final int MAX_IMAGE_WIDTH = 600;
    private static final int MAX_IMAGE_HEIGHT = 600;

    private final Attachment attachment;

    public AttachmentPreview(@Nonnull final Attachment attachment) {
        Validate.notNull(attachment);
        this.attachment = attachment;
    }

    @Nonnull
    public Component build() {
        switch (attachment.getType()) {
            case IMAGE:
                return createImage();
            case PDF:
                return new Label("Could not display PDF files");
            case TEXT:
                final TextArea textArea = new TextArea();
                textArea.setValue(new String(attachment.getContent()));
                textArea.setEnabled(false);
                return textArea;
            case UNKNOWN:
                return new Label("Not known file format");
            default:
                throw new UnsupportedOperationException("Not known type " + attachment.getType());
        }
    }

    @Nonnull
    private Image createImage() {
        try {
            final BufferedImage bi = ImageIO.read(new ByteArrayInputStream(attachment.getContent()));
            int width = bi.getWidth();
            int height = bi.getHeight();
            while (height > MAX_IMAGE_HEIGHT || width > MAX_IMAGE_WIDTH) {
                height /= 2;
                width /= 2;
            }
            final Image image = new Image();
            image.setSource(new StreamResource(() -> new ByteArrayInputStream(attachment.getContent()), attachment.getFileName()));
            image.setHeight(height, Sizeable.Unit.PIXELS);
            image.setWidth(width, Sizeable.Unit.PIXELS);
            return image;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not parse image stream", e);
        }
    }
}
