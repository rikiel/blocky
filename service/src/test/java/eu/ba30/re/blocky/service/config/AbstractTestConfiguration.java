package eu.ba30.re.blocky.service.config;

import java.util.List;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({"eu.ba30.re.blocky.aspects" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public abstract class AbstractTestConfiguration {
    @Nonnull
    protected abstract List<String> getSqlScripts();

    @Bean
    public DataSource dataSource() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.addScript("db/test-data-db-schema.sql");
        getSqlScripts().forEach(builder::addScript);
        return builder.build();
    }
}
