package eu.ba30.re.blocky.service.impl.spark.csv.repositorytest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.csv.SparkCsvRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractAttachmentsRepositoryImplTest;
import eu.ba30.re.blocky.service.impl.spark.common.mapper.SparkAttachmentMapper;

@ContextConfiguration(classes = { SparkCsvAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class SparkCsvAttachmentsRepositoryImplTest extends AbstractAttachmentsRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }

    @Configuration
    public static class AttachmentRepositoryConfiguration extends SparkCsvRepositoryTestConfiguration {
        @Bean
        public String attachmentCsvFileName() {
            return copyResourceToFile("csv/test-data-attachments.csv");
        }

        @Bean
        public SparkAttachmentMapper.ContentType contentType() {
            return SparkAttachmentMapper.ContentType.STRING;
        }
    }
}
