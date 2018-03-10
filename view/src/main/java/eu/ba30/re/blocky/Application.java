package eu.ba30.re.blocky;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("eu.ba30.re.blocky")
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String... args) {
        log.debug("main");
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() {
        return (args) -> {
            log.debug("run");
        };
    }
}
