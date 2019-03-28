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
		record.set("CONTRACT_ID", repairData.get("contractId") != null ? repairData.get("contractId") : "");
		record.set("PLATE_NUM", repairData.get("plateNum"));
		record.set("ENGINE_NUMBER", repairData.get("engineNumber"));
		record.set("CAR_STATUSNAME", repairData.get("statusname"));
		record.set("SALE_USERNAME", repairData.get("saleUserName"));
		record.set("SALE_USERID", repairData.get("saleUserId"));
		record.set("RE_DESC", repairData.get("RE_DESC"));
		record.set("PAYMENTTYPE", repairData.get("paymentType"));
		record.set("COMPANY_ID", companyData.get("ID") !=null ? companyData.get("ID") : "");
		record.set("COMPANY_NAME", companyData.get("NAME") !=null ? companyData.get("NAME") : "");
		record.set("CUSTOMER_ID", repairData.get("customerId") != null ? repairData.get("customerId")  : "");
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
}
