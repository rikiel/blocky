package eu.ba30.re.blocky.service.config.jdbc;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

@Configuration
public class JdbcServiceTestConfiguration extends AbstractJdbcTestConfiguration {
    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList(
                "db/test-data-attachments.sql",
                "db/test-data-cst-category.sql",
                "db/test-data-invoices.sql");
    }
}
