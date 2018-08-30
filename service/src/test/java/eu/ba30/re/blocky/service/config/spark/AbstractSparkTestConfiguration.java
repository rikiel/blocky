package eu.ba30.re.blocky.service.config.spark;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.impl.other.cst.CategoryImpl;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;
import eu.ba30.re.blocky.service.impl.spark.coder.AttachmentEncoder;
import eu.ba30.re.blocky.service.impl.spark.coder.InvoiceEncoder;
import eu.ba30.re.blocky.service.impl.spark.model.AttachmentDb;
import eu.ba30.re.blocky.service.impl.spark.model.InvoiceDb;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark" })
abstract class AbstractSparkTestConfiguration extends AbstractTestConfiguration {
    @Autowired
    private InvoiceEncoder invoiceEncoder;
    @Autowired
    private AttachmentEncoder attachmentEncoder;

    private static final TestObjectsBuilder DATA_BUILDER = new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK)
            // build invoices
            .category1()
            .attachment1()
            .invoice1()
            // build categories
            .category1()
            .category2()
            // build attachments
            .attachment1();

    @Bean
    public SparkSession sparkSession() throws AnalysisException {
        final SparkSession sparkSession = SparkSession.builder()
                .appName("Unit tests")
                .master("local")
                .getOrCreate();

        sparkSession
                .createDataFrame(DATA_BUILDER.buildCategories(), CategoryImpl.class)
                .createGlobalTempView("T_CST_CATEGORY");

        sparkSession
                .createDataFrame(attachmentEncoder.encodeAll(TestObjectsBuilder.INVOICE_ID_1, DATA_BUILDER.buildAttachments()), AttachmentDb.class)
                .createGlobalTempView("T_ATTACHMENTS");

        sparkSession
                .createDataFrame(invoiceEncoder.encodeAll(DATA_BUILDER.buildInvoices()), InvoiceDb.class)
                .createGlobalTempView("T_INVOICES");

        return sparkSession;
    }

    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList();
    }
}
