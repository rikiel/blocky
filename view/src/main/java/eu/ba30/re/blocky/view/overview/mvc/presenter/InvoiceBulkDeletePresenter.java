package eu.ba30.re.blocky.view.overview.mvc.presenter;

import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceBulkDeleteView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Scope("prototype")
public class InvoiceBulkDeletePresenter implements InvoiceBulkDeleteView.InvoiceBulkDeleteHandler {
    @Autowired
    private InvoiceBulkDeleteView view;

    @Override
    public void onViewEnter() {
    }

    @Nonnull
    @Override
    public InvoiceBulkDeleteView getView() {
        return view;
    }
}
