package eu.ba30.re.blocky.service.impl.jdbc.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.jdbc.JdbcRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractAttachmentsRepositoryImplTest;

@ContextConfiguration(classes = { JdbcAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class JdbcAttachmentsRepositoryImplTest extends AbstractAttachmentsRepositoryImplTest {
    @Configuration
    public static class AttachmentRepositoryConfiguration extends JdbcRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}