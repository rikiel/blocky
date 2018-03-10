package eu.ba30.re.blocky.view.overview.mvc.presenter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.ApplicationViewName;
import eu.ba30.re.blocky.view.common.mvc.view.utils.NavigationUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceCreateView;

@Component
@Scope("prototype")
public class InvoiceCreatePresenter implements InvoiceCreateView.InvoiceCreateHandler {
    private static final int UPLOAD_FILE_MAX_BYTES = 1_000_000;
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
            invoiceService.create(model.getInvoice());
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
    public void uploadFinished(@Nullable final String fileName, @Nullable final String mimeType) {
        if (fileName == null || mimeType == null) {
            return;
        }
        Validate.notNull(model.getAttachmentOutputStream());
        final Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setFileName(fileName);
        attachment.setMimeType(mimeType);
        attachment.setType(Attachment.Type.forMime(mimeType));
        attachment.setContent(model.getAttachmentOutputStream().toByteArray());

        view.showAttachment(attachment);
        model.setAttachmentOutputStream(null);
    }
}
