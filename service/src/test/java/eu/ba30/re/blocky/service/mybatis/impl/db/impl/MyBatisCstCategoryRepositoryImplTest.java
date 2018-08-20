package eu.ba30.re.blocky.service.mybatis.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.MyBatisRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.mybatis.impl.db.MyBatisCstCategoryRepository;

import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { MyBatisCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MyBatisCstCategoryRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private MyBatisCstCategoryRepository cstCategoryRepository;

    @Test
    public void getAllCategories() {
        assertReflectionEquals(new TestObjectsBuilder().category1().category2().buildCategories(),
                cstCategoryRepository.getAllCategories());
    }

    @Test
    public void getById() {
        assertReflectionEquals(new TestObjectsBuilder().category1().buildSingleCategory(),
                cstCategoryRepository.getById(1));
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Result should not be null!")
    public void getByIdError() {
        final Category category = cstCategoryRepository.getById(999);
        fail("getById should not pass! Found " + category);
    }

    @Configuration
    public static class CstCategoryRepositoryConfiguration extends MyBatisRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}