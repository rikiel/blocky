package eu.ba30.re.blocky.view.common.mvc.view.components;

import java.io.OutputStream;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.common.mvc.view.Style;

public class AttachmentUploadFragment {
    private final Handler handler;

    private Upload upload;
    private CssLayout uploadLayout = new CssLayout();
    private VerticalLayout previewsLayout = new VerticalLayout();

    public AttachmentUploadFragment(@Nonnull final Handler handler) {
        Validate.notNull(handler);

        this.handler = handler;
        this.upload = new Upload();

        initUploadComponent();
    }

    @Nonnull
    public Component build() {
        final VerticalLayout layout = new VerticalLayout();
        layout.addStyleName(Style.UPLOAD_FRAGMENT.getCssClass());

        uploadLayout.addStyleName(Style.UPLOAD.getCssClass());
        uploadLayout.addComponent(upload);
        previewsLayout.addStyleName(Style.ATTACHMENT_PREVIEW.getCssClass());

        layout.addComponentsAndExpand(uploadLayout, previewsLayout);

        return layout;
    }

    public void stopUpload() {
        upload.interruptUpload();
    }

    public void showAttachment(@Nonnull final Attachment attachment) {
        Validate.notNull(attachment);
        final TextField attachmentName = new TextField("Názov prílohy");
        final Binder<Attachment> nameBinder = new Binder<>();
        nameBinder.forField(attachmentName)
                .asRequired("Názov prílohy je povinný")
                .bind(Attachment::getName, Attachment::setName);
        nameBinder.readBean(attachment);

        final Button changeName = new Button("Zmeniť názov");
        changeName.addClickListener(event -> {
            nameBinder.validate();
            if (nameBinder.isValid()) {
                try {
                    nameBinder.writeBean(attachment);
                } catch (ValidationException e) {
                    throw new IllegalStateException(e);
                }
            }
        });

        final AttachmentPreview attachmentPreview = new AttachmentPreview(attachment);

        final VerticalLayout layout = new VerticalLayout();
        layout.addComponentsAndExpand(new HorizontalLayout(attachmentName, changeName));
        layout.addComponentsAndExpand(attachmentPreview.build());
        previewsLayout.addComponent(layout);
    }

    private void initUploadComponent() {
        upload.setCaption("Nahrať prílohu");
        upload.setReceiver((filename, mimeType) -> handler.uploadFileToOutputStream());
        upload.setImmediateMode(false);

        addLoggingListener();
        addHandlerListener();
    }

    private void addLoggingListener() {
        upload.addProgressListener(UploadLogger.INSTANCE);
        upload.addFinishedListener(UploadLogger.INSTANCE);
        upload.addFailedListener(UploadLogger.INSTANCE);
    }

    private void addHandlerListener() {
        upload.addFailedListener(event -> handler.uploadFailed());
        upload.addFinishedListener(event -> handler.uploadFinished(event.getFilename(), event.getMIMEType(), event.getLength()));
        upload.addProgressListener(handler::uploadProgress);
    }

    public interface Handler {
        @Nonnull
        OutputStream uploadFileToOutputStream();

        void uploadProgress(long readBytes, long contentLength);

        void uploadFailed();

        void uploadFinished(@Nonnull String fileName, @Nonnull String mimeType, long length);
    }

    private enum UploadLogger implements Upload.FinishedListener, Upload.FailedListener, Upload.ProgressListener {
        INSTANCE;

        private static final Logger log = LoggerFactory.getLogger(UploadLogger.class);

        @Override
        public void uploadFailed(Upload.FailedEvent event) {
            log.error("Upload failed", event.getReason());
        }

        @Override
        public void uploadFinished(Upload.FinishedEvent event) {
            log.info("Upload finished. FileName '{}', Length '{}', MimeType '{}'",
                    event.getFilename(), event.getLength(), event.getMIMEType());
        }

        @Override
        public void updateProgress(long readBytes, long contentLength) {
            log.trace("Progress of upload: Read '{}', TotalLength '{}'", readBytes, contentLength);
        }
    }
}
