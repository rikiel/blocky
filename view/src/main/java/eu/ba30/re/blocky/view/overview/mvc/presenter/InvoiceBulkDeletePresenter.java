package eu.ba30.re.blocky.view.overview.mvc.presenter;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.view.ApplicationViewName;
import eu.ba30.re.blocky.view.common.mvc.view.utils.NavigationUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceBulkDeleteView;

@Component
@Scope("prototype")
public class InvoiceBulkDeletePresenter implements InvoiceBulkDeleteView.InvoiceBulkDeleteHandler {
    @Autowired
    private InvoiceBulkDeleteView view;
    @Autowired
    private InvoiceService invoiceService;

    private InvoiceBulkDeleteModel model;

    @Override
    public void onViewEnter() {
        model = NavigationUtils.getDataAfterNavigation();
        view.setModel(model);
        view.buildView();
    }

    @Nonnull
    @Override
    public InvoiceBulkDeleteView getView() {
        return view;
    }

    @Override
    public void onBack() {
        NavigationUtils.navigateTo(ApplicationViewName.OVERVIEW);
    }

    @Override
    public void onDelete() {
        invoiceService.remove(model.getToRemove());
        // TODO BLOCKY-9 zobrazit hlasku OK/ERR
    }

    @Nonnull
    @Override
    public List<Invoice> getItems() {
        return model.getToRemove();
    }

    @Override
    public boolean isChangingSelectionAllowed() {
        return false;
    }

    @Override
    public void itemsSelectionChanged(@Nonnull final Set<Invoice> invoices) {
        throw new UnsupportedOperationException("Not supported for bulk delete view");
    }
}
