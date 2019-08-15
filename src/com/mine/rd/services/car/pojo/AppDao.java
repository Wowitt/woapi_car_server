package com.mine.rd.services.car.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mine.pub.pojo.BaseDao;

public class AppDao extends BaseDao {
	
	public Map<String, Object> rentCarList(int pn, int ps, Object searchContent){
		String sql = "from C_WOBO_CAR a , C_WOBO_CARSUPPLIER b where a.SU_ID = b.id and ifapp = '1'  ";
		if(searchContent != null && !"".equals(searchContent)){
			sql = sql + " and (a.brand like '%"+searchContent+"%' or b.branddesc like '%"+searchContent+"%'  ) ";
		}
//		sql = sql + " order by a.sysdate desc ";
		Page<Record> page = Db.paginate(pn, ps, "select a.*,b.NAME manufacturers ", sql );
		List<Record> eps = page.getList();
		List<Map<String, Object>> epList = new ArrayList<>();
		Map<String, Object> resMap = new HashMap<>();
		if(eps != null){
			for(Record record : eps){
				Map<String, Object> map = new HashMap<>();
				map.put("ID", record.get("ID"));
				map.put("brand", record.get("BRAND"));
				map.put("desc", record.get("BRANDDESC"));
				map.put("manufacturers", record.get("manufacturers"));
				map.put("SEAT_NUM", record.get("SEAT_NUM"));
				map.put("DRIVER_MAXNUM", record.get("DRIVER_MAXNUM"));
				map.put("WEIGHT", record.get("WEIGHT"));
				map.put("CHARGING_TIME", record.get("CHARGING_TIME"));
				map.put("setmealList", querySetmeal());
				map.put("imageList", queryCarImg(record.get("ID").toString()));
				epList.add(map);
			}
		}
		resMap.put("dataList", epList);
		resMap.put("totalPage", page.getTotalPage());
		resMap.put("totalRow", page.getTotalRow());
		return resMap;
	}
	
	public List<Map<String,Object>> querySetmeal(){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<Record> list = Db.find("select * from APP_SETMEAL ");
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public List<Map<String,Object>> queryCarImg(String id){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<Record> list = Db.find("select * from C_WOBO_CAR_IMG where car_id = ? order by sort ",id );
		for(Record record : list){
			resList.add(record.getColumns());
		}
		return resList;
	}
	
	public Map<String,Object> getCarInfo(String id){
		Map<String,Object> map = null;
		Record record = Db.findFirst("select a.*,b.NAME manufacturers from C_WOBO_CAR a, C_WOBO_CARSUPPLIER b where a.SU_ID = b.id and a.id = ? ",id);
		if(record != null){
			map = record.getColumns();
			map.put("powerType", "电动车");
			map.put("imageList", queryCarImg(record.get("ID").toString()));
		}
		return map;
	}
	
	public boolean userEdit(Map<String,Object> map){
		Record record = new Record();
		record.set("ID", map.get("ID"));
		record.set("NAME", map.get("NAME"));
		record.set("IMGPATH", map.get("IMGPATH"));
		record.set("MAIL", map.get("MAIL"));
		record.set("ADDRESS", map.get("ADDRESS"));
		record.set("SOURCE", map.get("SOURCE"));
		record.set("PURPOSE", map.get("PURPOSE"));
		record.set("EMERGENCY_PERSON", map.get("EMERGENCY_PERSON"));
		record.set("EMERGENCY_PHONE", map.get("EMERGENCY_PHONE"));
		record.set("STATUS", "1");
		return Db.update("APP_USER", "ID",record);
	}
	
	public boolean saveCertification(String userId,Map<String,Object> map){
		Record record = new Record();
		record.set("ID", userId);
		record.set("AIMGPATH", map.get("AIMGPATH"));
		record.set("BIMGPATH", map.get("BIMGPATH"));
		record.set("STATUS", "3");
		return Db.update("APP_USER", "ID",record);
	}
	
	public Map<String,Object> initUser(String id){
		Map<String,Object> map = null;
		Record record = Db.findFirst("select * from APP_USER where id = ? ",id);
		if(record != null){
			map = record.getColumns();
		}
		return map;
	}
}
