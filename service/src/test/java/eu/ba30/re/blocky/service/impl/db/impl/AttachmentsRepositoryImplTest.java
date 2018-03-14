package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;
import eu.ba30.re.blocky.service.impl.db.RepositoryTestConfiguration;

import static eu.ba30.re.blocky.service.TestUtils.getDbAttachment;
import static eu.ba30.re.blocky.service.TestUtils.getMockedAttachment2;
import static eu.ba30.re.blocky.service.TestUtils.getMockedAttachment3;
import static org.testng.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { AttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class AttachmentsRepositoryImplTest extends AbstractTestNGSpringContextTests {
    private static final int INVOICE_ID = 1;

    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test(priority = 1)
    public void getAttachmentList() {
        final List<Attachment> attachmentList = attachmentsRepository.getAttachmentList(INVOICE_ID);

        assertReflectionEquals(Lists.newArrayList(getDbAttachment()), attachmentList);
    }

    @Test(priority = 2)
    public void createAttachments() {
        attachmentsRepository.createAttachments(INVOICE_ID,
                Lists.newArrayList(getMockedAttachment2(), getMockedAttachment3()));

        assertReflectionEquals(Lists.newArrayList(getDbAttachment(), getMockedAttachment2(), getMockedAttachment3()),
                attachmentsRepository.getAttachmentList(INVOICE_ID));
    }

    @Test(priority = 3)
    public void removeAttachments() {
        attachmentsRepository.removeAttachments(Lists.newArrayList(getMockedAttachment2(), getMockedAttachment3()));

        assertReflectionEquals(Lists.newArrayList(getDbAttachment()),
                attachmentsRepository.getAttachmentList(INVOICE_ID));
    }

    @Test
    public void getNextItemId() {
        assertEquals(attachmentsRepository.getNextItemId(), 1);
        assertEquals(attachmentsRepository.getNextItemId(), 2);
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