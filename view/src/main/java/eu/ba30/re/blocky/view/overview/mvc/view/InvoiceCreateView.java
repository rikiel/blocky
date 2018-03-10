package eu.ba30.re.blocky.view.overview.mvc.view;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.common.mvc.view.components.AttachmentUploadFragment;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;

public interface InvoiceCreateView extends CommonView<InvoiceCreateModel, InvoiceCreateView.InvoiceCreateHandler> {
    void buildView();

    boolean validateView();

    void stopUpload();

    void showAttachment(@Nonnull final Attachment attachment);

    interface InvoiceCreateHandler extends CommonView.CommonHandler,
                                           AttachmentUploadFragment.Handler {
        void onBack();

        void onCreate();
    }
}
