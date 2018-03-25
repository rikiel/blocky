package eu.ba30.re.blocky.view.common.mvc.view.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.DetailsGenerator;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.view.common.mvc.view.utils.FormatterUtils;

public class InvoiceDetail implements DetailsGenerator<Invoice> {
    @Override
    public Component apply(Invoice invoice) {
        final RowBuilder builder = new RowBuilder();
        builder
                .addRow("Názov", invoice.getName())
                .addRow("Kategória", FormatterUtils.formatCategoryByNameAndDescription(invoice.getCategory()))
                .addRow("Ďalšie detaily", invoice.getDetails())
                .addRow("Dátum vytvorenia", FormatterUtils.formatDate(invoice.getCreationDate()));

        if (invoice.getModificationDate() != null) {
            builder.addRow("Datum poslednej modifikácie", FormatterUtils.formatDate(invoice.getModificationDate()));
        }

        invoice
                .getAttachments()
                .forEach(builder::addAttachmentRow);

        return builder.build();
    }

    private static class RowBuilder {
        private final VerticalLayout layout = new VerticalLayout();

        @Nonnull
        public Component build() {
            return layout;
        }

        @Nonnull
        public RowBuilder addRow(@Nonnull final  String caption, @Nullable final String value) {
            layout.addComponent(keyValue(caption, value));
            return this;
        }

        @Nonnull
        public RowBuilder addAttachmentRow(@Nonnull final Attachment attachment) {
            final VerticalLayout attachmentLayout = new VerticalLayout();
            attachmentLayout.addComponent(keyValue("Príloha", attachment.getName()));
            attachmentLayout.addComponent(keyValue("Typ súboru", FormatterUtils.formatAttachmentType(attachment.getType())));
            attachmentLayout.addComponent(new AttachmentPreview(attachment).build());
            layout.addComponent(attachmentLayout);
            return this;
        }

        private HorizontalLayout keyValue(@Nonnull final String caption, @Nullable final String value) {
            return new HorizontalLayout(new Label(caption), new Label(value));
        }
    }
}
