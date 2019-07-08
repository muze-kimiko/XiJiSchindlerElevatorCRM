package com.gzunicorn.struts.action.installationreport;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.sysmanager.Company;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ����ģ��=>����ͨ���ʱ���
 * @author Lijun
 *
 */
public class FactoryPassRateReportAction extends DispatchAction {

	Log log = LogFactory.getLog(FactoryPassRateReportAction.class);

	BaseDataImpl bd = new BaseDataImpl();
	DecimalFormat df = new DecimalFormat("##.##"); 


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "factorypassratereport", null);
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchRecord";
			return dispatchMethod(mapping, form, request, response, name);
		} else {
			ActionForward forward = super.execute(mapping, form, request,
					response);
			return forward;
		}

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

	@SuppressWarnings("unchecked")
	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("navigator.location", " ����ͨ���ʱ���>> ��ѯ");
		DynaActionForm dform = (DynaActionForm) form;
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs=null;
		try {
			
			String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			String day1=DateUtil.getDate(day, "MM", -1);//��ǰ�����·ݼ�1 ��
			dform.set("sdate1", day1);
			dform.set("edate1", day);
			
			hs=HibernateUtil.getSession();
			String sql1="from Company where comtype in('1','2')  and enabledflag='Y' and comid='"+userInfo.getComID()+"' order by comid desc";				
			List list1=hs.createQuery(sql1).list();
			
			if(list1!=null && list1.size()>0){
				request.setAttribute("departmentList", list1);
			}else{
				List departmentList=new ArrayList();
				String sql="from Company where comtype in('1','2') and enabledflag='Y' order by comid";				
				List list=hs.createQuery(sql).list();
				if(list!=null && list.size()>0){
					Map map1=new HashMap();
					//map1.put("comid", "");
					//map1.put("comfullname", "ȫ��");
					//departmentList.add(map1);
					for(Object object : list){
						Company com=(Company)object;
						Map map=new HashMap();
						map.put("comid", com.getComid());
						map.put("comfullname", com.getComfullname());
						departmentList.add(map);
					}
				}
				request.setAttribute("departmentList", departmentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		forward = mapping.findForward("factoryPassRateReportSearch");		
		return forward;
	}

	/**
	 * Get the navigation description from the properties file by navigation
	 * key;
	 * 
	 * @param request
	 * @param navigation
	 */

	private void setNavigation(HttpServletRequest request, String navigation) {
		Locale locale = this.getLocale(request);
		MessageResources messages = getResources(request);
		request.setAttribute("navigator.location",messages.getMessage(locale, navigation));
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
	@SuppressWarnings("unchecked")
	public ActionForward toSearchResults(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("navigator.location", " ����ͨ���ʱ���>> ��ѯ���");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		String action = dform.toString();
		if (dform.get("genReport") != null
				&& !dform.get("genReport").equals("")) {
			try {
				response = toExcelRecord(form, request, response);
				forward = mapping.findForward("exportExcel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String[] departmentarr = request.getParameterValues("department");
			String sdate1=request.getParameter("sdate1");
			String edate1=request.getParameter("edate1");
			String projectprovince=request.getParameter("projectprovince");
			String ishuizong=request.getParameter("ishuizong");//�Ƿ��������
			
			Session hs = null;
			int maxCheck=0;
			String departmentstr="";
			try {
				hs = HibernateUtil.getSession();

				
				request.setAttribute("searchcontent", "[�ύ��������:"+sdate1+" �� "+edate1+"����Ŀʡ��:"+projectprovince+"]");
				
				String sql="select isnull(max(checkNum),0) from ElevatorTransferCaseRegister";
				List list1=hs.createSQLQuery(sql).list();
				if(list1!=null && list1.size()>0){
					maxCheck=(Integer) list1.get(0);
				}
				if(maxCheck>0){
					List maxList=new ArrayList();
					for(int k=0;k<maxCheck;k++){
						maxList.add(k);
					}
					
					request.setAttribute("maxList",maxList);
					request.setAttribute("length", maxCheck);
					
					List relist=new ArrayList();
					
					if(ishuizong!=null && ishuizong.trim().equals("Y")){
						//����
						String departmentName="";
						String departmentstr2="";
						for(int i=0;i<departmentarr.length;i++){
							if(i==departmentarr.length-1){
								departmentstr+=departmentarr[i];
								departmentName+=bd.getName("Company", "comfullname", "comid", departmentarr[i]);
								departmentstr2+=departmentarr[i];
							}else{
								departmentstr+=departmentarr[i]+",";
								departmentName+=bd.getName("Company", "comfullname", "comid", departmentarr[i])+",<br/>";
								departmentstr2+=departmentarr[i]+"','";
							}
						}
						
						HashMap remap=new HashMap();
						remap.put("departmentName",departmentName);
						List tDList=this.queryReport1("T", "�����ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List tSList=this.queryReport1("T", "�豸��ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List tList=this.queryReport1("T", "", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List fDList=this.queryReport1("F", "�����ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List fSList=this.queryReport1("F", "�豸��ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List fList=this.queryReport1("F", "", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List dList=this.queryReport1("", "�����ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List sList=this.queryReport1("", "�豸��ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            List totalList=this.queryReport1("", "", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
			            if(tDList!=null && tDList.size()>0)
			            	remap.put("tDList", tDList);
			            if(tSList!=null && tSList.size()>0)
			            	remap.put("tSList", tSList);
			            if(tList!=null && tList.size()>0)
			            	remap.put("tList", tList);
			            if(fDList!=null && fDList.size()>0)
			            	remap.put("fDList", fDList);
			            if(fSList!=null && fSList.size()>0)
			            	remap.put("fSList", fSList);
			            if(fList!=null && fList.size()>0)
			            	remap.put("fList", fList);
			            if(dList!=null && dList.size()>0)
			            	remap.put("dList", dList);
			            if(sList!=null && sList.size()>0)
			            	remap.put("sList", sList);
		                if(totalList!=null && totalList.size()>0){
		                	remap.put("totalList", totalList);
		                }
		                
		                relist.add(remap);
					}else{
						//����������ʾ
						for(int i=0;i<departmentarr.length;i++){
							if(i==departmentarr.length-1){
								departmentstr+=departmentarr[i];
							}else{
								departmentstr+=departmentarr[i]+",";
							}
							
							HashMap remap=new HashMap();
							remap.put("departmentName",bd.getName("Company", "comfullname", "comid", departmentarr[i]));
							
							List tDList=this.queryReport1("T", "�����ͬ", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List tSList=this.queryReport1("T", "�豸��ͬ", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List tList=this.queryReport1("T", "", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List fDList=this.queryReport1("F", "�����ͬ", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List fSList=this.queryReport1("F", "�豸��ͬ", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List fList=this.queryReport1("F", "", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List dList=this.queryReport1("", "�����ͬ", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List sList=this.queryReport1("", "�豸��ͬ", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            List totalList=this.queryReport1("", "", departmentarr[i], sdate1, edate1, maxCheck,projectprovince);
				            if(tDList!=null && tDList.size()>0)
				            	remap.put("tDList", tDList);
				            if(tSList!=null && tSList.size()>0)
				            	remap.put("tSList", tSList);
				            if(tList!=null && tList.size()>0)
				            	remap.put("tList", tList);
				            if(fDList!=null && fDList.size()>0)
				            	remap.put("fDList", fDList);
				            if(fSList!=null && fSList.size()>0)
				            	remap.put("fSList", fSList);
				            if(fList!=null && fList.size()>0)
				            	remap.put("fList", fList);
				            if(dList!=null && dList.size()>0)
				            	remap.put("dList", dList);
				            if(sList!=null && sList.size()>0)
				            	remap.put("sList", sList);
			                if(totalList!=null && totalList.size()>0){
			                	remap.put("totalList", totalList);
			                }
			                
			                relist.add(remap);
						}
					}
					request.setAttribute("resultlist",relist);
				}
				HashMap rmap= new HashMap();
				rmap.put("departmentstr", departmentstr);
				rmap.put("sdate1", sdate1);
				rmap.put("edate1", edate1);
				rmap.put("projectprovince", projectprovince);
				rmap.put("ishuizong", ishuizong);
			    request.setAttribute("rmap", rmap);
			}catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					hs.close();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			
		 forward = mapping.findForward("factoryPassRateReportList");		
		}
		return forward;
	}
	
	public HttpServletResponse toExcelRecord(ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String naigation = new String();
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		String[] departmentarr = request.getParameterValues("department");
		if(departmentarr==null || departmentarr.length==0){
			String departmentstr=request.getParameter("departmentstr");
			departmentarr=departmentstr.split(",");
		}
		String sdate1=request.getParameter("sdate1");
		String edate1=request.getParameter("edate1");
		String projectprovince=request.getParameter("projectprovince");
		String ishuizong=request.getParameter("ishuizong");//�Ƿ��������

		Session hs = null;
		ResultSet rs=null;
		XSSFWorkbook wb = new XSSFWorkbook();
		int maxCheck=0;
		String departmentName="";

		try {
			hs = HibernateUtil.getSession();
			
			String sql="select isnull(max(checkNum),0) from ElevatorTransferCaseRegister";
			List list1=hs.createSQLQuery(sql).list();
			if(list1!=null && list1.size()>0){
				maxCheck=(Integer) list1.get(0);
			}
			
			XSSFSheet sheet = wb.createSheet("����ͨ���ʱ���");
			Locale locale = this.getLocale(request);
			MessageResources messages = getResources(request);
			
			//������Ԫ����ʽ
	        XSSFCellStyle cs = wb.createCellStyle();
	        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
	        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
	        XSSFFont f  = wb.createFont();
	        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
	        cs.setFont(f);
				
			XSSFRow row00 = sheet.createRow(0);
			XSSFCell cell00 = row00.createCell((short)0);
			cell00.setCellValue("[�ύ��������:"+sdate1+" �� "+edate1+"����Ŀʡ��:"+projectprovince+"]");

			if(maxCheck>0){
				int rownum=2;
				if(ishuizong!=null && ishuizong.trim().equals("Y")){
					//����
					String departmentstr2="";
					for(int i=0;i<departmentarr.length;i++){
						if(i==departmentarr.length-1){
							departmentName+=bd.getName("Company", "comfullname", "comid", departmentarr[i]);
							departmentstr2+=departmentarr[i];
						}else{
							departmentName+=bd.getName("Company", "comfullname", "comid", departmentarr[i])+",";
							departmentstr2+=departmentarr[i]+"','";
						}
					}
		            List tDList=this.queryReport("T", "�����ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List tSList=this.queryReport("T", "�豸��ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List tList=this.queryReport("T", "", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List fDList=this.queryReport("F", "�����ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List fSList=this.queryReport("F", "�豸��ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List fList=this.queryReport("F", "", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List dList=this.queryReport("", "�����ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List sList=this.queryReport("", "�豸��ͬ", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
		            List tatolList=this.queryReport("", "", departmentstr2, sdate1, edate1, maxCheck,projectprovince);
					
		            //��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
					//sheet.addMergedRegion(new CellRangeAddress(2, 13, 0, 0));//��һ�кϲ���
					//sheet.addMergedRegion(new CellRangeAddress(2, 5, 1, 1));//�ڶ��кϲ���
					//sheet.addMergedRegion(new CellRangeAddress(6, 9, 1, 1));//�ڶ��кϲ���
					//sheet.addMergedRegion(new CellRangeAddress(10, 13, 1, 1));//�ڶ��кϲ���
		            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum+11, 0, 0));//��һ�кϲ���
					sheet.addMergedRegion(new CellRangeAddress(rownum, rownum+3, 1, 1));//�ڶ��кϲ���
					sheet.addMergedRegion(new CellRangeAddress(rownum+4, rownum+7, 1, 1));//�ڶ��кϲ���
					sheet.addMergedRegion(new CellRangeAddress(rownum+8, rownum+11, 1, 1));//�ڶ��кϲ���
					
					if (tatolList != null && !tatolList.isEmpty()) {
						int l = tatolList.size();
						
						XSSFRow row0 = sheet.createRow(rownum);
						XSSFCell cell0 = row0.createCell((short)0);
						
						//���õ�Ԫ����
						//XSSFCellStyle cellStyle=wb.createCellStyle();       
						//cellStyle.setWrapText(true);       
						//cell0.setCellStyle(cellStyle);
						cell0.setCellValue(departmentName);
						
						cell0 = row0.createCell((short)1);
						cell0.setCellValue("ֱ��");
						
						cell0 = row0.createCell((short)2);
						cell0.setCellValue("��ͬ����");
						cell0.setCellStyle(cs);
						
						cell0 = row0.createCell((short)3);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell0.setCellValue("������Ŀ��");
						cell0.setCellStyle(cs);
						
						cell0 = row0.createCell((short)4);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell0.setCellValue("������̨��");
						cell0.setCellStyle(cs);
						int index=4;
						for(int k=0;k<maxCheck;k++){
							if(k==0){
								cell0 = row0.createCell((short)index+1);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue("����̨��");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+2);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue("����ϸ���");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue("���첻�ϸ���");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue("��ĺϸ���");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+5);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue("�����ܺϸ���");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+6);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue("����ϸ���");
								cell0.setCellStyle(cs);

								index+=6;
							}else{
		//						//System.out.println(bd.numToChinese(k+1));
								String num=bd.numToChinese(k+1);
								num=num.replaceAll("��", "");
								cell0 = row0.createCell((short)index+1);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue(num+"��̨��");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+2);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue(num+"��ϸ���");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue(num+"�첻�ϸ���");
								cell0.setCellStyle(cs);
								
								cell0 = row0.createCell((short)index+4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell0.setCellValue(num+"��ϸ���");
								cell0.setCellStyle(cs);
								
								index+=4;
							}
						}
						rownum++;
						
						XSSFRow row1 = sheet.createRow(rownum);
						XSSFCell cell1 =null;
						
						cell1 = row1.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell1.setCellValue("�����ͬ");
						if(tDList!=null && tDList.size()>0){
							for(int i=0;i<tDList.size();i++){
								HashMap map=(HashMap) tDList.get(i);
								
								cell1 = row1.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell1.setCellValue((Integer)map.get("salesContractNo"));
								
								cell1 = row1.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell1.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									
									if(j==0){
										cell1 = row1.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell1 = row1.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell1 = row1.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell1 = row1.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell1 = row1.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(map.get("rate"+j).toString());
										
										cell1 = row1.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell1 = row1.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell1 = row1.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell1 = row1.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell1 = row1.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell1 = row1.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell1.setCellValue(0);
							
							cell1 = row1.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell1.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell1 = row1.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue("0%");
									
									cell1 = row1.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue("0%");
									
									ind+=6;
								}else{
									cell1 = row1.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue(0);
									
									cell1 = row1.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue("0%");
									
									ind+=4;
								}
							}
						
						}
						rownum++;
						
						XSSFRow row2 = sheet.createRow(rownum);
						XSSFCell cell2 =null;
						
						cell2 = row2.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell2.setCellValue("�豸��ͬ");
						if(tSList!=null && tSList.size()>0){
							for(int i=0;i<tSList.size();i++){
								HashMap map=(HashMap) tSList.get(i);
								
								cell2 = row2.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell2.setCellValue((Integer)map.get("salesContractNo"));
								
								cell2 = row2.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell2.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell2 = row2.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell2 = row2.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell2 = row2.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell2 = row2.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell2 = row2.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(map.get("rate"+j).toString());
										
										cell2 = row2.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell2 = row2.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell2 = row2.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell2 = row2.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell2 = row2.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell2 = row2.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell2.setCellValue(0);
							
							cell2 = row2.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell2.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell2 = row2.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue("0%");
									
									cell2 = row2.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue("0%");
									
									ind+=6;
								}else{
									cell2 = row2.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue(0);
									
									cell2 = row2.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue("0%");
									
									ind+=4;
								}
								
							}
						}
						rownum++;
						
						XSSFRow row3 = sheet.createRow(rownum);
						XSSFCell cell3 =null;
						
						cell3 = row3.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell3.setCellValue("�ϼ�");
						if(tList!=null && tList.size()>0){
							for(int i=0;i<tList.size();i++){
								HashMap map=(HashMap) tList.get(i);
								
								cell3 = row3.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell3.setCellValue((Integer)map.get("salesContractNo"));
								
								cell3 = row3.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell3.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell3 = row3.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell3 = row3.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell3 = row3.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell3 = row3.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell3 = row3.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(map.get("rate"+j).toString());
										
										cell3 = row3.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell3 = row3.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell3 = row3.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell3 = row3.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell3 = row3.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell3 = row3.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell3.setCellValue(0);
							
							cell3 = row3.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell3.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell3 = row3.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue("0%");
									
									cell3 = row3.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue("0%");
									
									ind+=6;
								}else{
									cell3 = row3.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue(0);
									
									cell3 = row3.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue("0%");
									
									ind+=4;
								}
							}
						}
						rownum++;
						
						XSSFRow row4 = sheet.createRow(rownum);
						XSSFCell cell4 = null;
						
						cell4 = row4.createCell((short)1);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						//cell4.setCellType(4);
						cell4.setCellValue("����");
						
						cell4 = row4.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell4.setCellValue("��ͬ����");
						cell4.setCellStyle(cs);
						
						cell4 = row4.createCell((short)3);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell4.setCellValue("������Ŀ��");
						cell4.setCellStyle(cs);
						
						cell4 = row4.createCell((short)4);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell4.setCellValue("������̨��");
						cell4.setCellStyle(cs);
						index=4;
						for(int k=0;k<maxCheck;k++){
							if(k==0){
								cell4 = row4.createCell((short)index+1);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue("����̨��");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+2);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue("����ϸ���");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue("���첻�ϸ���");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue("��ĺϸ���");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+5);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue("�����ܺϸ���");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+6);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue("����ϸ���");
								cell4.setCellStyle(cs);

								index+=6;								
							}else{
		//						//System.out.println(bd.numToChinese(k+1));
								String num=bd.numToChinese(k+1);
								num=num.substring(0,1);
								cell4 = row4.createCell((short)index+1);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue(num+"��̨��");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+2);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue(num+"��ϸ���");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue(num+"�첻�ϸ���");
								cell4.setCellStyle(cs);
								
								cell4 = row4.createCell((short)index+4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell4.setCellValue(num+"��ϸ���");
								cell4.setCellStyle(cs);
								
								index+=4;
							}
						}
						rownum++;
						
						XSSFRow row5 = sheet.createRow(rownum);
						XSSFCell cell5 =null;
						
						cell5 = row5.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell5.setCellValue("�����ͬ");
						if(fDList!=null && fDList.size()>0){
							for(int i=0;i<fDList.size();i++){
								HashMap map=(HashMap) fDList.get(i);
								
								cell5 = row5.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell5.setCellValue((Integer)map.get("salesContractNo"));
								
								cell5 = row5.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell5.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell5 = row5.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell5 = row5.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell5 = row5.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell5 = row5.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell5 = row5.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(map.get("rate"+j).toString());
										
										cell5 = row5.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell5 = row5.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell5 = row5.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell5 = row5.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell5 = row5.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell5 = row5.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell5.setCellValue(0);
							
							cell5 = row5.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell5.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell5 = row5.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue("0%");
									
									cell5 = row5.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue("0%");
									
									ind+=6;
								}else{
									cell5 = row5.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue(0);
									
									cell5 = row5.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue("0%");
									
									ind+=4;
								}
							}
						
						}
						rownum++;
						
						XSSFRow row6 = sheet.createRow(rownum);
						XSSFCell cell6 =null;
						
						cell6 = row6.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell6.setCellValue("�豸��ͬ");
						if(fSList!=null && fSList.size()>0){
							for(int i=0;i<fSList.size();i++){
								HashMap map=(HashMap) fSList.get(i);
								
								cell6 = row6.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell6.setCellValue((Integer)map.get("salesContractNo"));
								
								cell6 = row6.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell6.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell6 = row6.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell6 = row6.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell6 = row6.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell6 = row6.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell6 = row6.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(map.get("rate"+j).toString());
										
										cell6 = row6.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell6 = row6.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell6 = row6.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell6 = row6.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell6 = row6.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell6 = row6.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell6.setCellValue(0);
							
							cell6 = row6.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell6.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell6 = row6.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue("0%");
									
									cell6 = row6.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue("0%");
									
									ind+=6;
								}else{
									cell6 = row6.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue(0);
									
									cell6 = row6.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue("0%");
									
									ind+=4;
								}
							}
						}
						rownum++;
						
						XSSFRow row7 = sheet.createRow(rownum);
						XSSFCell cell7 =null;
						
						cell7 = row7.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell7.setCellValue("�ϼ�");
						if(fList!=null && fList.size()>0){
							for(int i=0;i<fList.size();i++){
								HashMap map=(HashMap) fList.get(i);
								
								cell7 = row7.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell7.setCellValue((Integer)map.get("salesContractNo"));
								
								cell7 = row7.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell7.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell7 = row7.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell7 = row7.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell7 = row7.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell7 = row7.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell7 = row7.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(map.get("rate"+j).toString());
										
										cell7 = row7.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell7 = row7.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell7 = row7.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell7 = row7.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell7 = row7.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell7 = row7.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell7.setCellValue(0);
							
							cell7 = row7.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell7.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell7 = row7.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue("0%");
									
									cell7 = row7.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue("0%");
									
									ind+=6;
								}else{
									cell7 = row7.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue(0);
									
									cell7 = row7.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue("0%");
									
									ind+=4;
								}
							}
						}
						rownum++;
						
						XSSFRow row8 = sheet.createRow(rownum);
						XSSFCell cell8 = null;
						
						cell8 = row8.createCell((short)1);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						//cell8.setCellType(4);
						cell8.setCellValue("�ϼ�");
						
						cell8 = row8.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell8.setCellValue("��ͬ����");
						cell8.setCellStyle(cs);
						
						cell8 = row8.createCell((short)3);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell8.setCellValue("������Ŀ��");
						cell8.setCellStyle(cs);
						
						cell8 = row8.createCell((short)4);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell8.setCellValue("������̨��");
						cell8.setCellStyle(cs);
						
						index=4;
						for(int k=0;k<maxCheck;k++){
							if(k==0){
								cell8 = row8.createCell((short)index+1);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue("����̨��");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+2);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue("����ϸ���");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue("���첻�ϸ���");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue("��ĺϸ���");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+5);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue("�����ܺϸ���");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+6);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue("����ϸ���");
								cell8.setCellStyle(cs);

								index+=6;								
							}else{
		//						//System.out.println(bd.numToChinese(k+1));
								String num=bd.numToChinese(k+1);
								num=num.substring(0,1);
								cell8 = row8.createCell((short)index+1);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue(num+"��̨��");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+2);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue(num+"��ϸ���");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue(num+"�첻�ϸ���");
								cell8.setCellStyle(cs);
								
								cell8 = row8.createCell((short)index+4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell8.setCellValue(num+"��ϸ���");
								cell8.setCellStyle(cs);
								
								index+=4;
							}
						}
						rownum++;
						
						XSSFRow row9 = sheet.createRow(rownum);
						XSSFCell cell9 =null;
						
						cell9 = row9.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell9.setCellValue("�����ͬ");
						if(dList!=null && dList.size()>0){
							for(int i=0;i<dList.size();i++){
								HashMap map=(HashMap) dList.get(i);
								
								cell9 = row9.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell9.setCellValue((Integer)map.get("salesContractNo"));
								
								cell9 = row9.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell9.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell9 = row9.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell9 = row9.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell9 = row9.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell9 = row9.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell9 = row9.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(map.get("rate"+j).toString());
										
										cell9 = row9.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell9 = row9.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell9 = row9.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell9 = row9.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell9 = row9.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell9 = row9.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell9.setCellValue(0);
							
							cell9 = row9.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell9.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell9 = row9.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue("0%");
									
									cell9 = row9.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue("0%");
									
									ind+=6;
								}else{
									cell9 = row9.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue(0);
									
									cell9 = row9.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue("0%");
									
									ind+=4;
								}
							}
						}
						rownum++;
						
						XSSFRow row10 = sheet.createRow(rownum);
						XSSFCell cell10 =null;
						
						cell10 = row10.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell10.setCellValue("�豸��ͬ");
						if(sList!=null && sList.size()>0){
							for(int i=0;i<sList.size();i++){
								HashMap map=(HashMap) sList.get(i);
								
								cell10 = row10.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell10.setCellValue((Integer)map.get("salesContractNo"));
								
								cell10 = row10.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell10.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell10 = row10.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell10 = row10.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell10 = row10.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell10 = row10.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell10 = row10.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(map.get("rate"+j).toString());
										
										cell10 = row10.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell10 = row10.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell10 = row10.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell10 = row10.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell10 = row10.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell10 = row10.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell10.setCellValue(0);
							
							cell10 = row10.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell10.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell10 = row10.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue("0%");
									
									cell10 = row10.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue("0%");
									
									ind+=6;
								}else{
									cell10 = row10.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue(0);
									
									cell10 = row10.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue("0%");
									
									ind+=4;
								}
							}
						}
						rownum++;
						
						XSSFRow row11 = sheet.createRow(rownum);
						XSSFCell cell11 =null;
						
						cell11 = row11.createCell((short)2);
						//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell11.setCellValue("�ܼ�");
						if(tatolList!=null && tatolList.size()>0){
							for(int i=0;i<tatolList.size();i++){
								HashMap map=(HashMap) tatolList.get(i);
								
								cell11 = row11.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell11.setCellValue((Integer)map.get("salesContractNo"));
								
								cell11 = row11.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell11.setCellValue((Integer)map.get("elevator"));
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell11 = row11.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell11 = row11.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell11 = row11.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell11 = row11.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
										
										cell11 = row11.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(map.get("rate"+j).toString());
										
										cell11 = row11.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(map.get("ratejg"+j).toString());
										
										ind+=6;
									}else{
										cell11 = row11.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
										
										cell11 = row11.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
										
										cell11 = row11.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
										
										cell11 = row11.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(map.get("rate"+j).toString());
										
										ind+=4;
									}
								}
							}
						}else{
							cell11 = row11.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell11.setCellValue(0);
							
							cell11 = row11.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell11.setCellValue(0);
							int ind=4;
							for(int j=0;j<maxCheck;j++){
								if(j==0){
									cell11 = row11.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue("0%");
									
									cell11 = row11.createCell((short)ind+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue("0%");
									
									ind+=6;
								}else{
									cell11 = row11.createCell((short)ind+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue(0);
									
									cell11 = row11.createCell((short)ind+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue("0%");
									
									ind+=4;
								}
							}
						}
						rownum++;
					}
				}else{
					
					for(int d=0;d<departmentarr.length;d++){
			            List tDList=this.queryReport("T", "�����ͬ", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List tSList=this.queryReport("T", "�豸��ͬ", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List tList=this.queryReport("T", "", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List fDList=this.queryReport("F", "�����ͬ", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List fSList=this.queryReport("F", "�豸��ͬ", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List fList=this.queryReport("F", "", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List dList=this.queryReport("", "�����ͬ", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List sList=this.queryReport("", "�豸��ͬ", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
			            List tatolList=this.queryReport("", "", departmentarr[d], sdate1, edate1, maxCheck,projectprovince);
						
			            //��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
						//sheet.addMergedRegion(new CellRangeAddress(2, 13, 0, 0));//��һ�кϲ���
						//sheet.addMergedRegion(new CellRangeAddress(2, 5, 1, 1));//�ڶ��кϲ���
						//sheet.addMergedRegion(new CellRangeAddress(6, 9, 1, 1));//�ڶ��кϲ���
						//sheet.addMergedRegion(new CellRangeAddress(10, 13, 1, 1));//�ڶ��кϲ���
			            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum+11, 0, 0));//��һ�кϲ���
						sheet.addMergedRegion(new CellRangeAddress(rownum, rownum+3, 1, 1));//�ڶ��кϲ���
						sheet.addMergedRegion(new CellRangeAddress(rownum+4, rownum+7, 1, 1));//�ڶ��кϲ���
						sheet.addMergedRegion(new CellRangeAddress(rownum+8, rownum+11, 1, 1));//�ڶ��кϲ���
						
						departmentName= bd.getName("Company", "comfullname", "comid", departmentarr[d]);
						if (tatolList != null && !tatolList.isEmpty()) {
							int l = tatolList.size();
							
							XSSFRow row0 = sheet.createRow(rownum);
							XSSFCell cell0 = row0.createCell((short)0);
	
							cell0.setCellValue(departmentName);
							
							cell0 = row0.createCell((short)1);
							cell0.setCellValue("ֱ��");
							
							cell0 = row0.createCell((short)2);
							cell0.setCellValue("��ͬ����");
							cell0.setCellStyle(cs);
							
							cell0 = row0.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell0.setCellValue("������Ŀ��");
							cell0.setCellStyle(cs);
							
							cell0 = row0.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell0.setCellValue("������̨��");
							cell0.setCellStyle(cs);
							int index=4;
							for(int k=0;k<maxCheck;k++){
								if(k==0){
									cell0 = row0.createCell((short)index+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue("����̨��");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue("����ϸ���");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue("���첻�ϸ���");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue("��ĺϸ���");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue("�����ܺϸ���");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue("����ϸ���");
									cell0.setCellStyle(cs);

									index+=6;
								}else{
			//						//System.out.println(bd.numToChinese(k+1));
									String num=bd.numToChinese(k+1);
									num=num.substring(0,1);
									cell0 = row0.createCell((short)index+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue(num+"��̨��");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue(num+"��ϸ���");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue(num+"�첻�ϸ���");
									cell0.setCellStyle(cs);
									
									cell0 = row0.createCell((short)index+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell0.setCellValue(num+"��ϸ���");
									cell0.setCellStyle(cs);

									index+=4;
								}
							}
							rownum++;
							
							XSSFRow row1 = sheet.createRow(rownum);
							XSSFCell cell1 =null;
							
							cell1 = row1.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell1.setCellValue("�����ͬ");
							if(tDList!=null && tDList.size()>0){
								for(int i=0;i<tDList.size();i++){
									HashMap map=(HashMap) tDList.get(i);
									
									cell1 = row1.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue((Integer)map.get("salesContractNo"));
									
									cell1 = row1.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell1.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell1 = row1.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell1 = row1.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell1 = row1.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell1 = row1.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell1 = row1.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(map.get("rate"+j).toString());
											
											cell1 = row1.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell1 = row1.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell1 = row1.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell1 = row1.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell1 = row1.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell1.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell1 = row1.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell1.setCellValue(0);
								
								cell1 = row1.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell1.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell1 = row1.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue("0%");
										
										cell1 = row1.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue("0%");
										
										ind+=6;
									}else{
										cell1 = row1.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue(0);
										
										cell1 = row1.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell1.setCellValue("0%");
										
										ind+=4;
									}
								}
							
							}
							rownum++;
							
							XSSFRow row2 = sheet.createRow(rownum);
							XSSFCell cell2 =null;
							
							cell2 = row2.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell2.setCellValue("�豸��ͬ");
							if(tSList!=null && tSList.size()>0){
								for(int i=0;i<tSList.size();i++){
									HashMap map=(HashMap) tSList.get(i);
									
									cell2 = row2.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue((Integer)map.get("salesContractNo"));
									
									cell2 = row2.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell2.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell2 = row2.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell2 = row2.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell2 = row2.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell2 = row2.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell2 = row2.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(map.get("rate"+j).toString());
											
											cell2 = row2.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell2 = row2.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell2 = row2.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell2 = row2.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell2 = row2.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell2.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell2 = row2.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell2.setCellValue(0);
								
								cell2 = row2.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell2.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell2 = row2.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue("0%");
										
										cell2 = row2.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue("0%");
										
										ind+=6;
									}else{
										cell2 = row2.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue(0);
										
										cell2 = row2.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell2.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
							
							XSSFRow row3 = sheet.createRow(rownum);
							XSSFCell cell3 =null;
							
							cell3 = row3.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell3.setCellValue("�ϼ�");
							if(tList!=null && tList.size()>0){
								for(int i=0;i<tList.size();i++){
									HashMap map=(HashMap) tList.get(i);
									
									cell3 = row3.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue((Integer)map.get("salesContractNo"));
									
									cell3 = row3.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell3.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell3 = row3.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell3 = row3.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell3 = row3.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell3 = row3.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell3 = row3.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(map.get("rate"+j).toString());
											
											cell3 = row3.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell3 = row3.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell3 = row3.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell3 = row3.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell3 = row3.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell3.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell3 = row3.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell3.setCellValue(0);
								
								cell3 = row3.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell3.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell3 = row3.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue("0%");
										
										cell3 = row3.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue("0%");
										
										ind+=6;
									}else{
										cell3 = row3.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue(0);
										
										cell3 = row3.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell3.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
							
							XSSFRow row4 = sheet.createRow(rownum);
							XSSFCell cell4 = null;
							
							cell4 = row4.createCell((short)1);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							//cell4.setCellType(4);
							cell4.setCellValue("����");
							
							cell4 = row4.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell4.setCellValue("��ͬ����");
							cell4.setCellStyle(cs);
							
							cell4 = row4.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell4.setCellValue("������Ŀ��");
							cell4.setCellStyle(cs);
							
							cell4 = row4.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell4.setCellValue("������̨��");
							cell4.setCellStyle(cs);
							index=4;
							for(int k=0;k<maxCheck;k++){
								if(k==0){
									cell4 = row4.createCell((short)index+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue("����̨��");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue("����ϸ���");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue("���첻�ϸ���");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue("��ĺϸ���");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue("�����ܺϸ���");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue("����ϸ���");
									cell4.setCellStyle(cs);

									index+=6;
								}else{
			//						//System.out.println(bd.numToChinese(k+1));
									String num=bd.numToChinese(k+1);
									num=num.substring(0,1);
									cell4 = row4.createCell((short)index+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue(num+"��̨��");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue(num+"��ϸ���");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue(num+"�첻�ϸ���");
									cell4.setCellStyle(cs);
									
									cell4 = row4.createCell((short)index+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell4.setCellValue(num+"��ϸ���");
									cell4.setCellStyle(cs);

									index+=4;
								}
							}
							rownum++;
							
							XSSFRow row5 = sheet.createRow(rownum);
							XSSFCell cell5 =null;
							
							cell5 = row5.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell5.setCellValue("�����ͬ");
							if(fDList!=null && fDList.size()>0){
								for(int i=0;i<fDList.size();i++){
									HashMap map=(HashMap) fDList.get(i);
									
									cell5 = row5.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue((Integer)map.get("salesContractNo"));
									
									cell5 = row5.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell5.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell5 = row5.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell5 = row5.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell5 = row5.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell5 = row5.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell5 = row5.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(map.get("rate"+j).toString());
											
											cell5 = row5.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell5 = row5.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell5 = row5.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell5 = row5.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell5 = row5.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell5.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell5 = row5.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell5.setCellValue(0);
								
								cell5 = row5.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell5.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell5 = row5.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue("0%");
										
										cell5 = row5.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue("0%");
										
										ind+=6;
									}else{
										cell5 = row5.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue(0);
										
										cell5 = row5.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell5.setCellValue("0%");
										
										ind+=4;
									}
								}
							
							}
							rownum++;
							
							XSSFRow row6 = sheet.createRow(rownum);
							XSSFCell cell6 =null;
							
							cell6 = row6.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell6.setCellValue("�豸��ͬ");
							if(fSList!=null && fSList.size()>0){
								for(int i=0;i<fSList.size();i++){
									HashMap map=(HashMap) fSList.get(i);
									
									cell6 = row6.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue((Integer)map.get("salesContractNo"));
									
									cell6 = row6.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell6.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell6 = row6.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell6 = row6.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell6 = row6.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell6 = row6.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell6 = row6.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(map.get("rate"+j).toString());
											
											cell6 = row6.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell6 = row6.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell6 = row6.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell6 = row6.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell6 = row6.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell6.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell6 = row6.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell6.setCellValue(0);
								
								cell6 = row6.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell6.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell6 = row6.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue("0%");
										
										cell6 = row6.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue("0%");
										
										ind+=6;
									}else{
										cell6 = row6.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue(0);
										
										cell6 = row6.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell6.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
							
							XSSFRow row7 = sheet.createRow(rownum);
							XSSFCell cell7 =null;
							
							cell7 = row7.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell7.setCellValue("�ϼ�");
							if(fList!=null && fList.size()>0){
								for(int i=0;i<fList.size();i++){
									HashMap map=(HashMap) fList.get(i);
									
									cell7 = row7.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue((Integer)map.get("salesContractNo"));
									
									cell7 = row7.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell7.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell7 = row7.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell7 = row7.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell7 = row7.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell7 = row7.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell7 = row7.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(map.get("rate"+j).toString());
											
											cell7 = row7.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell7 = row7.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell7 = row7.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell7 = row7.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell7 = row7.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell7.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell7 = row7.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell7.setCellValue(0);
								
								cell7 = row7.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell7.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell7 = row7.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue("0%");
										
										cell7 = row7.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue("0%");
										
										ind+=6;
									}else{
										cell7 = row7.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue(0);
										
										cell7 = row7.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell7.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
							
							XSSFRow row8 = sheet.createRow(rownum);
							XSSFCell cell8 = null;
							
							cell8 = row8.createCell((short)1);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							//cell8.setCellType(4);
							cell8.setCellValue("�ϼ�");
							
							cell8 = row8.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell8.setCellValue("��ͬ����");
							cell8.setCellStyle(cs);
							
							cell8 = row8.createCell((short)3);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell8.setCellValue("������Ŀ��");
							cell8.setCellStyle(cs);
							
							cell8 = row8.createCell((short)4);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell8.setCellValue("������̨��");
							cell8.setCellStyle(cs);
							
							index=4;
							for(int k=0;k<maxCheck;k++){
								if(k==0){
									cell8 = row8.createCell((short)index+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue("����̨��");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue("����ϸ���");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue("���첻�ϸ���");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue("��ĺϸ���");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+5);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue("�����ܺϸ���");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+6);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue("����ϸ���");
									cell8.setCellStyle(cs);

									index+=6;
								}else{
			//						//System.out.println(bd.numToChinese(k+1));
									String num=bd.numToChinese(k+1);
									num=num.substring(0,1);
									cell8 = row8.createCell((short)index+1);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue(num+"��̨��");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+2);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue(num+"��ϸ���");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue(num+"�첻�ϸ���");
									cell8.setCellStyle(cs);
									
									cell8 = row8.createCell((short)index+4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell8.setCellValue(num+"��ϸ���");
									cell8.setCellStyle(cs);

									index+=4;
								}
							}
							rownum++;
							
							XSSFRow row9 = sheet.createRow(rownum);
							XSSFCell cell9 =null;
							
							cell9 = row9.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell9.setCellValue("�����ͬ");
							if(dList!=null && dList.size()>0){
								for(int i=0;i<dList.size();i++){
									HashMap map=(HashMap) dList.get(i);
									
									cell9 = row9.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue((Integer)map.get("salesContractNo"));
									
									cell9 = row9.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell9.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell9 = row9.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell9 = row9.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell9 = row9.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell9 = row9.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell9 = row9.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(map.get("rate"+j).toString());
											
											cell9 = row9.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell9 = row9.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell9 = row9.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell9 = row9.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell9 = row9.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell9.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell9 = row9.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell9.setCellValue(0);
								
								cell9 = row9.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell9.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell9 = row9.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue("0%");
										
										cell9 = row9.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue("0%");
										
										ind+=6;
									}else{
										cell9 = row9.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue(0);
										
										cell9 = row9.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell9.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
							
							XSSFRow row10 = sheet.createRow(rownum);
							XSSFCell cell10 =null;
							
							cell10 = row10.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell10.setCellValue("�豸��ͬ");
							if(sList!=null && sList.size()>0){
								for(int i=0;i<sList.size();i++){
									HashMap map=(HashMap) sList.get(i);
									
									cell10 = row10.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue((Integer)map.get("salesContractNo"));
									
									cell10 = row10.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell10.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell10 = row10.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell10 = row10.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell10 = row10.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell10 = row10.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell10 = row10.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(map.get("rate"+j).toString());
											
											cell10 = row10.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell10 = row10.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell10 = row10.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell10 = row10.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell10 = row10.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell10.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell10 = row10.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell10.setCellValue(0);
								
								cell10 = row10.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell10.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell10 = row10.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue("0%");
										
										cell10 = row10.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue("0%");
										
										ind+=6;
									}else{
										cell10 = row10.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue(0);
										
										cell10 = row10.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell10.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
							
							XSSFRow row11 = sheet.createRow(rownum);
							XSSFCell cell11 =null;
							
							cell11 = row11.createCell((short)2);
							//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell11.setCellValue("�ܼ�");
							if(tatolList!=null && tatolList.size()>0){
								for(int i=0;i<tatolList.size();i++){
									HashMap map=(HashMap) tatolList.get(i);
									
									cell11 = row11.createCell((short)3);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue((Integer)map.get("salesContractNo"));
									
									cell11 = row11.createCell((short)4);
									//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
									cell11.setCellValue((Integer)map.get("elevator"));
									int ind=4;
									for(int j=0;j<maxCheck;j++){
										if(j==0){
											cell11 = row11.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell11 = row11.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell11 = row11.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell11 = row11.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("nojgfactory"+j).toString()));
											
											cell11 = row11.createCell((short)ind+5);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(map.get("rate"+j).toString());
											
											cell11 = row11.createCell((short)ind+6);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(map.get("ratejg"+j).toString());
											
											ind+=6;
										}else{
											cell11 = row11.createCell((short)ind+1);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("factory"+j).toString()));
											
											cell11 = row11.createCell((short)ind+2);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("nofactory"+j).toString()));
											
											cell11 = row11.createCell((short)ind+3);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(Integer.valueOf(map.get("unqualified"+j).toString()));
											
											cell11 = row11.createCell((short)ind+4);
											//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
											cell11.setCellValue(map.get("rate"+j).toString());
											
											ind+=4;
										}
									}
								}
							}else{
								cell11 = row11.createCell((short)3);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell11.setCellValue(0);
								
								cell11 = row11.createCell((short)4);
								//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
								cell11.setCellValue(0);
								int ind=4;
								for(int j=0;j<maxCheck;j++){
									if(j==0){
										cell11 = row11.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+5);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue("0%");
										
										cell11 = row11.createCell((short)ind+6);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue("0%");
										
										ind+=6;
									}else{
										cell11 = row11.createCell((short)ind+1);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+2);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+3);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue(0);
										
										cell11 = row11.createCell((short)ind+4);
										//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
										cell11.setCellValue("0%");
										
										ind+=4;
									}
								}
							}
							rownum++;
						}
					}
				}
			}else{
				XSSFRow row01 = sheet.createRow(2);
				XSSFCell cell01 = row01.createCell((short)0);
				cell01.setCellValue("��ѯû�м�¼��");
			}
			
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("����ͨ���ʱ���", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	
	public List queryReport(String elevatorType,String salesContractType,
			String department,String sdate1,String edate1,int maxCheck,String projectprovince){
		Session hs=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		List reportList=new ArrayList();
		try {
			hs=HibernateUtil.getSession();
			conn=hs.connection();
			//��������� ���첿����˹����������ύ����װ������˵ġ�20180702��Ϊ����Ա�ύ
			String sql="select COUNT(distinct a.salescontractno) salesContractNo,COUNT(a.elevatorNo) elevator";
			if(elevatorType!=null && !"".equals(elevatorType)){
				sql+=",a.elevatorType elevatorType";
			}
			if(salesContractType!=null && !"".equals(salesContractType)){
				sql+=",a.salesContractType salesContractType";
			}
			for(int i=0;i<maxCheck;i++){
				sql+=",COUNT(case a.CheckNum when "+(i+1)+" then a.elevatorNo end) as factory"+(i+1)
					+",isnull(COUNT(case when a.CheckNum="+(i+1)+" and a.factoryCheckResult='�ϸ�' then a.elevatorNo end),0) as nofactory"+(i+1)
					+",isnull(COUNT(case when a.CheckNum="+(i+1)+" and isnull(a.FactoryCheckResult2,a.factoryCheckResult) in('����Ա��ĺϸ�','��ĺϸ�') then a.elevatorNo end),0) as nojgfactory"+(i+1)
					+",isnull(COUNT(case when a.CheckNum="+(i+1)+" and a.factoryCheckResult='���ϸ�' then a.elevatorNo end),0) as unqualified"+(i+1);
			}
			sql+=" from ElevatorTransferCaseRegister a where isnull(SubmitType,'N')='Y' and isnull(ProcessStatus,'0') in('2','3') ";
					//"(a.Status=1 or a.billno=(select distinct b.billno from ElevatorTransferCaseProcess b " +
					//"where b.billno=a.billno and b.TaskName='���첿�����' and b.ApproveResult='�ύ��װ�������')) ";
			
			if(elevatorType!=null && !"".equals(elevatorType)){
				sql+=" and a.elevatorType='"+elevatorType.trim()+"'";
			}
			if(salesContractType!=null && !"".equals(salesContractType)){
				sql+=" and a.salesContractType='"+salesContractType.trim()+"'";
			}else{
				sql+=" and a.salesContractType in ('�����ͬ','�豸��ͬ')";
			}
			if(department!=null && !"".equals(department)){
				sql+=" and a.department in ('"+department.trim()+"')";
			}
			if(sdate1!=null && !"".equals(sdate1)){
				sql+=" and checkTime >='"+sdate1.trim()+" 00:00:00'";
			}
			if(edate1!=null && !"".equals(edate1)){
				sql+=" and checkTime <='"+edate1.trim()+" 99:99:99'";
			}
			if(projectprovince!=null && !"".equals(projectprovince.trim())){
				sql+=" and a.projectprovince like '%"+projectprovince.trim()+"%'";
			}
			
			if(!elevatorType.equals("") && !salesContractType.equals("")){
				sql+=" group by a.elevatorType,a.salesContractType ";
			}else if(!elevatorType.equals("")){
				sql+=" group by a.elevatorType ";
			}else if(!salesContractType.equals("")){
				sql+=" group by a.salesContractType ";
			}
			
			//System.out.println("excel>>>"+sql);
			
			ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();

            while(rs.next()){
              	HashMap map= new HashMap();
              	if(!elevatorType.equals("")){
              		map.put("elevatorType", rs.getString("elevatorType"));
              	}
              	if(!salesContractType.equals("")){
              		map.put("salesContractType",  rs.getString("salesContractType"));
              	}
              	map.put("salesContractNo", rs.getInt("salesContractNo"));
              	map.put("elevator", rs.getInt("elevator"));
              	for(int j=0;j<maxCheck;j++){
              		int factory=rs.getInt("factory"+(j+1));//��̨��
              		int nofactory=rs.getInt("nofactory"+(j+1));//�ϸ�̨��
          			int nojgfactory=rs.getInt("nojgfactory"+(j+1));//��ĺϸ�̨��
          			
              		map.put("factory"+j, factory);//��̨��
              		if(j==0){
              			map.put("nofactory"+j, nofactory-nojgfactory);//���ǰ�ϸ�̨��=����ϸ�̨��-�����ĺϸ�̨��
              		}else{
              			map.put("nofactory"+j, nofactory);//�ϸ�̨��
              		}
              		map.put("unqualified"+j, rs.getInt("unqualified"+(j+1)));//���ϸ���
              		map.put("nojgfactory"+j, nojgfactory);//��ĺϸ�̨��
              		
              		double rate=0;//�ϸ���
              		double rate2=0;//��ĺϸ�ǰ�ϸ���
              		if(rs.getInt("factory"+(j+1))!=0){
              			rate=nofactory/Double.valueOf(factory)*100;
              			rate2=(nofactory-nojgfactory)/Double.valueOf(factory)*100;
              		}else{
              			rate=0;
              			rate2=0;
              		}
              		map.put("rate"+j, rate==0 ? rate+"%" : df.format(rate)+"%");
              		map.put("ratejg"+j, rate2==0 ? rate2+"%" : df.format(rate2)+"%");
              	}
              	reportList.add(map);
              }
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		return reportList;
	}
	
	
	public List queryReport1(String elevatorType,String salesContractType,
			String department,String sdate1,String edate1,int maxCheck,String projectprovince){
		Session hs=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List reportList=new ArrayList();
		try {
			hs=HibernateUtil.getSession();
			conn=hs.connection();
			//��������� ���첿����˹����������ύ����װ������˵ġ�20180702��Ϊ����Ա�ύ
			String sql="select COUNT(distinct a.salescontractno) salesContractNo,COUNT(a.elevatorNo) elevator";
			if(elevatorType!=null && !"".equals(elevatorType)){
				sql+=",a.elevatorType elevatorType";
			}
			if(salesContractType!=null && !"".equals(salesContractType)){
				sql+=",a.salesContractType salesContractType";
			}
			for(int i=0;i<maxCheck;i++){
				sql+=",isnull(COUNT(case a.CheckNum when "+(i+1)+" then a.elevatorNo end),0) as factory"+(i+1)
					+",isnull(COUNT(case when a.CheckNum="+(i+1)+" and a.factoryCheckResult='�ϸ�' then a.elevatorNo end),0) as nofactory"+(i+1)
					+",isnull(COUNT(case when a.CheckNum="+(i+1)+" and isnull(a.FactoryCheckResult2,a.factoryCheckResult) in('����Ա��ĺϸ�','��ĺϸ�') then a.elevatorNo end),0) as nojgfactory"+(i+1)
					+",isnull(COUNT(case when a.CheckNum="+(i+1)+" and a.factoryCheckResult='���ϸ�' then a.elevatorNo end),0) as unqualified"+(i+1);
			}
			sql+=" from ElevatorTransferCaseRegister a where isnull(SubmitType,'N')='Y' and isnull(ProcessStatus,'0') in('2','3')";
					//"(a.Status=1 or a.billno=(select distinct b.billno from ElevatorTransferCaseProcess b " +
					//"where b.billno=a.billno and b.TaskName='���첿�����' and b.ApproveResult='�ύ��װ�������')) ";
			
			if(elevatorType!=null && !"".equals(elevatorType)){
				sql+=" and a.elevatorType='"+elevatorType.trim()+"'";
			}
			if(salesContractType!=null && !"".equals(salesContractType)){
				sql+=" and a.salesContractType ='"+salesContractType.trim()+"'";
			}else{
				sql+=" and a.salesContractType in ('�����ͬ','�豸��ͬ')";
			}
			if(department!=null && !"".equals(department)){
				sql+=" and a.department in ('"+department.trim()+"')";
			}
			if(sdate1!=null && !"".equals(sdate1)){
				sql+=" and checkTime >='"+sdate1.trim()+" 00:00:00'";
			}
			if(edate1!=null && !"".equals(edate1)){
				sql+=" and checkTime <='"+edate1.trim()+" 99:99:99'";
			}
			if(projectprovince!=null && !"".equals(projectprovince.trim())){
				sql+=" and a.projectprovince like '%"+projectprovince.trim()+"%'";
			}
			if(!elevatorType.equals("") && !salesContractType.equals("")){
				sql+=" group by a.elevatorType,a.salesContractType ";
			}else if(!elevatorType.equals("")){
				sql+=" group by a.elevatorType ";
			}else if(!salesContractType.equals("")){
				sql+=" group by a.salesContractType ";
			}
			
			//System.out.println(sql);
			
			ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();

            while(rs.next()){
              	HashMap map= new HashMap();
              	if(!elevatorType.equals("")){
              		map.put("elevatorType", rs.getString("elevatorType"));
              	}
              	if(!salesContractType.equals("")){
              		map.put("salesContractType",  rs.getString("salesContractType"));
              	}
              	map.put("salesContractNo", rs.getInt("salesContractNo"));
              	map.put("elevator", rs.getInt("elevator"));
              	List factoryList=new ArrayList();
              	for(int j=0;j<maxCheck;j++){
              		Map map1=new HashMap();
              		
          			int factory=rs.getInt("factory"+(j+1));//��̨��
              		int nofactory=rs.getInt("nofactory"+(j+1));//�ϸ�̨��
          			int nojgfactory=rs.getInt("nojgfactory"+(j+1));//��ĺϸ�̨��
          			
              		map1.put("factory"+j, factory);//��̨��
              		if(j==0){
              			map1.put("nofactory"+j, nofactory-nojgfactory);//���ǰ�ϸ�̨��=����ϸ�̨��-�����ĺϸ�̨��
              		}else{
              			map1.put("nofactory"+j, nofactory);//�ϸ�̨��
              		}
              		map1.put("unqualified"+j, rs.getInt("unqualified"+(j+1)));//���ϸ���
              		map1.put("nojgfactory"+j, nojgfactory);//��ĺϸ�̨��

              		double rate=0;//�ϸ���
              		double rate2=0;//��ĺϸ�ǰ�ϸ���
              		if(rs.getInt("factory"+(j+1))!=0){
              			rate=nofactory/Double.valueOf(factory)*100;
              			rate2=(nofactory-nojgfactory)/Double.valueOf(factory)*100;
              		}else{
              			rate=0;
              			rate2=0;
              		}
              		map1.put("rate"+j, rate==0 ? rate+"%" : df.format(rate)+"%");
              		map1.put("ratejg"+j, rate2==0 ? rate2+"%" : df.format(rate2)+"%");
              		factoryList.add(map1);
              	}
              	map.put("factoryList", factoryList);
              	reportList.add(map);
              }
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		return reportList;
	}
}
