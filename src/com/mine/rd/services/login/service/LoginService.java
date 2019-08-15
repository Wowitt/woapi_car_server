package com.mine.rd.services.login.service;

import java.sql.SQLException;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.mine.pub.controller.BaseController;
import com.mine.pub.service.BaseService;
import com.mine.rd.services.login.pojo.LoginDao;

public class LoginService extends BaseService {

	private LoginDao dao = new LoginDao();
	
	public LoginService(BaseController controller) {
		super(controller);
	}
	
	/**
	 * @author ouyangxu
	 * @throws Exception 
	 * @date 20170221
	 * 方法：管理员登录
	 */
	private void adminLogin() throws Exception{
		controller.doIWBSESSION();
		String username = controller.getMyParam("adminName").toString();
		String pwd = controller.getMyParam("adminPwd").toString();
		//sysadmin-系统管理员
		if("sysadmin".equals(username)){
			Map<String , Object> user = dao.login(username);
			if(user != null && user.get("userId") != null && !"".equals(user.get("userId"))){
				if(pwd.equals(user.get("pwd"))){
					String userPortrait = "";
					controller.setMySession("userId",user.get("userId"));
					controller.setMySession("nickName",user.get("nickName"));
					controller.setMySession("userName",user.get("name"));
					controller.setMySession("roleId",user.get("roleId"));
					controller.setMySession("ifLogin","0");
					controller.setMySession("userPortrait",userPortrait);
					//token值
					controller.setMySession("WJWT", dao.getToken());
					controller.setAttr("resFlag", "0");
					controller.setAttr("msg", "登录成功！");
				}else{
					controller.setAttr("resFlag", "1");
					controller.setAttr("msg", "密码错误！");
				}
			}else{   // 查询不到用户信息
				controller.setAttr("resFlag", "1");
				controller.setAttr("msg", "用户不存在！");
			}
		}else{
			controller.setAttr("resFlag", "1");
			controller.setAttr("msg", "用户不存在！");
		}
	}
	
	private void login() throws Exception{
		controller.doIWBSESSION();
		String username = controller.getMyParam("userId").toString();
		String pwd = controller.getMyParam("userPwd").toString();
		Map<String , Object> user = dao.login(username);
		if(user != null && user.get("userId") != null && !"".equals(user.get("userId"))){
			if(pwd.equals(user.get("pwd"))){
				String userPortrait = "";
				controller.setMySession("userId",user.get("userId"));
				controller.setMySession("roleId",user.get("roleId"));
				controller.setMySession("roleIds",user.get("roleIds"));
				controller.setMySession("nickName",user.get("nickName"));
				controller.setMySession("userName",user.get("name"));
				controller.setMySession("ifLogin","0");
				controller.setMySession("userPortrait",userPortrait);
				//token值
				controller.setMySession("WJWT", dao.getToken());
				controller.setAttr("resFlag", "0");
				controller.setAttr("msg", "登录成功！");
			}else{
				controller.setAttr("resFlag", "1");
				controller.setMySession("ifLogin","");
				controller.setAttr("msg", "密码错误！");
			}
		}else{   // 查询不到用户信息
			controller.setAttr("resFlag", "1");
			controller.setMySession("ifLogin","");
			controller.setAttr("msg", "用户不存在！");
		}
	}
	
	private void loginForAPP() throws Exception{
		controller.doIWBSESSION();
		String tel = controller.getMyParam("tel").toString();
		String pwd = controller.getMyParam("pwd").toString();
		Map<String , Object> user = dao.loginForAPP(tel);
		if(user != null && user.get("ID") != null && !"".equals(user.get("ID"))){
			if(pwd.equals(user.get("PWD"))){
				controller.setMySession("userId",user.get("ID"));
				controller.setMySession("tel",tel);
				controller.setMySession("userName",user.get("NAME") == null ? "" : user.get("NAME"));
				controller.setMySession("status",user.get("STATUS"));
				controller.setMySession("ifLogin","0");
				controller.setMySession("userPortrait",user.get("IMGPATH")== null ? "" : user.get("IMGPATH"));
				//token值
				controller.setMySession("WJWT", dao.getToken());
				controller.setAttr("resFlag", "0");
				controller.setAttr("msg", "登录成功！");
			}else{
				controller.setAttr("resFlag", "1");
				controller.setMySession("ifLogin","");
				controller.setAttr("msg", "密码错误！");
			}
		}else{   // 查询不到用户信息
			controller.setAttr("resFlag", "1");
			controller.setMySession("ifLogin","");
			controller.setAttr("msg", "用户不存在！");
		}
	}
	
	private void registerForAPP() throws Exception{
		String tel = controller.getMyParam("tel").toString();
		String pwd = controller.getMyParam("pwd").toString();
		Map<String , Object> user = dao.loginForAPP(tel);
		if(user != null && user.get("ID") != null && !"".equals(user.get("ID"))){
			controller.setAttr("resFlag", "1");
			controller.setMySession("ifLogin","");
			controller.setAttr("msg", "用户已存在！");
		}else{   // 查询不到用户信息
			Map<String , Object> registerUser = dao.registerForAPP(tel,pwd);
			if(registerUser != null){
				controller.doIWBSESSION();
				controller.setMySession("tel",tel);
				controller.setMySession("userId",registerUser.get("ID"));
				controller.setMySession("userName",registerUser.get("NAME"));
				controller.setMySession("status",registerUser.get("STATUS"));
				controller.setMySession("ifLogin","0");
				controller.setMySession("userPortrait","");
				//token值
				controller.setMySession("WJWT", dao.getToken());
				controller.setAttr("resFlag", "0");
				controller.setAttr("msg", "登录成功！");
			}
		}
	}
	
	@Override
	public void doService() throws Exception {
		Db.tx(new IAtom() {
	        @Override
	        public boolean run() throws SQLException {
	            try {
	            	if("adminLogin".equals(getLastMethodName(7))){
	        			adminLogin();
	        		}else if("login".equals(getLastMethodName(7))){
	        			login();
	        		}
	        		else if("loginForPDA".equals(getLastMethodName(7))){
	        			login();
	        		}
	        		else if("loginForAPP".equals(getLastMethodName(7))){
	        			loginForAPP();
	        		}
	        		else if("registerForAPP".equals(getLastMethodName(7))){
	        			registerForAPP();
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
	
}
