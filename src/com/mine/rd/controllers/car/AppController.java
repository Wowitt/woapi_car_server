package com.mine.rd.controllers.car;

import org.apache.log4j.Logger;

import com.jfinal.aop.Clear;
import com.mine.pub.controller.BaseController;
import com.mine.pub.service.Service;
import com.mine.rd.services.car.service.AppService;

@Clear
public class AppController extends BaseController{
	private Logger logger = Logger.getLogger(AppController.class);
	
	public void test(){
		logger.info("测试");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("测试异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
	
	public void rentCarList(){
		logger.info("待租车列表");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("待租车列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	public void carDetail(){
		logger.info("车辆明细页面数据");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("车辆明细页面数据异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	public void uploadImg(){
		logger.info("上传头像");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("上传头像异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	public void userEdit(){
		logger.info("用户信息编辑");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("用户信息编辑异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	public void initUser(){
		logger.info("初始化用户信息");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("初始化用户信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	public void uploadCertification(){
		logger.info("实名认证正面");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("实名认证正面异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	public void saveCertification(){
		logger.info("保存实名认证");
		Service service = new AppService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("保存实名认证异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
}
