package eu.ba30.re.blocky.view.overview.mvc.view;

import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;

public interface InvoiceCreateView extends CommonView<InvoiceCreateModel, InvoiceCreateView.InvoiceCreateHandler> {
    void buildView();

    boolean validateView();

    interface InvoiceCreateHandler extends CommonView.CommonHandler {
        void onBack();

        void onCreate();
    }
}
