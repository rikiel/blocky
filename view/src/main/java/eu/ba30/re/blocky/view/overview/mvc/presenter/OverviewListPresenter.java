package eu.ba30.re.blocky.view.overview.mvc.presenter;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.ApplicationViewName;
import eu.ba30.re.blocky.view.common.mvc.view.utils.NavigationUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
import eu.ba30.re.blocky.view.overview.mvc.model.OperationResult;
import eu.ba30.re.blocky.view.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.view.overview.mvc.view.OverviewListView;

@Component
@Scope("prototype")
public class OverviewListPresenter implements OverviewListView.OverviewListHandler {
    @Autowired
    private OverviewListView view;
    @Autowired
    private InvoiceService invoiceService;

    private OverviewListModel model;

    @Override
    public void onViewEnter() {
        final OperationResult operationResult = NavigationUtils.tryGetDataAfterNavigation();

        model = new OverviewListModel();
        fillInvoicesFromService();

        view.setModel(model);
        view.buildView();

        if (operationResult != null) {
            view.showOperationResult(operationResult);
        }
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

    @Override
    public boolean isUpdateAllowed() {
        return true;
    }

    @Override
    public boolean isDeleteAllowed() {
        return true;
    }

    @Override
    public void onDelete(@Nonnull final Invoice invoice) {
        final InvoiceBulkDeleteModel targetModel = new InvoiceBulkDeleteModel();
        targetModel.setToRemove(Lists.newArrayList(invoice));
        targetModel.setShowSingleItemDetail(true);
        NavigationUtils.navigateTo(ApplicationViewName.BULK_DELETE, targetModel);
    }

    @Override
    public void onUpdate(@Nonnull final Invoice invoice) {
        final InvoiceCreateModel targetModel = new InvoiceCreateModel(invoice, InvoiceCreateModel.UseCase.MODIFY);
        NavigationUtils.navigateTo(ApplicationViewName.CREATE, targetModel);
    }

    private void fillInvoicesFromService() {
        model.setInvoices(invoiceService.getInvoices());
    }
}
