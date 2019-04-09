package com.mine.pub.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.render.Render;
import com.mine.pub.kit.DateKit;
import com.mine.pub.kit.FileKit;
import com.mine.pub.kit.JsonMyKit;
import com.mine.pub.pojo.CommonDao;
import com.mine.render.MyCaptchaRender;
/**
 * @author woody
 * @date 20160325
 * @controller 基础类
 * */
public class BaseController extends Controller
{
	private String paramStr = "";
	private Map<String,	Object> paramMap = null;
	public  Map<String,	Object> resMap = null;
	private Map<String,Object> mySession ;
	public String ifappAction = "";
	private CommonDao commonDao = new CommonDao();

	/**
	 * @author woody
	 * @date 20151001
	 * @跨域返回
	 * */
	public void renderJsonForCors(String str) {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		this.renderJson(str);
	}
	/**
	 * @author woody
	 * @date 20151001
	 * @跨域返回
	 * @注册和登录
	 * */
	public void renderJsonForCorsLoginRegister() {
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		this.renderJson();
	}
	/**
	 * @author woody
	 * @date 20151001
	 * @跨域返回
	 * */
	public void renderJsonForCors() {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		this.renderJson();
	}
	public void redirectForCors(String url) {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		this.redirect(url);
	}
	/**
	 * @author woody
	 * @date 20151001
	 * @跨域返回
	 * */
	public void renderHtmlForCors(String htmltext) {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		this.renderHtml(htmltext);
	}
	/**
	 * Render with any Render which extends Render
	 */
	public void renderForCors(Render render) {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		render(render);
	}
	/**
	 * Render with any Render which extends Render
	 */
	public void renderForCors(String str) {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		render(str);
	}
	/**
	 * Render with any Render which extends Render
	 */
	public void renderFileForCors(File file) {
		this.doIWBSESSION();
		this.doUserBaseInfo();
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		renderFile(file);
	}
	/**
	 * @author woody
	 * @date 20160308
	 * @将request读取出io流 再转成string
	 * */
	public String getParamStr()
	{
		if(paramStr == "")
		{
			paramStr = getPara("params").toString();
		}
		return paramStr;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> getParamMap()
	{
		if(this.paramMap == null)
		{
			if(getParamStr() != null && !"".equals(getParamStr()) )
			{
				paramMap = JsonMyKit.parse(getParamStr(), Map.class);
			}
		}
		return this.paramMap;
	}
	public Object getMyParam(String key)
	{
		if(getParamMap() == null || getParamMap().get(key) == null)
		{
			return null;
		}else
		{
			return getParamMap().get(key);
		}
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> getMyParamMap(String key)
	{
		if(getParamMap() == null || getParamMap().get(key) == null)
		{
			return null;
		}else
		{
			return (Map<String, Object>) getParamMap().get(key);
		}
	}
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getMyParamList(String key)
	{
		if(getParamMap() == null || getParamMap().get(key) == null)
		{
			return null;
		}else
		{
			return (List<Map<String,Object>>) getParamMap().get(key);
		}
	}
	/**
	 * @author woody
	 * @date 20160314
	 * @获取json参数中2层list值
	 * */
	@SuppressWarnings("unchecked")
	public List<Map<String,List<Map<String,Object>>>> getMyParamListMany(String key)
	{
		return (List<Map<String,List<Map<String,Object>>>>) getParamMap().get(key);  
	}
	//===================== session 处理 start =============================================
	/**
	 * @author woody
	 * @date 20151010
	 * @获取session
	 * */
	public Object getMySession(String key) {
		return getMySession() == null ? "" : getMySession().get(key);
	}
	public void setMySession(String key,Object obj) throws Exception {
		commonDao.procSession(getIWBSESSION().toString(), key, obj.toString());
		if(getMySession() == null)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(key, obj);
			setMySession(map);
		}
		else
		{
			getMySession().put(key, obj);
			setMySession(getMySession());
		}
	}
	public String getIWBSESSION() {
		if(getMyParam("IWBSESSION") == null || "".equals(getMyParam("IWBSESSION").toString()))
		{
			if(getAttr("IWBSESSION") !=null && !"".equals(getAttr("IWBSESSION").toString()))
			{
				return getAttr("IWBSESSION").toString();
			}
			else
			{
				return "";
			}
		}else
		{
			return getMyParam("IWBSESSION").toString();
		}
//		return getMyParam("IWBSESSION") == null ? "" : getMyParam("IWBSESSION").toString();
	}
	public Map<String, Object> getMySession() {
		mySession = CacheKit.get("mySession", getIWBSESSION());
		return mySession;
	}
	private void setMySession(Map<String,Object> map)
	{
		CacheKit.put("mySession", getIWBSESSION(), map);
	}
	/**
	 * @author woody
	 * @date 20151008
	 * @处理IWBSESSION
	 * */
	public void doIWBSESSION()
	{
		if(getMyParam("IWBSESSION") == null || "".equals(getMyParam("IWBSESSION")))
		{
			if("".equals(getMyParam("DEVICE_UUID").toString()))
			{
				setAttr("IWBSESSION", "BROWSER-"+DateKit.getTimestamp(commonDao.getSysdate()));
			}else
			{
				setAttr("IWBSESSION", getMyParam("DEVICE_UUID").toString()+"-"+DateKit.getTimestamp(commonDao.getSysdate()));
			}
			setMySession(new HashMap<String,Object>());
		}else
		{
			setAttr("IWBSESSION", getMyParam("IWBSESSION").toString());
		}
	}
	//===================== session 处理 end =============================================
	
	/**
	 * @author woody
	 * @date 20151109
	 * @用户基本信息
	 * */
	private void doUserBaseInfo()
	{
		setAttr("userPortrait", getMySession("userPortrait") == null ? "" : getMySession("userPortrait"));
		setAttr("ifLogin", getMySession("ifLogin") == null ? "1" : getMySession("ifLogin"));
		setAttr("nickName", getMySession("nickName") == null ? "" : getMySession("nickName")); //其实是username
		setAttr("userId", getMySession("userId") == null ? "" : getMySession("userId"));
		setAttr("userType", getMySession("userType") == null ? "" : getMySession("userType"));
		setAttr("userName", getMySession("userName") == null ? "" : getMySession("userName"));
		setAttr("realName", getMySession("realName") == null ? "" : getMySession("realName"));
		setAttr("roleId", getMySession("roleId") == null ? "" : getMySession("roleId"));
		setAttr("roleIds", getMySession("roleIds") == null ? "" : getMySession("roleIds"));
		setAttr("status", getMySession("status") == null ? "" : getMySession("status"));
		setAttr("WJWT", getMySession("WJWT") == null ? "" : getMySession("WJWT"));
		setAttr("contextPath", getRequest().getContextPath());
	}
	/** 
     * 大陆号码或香港号码均可 
     */  
    public boolean isPhoneLegal(String str)throws PatternSyntaxException {  
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);  
    }  
  
    /** 
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
     */  
    public boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  
  
    /** 
     * 香港手机号码8位数，5|6|8|9开头+7位任意数 
     */  
    public boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
        String regExp = "^(5|6|8|9)\\d{7}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }
    
    /** 
     * 链接是否存在中文
     */  
    public boolean isChinese(String str) throws PatternSyntaxException {  
        String regExp = "[\u4e00-\u9fa5]"; 
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return !m.find();
    }
    /**
     * @author woody
     * @返回验证码图片
     * */
    public void renderMyCaptcha(String str) {
    	render(new MyCaptchaRender(str));
	}
    
    
    /** 
     * 生成四位随机数
     */ 
    public String createRandom(){
    	int x;
        Random ne=new Random();//实例化一个random的对象ne
        x=ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        return x+"";
    }
    
    /** 
     * 返回验证码图片
     */ 
    public void getCheckCode() {
    	getResponse().setHeader("Access-Control-Allow-Origin", "*");
    	String session = getPara("IWBSESSION");
    	mySession = CacheKit.get("mySession", session);
    	String random = mySession == null ? "" : mySession.get("checkCode").toString();
    	MyCaptchaRender my = new MyCaptchaRender(random);
    	this.render(my);
	}
    
    /** 
     * 返回验证码图片数字
     */ 
    public void getCheckCodeNum() throws Exception {
    	this.doIWBSESSION();
    	getResponse().setHeader("Access-Control-Allow-Origin", "*");
    	String random = this.createRandom();
    	this.setMySession("checkCode",random);
    	this.setAttr("checkCode", random);
    	this.renderJson();
	}
    
    /**
	 * @author woody
	 * @date 2015-4-12
	 * @将base64转成图片存放的路径
	 * */
	public String base64ToImgpath(String content,String filename,String name)
	{
		StringBuffer sb = new StringBuffer();
		content = content.replaceAll(" ", "+");
		String contents[] = content.split("data:image/");
		for(int i = 0 ; i < contents.length ; i++)
		{
			if(contents[i].indexOf("base64,") > 0 )
			{
				String suffix = contents[i].substring(0, contents[i].indexOf(";"));
				String imgBase64 = contents[i].substring(contents[i].indexOf(";")+"base64,".length()+1);
				String imgPath = FileKit.imgFromBase64(imgBase64, filename, name,suffix);
				contents[i] = contents[i].replace(contents[i].substring(0), imgPath);
			}
			sb.append(contents[i].trim());
		}
		return sb.toString();
	}
}
