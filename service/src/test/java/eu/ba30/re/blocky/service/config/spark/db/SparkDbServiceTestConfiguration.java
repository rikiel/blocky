package eu.ba30.re.blocky.service.config.spark.db;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbCstManagerImpl;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbInvoiceServiceImpl;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark.common.aspect" })
public class SparkDbServiceTestConfiguration extends AbstractSparkDbTestConfiguration {
    @Bean
    public CstManager cstManager() {
        return new SparkDbCstManagerImpl();
    }

    @Bean
    public InvoiceService invoiceService() {
        return new SparkDbInvoiceServiceImpl();
    }

    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList(
                "db/test-data-attachments.sql",
                "db/test-data-cst-category.sql",
                "db/test-data-invoices.sql");
    }
}
