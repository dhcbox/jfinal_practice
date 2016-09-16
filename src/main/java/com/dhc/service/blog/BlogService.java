package com.dhc.service.blog;

import com.dhc.constant.Const;
import com.dhc.constant.blog.BlogConst;
import com.dhc.model.Blog;
import com.dhc.redis.blog.BlogRedis;
import com.dhc.util.BeanUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class BlogService {

	public static final BlogService blogService = new BlogService();

	public Page<Blog> paginateBlog(int pageNumber, int pageSize) {

		Page<Blog> blogPage = Blog.dao.paginate(pageNumber,pageSize);

		return blogPage;
	}

	public Blog findBlogById(int id) {

		String columns = BlogConst.CONTENT + Const.SYMBOLCOMMA
				+ BlogConst.TITLE;
		Blog blog = Blog.dao.findByIdLoadColumns(id, columns);

		return BeanUtil.isBeanEmpty(blog) ? null : blog;

	}

	public boolean deleteBlogById(int id) {

		Blog blog = findBlogById(id);
		BlogRedis.removeCacheBlog(id);
		
		if (!BeanUtil.isBeanEmpty(blog)) {
			return Blog.dao.deleteById(id);
		}
		
		return false;
	}

	
	public String saveBlog(Blog blog) {
		if (StrKit.isBlank(blog.getStr("title"))) {
			return "博客标题不能为空";
		}
		if (StrKit.isBlank(blog.getStr("content"))) {
			return "博客内容不能为空";
		}
		boolean flag = blog.save();
		if(flag){
			 Blog b = Blog.dao.getLastInsertBlog();
			 BlogRedis.CacheBlog(b.getInt(BlogConst.ID), findBlogById(b.getInt(BlogConst.ID)));
		}
		return flag ? "" : "保存失败";
	}

	public boolean updateBlog(Blog blog) {
		return blog.update();
	}

}
