package eu.ba30.re.blocky.service.config.hibernate;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

@Configuration
public class HibernateServiceTestConfiguration extends AbstractHibernateTestConfiguration {
    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList(
                "db/test-data-attachments.sql",
                "db/test-data-cst-category.sql",
                "db/test-data-invoices.sql");
    }
}
