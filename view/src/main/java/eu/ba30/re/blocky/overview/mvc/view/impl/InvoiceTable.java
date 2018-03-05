package eu.ba30.re.blocky.overview.mvc.view.impl;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import eu.ba30.re.blocky.Invoice;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class InvoiceTable extends VerticalLayout {
    private final Grid<Invoice> grid;
    private final SelectionHandler handler;

    InvoiceTable(@Nonnull final SelectionHandler handler) {
        Objects.requireNonNull(handler);
        this.handler = handler;

        grid = new Grid<>();
        grid.setItems(handler.getItems());
        // TODO BLOCKY-3 Detaily poloziek
        grid.addColumn(Invoice::getName).setCaption("Názov");
        grid.addColumn(Invoice::getCategory).setCaption("Kategória");

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(event -> handler.selectionChanged(event.getAllSelectedItems()));

        addComponent(grid);
    }

    public void refresh() {
        grid.setItems(handler.getItems());
    }

    public interface SelectionHandler {
        @Nonnull
        List<Invoice> getItems();

        void selectionChanged(@Nonnull final Set<Invoice> invoices);
    }
}
