package com.jinghui.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	@Test
	public void testJedis(){
		// 创建jedis对象
		Jedis jedis = new Jedis("47.96.111.66", 6379);
		jedis.auth("cjhhiu123..");
		// 操作命令
		jedis.set("test", "1222");
		String str = jedis.get("test");
		System.out.println(str);
		//关闭连接
		jedis.close();
	}
	
	/**
	 * 连接池
	 */
	@Test
	public void testJedisPool(){
		// 创建连接池对象
		JedisPool pool = new JedisPool("47.96.111.66", 6379);
		// 从连接池获得连接
		Jedis jedis = pool.getResource();
		jedis.auth("cjhhiu123..");
		// 操作命令
		String str = jedis.get("test");
		System.out.println(str);
		// 每次使用完，关闭连接。连接池回收资源
		jedis.close();
		// 关闭连接池（单例）
		pool.close();
	}
	
	/**
	 * 连接集群
	 */
	@Test
	public void testJedisCluster(){
		// 创建集群对象，参数是一个set类型，set中包含HostAndPort对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("47.96.111.66", 7001));
		nodes.add(new HostAndPort("47.96.111.66", 7002));
		nodes.add(new HostAndPort("47.96.111.66", 7003));
		nodes.add(new HostAndPort("47.96.111.66", 7004));
		nodes.add(new HostAndPort("47.96.111.66", 7005));
		nodes.add(new HostAndPort("47.96.111.66", 7006));
		//直接使用jedisCluster对象
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("test", "222");
		String str = jedisCluster.get("test");
		System.out.println(str);
		// 关闭jedisCluster
		jedisCluster.close();
		
	}

}
