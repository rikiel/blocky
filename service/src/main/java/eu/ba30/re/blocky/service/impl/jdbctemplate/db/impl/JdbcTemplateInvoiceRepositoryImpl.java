package eu.ba30.re.blocky.service.impl.jdbctemplate.db.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.jdbctemplate.db.JdbcTemplateInvoiceRepository;

@Service
public class JdbcTemplateInvoiceRepositoryImpl implements JdbcTemplateInvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateInvoiceRepositoryImpl.class);

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

    @Autowired
    private CstManager cstManager;

    @Autowired
    private JdbcTemplate jdbc;

    @Nonnull
    @Override
    public List<Invoice> getInvoices() {
        return jdbc.query(GET_ALL_INVOICES_SQL_REQUEST, new InvoiceRowMapper());
    }

    @Override
    public void remove(@Nonnull final List<Invoice> invoices) {
        Validate.notEmpty(invoices);

        final List<Object[]> invoiceIds = invoices
                .stream()
                .map(invoice -> {
                    Validate.notNull(invoice.getId());
                    return new Object[] { invoice.getId() };
                })
                .collect(Collectors.toList());

        final int[] removedPerItem = jdbc.batchUpdate(REMOVE_INVOICE_SQL_REQUEST, invoiceIds);

        Validate.validateOneRowAffectedInDbCall(removedPerItem);
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId(), invoice.getName(), invoice.getCreationDate());

        final int created = jdbc.update(CREATE_INVOICE_SQL_REQUEST,
                invoice.getId(),
                invoice.getName(),
                invoice.getCategory() == null
                        ? null
                        : invoice.getCategory().getId(),
                invoice.getDetails(),
                Date.valueOf(invoice.getCreationDate()),
                invoice.getModificationDate() == null
                        ? null
                        : Date.valueOf(invoice.getModificationDate()));

        Validate.validateOneRowAffectedInDbCall(new int[] { created });
    }

    @Override
    public int getNextItemId() {
        return jdbc.queryForObject(GET_NEXT_INVOICE_ID_SQL_REQUEST, Integer.class);
    }

    private class InvoiceRowMapper implements RowMapper<Invoice> {
        @Override
        public Invoice mapRow(ResultSet resultSet, int i) throws SQLException {
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
                    : cstManager.getCategory(id);
        }

        @Nullable
        private LocalDate getLocalDate(@Nullable final Date date) {
            return date == null
                    ? null
                    : date.toLocalDate();
        }
    }
}
