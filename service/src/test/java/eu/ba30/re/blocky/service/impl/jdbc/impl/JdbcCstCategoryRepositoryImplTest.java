package eu.ba30.re.blocky.service.impl.jdbc.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.jdbc.JdbcRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstCategoryRepositoryImplTest;

@ContextConfiguration(classes = { JdbcCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class JdbcCstCategoryRepositoryImplTest extends AbstractCstCategoryRepositoryImplTest {
    @Configuration
    public static class CstCategoryRepositoryConfiguration extends JdbcRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}