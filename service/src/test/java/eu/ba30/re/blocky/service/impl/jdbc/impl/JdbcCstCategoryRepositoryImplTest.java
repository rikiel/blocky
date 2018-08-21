package eu.ba30.re.blocky.service.impl.jdbc.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.jdbc.JdbcRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.jdbc.db.JdbcCstCategoryRepository;

import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { JdbcCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcCstCategoryRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private JdbcCstCategoryRepository cstCategoryRepository;

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

    @Test
    public void getByIdError() {
        try {
            cstCategoryRepository.getById(999);
            fail("getById should not pass!");
        } catch (Exception e) {
            // nothing to do
        }
    }

    @Configuration
    public static class CstCategoryRepositoryConfiguration extends JdbcRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}