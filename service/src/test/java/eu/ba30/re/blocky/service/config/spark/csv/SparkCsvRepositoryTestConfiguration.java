package eu.ba30.re.blocky.service.config.spark.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.impl.spark.SparkRepositoryTransactionManagerAspect;
import eu.ba30.re.blocky.service.impl.spark.common.SparkTransactionManager;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark.csv.repository" })
public abstract class SparkCsvRepositoryTestConfiguration extends AbstractSparkCsvTestConfiguration {
    @Autowired
    private SparkTransactionManager transactionManager;

    @Bean
    public SparkRepositoryTransactionManagerAspect sparkRepositoryTransactionManagerAspect() {
        return new SparkRepositoryTransactionManagerAspect(transactionManager);
    }
}
