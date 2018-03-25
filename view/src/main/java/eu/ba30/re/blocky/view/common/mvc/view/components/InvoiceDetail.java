package eu.ba30.re.blocky.view.common.mvc.view.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    private final VerticalLayout layout = new VerticalLayout();

    public InvoiceDetail(@Nonnull final Invoice invoice,
                         @Nonnull final Handler handler) {
        Validate.notNull(invoice, handler);
        this.invoice = invoice;
        this.handler = handler;
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
                    final VerticalLayout attachmentLayout = new VerticalLayout();
                    attachmentLayout.addComponent(keyValue("Príloha", attachment.getName()));
                    attachmentLayout.addComponent(keyValue("Typ súboru", FormatterUtils.formatAttachmentType(attachment.getType())));
                    attachmentLayout.addComponent(new AttachmentPreview(attachment).build());
                    layout.addComponent(attachmentLayout);
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
        return new HorizontalLayout(new Label(caption), new Label(value));
    }

    public interface Handler {
        boolean isUpdateAllowed();

        boolean isDeleteAllowed();

        void onDelete(@Nonnull Invoice invoice);

        void onUpdate(@Nonnull Invoice invoice);
    }
}
