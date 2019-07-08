package com.gzunicorn.struts.action.MobileOfficing.accessoriesrequisition;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplanfileinfo.MaintenanceWorkPlanFileinfo;
import com.gzunicorn.hibernate.mobileofficeplatform.accessoriesrequisition.AccessoriesRequisition;
import com.gzunicorn.hibernate.mobileofficeplatform.technologysupport.TechnologySupport;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class AccessoriesRequisitionAction extends DispatchAction {

	Log log = LogFactory.getLog(AccessoriesRequisitionAction.class);

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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String name = request.getParameter("method");
		/** **********��ʼ�û�Ȩ�޹���*********** */
		if(!"toDisplayRecord".equals(name)){
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "accessoriesrequisition", null);
		}
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
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

		request.setAttribute("navigator.location", "���������� >> ��ѯ�б�");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		request.setAttribute("userroleid", userInfo.getRoleID());
		
		if (tableForm.getProperty("genReport") != null
				&& !tableForm.getProperty("genReport").equals("")) {
			try {
				response = toExcelRecord(form, request, response);
				forward = mapping.findForward("exportExcel");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			HTMLTableCache cache = new HTMLTableCache(session,"accessoriesRequisitionList");
	
			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fAccessoriesRequisition");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("operDate");
			table.setIsAscending(false);
			cache.updateTable(table);
	
			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else if (action.equals("Submit")) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			
			cache.saveForm(tableForm);
			String appNo = tableForm.getProperty("appNo");
			String singleNo = tableForm.getProperty("singleNo");
			String maintDivision = tableForm.getProperty("maintDivision");
			String operId = tableForm.getProperty("operId");
			String sdate1 = tableForm.getProperty("sdate1");
			String edate1 = tableForm.getProperty("edate1");
			String handleStatus = tableForm.getProperty("handleStatus");
			String elevatorNo = tableForm.getProperty("elevatorNo");
			
			String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			String day1=DateUtil.getDate(day, "MM", -3);//��ǰ�����·�֮ǰ3����  ��
			if(sdate1==null || sdate1.trim().equals("")){
				sdate1=day1;
				tableForm.setProperty("sdate1",sdate1);
			}
			if(edate1==null || edate1.trim().equals("")){
				edate1=day;
				tableForm.setProperty("edate1",edate1);
			}
	
			if (!userInfo.getComID().equals("00")) {
				maintDivision = userInfo.getComID();
			}
	
			if(userInfo.getRoleID().equals("A50")){
				//ά����
				operId=userInfo.getUserID();
				request.setAttribute("add", "yes");
			}
			if(userInfo.getRoleID().equals("A01")){
				//ϵͳ����Ա
				request.setAttribute("add", "yes");
			}
	
	
			Session hs = null;
			Query query = null;
			String order = "";
			try {
				hs = HibernateUtil.getSession();
				
				String MaintStation="";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					MaintStation=userInfo.getStorageId();
				}
				
				String sql = "select ar.AppNo,ar.SingleNo,ar.OldNo,ar.NewNo,lu.username as openId,"
						+ "pc.UserName as personInCharge,c.comfullname,si.storagename,ar.OperDate,"
						+ "wm.UserName as warehouseManager,ar.handleStatus,ar.elevatorNo "
						+ "from AccessoriesRequisition ar "
						+ "left join  Loginuser wm on wm.UserID=ar.warehouseManager "
						+ "left join  Loginuser pc on pc.UserID=ar.PersonInCharge,"
						+ "Loginuser lu,Company c ,Storageid si "
						+ "where c.comid=ar.maintDivision and si.storageid=maintStation and ar.operId=lu.userid ";
				
				if (appNo != null && !appNo.equals("")) {
					sql += " and ar.appNo like '%" + appNo.trim() + "%'";
				}
				if (MaintStation != null && !MaintStation.equals("")) {
					sql += " and ar.MaintStation like '" + MaintStation.trim() + "%'";
				}
				if (handleStatus != null && !handleStatus.equals("")) {
					sql += " and ar.handleStatus like '" + handleStatus.trim() + "'";
				}
				if (singleNo != null && !singleNo.equals("")) {
					sql += " and ar.singleNo like '%" + singleNo.trim() + "%'";
				}
				if (elevatorNo != null && !elevatorNo.equals("")) {
					sql += " and ar.elevatorNo like '%" + elevatorNo.trim() + "%'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and ar.maintDivision like '" + maintDivision.trim()+ "'";
				}
				if (sdate1 != null && !sdate1.equals("")) {
					sql += " and ar.operDate >= '" + sdate1.trim() + " 00:00:00"
							+ "'";
				}
				if (edate1 != null && !edate1.equals("")) {
					sql += " and ar.operDate <= '" + edate1.trim() + " 23:59:59"
							+ "'";
				}
				if (operId != null && !operId.equals("")) {
					sql += " and (lu.UserID like '" + operId.trim()
							+ "%' or lu.UserName like '" + operId.trim() + "%')";
				}
				if (table.getIsAscending()) {
					order += " order by " + table.getSortColumn();
				} else {
					order += " order by " + table.getSortColumn() + " desc";
				}
				
				//System.out.println(sql + order);
	
				query = hs.createSQLQuery(sql + order);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;
	
				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());
	
				cache.check(table);
	
				List list = query.list();
				List accessoriesRequisitionList = new ArrayList();
	
				if (list != null && list.size() > 0) {
					int j = list.size();
					for (int i = 0; i < j; i++) {
						Object[] ts = (Object[]) list.get(i);
						HashMap map = new HashMap();
						map.put("appNo", ts[0]);
						map.put("singleNo", ts[1]);
						map.put("oldNo", ts[2]);
						map.put("newNo", ts[3]);
						map.put("operId",ts[4]);
						map.put("personInCharge", ts[5]);
						map.put("maintDivision", ts[6]);
						map.put("maintStation", ts[7]);
						map.put("operDate", ts[8]);
						map.put("warehouseManager", ts[9]);
						map.put("elevatorNo", ts[11]);
						
						if(ts[10]!=null){
							String hstatus=(String)ts[10];
		            	  	String hstatusname="";
		            	  	//����״̬ ��1 ά����������ˣ�2 ��������Ա��ˣ�3 ά����ȷ�ϣ�4 �ɼ��˻أ�5 �ѹرա�
		            	  	if(hstatus.trim().equals("1")){
		            	  		hstatusname="ά�����������";
		            	  	}else if(hstatus.trim().equals("2")){
		            	  		hstatusname="��������Ա���";
		            	  	}else if(hstatus.trim().equals("3")){
		            	  		hstatusname="ά����ȷ��";
		            	  	}else if(hstatus.trim().equals("4")){
		            	  		hstatusname="�ɼ��˻�";
		            	  	}else if(hstatus.trim().equals("5")){
		            	  		hstatusname="�ѹر�";
		            	  	}else if(hstatus.trim().equals("6")){
		            	  		hstatusname="��ֹ";
		            	  	}
							map.put("handleStatus", hstatusname);
						}else{
							map.put("handleStatus", "");
						}
						
						
						accessoriesRequisitionList.add(map);
					}
				}
	
				table.addAll(accessoriesRequisitionList);
				//�ֲ�������
				request.setAttribute("CompanyList",Grcnamelist1.getgrcnamelist(userInfo.getUserID()));
				session.setAttribute("accessoriesRequisitionList", table);
	
			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					hs.close();
					// HibernateSessionFactory.closeSession();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			forward = mapping.findForward("accessoriesRequisitionList");
		}
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
		request.setAttribute("navigator.location",
				messages.getMessage(locale, navigation));
	}


	/**
	 * ����鿴�ķ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();

		request.setAttribute("navigator.location", "���������� >> �鿴");

		ActionForward forward = null;
		String id = (String) dform.get("id");
		Session hs = null;
		AccessoriesRequisition a = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from AccessoriesRequisition ar where ar.appNo = :appNo");
				query.setString("appNo", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					a = (AccessoriesRequisition) list.get(0);
					
					//�������Ƽ��ͺ�/��ע,��ҳ����Ի��С�
					String r5str=a.getR5();
					r5str=r5str.replaceAll(";", ";<br/>");
					r5str=r5str.replaceAll("��", ";<br/>");
					a.setR5(r5str);
					
					a.setMaintDivision(bd.getName(hs,"Company", "ComFullName", "ComID",a.getMaintDivision()));
					a.setMaintStation(bd.getName(hs,"StorageID", "StorageName", "StorageID",a.getMaintStation()));
					String username=bd.getName(hs, "LoginUser", "UserName", "UserID",a.getOperId());
                	String phone=bd.getName(hs, "LoginUser", "phone", "UserID",a.getOperId());
                	a.setOperId(username+" "+phone);
                	
                	//��Ŀ���Ƽ�¥����
                	String elerem=bd.getName(hs, "ElevatorCoordinateLocation", "Rem", "ElevatorNo",a.getElevatorNo());
                	request.setAttribute("elerem", elerem);
					
					//ά��������
					a.setPersonInCharge(bd.getName(hs,"LoginUser", "UserName", "UserID",a.getPersonInCharge()));
					if(a.getIsAgree()!=null&& !a.getIsAgree().equals("")){
						 if(a.getIsAgree().equals("Y")||a.getIsAgree()=="Y"){
							a.setIsAgree("ͬ��");
						 }else{
							a.setIsAgree("��ͬ��");
						 }
					}
					if(a.getIsCharges()!=null&& !a.getIsCharges().equals("")){
						 if(a.getIsCharges().equals("Y") ||a.getIsCharges()=="Y"){
							a.setIsCharges("�շ�");
						 }else{
							a.setIsCharges("���");
						 }
					}
					if(a.getR1()!=null&& !a.getR1().equals("")){
						 if(a.getR1().equals("Y") || a.getR1()=="Y"){
							a.setR1("��");
						 }else{
							a.setR1("��");
						 }
					}
					if(a.getR3()!=null&& !a.getR3().equals("")){
						 if(a.getR3().equals("Y") || a.getR3()=="Y"){
							a.setR3("��");
						 }else{
							a.setR3("��");
						 }
					}
					
					//��������Ա
					a.setWarehouseManager(bd.getName(hs,"LoginUser", "UserName", "UserID",a.getWarehouseManager()));
					if(a.getWmIsAgree()!=null && !a.getWmIsAgree().trim().equals("")){
						a.setWmIsAgree(a.getWmIsAgree().trim().equals("N") ? "��ͬ��" : "ͬ��");
            	  	}
					if(a.getWmIsCharges()!=null && !a.getWmIsCharges().trim().equals("")){
            	  		String wmischargesname="";
            	  		if(a.getWmIsCharges().trim().equals("N")){
            	  			wmischargesname="���";
            	  		}else if(a.getWmIsCharges().trim().equals("Y")){
            	  			wmischargesname="�շ�";
            	  		}
            	  		a.setWmIsCharges(wmischargesname);
            	  	}
					//1: �ֿ�ֱ����ȡ,2: �ܿ����
            	  	if(a.getWmPayment()!=null && !a.getWmPayment().trim().equals("")){
            	  		String wmpaymentname="";
            	  		if(a.getWmPayment().trim().equals("1")){
            	  			wmpaymentname="�ֿ�ֱ����ȡ";
            	  		}else if(a.getWmPayment().trim().equals("2")){
            	  			wmpaymentname="�ܿ����";
            	  		}else if(a.getWmPayment().trim().equals("3")){
            	  			wmpaymentname="�⹺";
            	  		}
            	  		a.setWmPayment(wmpaymentname);
            	  	}
            	  	
            	  	//�ɼ��˻�
					a.setJjOperId(bd.getName(hs,"LoginUser", "UserName", "UserID",a.getJjOperId()));
            	  	if(a.getJjReturn()!=null && !a.getJjReturn().trim().equals("")){
            	  		String jjname="";
            	  		if(a.getJjReturn().trim().equals("Y")){
            	  			jjname="���˻�";
            	  		}else if(a.getJjReturn().trim().equals("N")){
            	  			jjname="δ�˻�";
            	  		}
            	  		a.setJjReturn(jjname);
            	  	}
            	  	if(a.getIsConfirm()!=null&& !a.getIsConfirm().equals("")){
						 if(a.getIsConfirm().equals("Y") || a.getIsConfirm()=="Y"){
							 a.setIsConfirm("�Ѹ���");
						 }else{
							 a.setIsConfirm("�뱸����");
						 }
					}
            	  	
            	  	//�������
					a.setCkoperid(bd.getName(hs,"LoginUser", "UserName", "UserID",a.getCkoperid()));
            	  	if(a.getCkiswc()!=null && !a.getCkiswc().trim().equals("")){
            	  		String jjname="";
            	  		if(a.getCkiswc().trim().equals("Y")){
            	  			jjname="�����";
            	  		}else if(a.getCkiswc().trim().equals("N")){
            	  			jjname="δ���";
            	  		}
            	  		a.setCkiswc(jjname);
            	  	}
            	  	
            	  	//�г�/�Ᵽ [FREE - �Ᵽ,PAID - �г�],
            	  	HashMap hmap=new HashMap();
        			String sqlk="select md.ElevatorNo,mm.MainMode,mm.ContractEDate,mm.BillNo "
        					+ "from MaintContractDetail md ,MaintContractMaster mm "
        					+ "where mm.BillNo=md.BillNo and mm.contractStatus in('XB','ZB') "
        					+ "and md.ElevatorNo='"+a.getElevatorNo()+"'";
        			List krelist=hs.createSQLQuery(sqlk).list();
        			if(krelist!=null && krelist.size()>0){
        				Object[] obj=(Object[])krelist.get(0);
            			hmap.put("mainmode", (String)obj[1]);//�г�/�Ᵽ
            			hmap.put("contractedate", (String)obj[2]);//��ͬ��������
            			hmap.put("billno", (String)obj[3]);
        			}else{
            			hmap.put("mainmode", "");//�г�/�Ᵽ
            			hmap.put("contractedate", "");//��ͬ��������
            			hmap.put("billno", "");
        			}
        			request.setAttribute("contracthmap", hmap);//��ͬ��������
        			
        			//�ɼ�ͼƬ
        			List olgimglist=bd.getWbFileInfoList(hs,"AccessoriesRequisition_OldImage",a.getAppNo());
        			request.setAttribute("olgimglist", olgimglist);
        			//�¼�ͼƬ
        			List newimglist=bd.getWbFileInfoList(hs,"AccessoriesRequisition_NewImage",a.getAppNo());
        			request.setAttribute("newimglist", newimglist);
        			//��Ʊ��Ϣ
        			List invoiceImagelist=bd.getWbFileInfoList(hs,"AccessoriesRequisition_invoiceImage",a.getAppNo());
        			request.setAttribute("invoiceImagelist", invoiceImagelist);
        			
				}

				if (a == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
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
			request.setAttribute("accessoriesRequisitionBean",a);
			
			
			forward = mapping.findForward("accessoriesRequisitionDisplay");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}

	/**
	 * ���ظ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toDownLoadFiles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String filename = request.getParameter("filename");
		String folder = request.getParameter("folder");
		if (folder == null || "".equals(folder)) {
			folder = "AccessoriesRequisition.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		filename=URLDecoder.decode(filename, "utf-8");
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(filename, "utf-8"));

		fis = new FileInputStream(folder + "/" + filename);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);

		int bytesRead = 0;
		byte[] buffer = new byte[5 * 1024];
		while ((bytesRead = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
			bos.flush();
		}
		if (fos != null) {
			fos.close();
		}
		if (bos != null) {
			bos.close();
		}
		if (fis != null) {
			fis.close();
		}
		if (bis != null) {
			bis.close();
		}
		return null;
	}

	/**
	 * ajax ���� �ֲ���ά��վ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void toStorageIDList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Session hs = null;
		String comid = request.getParameter("comid");
		response.setHeader("Content-Type", "text/html; charset=GBK");
		List list2 = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs = HibernateUtil.getSession();
			if (comid != null && !"".equals(comid)) {
				String hql = "select a from Storageid a,Company b where a.comid = b.comid and a.comid="
						+ comid + " and a.storagetype=1 and a.parentstorageid = '0'";
				List list = hs.createQuery(hql).list();
				if (list != null && list.size() > 0) {
					sb.append("<rows>");
					for (int i = 0; i < list.size(); i++) {
						Storageid sid = (Storageid) list.get(i);
						sb.append(
								"<cols name='" + sid.getStoragename()
										+ "' value='" + sid.getStorageid()
										+ "'>").append("</cols>");
					}
					sb.append("</rows>");

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			hs.close();
		}
		sb.append("</root>");

		response.setCharacterEncoding("gbk");
		response.setContentType("text/xml;charset=gbk");
		response.getWriter().write(sb.toString());
	}

	/**
	 * ��ת���ϴ���ť
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toModifyRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();

		request.setAttribute("navigator.location", "���������� >> �ϴ�");

		ActionForward forward = null;
		String id = (String) dform.get("id");
		Session hs = null;
		AccessoriesRequisition accessoriesRequisition = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs
						.createQuery("from AccessoriesRequisition ar where ar.appNo = :appNo");
				query.setString("appNo", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					accessoriesRequisition = (AccessoriesRequisition) list
							.get(0);
					accessoriesRequisition.setMaintDivision(bd.getName(hs,
							"Company", "ComFullName", "ComID",
							accessoriesRequisition.getMaintDivision()));
					accessoriesRequisition.setMaintStation(bd.getName(hs,
							"StorageID", "StorageName", "StorageID",
							accessoriesRequisition.getMaintStation()));
					accessoriesRequisition.setOperId(bd.getName(hs,
							"LoginUser", "UserName", "UserID",
							accessoriesRequisition.getOperId()));
					accessoriesRequisition.setPersonInCharge(bd.getName(hs,
							"LoginUser", "UserName", "UserID",
							accessoriesRequisition.getPersonInCharge()));
					accessoriesRequisition.setWarehouseManager(bd.getName(hs,
							"LoginUser", "UserName", "UserID",
							accessoriesRequisition.getWarehouseManager()));
					if(accessoriesRequisition.getIsAgree()!=null&& !accessoriesRequisition.getIsAgree().equals("")){
						 if(accessoriesRequisition.getIsAgree().equals("Y")||accessoriesRequisition.getIsAgree()=="Y"){
							accessoriesRequisition.setIsAgree("ͬ��");
						 }else{
							accessoriesRequisition.setIsAgree("��ͬ��");
						 }
						}
					
					String newimage=accessoriesRequisition.getNewImage();
						request.setAttribute("display", "no");

				}

				if (accessoriesRequisition == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"display.recordnotfounterror"));
				}
			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			} finally {
				try {
					hs.close();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			request.setAttribute("accessoriesRequisitionBean",
					accessoriesRequisition);
			
			forward = mapping.findForward("accessoriesRequisitionModify");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}

	/**
	 * ����ϴ���ť
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String appNo=request.getParameter("appNo");
		Session hs = null;
		 Transaction tx = null;
		FormFile formFile = null;
		Fileinfo file = null;
		try {
			   hs = HibernateUtil.getSession();
				tx=hs.beginTransaction();
			   String folder="AccessoriesRequisition.file.upload.folder";
			   folder = PropertiesUtil.getProperty(folder).trim();//
			 //  Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			   formFile=(FormFile) dform.get("newfile");
			   HandleFile hf = new HandleFileImpA();
			   String newFileName=appNo+"_"+formFile.getFileName();			//�����ļ���ϵͳ
			  
			   AccessoriesRequisition ar=(AccessoriesRequisition) hs.get(AccessoriesRequisition.class, appNo);
               hf.delFile(folder+ar.getNewImage());
               hf.createFile(formFile.getInputStream(), folder,newFileName);
			   ar.setNewImage(newFileName);
			   
			   hs.update(ar);
			   tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
			forward = mapping.findForward("returnList");

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	
	/**
	 * ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toDeleteRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		Session hs = null;
		Transaction tx = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();

			AccessoriesRequisition support = (AccessoriesRequisition) hs.get(AccessoriesRequisition.class, (String) dform.get("id"));
			if (support != null) {
				hs.delete(support);
				 errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));
			}
			tx.commit();
		} catch (Exception e2) {
			e2.printStackTrace();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.foreignkeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
			}
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		ActionForward forward = null;
		try {
			forward = mapping.findForward("returnList");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}
	

/**
 * Method toSearchRecord execute, to Excel Record �б��ѯ����Excel
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return ActionForward
 * @throws IOException
 */
public HttpServletResponse toExcelRecord(ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws IOException {
	
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
	
		String appNo = tableForm.getProperty("appNo");
		String singleNo = tableForm.getProperty("singleNo");
		String maintDivision = tableForm.getProperty("maintDivision");
		String operId = tableForm.getProperty("operId");
		String sdate1 = tableForm.getProperty("sdate1");
		String edate1 = tableForm.getProperty("edate1");
		String handleStatus = tableForm.getProperty("handleStatus");
		String elevatorNo = tableForm.getProperty("elevatorNo");
		
		String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
		String day1=DateUtil.getDate(day, "MM", -3);//��ǰ�����·�֮ǰ3����  ��
		if(sdate1==null || sdate1.trim().equals("")){
			sdate1=day1;
		}
		if(edate1==null || edate1.trim().equals("")){
			edate1=day;
		}

		if (!userInfo.getComID().equals("00")) {
			maintDivision = userInfo.getComID();
		}

		if(userInfo.getRoleID().equals("A50")){
			//ά����
			operId=userInfo.getUserID();
		}
	
		Session hs = null;
		ResultSet rs=null;
		XSSFWorkbook wb = new XSSFWorkbook();
		
		try{
			hs = HibernateUtil.getSession();
			
			String MaintStation="";
			//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
			String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
			List vmlist=hs.createSQLQuery(sqlss).list();
			if(vmlist!=null && vmlist.size()>0){
				MaintStation=userInfo.getStorageId();
			}
			
			String sql = "select ar.SingleNo,ar.elevatorNo,ar.handleStatus,lu.username as openId, "
					+ "pc.UserName as personInCharge,wm.UserName as warehouseManager, "
					+ "c.comfullname,si.storagename,ar.OperDate, "
					+ "ar.R7,mmd.MainMode,mmd.ContractEDate, "
					+ "ar.IsCharges,ar.NewNo,ar.instock, "
					+ "ar.JjReturn,ar.JjResult,ar.ckiswc "
					+ "from AccessoriesRequisition ar "
					+ "left join  Loginuser wm on wm.UserID=ar.warehouseManager "
					+ "left join  Loginuser pc on pc.UserID=ar.PersonInCharge "
					+ "left join (select md.ElevatorNo,max(mm.MainMode) as MainMode,max(mm.ContractEDate) as ContractEDate "
					+ "        		from MaintContractDetail md ,MaintContractMaster mm "
					+ "        		where mm.BillNo=md.BillNo and mm.contractStatus in('XB','ZB') "
					+ "        		group by md.ElevatorNo "
					+ "        ) mmd on  mmd.ElevatorNo=ar.elevatorNo, "
					+ "Loginuser lu,Company c ,Storageid si "
					+ "where c.comid=ar.maintDivision and si.storageid=maintStation and ar.operId=lu.userid ";
			
			if (appNo != null && !appNo.equals("")) {
				sql += " and ar.appNo like '%" + appNo.trim() + "%'";
			}
			if (MaintStation != null && !MaintStation.equals("")) {
				sql += " and ar.MaintStation like '" + MaintStation.trim() + "%'";
			}
			if (handleStatus != null && !handleStatus.equals("")) {
				sql += " and ar.handleStatus like '" + handleStatus.trim() + "'";
			}
			if (singleNo != null && !singleNo.equals("")) {
				sql += " and ar.singleNo like '%" + singleNo.trim() + "%'";
			}
			if (elevatorNo != null && !elevatorNo.equals("")) {
				sql += " and ar.elevatorNo like '%" + elevatorNo.trim() + "%'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and ar.maintDivision like '" + maintDivision.trim()+ "'";
			}
			if (sdate1 != null && !sdate1.equals("")) {
				sql += " and ar.operDate >= '" + sdate1.trim() + " 00:00:00'";
			}
			if (edate1 != null && !edate1.equals("")) {
				sql += " and ar.operDate <= '" + edate1.trim() + " 23:59:59'";
			}
			if (operId != null && !operId.equals("")) {
				sql += " and (lu.UserID like '" + operId.trim()
						+ "%' or lu.UserName like '" + operId.trim() + "%')";
			}

			sql += " order by ar.AppNo asc";

			rs=hs.connection().prepareStatement(sql).executeQuery();
			
			String[] titlename={"����/��������","���ݱ��","������","������","ά��������","��������Ա",
					"����ά���ֲ�","����ά��վ","��������","�������","�г�/�Ᵽ","��ͬ��������",
					"�����ж��Ƿ��շ�","�¼�����/�ͺ�","�������Ƿ��п��","�ɼ��˻�","�ɼ������","��������Ƿ����"};
			String[] titleid={"SingleNo","elevatorNo","handleStatus","openId","personInCharge","warehouseManager",
					"comfullname","storagename","OperDate","R7","MainMode","ContractEDate",
					"IsCharges","NewNo","instock","JjReturn","JjResult","ckiswc"};
			
	        XSSFSheet sheet = wb.createSheet("����������");
	        
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
			XSSFRow row = null;
			XSSFCell cell =null;
			int j=0;
			while(rs.next()) {
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(j+1);
				for(int c=0;c<titleid.length;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

			    	String valstr=rs.getString(titleid[c]);
				    if(titleid[c].equals("handleStatus")){
				    	//����״̬ ��1 ά����������ˣ�2 ��������Ա��ˣ�3 ά����ȷ�ϣ�4 �ɼ��˻أ�5 �ѹرա�
	            	  	if(valstr.trim().equals("1")){
	            	  		cell.setCellValue("ά�����������");
	            	  	}else if(valstr.trim().equals("2")){
	            	  		cell.setCellValue("��������Ա���");
	            	  	}else if(valstr.trim().equals("3")){
	            	  		cell.setCellValue("ά����ȷ��");
	            	  	}else if(valstr.trim().equals("4")){
	            	  		cell.setCellValue("�ɼ��˻�");
	            	  	}else if(valstr.trim().equals("5")){
	            	  		cell.setCellValue("�ѹر�");
	            	  	}else if(valstr.trim().equals("6")){
	            	  		cell.setCellValue("��ֹ");
	            	  	}
				    }else if(titleid[c].equals("IsCharges")){
				    	//�����ж��Ƿ��շ�
				    	if(valstr!=null && valstr.trim().equals("Y")){
					    	cell.setCellValue("�շ�");
				    	}else{
				    		cell.setCellValue("���");
				    	}
				    }else if(titleid[c].equals("JjReturn")){
				    	//�ɼ��˻�
				    	if(valstr!=null && valstr.trim().equals("Y")){
					    	cell.setCellValue("���˻�");
				    	}else{
				    		cell.setCellValue("δ�˻�");
				    	}
				    }else if(titleid[c].equals("ckiswc")){
				    	//��������Ƿ����
				    	if(valstr!=null && valstr.trim().equals("Y")){
					    	cell.setCellValue("�����");
				    	}else{
				    		cell.setCellValue("δ���");
				    	}
				    }else if(titleid[c].equals("MainMode")){
				    	//�г�/�Ᵽ
				    	if(valstr!=null && valstr.trim().equals("FREE")){
					    	cell.setCellValue("�Ᵽ");
				    	}else{
				    		cell.setCellValue("�г�");
				    	}
				    }else{
				    	cell.setCellValue(valstr);
				    }
				    
				}
				j++;
			}
	
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
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("����������", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}

	
}
