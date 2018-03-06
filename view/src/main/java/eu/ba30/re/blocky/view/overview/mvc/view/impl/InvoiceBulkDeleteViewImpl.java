package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import eu.ba30.re.blocky.view.common.mvc.view.Style;
import eu.ba30.re.blocky.view.common.mvc.view.components.Header;
import eu.ba30.re.blocky.view.common.mvc.view.components.InvoiceTable;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceBulkDeleteView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Scope("prototype")
public class InvoiceBulkDeleteViewImpl extends VerticalLayout implements InvoiceBulkDeleteView {
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
    public void buildView() {
        removeAllComponents();

        addHeader();
        addActions();
        addItems();
    }

    private void addHeader(){
        addComponent(new Header("Zmazať položky"));
    }

    private void addActions() {
        final HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName(Style.BUTTONS.getCssClass());

        final Button backButton = new Button("Späť");
        backButton.addClickListener(event -> handler.onBack());

        final Button deleteButton = new Button("Zmazať");
        deleteButton.addClickListener(event -> handler.onDelete());

        layout.addComponentsAndExpand(backButton, deleteButton);
        addComponent(layout);
    }

    private void addItems() {
        addComponent(new InvoiceTable(handler));
    }
}
