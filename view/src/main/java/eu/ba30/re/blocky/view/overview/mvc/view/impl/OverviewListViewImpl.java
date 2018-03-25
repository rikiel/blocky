package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

import eu.ba30.re.blocky.view.common.mvc.view.Style;
import eu.ba30.re.blocky.view.common.mvc.view.components.Header;
import eu.ba30.re.blocky.view.common.mvc.view.components.InvoiceTable;
import eu.ba30.re.blocky.view.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.view.overview.mvc.view.OverviewListView;

@Component
@Scope("prototype")
public class OverviewListViewImpl extends AbstractViewImpl implements OverviewListView {
    private OverviewListHandler handler;
    private OverviewListModel model;

    private VerticalLayout rootLayout;
    private Button addButton;
    private Button bulkRemoveButton;
    private InvoiceTable invoiceTable;

    @Override
    public void setHandler(@Nonnull final OverviewListHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(@Nonnull final OverviewListModel model) {
        this.model = model;
    }

    @Override
    public void buildView() {
        removeAllComponents();

        initializeLayouts();
        addActions();
        addItems();
    }

    private void initializeLayouts() {
        rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        addComponent(new Header("Zoznam"));
        addComponent(rootLayout);
    }

    private void addActions() {
        final CssLayout actionLayout = new CssLayout();
        actionLayout.addStyleName(Style.BUTTONS.getCssClass());

        addButton = new Button("Pridat");
        addButton.addClickListener((Button.ClickListener) clickEvent -> handler.onAddNew());

        bulkRemoveButton = new Button("Odstranit vybrane");
        bulkRemoveButton.setEnabled(false);
        bulkRemoveButton.addClickListener((Button.ClickListener) clickEvent -> handler.onBulkRemove());

        actionLayout.addComponents(addButton, bulkRemoveButton);
        rootLayout.addComponent(actionLayout);
    }

    private void addItems() {
        invoiceTable = new InvoiceTable(handler);
        rootLayout.addComponent(invoiceTable);
    }

    @Override
    public void setBulkRemoveButtonEnabled(boolean enabled) {
        bulkRemoveButton.setEnabled(enabled);
    }

    @Override
    public void refreshList() {
        invoiceTable.refresh();
    }
}
