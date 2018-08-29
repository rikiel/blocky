package eu.ba30.re.blocky.service.impl.mybatis.repository.xml;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.xml.MyBatisRepositoryXmlTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstCategoryRepositoryImplTest;

@ContextConfiguration(classes = { MyBatisXmlCstCategoryRepositoryImplTest.CstCategoryRepositoryConfiguration.class })
public class MyBatisXmlCstCategoryRepositoryImplTest extends AbstractCstCategoryRepositoryImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.MY_BATIS);
    }

    @Configuration
    public static class CstCategoryRepositoryConfiguration extends MyBatisRepositoryXmlTestConfiguration {
        @Nonnull
        @Override
        protected List<String> getSqlScripts() {
            return Lists.newArrayList("db/repositoryTests/test-data-cst-category.sql");
        }
    }
}