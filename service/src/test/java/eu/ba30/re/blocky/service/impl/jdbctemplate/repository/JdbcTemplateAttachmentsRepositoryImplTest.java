package eu.ba30.re.blocky.service.impl.jdbctemplate.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractAttachmentsRepositoryImplTest;

@ContextConfiguration(classes = { JdbcTemplateAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class JdbcTemplateAttachmentsRepositoryImplTest extends AbstractAttachmentsRepositoryImplTest {
    @Configuration
    public static class AttachmentRepositoryConfiguration extends JdbcTemplateRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}