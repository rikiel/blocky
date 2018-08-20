package eu.ba30.re.blocky.service.jdbctemplate.impl.db.impl;

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
import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.jdbctemplate.impl.db.JdbcTemplateCstCategoryRepository;

import static org.testng.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { JdbcTemplateCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcTemplateCstCategoryRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private JdbcTemplateCstCategoryRepository cstCategoryRepository;

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
    public static class CstCategoryRepositoryConfiguration extends JdbcTemplateRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}