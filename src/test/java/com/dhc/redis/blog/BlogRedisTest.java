package com.dhc.redis.blog;

import org.junit.Test;

import com.dhc.constant.blog.BlogConst;
import com.dhc.model.Blog;
import com.jfinal.plugin.redis.RedisPlugin;

public class BlogRedisTest {
	
	@Test
	public void testPrint(){
		System.out.println("+++++++++++++++++++");
	}
	
	@Test
	public void testGetBlogFromCache(){
		RedisPlugin rp = new RedisPlugin(BlogConst.BLOGTABLE, "localhost");
		rp.start();
		Blog blog = BlogRedis.getBlogFromCache(10);
		System.out.println(blog);
	}

}
