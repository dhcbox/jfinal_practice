package com.dhc.service.blog;

import java.util.HashMap;

import com.dhc.constant.Const;
import com.dhc.constant.blog.BlogConst;
import com.dhc.model.Blog;
import com.dhc.redis.blog.BlogRedis;
import com.dhc.util.BeanUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class BlogService {

	public static final BlogService blogService = new BlogService();

	public Page<Blog> paginateBlog(String pageNumber, String pageSize) {

		if(StrKit.isBlank(pageNumber) || StrKit.isBlank(pageSize)){
			return null;
		}
		
		Page<Blog> blogPage = Blog.dao.paginate(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));

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

	public String saveBlog(String title, String content) {

		if (StrKit.isBlank(title)) {
			return "博客标题不能为空";
		}

		if (StrKit.isBlank(content)) {
			return "博客内容不能为空";
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(BlogConst.TITLE, title);
		map.put(BlogConst.CONTENT, content);

		boolean flag = Blog.dao._setAttrs(map).save();
		if(flag){
			 Blog blog = Blog.dao.getLastInsertBlog();
			 BlogRedis.CacheBlog(blog.getInt(BlogConst.ID), findBlogById(blog.getInt(BlogConst.ID)));
		}
		
		return flag ? "" : "保存失败";

	}

	public boolean updateBlog(int id, String title) {
		return Blog.dao.set(BlogConst.TITLE, title).set(BlogConst.ID, id)
				.update();
	}

}
