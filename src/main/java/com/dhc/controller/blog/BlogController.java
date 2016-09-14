package com.dhc.controller.blog;

import org.apache.log4j.Logger;

import com.dhc.constant.Const;
import com.dhc.constant.blog.BlogConst;
import com.dhc.controller.ControllerCommon;
import com.dhc.model.Blog;
import com.dhc.redis.blog.BlogRedis;
import com.dhc.service.blog.BlogService;
import com.dhc.util.BeanUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class BlogController extends Controller {

	public static Logger logger = Logger.getLogger(BlogController.class);
	/**
	 * 分页查询
	 */
	public void index() {

		String pageNumber = this.getPara(Const.PAGENUMBER);
		String pageSize = this.getPara(Const.PAGESIZE);
		
		Page<Blog> blogPage = null;

		if(StrKit.isBlank(pageNumber) || StrKit.isBlank(pageSize)){
			blogPage = BlogService.blogService.paginateBlog(getParaToInt(0, 1),2);
		}else{
			blogPage = BlogService.blogService.paginateBlog(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
		}
		
		if(blogPage == null){
			ControllerCommon.errorMsg("参数不能为空");
			logger.error("查找失败");
		}
		
		logger.info("查找成功");
		setAttr("blogPage", blogPage);
		render("blog.html");
		
//		renderJson(ControllerCommon.ctrCommon.returnJsonToClient(blogPage));
	}
	
	/**
	 * 跳转至新增页面
	 */
	public void add(){
		
	}
	/**
	 * 跳转至修改页面
	 */
	public void edit(){
		setAttr("blog", Blog.dao.findById(getParaToInt()));
	}

	/**
	 * 查找元素
	 */
	public void find() {
		
		//先从缓存里面找数据
		Blog cacheBlog = BlogRedis.getBlogFromCache(getParaToInt(BlogConst.ID));

		if(!BeanUtil.isBeanEmpty(cacheBlog)){
			logger.info("查找成功");
			renderJson(ControllerCommon.ctrCommon.returnJsonToClient(cacheBlog));
		}else {
			//缓存没有数据，从DB读
			Blog blog = BlogService.blogService.findBlogById(getParaToInt(BlogConst.ID));
			if (BeanUtil.isBeanEmpty(blog)) {
				logger.error("查找失败");
				ControllerCommon.errorMsg("数据为空");
			}
			logger.info("查找成功");
			renderJson(ControllerCommon.ctrCommon.returnJsonToClient(blog));
		}
	}

	/**
	 * 删除元素
	 */
	public void delete() {

		boolean result = BlogService.blogService
				.deleteBlogById(getParaToInt(BlogConst.ID));
		if (!result) {
			ControllerCommon.errorMsg("删除失败");
		}
		renderJson(ControllerCommon.ctrCommon.returnJsonToClient(result));
	}

	public void save() {
		String result = BlogService.blogService.saveBlog(getModel(Blog.class));
		if (!StrKit.isBlank(result)) {
			ControllerCommon.errorMsg(result);
		}
		redirect("/blog");
		
//		renderJson(ControllerCommon.ctrCommon.returnJsonToClient(result));
	}

	public void update() {
		boolean result = BlogService.blogService.updateBlog(getModel(Blog.class));
		if (!result) {
			ControllerCommon.errorMsg("更新失败");
		}
		redirect("/blog");
		
//		renderJson(ControllerCommon.ctrCommon.returnJsonToClient(result));
	}
}
