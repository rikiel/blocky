package eu.ba30.re.blocky.view.overview.mvc.presenter;

import com.google.common.collect.Lists;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.InvoiceServiceImpl;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.ApplicationViewName;
import eu.ba30.re.blocky.view.common.mvc.view.utils.NavigationUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
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
    private InvoiceService invoiceService;

    private OverviewListModel model;

    @Override
    public void onViewEnter() {
        model = new OverviewListModel();
        fillInvoicesFromService();

        view.setModel(model);
        view.buildView();
    }

    @Nonnull
    @Override
    public OverviewListView getView() {
        return view;
    }

    @Override
    public void onAddNew() {
        final InvoiceCreateModel targetModel = new InvoiceCreateModel(new Invoice(), InvoiceCreateModel.UseCase.CREATE);
        NavigationUtils.navigateTo(ApplicationViewName.CREATE, targetModel);
    }

    @Override
    public void onBulkRemove() {
        final List<Invoice> toRemove = model.getSelectedInvoices();
        Validate.notEmpty(toRemove);

        final InvoiceBulkDeleteModel targetModel = new InvoiceBulkDeleteModel();
        targetModel.setToRemove(toRemove);
        NavigationUtils.navigateTo(ApplicationViewName.BULK_DELETE, targetModel);
    }

    @Nonnull
    @Override
    public List<Invoice> getItems() {
        return model.getInvoices();
    }

    @Override
    public boolean isChangingSelectionAllowed() {
        return true;
    }

    @Override
    public void itemsSelectionChanged(@Nonnull final Set<Invoice> invoices) {
        view.setBulkRemoveButtonEnabled(!invoices.isEmpty());
        model.setSelectedInvoices(Lists.newArrayList(invoices));
    }

    private void fillInvoicesFromService() {
        model.setInvoices(invoiceService.getInvoices());
    }
}
