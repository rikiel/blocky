package eu.ba30.re.blocky.service.jdbctemplate.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.jdbctemplate.impl.db.JdbcTemplateAttachmentsRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { JdbcTemplateAttachmentsRepositoryImplTest.AttachmentRepositoryConfiguration.class })
public class JdbcTemplateAttachmentsRepositoryImplTest extends AbstractTestNGSpringContextTests {
    private static final int INVOICE_ID = 1;

    @Autowired
    private JdbcTemplateAttachmentsRepository attachmentsRepository;

    @Test
    public void getNextItemId() {
        assertEquals(attachmentsRepository.getNextItemId(), 10);
        assertEquals(attachmentsRepository.getNextItemId(), 11);
    }

    @Test(priority = 1)
    public void getAttachmentList() {
        assertReflectionEquals(new TestObjectsBuilder().attachment1().buildAttachments(),
                attachmentsRepository.getAttachmentList(INVOICE_ID));
    }

    @Test(priority = 1)
    public void getAttachmentListEmpty() {
        assertReflectionEquals(Lists.newArrayList(),
                attachmentsRepository.getAttachmentList(999));
    }

    @Test(priority = 1)
    public void getAttachmentWithInvoiceIdList() {
        assertReflectionEquals(Lists.newArrayList(new TestObjectsBuilder().attachment1().buildSingleAttachment()),
                attachmentsRepository.getAllAttachments());
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

    @Test(priority = 4,
            dataProvider = "createAttachmentsErrorDataProvider")
    public void createAttachmentsError(Attachment toCreate) {
        final List<Attachment> allAttachments = attachmentsRepository.getAttachmentList(1);
        try {
            attachmentsRepository.createAttachments(1, Lists.newArrayList(toCreate));
            fail("createAttachments should not pass");
        } catch (Exception e) {
            assertReflectionEquals("Should not create any attachment",
                    allAttachments,
                    attachmentsRepository.getAttachmentList(1));
        }
    }

    @Test(priority = 4,
            dataProvider = "removeAttachmentsErrorDataProvider")
    public void removeAttachmentsError(Attachment toRemove) {
        final List<Attachment> allAttachments = attachmentsRepository.getAttachmentList(1);
        try {
            attachmentsRepository.removeAttachments(Lists.newArrayList(toRemove));
            fail("removeAttachments should not pass!");
        } catch (Exception e) {
            assertReflectionEquals("Should not create any attachment",
                    allAttachments,
                    attachmentsRepository.getAttachmentList(1));
        }
    }

    @DataProvider
    private Object[][] createAttachmentsErrorDataProvider() {
        return new Object[][] {
                // null values
                { null },
                { new Attachment() },
                // attachments exists in db
                { new TestObjectsBuilder().attachment1().buildSingleAttachment() },
                };
    }

    @DataProvider
    private Object[][] removeAttachmentsErrorDataProvider() {
        return new Object[][] {
                // null values
                { null },
                { new Attachment() },
                // not exist
                { new TestObjectsBuilder().attachment2().buildSingleAttachment() },
                { new TestObjectsBuilder().attachment3().buildSingleAttachment() },
                };
    }

    @Configuration
    public static class AttachmentRepositoryConfiguration extends JdbcTemplateRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-attachments.sql");
        }
    }
}