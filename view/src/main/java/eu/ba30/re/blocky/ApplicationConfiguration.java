package eu.ba30.re.blocky;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import eu.ba30.re.blocky.common.spring.CommonSpringApplicationConfiguration;
import eu.ba30.re.blocky.model.spring.ModelSpringApplicationConfiguration;
import eu.ba30.re.blocky.service.spring.ServiceSpringApplicationConfiguration;
import eu.ba30.re.blocky.view.spring.ViewSpringApplicationConfiguration;

@Configuration
@ComponentScan({
        "eu.ba30.re.blocky.service.mock",
})
@Import({
        CommonSpringApplicationConfiguration.class,
        ModelSpringApplicationConfiguration.class,
        ServiceSpringApplicationConfiguration.class,
        ViewSpringApplicationConfiguration.class,

})
public class ApplicationConfiguration {
}