package eu.ba30.re.blocky.view.overview.mvc.model;

import java.util.List;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.Invoice;

public class OverviewListModel {
    private List<Invoice> invoices;
    private List<Invoice> selectedInvoices;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Invoice> getSelectedInvoices() {
        return selectedInvoices;
    }

    public void setSelectedInvoices(List<Invoice> selectedInvoices) {
        this.selectedInvoices = selectedInvoices;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("invoices", invoices)
                .add("selectedInvoices", selectedInvoices)
                .toString();
    }
}
