package com.dhc.redis.blog;

import org.junit.Test;

import com.dhc.constant.blog.BlogConst;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.RedisPlugin;

public class BlogRedisTest {
	
	@Test
	public void testPrint(){
		System.out.println("+++++++++++++++++++");
	}
	
	@Test
	public void testGetCache(){
		RedisPlugin rp = new RedisPlugin(BlogConst.BLOGTABLE, "localhost");
		rp.start();
		Cache ca = BlogRedis.getCache();
		System.out.println(ca);
	}

}
