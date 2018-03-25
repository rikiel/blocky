package eu.ba30.re.blocky.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;

import javax.annotation.Nonnull;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import eu.ba30.re.blocky.utils.Validate;

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

    public MockDb() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScripts(
                "db/mock-data-db-schema.sql"
        );
        jdbc = new JdbcTemplate(builder.build());
        initDb();
    }

    @Nonnull
    public JdbcTemplate getJdbcTemplate() {
        return jdbc;
    }

    private void initDb() {
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
                2, "Dobitie kreditu", 3, "Vodafone", Date.valueOf(LocalDate.parse("2018-03-25")), null);

        for (int i = 0; i < 5; ++i) {
            LocalDate date = LocalDate.parse("2018-01-15").plusWeeks(i);
            jdbc.update(INSERT_INVOICE_SQL_REQUEST,
                    i + 10, "Albert", 1, "Potraviny", Date.valueOf(date), null);
        }
    }

    private void addAttachments() {
        for (int i = 1; i <= 2; ++i) {
            final String fileName = String.format("img-%d.jpg", i);

            jdbc.update(INSERT_ATTACHMENT_SQL_REQUEST,
                    i,
                    i,
                    "Západ slnka - " + i,
                    fileName,
                    "image/jpeg",
                    1,
                    readImage("attachments/" + fileName));
        }
    }

    @Nonnull
    private byte[] readImage(@Nonnull final String resourceFileName) {
        final String resourceFile = getClass().getClassLoader().getResource(resourceFileName).getFile();
        try {
            return Files.readAllBytes(new File(resourceFile).toPath());
        } catch (IOException e) {
            Validate.fail(e);
            return null;
        }
    }
}
