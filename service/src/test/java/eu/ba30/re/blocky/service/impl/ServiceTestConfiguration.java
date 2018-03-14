package eu.ba30.re.blocky.service.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.AbstractTestConfiguration;

@Configuration
public class ServiceTestConfiguration extends AbstractTestConfiguration {
    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList("db/test-data-attachments.sql",
                "db/test-data-cst-category.sql",
                "db/test-data-invoices.sql");
    }
}
