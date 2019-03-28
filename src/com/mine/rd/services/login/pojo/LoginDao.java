package com.mine.rd.services.login.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.mine.pub.pojo.BaseDao;

public class LoginDao extends BaseDao {
	
	List<Record> cityList = CacheKit.get("mydict", "city_q");
	
	/**
	 * 
	 * @author weizanting
	 * @date 20161221
	 * 方法：登录--是否存在该用户
	 * 
	 */
	public Map<String, Object> login(String username){
		String sql = "select a.USER_ID,a.NAME,c.PORTRAIT,a.PWD,b.ROLE_ID from WOBO_USER a, WOBO_USER_INFO c ,WOBO_USERROLE b where a.user_id = b.user_id and a.STATUS = '1'  and a.USER_ID = c.USER_ID  and a.USER_ID=?  ";
		Record record =  Db.findFirst(sql, username);
		Map<String, Object> map = new HashMap<>();
		if(record != null){
			map.put("userId", record.get("USER_ID"));
			map.put("name", record.get("NAME"));
			map.put("nickName", record.get("NAME"));
			map.put("ifLogin", "0");
			map.put("pwd", record.get("PWD"));
			map.put("userPortrait", record.get("PORTRAIT"));
			map.put("status", record.get("STATUS"));
			map.put("roleId", record.get("ROLE_ID"));
		}
		return map;
	}
	
	/**
	 * @author ouyangxu
	 * @date 20170216
	 * 方法：单位与角色关联
	 */
	public boolean manageEpRole(String userId, String roleId){
		Record userRole = Db.findFirst("select * from WOBO_USERROLE where USER_ID = ? and ROLE_ID = ? and STATUS = '1'", userId, roleId);
		if(userRole == null){
			Record record = new Record();
			record.set("USER_ID", userId);
			record.set("ROLE_ID", roleId);
			record.set("STATUS", "1");
			return Db.save("Z_WOBO_USERROLE", record);
		}
		return true;
	}
	
	/**
	 * 
	 * @author woody
	 * @date 20180825
	 * 方法：单位管理员登录
	 * 
	 */
	public Map<String, Object> epAdminLogin(String userId){
		String sql = "select * from wobo_user";
		Record record = Db.findFirst(sql, userId);
		Map<String, Object> map = new HashMap<>();
		if(record != null){
			map.put("operatorId", record.get("OPERATORID"));
			map.put("pwd", record.get("PASSWORD"));
			map.put("USER_ID", record.get("EP_ID"));
			map.put("NAME", record.get("EP_NAME"));
			map.put("EP_ID", record.get("EP_ID"));
			map.put("EP_NAME", record.get("EP_NAME"));
			map.put("belongSepa", record.get("BELONG_SEPA"));
			map.put("sepaName", convert(cityList, record.get("BELONG_SEPA")));
		}
		return map;
	}
	
}
