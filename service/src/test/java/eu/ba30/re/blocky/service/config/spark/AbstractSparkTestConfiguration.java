package eu.ba30.re.blocky.service.config.spark;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.model.impl.other.cst.CategoryImpl;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentDb;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentEncoder;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceDb;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceEncoder;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark" })
abstract class AbstractSparkTestConfiguration extends AbstractTestConfiguration {
    @Autowired
    private InvoiceEncoder invoiceEncoder;
    @Autowired
    private AttachmentEncoder attachmentEncoder;

    @Bean
    public SparkSession sparkSession() throws AnalysisException {
        final SparkSession sparkSession = SparkSession.builder()
                .appName("Unit tests")
                .master("local")
                .getOrCreate();

        final TestObjectsBuilder builder = new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK)
                // build invoices
                .category1()
                .attachment1()
                .invoice1()
                // build categories
                .category1()
                .category2()
                // build attachments
                .attachment1();

        sparkSession
                .createDataFrame(builder.buildCategories(), CategoryImpl.class)
                .createGlobalTempView("T_CST_CATEGORY");

        sparkSession
                .createDataFrame(attachmentEncoder.encodeAll(builder.buildAttachments()), AttachmentDb.class)
                .createGlobalTempView("T_ATTACHMENTS");

        sparkSession
                .createDataFrame(invoiceEncoder.encodeAll(builder.buildInvoices()), InvoiceDb.class)
                .createGlobalTempView("T_INVOICES");

        return sparkSession;
    }
}
