package com.mine.rd.controllers.car;

import org.apache.log4j.Logger;

import com.mine.pub.controller.BaseController;
import com.mine.pub.service.Service;
import com.mine.rd.services.car.service.CarService;

public class CarController extends BaseController{
	private Logger logger = Logger.getLogger(CarController.class);
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：根据卡号查询车辆信息
	 */
	public void querybyCardno(){
		logger.info("根据卡号查询车辆信息");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("根据卡号查询车辆信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：根据牌照号查询车辆信息
	 */
	public void querybyPlateNum(){
		logger.info("根据牌照号查询车辆信息");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("根据牌照号查询车辆信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：pda登录后初始化信息
	 */
	public void init(){
		logger.info("登录后初始化信息");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("登录后初始化信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：转场
	 */
	public void changeField(){
		logger.info("转场");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("转场异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：进车位
	 */
	public void updatePark(){
		logger.info("进车位");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("进车位异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：出场
	 */
	public void outField(){
		logger.info("出场");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("出场异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：回场
	 */
	public void inField(){
		logger.info("回场");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("回场异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：获取车辆管理计划名称
	 */
	public void getCarPlan(){
		logger.info("获取车辆管理计划名称");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("获取车辆管理计划名称异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：保存车辆管理计划名称
	 */
	public void saveCarPlan(){
		logger.info("保存车辆管理计划名称");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("保存车辆管理计划名称异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：查询车辆盘点列表
	 */
	public void queryCarCountGroup(){
		logger.info("查询车辆盘点列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查询车辆盘点列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190319
	 * 方法：查询车辆盘点明细列表
	 */
	public void queryCarCountDetailList(){
		logger.info("查询车辆盘点明细列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查询车辆盘点明细列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190321
	 * 方法：查询零件列表
	 */
	public void componentList(){
		logger.info("查询零件列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查询零件列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190322
	 * 方法：查询车辆列表
	 */
	public void carList(){
		logger.info("查询车辆列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查询车辆列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190322
	 * 方法：查询保险列表
	 */
	public void insureanceList(){
		logger.info("查询保险列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查询保险列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190322
	 * 方法：获取统计数据
	 */
	public void getStatistics(){
		logger.info("获取统计数据");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("获取统计数据异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190322
	 * 方法：获取零件列表
	 */
	public void queryComponentList(){
		logger.info("获取零件列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("获取零件列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190322
	 * 方法：获取公司列表
	 */
	public void queryCompanyList(){
		logger.info("获取公司列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("获取公司列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190322
	 * 方法：保存维修记录
	 */
	public void saveRepair(){
		logger.info("保存维修记录");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("保存维修记录异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：查询维修列表
	 */
	public void repairList(){
		logger.info("查询维修列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查询维修列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：获取维修单信息
	 */
	public void queryRepair(){
		logger.info("获取维修单信息");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("获取维修单信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：删除维修单零件
	 */
	public void delComponentItem(){
		logger.info("删除维修单零件");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("删除维修单零件异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：校验顾客是否重复
	 */
	public void checkCustomerRepeat(){
		logger.info("校验顾客是否重复");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("校验顾客是否重复异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：保存客户信息
	 */
	public void saveCustomer(){
		logger.info("保存客户信息");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("保存客户信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：销售app初始化
	 */
	public void saleAppInit(){
		logger.info("销售app初始化");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("销售app初始化异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：签约
	 */
	public void sign(){
		logger.info("签约");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("签约异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：合同列表
	 */
	public void contractList(){
		logger.info("合同列表");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("合同列表异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：获取合同信息
	 */
	public void getContract(){
		logger.info("获取合同信息");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("获取合同信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：合同详情初始化数据
	 */
	public void contractDetailInit(){
		logger.info("合同详情初始化数据");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("合同详情初始化数据异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：更新合同
	 */
	public void saveContract(){
		logger.info("更新合同");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("更新合同异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：合同WEB端详情初始化数据
	 */
	public void contractDetailWebInit(){
		logger.info("合同WEB详情初始化数据");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("合同WEB详情初始化数据异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：更新合同财务
	 */
	public void saveContractFinance(){
		logger.info("更新合同财务");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("更新合同财务异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：更新合同车管部门
	 */
	public void saveContractCarManage(){
		logger.info("更新合同车管部门");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("更新合同车管部门异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：更新合同交车
	 */
	public void saveContractDeliver(){
		logger.info("更新合同交车");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("更新合同交车异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：检查车辆
	 */
	public void saveContractCheckImg(){
		logger.info("检查车辆");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("检查车辆异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：初始化检查车辆
	 */
	public void initContractCheckImg(){
		logger.info("检查车辆");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("初始化检查车辆异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
	
	/**
	 * @author woody
	 * @date 20190323
	 * 方法：保存车辆共享池
	 */
	public void saveCarShare(){
		logger.info("保存车辆共享池");
		Service service = new CarService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("保存车辆共享池异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJsonForCors();
	}
}
