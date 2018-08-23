package eu.ba30.re.blocky.service.impl.mybatis;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.config.mybatis.xml.MyBatisServiceXmlTestConfiguration;
import eu.ba30.re.blocky.service.impl.AbstractCstManagerImplTest;

@ContextConfiguration(classes = { MyBatisServiceXmlTestConfiguration.class })
public class MyBatisXmlCstManagerImplTest extends AbstractCstManagerImplTest {
}