package com.dhc.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.dhc.constant.Const;
import com.dhc.constant.blog.BlogConst;
import com.dhc.controller.blog.BlogController;
import com.dhc.controller.index.IndexController;
import com.dhc.model.Blog;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.redis.RedisPlugin;

/**
 * JFinal核心配置类
 * @author DHC
 * @version v1.0
 */
public class CoreConfig extends JFinalConfig {

	/**
	 * 常量配置方法
	 */
	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", true));
		me.setUrlParaSeparator(Const.SYMBOLAMPERSAND);
	}
	
	/**
	 * 路由配置方法
	 * 1、写个类如：BlogRoute继承Routes，配置me.add(new BlogRoute());
	 * 2、第三个参数可通过注解@ActionKey("/index")方式
	 */
	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/index");
		me.add("/blog", BlogController.class);
	}

	/**
	 * 插件配置方法
	 * 本项目中集成了druid\redis\quartz
	 */
	@Override
	public void configPlugin(Plugins me) {
		//数据库监控插件druid
		DruidPlugin dp = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		// sql监控
		dp.addFilter(new StatFilter()); 
		// 防止sql注入
		dp.addFilter(new WallFilter()); 
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql"); 
		dp.addFilter(wall);
		me.add(dp);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		me.add(arp);

		arp.addMapping(BlogConst.BLOGTABLE, "id", Blog.class);

		//redis缓存支持根据不同模块使用缓存，目前我创建一个关于blog的缓存块
		RedisPlugin blogRedis = new RedisPlugin(BlogConst.BLOGTABLE, "localhost");
		me.add(blogRedis);

		//定时任务插件 目前配置了一个每5秒钟执行一次的定时脚本
//		QuartzPlugin quartzPlugin = new QuartzPlugin("job.properies");
//		me.add(quartzPlugin);

	}
	
	/**
	 * 拦截器配置方法 
	 * 类似与struts2拦截器，处理action
	 */
	@Override
	public void configInterceptor(Interceptors me) {

//		me.add(new TxByMethods("find", "update", "delete", "save")); // 声明式事务管理
//		me.add(new TxByMethodRegex("(.*save.*|.*update.*|.*delete.*)"));// 声明式事务管理
//		me.addGlobalServiceInterceptor(xx);
		
	}
	
	
	/** 
	 * 处理配置方法 
	 * 接管所有web请求，可在此层做功能性的拓展
	 */
	@Override
	public void configHandler(Handlers me) {
		DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
		me.add(dvh);

	}
	

	/**
	 * 插件关闭之前【方法可选择性使用】
	 */
	public void beforeJFinalStop() {
		System.out.println("插件关闭");
	}

	/**
	 * 插件加载完后【方法可选择性使用】
	 */
	public void afterJFinalStart() {
		System.out.println("加载完毕");
	}


}
