package com.dhc.controller.index;

import com.jfinal.core.Controller;

public class IndexController extends Controller{
	
	
	public void index(){
		renderText("hello JFinal");
	}

}