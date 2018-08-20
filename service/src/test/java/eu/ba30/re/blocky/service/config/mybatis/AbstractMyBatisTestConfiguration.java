package eu.ba30.re.blocky.service.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.mybatis" })
@MapperScan("eu.ba30.re.blocky.service.impl.mybatis.db.impl.mapper")
abstract class AbstractMyBatisTestConfiguration extends AbstractTestConfiguration {
}
