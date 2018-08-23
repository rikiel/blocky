package eu.ba30.re.blocky.service.impl.mybatis.repository.annotation;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.mybatis.annotation.MyBatisRepositoryAnnotationTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractAttachmentsRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisAnnotationAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class MyBatisAnnotationAttachmentsRepositoryImplTest extends AbstractAttachmentsRepositoryImplTest {
    @Configuration
    public static class AttachmentRepositoryConfiguration extends MyBatisRepositoryAnnotationTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}