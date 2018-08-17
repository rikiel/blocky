package eu.ba30.re.blocky.service.config.jdbctemplate;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

@Configuration
public class JdbcTemplateServiceTestConfiguration extends AbstractJdbcTemplateTestConfiguration {
    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList(
                "db/test-data-attachments.sql",
                "db/test-data-cst-category.sql",
                "db/test-data-invoices.sql");
    }
}
