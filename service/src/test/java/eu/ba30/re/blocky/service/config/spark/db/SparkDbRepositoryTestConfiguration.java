package eu.ba30.re.blocky.service.config.spark.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.impl.spark.SparkRepositoryTransactionManagerAspect;
import eu.ba30.re.blocky.service.impl.spark.common.SparkTransactionManager;

@Configuration
public abstract class SparkDbRepositoryTestConfiguration extends AbstractSparkDbTestConfiguration {
    @Autowired
    private SparkTransactionManager transactionManager;

    @Bean
    public SparkRepositoryTransactionManagerAspect sparkRepositoryTransactionManagerAspect() {
        return new SparkRepositoryTransactionManagerAspect(transactionManager);
    }
}