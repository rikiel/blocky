package eu.ba30.re.blocky.service.impl.mybatis.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.config.mybatis.MyBatisRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstCategoryRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class MyBatisCstCategoryRepositoryImplTest extends AbstractCstCategoryRepositoryImplTest {
    @Configuration
    public static class CstCategoryRepositoryConfiguration extends MyBatisRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}