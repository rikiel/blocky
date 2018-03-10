package eu.ba30.re.blocky.view.overview.mvc.presenter;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.view.ApplicationViewName;
import eu.ba30.re.blocky.view.common.mvc.view.utils.NavigationUtils;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceCreateModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceCreateView;

@Component
@Scope("prototype")
public class InvoiceCreatePresenter implements InvoiceCreateView.InvoiceCreateHandler {

    @Autowired
    private InvoiceCreateView view;
    @Autowired
    private InvoiceService invoiceService;

    private InvoiceCreateModel model;

    @Override
    public void onViewEnter() {
        model = NavigationUtils.getDataAfterNavigation();

        view.setModel(model);
        view.buildView();
    }

    @Nonnull
    @Override
    public InvoiceCreateView getView() {
        return view;
    }

    @Override
    public void onBack() {
        NavigationUtils.navigateTo(ApplicationViewName.OVERVIEW);
    }

    @Override
    public void onCreate() {
        if (view.validateView()) {
            invoiceService.create(model.getInvoice());
        }
    }
}
