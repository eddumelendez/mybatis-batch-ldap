package net.eddumelendez.config;

import net.eddumelendez.config.BatchConfiguration;
import net.eddumelendez.config.MybatisConfiguration;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LdapTestConfig.class,
		BatchConfiguration.class, MybatisConfiguration.class, TestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class AbstractTest {

}
