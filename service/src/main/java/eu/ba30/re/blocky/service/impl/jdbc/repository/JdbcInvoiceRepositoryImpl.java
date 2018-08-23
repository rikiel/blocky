package eu.ba30.re.blocky.service.impl.jdbc.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.exception.DatabaseException;
import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;

@Service
public class JdbcInvoiceRepositoryImpl implements InvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcInvoiceRepositoryImpl.class);

    private static final String GET_ALL_INVOICES_SQL_REQUEST = ""
                                                               + " SELECT * "
                                                               + " FROM T_INVOICES ";

    private static final String CREATE_INVOICE_SQL_REQUEST = ""
                                                             + " INSERT INTO T_INVOICES "
                                                             + " (ID, NAME, CATEGORY_ID, DETAILS, CREATION, LAST_MODIFICATION) "
                                                             + " VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String REMOVE_INVOICE_SQL_REQUEST = ""
                                                             + " DELETE FROM T_INVOICES "
                                                             + " WHERE ID = ?";
    private static final String GET_NEXT_INVOICE_ID_SQL_REQUEST = "" +
                                                                  " SELECT NEXT VALUE FOR S_INVOICE_ID " +
                                                                  " FROM DUAL_INVOICE_ID ";

    private final InvoiceRowMapper MAPPER = new InvoiceRowMapper();

    @Autowired
    private CstManager cstManager;

    @Autowired
    private Connection connection;

    @Nonnull
    @Override
    public List<Invoice> getInvoiceList() {
        final List<Invoice> results = Lists.newArrayList();
        try (final Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(GET_ALL_INVOICES_SQL_REQUEST)) {
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
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        try {
            try (final PreparedStatement statement = connection.prepareStatement(REMOVE_INVOICE_SQL_REQUEST)) {
                for (final Invoice invoice : invoices) {
                    statement.setInt(1, invoice.getId());
                    statement.addBatch();
                }
                final int[] affectedRows = statement.executeBatch();
                Validate.validateOneRowAffectedInDbCall(affectedRows);
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId(), invoice.getName(), invoice.getCreationDate());

        try {
            try (final PreparedStatement statement = connection.prepareStatement(CREATE_INVOICE_SQL_REQUEST)) {
                statement.setInt(1, invoice.getId());
                statement.setString(2, invoice.getName());
                statement.setInt(3, invoice.getCategory().getId());
                statement.setString(4, invoice.getDetails());
                statement.setDate(5, Date.valueOf(invoice.getCreationDate()));
                statement.setDate(6, Date.valueOf(invoice.getModificationDate()));

                statement.execute();
                Validate.equals(statement.getUpdateCount(), 1, "Should create one row. Found " + statement.getUpdateCount());
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
    }

    @Override
    public int getNextItemId() {
        try (final Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(GET_NEXT_INVOICE_ID_SQL_REQUEST)) {
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

    private class InvoiceRowMapper {
        @Nonnull
        Invoice mapRow(ResultSet resultSet) throws SQLException {
            final Invoice invoice = new Invoice();

            final int id = resultSet.getInt("ID");
            invoice.setId(id);
            invoice.setName(resultSet.getString("NAME"));
            invoice.setCategory(getCategory(resultSet.getObject("CATEGORY_ID", Integer.class)));
            invoice.setDetails(resultSet.getString("DETAILS"));
            invoice.setCreationDate(getLocalDate(resultSet.getDate("CREATION")));
            invoice.setModificationDate(getLocalDate(resultSet.getDate("LAST_MODIFICATION")));

            log.debug("Loaded invoice: {}", invoice);
            return invoice;
        }

        @Nullable
        private Category getCategory(@Nullable final Integer id) {
            return id == null
                    ? null
                    : cstManager.getCategoryById(id);
        }

        @Nullable
        private LocalDate getLocalDate(@Nullable final Date date) {
            return date == null
                    ? null
                    : date.toLocalDate();
        }
    }
}
