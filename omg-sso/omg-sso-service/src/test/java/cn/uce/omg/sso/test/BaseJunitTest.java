package cn.uce.omg.sso.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml","file:src/test/resources/spring-*.xml" })
public class BaseJunitTest extends AbstractJUnit4SpringContextTests {
	public <T> T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}

	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	protected ApplicationContext getContext() {
		return applicationContext;
	}

	@Before
	public void excuteBefore() {
		System.out.println("==========单元测试执行开始========");
	}

	@After
	public void excuteEnd() {
		System.out.println("==========单元测试执行结束========");
	}
	
	@Test
	public void test() {
		System.out.println("test");
	}
}
