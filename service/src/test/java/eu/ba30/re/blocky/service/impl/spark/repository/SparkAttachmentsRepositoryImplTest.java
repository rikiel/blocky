package eu.ba30.re.blocky.service.impl.spark.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.SparkRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractAttachmentsRepositoryImplTest;
import eu.ba30.re.blocky.service.impl.spark.SparkTransactionManager;

@ContextConfiguration(classes = { SparkAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class SparkAttachmentsRepositoryImplTest extends AbstractAttachmentsRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }

    @Configuration
    public static class AttachmentRepositoryConfiguration extends SparkRepositoryTestConfiguration {
        // TODO BLOCKY-16 beany cez komponent scan
        @Bean
        public SparkAttachmentsRepositoryImpl attachmentsRepository() {
            return new SparkAttachmentsRepositoryImpl();
        }

        @Bean
        public SparkTransactionManager sparkTransactionManager() {
            return new SparkTransactionManager();
        }

        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}
