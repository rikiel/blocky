package eu.ba30.re.blocky.service.config.mybatis.xml;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import eu.ba30.re.blocky.service.config.mybatis.AbstractMyBatisTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.mybatis.repository.xml" })
@MapperScan("eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper")
abstract class AbstractMyBatisXmlTestConfiguration extends AbstractMyBatisTestConfiguration {
    @Bean
    @Autowired
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-configuration.xml"));
        return sqlSessionFactoryBean;
    }
}
