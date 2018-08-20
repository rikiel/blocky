package eu.ba30.re.blocky.service.impl;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;

import eu.ba30.re.blocky.utils.Validate;

@Component
public class MockDb {
    private static final String INSERT_ATTACHMENT_SQL_REQUEST = ""
                                                                + " INSERT INTO T_ATTACHMENTS "
                                                                + " (ID, INVOICE_ID, NAME, FILE_NAME, MIME_TYPE, TYPE, FILE_CONTENT) "
                                                                + " VALUES (?, ?, ?, ?, ?, ?, ?)";


    private static final String INSERT_INVOICE_SQL_REQUEST = ""
                                                          + " INSERT INTO T_INVOICES "
                                                          + " (ID, NAME, CATEGORY_ID, DETAILS, CREATION, LAST_MODIFICATION) "
                                                          + " VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String INSERT_CATEGORY_SQL_REQUEST = ""
                                                              + " INSERT INTO T_CST_CATEGORY "
                                                              + " (ID, NAME, DESCR) "
                                                              + " VALUES (?, ?, ?)";

    private final JdbcTemplate jdbc;

    @Value("classpath:attachments/img-1.jpg")
    private Resource attachment1;
    @Value("classpath:attachments/img-2.jpg")
    private Resource attachment2;
    @Value("classpath:attachments/nakupnyZoznam")
    private Resource attachment3;

    public MockDb() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScripts(
                "db/mock-data-db-schema.sql"
        );
        jdbc = new JdbcTemplate(builder.build());
    }

    @Nonnull
    public JdbcTemplate getJdbcTemplate() {
        return jdbc;
    }

    public void initDb() {
        addCstCategories();
        addInvoices();
        addAttachments();
    }

    private void addCstCategories() {
        jdbc.update(INSERT_CATEGORY_SQL_REQUEST,
                1, "Potraviny", "Nákup potravín");

        jdbc.update(INSERT_CATEGORY_SQL_REQUEST,
                2, "Drogéria", "Nákup drogérie");

        jdbc.update(INSERT_CATEGORY_SQL_REQUEST,
                3, "Mobilný operátor", "Dobitie kreditu, platba za paušál");

        jdbc.update(INSERT_CATEGORY_SQL_REQUEST,
                4, "Ostatné", "Rôzne");
    }

    private void addInvoices() {
        jdbc.update(INSERT_INVOICE_SQL_REQUEST,
                1, "Darčeky na vianoce", 4, "Vianoce 2017", Date.valueOf(LocalDate.parse("2017-12-20")), Date.valueOf(LocalDate.parse("2017-12-22")));

        jdbc.update(INSERT_INVOICE_SQL_REQUEST,
                2, "Dobitie kreditu", 3, "Vodafone", Date.valueOf(LocalDate.parse("2018-03-25")), Date.valueOf(LocalDate.parse("2018-03-25")));

        for (int i = 3; i < 10; ++i) {
            LocalDate date = LocalDate.parse("2018-01-15").plusWeeks(i);
            jdbc.update(INSERT_INVOICE_SQL_REQUEST,
                    i, "Albert", 1, "Potraviny", Date.valueOf(date), Date.valueOf(date));
        }
    }

    private void addAttachments() {
        jdbc.update(INSERT_ATTACHMENT_SQL_REQUEST,
                1,
                1,
                "Západ slnka - 1",
                attachment1.getFilename(),
                "image/jpeg",
                1,
                readInput(attachment1));
        jdbc.update(INSERT_ATTACHMENT_SQL_REQUEST,
                2,
                2,
                "Západ slnka - 2",
                attachment1.getFilename(),
                "image/jpeg",
                1,
                readInput(attachment2));

        jdbc.update(INSERT_ATTACHMENT_SQL_REQUEST,
                3,
                1,
                "Nákupný zoznam",
                attachment1.getFilename(),
                "text/text",
                3,
                readInput(attachment3));
    }

    @Nonnull
    private byte[] readInput(@Nonnull final Resource resource) {
        try {
            return ByteStreams.toByteArray(resource.getInputStream());
        } catch (IOException e) {
            Validate.fail(e);
            return null;
        }
    }
}
