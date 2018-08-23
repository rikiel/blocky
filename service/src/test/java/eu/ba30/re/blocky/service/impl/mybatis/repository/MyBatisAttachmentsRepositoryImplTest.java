package eu.ba30.re.blocky.service.impl.mybatis.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.mybatis.MyBatisRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractAttachmentsRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class MyBatisAttachmentsRepositoryImplTest extends AbstractAttachmentsRepositoryImplTest {
    @Configuration
    public static class AttachmentRepositoryConfiguration extends MyBatisRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}