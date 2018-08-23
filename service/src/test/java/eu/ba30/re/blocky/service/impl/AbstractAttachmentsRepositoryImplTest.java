package eu.ba30.re.blocky.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.repository.AttachmentsRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractAttachmentsRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test
    public void getNextItemId() {
        final int sequenceBegin = 10;
        for (int i = 0; i < 100; ++i) {
            assertEquals(attachmentsRepository.getNextAttachmentId(), sequenceBegin + i);
        }
    }

    @Test
    public void getAttachmentsByInvoiceId() {
        assertReflectionEquals(new TestObjectsBuilder().attachment1().buildAttachments(),
                attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1));

        assertReflectionEquals(Lists.newArrayList(),
                attachmentsRepository.getAttachmentsByInvoiceId(999));
    }

    @Test
    public void getAttachmentList() {
        assertReflectionEquals(Lists.newArrayList(new TestObjectsBuilder().attachment1().buildSingleAttachment()),
                attachmentsRepository.getAttachmentList());
    }

    @Test
    public void createAttachmentsForInvoice() {
        attachmentsRepository.createAttachmentsForInvoice(TestObjectsBuilder.INVOICE_ID_1,
                new TestObjectsBuilder().attachment2().attachment3().buildAttachments());

        assertReflectionEquals(new TestObjectsBuilder().attachment1().attachment2().attachment3().buildAttachments(),
                attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1));
    }

    @Test
    public void removeAttachments() {
        attachmentsRepository.removeAttachments(new TestObjectsBuilder().attachment1().buildAttachments());

        assertReflectionEquals(Lists.newArrayList(),
                attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1));
    }

    @Test(dataProvider = "createAttachmentsErrorDataProvider")
    public void createAttachmentsForInvoiceWithError(Attachment toCreate) {
        final List<Attachment> allAttachments = attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1);
        try {
            attachmentsRepository.createAttachmentsForInvoice(1, Lists.newArrayList(toCreate));
            fail("createAttachmentsForInvoice should not pass");
        } catch (Exception e) {
            assertReflectionEquals("Should not create any attachment",
                    allAttachments,
                    attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1));
        }
    }

    @Test(dataProvider = "removeAttachmentsErrorDataProvider")
    public void removeAttachmentsWithError(Attachment toRemove) {
        final List<Attachment> allAttachments = attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1);
        try {
            attachmentsRepository.removeAttachments(Lists.newArrayList(toRemove));
            fail("removeAttachments should not pass!");
        } catch (Exception e) {
            assertReflectionEquals("Should not create any attachment",
                    allAttachments,
                    attachmentsRepository.getAttachmentsByInvoiceId(TestObjectsBuilder.INVOICE_ID_1));
        }
    }

    @DataProvider
    protected Object[][] createAttachmentsErrorDataProvider() {
        return new Object[][] {
                // null values
                { null },
                { new Attachment() },
                // attachments exists in db
                { new TestObjectsBuilder().attachment1().buildSingleAttachment() },
                };
    }

    @DataProvider
    protected Object[][] removeAttachmentsErrorDataProvider() {
        return new Object[][] {
                // null values
                { null },
                { new Attachment() },
                // not exist
                { new TestObjectsBuilder().attachment2().buildSingleAttachment() },
                { new TestObjectsBuilder().attachment3().buildSingleAttachment() },
                };
    }
}