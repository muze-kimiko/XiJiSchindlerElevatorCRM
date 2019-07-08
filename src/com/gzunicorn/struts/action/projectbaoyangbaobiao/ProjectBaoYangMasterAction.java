package com.gzunicorn.struts.action.projectbaoyangbaobiao;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.logic.DataOperation;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.action.ServeTableForm;



public class ProjectBaoYangMasterAction extends DispatchAction {
	/**
	 * 电梯保养资料总表报表
	 * 
	 * @author Administrator
	 * 
	 */
		Log log = LogFactory.getLog("ProjectBaoYangMasterAction.class");

		BaseDataImpl bd = new BaseDataImpl();

		/**
		 * Method execute
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return ActionForward
		 */
		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String name = request.getParameter("method");
			if (name == null || name.equals("")) {
				name = "toSearchCondition";
			}
			ActionForward forward = dispatchMethod(mapping, form, request,
					response, name);
			return forward;
		}

		/**
		 * 显示查询条件内容
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward toSearchCondition(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			request.setAttribute("navigator.location", "保养资料总表报表 >> 查询");
			HttpSession session=request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			List mugStorages = null;
			List grcidlist=new ArrayList();
			Session hs = null;
			try {
				hs = HibernateUtil.getSession();
				grcidlist=Grcnamelist1.getgrcnamelist(hs,userInfo.getUserID());
				mugStorages=Grcnamelist1.getStorageName(hs,userInfo.getUserID());//保养站
			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					hs.close();
				} catch (HibernateException e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("grcidlist",grcidlist);
			request.setAttribute("mugStorages", mugStorages);
			return mapping.findForward("condition");
		}

		/**
		 * Method toSearchRecord execute, Search record
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return ActionForward
		 */

		public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			ServeTableForm tableForm = (ServeTableForm) form;
			HttpSession session = request.getSession();
			// 保存是不是从查询后点击导出excel,如果是的话,flag中的值应该为Y
			String flag = tableForm.getProperty("genReport");
			//合同号
			String contractid = tableForm.getProperty("contractid");
			if (contractid == null || "".equals(contractid)) {
				contractid = "%";
			} else {
				contractid = "%"+contractid.trim()+"%";
			}
			session.setAttribute("contractid", contractid);
			//电梯型号
			String elevatorid = tableForm.getProperty("elevatorid");
			if (elevatorid == null || "".equals(elevatorid)) {
				elevatorid = "%";
			} else {
				elevatorid = "%"+elevatorid.trim()+"%";
			}
			session.setAttribute("elevatorid", elevatorid);
			// 保养开始日期
			String startdate = tableForm.getProperty("startdate");
			if (startdate == null || "".equals(startdate)) {
				//startdate = "0000-00-00";
				startdate = "";
			} else {
				startdate = startdate.trim();
			}
			// 保养结束日期
			String enddate = tableForm.getProperty("enddate");
			if (enddate == null || "".equals(enddate)) {
				enddate = "9999-99-99";
			} else {
				enddate = enddate.trim();
			}
			// 退保开始日期
			String tstartdate = tableForm.getProperty("tstartdate");
			if (tstartdate == null || "".equals(tstartdate)) {
				//tstartdate = "0000-00-00";
				tstartdate = "";
			} else {
				tstartdate = tstartdate.trim();
			}
			session.setAttribute("tstartdate", tstartdate);
			// 退保结束日期
			String tenddate = tableForm.getProperty("tenddate");
			if (tenddate == null || "".equals(tenddate)) {
				tenddate = "9999-99-99";
			} else {
				tenddate = tenddate.trim();
			}
			session.setAttribute("tenddate", tenddate);
			// 甲方单位
			String custname = tableForm.getProperty("custname");
			if (custname == null || "".equals(custname)) {
				custname = "%";
			} else {
				custname = "%"+custname.trim()+"%";
			}
			session.setAttribute("custname", custname);
			// 合同性质
			String searchflags = tableForm.getProperty("searchflags");
			if (searchflags == null || "".equals(searchflags)) {
				searchflags = "";
			} else {
				searchflags = searchflags.trim();
			}
			session.setAttribute("searchflags", searchflags);
			// 保养站内容。
			String storageid = tableForm.getProperty("storageids");
			if (storageid == null || "".equals(storageid)) {
				storageid = "%";
			} else {
				storageid = storageid.trim();
			}
			//所属维保分部
			String genReport=tableForm.getProperty("genReport");
			String grcid22=tableForm.getProperty("grcid22");
			String grcid = tableForm.getProperty("grcid");
			if (grcid == null || "".equals(grcid)) {
				if(genReport!= null && (genReport.equals("Y") || "Y".equals(flag))){
					grcid=grcid22;
				}else{
				grcid = "";
				}
			} else {
				grcid = grcid.trim();
			}
			session.setAttribute("mugStorages", storageid);
			List tempList = new ArrayList();
			float _nowfeeprice = 0;
			float _nonowfeeprice = 0;
			float _billatm = 0;
			float _billnoatm = 0;
			float _nobillatm = 0;
			float _nobillnoatm = 0;
			float _countpremoneyprice = 0;
			Connection conn = null;
			String sql = null;
			HashMap map = new HashMap();
			try {
				conn = HibernateUtil.getSession().connection();
				map.put("startdate",startdate );
				map.put("enddate", enddate);
				sql = "exec SP_MAINMUG_CONTRACT_INFO_QUERY '" + contractid + "','"
						+ custname + "','" +elevatorid+"','"+startdate+"','"+enddate+"','"+tstartdate+"','"+tenddate+"','"+searchflags+"','"+grcid+"'" ;
				//System.out.println(sql);
				DataOperation op = new DataOperation();
				op.setCon(conn);
				tempList = op.getSPList(sql);
				List list = new ArrayList();
				String floor = null;
				String stage = null;
				String door = null;
				for (Iterator it = tempList.iterator(); it.hasNext();) {
					HashMap _map = (HashMap) it.next();
					if (_map.get("floor") != null){
						floor = (String)_map.get("floor");
					}
					if (_map.get("stage") != null){
						stage = (String)_map.get("stage");
					}
					if (_map.get("door") != null){
						door = (String)_map.get("door");
					}
					String floorstagedoor = floor+"/"+stage+"/"+door;
					_map.put("floorstagedoor",floorstagedoor);
					list.add(_map);		
				}
				request.setAttribute("dateinfo", map);
				request.setAttribute("report_list", list);
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}

			ActionForward forward = null;
			request.setAttribute("grcid22", grcid);
			session.setAttribute("billatm", String.valueOf(_billatm));
			session.setAttribute("billnoatm", String.valueOf(_billnoatm));
			session.setAttribute("nobillatm", String.valueOf(_nobillatm));
			session.setAttribute("nobillnoatm", String.valueOf(_nobillnoatm));
			session.setAttribute("nowfeeprice", String.valueOf(_nowfeeprice));
			session.setAttribute("nonowfeeprice", String.valueOf(_nonowfeeprice));
			session.setAttribute("totalprice", String.valueOf(_countpremoneyprice));
			if (!"Y".equals(flag)) {
				session.setAttribute("result", tempList);
			}

			if (tableForm.getProperty("genReport") != null
					&& tableForm.getProperty("genReport").equals("Y")
					|| "Y".equals(flag)) {
				try {
					response = toExcelRecord(tempList, request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				forward = mapping.findForward("returnList");
			}
			return forward;
		}
		/**
		 * 导出查询数据到Excel
		 * @param resultList
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		public HttpServletResponse toExcelRecord(List resultList,
				HttpServletRequest request, HttpServletResponse response) throws IOException {
			
		XSSFWorkbook wb = new XSSFWorkbook();
		//配置表头的共用单元格样式
		XSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中
		cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中
		XSSFFont f  = wb.createFont();
		f.setFontHeightInPoints((short) 11);//字号
		f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗
		cs.setFont(f);
		cs.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		XSSFCellStyle cc = wb.createCellStyle();
		cc.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中
		cc.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中
		cc.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cc.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cc.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cc.setBorderRight(XSSFCellStyle.BORDER_THIN);
		int rowlistLen=resultList.size();
		//System.out.println(rowlistLen);
		
		try{

			String headstr="合同号,站别,甲方单位,电梯编号,层/站/门,开始时间,到期时间,退保时间,联系人,联系电话,联系地址,保养地址,合同状态,所属维保分部";
			String key1str="contractid,nodename,custname,elevatorid,floorstagedoor,mugstartdate,mugenddate,backdate,linkman,linkphone,linkaddress,mugaddress,searchflag,grcname";
			
			String[] headName = headstr.split(",");
			int headNameLen = headName.length;//表头个数
		
			int row0lineNo=0;
			int rowno=0;
			XSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, "保养资料总表报表");					
					
			/*输出第一行表头开始*/
			XSSFRow row0 = sheet.createRow( rowno); // 创建第一行
			XSSFCell cell0=null;
			for (int i = 0; i < headNameLen; i++) {				
				cell0 = row0.createCell((short) row0lineNo);
				if (i==4||i==9||i==10||i==11) {
					cell0.setCellValue(headName[i]);
					cell0.setCellStyle(cc);	
				}else {
					cell0.setCellValue(headName[i]);
					cell0.setCellStyle(cs);	
				}								
				row0lineNo++;
			}
			rowno+=1;
			String[] key1Name = key1str.split(",");
			int key1NameLen = key1Name.length;
			/*输出对应表头的数值*/
			HashMap rowMap=null;
			int rownlineNo=0;
			if(rowlistLen>0){				
				for (int k = 0 ;  k < rowlistLen; k++) {	
					rownlineNo=0;
					row0 = sheet.createRow( rowno);
					rowMap = (HashMap) resultList.get(k);	
					for (int m = 0; m < key1NameLen; m++) {
						cell0 = row0.createCell((short) rownlineNo);
						if (m==4||m==9||m==10||m==11) {
							cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							cell0.setCellStyle(cc);
						}else {
							cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							cell0.setCellStyle(cs);
						}	
						rownlineNo++;
					}
					rowno++;
				}
				/*输出最后表尾开始*/
				String footstr="总记录数:"+rowlistLen+"条";
				XSSFRow rowo = sheet.createRow( (rowno)); // 创建最后一行			
					cell0 = rowo.createCell((short)0,5);
					cell0.setCellValue(footstr);
					cell0.setCellStyle(cs);					
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("保养资料总表报表", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
		
		
	}