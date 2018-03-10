package eu.ba30.re.blocky.view.overview.mvc.view;

import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.common.mvc.view.components.InvoiceTable;
import eu.ba30.re.blocky.view.overview.mvc.model.OverviewListModel;

public interface OverviewListView extends CommonView<OverviewListModel, OverviewListView.OverviewListHandler> {
    void setBulkRemoveButtonEnabled(boolean enabled);

    void refreshList();

    void buildView();

    interface OverviewListHandler extends CommonView.CommonHandler, InvoiceTable.SelectionHandler {
        void onAddNew();

        void onBulkRemove();
    }
}
