package eu.ba30.re.blocky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.service.CstManager;

import static eu.ba30.re.blocky.service.TestUtils.getDbCategory;
import static eu.ba30.re.blocky.service.TestUtils.getDbCategory2;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class CstManagerImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CstManager cstManager;

    @Test
    public void getCategories() {
        assertReflectionEquals(Lists.newArrayList(getDbCategory(), getDbCategory2()),
                cstManager.getCategories());
    }

    @Test
    public void getCategory() {
        assertReflectionEquals(getDbCategory(),
                cstManager.getCategory(1));
    }
}