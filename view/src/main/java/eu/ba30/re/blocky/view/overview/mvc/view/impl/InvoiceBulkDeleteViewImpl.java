package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import com.vaadin.navigator.ViewChangeListener;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceBulkDeleteView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Scope("prototype")
public class InvoiceBulkDeleteViewImpl implements InvoiceBulkDeleteView {
    private InvoiceBulkDeleteHandler handler;
    private InvoiceBulkDeleteModel model;

    @Override
    public void setHandler(@Nonnull final InvoiceBulkDeleteHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(@Nonnull final InvoiceBulkDeleteModel model) {
        this.model = model;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        handler.onViewEnter();
    }
}
