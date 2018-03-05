package eu.ba30.re.blocky.view.overview.mvc.presenter;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.impl.InvoiceServiceImpl;
import eu.ba30.re.blocky.view.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.view.overview.mvc.view.OverviewListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

@Component
@Scope("prototype")
public class OverviewListPresenter implements OverviewListView.OverviewListHandler {
    private static final Logger log = LoggerFactory.getLogger(OverviewListPresenter.class);

    @Autowired
    private OverviewListView view;

    @Autowired
    private InvoiceServiceImpl invoiceService;

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

    @Nonnull
    @Override
    public List<Invoice> getItems() {
        return model.getInvoices();
    }

    @Override
    public void selectionChanged(@Nonnull final Set<Invoice> invoices) {
        view.setBulkRemoveButtonEnabled(!invoices.isEmpty());
    }
}
