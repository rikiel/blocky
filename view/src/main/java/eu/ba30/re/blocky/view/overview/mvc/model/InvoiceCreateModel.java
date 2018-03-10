package eu.ba30.re.blocky.view.overview.mvc.model;

import java.io.ByteArrayOutputStream;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.utils.Validate;

public class InvoiceCreateModel {
    @Nonnull
    private final Invoice invoice;
    @Nonnull
    private final UseCase useCase;

    private ByteArrayOutputStream attachmentOutputStream;

    public InvoiceCreateModel(@Nonnull final Invoice invoice,
                              @Nonnull final UseCase useCase) {
        Validate.notNull(invoice, useCase);

        this.invoice = invoice;
        this.useCase = useCase;
    }

    @Nonnull
    public Invoice getInvoice() {
        return invoice;
    }

    @Nonnull
    public UseCase getUseCase() {
        return useCase;
    }

    public ByteArrayOutputStream getAttachmentOutputStream() {
        return attachmentOutputStream;
    }

    public void setAttachmentOutputStream(ByteArrayOutputStream attachmentOutputStream) {
        this.attachmentOutputStream = attachmentOutputStream;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("invoice", invoice)
                .add("useCase", useCase)
                .toString();
    }

    public enum UseCase {
        CREATE,
        MODIFY
    }
}
