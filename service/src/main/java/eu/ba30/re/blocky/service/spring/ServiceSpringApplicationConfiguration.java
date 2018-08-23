package eu.ba30.re.blocky.service.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.impl.jdbctemplate" })
@MapperScan({ "eu.ba30.re.blocky.service.impl.mybatis.repository.mapper" })
public class ServiceSpringApplicationConfiguration {
}
