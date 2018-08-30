package eu.ba30.re.blocky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractCstCategoryRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CstCategoryRepository cstCategoryRepository;

    protected abstract TestObjectsBuilder createBuilder();

    @Test
    public void getCategoryList() {
        assertReflectionEquals(createBuilder().category1().category2().buildCategories(),
                cstCategoryRepository.getCategoryList());
    }

    @Test
    public void getCategoryById() {
        assertReflectionEquals(createBuilder().category1().buildSingleCategory(),
                cstCategoryRepository.getCategoryById(1));
    }

    @Test
    public void getErrorForNotExistingCategoryId() {
        try {
            final Category category = cstCategoryRepository.getCategoryById(999);
            fail("getCategoryById should not pass! Found " + category);
        } catch (Exception e) {
            // test OK
        }
    }
}