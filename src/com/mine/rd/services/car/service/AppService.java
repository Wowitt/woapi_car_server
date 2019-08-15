package com.mine.rd.services.car.service;

import java.sql.SQLException;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.mine.pub.controller.BaseController;
import com.mine.pub.kit.DateKit;
import com.mine.pub.service.BaseService;
import com.mine.rd.services.car.pojo.AppDao;

public class AppService  extends BaseService{
	private int pn = 0;
	private int ps = 0;
	private AppDao dao = new AppDao();
	public AppService(BaseController controller) {
		super(controller);
	}

	@Override
	public void doService() throws Exception {
		Db.tx(new IAtom() {
	        @Override
	        public boolean run() throws SQLException {
	            try {
	            	if("test".equals(getLastMethodName(7))){
	            		test();
	            	}
	            	else if("rentCarList".equals(getLastMethodName(7))){
	            		rentCarList();
	            	}
	            	else if("carDetail".equals(getLastMethodName(7))){
	            		carDetail();
	            	}
	            	else if("uploadImg".equals(getLastMethodName(7))){
	            		uploadImg();
	            	}
	            	else if("userEdit".equals(getLastMethodName(7))){
	            		userEdit();
	            	}
	            	else if("initUser".equals(getLastMethodName(7))){
	            		initUser();
	            	}
	            	else if("initUser".equals(getLastMethodName(7))){
	            		initUser();
	            	}
	            	else if("uploadCertification".equals(getLastMethodName(7))){
	            		uploadCertification();
	            	}
	            	else if("saveCertification".equals(getLastMethodName(7))){
	            		saveCertification();
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
	
	private void test(){
		controller.setAttr("resFlag", "0");
	}
	
	private void rentCarList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.rentCarList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void carDetail(){
		String carId = controller.getMyParam("carId") == null ? "" : controller.getMyParam("carId").toString();
		controller.setAttr("getCarInfo",dao.getCarInfo(carId));
		controller.setAttr("setmealList",dao.querySetmeal());
		controller.setAttr("resFlag", "0");
	}
	
	private void uploadImg(){
		String imgBase64 = controller.getMyParam("imgBase64") == null ? "" : controller.getMyParam("imgBase64").toString();
		String tel = controller.getMySession("tel").toString();
		String imgPath = "";
		if(imgBase64 != null && !"".equals(imgBase64)){
			imgPath = controller.base64ToImgpath(imgBase64,"customer_portrait",tel+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
		}
		if("".equals(imgPath)){
			controller.setAttr("resFlag", "1");
		}else{
			controller.setAttr("imgPath",imgPath);
			controller.setAttr("resFlag", "0");
		}
	}
	
	private void userEdit(){
		Map<String, Object> map = controller.getMyParamMap("user");
		dao.userEdit(map);
		controller.setAttr("resFlag", "0");
	}
	
	private void initUser(){
		String userId = controller.getMySession("userId").toString();
		controller.setAttr("user",dao.initUser(userId));
		controller.setAttr("resFlag", "0");
	}
	
	private void uploadCertification(){
		String imgBase64 = controller.getMyParam("imgBase64") == null ? "" : controller.getMyParam("imgBase64").toString();
		String tel = controller.getMySession("tel").toString();
		String imgPath = "";
		if(imgBase64 != null && !"".equals(imgBase64)){
			imgPath = controller.base64ToImgpath(imgBase64,"customer_certification",tel+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
		}
		if("".equals(imgPath)){
			controller.setAttr("resFlag", "1");
		}else{
			controller.setAttr("imgPath",imgPath);
			controller.setAttr("resFlag", "0");
		}
	}
	
	private void saveCertification(){
		Map<String, Object> map = controller.getMyParamMap("item");
		String userId = controller.getMySession("userId").toString();
		dao.saveCertification(userId,map);
		controller.setAttr("resFlag", "0");
	}
}
