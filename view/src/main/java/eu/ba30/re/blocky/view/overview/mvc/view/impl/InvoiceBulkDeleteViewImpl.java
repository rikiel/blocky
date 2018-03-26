package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

import eu.ba30.re.blocky.view.common.mvc.view.Style;
import eu.ba30.re.blocky.view.common.mvc.view.components.Header;
import eu.ba30.re.blocky.view.common.mvc.view.components.InvoiceTable;
import eu.ba30.re.blocky.view.overview.mvc.model.InvoiceBulkDeleteModel;
import eu.ba30.re.blocky.view.overview.mvc.view.InvoiceBulkDeleteView;

@Component
@Scope("prototype")
public class InvoiceBulkDeleteViewImpl extends AbstractViewImpl implements InvoiceBulkDeleteView {
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

    private void addHeader() {
        addComponent(new CssLayout(new Header("Zmazať položky")));
    }

    private void addActions() {
        final CssLayout actionLayout = new CssLayout();
        actionLayout.addStyleName(Style.BUTTONS.getCssClass());

        final Button backButton = new Button("Späť");
        backButton.addClickListener(event -> handler.onBack());

        final Button deleteButton = new Button("Zmazať");
        deleteButton.addClickListener(event -> handler.onBulkDelete());

        actionLayout.addComponents(backButton, deleteButton);
        addComponent(actionLayout);
    }

    private void addItems() {
        final InvoiceTable invoiceTable = new InvoiceTable(handler);
        if (model.isShowSingleItemDetail()) {
            invoiceTable.showSingleItemDetail();
        }
        addComponent(invoiceTable);
    }
}
