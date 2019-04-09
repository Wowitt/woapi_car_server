package com.mine.rd.services.car.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mine.pub.pojo.BaseDao;

public class CarDao extends BaseDao {
	
	public Map<String,Object> querybyCardno(String cardno){
		Record record = Db.findFirst("select * from C_WOBO_CAR where cardno = ? ",cardno);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> querybyPlateNum(String plateNum){
		Record record = Db.findFirst("select a.*,b.USER_ID,b.USER_NAME from C_WOBO_CAR a , C_WOBO_CARSALEMAN b where a.ENGINE_NUMBER=b.ENGINE_NUMBER and a.plate_Num = ? ",plateNum);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> querybyPlateNumForRepair(String plateNum){
		Record record = Db.findFirst("select a.* from C_WOBO_CAR a where a.plate_Num = ?",plateNum);
		Record contract = Db.findFirst("select * from C_WOBO_CONTRACT a where a.CAR_PLATE_NUM = ? and STATUS = '1' order by DELIVER_DATE desc",plateNum);
		if(contract != null){
			record.set("CU_TYPE", contract.get("CU_TYPE"));
			record.set("CU_NAME", contract.get("CU_NAME"));
			record.set("CU_PHONE", contract.get("CU_PHONE"));
			record.set("CU_EP_NAME", contract.get("CU_EP_NAME"));
			record.set("CONTRACT_ID", contract.get("ID"));
			record.set("CONTRACT_TYPE", contract.get("TYPE"));
			record.set("CONTRACT_NAME", contract.get("CONTRACT_NAME"));
			record.set("SALE_USER_ID", contract.get("SALE_USER_ID"));
			record.set("SALE_USER_NAME", contract.get("SALE_USER_NAME"));
		}
		return record != null ? record.getColumns() : null;
	}
	
	public int updateCarFieldInfo(String carId,String fieldId,String fieldName){
		return Db.update("update C_WOBO_CAR set FIELD_ID = ? , FIELD_NAME = ? where ID = ? ",fieldId,fieldName,carId);
	}
	
	public void addCarCount(String carId,String plateNum,String userId,String username,String planName){
		Record countRecord = Db.findFirst("select count(1) num from C_WOBO_CARCOUNT where CAR_ID = ? and user_id =? and planname = ? ",carId,userId,planName);
		long num = countRecord.getLong("num");
		if(num < 1){
			Record record = new Record();
			record.set("CAR_ID", carId);
			record.set("PLATE_NUM", plateNum);
			record.set("USER_ID", userId);
			record.set("USER_NAME", username);
			record.set("planName", planName);
			record.set("actiondate", getSysdate());
			Db.save("C_WOBO_CARCOUNT", record);
		}
	}
	
	public List<Map<String,Object>> queryCarField(){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<Record> list = Db.find("select * from C_WOBO_CARFIELD where status = '1' ");
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public List<Map<String,Object>> queryCarPark(String fieldId){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<Record> list = Db.find("select * from C_WOBO_CARPARK where field_id = ? ",fieldId);
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public int removeParkCar(String carId){
		return Db.update("update C_WOBO_CARPARK set car_id = '', plate_num = '' where car_id = ?",carId);
	}
	
	public int updatePark(String carId,String plateNum,String fieldId,String parkNo){
		return Db.update("update C_WOBO_CARPARK set car_id = ?,plate_num = ? where no = ? and field_id=? ",carId,plateNum,parkNo,fieldId);
	}
	
	public void addCountFlow(Map<String,Object> map){
		Record record = new Record();
		record.set("actiondate", getSysdate());
		record.set("CAR_ID", map.get("carId"));
		record.set("PLATE_NUM", map.get("plateNum"));
		record.set("FIELD_ID", map.get("fieldId"));
		record.set("FIELD_NAME", map.get("fieldName"));
		record.set("PARK_NO", map.get("parkNo"));
		record.set("USER_ID", map.get("userId"));
		record.set("USER_NAME", map.get("userName"));
		record.set("BIZ_NAME", map.get("bizName"));
		Db.save("C_WOBO_CARCOUNT_FLOW", record);
	}
	
	public void addLogFlow(Map<String,Object> map){
		Record record = new Record();
		record.set("actiondate", getSysdate());
		record.set("fromPlatform", map.get("fromPlatform"));
		record.set("CONTENT", map.get("content"));
		record.set("BIZ_NAME", map.get("bizName"));
		Db.save("C_WOBO_LOG_FLOW", record);
	}
	
	public String getCarPlan(){
		Record record = Db.findFirst("select * from C_WOBO_CARPLAN ");
		String name = "";
		if(record != null){
			name = record.getStr("NAME");
		}
		return name;
	}
	
	public boolean saveCarPlan(String name){
		Db.update("delete from C_WOBO_CARPLAN ");
		if(!"".equals(name)){
			Record record = new Record();
			record.set("NAME", name);
			return Db.save("C_WOBO_CARPLAN", record);
		}
		else{
			return true;
		}
	}
	
	public Map<String, Object> queryCarCountGroup(){
		List<Record> record = Db.find("select case when planName = '' then '日常' else planName end planName ,count(1) num from C_WOBO_CARCOUNT group by planName ");
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(record.size() > 0){
			for(Record role : record){
				Map<String, Object> map = new HashMap<>();
				map.put("planName", role.get("planName"));
				map.put("num", role.get("num"));
				list.add(map);
			}
		}
		resMap.put("carCountList", list);
		return resMap;
	}
	
	public Map<String, Object> queryCarCountDetailList(String name){
		if("日常".equals(name)){
			name = "";
		}
		List<Record> record = Db.find("select b.*,a.FIELD_NAME from C_WOBO_CAR a , C_WOBO_CARCOUNT b where a.id = b.car_id and  b.planName =? ",name);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(record.size() > 0){
			for(Record tmp : record){
				Map<String, Object> map = new HashMap<>();
				map.put("planName", tmp.get("PLATE_NUM"));
				map.put("userName", tmp.get("USER_NAME"));
				map.put("fieldName",tmp.get("FIELD_NAME"));
				map.put("actiondate", tmp.getDate("actiondate").toString().substring(0, tmp.getDate("actiondate").toString().indexOf(".")));
				list.add(map);
			}
		}
		resMap.put("carCountDetailList", list);
		return resMap;
	}
	
	public void saveCar(Map<String,Object> map){
		Record tmp = Db.findFirst("select * from C_WOBO_CAR where ENGINE_NUMBER =? ",map.get("ENGINE_NUMBER").toString());
		if(tmp == null || tmp.get("ID") == null || "".equals(tmp.get("ID"))){
			Record record = new Record();
			record.set("ID", "CA"+getSeq("C_WOBO_CAR"));
			record.set("PLATE_NUM", map.get("PLATE_NUM"));
			record.set("ENGINE_NUMBER", map.get("ENGINE_NUMBER"));
			record.set("FRAME_NUMBER", map.get("FRAME_NUMBER"));
			record.set("CAR_MODEL", map.get("CAR_MODEL"));
			record.set("PURCHASE", map.get("PURCHASE"));
			record.set("INVOICE", map.get("INVOICE"));
			record.set("STATUSNAME", map.get("STATUSNAME"));
			record.set("DRIVER_YEAR", map.get("DRIVER_YEAR"));
			record.set("DRIVER_MONTH", map.get("DRIVER_MONTH"));
			record.set("DRIVER_DATE", map.get("DRIVER_DATE"));
			record.set("sysdate", getSysdate());
			Db.save("C_WOBO_CAR", record);
		}else{
			Record record = new Record();
			record.set("ID", tmp.get("ID"));
			record.set("PLATE_NUM", map.get("PLATE_NUM"));
			record.set("ENGINE_NUMBER", map.get("ENGINE_NUMBER"));
			record.set("FRAME_NUMBER", map.get("FRAME_NUMBER"));
			record.set("CAR_MODEL", map.get("CAR_MODEL"));
			record.set("PURCHASE", map.get("PURCHASE"));
			record.set("INVOICE", map.get("INVOICE"));
			record.set("STATUSNAME", map.get("STATUSNAME"));
			record.set("DRIVER_YEAR", map.get("DRIVER_YEAR"));
			record.set("DRIVER_MONTH", map.get("DRIVER_MONTH"));
			record.set("DRIVER_DATE", map.get("DRIVER_DATE"));
			Db.update("C_WOBO_CAR","ID", record);
		}
	}
	
	public void saveCarInsureance(Map<String,Object> map){
		Record tmp = Db.findFirst("select * from C_WOBO_INSUREANCE where ENGINE_NUMBER=? and FORCE_IS = ? and FORCE_DATE =? and status = '1' ",map.get("ENGINE_NUMBER").toString(),map.get("FORCE_IS").toString(),map.get("FORCE_DATE"));
		if(tmp == null || tmp.get("ID") == null || "".equals(tmp.get("ID"))){
			Record record = new Record();
			record.set("ID", getSeqId("C_WOBO_INSUREANCE"));
			record.set("PLATE_NUM", map.get("PLATE_NUM"));
			record.set("ENGINE_NUMBER", map.get("ENGINE_NUMBER"));
			record.set("BUS_IS", map.get("BUS_IS"));
			record.set("FORCE_IS", map.get("FORCE_IS"));
			record.set("STATUS", "1");
			record.set("BUS_DATE", map.get("BUS_DATE"));
			record.set("FORCE_DATE", map.get("FORCE_DATE"));
			record.set("sysdate", getSysdate());
			Db.save("C_WOBO_INSUREANCE", record);
		}else{
			Record record = new Record();
			record.set("ID", tmp.get("ID"));
			record.set("PLATE_NUM", map.get("PLATE_NUM"));
			record.set("ENGINE_NUMBER", map.get("ENGINE_NUMBER"));
			record.set("BUS_IS", map.get("BUS_IS"));
			record.set("FORCE_IS", map.get("FORCE_IS"));
			record.set("STATUS", "1");
			record.set("BUS_DATE", map.get("BUS_DATE"));
			record.set("FORCE_DATE", map.get("FORCE_DATE"));
			Db.update("C_WOBO_INSUREANCE","ID", record);
		}
	}
	
	public void saveCarSaleMan(Map<String,Object> map){
		Db.update("delete from C_WOBO_CARSALEMAN where ENGINE_NUMBER=? and PLATE_NUM = ? ",map.get("ENGINE_NUMBER").toString(),map.get("PLATE_NUM").toString());
		Record record = new Record();
		record.set("ENGINE_NUMBER", map.get("ENGINE_NUMBER"));
		record.set("PLATE_NUM", map.get("PLATE_NUM"));
		record.set("USER_NAME", map.get("USER_NAME"));
		Db.save("C_WOBO_CARSALEMAN", record);
	}
	
	public void saveCarComponent(Map<String,Object> map){
		Record tmp = Db.findFirst("select * from C_WOBO_CAR_COMPONENTS where BAR_CODE =? ",map.get("BAR_CODE").toString());
		if(tmp == null || tmp.get("ID") == null || "".equals(tmp.get("ID"))){
			Record record = new Record();
			record.set("ID", getSeqId("C_WOBO_CAR_COMPONENTS"));
			record.set("NAME", map.get("NAME"));
			record.set("SPEC", map.get("SPEC"));
			record.set("KIND", map.get("KIND"));
			record.set("UNIT", map.get("UNIT"));
			record.set("BAR_CODE", map.get("BAR_CODE"));
			record.set("CODE", map.get("CODE"));
			record.set("REMARK", map.get("REMARK"));
			record.set("SALE_PRICE", map.get("SALE_PRICE"));
			record.set("COST_PRICE", map.get("COST_PRICE"));
			record.set("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));
			record.set("NUM", map.get("NUM"));
			record.set("MONEY", map.get("MONEY"));
			record.set("BASE_NUM", map.get("BASE_NUM"));
			record.set("sysdate", getSysdate());
			Db.save("C_WOBO_CAR_COMPONENTS", record);
		}else{
			Record record = new Record();
			record.set("ID", tmp.get("ID"));
			record.set("NAME", map.get("NAME"));
			record.set("SPEC", map.get("SPEC"));
			record.set("KIND", map.get("KIND"));
			record.set("UNIT", map.get("UNIT"));
			record.set("BAR_CODE", map.get("BAR_CODE"));
			record.set("CODE", map.get("CODE"));
			record.set("REMARK", map.get("REMARK"));
			record.set("SALE_PRICE", map.get("SALE_PRICE"));
			record.set("COST_PRICE", map.get("COST_PRICE"));
			record.set("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));
			record.set("NUM", map.get("NUM"));
			record.set("MONEY", map.get("MONEY"));
			record.set("BASE_NUM", map.get("BASE_NUM"));
			Db.update("C_WOBO_CAR_COMPONENTS","ID", record);
		}
	}
	
	/**
	 * @author woody
	 * @date 20180902
	 * 方法：车辆列表
	 */
	public Map<String, Object> componentList(int pn, int ps, Object searchContent){
		String sql = "from C_WOBO_CAR_COMPONENTS A where 1=1 ";
		if(searchContent != null && !"".equals(searchContent)){
			sql = sql + " and (A.NAME like '%"+searchContent+"%' or A.KIND like '%"+searchContent+"%'  or A.UNIT like '%"+searchContent+"%' or A.BAR_CODE like '%"+searchContent+"%'  or A.SALE_PRICE like '%"+searchContent+"%' or A.COST_PRICE like '%"+searchContent+"%' or A.PURCHASE_PRICE like '%"+searchContent+"%' or A.NUM like '%"+searchContent+"%' or A.MONEY like '%"+searchContent+"%' ) ";
		}
		
		Page<Record> page = Db.paginate(pn, ps, "select A.*  ", sql );
		List<Record> eps = page.getList();
		List<Map<String, Object>> epList = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		if(eps != null){
			for(Record record : eps){
				Map<String, Object> map = new HashMap<>();
				map.put("ID", record.get("ID"));
				map.put("NAME", record.get("NAME"));
				map.put("KIND", record.get("KIND"));
				map.put("UNIT", record.get("UNIT"));
				map.put("BAR_CODE", record.get("BAR_CODE"));
				map.put("SALE_PRICE", record.get("SALE_PRICE"));
				map.put("COST_PRICE", record.get("COST_PRICE"));
				map.put("PURCHASE_PRICE", record.get("PURCHASE_PRICE"));
				map.put("NUM", record.get("NUM"));
				map.put("MONEY", record.get("MONEY"));
				epList.add(map);
			}
		}
		resMap.put("componentList", epList);
		resMap.put("totalPage", page.getTotalPage());
		resMap.put("totalRow", page.getTotalRow());
		return resMap;
	}
	
	/**
	 * @author woody
	 * @date 20190403
	 * 方法：合同列表
	 */
	public Map<String, Object> contractList(int pn, int ps, Object searchContent){
		String sql = "from C_WOBO_CONTRACT A where A.status != '2'  ";
		if(searchContent != null && !"".equals(searchContent)){
			sql = sql + " and (A.CU_NAME like '%"+searchContent+"%' or A.ID like '%"+searchContent+"%' or A.CU_EP_NAME like '%"+searchContent+"%' or A.CAR_PLATE_NUM like '%"+searchContent+"%' or A.TYPE like '%"+searchContent+"%' or A.STATUSNAME like '%"+searchContent+"%'  ) ";
		}
		
		Page<Record> page = Db.paginate(pn, ps, "select A.*  ", sql );
		List<Record> eps = page.getList();
		List<Map<String, Object>> epList = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		if(eps != null){
			for(Record record : eps){
				Map<String, Object> map = new HashMap<>();
				map.put("ID", record.get("ID"));
				map.put("CU_NAME", record.get("CU_NAME"));
				map.put("CU_TYPE", record.get("CU_TYPE"));
				map.put("CU_PHONE", record.get("CU_PHONE"));
				map.put("CU_EP_NAME", record.get("CU_EP_NAME"));
				map.put("TYPE" ,record.get("TYPE"));
				map.put("CAR_PLATE_NUM" ,record.get("CAR_PLATE_NUM"));
				map.put("STATUSNAME", record.get("STATUSNAME"));
				epList.add(map);
			}
		}
		resMap.put("dataList", epList);
		resMap.put("totalPage", page.getTotalPage());
		resMap.put("totalRow", page.getTotalRow());
		return resMap;
	}
	
	/**
	 * @author woody
	 * @date 20180902
	 * 方法：车辆列表
	 */
	public Map<String, Object> carList(int pn, int ps, Object searchContent){
		String sql = "from c_wobo_car A ,C_WOBO_CARSALEMAN B where a.ENGINE_NUMBER = b.ENGINE_NUMBER ";
		if(searchContent != null && !"".equals(searchContent)){
			sql = sql + " and (A.PLATE_NUM like '%"+searchContent+"%' or A.ENGINE_NUMBER like '%"+searchContent+"%'  or A.FRAME_NUMBER like '%"+searchContent+"%' or A.FIELD_NAME like '%"+searchContent+"%'  or A.STATUSNAME like '%"+searchContent+"%' or A.CAR_MODEL like '%"+searchContent+"%' or A.INVOICE like '%"+searchContent+"%' or concat(A.DRIVER_YEAR,'-',A.DRIVER_MONTH,'-',A.DRIVER_DATE) like '%"+searchContent+"%' or A.PURCHASE like '%"+searchContent+"%' or B.USER_NAME like '%"+searchContent+"%' ) ";
		}
		
		Page<Record> page = Db.paginate(pn, ps, "select A.*,concat(A.DRIVER_YEAR,'-',A.DRIVER_MONTH,'-',A.DRIVER_DATE) driverDate,b.USER_NAME  ", sql );
		List<Record> eps = page.getList();
		List<Map<String, Object>> epList = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		if(eps != null){
			for(Record record : eps){
				Map<String, Object> map = new HashMap<>();
				map.put("ID", record.get("ID"));
				map.put("PLATE_NUM", record.get("PLATE_NUM"));
				map.put("ENGINE_NUMBER", record.get("ENGINE_NUMBER"));
				map.put("FRAME_NUMBER", record.get("FRAME_NUMBER"));
				map.put("FIELD_ID", record.get("FIELD_ID"));
				map.put("FIELD_NAME", record.get("FIELD_NAME"));
				map.put("STATUS", record.get("STATUS"));
				map.put("STATUSNAME", record.get("STATUSNAME"));
				map.put("CAR_MODEL", record.get("CAR_MODEL"));
				map.put("INVOICE", record.get("INVOICE"));
				map.put("PURCHASE", record.get("PURCHASE"));
				map.put("driverDate", record.get("driverDate"));
				map.put("USER_NAME", record.get("USER_NAME"));
				epList.add(map);
			}
		}
		resMap.put("carList", epList);
		resMap.put("totalPage", page.getTotalPage());
		resMap.put("totalRow", page.getTotalRow());
		return resMap;
	}
	
	/**
	 * @author woody
	 * @date 20180902
	 * 方法：车辆列表
	 */
	public Map<String, Object> insureanceList(int pn, int ps, Object searchContent){
		String sql = "from C_WOBO_INSUREANCE A  where 1=1 ";
		if(searchContent != null && !"".equals(searchContent)){
			sql = sql + " and (A.PLATE_NUM like '%"+searchContent+"%' or A.BUS_IS like '%"+searchContent+"%'  or A.BUS_DATE like '%"+searchContent+"%' or A.FORCE_IS like '%"+searchContent+"%'  or A.FORCE_DATE like '%"+searchContent+"%'  ) ";
		}
		sql = sql + " order by a.FORCE_DATE desc ";
		Page<Record> page = Db.paginate(pn, ps, "select A.*  ", sql );
		List<Record> eps = page.getList();
		List<Map<String, Object>> epList = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		if(eps != null){
			for(Record record : eps){
				Map<String, Object> map = new HashMap<>();
				map.put("ID", record.get("ID"));
				map.put("PLATE_NUM", record.get("PLATE_NUM"));
				map.put("ENGINE_NUMBER", record.get("ENGINE_NUMBER"));
				map.put("BUS_IS", record.get("BUS_IS"));
				map.put("BUS_DATE", record.get("BUS_DATE"));
				map.put("FORCE_IS", record.get("FORCE_IS"));
				map.put("FORCE_DATE", record.get("FORCE_DATE"));
				epList.add(map);
			}
		}
		resMap.put("insureanceList", epList);
		resMap.put("totalPage", page.getTotalPage());
		resMap.put("totalRow", page.getTotalRow());
		return resMap;
	}
	
	public long getCarCount(){
		Record record = Db.findFirst("select count(1) num from C_WOBO_CAR ");
		long res = 0;
		if(record != null && record.getColumns() != null){
			res = record.getLong("num");
		}
		return res;
	}
	
	public List<Map<String,Object>> queryComponentList(){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<Record> list = Db.find("select *  from C_WOBO_CAR_COMPONENTS order by num desc");
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public List<Map<String,Object>> queryCompanyList(){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<Record> list = Db.find("select *  from C_WOBO_COMPANY where status =1");
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	@SuppressWarnings("unchecked")
	public String saveRepair(Map<String,Object> repairData){
		String id = "";
		Map<String, Object> companyData = (Map<String, Object>) repairData.get("companyData");
		if(repairData.get("ID") != null && !"".equals(repairData.get("ID"))){
			id = repairData.get("ID").toString();
		}else{
			id = getSeqId("C_WOBO_REPAIR");
		}
		
		Record record = new Record();
		record.set("ID", id);
		record.set("CAR_ID", repairData.get("carId"));
		record.set("CONTRACT_ID", repairData.get("CONTRACT_ID") != null ? repairData.get("CONTRACT_ID") : "");
		record.set("CONTRACT_NAME", repairData.get("CONTRACT_NAME") != null ? repairData.get("CONTRACT_NAME") : "");
		record.set("EP_NAME", repairData.get("CU_EP_NAME") != null ? repairData.get("CU_EP_NAME") : "");
		record.set("CU_NAME", repairData.get("CU_NAME") != null ? repairData.get("CU_NAME") : "");
		record.set("CU_PHONE", repairData.get("CU_PHONE") != null ? repairData.get("CU_PHONE") : "");
		record.set("PLATE_NUM", repairData.get("plateNum"));
		record.set("ENGINE_NUMBER", repairData.get("engineNumber"));
		record.set("CAR_STATUSNAME", repairData.get("statusname"));
		record.set("SALE_USERNAME", repairData.get("saleUserName"));
		record.set("SALE_USERID", repairData.get("saleUserId"));
		record.set("RE_DESC", repairData.get("RE_DESC"));
		record.set("PAYMENTTYPE", repairData.get("paymentType"));
		record.set("COMPANY_ID", companyData.get("ID") !=null ? companyData.get("ID") : "");
		record.set("COMPANY_NAME", companyData.get("NAME") !=null ? companyData.get("NAME") : "");
		record.set("begindate", getSysdate());
		record.set("sysdate", getSysdate());
		boolean flag = false;
		if(repairData.get("ID") != null && !"".equals(repairData.get("ID"))){
			flag = Db.update("C_WOBO_REPAIR","ID", record);
			return id;
		}else{
			flag = Db.save("C_WOBO_REPAIR", record);
			return flag ? id : "";
		}
	}
	
	public List<Map<String, Object>> saveRepairList(List<Map<String, Object>> list,String repairId){
		Db.update("delete from  C_WOBO_REPAIR_LIST where re_id = ? ",repairId);
		for(int i = 0 ; i < list.size() ; i++){
			Record record = new Record();
			int id = i+1;
			record.set("ID", id);
			record.set("RE_ID", repairId);
			record.set("CO_ID", list.get(i).get("ID"));
			record.set("NAME", list.get(i).get("NAME"));
			record.set("NUM", list.get(i).get("NUM"));
			record.set("SALE_PRICE", list.get(i).get("SALE_PRICE"));
			record.set("BAR_CODE", list.get(i).get("BAR_CODE"));
			record.set("KIND", list.get(i).get("KIND"));
			record.set("UNIT", list.get(i).get("UNIT"));
			Db.save("C_WOBO_REPAIR_LIST", record);
			list.get(i).put("ID", id);
		}
		return list;
	}
	
	/**
	 * @author woody
	 * @date 20180902
	 * 方法：维修列表
	 */
	public Map<String, Object> repairList(int pn, int ps, Object searchContent){
		String sql = "from C_WOBO_REPAIR A  where 1=1 ";
		if(searchContent != null && !"".equals(searchContent)){
			sql = sql + " and (A.ID like '%"+searchContent+"%' or A.PLATE_NUM like '%"+searchContent+"%' or A.RE_DESC like '%"+searchContent+"%' or A.CAR_STATUSNAME like '%"+searchContent+"%' ) ";
		}
		sql = sql + " order by a.sysdate desc ";
		Page<Record> page = Db.paginate(pn, ps, "select A.*  ", sql );
		List<Record> eps = page.getList();
		List<Map<String, Object>> epList = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		if(eps != null){
			for(Record record : eps){
				Map<String, Object> map = new HashMap<>();
				map.put("ID", record.get("ID"));
				map.put("PLATE_NUM", record.get("PLATE_NUM"));
				map.put("CAR_STATUSNAME", record.get("CAR_STATUSNAME"));
				map.put("SALE_USERNAME", record.get("SALE_USERNAME"));
				map.put("RE_DESC", record.get("RE_DESC"));
				map.put("begindate", record.getDate("begindate").toString().substring(0, record.getDate("begindate").toString().indexOf(".")));
				epList.add(map);
			}
		}
		resMap.put("repairList", epList);
		resMap.put("totalPage", page.getTotalPage());
		resMap.put("totalRow", page.getTotalRow());
		return resMap;
	}
	
	public Map<String ,Object> queryRepair(String repairId){
		Record record = Db.findFirst("select * from C_WOBO_REPAIR where id = ? ",repairId);
		record.set("begindate", record.getDate("begindate").toString().substring(0, record.getDate("begindate").toString().indexOf(".")));
		return record.getColumns();
	}
	
	public List<Map<String ,Object>> queryRepairList(String repairId){
		List<Record> list = Db.find("select * from C_WOBO_REPAIR_LIST where re_id = ? ",repairId);
		List<Map<String, Object>> reslist = new ArrayList<>();
		if(list.size() > 0){
			for(Record tmp : list){
				reslist.add(tmp.getColumns());
			}
		}
		return reslist;
	}
	
	public int delComponentItem(String repairId,String sonId){
		int resInt = Db.update("delete from C_WOBO_REPAIR_LIST where re_id = ? and id=? ",repairId,sonId);
		return resInt;
	}
	
	public Map<String,Object> checkCustomerWillRepeat(String name,String phone){
		Record record = Db.findFirst("select * from C_WOBO_CUSTOMER_WILL where name = ? and phone = ? and status = '1' ",name,phone);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> checkCustomerRepeat(String name,String phone){
		Record record = Db.findFirst("select * from C_WOBO_CUSTOMER where name = ? and phone = ? and status = '1' ",name,phone);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> getCustomerWill(String id){
		Record record = Db.findFirst("select * from C_WOBO_CUSTOMER_WILL where id = ? and status = '1' ",id);
		return record != null ? record.getColumns() : null;
	}
	
	public boolean addCustomerWill(Map<String,Object> map){
		Record record = new Record();
		String id = getSeqId("C_WOBO_CUSTOMER");
		map.put("ID", id);
		record.set("ID", getSeqId("C_WOBO_CUSTOMER"));
		record.set("NAME", map.get("NAME"));
		record.set("PERSON_CODE", map.get("PERSON_CODE"));
		record.set("PERSON_ADDRESS", map.get("PERSON_ADDRESS"));
		record.set("PHONE", map.get("PHONE"));
		record.set("WORK", map.get("WORK"));
		record.set("SOURCE", map.get("SOURCE"));
		record.set("WILL_GENDER", map.get("WILL_GENDER"));
		record.set("WILL_AGE", map.get("WILL_AGE"));
		record.set("WILL_INTENTION", map.get("WILL_INTENTION"));
		record.set("WILL_ITH", map.get("WILL_ITH"));
		record.set("PERSON_IMG", map.get("PERSON_IMG"));
		record.set("PERSON_ONLY_IMG", map.get("PERSON_ONLY_IMG"));
		record.set("BUSINESS_LICENSE_IMG", map.get("BUSINESS_LICENSE_IMG"));
		record.set("ENTRUST_IMG", map.get("ENTRUST_IMG"));
		record.set("EP_NAME", map.get("EP_NAME"));
		record.set("EP_ADDRESS", map.get("EP_ADDRESS"));
		record.set("EP_CODE", map.get("EP_CODE"));
		if(map.get("EP_NAME") != null && !"".equals(map.get("EP_NAME"))){
			record.set("TYPE", "1");
		}else{
			record.set("TYPE", "0");
		}
		record.set("EMERGENCY_PERSON", map.get("EMERGENCY_PERSON"));
		record.set("EMERGENCY_PHONE", map.get("EMERGENCY_PHONE"));
		record.set("SALE_USER_ID", map.get("SALE_USER_ID"));
		record.set("SALE_USER_NAME", map.get("SALE_USER_NAME"));
		record.set("STATUS", "1");
		record.set("sysdate", getSysdate());
		record.set("REGISTER_DATE", getSysdate());
		return Db.save("C_WOBO_CUSTOMER_WILL", record);
	}
	
	public boolean addCustomer(Map<String,Object> map){
		Record record = new Record();
		String id = getSeqId("C_WOBO_CUSTOMER");
		map.put("ID", id);
		record.set("ID", map.get("ID"));
		record.set("NAME", map.get("NAME"));
		record.set("PERSON_CODE", map.get("PERSON_CODE"));
		record.set("PERSON_ADDRESS", map.get("PERSON_ADDRESS"));
		record.set("PHONE", map.get("PHONE"));
		record.set("WORK", map.get("WORK"));
		record.set("SOURCE", map.get("SOURCE"));
		record.set("WILL_GENDER", map.get("WILL_GENDER"));
		record.set("WILL_AGE", map.get("WILL_AGE"));
		record.set("WILL_INTENTION", map.get("WILL_INTENTION"));
		record.set("WILL_ITH", map.get("WILL_ITH"));
		record.set("PERSON_IMG", map.get("PERSON_IMG"));
		record.set("PERSON_ONLY_IMG", map.get("PERSON_ONLY_IMG"));
		record.set("BUSINESS_LICENSE_IMG", map.get("BUSINESS_LICENSE_IMG"));
		record.set("ENTRUST_IMG", map.get("ENTRUST_IMG"));
		record.set("EP_NAME", map.get("EP_NAME"));
		record.set("EP_ADDRESS", map.get("EP_ADDRESS"));
		record.set("EP_CODE", map.get("EP_CODE"));
		if(map.get("EP_NAME") != null && !"".equals(map.get("EP_NAME"))){
			record.set("TYPE", "1");
		}else{
			record.set("TYPE", "0");
		}
		record.set("EMERGENCY_PERSON", map.get("EMERGENCY_PERSON"));
		record.set("EMERGENCY_PHONE", map.get("EMERGENCY_PHONE"));
		record.set("SALE_USER_ID", map.get("SALE_USER_ID"));
		record.set("SALE_USER_NAME", map.get("SALE_USER_NAME"));
		record.set("STATUS", "1");
		record.set("sysdate", getSysdate());
		record.set("REGISTER_DATE", getSysdate());
		return Db.save("C_WOBO_CUSTOMER_WILL", record);
	}
	
	public boolean updateCustomerWill(Map<String,Object> map){
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("NAME", map.get("NAME"));
		record.set("PERSON_CODE", map.get("PERSON_CODE"));
		record.set("PERSON_ADDRESS", map.get("PERSON_ADDRESS"));
		record.set("PHONE", map.get("PHONE"));
		record.set("WORK", map.get("WORK"));
		record.set("SOURCE", map.get("SOURCE"));
		record.set("WILL_GENDER", map.get("WILL_GENDER"));
		record.set("WILL_AGE", map.get("WILL_AGE"));
		record.set("WILL_INTENTION", map.get("WILL_INTENTION"));
		record.set("WILL_ITH", map.get("WILL_ITH"));
		record.set("PERSON_IMG", map.get("PERSON_IMG"));
		record.set("PERSON_ONLY_IMG", map.get("PERSON_ONLY_IMG"));
		record.set("BUSINESS_LICENSE_IMG", map.get("BUSINESS_LICENSE_IMG"));
		record.set("ENTRUST_IMG", map.get("ENTRUST_IMG"));
		record.set("EP_NAME", map.get("EP_NAME"));
		record.set("EP_ADDRESS", map.get("EP_ADDRESS"));
		record.set("EP_CODE", map.get("EP_CODE"));
		if(map.get("EP_NAME") != null && !"".equals(map.get("EP_NAME"))){
			record.set("TYPE", "1");
		}else{
			record.set("TYPE", "0");
		}
		record.set("EMERGENCY_PERSON", map.get("EMERGENCY_PERSON"));
		record.set("EMERGENCY_PHONE", map.get("EMERGENCY_PHONE"));
		return Db.update("C_WOBO_CUSTOMER_WILL", "ID",record);
	}
	
	public boolean updateCustomer(Map<String,Object> map){
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("NAME", map.get("NAME"));
		record.set("PERSON_CODE", map.get("PERSON_CODE"));
		record.set("PERSON_ADDRESS", map.get("PERSON_ADDRESS"));
		record.set("PHONE", map.get("PHONE"));
		record.set("WORK", map.get("WORK"));
		record.set("SOURCE", map.get("SOURCE"));
		record.set("WILL_GENDER", map.get("WILL_GENDER"));
		record.set("WILL_AGE", map.get("WILL_AGE"));
		record.set("WILL_INTENTION", map.get("WILL_INTENTION"));
		record.set("WILL_ITH", map.get("WILL_ITH"));
		record.set("PERSON_IMG", map.get("PERSON_IMG"));
		record.set("PERSON_ONLY_IMG", map.get("PERSON_ONLY_IMG"));
		record.set("BUSINESS_LICENSE_IMG", map.get("BUSINESS_LICENSE_IMG"));
		record.set("ENTRUST_IMG", map.get("ENTRUST_IMG"));
		record.set("EP_NAME", map.get("EP_NAME"));
		record.set("EP_ADDRESS", map.get("EP_ADDRESS"));
		record.set("EP_CODE", map.get("EP_CODE"));
		if(map.get("EP_NAME") != null && !"".equals(map.get("EP_NAME"))){
			record.set("TYPE", "1");
		}else{
			record.set("TYPE", "0");
		}
		record.set("EMERGENCY_PERSON", map.get("EMERGENCY_PERSON"));
		record.set("EMERGENCY_PHONE", map.get("EMERGENCY_PHONE"));
		return Db.update("C_WOBO_CUSTOMER", "ID",record);
	}
	
	public List<Map<String,Object>> querySelectList(String id){
		List<Record> list = Db.find("select * from pub_select where id = ? order by sort ",id);
		List<Map<String, Object>> resList = new ArrayList<>();
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public void customerInfoTransfer(String id){
		int res = Db.update("insert into C_WOBO_CUSTOMER select  * from C_WOBO_CUSTOMER_will where id = ? ",id);
		if(res > 0){
			Db.update("delete from C_WOBO_CUSTOMER_will where id = ? ",id);
		}
	}
	
	public boolean addContract(Map<String,Object> map){
		Record record = new Record();
		String id = getSeqId("C_WOBO_CONTRACT");
		record.set("ID",id);
		record.set("CU_ID", map.get("ID"));
		if(map.get("EP_NAME") != null && !"".equals(map.get("EP_NAME"))){
			record.set("CU_TYPE", "1");
		}else{
			record.set("CU_TYPE", "0");
		}
		record.set("CU_NAME", map.get("NAME"));
		record.set("CU_PERSON_CODE", map.get("PERSON_CODE"));
		record.set("CU_PERSON_ADDRESS", map.get("PERSON_ADDRESS"));
		record.set("CU_PHONE", map.get("PHONE"));
		record.set("CU_WORK", map.get("WORK"));
		record.set("CU_SOURCE", map.get("SOURCE"));
		record.set("CU_EP_NAME", map.get("EP_NAME"));
		record.set("CU_EP_ADDRESS", map.get("EP_ADDRESS"));
		record.set("CU_EP_CODE", map.get("EP_CODE"));
		record.set("CU_EMERGENCY_PERSON", map.get("EMERGENCY_PERSON"));
		record.set("CU_EMERGENCY_PHONE", map.get("EMERGENCY_PHONE"));
		record.set("CU_BUSINESS_LICENSE_IMG", map.get("BUSINESS_LICENSE_IMG"));
		record.set("CU_PERSON_IMG", map.get("PERSON_IMG"));
		record.set("CU_PERSON_ONLY_IMG", map.get("PERSON_ONLY_IMG"));
		record.set("CU_ENTRUST_IMG", map.get("ENTRUST_IMG"));
		record.set("SALE_USER_ID", map.get("SALE_USER_ID"));
		record.set("SALE_USER_NAME", map.get("SALE_USER_NAME"));
		record.set("STATUS", "1");
		record.set("STATUSNAME", "保存");
		record.set("sysdate", getSysdate());
		return Db.save("C_WOBO_CONTRACT", record);
	}
	
	public Map<String,Object> getContract(String id){
		Record record = Db.findFirst("select * from C_WOBO_CONTRACT where id = ? ",id);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> getContractFinance(String id){
		Record record = Db.findFirst("select * from C_WOBO_CONTRACT_FINANCE where id = ? ",id);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> getContractCarManage(String id){
		Record record = Db.findFirst("select * from C_WOBO_CONTRACT_CAR_MANAGE where id = ? ",id);
		return record != null ? record.getColumns() : null;
	}
	
	public Map<String,Object> contractDeliver(String id){
		Record record = Db.findFirst("select * from C_WOBO_CONTRACT_DELIVER where id = ? ",id);
		return record != null ? record.getColumns() : null;
	}
	
	public List<Map<String,Object>> querySelectCarList(){
		List<Record> list = Db.find("select a.* from C_WOBO_CAR a, C_WOBO_CAR_SHARE b where a.id = b.car_id ");
		List<Map<String, Object>> resList = new ArrayList<>();
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public boolean updateContract(Map<String,Object> map){
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("CONTRACT_NAME", map.get("CONTRACT_NAME"));
		record.set("TYPE", map.get("TYPE"));
		record.set("CAR_PLATE_NUM", map.get("CAR_PLATE_NUM"));
		record.set("CAR_FRAME_NUMBER", map.get("CAR_FRAME_NUMBER"));
		record.set("CAR_MODEL", map.get("CAR_MODEL"));
		record.set("CAR_ENGINE_NUMBER", map.get("CAR_ENGINE_NUMBER"));
		record.set("CAR_ID", map.get("CAR_ID"));
		record.set("CAR_PROP", map.get("CAR_PROP"));
		record.set("CAR_DRIVER_DATE", map.get("CAR_DRIVER_DATE"));
		record.set("CAR_DRIVER_MONTH", map.get("CAR_DRIVER_MONTH"));
		record.set("CAR_DRIVER_YEAR", map.get("CAR_DRIVER_YEAR"));
		record.set("INSUREANCE", map.get("INSUREANCE"));
		record.set("CAR_DRIVER_NUM", map.get("CAR_DRIVER_NUM"));
		record.set("NUM", map.get("NUM"));
		record.set("UNIT_PRICE", map.get("UNIT_PRICE"));
		record.set("UNIT_PRICE_SUM", map.get("UNIT_PRICE_SUM"));
		record.set("INVOICE", map.get("INVOICE"));
		record.set("SIGN_DATE", map.get("SIGN_DATE"));
		record.set("DELIVER_DATE", map.get("DELIVER_DATE"));
		record.set("REMARK", map.get("REMARK"));
		return Db.update("C_WOBO_CONTRACT", "ID",record);
	}
	
	public int updateCarDriverNum(String id,String driver_num){
		int res = Db.update("update c_wobo_car set DRIVER_NUM = ? where id = ? ",driver_num,id);
		return res;
	}
	
	public boolean saveContractFinance(Map<String,Object> map){
		Db.update("delete from C_WOBO_CONTRACT_FINANCE where id = ? ",map.get("ID"));
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("IF_CONTRACT", map.get("IF_CONTRACT"));
		record.set("CONTRACT_PERIOD", map.get("CONTRACT_PERIOD"));
		record.set("PAY_PERIOD", map.get("PAY_PERIOD"));
		record.set("AGENT", map.get("AGENT"));
		record.set("EARNEST_MONEY", map.get("EARNEST_MONEY"));
		if(map.get("EARNEST_DATE") != null && !"".equals( map.get("EARNEST_DATE"))){
			record.set("EARNEST_DATE", map.get("EARNEST_DATE"));
		}
		record.set("EARNEST_RECEIPT", map.get("EARNEST_RECEIPT"));
		record.set("EARNEST_PAYTYPE", map.get("EARNEST_PAYTYPE"));
		record.set("MONEY", map.get("MONEY"));
		if(map.get("PAY_DATE") != null && !"".equals( map.get("PAY_DATE"))){
			record.set("PAY_DATE", map.get("PAY_DATE"));
		}
		record.set("PAY_RECEIPT", map.get("PAY_RECEIPT"));
		record.set("PAY_TYPE", map.get("PAY_TYPE"));
		record.set("STATUS", map.get("STATUS"));
		record.set("USER_ID", map.get("USER_ID"));
		record.set("USER_NAME", map.get("USER_NAME"));
		record.set("sysdate", getSysdate());
		return Db.save("C_WOBO_CONTRACT_FINANCE",record);
	}
	
	public boolean saveContractCarManage(Map<String,Object> map){
		Db.update("delete from C_WOBO_CONTRACT_CAR_MANAGE where id = ? ",map.get("ID"));
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("DRIVING_LICENSE", map.get("DRIVING_LICENSE"));
		record.set("MAINTENANCE_SIGN", map.get("MAINTENANCE_SIGN"));
		record.set("CAR_KEY", map.get("CAR_KEY"));
		record.set("CAR_KEY_NUM", map.get("CAR_KEY_NUM"));
		record.set("STATUS", map.get("STATUS"));
		record.set("USER_ID", map.get("USER_ID"));
		record.set("USER_NAME", map.get("USER_NAME"));
		record.set("sysdate", getSysdate());
		return Db.save("C_WOBO_CONTRACT_CAR_MANAGE",record);
	}
	
	public boolean saveContractDeliver(Map<String,Object> map){
		Db.update("delete from C_WOBO_CONTRACT_DELIVER where id = ? ",map.get("ID"));
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("INSURANCE", map.get("INSURANCE"));
		record.set("CHARGE_GUN", map.get("CHARGE_GUN"));
		record.set("TOOL_KIT", map.get("TOOL_KIT"));
		record.set("TRIPOD", map.get("TRIPOD"));
		record.set("FIRE_EXTINGUISHER", map.get("FIRE_EXTINGUISHER"));
		record.set("YEAR_CHECK_FLAG", map.get("YEAR_CHECK_FLAG"));
		record.set("MAINTENANCE", map.get("MAINTENANCE"));
		record.set("STATUS", map.get("STATUS"));
		record.set("USER_ID", map.get("USER_ID"));
		record.set("USER_NAME", map.get("USER_NAME"));
		record.set("sysdate", getSysdate());
		return Db.save("C_WOBO_CONTRACT_DELIVER",record);
	}
	
	public boolean saveCheckCar(String id,List<Map<String,Object>> list,String type){
		Db.update("delete from C_WOBO_CONTRACT_CHECKIMG where id = ? and type = ? ",id,type);
		boolean flag = true;
		for(int i = 0 ; i < list.size() ; i++){
			Record record = new Record();
			record.set("ID", id);
			record.set("SON_ID", i+1);
			record.set("TYPE", list.get(i).get("TYPE"));
			record.set("CONTENT", list.get(i).get("CONTENT"));
			record.set("IMG", list.get(i).get("IMG"));
			Db.save("C_WOBO_CONTRACT_CHECKIMG", record);
		}
		return flag;
	}
	
	public List<Map<String,Object>> initContractCheckImg(String id,String type){
		List<Record> list = Db.find("select * from C_WOBO_CONTRACT_CHECKIMG where id = ? and type = ? ",id,type);
		List<Map<String, Object>> resList = new ArrayList<>();
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public boolean saveCarShare(String carId,String plateNum){
		Db.update("delete from C_WOBO_CAR_SHARE where CAR_ID = ? and PLATE_NUM = ?",carId,plateNum);
		Record record = new Record();
		record.set("CAR_ID", carId);
		record.set("PLATE_NUM", plateNum);
		record.set("actiondate",getSysdate());
		return Db.save("C_WOBO_CAR_SHARE", record);
	}
}
