package eu.ba30.re.blocky.service.config.spark.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.impl.spark.db.SparkDbTransactionManager;
import eu.ba30.re.blocky.service.impl.spark.db.repositorytest.SparkRepositoryTransactionManagerAspect;

@Configuration
public abstract class SparkDbRepositoryTestConfiguration extends AbstractSparkDbTestConfiguration {
    @Autowired
    private SparkDbTransactionManager transactionManager;

    @Bean
    public SparkRepositoryTransactionManagerAspect sparkRepositoryTransactionManagerAspect() {
        return new SparkRepositoryTransactionManagerAspect(transactionManager);
    }
}
