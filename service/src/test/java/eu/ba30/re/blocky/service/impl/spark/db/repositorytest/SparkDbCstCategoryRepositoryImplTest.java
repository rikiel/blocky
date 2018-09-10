package eu.ba30.re.blocky.service.impl.spark.db.repositorytest;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.spark.db.SparkDbRepositoryTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstCategoryRepositoryImplTest;

@ContextConfiguration(classes = { SparkDbCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class SparkDbCstCategoryRepositoryImplTest extends AbstractCstCategoryRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.SPARK);
    }

    @Configuration
    public static class CstCategoryRepositoryConfiguration extends SparkDbRepositoryTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}
