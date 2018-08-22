package eu.ba30.re.blocky.service.impl.jdbctemplate.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;

@Service
public class JdbcTemplateAttachmentsRepositoryImpl implements AttachmentsRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateAttachmentsRepositoryImpl.class);

    private static final String CREATE_ATTACHMENT_SQL_REQUEST = ""
                                                                + " INSERT INTO T_ATTACHMENTS "
                                                                + " (ID, INVOICE_ID, NAME, FILE_NAME, MIME_TYPE, TYPE, FILE_CONTENT) "
                                                                + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
    private static final String GET_ATTACHMENT_LIST_BY_ID_SQL_REQUEST = ""
                                                                        + " SELECT * "
                                                                        + " FROM T_ATTACHMENTS "
                                                                        + " WHERE INVOICE_ID = ?";
    private static final String GET_ATTACHMENT_LIST_SQL_REQUEST = ""
                                                                  + " SELECT * "
                                                                  + " FROM T_ATTACHMENTS ";
    private static final String REMOVE_ATTACHMENTS_SQL_REQUEST = ""
                                                                 + " DELETE FROM T_ATTACHMENTS "
                                                                 + " WHERE ID = ?";
    private static final String GET_NEXT_ATTACHMENT_ID_SQL_REQUEST = "" +
                                                                     " SELECT NEXT VALUE FOR S_ATTACHMENT_ID " +
                                                                     " FROM DUAL_ATTACHMENT_ID ";

    @Autowired
    private JdbcTemplate jdbc;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList(final int invoiceId) {
        return jdbc.query(GET_ATTACHMENT_LIST_BY_ID_SQL_REQUEST,
                new Object[] { invoiceId },
                new AttachmentMapper());
    }

    @Override
    public void createAttachments(final int invoiceId, @Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        final List<Object[]> sqlArgs = attachments
                .stream()
                .map(item -> {
                    Validate.notNull(item.getId(), item.getAttachmentType(), item.getContent());
                    return new Object[] {
                            item.getId(),
                            invoiceId,
                            item.getName(),
                            item.getFileName(),
                            item.getMimeType(),
                            item.getAttachmentType().getId(),
                            item.getContent()
                    };
                })
                .collect(Collectors.toList());

        final int[] createdPerItem = jdbc.batchUpdate(CREATE_ATTACHMENT_SQL_REQUEST, sqlArgs);

        Validate.validateOneRowAffectedInDbCall(createdPerItem);
    }

    @Override
    public void removeAttachments(@Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        final List<Object[]> attachmentIds = attachments
                .stream()
                .map(attachment -> {
                    Validate.notNull(attachment.getId());
                    return new Object[] { attachment.getId() };
                })
                .collect(Collectors.toList());

        final int[] removedPerRow = jdbc.batchUpdate(REMOVE_ATTACHMENTS_SQL_REQUEST, attachmentIds);

        Validate.validateOneRowAffectedInDbCall(removedPerRow);
    }

    @Override
    public int getNextItemId() {
        return jdbc.queryForObject(GET_NEXT_ATTACHMENT_ID_SQL_REQUEST, Integer.class);
    }

    @Nonnull
    @Override
    public List<Attachment> getAllAttachments() {
        return jdbc.query(GET_ATTACHMENT_LIST_SQL_REQUEST,
                new AttachmentMapper());
    }

    private static class AttachmentMapper implements RowMapper<Attachment> {
        @Override
        public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Attachment attachment = new Attachment();

            attachment.setId(rs.getInt("ID"));
            attachment.setName(rs.getString("NAME"));
            attachment.setFileName(rs.getString("FILE_NAME"));
            attachment.setMimeType(rs.getString("MIME_TYPE"));
            attachment.setAttachmentType(AttachmentType.forId(rs.getInt("TYPE")));
            attachment.setContent(rs.getBytes("FILE_CONTENT"));

            log.debug("Loaded attachment: {}", attachment);
            return attachment;
        }
    }
}
