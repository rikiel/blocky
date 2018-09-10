package eu.ba30.re.blocky.service.config.spark.csv;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.ibatis.io.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.spark.csv.SparkCsvCstManagerImpl;

@Configuration
public class SparkCsvServiceTestConfiguration extends AbstractSparkCsvTestConfiguration {
    @Bean
    public CstManager cstManager() {
        return new SparkCsvCstManagerImpl();
    }

    @Bean
    public String categoryCsvFileName() {
        return getAbsolutePath("csv/test-data-cst-category.csv");
    }

    private String getAbsolutePath(String fileName) {
        final File file;
        try {
            file = Resources.getResourceAsFile(fileName);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return file.getAbsolutePath();
    }
}
