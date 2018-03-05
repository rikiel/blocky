package eu.ba30.re.blocky.overview.mvc.view.impl;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import eu.ba30.re.blocky.common.mvc.view.components.Header;
import eu.ba30.re.blocky.common.mvc.view.components.Style;
import eu.ba30.re.blocky.overview.mvc.model.OverviewListModel;
import eu.ba30.re.blocky.overview.mvc.view.OverviewListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OverviewListViewImpl extends CssLayout implements OverviewListView {
    private static final Logger log = LoggerFactory.getLogger(OverviewListViewImpl.class);

    private OverviewListHandler handler;
    private OverviewListModel model;

    private CssLayout rootLayout;
    private Button addButton;
    private Button bulkRemoveButton;

    @Override
    public void setHandler(OverviewListHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(OverviewListModel model) {
        this.model = model;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        log.debug("enter()");
        handler.onViewEnter();
    }

    @Override
    public void buildView() {
        initializeLayouts();
        addActions();
        addItems();
    }

    private void initializeLayouts() {
        rootLayout = new CssLayout();
        addComponent(new VerticalLayout(
                new Header("Zoznam"),
                rootLayout));
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
        final VerticalLayout layout = new VerticalLayout(new InvoiceTable(handler));
        rootLayout.addComponent(layout);
    }

    @Override
    public void setBulkRemoveButtonEnabled(boolean enabled) {
        bulkRemoveButton.setEnabled(enabled);
    }
}
