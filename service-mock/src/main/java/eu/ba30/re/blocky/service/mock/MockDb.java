package eu.ba30.re.blocky.service.mock;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;

import eu.ba30.re.blocky.common.utils.Validate;

@Component
public class MockDb {
    private static final Logger log = LoggerFactory.getLogger(MockDb.class);

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

    private JdbcTemplate jdbcTemplate;

    @Value("classpath:attachments/img-1.jpg")
    private Resource attachment1;
    @Value("classpath:attachments/img-2.jpg")
    private Resource attachment2;
    @Value("classpath:attachments/nakupnyZoznam")
    private Resource attachment3;

    @PostConstruct
    private void init() {
        log.info("Loading DB schema");

        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScripts(
                "db/mock-data-db-schema.sql"
        );
        jdbcTemplate = new JdbcTemplate(builder.build());
        initDb();
    }

    @Nonnull
    JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private void initDb() {
        log.info("Init DB");
        addCstCategories();
        addInvoices();
        addAttachments();
    }

    private void addCstCategories() {
        log.info("Adding default cst categories to DB");

        jdbcTemplate.update(INSERT_CATEGORY_SQL_REQUEST,
                1, "Potraviny", "Nákup potravín");

        jdbcTemplate.update(INSERT_CATEGORY_SQL_REQUEST,
                2, "Drogéria", "Nákup drogérie");

        jdbcTemplate.update(INSERT_CATEGORY_SQL_REQUEST,
                3, "Mobilný operátor", "Dobitie kreditu, platba za paušál");

        jdbcTemplate.update(INSERT_CATEGORY_SQL_REQUEST,
                4, "Ostatné", "Rôzne");
    }

    private void addInvoices() {
        log.debug("Adding default invoices to DB");

        jdbcTemplate.update(INSERT_INVOICE_SQL_REQUEST,
                1, "Darčeky na vianoce", 4, "Vianoce 2017", Date.valueOf(LocalDate.parse("2017-12-20")), Date.valueOf(LocalDate.parse("2017-12-22")));

        jdbcTemplate.update(INSERT_INVOICE_SQL_REQUEST,
                2, "Dobitie kreditu", 3, "Vodafone", Date.valueOf(LocalDate.parse("2018-03-25")), Date.valueOf(LocalDate.parse("2018-03-25")));

        for (int i = 3; i < 10; ++i) {
            LocalDate date = LocalDate.parse("2018-01-15").plusWeeks(i);
            jdbcTemplate.update(INSERT_INVOICE_SQL_REQUEST,
                    i, "Albert", 1, "Potraviny", Date.valueOf(date), Date.valueOf(date));
        }
    }

    private void addAttachments() {
        log.debug("Adding default attachments to DB");

        jdbcTemplate.update(INSERT_ATTACHMENT_SQL_REQUEST,
                1,
                1,
                "Západ slnka - 1",
                attachment1.getFilename(),
                "image/jpeg",
                1,
                readInput(attachment1));
        jdbcTemplate.update(INSERT_ATTACHMENT_SQL_REQUEST,
                2,
                2,
                "Západ slnka - 2",
                attachment1.getFilename(),
                "image/jpeg",
                1,
                readInput(attachment2));

        jdbcTemplate.update(INSERT_ATTACHMENT_SQL_REQUEST,
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
