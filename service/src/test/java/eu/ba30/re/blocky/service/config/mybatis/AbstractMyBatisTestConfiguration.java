package eu.ba30.re.blocky.service.config.mybatis;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;
import eu.ba30.re.blocky.service.impl.mybatis.MyBatisCstManagerImpl;
import eu.ba30.re.blocky.service.impl.mybatis.MyBatisInvoiceServiceImpl;

public abstract class AbstractMyBatisTestConfiguration extends AbstractTestConfiguration {
    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Autowired
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public CstManager cstManager() {
        return new MyBatisCstManagerImpl();
    }

    @Bean
    public InvoiceService invoiceService() {
        return new MyBatisInvoiceServiceImpl();
    }
}
