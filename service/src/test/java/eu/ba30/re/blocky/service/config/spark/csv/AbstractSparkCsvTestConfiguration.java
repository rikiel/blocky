package eu.ba30.re.blocky.service.config.spark.csv;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

@ComponentScan({
        "eu.ba30.re.blocky.service.impl.spark.csv",
        "eu.ba30.re.blocky.service.impl.spark.mapper"
})
abstract class AbstractSparkCsvTestConfiguration extends AbstractTestConfiguration {
    @Bean
    @Autowired
    public SparkSession sparkSession() {
        return SparkSession.builder()
                .appName("Unit tests")
                .master("local")
                .getOrCreate();
    }

    @Nonnull
    @Override
    protected List<String> getSqlScripts() {
        return Lists.newArrayList();
    }
}
