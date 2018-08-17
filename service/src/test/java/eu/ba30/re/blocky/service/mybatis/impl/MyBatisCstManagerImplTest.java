package eu.ba30.re.blocky.service.mybatis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.TestObjectsBuilder;
import eu.ba30.re.blocky.service.config.mybatis.MyBatisServiceTestConfiguration;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { MyBatisServiceTestConfiguration.class })
public class MyBatisCstManagerImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CstManager cstManager;

    @Test
    public void getCategories() {
        assertReflectionEquals(new TestObjectsBuilder().category1().category2().buildCategories(),
                cstManager.getCategories());
    }

    @Test
    public void getCategory() {
        assertReflectionEquals(new TestObjectsBuilder().category1().buildSingleCategory(),
                cstManager.getCategory(1));
    }
}