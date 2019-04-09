package com.mine.rd.services.car.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.mine.pub.controller.BaseController;
import com.mine.pub.kit.DateKit;
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
		            }else if("checkCustomerRepeat".equals(getLastMethodName(7))){
		            	checkCustomerRepeat();
		            }else if("saveCustomer".equals(getLastMethodName(7))){
		            	saveCustomer();
		            }else if("saleAppInit".equals(getLastMethodName(7))){
		            	saleAppInit();
		            }else if("sign".equals(getLastMethodName(7))){
		            	sign();
		            }else if("contractList".equals(getLastMethodName(7))){
		            	contractList();
		            }else if("getContract".equals(getLastMethodName(7))){
		            	getContract();
		            }else if("contractDetailInit".equals(getLastMethodName(7))){
		            	contractDetailInit();
		            }else if("saveContract".equals(getLastMethodName(7))){
		            	saveContract();
		            }else if("contractDetailWebInit".equals(getLastMethodName(7))){
		            	contractDetailWebInit();
		            }else if("saveContractFinance".equals(getLastMethodName(7))){
		            	saveContractFinance();
		            }else if("saveContractCarManage".equals(getLastMethodName(7))){
		            	saveContractCarManage();
		            }else if("saveContractDeliver".equals(getLastMethodName(7))){
		            	saveContractDeliver();
		            }else if("saveContractCheckImg".equals(getLastMethodName(7))){
		            	saveContractCheckImg();
		            }else if("initContractCheckImg".equals(getLastMethodName(7))){
		            	initContractCheckImg();
		            }else if("saveCarShare".equals(getLastMethodName(7))){
		            	saveCarShare();
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
		Map<String,Object> car = dao.querybyPlateNumForRepair(plateNum);
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
	
	private void checkCustomerRepeat(){
		String name = controller.getMyParam("NAME").toString();
		String phone = controller.getMyParam("PHONE").toString();
		Map<String,Object> map = dao.checkCustomerWillRepeat(name, phone);
		String msg = "";
		if(map == null ){
			map = dao.checkCustomerRepeat(name, phone);
			if(map == null){
				msg="该顾客是新顾客，请登记顾客信息";
			}else{
				msg="该顾客已签约过,登记销售员是"+map.get("SALE_USER_NAME")+",登记时间是"+map.get("REGISTER_DATE").toString().substring(0,10);
				controller.setAttr("willFlag", "0");
			}
		}
		else{
			msg="该顾客已登记,登记销售员是"+map.get("SALE_USER_NAME")+",登记时间是"+map.get("REGISTER_DATE").toString().substring(0,10);
			controller.setAttr("willFlag", "1");
		}
		controller.setAttr("userInfo", map);
		controller.setAttr("resFlag", "0");
		controller.setAttr("msg", msg);
	}
	
	private void pubCustomer(Map<String,Object> map,String id,String PERSON_ONLY_IMG,String BUSINESS_LICENSE_IMG,String ENTRUST_IMG,String PERSON_IMG,String userId,String userName){	
		if(controller.getMyParam("PERSON_ONLY_IMG_base64")  != null &&  !"".equals(controller.getMyParam("PERSON_ONLY_IMG_base64").toString())){
			String imgPath = controller.base64ToImgpath(controller.getMyParam("PERSON_ONLY_IMG_base64").toString(),"customer","PERSON_ONLY_IMG_"+controller.getMyParam("PHONE").toString()+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
			imgPath = imgPath.replace("\\", "/");
			PERSON_ONLY_IMG = imgPath;
		}else{
			PERSON_ONLY_IMG = controller.getMyParam("PERSON_ONLY_IMG") == null ? "" : controller.getMyParam("PERSON_ONLY_IMG").toString();
		}
		if(controller.getMyParam("PERSON_IMG_base64")  != null &&  !"".equals(controller.getMyParam("PERSON_IMG_base64").toString())){
			String imgPath = controller.base64ToImgpath(controller.getMyParam("PERSON_IMG_base64").toString(),"customer","PERSON_IMG_"+controller.getMyParam("PHONE").toString()+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
			imgPath = imgPath.replace("\\", "/");
			PERSON_IMG = imgPath;
		}else{
			PERSON_IMG = controller.getMyParam("PERSON_IMG") == null ? "" : controller.getMyParam("PERSON_IMG").toString();
		}
		if(controller.getMyParam("BUSINESS_LICENSE_IMG_base64")  != null &&  !"".equals(controller.getMyParam("BUSINESS_LICENSE_IMG_base64").toString())){
			String imgPath = controller.base64ToImgpath(controller.getMyParam("BUSINESS_LICENSE_IMG_base64").toString(),"customer","BUSINESS_LICENSE_IMG_"+controller.getMyParam("PHONE").toString()+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
			imgPath = imgPath.replace("\\", "/");
			BUSINESS_LICENSE_IMG = imgPath;
		}else{
			BUSINESS_LICENSE_IMG = controller.getMyParam("BUSINESS_LICENSE_IMG") == null ? "" : controller.getMyParam("BUSINESS_LICENSE_IMG").toString();
		}
		if(controller.getMyParam("ENTRUST_IMG_base64")  != null &&  !"".equals(controller.getMyParam("ENTRUST_IMG_base64").toString())){
			String imgPath = controller.base64ToImgpath(controller.getMyParam("ENTRUST_IMG_base64").toString(),"customer","ENTRUST_IMG_"+controller.getMyParam("PHONE").toString()+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
			imgPath = imgPath.replace("\\", "/");
			ENTRUST_IMG = imgPath;
		}else{
			ENTRUST_IMG = controller.getMyParam("ENTRUST_IMG") == null ? "" : controller.getMyParam("ENTRUST_IMG").toString();
		}
		map.put("ID", id);
		map.put("NAME", controller.getMyParam("NAME"));
		map.put("PERSON_CODE", controller.getMyParam("PERSON_CODE"));
		map.put("PERSON_ADDRESS", controller.getMyParam("PERSON_ADDRESS"));
		map.put("PHONE", controller.getMyParam("PHONE"));
		map.put("WORK", controller.getMyParam("WORK"));
		map.put("SOURCE", controller.getMyParam("SOURCE"));
		map.put("WILL_GENDER", controller.getMyParam("WILL_GENDER"));
		map.put("WILL_AGE", controller.getMyParam("WILL_AGE"));
		map.put("WILL_INTENTION", controller.getMyParam("WILL_INTENTION"));
		map.put("WILL_ITH", controller.getMyParam("WILL_ITH"));
		map.put("EP_NAME", controller.getMyParam("EP_NAME"));
		map.put("EP_ADDRESS", controller.getMyParam("EP_ADDRESS"));
		map.put("EP_CODE", controller.getMyParam("EP_CODE"));
		map.put("PERSON_ONLY_IMG", PERSON_ONLY_IMG);
		map.put("PERSON_IMG", PERSON_IMG);
		map.put("BUSINESS_LICENSE_IMG", BUSINESS_LICENSE_IMG);
		map.put("ENTRUST_IMG", ENTRUST_IMG);
		map.put("EMERGENCY_PERSON", controller.getMyParam("EMERGENCY_PERSON"));
		map.put("EMERGENCY_PHONE", controller.getMyParam("EMERGENCY_PHONE"));
		map.put("SALE_USER_ID",userId);
		map.put("SALE_USER_NAME",userName);
	}
	
	private void saveCustomer(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		String id = controller.getMyParam("ID") == null ? "" : controller.getMyParam("ID").toString();
		String PERSON_ONLY_IMG = "";
		String PERSON_IMG = "";
		String BUSINESS_LICENSE_IMG = "";
		String ENTRUST_IMG = "";
		Map<String,Object> map = new HashMap<String,Object>();
		pubCustomer(map, id, PERSON_ONLY_IMG, BUSINESS_LICENSE_IMG, ENTRUST_IMG, PERSON_IMG, userId, userName);
		if("".equals(id)){
			if(dao.addCustomerWill(map)){
				controller.setAttr("userInfo", map);
				controller.setAttr("resFlag", "0");
				controller.setAttr("msg", "登记成功");
				Map<String,Object> mapLog = new HashMap<String,Object>();
				mapLog.put("fromPlatform", "app");
				mapLog.put("content", userId+"|"+userName+"|"+controller.getMyParam("NAME") +"|"+controller.getMyParam("PHONE"));
				mapLog.put("BIZ_NAME", "登记客户");
				dao.addLogFlow(mapLog);
			}else{
				controller.setAttr("resFlag", "1");
				controller.setAttr("msg", "登记失败");
			}
		}else{
			String willFlag = controller.getMyParam("willFlag") == null ? "" : controller.getMyParam("willFlag").toString();
			boolean flag = false;
			if("0".equals(willFlag)){
				flag = dao.updateCustomer(map);
			}else{
				flag = dao.updateCustomerWill(map);
			}
			if(flag){
				controller.setAttr("userInfo", map);
				controller.setAttr("resFlag", "0");
				controller.setAttr("msg", "更新成功");
				Map<String,Object> mapLog = new HashMap<String,Object>();
				mapLog.put("fromPlatform", "app");
				mapLog.put("content", userId+"|"+userName+"|" + id + "|" +controller.getMyParam("NAME") +"|"+controller.getMyParam("PHONE"));
				mapLog.put("BIZ_NAME", "更新客户信息");
				dao.addLogFlow(mapLog);
			}else{
				controller.setAttr("resFlag", "1");
				controller.setAttr("msg", "更新失败");
			}
			
		}
	}
	
	private void saleAppInit(){
		controller.setAttr("sourceList",dao.querySelectList("source"));
		controller.setAttr("intentionList",dao.querySelectList("intention"));
		controller.setAttr("ithList",dao.querySelectList("ith"));
		controller.setAttr("resFlag", "0");
	}
	
	private void sign(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		String id = controller.getMyParam("ID") == null ? "" : controller.getMyParam("ID").toString();
		String PERSON_ONLY_IMG = "";
		String PERSON_IMG = "";
		String BUSINESS_LICENSE_IMG = "";
		String ENTRUST_IMG = "";
		Map<String,Object> map = new HashMap<String,Object>();
		pubCustomer(map, id, PERSON_ONLY_IMG, BUSINESS_LICENSE_IMG, ENTRUST_IMG, PERSON_IMG, userId, userName);
		if("".equals(id)){
			if(dao.addCustomer(map)){
				if(dao.addContract(map)){
					controller.setAttr("userInfo", map);
					controller.setAttr("resFlag", "0");
					controller.setAttr("msg", "签约成功");
					Map<String,Object> mapLog = new HashMap<String,Object>();
					mapLog.put("fromPlatform", "app");
					mapLog.put("content", userId+"|"+userName+"|"+controller.getMyParam("NAME") +"|"+controller.getMyParam("PHONE"));
					mapLog.put("BIZ_NAME", "登记客户");
					dao.addLogFlow(mapLog);
				}else{
					controller.setAttr("resFlag", "1");
					controller.setAttr("msg", "签约失败");
				}
			}else{
				controller.setAttr("resFlag", "1");
				controller.setAttr("msg", "签约失败");
			}
		}else{
			String willFlag = controller.getMyParam("willFlag") == null ? "" : controller.getMyParam("willFlag").toString();
			boolean flag = false;
			boolean flag_contract = false;
			if("0".equals(willFlag)){
				flag = dao.updateCustomer(map);
				flag_contract = dao.addContract(map);
			}else{
				flag = dao.updateCustomerWill(map);
				flag_contract = dao.addContract(map);
				dao.customerInfoTransfer(id);
			}
			if(flag && flag_contract){
				controller.setAttr("resFlag", "0");
				controller.setAttr("msg", "签约成功");
				controller.setAttr("userInfo", map);
				Map<String,Object> mapLog = new HashMap<String,Object>();
				mapLog.put("fromPlatform", "app");
				mapLog.put("content", userId+"|"+userName+"|" + id + "|" +controller.getMyParam("NAME") +"|"+controller.getMyParam("PHONE"));
				mapLog.put("BIZ_NAME", "更新客户信息");
				dao.addLogFlow(mapLog);
			}else{
				controller.setAttr("resFlag", "1");
				controller.setAttr("msg", "签约失败");
			}
			
		}
	}
	
	private void contractList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.contractList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void getContract(){
		String id = controller.getMyParam("id").toString();
		Map<String,Object> map = dao.getContract(id);
		if(map != null){
			controller.setAttr("contract",map);
			controller.setAttr("contractFinance",dao.getContractFinance(id));
			controller.setAttr("contractCarManage",dao.getContractCarManage(id));
			controller.setAttr("contractDeliver",dao.contractDeliver(id));
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","查不到信息");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void contractDetailInit(){
		controller.setAttr("typeList",dao.querySelectList("contract_type"));
		controller.setAttr("priceList",dao.querySelectList("car_price"));
		controller.setAttr("carList",dao.querySelectCarList());
		controller.setAttr("resFlag", "0");
	}
	
	private void saveContract(){
		Map<String, Object> map = controller.getMyParamMap("contract");
		boolean flag = dao.updateContract(map);
		int resInt = 0;
		if(map.get("CAR_DRIVER_NUM") != null && !"".equals(map.get("CAR_DRIVER_NUM"))){
			resInt = dao.updateCarDriverNum(map.get("CAR_ID").toString(), map.get("CAR_DRIVER_NUM").toString());
		}
		if(flag && resInt > 0){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void contractDetailWebInit(){
		controller.setAttr("agentList",dao.querySelectList("agent"));
		controller.setAttr("payPeriodList",dao.querySelectList("pay_period"));
		controller.setAttr("payTypeList",dao.querySelectList("pay_type"));
		controller.setAttr("resFlag", "0");
	}
	
	private void saveContractFinance(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		Map<String, Object> map = controller.getMyParamMap("contractFinance");
		map.put("USER_ID", userId);
		map.put("USER_NAME", userName);
		boolean flag = dao.saveContractFinance(map);
		if(flag){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void saveContractCarManage(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		Map<String, Object> map = controller.getMyParamMap("contractCarManage");
		map.put("USER_ID", userId);
		map.put("USER_NAME", userName);
		boolean flag = dao.saveContractCarManage(map);
		if(flag){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void saveContractDeliver(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		Map<String, Object> map = controller.getMyParamMap("contractDeliver");
		map.put("USER_ID", userId);
		map.put("USER_NAME", userName);
		boolean flag = dao.saveContractDeliver(map);
		if(flag){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void saveContractCheckImg(){
		String id = controller.getMyParam("id").toString();
		String type = controller.getMyParam("type").toString();
		List<Map<String, Object>> list = controller.getMyParamList("items");
		for(int i=0 ; i< list.size() ; i++){
			if(list.get(i).get("IMG_base64") != null &&  !"".equals(list.get(i).get("IMG_base64").toString())){
				String imgPath = controller.base64ToImgpath(list.get(i).get("IMG_base64").toString(),"CHECK_CAR"+"_"+list.get(i).get("ID"),list.get(i).get("TYPE")+"_"+DateKit.toStr(dao.getSysdate(), "yyyyMMddHHmmss"));
				list.get(i).put("IMG", imgPath);
			}	
		}
		if(dao.saveCheckCar(id, list,type)){
			controller.setAttr("items",list);
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("items",list);
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void initContractCheckImg(){
		String id = controller.getMyParam("id").toString();
		String type = controller.getMyParam("type").toString();
		controller.setAttr("dataList",dao.initContractCheckImg(id,type));
		controller.setAttr("resFlag", "0");
	}
	
	private void saveCarShare(){
		String carId = controller.getMyParam("carId").toString();
		String plateNum = controller.getMyParam("plateNum").toString();
		if(dao.saveCarShare(carId,plateNum)){
			controller.setAttr("msg","提车成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","提车失败");
			controller.setAttr("resFlag", "0");
		};
	}
}
