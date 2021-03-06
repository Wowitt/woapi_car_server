package com.mine.rd.controllers.demo;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.ext.kit.excel.PoiExporter;
import com.ext.kit.excel.PoiImporter;
import com.ext.kit.excel.Rule;
import com.ext.render.excel.PoiRender;
import com.itextpdf.text.DocumentException;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;
import com.mine.pub.controller.BaseController;
import com.mine.pub.kit.DateKit;
import com.mine.pub.kit.ITextKit;
import com.mine.pub.service.Service;
import com.mine.rd.services.car.pojo.CarDao;
import com.mine.rd.services.demo.pojo.DemoDao;
import com.mine.rd.services.demo.service.DemoService;
import com.mine.rd.websocket.MyWebSocket;
@Clear
public class DemoController extends BaseController{
	
	private Logger logger = Logger.getLogger(DemoController.class);
	DemoDao dao = new DemoDao();
	static int count = 0;
	
	public void index(){
		renderJson("aaa");
	}
	
	public void renderCaptcha1() {
		// TODO Auto-generated method stub
		renderMyCaptcha("AAA");
	}
	public void exportPdf() throws IOException, DocumentException{
		String DEST = PathKit.getWebRootPath()+"/output/1.pdf";
		System.out.println("===>>"+DEST);
		String HTML = PathKit.getWebRootPath()+"/templates/headers.html";
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		ITextKit.createPdf(DEST, HTML);
		renderJson();
	}
	/**
	 * @author woody
	 * @测试读取特定格式excel
	 * @date 20170804
	 * */
	public void testExcel()
	{
		String toPath = "e:\\C90B7000.xls";
		int resInt = 0;
		File file = new File(toPath);
		List<List<List<String>>> list = PoiImporter.readExcel(file, new Rule());
		System.out.println("list.size===>"+list.size());
		PoiExporter.data(list);
//		for(List<List<String>> lists : list)
//		{
//			lists.remove(0);
//			List<List<String>> listData = lists;
//			for(List<String> listss : listData)
//			{
//				resInt =  doExcelRow(listss);
//				System.out.println("===> " + listss.size());
//			}
//		}
		String filename = "demo.xlsx";
		int num = 1;
		String[] sheetNames = new String[num];
		sheetNames[0] = "111";
		String[][] headers = new String[num][];
		headers[0][0] = "1";
		headers[0][1] = "1";
		headers[0][2] = "1";
		headers[0][3] = "1";
		headers[0][4] = "1";
		headers[0][5] = "1";
		headers[0][6] = "1";
		headers[0][7] = "1";
		headers[0][8] = "1";
		headers[0][9] = "1";
		headers[0][10] = "1";
		headers[0][11] = "1";
		headers[0][12] = "1";
		headers[0][13] = "1";
		headers[0][14] = "1";
		headers[0][15] = "1";
		headers[0][16] = "1";
		headers[0][17] = "1";
		headers[0][18] = "1";
		String[][] columns = new String[num][];
		
		render(PoiRender.me(list).fileName(filename).sheetName(sheetNames).headers(headers).columns(columns).cellWidth(3000));
	}
	public void testExcel2()
	{
		String fileToBeRead = "e:\\leimuban1.xls"; // excel位置
        int coloum = 0; // 比如你要获取第1列
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
            HSSFSheet sheet = workbook.getSheet("Sheet1");
 
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow((short) i);
                if (null == row) {
                    continue;
                } else {
                	if(row.getRowNum() == 9)
                	{
                		HSSFRow rowadd = createRow(sheet,15);  
                		createCell(rowadd);
                		System.out.println("getRowNum===>"+row.getRowNum());
                		HSSFCell cell = row.getCell(16);
                        if (null == cell) {
                            continue;
                        } else {
                            System.out.println("cell===>"+cell);
//                            System.out.println("celltype===>"+cell.getCellType());
//                            System.out.println("cellstringvalue===>"+cell.getStringCellValue());
                              int temp = (int) cell.getNumericCellValue();
                              cell.setCellValue(temp + 20);
                        }
                	}
                }
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(fileToBeRead);
                workbook.setForceFormulaRecalculation(true);
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		renderJson("{\"key\":\"test\"}");
	}
	/** 
    * 找到需要插入的行数，并新建一个POI的row对象 
    * @param sheet 
    * @param rowIndex 
    * @return 
    */  
    private HSSFRow createRow(HSSFSheet sheet, Integer rowIndex) {  
    	HSSFRow row = null;  
        if (sheet.getRow(rowIndex) != null) {  
            int lastRowNo = sheet.getLastRowNum();  
            sheet.shiftRows(rowIndex, lastRowNo, 1);  
        }  
        row = sheet.createRow(rowIndex);  
        return row;  
    } 
    /** 
     * 创建要出入的行中单元格 
     * @param row 
     * @return 
     */  
    private HSSFCell createCell(HSSFRow row) {  
    	HSSFCell cell = row.createCell(0);  
        cell.setCellValue(999999);  
        row.createCell(1).setCellValue(1.2);  
        row.createCell(2).setCellValue("This is a string cell");  
        return cell;  
    }  
	/**
	 * @author woody
	 * @查看国家对接接口流水表
	 * */
	public void checkUploadInterfaceFlow()
	{
		List<Record> list = Db.find("select count,status,CONVERT(varchar(20),sysdate, 20) sysdate from UPLOAD_INTERFACE_FLOW order by sysdate desc ");
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		for(Record record : list)
		{
			listMap.add(record.getColumns());
		}
		renderJson(listMap);
	}
	
//	public void register()
//	{
//		mixReturn("");
//	}
	public void wsTest()
	{
		System.out.println("wsTest===>>"+count++);
		renderJson("111");
	}
	
	public void wsSend()
	{
		for(MyWebSocket item: MyWebSocket.webSocketSet){  
            try {
            	System.out.println("aaaaa");
            	item.sendMessage("");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
		}
		renderJson("111");
	}
	public void getMenuList()
	{
		List list = CacheKit.getKeys("mySession");
		String userId = "sysadmin";
		setAttr("menulist", dao.getMenuList(userId));
		System.out.println("===>"+dao.getMenuList(userId));
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		renderJson();
	}
//	public void demoFile()
//	{
//		mixRenderReturn("index.html");
//	}
//	public void demoFile222()
//	{
//		mixRenderReturn("file/index.jsp");
//	}
//	public void test111()
//	{
//		mixRenderReturn("HtmlPage1.html");
//	}
//	public void test2()
//	{
//		mixRenderReturn("HtmlPage2.html");
//	}
//	public void demoExcelImport()
//	{
//		mixRenderReturn("file/excelImport.jsp");
////		render("excelImport.html");
//	}
//	public void sendFile()
//	{
//		//上传并获取request
//		//	UploadFile uploadFile = getFile("fileuploaddata", "uploads/coures/"+uerid, 150*1024*1024); //最大不要超过150m
//		UploadFile uploadFile = getFile("fileuploaddata", "::c:/1111111", 150*1024*1024); //最大不要超过150m
//		String uploadFilePath = uploadFile.getUploadPath() + "/" + uploadFile.getFileName();
//		PdfKit.convert2PDF(uploadFilePath, "c:/1111111/"+uploadFile.getFileName()+".pdf");
//		PdfKit.convert2PDF(uploadFilePath, "h:/1111111/"+uploadFile.getFileName()+".pdf");
//		PdfKit.convert2PDF(uploadFilePath, "h:/1111112/123.pdf");
//		mixReturn();
//	}
//	public void test1(){
//		mixRenderReturn("file/index.jsp");
//	}
//	
//	public void toPdf()
//	{
//		mixRenderReturn("file/indexFile.jsp");
//	}
//	
//
//	public void toPdfFile()
//	{
//		//上传并获取request
//		UploadFile uploadFile = getFile("Filedata", "testToPdf/", 150*1024*1024); //最大不要超过150m
//		String uploadFilePath = uploadFile.getUploadPath() + "/" + uploadFile.getFileName();
//		PdfKit.convert2PDF(uploadFilePath, uploadFile.getUploadPath()+uploadFile.getFileName()+".pdf");
//		String path = uploadFile.getUploadPath()+uploadFile.getFileName()+".pdf";
//		path = path.replace('\\', '/');
//		path = "http://localhost:9001/upload" + path.split("/upload")[1];
//		setAttr("path", path);
//		mixReturn();
////		upload(7, "1");
//	}
//	
//	/**
//	 * @author woody
//	 * @date 20151019
//	 * @导入excel
//	 * */
//	
//	public void importExcel()
//	{
//		String toPath = "";
//		@SuppressWarnings("unused")
//		int resInt = 0;
//		try {
//			//上传并获取request
//			//显示进度条
//			UploadFile uploadFile = getFile("Filedata", "import/student/excel", 15*1024*1024); //最大不要超过150m
//			//正常上传
////			UploadFile uploadFile = getFile("fileuploaddata", "import/student/excel", 15*1024*1024); //最大不要超过150m
//			if(uploadFile != null && uploadFile.getUploadPath() != null )
//			{
//				toPath = uploadFile.getUploadPath() + "/" + uploadFile.getFileName();
//				File file = new File(toPath);
//				List<List<List<String>>> list = PoiImporter.readExcel(file, new Rule());
//				System.out.println("list.size===>"+list.size());
//				for(List<List<String>> lists : list)
//				{
//					lists.remove(0);
//					List<List<String>> listData = lists;
//					for(List<String> listss : listData)
//					{
//						resInt =  doExcelRow(listss);
//						System.out.println("===> " + listss.size());
//					}
//				}
//			}
//			mixReturn("");
//		} 
//		catch (Exception e) {
//			System.err.println("上传失败");
//			logger.error(e.getMessage(),e);
//			setAttr("msg", "上传失败");
//			renderText("Error");
//			e.printStackTrace();
//			throw(e);
//		}
//	}
	/**
	 * @author woody
	 * @date 20151019
	 * @处理excel中每行记录
	 * @return 0-序号为非法字符 1-成功
	 * */
	@SuppressWarnings("unused")
	private int doExcelRow(List<String> list)
	{
		String[] strs = list.get(0).split("\\."); 
		int resInt = 0; //默认非法
		if(strs.length == 3 || strs.length == 4)
		{
			Map<String,String> map = new HashMap<String, String>();
			map.put("NO", strs[0]);
			resInt = 1;
		}
		else
		{
			resInt = 0;
		}
		return resInt;
	}
	/**
	 * @author woody
	 * @date 20150919
	 * @导出excel
	 * */
//	public void export()
//	{
//		ExcelDemoDao dao = new ExcelDemoDao();
//		String filename = "demo.xlsx";
//		int num = dao.querySheets().size();
//		String[] sheetNames = new String[num];
//		List<?>[] sheetAllList = new ArrayList<?>[num];
//		String[][] headers = new String[num][];
//		String[][] columns = new String[num][];
//		dao.getExcelInfo(sheetNames,sheetAllList,headers,columns);
//		mixRenderReturn(PoiRender.me(sheetAllList).fileName(filename).sheetName(sheetNames).headers(headers).columns(columns).cellWidth(3000));
//	}
//
//	public void export_bak()
//	{
//		String filename = "demo.xlsx";
//		List<Record> list = CacheKit.get("mydict", "demo");
//		int num = list.size();
//		String[] sheetNames = new String[num];
//		List<?>[] projectAllList = new ArrayList<?>[num];
//		String[][] headers = new String[num][];
//		String[][] columns = new String[num][];
//		String[] cols = new String[5];
//		cols[0] = "id";
//		cols[1] = "id_main";
//		cols[2] = "dict_id";
//		cols[3] = "dict_value";
//		cols[4] = "status";
//		for(int i = 0 ; i < list.size() ; i++ )
//		{
//			List<Object> data = Lists.newArrayList();
//			sheetNames[i] = "demo"+i;
//			for(Record one : list)
//			{
//				data.add(one.getColumns());
//			}
//			projectAllList[i] = data;
//			headers[i] = cols;
//			columns[i] = cols;
//		}
//
//		mixRenderReturn(PoiRender.me(projectAllList).fileName(filename).sheetName(sheetNames).headers(headers).columns(columns).cellWidth(3000));
//	}
	
	/**
	 * @author ouyangxu
	 * @date 20160815
	 * @app 富文本编辑器上传图片
	 **/
	public void textImage(){
		logger.info("富文本编辑器上传图片");
		try {
			UploadFile uploadFile = getFile("Filedata", "textimage/", 20*1024*1024); //最大不要超过20m
			String uploadFilePath = "";
			String path = "uploadAttachment" + DateKit.toStr(new Date(), "yyyyMMddHHmmss");
			String fileName = "";
			boolean flag = uploadFile.getFile().renameTo(new File(uploadFile.getUploadPath() + "/" + path + uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf("."))));
			fileName = uploadFile.getFileName();
			System.out.println(fileName);
			System.out.println(flag);
			if(flag == true){
				uploadFilePath = uploadFile.getUploadPath() + "/" + path + uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf("."));
				System.out.println(uploadFilePath);
			}
			uploadFilePath = uploadFilePath.replaceAll("\\\\", "/");
			uploadFilePath = uploadFilePath.replaceAll("//", "/");
			String[] picPath = uploadFilePath.split("onlineschool");
			System.out.println("/webapps"+picPath[1]);
			System.out.println("path============>/webapps"+picPath[1]);
			String resJson="";
			if(!"".equals(picPath[1])){
				//富文本上传成功
				resJson="{\"error\":0,\"url\":\"/webapps"+picPath[1]+"\"}";	
			}else{
				//富文本上传失败
				resJson="{\"error\":1,\"message\":\"fail\"}";	
			}
			System.out.println("11=========>" + resJson);
			HttpServletResponse response = this.getResponse();
			try {
				response.setHeader("Content-Type", "text/html;charset=UTF-8");
				response.getWriter().write(resJson);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} catch (Exception e) {
			logger.error("富文本编辑器上传图片异常===>" + e.getMessage());
			e.printStackTrace();
			throw new ActiveRecordException(e);
		}
		renderNull();
	}
	public void uploadExcel(){
		logger.info("上传excel");
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					UploadFile uploadFile = getFile("uploadExcel", "excels/car/", 10*1024*1024);
					String filePath = "";
					if(uploadFile != null && uploadFile.getUploadPath() != null )
					{
							filePath = uploadFile.getUploadPath()+uploadFile.getFileName();
							System.out.println(filePath);
							File file = new File(filePath);
							List<List<List<String>>> list = PoiImporter.readExcel(file, new Rule());
							CarDao dao = new CarDao(); 
							System.out.println("list.size===>"+list.size());
							for(List<List<String>> lists : list)
							{
								lists.remove(0);
								List<List<String>> listData = lists;
								for(List<String> listss : listData)
								{
									saveTable(listss,dao);
//										System.out.println("===> " + listss.size());
								}
							}
							setAttr("msg", "上传成功"+",操作数据"+(list.get(0).size())+"条");
			                setAttr("resFlag", "0");
					}else{
						setAttr("msg", "上传失败");
		                setAttr("resFlag", "1");
					}
	            } catch (Exception e) {
	            	logger.error("上传excel异常===>" + e.getMessage());
	                e.printStackTrace();
	                setAttr("msg", "上传失败");
	                setAttr("resFlag", "1");
	                return false;
	            }
	            return true;
			}}
		);
		renderJson();
	}
	public void uploadComponentExcel(){
		logger.info("上传零件库excel");
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					UploadFile uploadFile = getFile("uploadExcel", "excels/component/", 10*1024*1024);
					String filePath = "";
					if(uploadFile != null && uploadFile.getUploadPath() != null )
					{
						filePath = uploadFile.getUploadPath()+uploadFile.getFileName();
						System.out.println(filePath);
						File file = new File(filePath);
						List<List<List<String>>> list = PoiImporter.readExcel(file, new Rule());
						CarDao dao = new CarDao(); 
						System.out.println("list.size===>"+list.size());
						for(List<List<String>> lists : list)
						{
							lists.remove(0);
							List<List<String>> listData = lists;
							for(List<String> listss : listData)
							{
								saveComponent(listss,dao);
//										System.out.println("===> " + listss.size());
							}
						}
						setAttr("msg", "上传成功"+",操作数据"+(list.get(0).size())+"条");
						setAttr("resFlag", "0");
					}else{
						setAttr("msg", "上传失败");
						setAttr("resFlag", "1");
					}
				} catch (Exception e) {
					logger.error("上传零件库excel异常===>" + e.getMessage());
					e.printStackTrace();
					setAttr("msg", "上传失败");
					setAttr("resFlag", "1");
					return false;
				}
				return true;
			}}
				);
		renderJson();
	}
	
	private void saveTable(List<String> list,CarDao dao)
	{
		boolean flag = true;
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i = 0 ; i< list.size() ; i++){
			if(i == 0 ){
				map.put("PURCHASE", list.get(i));
			}
			else if(i == 1){
				map.put("CAR_MODEL", list.get(i));
			}
			else if(i == 2){
				if(list.get(i) !=null && !"".equals(list.get(i))){
					map.put("FRAME_NUMBER", list.get(i));
				}else{
					flag = false;
					break;
				}
			}
			else if(i == 3){
				if(list.get(i) !=null && !"".equals(list.get(i))){
					map.put("ENGINE_NUMBER", list.get(i));
				}else{
					flag = false;
					break;
				}
			}
			else if(i == 4){
				if(list.get(i) !=null && !"".equals(list.get(i))){
					map.put("PLATE_NUM", list.get(i));
				}else{
					flag = false;
					break;
				}
			}
			else if(i == 5){
				map.put("INVOICE", list.get(i));
			}
			else if(i == 6){
				map.put("STATUSNAME", list.get(i));
			}
			else if(i == 7){
				map.put("DRIVER_YEAR", list.get(i));
			}
			else if(i == 8){
				map.put("DRIVER_MONTH", list.get(i));
			}
			else if(i == 9){
				map.put("DRIVER_DATE", list.get(i));
			}
			else if(i == 10){
				if(list.get(i) !=null && !"".equals(list.get(i)) && !"#".equals(list.get(i).substring(0, 1))){
					map.put("BUS_DATE", list.get(i));
				}else{
					map.put("BUS_DATE", null);
				}
			}
			else if(i == 11){
				if(list.get(i) !=null && !"".equals(list.get(i)) && !"#".equals(list.get(i).substring(0, 1))){
					map.put("FORCE_DATE", list.get(i));
				}else{
					map.put("FORCE_DATE", null);
				}
			}
			else if(i == 12){
				map.put("FORCE_IS",list.get(i).toString());
			}
			else if(i == 13){
				map.put("BUS_IS",list.get(i).toString());
			}
			else if(i == 14){
				map.put("USER_NAME",list.get(i));
			}
		}
		map.put("OWNER","恒天");
		if(flag){
			dao.saveCar(map);
			dao.saveCarInsureance(map);
			dao.saveCarSaleMan(map);
		}	
	}
	private void saveComponent(List<String> list,CarDao dao)
	{
		boolean flag = true;
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i = 0 ; i< list.size() ; i++){
			if(i == 0 ){
				if(list.get(i) !=null && !"".equals(list.get(i))){
					map.put("NAME", list.get(i));
				}else{
					flag = false;
					break;
				}
			}
			else if(i == 1){
				map.put("SPEC", list.get(i));
			}
			else if(i == 2){
				map.put("KIND", list.get(i));
			}
			else if(i == 3){
				map.put("UNIT", list.get(i));
			}
			else if(i == 4){
				if(list.get(i) !=null && !"".equals(list.get(i))){
					map.put("BAR_CODE", list.get(i));
				}else{
					flag = false;
					break;
				}
			}
			else if(i == 5){
				map.put("CODE", list.get(i));
			}
			else if(i == 6){
				map.put("REMARK", list.get(i));
			}
			else if(i == 7){
				map.put("SALE_PRICE", list.get(i));
			}
			else if(i == 8){
				map.put("COST_PRICE", list.get(i));
			}
			else if(i == 9){
				map.put("PURCHASE_PRICE", list.get(i));
			}
			else if(i == 10){
				map.put("NUM", list.get(i));
			}
			else if(i == 11){
				map.put("NUM", list.get(i));
			}
			else if(i == 12){
				map.put("MONEY",list.get(i).toString());
			}
			else if(i == 13){
				map.put("BASE_NUM",list.get(i).toString());
			}
		}
		if(flag){
			dao.saveCarComponent(map);
		}	
	}
	
	public void uploadLocalTransferData()
	{
		Service service = new DemoService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("上传国家数据==>" + e.getMessage());
			e.printStackTrace();
		}
		renderJson();
	}
	public void testPost() throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = null;  
		InputStream inputStream = getRequest().getInputStream();
		if (inputStream != null) {  
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  

            char[] charBuffer = new char[128];  
            
            
            int bytesRead = -1;  

            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {  
                sb.append(charBuffer, 0, bytesRead);  
            }  
        } else {  
            sb.append("");  
        }  
		System.out.println("params=====>>"+sb.toString());
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
//		renderJson("{'id':'1'}");
		renderJson("{\"id\":\"adfa\"}");
//		renderText("aaa");
	}
	
	public void carpark() {
		logger.info("查看停车场信息");
		Service service = new DemoService(this);
		try {
			service.doService();
		} catch (Exception e) {
			logger.error("查看停车场信息异常===>" + e.getMessage());
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
	
	public void test() {
		Service service = new DemoService(this);
		try {
			service.doService();
		} catch (Exception e) {
			this.setAttr("msg", "系统异常！");
			this.setAttr("resFlag", "1");
			e.printStackTrace();
		}
		renderJson();
	}
	
	public void testword(){
		Map<String, Object> map=new HashMap<String, Object>();
        map.put("[sum]", "长沙");
        map.put("多少啊", "哈哈哈哈哈");
        String wordId = DateKit.toStr(new Date(),"yyyyMMddHHmmssSSS");
        String sourcePath = PathKit.getWebRootPath() + "/poi_template/xxx.docx";
        String targetPath = PathKit.getWebRootPath() + "/poi_template/xxx" + wordId  + ".docx";
        try {
            replaceAndSaveDoc(sourcePath, map , targetPath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		renderJson();
	}
	
	public void replaceAndSaveDoc(String sourcePath, Map<String, Object> param , String targetPath) throws FileNotFoundException{
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
    private XWPFDocument replaceDoc(InputStream fis, Map<String, Object> param) {
        try {
            //InputStream fis = new FileInputStream(f);
            //InputStream fis = new FileInputStream(filePath);
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
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    private void outPutWord(XWPFDocument doc , String targetPath) throws FileNotFoundException{
        try {
            if (doc != null) {
                OutputStream os = new FileOutputStream(targetPath);
                doc.write(os);
                os.close();
                System.out.println("已替换word文件，文件地址：" + targetPath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
