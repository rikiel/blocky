package eu.ba30.re.blocky.overview.mvc.view;

import eu.ba30.re.blocky.common.mvc.view.CommonView;
import eu.ba30.re.blocky.overview.mvc.model.OverviewListModel;

public interface OverviewListView extends CommonView<OverviewListModel, OverviewListView.OverviewListHandler> {
    void setBulkRemoveButtonEnabled(boolean enabled);

    void buildView();

    interface OverviewListHandler extends CommonView.CommonHandler {
        void onAddNew();

        void onBulkRemove();
    }
}
