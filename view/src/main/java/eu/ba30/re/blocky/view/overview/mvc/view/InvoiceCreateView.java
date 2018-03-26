package eu.ba30.re.blocky.view.overview.mvc.view;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.common.mvc.view.components.AttachmentUploadFragment;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;

public interface InvoiceCreateView extends CommonView<InvoiceCreateModel, InvoiceCreateView.InvoiceCreateHandler> {
    void buildView();

    /**
     * @return if all required items in view are filled with valid data
     */
    boolean validateView();

    void stopUpload();

    /**
     * @param attachment to display in view
     */
    void showAttachment(@Nonnull final Attachment attachment);

    interface InvoiceCreateHandler extends CommonView.CommonHandler,
                                           AttachmentUploadFragment.Handler {
        void onBack();

        void onCreate();

        void onUpdate();
    }
}
