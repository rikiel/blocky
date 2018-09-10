package eu.ba30.re.blocky.service.config.spark.csv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.spark.common.mapper.SparkAttachmentMapper;
import eu.ba30.re.blocky.service.impl.spark.csv.SparkCsvCstManagerImpl;

@Configuration
@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark.csv" })
public class SparkCsvServiceTestConfiguration extends AbstractSparkCsvTestConfiguration {
    @Bean
    public CstManager cstManager() {
        return new SparkCsvCstManagerImpl();
    }

    @Bean
    public String categoryCsvFileName() {
        return copyResourceToFile("csv/test-data-cst-category.csv");
    }

    @Bean
    public SparkAttachmentMapper.ContentType contentType() {
        return SparkAttachmentMapper.ContentType.STRING;
    }
}
