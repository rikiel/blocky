package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.impl.db.CstCategoryRepository;
import eu.ba30.re.blocky.service.impl.db.RepositoryTestConfiguration;

import static eu.ba30.re.blocky.service.TestUtils.getDbCategory;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { CstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class CstCategoryRepositoryImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CstCategoryRepository cstCategoryRepository;

    @Test
    public void getAllCategories() {
        assertReflectionEquals(Lists.newArrayList(getDbCategory()),
                cstCategoryRepository.getAllCategories());
    }

    @Test
    public void getById() {
        assertReflectionEquals(getDbCategory(),
                cstCategoryRepository.getById(1));
    }

    @Configuration
    public static class CstCategoryRepositoryConfiguration extends RepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}