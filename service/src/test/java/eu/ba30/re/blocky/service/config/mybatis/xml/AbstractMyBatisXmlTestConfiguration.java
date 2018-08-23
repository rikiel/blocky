package eu.ba30.re.blocky.service.config.mybatis.xml;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;
import eu.ba30.re.blocky.service.impl.mybatis.MyBatisCstManagerImpl;
import eu.ba30.re.blocky.service.impl.mybatis.MyBatisInvoiceServiceImpl;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.mybatis.repository.xml" })
@MapperScan("eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper")
abstract class AbstractMyBatisXmlTestConfiguration extends AbstractTestConfiguration {
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
    @Autowired
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-configuration.xml"));
        return sqlSessionFactoryBean;
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
