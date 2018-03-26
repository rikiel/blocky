package eu.ba30.re.blocky.view.overview.mvc.presenter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Notification;

import eu.ba30.re.blocky.exception.DatabaseException;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.ApplicationViewName;
import eu.ba30.re.blocky.view.common.mvc.view.utils.NavigationUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceCreateView;

@Component
@Scope("prototype")
public class InvoiceCreatePresenter implements InvoiceCreateView.InvoiceCreateHandler {
    private static final Logger log = LoggerFactory.getLogger(InvoiceCreatePresenter.class);

    private static final int UPLOAD_FILE_MAX_BYTES = 10_000_000;
    private static final int MAXIMUM_UPLOADS_COUNT = 3;

    @Autowired
    private InvoiceCreateView view;
    @Autowired
    private InvoiceService invoiceService;

    private InvoiceCreateModel model;

    @Override
    public void onViewEnter() {
        model = NavigationUtils.getDataAfterNavigation();

        view.setModel(model);
        view.buildView();
    }

    @Nonnull
    @Override
    public InvoiceCreateView getView() {
        return view;
    }

    @Override
    public void onBack() {
        NavigationUtils.navigateTo(ApplicationViewName.OVERVIEW);
    }

    @Override
    public void onCreate() {
        if (view.validateView()) {
            try {
                invoiceService.create(model.getInvoice());
            } catch (DatabaseException e) {
                Notification.show(String.format("Položku '%s' sa nepodarilo vytvoriť", model.getInvoice().getName()),
                        Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void onUpdate() {
        if (view.validateView()) {
            try {
                invoiceService.update(model.getInvoice());
            } catch (DatabaseException e) {
                Notification.show(String.format("Položku '%s' sa nepodarilo zmeniť", model.getInvoice().getName()),
                        Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    @Nonnull
    @Override
    public OutputStream uploadFileToOutputStream() {
        Validate.isNull(model.getAttachmentOutputStream());

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        model.setAttachmentOutputStream(stream);
        return stream;
    }

    @Override
    public void uploadProgress(long readBytes, long contentLength) {
        if (readBytes > UPLOAD_FILE_MAX_BYTES || contentLength > UPLOAD_FILE_MAX_BYTES) {
            view.stopUpload();
            model.setAttachmentOutputStream(null);
        }
    }

    @Override
    public void uploadFailed() {
        model.setAttachmentOutputStream(null);
    }

    @Override
    public void uploadFinished(@Nonnull final String fileName, @Nonnull final String mimeType) {
        if (model.getAttachmentOutputStream() == null) {
            // upload failed
            return;
        }
        final Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setFileName(fileName);
        attachment.setMimeType(mimeType);
        attachment.setType(AttachmentType.forMime(mimeType));
        attachment.setContent(model.getAttachmentOutputStream().toByteArray());

        view.showAttachment(attachment);
        model.setAttachmentOutputStream(null);
        model.getInvoice().getAttachments().add(attachment);
    }

    @Override
    public void deleteAttachment(@Nonnull final Attachment attachment) {
        final boolean removed = model.getInvoice().getAttachments().remove(attachment);
        if (!removed) {
            log.warn("Attachment was not found: {} -> {}", attachment, model.getInvoice().getAttachments());
        }
    }
}
