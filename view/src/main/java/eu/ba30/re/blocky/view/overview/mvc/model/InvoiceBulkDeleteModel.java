package eu.ba30.re.blocky.view.overview.mvc.model;

import java.util.List;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.Invoice;

public class InvoiceBulkDeleteModel {
    private List<Invoice> toRemove;

    public List<Invoice> getToRemove() {
        return toRemove;
    }

    public void setToRemove(List<Invoice> toRemove) {
        this.toRemove = toRemove;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("toRemove", toRemove)
                .toString();
    }
}
