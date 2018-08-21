package eu.ba30.re.blocky.view.common.mvc.view.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.view.common.mvc.view.utils.FormatterUtils;

/**
 * Detail of {@link Invoice} in grid with possible actions - update and delete of invoice.
 */
public class InvoiceDetail extends VerticalLayout {
    private final Invoice invoice;
    private final Handler handler;

    public InvoiceDetail(@Nonnull final Invoice invoice,
                         @Nonnull final Handler handler) {
        Validate.notNull(invoice, handler);
        this.invoice = invoice;
        this.handler = handler;

        setSpacing(false);
        buildComponentLayout();
    }

    private void buildComponentLayout() {
        addRow("Názov", invoice.getName());
        addRow("Kategória", FormatterUtils.formatCategoryByNameAndDescription(invoice.getCategory()));
        addRow("Ďalšie detaily", invoice.getDetails());
        addRow("Dátum vytvorenia", FormatterUtils.formatDate(invoice.getCreationDate()));

        if (invoice.getModificationDate() != null) {
            addRow("Datum poslednej modifikácie", FormatterUtils.formatDate(invoice.getModificationDate()));
        }

        addAttachmentRows();
        addActions();
    }

    private void addRow(@Nonnull final String caption, @Nullable final String value) {
        addComponent(keyValue(caption, value));
    }

    private void addAttachmentRows() {
        invoice.getAttachments()
                .forEach(attachment -> {
                    addComponent(keyValue("Príloha", attachment.getName()));
                    addComponent(keyValue("Typ súboru", FormatterUtils.formatAttachmentType(attachment.getAttachmentType())));
                    addComponent(new AttachmentPreview(attachment));
                });
    }

    private void addActions() {
        final Button updateButton = new Button("Upraviť");
        updateButton.addClickListener(event -> handler.onUpdate(invoice));
        updateButton.setVisible(handler.isUpdateAllowed());

        final Button removeButton = new Button("Zmazať");
        removeButton.addClickListener(event -> handler.onDelete(invoice));
        removeButton.setVisible(handler.isDeleteAllowed());

        addComponent(new HorizontalLayout(updateButton, removeButton));
    }

    @Nonnull
    private HorizontalLayout keyValue(@Nonnull final String caption, @Nullable final String value) {
        final Label lCaption = new Label(caption);
        final Label lValue = new Label(value);

        lCaption.setWidth(210, Sizeable.Unit.PIXELS);

        return new HorizontalLayout(lCaption, lValue);
    }

    public interface Handler {
        /**
         * @return if button for updating invoice should be visible
         */
        boolean isUpdateAllowed();

        /**
         * @return if button for deleting invoice should be visible
         */
        boolean isDeleteAllowed();

        /**
         * @param invoice to be deleted
         */
        void onDelete(@Nonnull Invoice invoice);

        /**
         * @param invoice to be updated
         */
        void onUpdate(@Nonnull Invoice invoice);
    }
}
