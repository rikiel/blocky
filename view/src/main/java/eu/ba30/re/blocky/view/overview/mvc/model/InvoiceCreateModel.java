package eu.ba30.re.blocky.view.overview.mvc.model;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.utils.Validate;

public class InvoiceCreateModel {
    @Nonnull
    private final Invoice invoice;
    @Nonnull
    private final UseCase useCase;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("invoice", invoice)
                .toString();
    }

    public enum UseCase {
        CREATE,
        MODIFY
    }
}
