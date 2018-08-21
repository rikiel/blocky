package eu.ba30.re.blocky.service.impl.mybatis;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.config.mybatis.MyBatisServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.CstManagerImplTest;

@ContextConfiguration(classes = { MyBatisServiceTestConfiguration.class })
public class MyBatisCstManagerImplTest extends CstManagerImplTest {
}