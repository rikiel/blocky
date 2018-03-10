package eu.ba30.re.blocky.view.overview.mvc.view;

import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.common.mvc.view.components.InvoiceTable;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;

public interface InvoiceBulkDeleteView extends CommonView<InvoiceBulkDeleteModel, InvoiceBulkDeleteView.InvoiceBulkDeleteHandler> {
    void buildView();

    interface InvoiceBulkDeleteHandler extends CommonView.CommonHandler, InvoiceTable.SelectionHandler {
        void onBack();

        void onDelete();
    }
}
