package eu.ba30.re.blocky.service.impl.spark;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentDecoder;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentEncoder;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceDecoder;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceEncoder;
import eu.ba30.re.blocky.service.impl.spark.model.AttachmentDb;
import eu.ba30.re.blocky.service.impl.spark.model.InvoiceDb;

@Service
public class SparkInvoiceServiceImpl implements InvoiceService {
    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private AttachmentDecoder attachmentDecoder;
    @Autowired
    private InvoiceDecoder invoiceDecoder;
    @Autowired
    private AttachmentEncoder attachmentEncoder;
    @Autowired
    private InvoiceEncoder invoiceEncoder;

    private Dataset<InvoiceDb> invoiceDataset;
    private Dataset<AttachmentDb> attachmentDataset;

    @PostConstruct
    private void init() {
        //        invoiceDataset = sparkSession.sql("SELECT * FROM global_temp.T_INVOICES").as(Encoders.bean(InvoiceDb.class));
        //        attachmentDataset = sparkSession.sql("SELECT * FROM global_temp.T_ATTACHMENTS").as(Encoders.bean(AttachmentDb.class));
    }

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        final List<Invoice> invoices = invoiceDecoder.decodeAll(invoiceDataset.collectAsList());
        invoices.forEach(invoice -> invoice.setAttachments(getAttachmentsForInvoiceId(invoice.getId())));
        return invoices;
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList() {
        return attachmentDecoder.decodeAll(attachmentDataset.collectAsList());
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Invoice update(@Nonnull final Invoice invoice) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    private List<Attachment> getAttachmentsForInvoiceId(int invoiceId) {
        return attachmentDecoder.decodeAll(attachmentDataset.where(new Column("invoiceId").equalTo(invoiceId)).collectAsList());
    }
}
