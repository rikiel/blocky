package eu.ba30.re.blocky.overview.mvc.presenter;

import eu.ba30.re.blocky.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.overview.mvc.view.OverviewListView;
import eu.ba30.re.blocky.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OverviewListPresenter implements OverviewListView.OverviewListHandler {
    private static final Logger log = LoggerFactory.getLogger(OverviewListPresenter.class);

    @Autowired
    private OverviewListView view;

    @Autowired
    private InvoiceService invoiceService;

    private OverviewListModel model;

    @Override
    public void onViewEnter() {
        model = new OverviewListModel();
        model.setInvoices(invoiceService.getInvoices());

        view.setModel(model);
        view.buildView();
    }

    @Override
    public OverviewListView getView() {
        return view;
    }

    @Override
    public void onAddNew() {
        log.debug("onAddNew");

    }

    @Override
    public void onBulkRemove() {
        log.debug("onBulkRemove");

    }
}
