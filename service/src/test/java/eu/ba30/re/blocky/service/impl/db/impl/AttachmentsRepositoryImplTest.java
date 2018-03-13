package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.RepositoryTestConfiguration;

import static eu.ba30.re.blocky.service.TestUtils.getMockedAttachment;

@ContextConfiguration(classes = { AttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class AttachmentsRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test
    public void getAttachmentList() throws Exception {
    }

    @Test
    public void createAttachments() throws Exception {
        attachmentsRepository.createAttachments(1,
                Lists.newArrayList(getMockedAttachment()));
    }

    @Test
    public void removeAttachments() throws Exception {
    }

    @Configuration
    public static class AttachmentRepositoryConfiguration extends RepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/test-data-attachments.sql");
        }
    }
}