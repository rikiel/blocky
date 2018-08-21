package eu.ba30.re.blocky.service.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.impl.jdbctemplate" })
public class ServiceSpringApplicationConfiguration {
}
