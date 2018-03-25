package eu.ba30.re.blocky.view.common.mvc.view.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.utils.Validate;
import eu.ba30.re.blocky.view.common.mvc.view.utils.FormatterUtils;

public class InvoiceDetail {
    private final Invoice invoice;
    private final Handler handler;
    private final VerticalLayout layout;

    public InvoiceDetail(@Nonnull final Invoice invoice,
                         @Nonnull final Handler handler) {
        Validate.notNull(invoice, handler);
        this.invoice = invoice;
        this.handler = handler;
        layout  = new VerticalLayout();
        layout.setSpacing(false);
    }

    @Nonnull
    public Component build() {
        addRow("Názov", invoice.getName());
        addRow("Kategória", FormatterUtils.formatCategoryByNameAndDescription(invoice.getCategory()));
        addRow("Ďalšie detaily", invoice.getDetails());
        addRow("Dátum vytvorenia", FormatterUtils.formatDate(invoice.getCreationDate()));

        if (invoice.getModificationDate() != null) {
            addRow("Datum poslednej modifikácie", FormatterUtils.formatDate(invoice.getModificationDate()));
        }

        addAttachmentRows();
        addActions();

        return layout;
    }

    private void addRow(@Nonnull final String caption, @Nullable final String value) {
        layout.addComponent(keyValue(caption, value));
    }

    private void addAttachmentRows() {
        invoice.getAttachments()
                .forEach(attachment -> {
                    layout.addComponent(keyValue("Príloha", attachment.getName()));
                    layout.addComponent(keyValue("Typ súboru", FormatterUtils.formatAttachmentType(attachment.getType())));
                    layout.addComponent(new AttachmentPreview(attachment).build());
                });
    }

    private void addActions() {
        final Button updateButton = new Button("Upraviť");
        updateButton.addClickListener(event -> handler.onUpdate(invoice));
        updateButton.setVisible(handler.isUpdateAllowed());

        final Button removeButton = new Button("Zmazať");
        removeButton.addClickListener(event -> handler.onDelete(invoice));
        removeButton.setVisible(handler.isDeleteAllowed());

        layout.addComponent(new HorizontalLayout(updateButton, removeButton));
    }

    @Nonnull
    private HorizontalLayout keyValue(@Nonnull final String caption, @Nullable final String value) {
        final Label lCaption = new Label(caption);
        final Label lValue = new Label(value);

        lCaption.setWidth(210, Sizeable.Unit.PIXELS);

        return new HorizontalLayout(lCaption, lValue);
    }

    public interface Handler {
        boolean isUpdateAllowed();

        boolean isDeleteAllowed();

        void onDelete(@Nonnull Invoice invoice);

        void onUpdate(@Nonnull Invoice invoice);
    }
}
