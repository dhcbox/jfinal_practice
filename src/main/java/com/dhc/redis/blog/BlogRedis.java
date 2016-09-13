package com.dhc.redis.blog;

import com.dhc.constant.blog.BlogConst;
import com.dhc.model.Blog;
import com.dhc.util.BeanUtil;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class BlogRedis {

	public static Cache getCache() {

		Cache blogCache = Redis.use(BlogConst.BLOGTABLE);
		return blogCache;

	}

	public static void CacheBlog(Object keyObject,Object valueObject) {

		Cache blogCache = getCache();
		blogCache.set(keyObject,valueObject);
		
	}
	
	public static Blog getBlogFromCache(Object keyObject){

		Cache blogCache = getCache();
		Blog blog = blogCache.get(keyObject);

		return BeanUtil.isBeanEmpty(blog)?null:blog;
	
	}
	
	public static void removeCacheBlog(Object keyObject){
		
		Cache blogCache = getCache();
		blogCache.del(keyObject);
		
	}

}
