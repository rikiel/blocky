package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;

@Service
public class AttachmentsRepositoryImpl implements AttachmentsRepository {
    private static final String CREATE_ATTACHMENT_SQL_REQUEST = ""
                                                                + " INSERT INTO T_ATTACHMENTS "
                                                                + " (ID, NAME, FILE_NAME, MIME_TYPE, TYPE, FILE_CONTENT) "
                                                                + " VALUES (?, ?, ?, ?, ?, ?) ";

    @Autowired
    private JdbcTemplate jdbc;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList(int invoiceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createAttachments(int invoiceId, @Nonnull List<Attachment> attachments) {
        final Attachment item = attachments.get(0);
        jdbc.update(CREATE_ATTACHMENT_SQL_REQUEST,
                item.getId(),
                item.getName(),
                item.getFileName(),
                item.getMimeType(),
                item.getType().toString(),
                item.getContent());
    }

    @Override
    public void removeAttachments(@Nonnull List<Integer> invoiceIds) {
        throw new UnsupportedOperationException();

    }
}
