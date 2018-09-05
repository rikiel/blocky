package eu.ba30.re.blocky.service.impl.spark.repositorytest;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.SparkRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstCategoryRepositoryImplTest;

@ContextConfiguration(classes = { SparkCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class SparkCstCategoryRepositoryImplTest extends AbstractCstCategoryRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }

    @Configuration
    public static class CstCategoryRepositoryConfiguration extends SparkRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}
