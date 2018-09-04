package eu.ba30.re.blocky.service.config.spark;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

// TODO BLOCKY-16 beany cez komponent scan
//@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark" })
abstract class AbstractSparkTestConfiguration extends AbstractTestConfiguration {
    private static final String EMBEDDED_DATABASE_URL = "jdbc:hsqldb:mem:testdb";

    @Bean
    @Autowired
    public SparkSession sparkSession(DataSource dataSource /* need to be initialized before method runs */,
                                     String jdbcConnectionUrl,
                                     Properties jdbcConnectionProperties) throws AnalysisException {
        JdbcDialects.registerDialect(new HsqlDbDialect());

        final SparkSession sparkSession = SparkSession.builder()
                .appName("Unit tests")
                .master("local")
                .getOrCreate();

        sparkSession
                .read()
                .jdbc(jdbcConnectionUrl, "T_CST_CATEGORY", jdbcConnectionProperties)
                .createGlobalTempView("T_CST_CATEGORY");

        //        sparkSession
        //                .createDataFrame(attachmentEncoder.encodeAll(TestObjectsBuilder.INVOICE_ID_1, DATA_BUILDER.buildAttachments()), AttachmentDb.class)
        //                .createGlobalTempView("T_ATTACHMENTS");
        //
        //        sparkSession
        //                .createDataFrame(invoiceEncoder.encodeAll(DATA_BUILDER.buildInvoices()), InvoiceDb.class)
        //                .createGlobalTempView("T_INVOICES");

        sparkSession
                .read()
                .jdbc(jdbcConnectionUrl, "T_ATTACHMENTS", jdbcConnectionProperties)
                .createGlobalTempView("T_ATTACHMENTS");

        sparkSession
                .read()
                .jdbc(jdbcConnectionUrl, "T_INVOICES", jdbcConnectionProperties)
                .createGlobalTempView("T_INVOICES");

        return sparkSession;
    }

    @Bean
    public String jdbcConnectionUrl() {
        return EMBEDDED_DATABASE_URL;
    }

    @Bean
    public Properties jdbcConnectionProperties() {
        return new Properties();
    }
}
