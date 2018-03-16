package eu.ba30.re.blocky.service.impl.db.impl;

import java.sql.Date;
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

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.db.InvoiceRepository;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(InvoiceRepositoryImpl.class);

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
                    return new Object[]{invoice.getId()};
                })
                .collect(Collectors.toList());

        final int[] removedPerItem = jdbc.batchUpdate(REMOVE_INVOICE_SQL_REQUEST, invoiceIds);

        Validate.validateOneRowAffectedInDbCall(removedPerItem);
    }

    @Override
    public void create(@Nonnull final Invoice invoice) {
        Validate.notNull(invoice);
        Validate.notNull(invoice.getId());

        final int created = jdbc.update(CREATE_INVOICE_SQL_REQUEST,
                invoice.getId(),
                invoice.getName(),
                invoice.getCategory() == null
                        ? null
                        : invoice.getCategory().getId(),
                invoice.getDetails(),
                Date.valueOf(invoice.getCreationDate()),
                Date.valueOf(invoice.getModificationDate()));

        Validate.validateOneRowAffectedInDbCall(new int[]{created});
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
            invoice.setCategory(cstManager.getCategory(resultSet.getInt("CATEGORY_ID")));
            invoice.setDetails(resultSet.getString("DETAILS"));
            invoice.setCreationDate(resultSet.getDate("CREATION").toLocalDate());
            invoice.setModificationDate(resultSet.getDate("LAST_MODIFICATION").toLocalDate());

            log.debug("Loaded invoice: {}", invoice);
            return invoice;
        }
    }
}
