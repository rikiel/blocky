package eu.ba30.re.blocky.service.config.mybatis.annotation;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.service.config.mybatis.AbstractMyBatisTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.mybatis.repository.annotation" })
@MapperScan("eu.ba30.re.blocky.service.impl.mybatis.repository.annotation.mapper")
abstract class AbstractMyBatisAnnotationTestConfiguration extends AbstractMyBatisTestConfiguration {
    @Bean
    @Autowired
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        final Configuration configuration = new Configuration();
        // Bez tohoto 2x po sebe vykonane selecty pocas 1 transakcie vzdy vratia rovnaku hodnotu z cache
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean;
    }
}
