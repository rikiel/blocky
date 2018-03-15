package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.RepositoryTestConfiguration;

import static org.testng.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { AttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class AttachmentsRepositoryImplTest extends AbstractTestNGSpringContextTests {
    private static final int INVOICE_ID = 1;

    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test(priority = 1)
    public void getAttachmentList() {
        assertReflectionEquals(new TestObjectsBuilder().attachment1().buildAttachments(),
                attachmentsRepository.getAttachmentList(INVOICE_ID));
    }

    @Test(priority = 2)
    public void createAttachments() {
        attachmentsRepository.createAttachments(INVOICE_ID,
                new TestObjectsBuilder().attachment2().attachment3().buildAttachments());

        assertReflectionEquals(new TestObjectsBuilder().attachment1().attachment2().attachment3().buildAttachments(),
                attachmentsRepository.getAttachmentList(INVOICE_ID));
    }

    @Test(priority = 3)
    public void removeAttachments() {
        attachmentsRepository.removeAttachments(new TestObjectsBuilder().attachment2().attachment3().buildAttachments());

        assertReflectionEquals(new TestObjectsBuilder().attachment1().buildAttachments(),
                attachmentsRepository.getAttachmentList(INVOICE_ID));
    }

    @Test
    public void getNextItemId() {
        assertEquals(attachmentsRepository.getNextItemId(), 10);
        assertEquals(attachmentsRepository.getNextItemId(), 11);
    }

    @Configuration
    public static class AttachmentRepositoryConfiguration extends RepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}