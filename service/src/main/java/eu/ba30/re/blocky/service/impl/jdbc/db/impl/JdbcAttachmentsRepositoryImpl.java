package eu.ba30.re.blocky.service.impl.jdbc.db.impl;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.exception.DatabaseException;
import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.service.impl.jdbc.db.JdbcAttachmentsRepository;

@Service
public class JdbcAttachmentsRepositoryImpl implements JdbcAttachmentsRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcAttachmentsRepositoryImpl.class);

    private static final String CREATE_ATTACHMENT_SQL_REQUEST = ""
                                                                + " INSERT INTO T_ATTACHMENTS "
                                                                + " (ID, INVOICE_ID, NAME, FILE_NAME, MIME_TYPE, TYPE, FILE_CONTENT) "
                                                                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
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

    private final AttachmentMapper MAPPER = new AttachmentMapper();

    @Autowired
    private Connection connection;

    @Nonnull
    @Override
    public List<Attachment> getAttachmentList(final int invoiceId) {
        final List<Attachment> results = Lists.newArrayList();
        try (final PreparedStatement statement = connection.prepareStatement(GET_ATTACHMENT_LIST_BY_ID_SQL_REQUEST)) {
            statement.setInt(1, invoiceId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(MAPPER.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
        return results;
    }

    @Override
    public void createAttachments(final int invoiceId, @Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        try {
            for (Attachment attachment : attachments) {
                try (final PreparedStatement statement = connection.prepareStatement(CREATE_ATTACHMENT_SQL_REQUEST)) {
                    statement.setInt(1, attachment.getId());
                    statement.setInt(2, invoiceId);
                    statement.setString(3, attachment.getName());
                    statement.setString(4, attachment.getFileName());
                    statement.setString(5, attachment.getMimeType());
                    statement.setInt(6, attachment.getAttachmentType().getId());
                    statement.setBlob(7, new ByteArrayInputStream(attachment.getContent()));

                    statement.execute();
                    Validate.equals(statement.getUpdateCount(), 1, "Should create one row. Found " + statement.getUpdateCount());
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
    }

    @Override
    public void removeAttachments(@Nonnull final List<Attachment> attachments) {
        Validate.notEmpty(attachments);

        try {
            for (Attachment attachment : attachments) {
                try (final PreparedStatement statement = connection.prepareStatement(REMOVE_ATTACHMENTS_SQL_REQUEST)) {
                    statement.setInt(1, attachment.getId());

                    statement.execute();
                    Validate.equals(statement.getUpdateCount(), 1, "Should remove one row. Found " + statement.getUpdateCount());
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
    }

    @Override
    public int getNextItemId() {
        try (final Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(GET_NEXT_ATTACHMENT_ID_SQL_REQUEST)) {
                Integer id = null;
                while (resultSet.next()) {
                    Validate.isNull(id, "More IDs was returned!");
                    id = resultSet.getInt(1);
                }
                Validate.notNull(id, "No ID was returned!");
                return id;
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
    }

    @Nonnull
    @Override
    public List<Attachment> getAllAttachments() {
        final List<Attachment> results = Lists.newArrayList();
        try (final Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(GET_ATTACHMENT_LIST_SQL_REQUEST)) {
                while (resultSet.next()) {
                    results.add(MAPPER.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
        return results;
    }

    private static class AttachmentMapper {
        @Nonnull
        Attachment mapRow(ResultSet rs) throws SQLException {
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
