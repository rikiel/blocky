package eu.ba30.re.blocky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.TestObjectsBuilder;

import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractCstManagerImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CstManager cstManager;

    @Test
    public void getCategoryList() {
        assertReflectionEquals(new TestObjectsBuilder().category1().category2().buildCategories(),
                cstManager.getCategoryList());
    }

    @Test
    public void getCategoryById() {
        assertReflectionEquals(new TestObjectsBuilder().category1().buildSingleCategory(),
                cstManager.getCategoryById(1));
    }

    @Test
    public void getErrorForNotExistingCategoryId() {
        try {
            final Category category = cstManager.getCategoryById(999);
            fail("getCategoryById should not pass! Found " + category);
        } catch (Exception e) {
            // test OK
        }
    }
}