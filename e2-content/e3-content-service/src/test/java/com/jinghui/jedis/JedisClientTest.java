package com.jinghui.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jinghui.common.jedis.JedisClient;

public class JedisClientTest {

	@Test
	public void testJedisClient(){
		// 初始化spring容器
		ApplicationContext app = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		// 从容器中获取JedisClient对象
		JedisClient jedisClient = app.getBean(JedisClient.class);
		String str = jedisClient.get("test");
		System.out.println(str);
	}
}
