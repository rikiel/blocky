package eu.ba30.re.blocky.service.impl.spark.csv;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;

@Service
public class SparkCsvInvoiceServiceImpl implements InvoiceService {
    @Autowired
    private AttachmentsRepository attachmentsRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        final List<Invoice> invoices = invoiceRepository.getInvoiceList();
        invoices.forEach(invoice -> invoice.setAttachments(attachmentsRepository.getAttachmentsByInvoiceId(invoice.getId())));
        return invoices;
    }

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList() {
        return attachmentsRepository.getAttachmentList();
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        invoiceRepository.remove(invoices);
        final Set<Attachment> attachments = invoices
                .stream()
                .flatMap(invoice -> attachmentsRepository.getAttachmentsByInvoiceId(invoice.getId()).stream())
                .collect(Collectors.toSet());
        if (!attachments.isEmpty()) {
            attachmentsRepository.removeAttachments(Lists.newArrayList(attachments));
        }
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.isNull(invoice.getId());

        invoice.setId(invoiceRepository.getNextInvoiceId());
        invoice.setCreationDate(LocalDate.now());
        invoiceRepository.create(invoice);

        final List<Attachment> attachments = invoice.getAttachments();
        if (!attachments.isEmpty()) {
            attachments.forEach(attachment -> {
                Validate.isNull(attachment.getId());
                attachment.setId(attachmentsRepository.getNextAttachmentId());
            });
            attachmentsRepository.createAttachmentsForInvoice(invoice.getId(), attachments);
        }
    }

    @Nonnull
    @Override
    public Invoice update(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId());

        invoiceRepository.remove(Lists.newArrayList(invoice));
        invoice.setModificationDate(LocalDate.now());
        invoiceRepository.create(invoice);

        // instead of update, do remove all from db and then insert all from model
        final List<Attachment> actualDbAttachments = attachmentsRepository.getAttachmentsByInvoiceId(invoice.getId());
        if (!actualDbAttachments.isEmpty()) {
            attachmentsRepository.removeAttachments(actualDbAttachments);
        }
        if (!invoice.getAttachments().isEmpty()) {
            invoice.getAttachments().forEach(attachment -> {
                if (attachment.getId() == null) {
                    attachment.setId(attachmentsRepository.getNextAttachmentId());
                }
            });
            attachmentsRepository.createAttachmentsForInvoice(invoice.getId(), invoice.getAttachments());
        }
        return invoice;
    }
}
