package com.dhc.util;

import org.junit.Test;

import com.dhc.model.Blog;
import com.dhc.redis.blog.BlogRedis;

public class JFinalTestUtil {

	@Test
	public void testPrint(){
		System.out.println("eeeeeeeeeeeeeeee");
	}
	
	@Test
	public void testFromRedis(){
		//xxxx
		Blog b = BlogRedis.getBlogFromCache(20);
		System.out.println(b);
	}

}
