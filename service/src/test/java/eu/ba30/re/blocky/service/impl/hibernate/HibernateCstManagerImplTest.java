package eu.ba30.re.blocky.service.impl.hibernate;

import org.springframework.test.context.ContextConfiguration;

import eu.ba30.re.blocky.service.config.hibernate.HibernateServiceTestConfiguration;
import eu.ba30.re.blocky.service.impl.CstManagerImplTest;

@ContextConfiguration(classes = { HibernateServiceTestConfiguration.class })
public class HibernateCstManagerImplTest extends CstManagerImplTest {
}