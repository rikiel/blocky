package eu.ba30.re.blocky.service.impl.mybatis.annotation;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.annotation.MyBatisServiceAnnotationTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstManagerImplTest;

@ContextConfiguration(classes = { MyBatisServiceAnnotationTestConfiguration.class })
public class MyBatisAnnotationCstManagerImplTest extends AbstractCstManagerImplTest {
    @Override
    protected TestObjectsBuilder createBuilder() {
        return new TestObjectsBuilder(TestObjectsBuilder.FrameworkType.MY_BATIS);
    }
}