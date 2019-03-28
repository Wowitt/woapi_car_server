package com.mine.rd.services.car.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.mine.pub.controller.BaseController;
import com.mine.pub.service.BaseService;
import com.mine.rd.services.car.pojo.CarDao;

public class CarService extends BaseService{

	private CarDao dao = new CarDao();
	private int pn = 0;
	private int ps = 0;
	public CarService(BaseController controller) {
		super(controller);
	}

	@Override
	public void doService() throws Exception {
		Db.tx(new IAtom() {
	        @Override
	        public boolean run() throws SQLException {
	            try {
	            	if("querybyCardno".equals(getLastMethodName(7))){
	            		querybyCardno();
	            	}else if("init".equals(getLastMethodName(7))){
	            		init();
	        		}else if("changeField".equals(getLastMethodName(7))){
	        			changeField();
		            }else if("updatePark".equals(getLastMethodName(7))){
		            	updatePark();
		            }else if("outField".equals(getLastMethodName(7))){
		            	outField();
		            }else if("inField".equals(getLastMethodName(7))){
		            	inField();
		            }else if("getCarPlan".equals(getLastMethodName(7))){
		            	getCarPlan();
		            }else if("saveCarPlan".equals(getLastMethodName(7))){
		            	saveCarPlan();
		            }else if("queryCarCountGroup".equals(getLastMethodName(7))){
		            	queryCarCountGroup();
		            }else if("queryCarCountDetailList".equals(getLastMethodName(7))){
		            	queryCarCountDetailList();
		            }else if("componentList".equals(getLastMethodName(7))){
		            	componentList();
		            }else if("carList".equals(getLastMethodName(7))){
		            	carList();
		            }else if("insureanceList".equals(getLastMethodName(7))){
		            	insureanceList();
		            }else if("getStatistics".equals(getLastMethodName(7))){
		            	getStatistics();
		            }else if("queryComponentList".equals(getLastMethodName(7))){
		            	queryComponentList();
		            }else if("querybyPlateNum".equals(getLastMethodName(7))){
		            	querybyPlateNum();
		            }else if("queryCompanyList".equals(getLastMethodName(7))){
		            	queryCompanyList();
		            }else if("saveRepair".equals(getLastMethodName(7))){
		            	saveRepair();
		            }else if("repairList".equals(getLastMethodName(7))){
		            	repairList();
		            }else if("queryRepair".equals(getLastMethodName(7))){
		            	queryRepair();
		            }else if("delComponentItem".equals(getLastMethodName(7))){
		            	delComponentItem();
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
	
	private void init(){
		List<Map<String,Object>> fieldList = dao.queryCarField();
		for(Map<String,Object> map : fieldList){
			map.put("parkList", dao.queryCarPark(map.get("ID").toString()));
		}
		controller.setAttr("fieldList",fieldList);
		controller.setAttr("resFlag", "0");
	}
	
	private void getStatistics(){
		long carCount = dao.getCarCount();
		controller.setAttr("carCount",carCount);
		controller.setAttr("resFlag", "0");
	}
	
	private void querybyCardno(){
		String cardno = controller.getMyParam("cardno").toString();
		Map<String,Object> car = dao.querybyCardno(cardno);
		if(car != null){
			String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
			String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
			String planName = controller.getMyParam("carPlanName") == null ? "" : controller.getMyParam("carPlanName").toString();
			if(!"".equals(planName)){
				dao.addCarCount(car.get("ID").toString(), car.get("PLATE_NUM").toString(), userId, userName,planName);
			}
			controller.setAttr("car",car);
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","查不到车辆信息");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void querybyPlateNum(){
		String plateNum = controller.getMyParam("plateNum").toString();
		Map<String,Object> car = dao.querybyPlateNum(plateNum);
		if(car != null){
			controller.setAttr("car",car);
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","查不到车辆信息");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void changeField(){
		String carId = controller.getMyParam("carId").toString();
		String plateNum = controller.getMyParam("plateNum").toString();
		String fieldId = controller.getMyParam("fieldId").toString();
		String fieldName = controller.getMyParam("fieldName").toString();
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		int resInt = dao.removeParkCar(carId);
		if(resInt >= 0){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("carId", carId);
			map.put("plateNum", plateNum);
			map.put("fieldName", fieldName);
			map.put("fieldId", fieldId);
			map.put("userId", userId);
			map.put("userName", userName);
			map.put("bizName", "转场");
			dao.addCountFlow(map);
			dao.updateCarFieldInfo(carId, fieldId, fieldName);
			controller.setAttr("msg","转场成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","转场失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void updatePark(){
		String carId = controller.getMyParam("carId").toString();
		String parkNo = controller.getMyParam("parkNo").toString();
		String plateNum = controller.getMyParam("plateNum").toString();
		String fieldId = controller.getMyParam("fieldId").toString();
		String fieldName = controller.getMyParam("fieldName").toString();
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		int resInt = dao.removeParkCar(carId);
		int res2Int = dao.updatePark(carId,plateNum, fieldId, parkNo);
		int res3Int = dao.updateCarFieldInfo(carId, fieldId, fieldName);
		if(resInt >= 0 && res2Int >=0 && res3Int >=0){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("carId", carId);
			map.put("plateNum", plateNum);
			map.put("fieldName", fieldName);
			map.put("fieldId", fieldId);
			map.put("userId", userId);
			map.put("userName", userName);
			map.put("parkNo", parkNo);
			map.put("bizName", "进车位");
			dao.addCountFlow(map);
			if(controller.getMyParam("ifLog") != null && "1".equals(controller.getMyParam("ifLog"))){
				String oldplateNum = controller.getMyParam("oldplateNum") == null ? "" : controller.getMyParam("oldplateNum").toString();
				Map<String,Object> mapLog = new HashMap<String,Object>();
				mapLog.put("fromPlatform", "app");
				mapLog.put("content", userId+"|"+userName+"|"+oldplateNum +"|"+plateNum);
				mapLog.put("BIZ_NAME", "进车位");
				dao.addLogFlow(mapLog);
			}
			controller.setAttr("msg","转场成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","转场失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void outField(){
		String carId = controller.getMyParam("carId").toString();
		String plateNum = controller.getMyParam("plateNum").toString();
		String fieldId = controller.getMyParam("fieldId")== null ? "" : controller.getMyParam("fieldId").toString();
		String fieldName = controller.getMyParam("fieldName")== null ? "" : controller.getMyParam("fieldName").toString();
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		int resInt = dao.removeParkCar(carId);
		if(resInt >= 0){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("carId", carId);
			map.put("plateNum", plateNum);
			map.put("fieldName", fieldName);
			map.put("fieldId", fieldId);
			map.put("userId", userId);
			map.put("userName", userName);
			map.put("bizName", "出场");
			dao.addCountFlow(map);
			controller.setAttr("msg","出场成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","出场失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void inField(){
		String carId = controller.getMyParam("carId").toString();
		String plateNum = controller.getMyParam("plateNum").toString();
		String fieldId = controller.getMyParam("fieldId")== null ? "" : controller.getMyParam("fieldId").toString();
		String fieldName = controller.getMyParam("fieldName")== null ? "" : controller.getMyParam("fieldName").toString();
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("carId", carId);
		map.put("plateNum", plateNum);
		map.put("fieldName", fieldName);
		map.put("fieldId", fieldId);
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("bizName", "回场");
		dao.addCountFlow(map);
		controller.setAttr("msg","回场成功");
		controller.setAttr("resFlag", "0");
	}
	
	private void getCarPlan(){
		controller.setAttr("carPlanName",dao.getCarPlan());
		controller.setAttr("resFlag", "0");
	}
	
	private void saveCarPlan(){
		String name = controller.getMyParam("carPlanName").toString();
		if(dao.saveCarPlan(name)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void queryCarCountGroup(){
		controller.setAttrs(dao.queryCarCountGroup());
		controller.setAttr("resFlag", "0");
	}
	
	private void queryCarCountDetailList(){
		String planName = controller.getMyParam("planName") == null ? "" : controller.getMyParam("planName").toString();
		controller.setAttrs(dao.queryCarCountDetailList(planName));
		controller.setAttr("resFlag", "0");
	}

	private void componentList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.componentList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void carList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.carList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void insureanceList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.insureanceList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void queryComponentList(){
		List<Map<String,Object>> componentList = dao.queryComponentList();
		controller.setAttr("componentList",componentList);
		controller.setAttr("resFlag", "0");
	}
	
	private void queryCompanyList(){
		List<Map<String,Object>> companyList = dao.queryCompanyList();
		controller.setAttr("companyList",companyList);
		controller.setAttr("resFlag", "0");
	}
	
	private void saveRepair(){
		List<Map<String, Object>> components = controller.getMyParamList("components");
		Map<String, Object> repairData = controller.getMyParamMap("repairData");
		String repairId = dao.saveRepair(repairData);
		if(!"".equals(repairId)){
			components = dao.saveRepairList(components, repairId);
		}
		if(!"".equals(repairId) && components !=null){
			controller.setAttr("repairId", repairId);
			controller.setAttr("componentList", components);
			controller.setAttr("resFlag", "0");
			controller.setAttr("msg", "保存成功");
		}else{
			controller.setAttr("resFlag", "1");
			controller.setAttr("msg", "保存失败");
		}
	}
	
	private void repairList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.repairList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void queryRepair(){
		String repairId = controller.getMyParam("repairId").toString();
		controller.setAttr("repairData", dao.queryRepair(repairId));
		controller.setAttr("detailListData", dao.queryRepairList(repairId));
		controller.setAttr("resFlag", "0");
		controller.setAttr("msg", "保存成功");
	}
	
	private void delComponentItem(){
		String repairId = controller.getMyParam("repairId").toString();
		String sonId = controller.getMyParam("id").toString();
		if(dao.delComponentItem(repairId, sonId) > 0){
			controller.setAttr("resFlag", "0");
			controller.setAttr("msg", "删除成功");
		}else{
			controller.setAttr("resFlag", "1");
			controller.setAttr("msg", "删除失败");
		}
	}
}
