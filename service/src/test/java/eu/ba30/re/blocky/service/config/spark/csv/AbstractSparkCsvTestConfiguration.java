package eu.ba30.re.blocky.service.config.spark.csv;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.ibatis.io.Resources;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@ComponentScan(value = { "eu.ba30.re.blocky.service.impl.spark.common" }, lazyInit = true)
abstract class AbstractSparkCsvTestConfiguration extends AbstractTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AbstractSparkCsvTestConfiguration.class);

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
    protected final List<String> getSqlScripts() {
        throw new UnsupportedOperationException("CSV Spark does not use SQL");
    }

    @Nonnull
    protected String copyResourceToFile(@Nonnull String fileName) {
        try {
            // copy to temp file as we do not want to change original resource file in tests
            final File file = Resources.getResourceAsFile(fileName);
            final Path tempFile = Files.createTempFile(file.getName(), ".tmp.csv");
            Files.copy(file.toPath(), tempFile.toAbsolutePath(), REPLACE_EXISTING);
            final String tempAbsolutePath = tempFile.toFile().getAbsolutePath();
            log.debug("Using temporary file {} instead of using raw resource file {}", tempAbsolutePath, fileName);
            return tempAbsolutePath;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
