package eu.ba30.re.blocky.service.config.spark;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.impl.spark.SparkCstManagerImpl;
import eu.ba30.re.blocky.service.impl.spark.SparkInvoiceServiceImpl;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark.aspect" })
public class SparkServiceTestConfiguration extends AbstractSparkTestConfiguration {
    @Bean
    public CstManager cstManager() {
        return new SparkCstManagerImpl();
    }

    @Bean
    public InvoiceService invoiceService() {
        return new SparkInvoiceServiceImpl();
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
