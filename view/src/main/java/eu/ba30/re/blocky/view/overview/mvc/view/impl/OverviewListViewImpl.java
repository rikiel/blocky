package eu.ba30.re.blocky.view.overview.mvc.view.impl;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;

import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.common.mvc.view.Style;
import eu.ba30.re.blocky.view.common.mvc.view.components.Header;
import eu.ba30.re.blocky.view.common.mvc.view.components.InvoiceTable;
import eu.ba30.re.blocky.view.overview.mvc.model.OperationResult;
import eu.ba30.re.blocky.view.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.view.overview.mvc.view.OverviewListView;

@Component
@Scope("prototype")
public class OverviewListViewImpl extends AbstractViewImpl implements OverviewListView {
    private OverviewListHandler handler;
    private OverviewListModel model;

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

        addHeader();
        addActions();
        addItems();
    }

    @Override
    public void showOperationResult(@Nonnull final OperationResult operationResult) {
        Validate.notNull(operationResult);

        final Notification notification = new Notification(operationResult.getMessage());
        notification.setPosition(Position.TOP_CENTER);

        switch (operationResult.getResult()) {
            case SUCCESS:
                notification.setDelayMsec(3500);
                break;
            case ERROR:
                notification.setDelayMsec(5000);
                break;
        }
        notification.show(Page.getCurrent());
    }

    @Override
    public void setBulkRemoveButtonEnabled(boolean enabled) {
        bulkRemoveButton.setEnabled(enabled);
    }

    private void addHeader() {
        addComponent(new CssLayout(new Header("Zoznam platieb")));
    }

    private void addActions() {
        final CssLayout actionLayout = new CssLayout();
        actionLayout.addStyleName(Style.BUTTONS.getCssClass());

        addButton = new Button("Pridať");
        addButton.addClickListener((Button.ClickListener) clickEvent -> handler.onAddNew());

        bulkRemoveButton = new Button("Odstrániť vybrané");
        bulkRemoveButton.setEnabled(false);
        bulkRemoveButton.addClickListener((Button.ClickListener) clickEvent -> handler.onBulkRemove());

        actionLayout.addComponents(addButton, bulkRemoveButton);
        addComponent(actionLayout);
    }

    private void addItems() {
        invoiceTable = new InvoiceTable(handler);
        addComponent(invoiceTable);
    }
}
