package eu.ba30.re.blocky.view.common.mvc.view.components;

import java.io.ByteArrayInputStream;

import javax.annotation.Nonnull;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.utils.Validate;

public class AttachmentPreview extends CssLayout {
    private static final int MAX_WIDTH = 600;
    private static final int MAX_HEIGHT = 600;

    private final Attachment attachment;

    public AttachmentPreview(@Nonnull final Attachment attachment) {
        Validate.notNull(attachment);
        this.attachment = attachment;

        setWidth(MAX_WIDTH, Unit.PIXELS);
        setHeight(MAX_HEIGHT, Unit.PIXELS);
        buildComponentLayout();
    }

    private void buildComponentLayout() {
        final Component component;
        switch (attachment.getType()) {
            case IMAGE:
                component = createImagePreview();
                break;
            case PDF:
                component = createPdfPreview();
                break;
            case TEXT:
                component = createTextFilePreview();
                break;
            case UNKNOWN:
                component = createUnknownFileFormatPreview();
                break;
            default:
                throw new UnsupportedOperationException("Not known type " + attachment.getType());
        }
        component.setSizeFull();
        addComponent(component);
    }

    @Nonnull
    private Image createImagePreview() {
        final Image image = new Image();
        image.setSource(new StreamResource(() -> new ByteArrayInputStream(attachment.getContent()), attachment.getFileName()));
        return image;
    }

    @Nonnull
    private Component createPdfPreview() {
        return new Label("PDF súbory zatiaľ nedokážeme zobraziť.");
    }

    @Nonnull
    private Component createUnknownFileFormatPreview() {
        return new Label(String.format("Typ súboru '%s' zatiaľ nedokážeme zobraziť.", attachment.getMimeType()));
    }

    @Nonnull
    private Component createTextFilePreview() {
        final TextArea textArea = new TextArea();
        textArea.setValue(new String(attachment.getContent()));
        textArea.setEnabled(false);
        return textArea;
    }
}
