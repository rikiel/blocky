package eu.ba30.re.blocky.service.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.mybatis" })
@MapperScan("eu.ba30.re.blocky.service.mybatis.impl.db.impl.mapper")
abstract class AbstractMyBatisTestConfiguration extends AbstractTestConfiguration {
}
