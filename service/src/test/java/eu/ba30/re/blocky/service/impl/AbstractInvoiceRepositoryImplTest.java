package eu.ba30.re.blocky.service.impl;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.repository.InvoiceRepository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractInvoiceRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private InvoiceRepository invoiceRepository;

    protected abstract TestObjectsBuilder createBuilder();

    @Test
    public void getNextItemId() {
        final int sequenceBegin = 10;
        for (int i = 0; i < 100; ++i) {
            assertEquals(invoiceRepository.getNextInvoiceId(), sequenceBegin + i);
        }
    }

    @Test
    public void getInvoiceList() {
        initCstExpectations();

        assertReflectionEquals(createBuilder().category1().invoice1().buildInvoices(),
                invoiceRepository.getInvoiceList());
    }

    @Test
    public void create() {
        initCstExpectations();

        invoiceRepository.create(createBuilder().category1().invoice2().buildSingleInvoice());

        assertReflectionEquals(createBuilder().category1().invoice1()
                        .category1().invoice2()
                        .buildInvoices(),
                invoiceRepository.getInvoiceList());
    }

    @Test
    public void remove() {
        invoiceRepository.remove(createBuilder().category1().invoice1().buildInvoices());

        assertReflectionEquals(Lists.newArrayList(),
                invoiceRepository.getInvoiceList());
    }

    @Test(dataProvider = "createWithErrorDataProvider")
    public void createWithError(Invoice toCreate) {
        final List<Invoice> allInvoices = invoiceRepository.getInvoiceList();
        try {
            invoiceRepository.create(toCreate);
            fail("create should not pass!");
        } catch (Exception e) {
            assertReflectionEquals("Should not create any invoice",
                    allInvoices,
                    invoiceRepository.getInvoiceList());
        }
    }

    @Test(dataProvider = "removeWithErrorDataProvider")
    public void removeWithError(Invoice toRemove) {
        final List<Invoice> allInvoices = invoiceRepository.getInvoiceList();
        Validate.isFalse(allInvoices.contains(toRemove), "Invoice exists in db");
        try {
            invoiceRepository.remove(Lists.newArrayList(toRemove));
            fail("remove should not pass!");
        } catch (Exception e) {
            assertReflectionEquals("Should not remove any invoice",
                    allInvoices,
                    invoiceRepository.getInvoiceList());
        }
    }

    protected void initCstExpectations() {
        // add expectations if needed
    }

    @DataProvider
    protected Object[][] createWithErrorDataProvider() {
        return new Object[][] {
                // null values
                { null },
                { createBuilder().invoiceEmpty().buildSingleInvoice() },
                // invoice exists in db
                { createBuilder().invoice1().buildSingleInvoice() },
                };
    }

    @DataProvider
    protected Object[][] removeWithErrorDataProvider() {
        return new Object[][] {
                { null },
                { createBuilder().invoiceEmpty().buildSingleInvoice() },
                // not exist
                { createBuilder().invoice2().buildSingleInvoice() },
                };
    }
}