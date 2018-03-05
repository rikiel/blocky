package eu.ba30.re.blocky.overview.mvc.model;

import eu.ba30.re.blocky.Invoice;

import java.util.List;

public class OverviewListModel {
    private List<Invoice> invoices;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "OverviewListModel{" +
                "invoices=" + invoices +
                '}';
    }
}
