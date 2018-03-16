package eu.ba30.re.blocky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Attachment;
import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.InvoiceService;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.db.AttachmentsRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class InvoiceServiceTransactionsTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Test(dataProvider = "failTransactionForCreateDataProvider")
    public void failTransactionForCreate(Invoice invoice) {
        try {
            invoiceService.create(invoice);
            fail("create should not pass!");
        }catch (Exception e) {
            assertEquals(invoiceService.getInvoices().size(), 1);
            assertEquals(attachmentsRepository.getAllAttachments().size(), 3);
        }
    }

    @DataProvider
    private Object[][] failTransactionForCreateDataProvider() {
        final Attachment attachmentWithId = new TestObjectsBuilder().attachment1().buildSingleAttachment();
        final Category category = new TestObjectsBuilder().category1().buildSingleCategory();
        return new Object[][] {
                {null},
                // name is null
                {new Invoice()},
                // fail for id=1
                {createInvoice(1, category, attachmentWithId)},
                // fail for existing attachment
                {createInvoice(null, null, null, null)},
                {createInvoice(null, null, attachmentWithId)},
        };
    }

    private static Invoice createInvoice(Integer id, Category category, Attachment... attachments) {
        final Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setName("Name");
        invoice.setDetails("Details");
        invoice.setCategory(category);
        invoice.setAttachments(Lists.newArrayList(attachments));
        return invoice;
    }
}
