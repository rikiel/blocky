package eu.ba30.re.blocky.service.config.spark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.impl.spark.SparkTransactionManager;
import eu.ba30.re.blocky.service.impl.spark.repository.aspect.SparkRepositoryTransactionManagerAspect;

@Configuration
public class SparkRepositoryTestConfiguration extends AbstractSparkTestConfiguration {
    @Autowired
    private SparkTransactionManager transactionManager;

    @Bean
    public SparkRepositoryTransactionManagerAspect sparkRepositoryTransactionManagerAspect() {
        return new SparkRepositoryTransactionManagerAspect(transactionManager);
    }
}
