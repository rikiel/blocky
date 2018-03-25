package eu.ba30.re.blocky.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.InvoiceRepository;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public List<Invoice> getInvoices() {
        return invoiceRepository.getInvoices()
                .stream()
                .peek(invoice -> invoice.setAttachments(attachmentsRepository.getAttachmentList(invoice.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        invoiceRepository.remove(invoices);
        final Set<Attachment> attachments = invoices
                .stream()
                .flatMap(invoice -> attachmentsRepository.getAttachmentList(invoice.getId()).stream())
                .collect(Collectors.toSet());
        if (!attachments.isEmpty()) {
            attachmentsRepository.removeAttachments(Lists.newArrayList(attachments));
        }
    }

    @Transactional
    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.isNull(invoice.getId());

        invoice.setId(invoiceRepository.getNextItemId());
        invoice.setCreationDate(LocalDate.now());
        invoiceRepository.create(invoice);

        final List<Attachment> attachments = invoice.getAttachments();
        if (!attachments.isEmpty()) {
            attachments.forEach(attachment -> {
                Validate.isNull(attachment.getId());
                attachment.setId(attachmentsRepository.getNextItemId());
            });
            attachmentsRepository.createAttachments(invoice.getId(), attachments);
        }
    }

    @Transactional
    @Override
    public void update(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId());

        invoiceRepository.remove(Lists.newArrayList(invoice));
        invoice.setModificationDate(LocalDate.now());
        invoiceRepository.create(invoice);

        // instead of update, do remove all from db and then insert all from model
        final List<Attachment> actualDbAttachments = attachmentsRepository.getAttachmentList(invoice.getId());
        if (!actualDbAttachments.isEmpty()) {
            attachmentsRepository.removeAttachments(actualDbAttachments);
        }
        if (!invoice.getAttachments().isEmpty()) {
            invoice.getAttachments().forEach(attachment -> {
                if (attachment.getId() == null) {
                    attachment.setId(attachmentsRepository.getNextItemId());
                }
            });
            attachmentsRepository.createAttachments(invoice.getId(), invoice.getAttachments());
        }
    }
}
