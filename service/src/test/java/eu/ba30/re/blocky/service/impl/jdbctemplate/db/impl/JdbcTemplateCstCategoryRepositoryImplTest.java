package eu.ba30.re.blocky.service.impl.jdbctemplate.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.jdbctemplate.JdbcTemplateRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstCategoryRepositoryImplTest;

@ContextConfiguration(classes = { JdbcTemplateCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class JdbcTemplateCstCategoryRepositoryImplTest extends AbstractCstCategoryRepositoryImplTest {
    @Configuration
    public static class CstCategoryRepositoryConfiguration extends JdbcTemplateRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}