package eu.ba30.re.blocky.view.common.mvc.view.components;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.DetailsGenerator;
import com.vaadin.ui.components.grid.ItemClickListener;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.common.mvc.view.utils.FormatterUtils;

public class InvoiceTable extends Grid<Invoice> {
    private final SelectionHandler handler;

    public InvoiceTable(@Nonnull final SelectionHandler handler) {
        Validate.notNull(handler);
        this.handler = handler;

        init();
        refresh();
    }

    private void init() {
        addColumn(Invoice::getName).setCaption("Názov");
        addColumn(invoice -> FormatterUtils.formatCategoryByName(invoice.getCategory())).setCaption("Kategória");
        addColumn(invoice -> FormatterUtils.formatDate(invoice.getCreationDate())).setCaption("Dátum vytvorenia");

        setHeight(640, Unit.PIXELS);
        setWidth(720, Unit.PIXELS);

        setDetailsGenerator((DetailsGenerator<Invoice>) invoice -> new InvoiceDetail(invoice, handler).build());
        addItemClickListener((ItemClickListener<Invoice>) event -> setDetailsVisible(event.getItem(), !isDetailsVisible(event.getItem())));

        if (handler.isChangingSelectionAllowed()) {
            setSelectionMode(SelectionMode.MULTI);
            addSelectionListener(event -> handler.itemsSelectionChanged(event.getAllSelectedItems()));
        }
    }

    public void refresh() {
        setItems(handler.getItems());
    }

    public void showSingleItemDetail() {
        final List<Invoice> items = handler.getItems();
        Validate.equals(items.size(), 1, "Should contain exactly one element!");
        setDetailsVisible(items.get(0), true);
    }

    public interface SelectionHandler extends InvoiceDetail.Handler {
        @Nonnull
        List<Invoice> getItems();

        boolean isChangingSelectionAllowed();

        void itemsSelectionChanged(@Nonnull final Set<Invoice> invoices);
    }
}
