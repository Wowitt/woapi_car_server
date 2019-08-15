package com.mine.rd.services.car.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
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
		            }else if("saveFinanceFlow".equals(getLastMethodName(7))){
		            	saveFinanceFlow();
		            }else if("queryFinanceFlowList".equals(getLastMethodName(7))){
		            	queryFinanceFlowList();
		            }else if("delFinanceFlow".equals(getLastMethodName(7))){
		            	delFinanceFlow();
		            }else if("queryCarTable".equals(getLastMethodName(7))){
		            	queryCarTable();
		            }else if("saveCarTable".equals(getLastMethodName(7))){
		            	saveCarTable();
		            }else if("delCarTable".equals(getLastMethodName(7))){
		            	delCarTable();
		            }else if("saveCarTableDeliver".equals(getLastMethodName(7))){
		            	saveCarTableDeliver();
		            }else if("contractUnfinishList".equals(getLastMethodName(7))){
		            	contractUnfinishList();
		            }else if("saveCarTableOne".equals(getLastMethodName(7))){
		            	saveCarTableOne();
		            }else if("queryAndCarTablebyCardno".equals(getLastMethodName(7))){
		            	queryAndCarTablebyCardno();
		            }
		            else if("customerList".equals(getLastMethodName(7))){
		            	customerList();
		            }
		            else if("carparkList".equals(getLastMethodName(7))){
		            	carparkList();
		            }
		            else if("savecarpark".equals(getLastMethodName(7))){
		            	savecarpark();
		            }
		            else if("carListForYearCheck".equals(getLastMethodName(7))){
		            	carListForYearCheck();
		            }
		            else if("updateCarDriverYear".equals(getLastMethodName(7))){
		            	updateCarDriverYear();
		            }
		            else if("updateInsureance".equals(getLastMethodName(7))){
		            	updateInsureance();
		            }
		            else if("saveBreakRules".equals(getLastMethodName(7))){
		            	saveBreakRules();
		            }
		            else if("breakrulesList".equals(getLastMethodName(7))){
		            	breakrulesList();
		            }
		            else if("initFinanceFlow".equals(getLastMethodName(7))){
		            	initFinanceFlow();
		            }
		            else if("queryCarTableBack".equals(getLastMethodName(7))){
		            	queryCarTableBack();
		            }
		            else if("saveCarTableBack".equals(getLastMethodName(7))){
		            	saveCarTableBack();
		            }
		            else if("saveContractBack".equals(getLastMethodName(7))){
		            	saveContractBack();
		            }
		            else if("updateContractForBack".equals(getLastMethodName(7))){
		            	updateContractForBack();
		            }
		            else if("contractForReplaceCarList".equals(getLastMethodName(7))){
		            	contractForReplaceCarList();
		            }
		            else if("startReplaceCar".equals(getLastMethodName(7))){
		            	startReplaceCar();
		            }
		            else if("replaceCarList".equals(getLastMethodName(7))){
		            	replaceCarList();
		            }
		            else if("replaceCarInfo".equals(getLastMethodName(7))){
		            	replaceCarInfo();
		            }
		            else if("saveReplaceOfRepair".equals(getLastMethodName(7))){
		            	saveReplaceOfRepair();
		            }
		            else if("queryCarTableReplace".equals(getLastMethodName(7))){
		            	queryCarTableReplace();
		            }
		            else if("saveCarTableReplace".equals(getLastMethodName(7))){
		            	saveCarTableReplace();
		            }
		            else if("saveReplaceCarManage".equals(getLastMethodName(7))){
		            	saveReplaceCarManage();
		            }
		            else if("createWord".equals(getLastMethodName(7))){
		            	createWord();
		            }
		            else if("repairFlowList".equals(getLastMethodName(7))){
		            	repairFlowList();
		            }
		            else if("saveRepairFlow".equals(getLastMethodName(7))){
		            	saveRepairFlow();
		            }
		            else if("queryRepairFlow".equals(getLastMethodName(7))){
		            	queryRepairFlow();
		            }
		            else if("bangCar".equals(getLastMethodName(7))){
		            	bangCar();
		            }
		            else if("saveRepairPay".equals(getLastMethodName(7))){
		            	saveRepairPay();
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
		long componentCount = dao.getComponentCount();
		controller.setAttr("carCount",carCount);
		controller.setAttr("componentCount",componentCount);
		controller.setAttr("resFlag", "0");
	}
	
	private void queryAndCarTablebyCardno(){
		String cardno = controller.getMyParam("cardno").toString();
		String contractId = controller.getMyParam("contractId").toString();
		Map<String,Object> car = dao.querybyCardno(cardno);
		if(car != null){
			String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
			String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
			car.put("ID", contractId);
			car.put("PERSON_ID",userId);
			car.put("PERSON_NAME",userName);
			dao.saveCarTableOne(car);
			controller.setAttr("car",car);
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","查不到车辆信息");
			controller.setAttr("resFlag", "1");
		}
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
//		Map<String, Object> financeFlow = controller.getMyParamMap("financeFlow");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		repairData.put("USER_ID",userId);
		repairData.put("USER_NAME",userName);
//		if("".equals(repairData.get("CU_NAME"))){
//			Map<String, Object> c = new HashMap<String, Object>();
//			c.put("NAME", financeFlow.get("PAYER_NAME"));
//			c.put("PHONE", repairData.get("CU_PHONE"));
//			c.put("TYPE", "0");
//			c.put("SOURCE", "维修手动添加");
//			dao.addCustomerFromRepair(c);
//			repairData.put("CU_NAME", c.get("NAME"));
//			repairData.put("CU_PHONE", c.get("PHONE"));
//			financeFlow.put("PAYER_ID", c.get("ID"));
//		}
		String repairId = dao.saveRepair(repairData);	
//		financeFlow.put("PERSON_ID", userId);
//		financeFlow.put("PERSON_NAME", userName);
//		financeFlow.put("BIZ_ID", repairId);
//		if(!"".equals(financeFlow.get("ID"))){
//			 dao.delFinanceFlow(financeFlow.get("ID").toString());
//		}
//		boolean saveFinanceFlag = dao.saveFinanceFlow(financeFlow);
		if(!"".equals(repairId)){
			dao.updateStore(components, repairId);
			components = dao.saveRepairList(components, repairId);
			dao.saveRepairFlow(components,repairId);
		}
		if(!"".equals(repairId) && components !=null){
			controller.setAttr("repairId", repairId);
			String pat1 = "yyyy-MM-dd HH:mm:ss.SSS";
			SimpleDateFormat sdf = new SimpleDateFormat(pat1);
			controller.setAttr("begindate",sdf.format(dao.getSysdate()) );
			controller.setAttr("componentList", components);
//			controller.setAttr("financeFlow", financeFlow);
			controller.setAttr("resFlag", "0");
			controller.setAttr("msg", "保存成功");
		}else{
			controller.setAttr("resFlag", "1");
			controller.setAttr("msg", "保存失败");
		}
	}
	
	private void saveRepairPay(){
		List<Map<String, Object>> components = controller.getMyParamList("components");
		Map<String, Object> repairData = controller.getMyParamMap("repairData");
		Map<String, Object> financeFlow = controller.getMyParamMap("financeFlow");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		repairData.put("USER_ID",userId);
		repairData.put("USER_NAME",userName);
		if(!"".equals(financeFlow.get("PAYER_NAME")) && "".equals(repairData.get("CU_NAME"))){
			Map<String, Object> c = new HashMap<String, Object>();
			c.put("NAME", financeFlow.get("PAYER_NAME"));
			c.put("PHONE", repairData.get("CU_PHONE"));
			c.put("TYPE", "0");
			c.put("SOURCE", "维修手动添加");
			dao.addCustomerFromRepair(c);
			repairData.put("CU_NAME", c.get("NAME"));
			repairData.put("CU_PHONE", c.get("PHONE"));
			financeFlow.put("PAYER_ID", c.get("ID"));
		}
		String repairId = dao.saveRepair(repairData);	
		financeFlow.put("PERSON_ID", userId);
		financeFlow.put("PERSON_NAME", userName);
		financeFlow.put("BIZ_ID", repairId);
		if(!"".equals(financeFlow.get("ID"))){
			dao.delFinanceFlow(financeFlow.get("ID").toString());
		}
		boolean saveFinanceFlag = dao.saveFinanceFlow(financeFlow);
		if(!"".equals(repairId)){
			dao.updateStore(components, repairId);
			components = dao.saveRepairList(components, repairId);
			dao.saveRepairFlow(components,repairId);
		}
		if(!"".equals(repairId) && components !=null && saveFinanceFlag){
			controller.setAttr("repairId", repairId);
			String pat1 = "yyyy-MM-dd HH:mm:ss.SSS";
			SimpleDateFormat sdf = new SimpleDateFormat(pat1);
			controller.setAttr("begindate",sdf.format(dao.getSysdate()) );
			controller.setAttr("componentList", components);
			controller.setAttr("financeFlow", financeFlow);
			controller.setAttr("resFlag", "0");
			controller.setAttr("msg", "保存成功");
		}else{
			controller.setAttr("resFlag", "1");
			controller.setAttr("msg", "保存失败");
		}
	}
	
	private void saveRepairFlow(){
		Map<String, Object> dataFlow = controller.getMyParamMap("dataFlow");
		Map<String, Object> financeFlow = controller.getMyParamMap("financeFlow");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		dao.updateComponent(dataFlow);
		dataFlow.put("direction", "入");
		String dataFlowId = dao.saveRepairFlow(dataFlow);	
		financeFlow.put("PERSON_ID", userId);
		financeFlow.put("PERSON_NAME", userName);
		financeFlow.put("BIZ_ID", dataFlowId);
		if(!"".equals(financeFlow.get("ID"))){
			dao.delFinanceFlow(financeFlow.get("ID").toString());
		}
		boolean saveFinanceFlag = dao.saveFinanceFlow(financeFlow);
		if(!"".equals(dataFlowId) && saveFinanceFlag){
			controller.setAttr("dataFlowId", dataFlowId);
			controller.setAttr("financeFlow", financeFlow);
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
	
	private void repairFlowList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.repairFlowList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void queryRepair(){
		String repairId = controller.getMyParam("repairId").toString();
		controller.setAttr("repairData", dao.queryRepair(repairId));
		controller.setAttr("financeFlow", dao.queryFinanceFlow(repairId).isEmpty()  ? "" : dao.queryFinanceFlow(repairId).get(0));
		controller.setAttr("detailListData", dao.queryRepairList(repairId));
		controller.setAttr("companyPayModeList",dao.initCompanyPayMode());
		controller.setAttr("resFlag", "0");
	}
	
	private void queryRepairFlow(){
		String flowId = controller.getMyParam("flowId").toString();
		controller.setAttr("dataFlow", dao.queryRepairFlow(flowId));
		controller.setAttr("financeFlow", dao.queryFinanceFlow(flowId).get(0));
		controller.setAttr("companyPayModeList",dao.initCompanyPayMode());
		controller.setAttr("resFlag", "0");
		controller.setAttr("msg", "保存成功");
	}
	
	private void delComponentItem(){
		String repairId = controller.getMyParam("repairId").toString();
		String sonId = controller.getMyParam("id").toString();
		dao.updateStoreForDel(repairId, sonId);
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
				List<Map<String,Object>> cuPricelist = dao.getCuPriceList(map.get("ID").toString());
				controller.setAttr("cuPricelist", cuPricelist);
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
		map.put("WILL_RENT_MONTH", controller.getMyParam("WILL_RENT_MONTH"));
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
		List<Map<String, Object>> gearList = controller.getMyParamList("gearList");
		if("".equals(id)){
			if(dao.addCustomerWill(map)){
				dao.saveCuPrice(id, gearList);
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
			dao.saveCuPrice(id, gearList);
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
		controller.setAttr("workList",dao.querySelectList("work"));
		controller.setAttr("gearList",dao.queryGearList());
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
		List<Map<String, Object>> gearList = controller.getMyParamList("gearList");
		if("".equals(id)){
			if(dao.addCustomer(map)){
				if(dao.addContract(map)){
					dao.saveCuCoPrice(id,map.get("contractId").toString(), gearList);
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
			dao.saveCuCoPrice(id,map.get("contractId").toString(), gearList);
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
	
	private void contractForReplaceCarList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.contractForReplaceCarList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void contractUnfinishList(){
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
			controller.setAttr("contractBack",dao.getContractBack(id));
//			controller.setAttr("contractCarManage",dao.getContractCarManage(id));
//			controller.setAttr("contractDeliver",dao.contractDeliver(id));
			List<Map<String,Object>> cucoPricelist = dao.getCuCoPriceList(id);
			controller.setAttr("cucoPricelist", cucoPricelist);
			controller.setAttr("financeFlowList", dao.queryFinanceFlow(id));
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","查不到信息");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void contractDetailInit(){
		controller.setAttr("typeList",dao.querySelectList("contract_type"));
		controller.setAttr("priceList",dao.querySelectList("car_price"));
		controller.setAttr("constractNameList",dao.querySelectList("constract_name"));
		controller.setAttr("payPeriodList",dao.querySelectList("pay_period"));
		controller.setAttr("carList",dao.querySelectCarList());
		controller.setAttr("resFlag", "0");
	}
	
	private void saveContract(){
		Map<String, Object> map = controller.getMyParamMap("contract");
		boolean flag = dao.updateContract(map);
		List<Map<String, Object>> cucoPricelist = controller.getMyParamList("cucoPricelist");
		dao.saveCuCoPrice(map.get("CU_ID").toString(),map.get("ID").toString(), cucoPricelist);
		if(flag){
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
	
	private void saveContractBack(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		Map<String, Object> map = controller.getMyParamMap("contractBack");
		map.put("USER_ID", userId);
		map.put("USER_NAME", userName);
		map.put("STATUS", "1");
		boolean flag = dao.saveContractBack(map);
		if(flag){
			controller.setAttr("map",map);
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
			controller.setAttr("msg","提交成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","提交失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void saveReplaceCarManage(){
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		Map<String, Object> map = controller.getMyParamMap("replaceCarManage");
		map.put("USER_ID", userId);
		map.put("USER_NAME", userName);
		boolean flag = dao.saveReplaceCarManage(map);
		if(flag){
			controller.setAttr("msg","提交成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","提交失败");
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
			controller.setAttr("msg","提交成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","提交失败");
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
	
	private void saveFinanceFlow(){
		Map<String, Object> map = controller.getMyParamMap("parm");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		map.put("PERSON_ID", userId);
		map.put("PERSON_NAME", userName);
		if(dao.saveFinanceFlow(map)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void queryFinanceFlowList(){
		String BIZ_ID = controller.getMyParam("id").toString();
		controller.setAttr("dataList",dao.queryFinanceFlow(BIZ_ID));
		controller.setAttr("resFlag", "0");
	}
	
	private void queryCarTable(){
		String id = controller.getMyParam("id").toString();
		controller.setAttr("dataList",dao.queryCarTable(id));
		controller.setAttr("resFlag", "0");
	}
	
	private void queryCarTableReplace(){
		String id = controller.getMyParam("id").toString();
		controller.setAttr("dataList",dao.queryCarTableReplace(id));
		controller.setAttr("resFlag", "0");
	}
	
	private void queryCarTableBack(){
		String id = controller.getMyParam("id").toString();
		List<Map<String,Object>> resList = dao.queryCarTableBack(id) ;
		if(resList == null || resList.size() < 1){
			dao.initCarTableBack(id);
			controller.setAttr("dataList",dao.queryCarTable(id));
		}else{
			controller.setAttr("dataList", resList);
		}
		controller.setAttr("resFlag", "0");
	}
	
	private void delFinanceFlow(){
		String id = controller.getMyParam("id").toString();
		if(dao.delFinanceFlow(id) > 0){
			controller.setAttr("resFlag", "0");
		}
	}
	
	private void saveCarTable(){
		List<Map<String, Object>> list = controller.getMyParamList("list");
		if(dao.saveCarTable(list) && dao.updateCarPart(list)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void saveCarTableReplace(){
		List<Map<String, Object>> list = controller.getMyParamList("list");
		if(dao.saveCarTableReplace(list) && dao.updateCarPart(list)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void saveCarTableBack(){
		List<Map<String, Object>> list = controller.getMyParamList("list");
		if(dao.saveCarTableBack(list) && dao.updateCarPart(list)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void saveCarTableDeliver(){
		List<Map<String, Object>> list = controller.getMyParamList("list");
		if(dao.saveCarTableDeliver(list) && dao.updateCarPartDeliver(list)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void delCarTable(){
		if(dao.delCarTable(controller.getMyParamMap("obj"))){
			controller.setAttr("msg","删除成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","删除失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void saveCarTableOne(){
		Map<String, Object> map = controller.getMyParamMap("obj");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		map.put("PERSON_ID", userId);
		map.put("PERSON_NAME", userName);
		if(dao.saveCarTableOne(map) && dao.updateCarOne(map)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void customerList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		String type = controller.getMyParam("type") == null ? "" : controller.getMyParam("type").toString();
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs("1".equals(type) ? dao.customerList(pn, ps,searchContent) : dao.customerwillList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void carListForYearCheck(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.carListForYearCheck(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void carparkList(){
		controller.setAttr("dataList",dao.carparkList());
		controller.setAttr("resFlag", "0");
	}
	
	private void savecarpark(){
		List<Map<String, Object>> list = controller.getMyParamList("list");
		controller.setAttr("dataList",dao.savecarpark(list));
		controller.setAttr("msg","保存成功");
		controller.setAttr("resFlag", "0");
	}
	
	private void updateCarDriverYear(){
		Map<String, Object> map = controller.getMyParamMap("obj");
		if(dao.updateCarDriverYear(map)){
			String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
			String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
			Map<String,Object> mapLog = new HashMap<String,Object>();
			mapLog.put("fromPlatform", "web");
			mapLog.put("content", userId+"|"+userName+"|" + map.get("ID") + "|" +map.get("PLATE_NUM") +"|"+map.get("DRIVER_YEAR"));
			mapLog.put("BIZ_NAME", "更新车辆年检时间");
			dao.addLogFlow(mapLog);
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void updateInsureance(){
		Map<String, Object> map = controller.getMyParamMap("obj");
		if(dao.updateInsureance(map)){
			String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
			String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
			Map<String,Object> mapLog = new HashMap<String,Object>();
			mapLog.put("fromPlatform", "web");
			mapLog.put("content", userId+"|"+userName+"|" + map.get("ID") + "|" +map.get("PLATE_NUM") +"|"+map.get("FORCE_IS") +"|"+map.get("FORCE_DATE") +"|"+map.get("BUS_IS") +"|"+map.get("BUS_DATE"));
			mapLog.put("BIZ_NAME", "更新车辆保险");
			dao.addLogFlow(mapLog);
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "0");
		};
	}
	
	private void saveBreakRules(){
		Map<String, Object> map = controller.getMyParamMap("obj");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		map.put("PERSON_ID", userId);
		map.put("PERSON_NAME", userName);
		if("".equals(map.get("ID")) ? dao.saveBreakRules(map) : dao.updateBreakRules(map)){
			controller.setAttr("map",map);
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void breakrulesList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.breakrulesList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void initFinanceFlow(){
		controller.setAttr("companyPayModeList",dao.initCompanyPayMode());
		controller.setAttr("resFlag", "0");
	}
	
	private void replaceCarInfo(){
		Map<String, Object> map = controller.getMyParamMap("obj");
		controller.setAttr("replaceCar",dao.replaceCar(map));
		controller.setAttr("carTable",dao.carTable(map));
		controller.setAttr("financeFlowList", dao.queryFinanceFlow(map.get("ID").toString()));
		controller.setAttr("resFlag", "0");
	}
	
	private void updateContractForBack(){
		String id = controller.getMyParam("id").toString();
		if(dao.updateContractForBack(id)){
			controller.setAttr("msg","提交成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","提交失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void startReplaceCar(){
		Map<String, Object> map = controller.getMyParamMap("obj");
		String userId = controller.getMySession("userId") == null ? "" : controller.getMySession("userId").toString();
		String userName = controller.getMySession("userName") == null ? "" : controller.getMySession("userName").toString();
		map.put("PERSON_ID", userId);
		map.put("PERSON_NAME", userName);
		if(dao.checkReplaceCar(map) > 0){
			controller.setAttr("msg","已发起过");
			controller.setAttr("resFlag", "2");
		}
		else{
			if(dao.startReplaceCar(map)){
				if(dao.startReplaceCarCarTable(map)){
					controller.setAttr("msg","启动成功");
					controller.setAttr("resFlag", "0");
				}else{
					controller.setAttr("msg","启动成功");
					controller.setAttr("resFlag", "0");
				}
			}else{
				controller.setAttr("msg","启动失败");
				controller.setAttr("resFlag", "1");
			}
		}
	}
	
	private void replaceCarList(){
		pn = Integer.parseInt(controller.getMyParam("pn").toString());
		ps = Integer.parseInt(controller.getMyParam("ps").toString());
		Object searchContent = controller.getMyParam("searchContent");
		controller.setAttrs(dao.replaceList(pn, ps,searchContent));
		controller.setAttr("resFlag", "0");
	}
	
	private void saveReplaceOfRepair(){
		Map<String, Object> replaceCar = controller.getMyParamMap("replaceCar");
		Map<String, Object> carTable = controller.getMyParamMap("carTable");
		if(dao.updateCarTable(carTable) > 0 && dao.updateCarTable(replaceCar) > 0 && dao.updateReplaceCarCarTable(replaceCar) > 0 && dao.updateReplaceCar(replaceCar)){
			controller.setAttr("msg","保存成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","保存失败");
			controller.setAttr("resFlag", "1");
		}
	}
	
	private void createWord() throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
        String datetime = DateKit.toStr(new Date(),"yyyyMMddHHmmssSSS");
        String BIZ_ID = controller.getMyParam("BIZ_ID").toString();
        dao.createWordMap(map,BIZ_ID);
        String sourcePath = PathKit.getWebRootPath() + PropKit.get("word_template") + controller.getMyParam("templateName");
        String downloadPath = "/upload/words/" + BIZ_ID + "_" + datetime + controller.getMyParam("templateName");
        String targetPath = PathKit.getWebRootPath() + downloadPath;
        replaceAndSaveDoc(sourcePath, map , targetPath);
        if(dao.updateContractForFile(controller.getMyParam("id").toString(),downloadPath)){
        	controller.setAttr("downloadPath",downloadPath);
        	controller.setAttr("msg","生成成功");
			controller.setAttr("resFlag", "0");
        }else{
        	controller.setAttr("msg","生成失败");
			controller.setAttr("resFlag", "1");
        }
	}
	
	private void replaceAndSaveDoc(String sourcePath, Map<String, Object> param , String targetPath) throws Exception{
        // 读取word模板
        File f = new File(sourcePath);
        if(!f.exists()){
            throw new RuntimeException("未读取到源文件");
        }
        InputStream fis = new FileInputStream(f);
        XWPFDocument doc = replaceDoc(fis, param);
        outPutWord(doc , targetPath);
    }
	
	/**
     * 读取word模板并替换变量
     * @param fis   模版文件流
     * @param param 要替换的键值对
     * @return
     */
    private XWPFDocument replaceDoc(InputStream fis, Map<String, Object> param) throws Exception{
        XWPFDocument doc = new XWPFDocument(fis);
        //处理段落
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        processParagraph(paragraphList,doc,param);
        //处理表格
        Iterator<XWPFTable> it = doc.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                    processParagraph(paragraphListTable, doc, param);
                }
            }
        }
        return doc;
    }
    
    /**
     * 替换文字
     * @param paragraphList
     * @param doc
     * @param param
     */
    private void processParagraph(List<XWPFParagraph> paragraphList, XWPFDocument doc,Map<String, Object> param){
        if(paragraphList != null && paragraphList.size() > 0){
            for(XWPFParagraph paragraph:paragraphList){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if(text != null){
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if(text.indexOf(key) != -1){
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                    //System.out.println(text);//放开注释可打印读取到的word内容
                                }
                                else{
                                    text = text.replace(key, "");
                                }
                            }
                        }
                        if(isSetText){
                            run.setText(text,0);
                        }
                    }
                }
            }
        }
    }
	
	/**
     * 输出word到目标路径
     * @param doc 文档对象
     * @param targetPath 目标路径
     * @throws FileNotFoundException
     */
    private void outPutWord(XWPFDocument doc , String targetPath) throws Exception{
    	if (doc != null) {
            OutputStream os = new FileOutputStream(targetPath);
            doc.write(os);
            os.close();
            System.out.println("已替换word文件，文件地址：" + targetPath);
        }
    }
    
    private void bangCar(){
		String cardno = controller.getMyParam("cardno") == null ? "" : controller.getMyParam("cardno").toString();
		String plateNum = controller.getMyParam("plateNum") == null ? "" : controller.getMyParam("plateNum").toString();
		if(dao.bangCar(plateNum,cardno)){
			controller.setAttr("msg","绑定成功");
			controller.setAttr("resFlag", "0");
		}else{
			controller.setAttr("msg","绑定失败");
			controller.setAttr("resFlag", "1");
		}
	}
}
