package eu.ba30.re.blocky.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import eu.ba30.re.blocky.view.common.mvc.view.CommonView;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceBulkDeleteView;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceCreateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class InvoiceCreateVaadinView extends CssLayout implements View {
    @Autowired
    private InvoiceCreateView.InvoiceCreateHandler handler;

    @SuppressWarnings("unchecked")
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final CommonView view = handler.getView();
        view.setHandler(handler);
        handler.onViewEnter();
        addComponent(view);
    }
}
