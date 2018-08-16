package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.Invoice;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.config.RepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.db.CstCategoryRepository;
import eu.ba30.re.blocky.service.impl.db.InvoiceRepository;
import mockit.Capturing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { InvoiceRepositoryImplTest.InvoiceRepositoryConfiguration.class })
public class InvoiceRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Capturing
    private CstCategoryRepository cstCategoryRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void getNextItemId() {
        final int sequenceBegin = 10;
        for (int i = 0; i < 100; ++i) {
            assertEquals(invoiceRepository.getNextItemId(), sequenceBegin + i);
        }
    }

    @Test(priority = 1)
    public void getInvoices() {
        assertReflectionEquals(new TestObjectsBuilder().category1().invoice1().buildInvoices(),
                invoiceRepository.getInvoices());
    }

    @Test(priority = 2)
    public void create() {
        invoiceRepository.create(new TestObjectsBuilder().category1().invoice2().buildSingleInvoice());

        assertReflectionEquals(new TestObjectsBuilder().category1().invoice1()
                        .category1().invoice2()
                        .buildInvoices(),
                invoiceRepository.getInvoices());
    }

    @Test(priority = 3)
    public void remove() {
        invoiceRepository.remove(new TestObjectsBuilder().category1().invoice2().buildInvoices());

        assertReflectionEquals(new TestObjectsBuilder().category1().invoice1().buildInvoices(),
                invoiceRepository.getInvoices());
    }

    @Test(priority = 4,
            dataProvider = "createErrorDataProvider")
    public void createError(Invoice toCreate) {
        final List<Invoice> allInvoices = invoiceRepository.getInvoices();
        try {
            invoiceRepository.create(toCreate);
            fail("create should not pass!");
        } catch (Exception e) {
            assertReflectionEquals("Should not create any invoice",
                    allInvoices,
                    invoiceRepository.getInvoices());
        }
    }

    @Test(priority = 4,
            dataProvider = "removeErrorDataProvider")
    public void removeError(Invoice toRemove) {
        final List<Invoice> allInvoices = invoiceRepository.getInvoices();
        Validate.isFalse(allInvoices.contains(toRemove), "Invoice exists in db");
        try {
            invoiceRepository.remove(Lists.newArrayList(toRemove));
            fail("remove should not pass!");
        } catch (Exception e) {
            assertReflectionEquals("Should not remove any invoice",
                    allInvoices,
                    invoiceRepository.getInvoices());
        }
    }

    @DataProvider
    private Object[][] createErrorDataProvider() {
        return new Object[][] {
                // null values
                { null },
                { new Invoice() },
                // invoice exists in db
                { new TestObjectsBuilder().invoice1().buildSingleInvoice() },
                };
    }

    @DataProvider
    private Object[][] removeErrorDataProvider() {
        return new Object[][] {
                { null },
                { new Invoice() },
                // not exist
                { new TestObjectsBuilder().invoice2().buildSingleInvoice() },
                { new TestObjectsBuilder().invoice3().buildSingleInvoice() },
                };
    }

    @Configuration
    public static class InvoiceRepositoryConfiguration extends RepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList(
                    "db/repositoryTests/test-data-invoices.sql",
                    "db/repositoryTests/test-data-cst-category.sql");
        }
    }
}