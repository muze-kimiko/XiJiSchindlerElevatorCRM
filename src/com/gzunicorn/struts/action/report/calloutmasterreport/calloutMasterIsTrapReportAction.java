package com.gzunicorn.struts.action.report.calloutmasterreport;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
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
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.basedata.hotlinefaulttype.HotlineFaultType;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ���߹���ģ��=>���˱���
 * @author Lijun
 *
 */
public class calloutMasterIsTrapReportAction extends DispatchAction {

	Log log = LogFactory.getLog(calloutMasterIsTrapReportAction.class);

	BaseDataImpl bd = new BaseDataImpl();
	DecimalFormat df = new DecimalFormat("##.##"); 
	DecimalFormat df2 = new DecimalFormat("##"); 

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "calloutmasteristrapreport", null);
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
	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("navigator.location", " ���˱��� >> ��ѯ");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		Session hs=null;
		try {
			hs=HibernateUtil.getSession();
			
			//�ֲ��������б�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			request.setAttribute("maintDivisionList", maintDivisionList);
			//ά��վ������
			//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
			String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
			List vmlist=hs.createSQLQuery(sqlss).list();
			if(vmlist!=null && vmlist.size()>0){
				String hql="select a from Storageid a where a.storageid= '"+userInfo.getStorageId()+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";	
				List mainStationList=hs.createQuery(hql).list();
				request.setAttribute("mainStationList", mainStationList);
			}else{
				String hql="select a from Storageid a where a.comid like '"+userInfo.getComID()+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
				List mainStationList=hs.createQuery(hql).list();
			  
				 Storageid storid=new Storageid();
				 storid.setStorageid("%");
				 storid.setStoragename("ȫ��");
				 mainStationList.add(0,storid);
				 
				 request.setAttribute("mainStationList", mainStationList);
			}
			
			String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			String day1=DateUtil.getDate(day, "MM", -1);//��ǰ�����·ݼ�1 ��
			dform.set("sdate1", day1);
			dform.set("edate1", day);
			
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

		forward = mapping.findForward("calloutMasterIsTrapSearch");		
		return forward;
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

		request.setAttribute("navigator.location", " ���˱��� >> ��ѯ���");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String maintdivision = request.getParameter("maintdivision");
		String maintstation = request.getParameter("maintstation");
		String sdate1 = request.getParameter("sdate1");
		if(sdate1==null || sdate1.trim().equals("")){
			sdate1="0000-00-00";
		}
		String edate1 = request.getParameter("edate1");
		if(edate1==null || edate1.trim().equals("")){
			edate1="9999-99-99";
		}

		HashMap rmap= new HashMap();			
		rmap.put("maintdivision", maintdivision);
		rmap.put("maintstation", maintstation);
		rmap.put("sdate1", sdate1);
		rmap.put("edate1", edate1);
		request.setAttribute("rmap", rmap);
		
		Session hs = null;
		ResultSet rs=null;
		try {
			hs = HibernateUtil.getSession();
			
			//��¼�û�
			String sqls="select a from Loginuser a ";
     		List loginlist=hs.createQuery(sqls).list();
			
			//��������
     		String sqls2="select a from HotlineFaultType a ";
      		List hftlist=hs.createQuery(sqls2).list();
			
			String hql="exec sp_IsTrapReport '"+maintdivision.trim()+"','"+maintstation.trim()+"','"+sdate1.trim()+"','"+edate1.trim()+"'";
			System.out.println("���˱���>>>>"+hql);
			rs=hs.connection().prepareCall(hql).executeQuery();

            List reportList=new ArrayList();

            while(rs.next()){
            	HashMap map= new HashMap();
            	map.put("calloutmasterno", rs.getString("calloutmasterno"));
            	map.put("comname", rs.getString("comname"));
            	map.put("storagename", rs.getString("storagename"));
            	map.put("repairtime", rs.getString("repairtime"));
            	map.put("companyname", rs.getString("companyname"));
            	map.put("elevatorno", rs.getString("elevatorno"));
            	map.put("faultstatus", rs.getString("faultstatus"));
            	map.put("faultcode", rs.getString("faultcode"));
            	map.put("hmtidname", rs.getString("hmtidname"));
            	map.put("processdesc", rs.getString("processdesc"));
            	map.put("jjinfo", rs.getString("jjinfo"));
            	map.put("servicerem", rs.getString("servicerem"));
            	
            	map.put("username", rs.getString("userid"));//ά�޲�����Ա
            	/**
            	String userid=rs.getString("userid");
            	String username="";
	            if(userid!=null && !userid.trim().equals("")){
          		 String[] useridarr=userid.split(",");
           		 for(int u=0;u<useridarr.length;u++){
           			 for(int l=0;l<loginlist.size();l++){
           				 Loginuser login=(Loginuser)loginlist.get(l);
           				 String userid2=login.getUserid();
           				 if(useridarr[u].equals(userid2)){
           					username+=login.getUsername()+",";
           					break;
           				 }
           			 }
           		 }
	           	}
            	map.put("username", username);//ά�޲�����Ա
            	*/
            	String hftid=rs.getString("hftid");
            	String hftdesc="";
	           	if(hftid!=null && !hftid.trim().equals("")){
	           		 String[] hftarr=hftid.split(",");
	           		 for(int h=0;h<hftarr.length;h++){
	           			 for(int l=0;l<hftlist.size();l++){
	           				HotlineFaultType hft=(HotlineFaultType)hftlist.get(l);
	           				String hftid2=hft.getHftId();
	           				 if(hftarr[h].equals(hftid2)){
	           					hftdesc+=hft.getHftDesc()+",";
	           					break;
	           				 }
	           			 }
	           		 }
	           	}
            	map.put("hftidname",hftdesc);//������������

            	reportList.add(map);
            }
            
            if (dform.get("genReport") != null && dform.get("genReport").equals("Y")) {
            	//����excel
    			response = toExcelRecord(response,reportList);
    			forward = mapping.findForward("exportExcel");
    		}else{
                if(reportList.size()>0 && reportList.size()<3001){
                	request.setAttribute("isTrapReportList", reportList);
                }else if(reportList.size()>3000){
                	request.setAttribute("showinfostr", "��ѯ���ݳ�����ǧ�м�¼���뵼��EXCEL�鿴��");
                }else{
                	request.setAttribute("showinfostr", "û�м�¼��ʾ��");
                }
                
    			forward = mapping.findForward("calloutMasterIsTrapList");	
    		}
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
				

		return forward;
	}
	
	public HttpServletResponse toExcelRecord(HttpServletResponse response,
			List ReportList) throws IOException {

		String[] titlename={"���ޱ��","ά���ֲ���","ά��վ","����ʱ��","������","���޵�λ����","���ݱ��",
				"����״̬","���ϴ���","��������","��������","ά������","�ɼ���Ϣ","����Ʒ���ͺŻ�������ע"};
		String[] titleid={"calloutmasterno","comname","storagename","repairtime","username","companyname","elevatorno",
				"faultstatus","faultcode","hmtidname","hftidname","processdesc","jjinfo","servicerem"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("���˱���");
        
        //������Ԫ����ʽ
        XSSFCellStyle cs = wb.createCellStyle();
        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
        XSSFFont f  = wb.createFont();
        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
        cs.setFont(f);
        
        //��������
        XSSFRow row0 = sheet.createRow( 0);
        XSSFCell cell0 = null;
		for(int i=0;i<titlename.length;i++){
			cell0 = row0.createCell((short)i);
			//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellValue(titlename[i]);
			cell0.setCellStyle(cs);
		}
		//��������
		if (ReportList != null && ReportList.size()>0) {
			XSSFRow row = null;
			XSSFCell cell =null;
			for (int j = 0; j < ReportList.size(); j++) {
				HashMap map=(HashMap) ReportList.get(j);
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(j+1);
				for(int c=0;c<titleid.length;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				    cell.setCellValue((String)map.get(titleid[c]));
				}
			}
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("���˱���", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	
	/**
	 * ajax ���� �ֲ���ά��վ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void toStorageIDList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session hs=null;
		String comid=request.getParameter("comid");
		response.setHeader("Content-Type","text/html; charset=GBK");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs=HibernateUtil.getSession();
			if(comid!=null && !"".equals(comid)){
				String hql="select a from Storageid a where a.comid='"+comid+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0){
					sb.append("<rows>");
					for(int i=0;i<list.size();i++){
					Storageid sid=(Storageid)list.get(i);
					sb.append("<cols name='"+sid.getStoragename()+"' value='"+sid.getStorageid()+"'>").append("</cols>");
					}
					sb.append("</rows>");
								
					
				  }
			 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		finally{
			hs.close();
		}
		sb.append("</root>");
		
		response.setCharacterEncoding("gbk"); 
		response.setContentType("text/xml;charset=gbk");
		response.getWriter().write(sb.toString());
	}
}
