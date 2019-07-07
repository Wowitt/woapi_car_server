package com.mine.rd.services.api.service;

import java.sql.SQLException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.mine.pub.controller.BaseController;
import com.mine.pub.service.BaseService;
import com.mine.rd.services.car.pojo.CarDao;

public class ApiService extends BaseService{

	private CarDao dao = new CarDao();
	
	public ApiService(BaseController controller) {
		super(controller);
	}

	@Override
	public void doService() throws Exception {
		Db.tx(new IAtom() {
	        @Override
	        public boolean run() throws SQLException {
	            try {
	            	if("index".equals(getLastMethodName(7))){
	            		index();
	        		}
	            	else if("book".equals(getLastMethodName(7))){
	            		book();
	            	}
	            	else if("payMethod".equals(getLastMethodName(7))){
	            		payMethod();
	            	}
	            	else if("empInfo".equals(getLastMethodName(7))){
	            		empInfo();
	            	}
	            	else if("flowmeter".equals(getLastMethodName(7))){
	            		flowmeter();
	            	}
	            } catch (Exception e) {
	                e.printStackTrace();
	                controller.setAttr("msg", "系统异常，请重新登录！");
	    			controller.setAttr("resFlag", "1");
	                return false;
	            }
	            return true;
	        }
	    });
	}

	public void index(){
		String msg = controller.getPara("msg") == null ? "" : controller.getPara("msg").toString()+",";
		controller.setAttr("res", msg+"success!");
	}
	
	public void book(){
		controller.setAttr("resFlag", "0");
		controller.setAttr("list", dao.book());
	}
	
	public void payMethod(){
		controller.setAttr("resFlag", "0");
		controller.setAttr("list", dao.payMethod());
	}
	
	public void empInfo(){
		controller.setAttr("resFlag", "0");
		controller.setAttr("list", dao.empInfo());
	}
	
	public void flowmeter(){
		String UnitID = controller.getPara("UnitID") == null ? "" : controller.getPara("UnitID").toString();
		String beginDate = controller.getPara("beginDate") == null ? "" : controller.getPara("beginDate").toString();
		String endDate = controller.getPara("endDate") == null ? "" : controller.getPara("endDate").toString();
		if(!"".equals(UnitID) && !"".equals(beginDate) &&!"".equals(endDate)){
			controller.setAttr("resFlag", "0");
			controller.setAttr("list", dao.flowmeter(UnitID,beginDate,endDate));
		}else{
			controller.setAttr("resFlag", "1");
			controller.setAttr("resMsg", "缺少参数");
			controller.setAttr("list", dao.flowmeter(UnitID,beginDate,endDate));
		}
	}
}
