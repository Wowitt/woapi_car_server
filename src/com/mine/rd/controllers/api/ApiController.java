package com.mine.rd.controllers.api;

import com.mine.pub.controller.BaseController;
import com.mine.pub.service.Service;
import com.mine.rd.services.api.service.ApiService;
import com.jfinal.aop.Clear;

@Clear
public class ApiController  extends BaseController{
	
	public void index(){
		Service service = new ApiService(this);
		try {
			service.doService();
		} catch (Exception e) {
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
	
	public void book() {
		Service service = new ApiService(this);
		try {
			service.doService();
		} catch (Exception e) {
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
	
	public void payMethod() {
		Service service = new ApiService(this);
		try {
			service.doService();
		} catch (Exception e) {
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
	
	public void empInfo() {
		Service service = new ApiService(this);
		try {
			service.doService();
		} catch (Exception e) {
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}

	public void flowmeter() {
		Service service = new ApiService(this);
		try {
			service.doService();
		} catch (Exception e) {
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
}	
