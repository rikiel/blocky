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
import com.google.common.collect.Sets;

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
            attachments.forEach(attachment -> attachment.setId(attachmentsRepository.getNextItemId()));
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

        final Set<Attachment> actualDbAttachments = Sets.newHashSet(attachmentsRepository.getAttachmentList(invoice.getId()));
        final Set<Attachment> actualModelAttachments = Sets.newHashSet(invoice.getAttachments());

        final Sets.SetView<Attachment> toDelete = Sets.difference(actualDbAttachments, actualModelAttachments);
        if (!toDelete.isEmpty()) {
            attachmentsRepository.removeAttachments(Lists.newArrayList(toDelete));
        }
        final Sets.SetView<Attachment> toInsert = Sets.difference(actualModelAttachments, actualDbAttachments);
        if (!toInsert.isEmpty()) {
            toInsert.forEach(attachment -> {
                if (attachment.getId() == null) {
                    // should be created
                    attachment.setId(attachmentsRepository.getNextItemId());
                }
            });
            attachmentsRepository.createAttachments(invoice.getId(), Lists.newArrayList(toInsert));
        }
    }
}
