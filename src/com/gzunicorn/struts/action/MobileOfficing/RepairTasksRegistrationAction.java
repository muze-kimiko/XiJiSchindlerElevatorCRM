package com.gzunicorn.struts.action.MobileOfficing;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;

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
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutprocess.CalloutProcess;
import com.gzunicorn.hibernate.hotlinemanagement.calloutsms.CalloutSms;
import com.gzunicorn.hibernate.infomanager.elevatortransfercaseregister.ElevatorTransferCaseRegister;
import com.gzunicorn.hibernate.infomanager.handoverelevatorcheckfileinfo.HandoverElevatorCheckFileinfo;
import com.gzunicorn.hibernate.infomanager.handoverelevatorcheckitemregister.HandoverElevatorCheckItemRegister;
import com.gzunicorn.hibernate.infomanager.handoverelevatorspecialregister.HandoverElevatorSpecialRegister;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplandetail.MaintenanceWorkPlanDetail;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplanfileinfo.MaintenanceWorkPlanFileinfo;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplanmaster.MaintenanceWorkPlanMaster;
import com.gzunicorn.hibernate.mobileofficeplatform.accessoriesrequisition.AccessoriesRequisition;
import com.gzunicorn.hibernate.mobileofficeplatform.technologysupport.TechnologySupport;
import com.gzunicorn.hibernate.mobileofficeplatform.technologysupportfiles.TechnologySupportFiles;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.struts.action.hotlinemanagement.hotcalloutmaster.hotCalloutMasterjx;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;
import com.itextpdf.text.log.SysoLogger;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;


public class RepairTasksRegistrationAction extends DispatchAction {

	Log log = LogFactory.getLog(RepairTasksRegistrationAction.class);
	XjsggAction xj=new XjsggAction();
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

		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "repairtasksregistration", null);
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

		request.setAttribute("navigator.location", " ��������Ǽ�>> ��ѯ�б�");		
			ActionForward forward = null;
			
			HttpSession session = request.getSession();
			ServeTableForm tableForm = (ServeTableForm) form;
			String action = tableForm.getAction();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
				HTMLTableCache cache = new HTMLTableCache(session, "repairTasksRegistrationList");
				DefaultHTMLTable table = new DefaultHTMLTable();
				table.setMapping("fRepairTasksRegistration");
				table.setLength(SysConfig.HTML_TABLE_LENGTH);
				cache.updateTable(table);
				table.setSortColumn("cm.calloutMasterNo");
				table.setIsAscending(true);
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
				
				String companyName = tableForm.getProperty("companyId");// ���޵�λ����
				String calloutMasterNo = tableForm.getProperty("calloutMasterNo");// ���ޱ��
				String handleStatus = tableForm.getProperty("handleStatus");// ����״̬
				
				if(handleStatus==null||"".equals(handleStatus)){
					handleStatus="N";
					tableForm.setProperty("handleStatus", handleStatus);
				}

				Session hs = null;
				Query query = null;
				String order="";
				try {

					hs = HibernateUtil.getSession();
					String sql = "select cp,cm,c.companyName from CalloutProcess cp,CalloutMaster cm,Customer c "
					+ "where c.companyId=cm.companyId and cp.calloutMasterNo=cm.calloutMasterNo "
					+ "and  cp.assignObject2='"+userInfo.getUserID()+"'";
					
					if(companyName!=null&&!"".equals(companyName)){
						sql+=" and (c.companyId like '%"+companyName.trim()+"%' or c.companyName like '%"+companyName.trim()+"%')";
					}
                    if(calloutMasterNo!=null&&!"".equals(calloutMasterNo)){
                    	sql+=" and cm.calloutMasterNo like '%"+calloutMasterNo.trim()+"%'";
					}
                    if(handleStatus!=null&&!"".equals(handleStatus)&&!"%".equals(handleStatus)){
                    	if(handleStatus.equals("Y")){
                    		sql+=" and cm.handleStatus >= '4' ";
                    	}else{
                    		sql+=" and cm.handleStatus < '4' ";
                    	}
                    }
                    
                    
        			if (table.getIsAscending()) {	
        				sql+=" order by " + table.getSortColumn() + " asc ";
        			} else {
        				sql+=" order by " + table.getSortColumn() + " desc ";
        			}
					query = hs.createQuery(sql);
					table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

					// �ó���һҳ�����һ����¼����;
					query.setFirstResult(table.getFrom()); // pagefirst
					query.setMaxResults(table.getLength());

					cache.check(table);

					List list = query.list();
					List repairTasksRegistrationList = new ArrayList();
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							Object[] objects=(Object[]) list.get(i);
							HashMap map=new HashMap();
							CalloutProcess cp=(CalloutProcess) objects[0];
							CalloutMaster cm=(CalloutMaster) objects[1];
							map.put("calloutMasterNo", cm.getCalloutMasterNo());
							map.put("companyName", objects[2]);
							
							if(cm.getIsTrap().equals("Y")){
								map.put("isTrap", "����");
							}else{
								map.put("isTrap", "������");
							}
							List cpList=null;

							cpList=hs.createQuery("select a.pullname from Pulldown a where "
									+ "a.id.typeflag='CalloutMaster_RepairMode' and a.enabledflag='Y' and a.id.pullid='"+cm.getRepairMode()+"'").list();
							if(cpList!=null&&cpList.size()>0){
								map.put("repairMode",cpList.get(0));
							}
							
							cpList=hs.createQuery("select a.pullname from Pulldown a where "
									+ "a.id.typeflag='CalloutMaster_ServiceObjects' and a.enabledflag='Y' and a.id.pullid='"+cm.getServiceObjects()+"'").list();
							if(cpList!=null&&cpList.size()>0){
								map.put("serviceObjects",cpList.get(0));
							}
							
							if(cm.getHandleStatus()!=null&&!cm.getHandleStatus().trim().equals("")){
								if(cm.getHandleStatus().trim().equals("0")){
									map.put("handleStatusName", "��ת��");
								}else if(cm.getHandleStatus().trim().equals("1")){
									map.put("handleStatusName", "�ѽ���");
								}else if(cm.getHandleStatus().trim().equals("2")){
									map.put("handleStatusName", "�ѵ���");
								}else if(cm.getHandleStatus().trim().equals("3")){
									map.put("handleStatusName", "��ͣ��");
								}else{
									map.put("handleStatusName", "���깤");
									cm.setHandleStatus("4");
								}
							}else{
								map.put("handleStatusName", "δ����");
							}
							
							
							map.put("handleStatus", cm.getHandleStatus());
							
							repairTasksRegistrationList.add(map);
						}
					}
					table.addAll(repairTasksRegistrationList);
					session.setAttribute("repairTasksRegistrationList", table);
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
				
				forward = mapping.findForward("repairTasksRegistrationList");
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location"," ��������Ǽ�>> �鿴");
		
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id = request.getParameter("id");
		if(id==null && "".equals(id)){
			id=(String) dform.get("id");
		}
		HashMap hm=new HashMap();
		Session hs = null;
		CalloutMaster cm=null;
		CalloutProcess cp=null;
		 if (id != null) {				
				try {
					hs = HibernateUtil.getSession();
					cm=(CalloutMaster)hs.get(CalloutMaster.class, id);
					cm.setOperId(bd.getName("Loginuser", "username", "userid", cm.getOperId()));
					
//					cm.setRepairMode(cm.getRepairMode().equals("1")?"��������":"��������");
//					String serviceObjects=cm.getServiceObjects();
//					if(serviceObjects!=null && "1".equals(serviceObjects)){
//					cm.setServiceObjects("�Ա�����");	
//					}else{
//					cm.setServiceObjects(cm.getServiceObjects().equals("2")?"��������":"���ҷ�����");	
//					}
					List cpList=hs.createQuery("select a.pullname from Pulldown a where "
							+ "a.id.typeflag='CalloutMaster_RepairMode' and a.enabledflag='Y' and a.id.pullid='"+cm.getRepairMode()+"'").list();
					if(cpList!=null&&cpList.size()>0){
						cm.setRepairMode(cpList.get(0).toString());
					}
					
					cpList=hs.createQuery("select a.pullname from Pulldown a where "
							+ "a.id.typeflag='CalloutMaster_ServiceObjects' and a.enabledflag='Y' and a.id.pullid='"+cm.getServiceObjects()+"'").list();
					if(cpList!=null&&cpList.size()>0){
						cm.setServiceObjects(cpList.get(0).toString());
					}
					
					String companyName=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
					if(companyName==null || "".equals(companyName)){
						companyName=cm.getCompanyId();
					}
					cm.setCompanyId(companyName);
					cm.setMaintStation(bd.getName("Storageid", "storagename", "storageid", cm.getMaintStation()));
					cm.setAssignObject(bd.getName("Loginuser", "username", "userid", cm.getAssignObject()));
					cm.setIsTrap(String.valueOf(cm.getIsTrap()).equals("Y")?"����":"������");
					
					cm.setAuditOperid(bd.getName(hs,"Loginuser", "username", "userid", cm.getAuditOperid()));
					cm.setHfcId(bd.getName(hs,"HotlineFaultClassification", "hfcName", "hfcId", String.valueOf(cm.getHfcId())));//���Ϸ���
					String IsSendSms=cm.getIsSendSms();        			
					cp=(CalloutProcess)hs.get(CalloutProcess.class, id);
					if(cp!=null){
					cp.setTurnSendId(bd.getName(hs,"Loginuser", "username", "userid", String.valueOf(cp.getTurnSendId())));
					cp.setAssignUser(bd.getName(hs,"Loginuser", "username", "userid", String.valueOf(cp.getAssignUser())));
					String IsStop=cp.getIsStop();
					if(IsStop!=null && !"".equals(IsStop)){
					cp.setIsStop(IsStop.equals("Y")?"ͣ��":"��ͣ��");
					}
					cp.setHmtId(bd.getName(hs,"HotlineMotherboardType", "hmtName", "hmtId", String.valueOf(cp.getHmtId())));//��������
					cp.setHftId(bd.getName(hs,"HotlineFaultType", "hftDesc", "hftId",String.valueOf(cp.getHftId())));//��������
					String IsToll=cp.getIsToll();
					if(IsToll!=null && !"".equals(IsToll)){
						cp.setIsToll(String.valueOf(cp.getIsToll()).equals("Y")?"�շ�":"���շ�");
					}
//					Double ArriveLongitude=cp.getArriveLongitude();
//					Double ArriveLatitude=cp.getArriveLatitude();
//					Double FninishLongitude=cp.getFninishLongitude();
//					Double FninishLatitude=cp.getFninishLatitude();
//					if(ArriveLongitude!=null && ArriveLatitude!=null){
//						String elevatorLocation=hotCalloutMasterjx.getelecadrr(hs, id, ArriveLongitude, ArriveLatitude);
//						if(elevatorLocation==null){
//							elevatorLocation=cp.getArriveLocation();
//						}
//						hm.put("elevatorLocation",elevatorLocation );
//						}
//					if(FninishLongitude!=null && FninishLatitude!=null){
//						String elevatorLocation2=hotCalloutMasterjx.getelecadrr(hs,id,FninishLongitude,FninishLatitude);
//								if(elevatorLocation2==null){
//									elevatorLocation2=cp.getFninishLocation();
//								}
//								hm.put("elevatorLocation2", elevatorLocation2);}
					}			
				} catch (DataStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
			}
    request.setAttribute("CalloutMasterList", cm);
    request.setAttribute("CalloutProcessList", cp);
	if (!errors.isEmpty()) {
		saveErrors(request, errors);
	}
		forward = mapping.findForward("repairTasksRegistrationDisplay");
		return forward;
	}

	/**
	 * �ļ����ط���
	 * @param response
	 * @param localPath ���ش����ļ�����·�� ��:(D:/WebProjects/xxxxxx2010��ڼ���.jpg)
	 * @param loname  �ļ��߼����� ��:(2010��ڼ���.jpg)
	 * @throws IOException
	 */
	public void toDownLoadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
		throws IOException {
		/*
		 * ȡ���ļ�:�ļ�id+�ļ�·��+�ļ���+�� �ļ�id=ͨ��formbeanȡ�� �ļ�·��=ͨ��ȡ�������ļ��ķ����õ�
		 * �ļ�����=ͨ�����ݿ�õ� ��=io
		 */
		Session hs = null;

		String filesid =request.getParameter("filesid");//��ˮ��
		
		String localPath="";
		String oldname="";
		
		String folder = request.getParameter("folder");		//�ļ���
		if(null == folder || "".equals(folder)){
			folder ="MaintenanceWorkPlan.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
			String sqlfile="select a from MaintenanceWorkPlanFileinfo a where a.fileSid='"+filesid+"'";
			List list2=hs.createQuery(sqlfile).list();
            
			if(list2!=null && list2.size()>0){
				MaintenanceWorkPlanFileinfo fp=(MaintenanceWorkPlanFileinfo)list2.get(0);
				String newName=fp.getNewFileName();
				oldname=fp.getOldFileName();
				String root=folder;//�ϴ�Ŀ¼
				localPath = root+"/"+fp.getFilePath()+"/"+newName;
			}
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(oldname, "utf-8"));

		fis = new FileInputStream(localPath);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);

		int bytesRead = 0;
		byte[] buffer = new byte[5 * 1024];
		while ((bytesRead = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
			bos.flush();
		}
		if (fos != null) {fos.close();}
		if (bos != null) {bos.close();}
		if (fis != null) {fis.close();}
		if (bis != null) {bis.close();}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {				
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ת���޸�ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPrepareUpdateRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws DataStoreException {
		
		request.setAttribute("navigator.location","��������Ǽ� >> ����");	
		
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id = request.getParameter("id");
		if(id==null || "".equals(id)){
			id=request.getAttribute("calloutMasterNo").toString().trim();
		}
		
		HashMap hm=new HashMap();
		Session hs = null;
		CalloutMaster cm=null;
		CalloutProcess cp=null;
		 if (id != null) {				
				try {
					hs = HibernateUtil.getSession();
					cm=(CalloutMaster)hs.get(CalloutMaster.class, id);
					cm.setOperId(bd.getName("Loginuser", "username", "userid", cm.getOperId()));
					
//					cm.setRepairMode(cm.getRepairMode().equals("1")?"��������":"��������");
//					String serviceObjects=cm.getServiceObjects();
//					if(serviceObjects!=null && "1".equals(serviceObjects)){
//					cm.setServiceObjects("�Ա�����");	
//					}else{
//					cm.setServiceObjects(cm.getServiceObjects().equals("2")?"��������":"���ҷ�����");	
//					}
					List cpList=hs.createQuery("select a.pullname from Pulldown a where "
							+ "a.id.typeflag='CalloutMaster_RepairMode' and a.enabledflag='Y' and a.id.pullid='"+cm.getRepairMode()+"'").list();
					if(cpList!=null&&cpList.size()>0){
						cm.setRepairMode(cpList.get(0).toString());
					}
					
					cpList=hs.createQuery("select a.pullname from Pulldown a where "
							+ "a.id.typeflag='CalloutMaster_ServiceObjects' and a.enabledflag='Y' and a.id.pullid='"+cm.getServiceObjects()+"'").list();
					if(cpList!=null&&cpList.size()>0){
						cm.setServiceObjects(cpList.get(0).toString());
					}
					
					String companyName=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
					if(companyName==null || "".equals(companyName)){
						companyName=cm.getCompanyId();
					}
					cm.setCompanyId(companyName);
					cm.setR1(bd.getName("Storageid", "storagename", "storageid", cm.getMaintStation()));
					cm.setAssignObject(bd.getName("Loginuser", "username", "userid", cm.getAssignObject()));
					cm.setIsTrap(String.valueOf(cm.getIsTrap()).equals("Y")?"����":"������");
					
					cm.setAuditOperid(bd.getName(hs,"Loginuser", "username", "userid", cm.getAuditOperid()));
					cm.setHfcId(bd.getName(hs,"HotlineFaultClassification", "hfcName", "hfcId", String.valueOf(cm.getHfcId())));//���Ϸ���
					String IsSendSms=cm.getIsSendSms();        			
					cp=(CalloutProcess)hs.get(CalloutProcess.class, id);
					if(cp!=null){
					cp.setAssignUser(bd.getName(hs,"Loginuser", "username", "userid", String.valueOf(cp.getAssignUser())));
					String IsStop=cp.getIsStop();
					if(IsStop!=null && !"".equals(IsStop)){
					cp.setIsStop(IsStop.equals("Y")?"ͣ��":"��ͣ��");
					}
					cp.setHmtId(bd.getName(hs,"HotlineMotherboardType", "hmtName", "hmtId", String.valueOf(cp.getHmtId())));//��������
					cp.setHftId(bd.getName(hs,"HotlineFaultType", "hftDesc", "hftId",String.valueOf(cp.getHftId())));//��������
					String IsToll=cp.getIsToll();
					if(IsToll!=null && !"".equals(IsToll)){
						cp.setIsToll(String.valueOf(cp.getIsToll()).equals("Y")?"�շ�":"���շ�");
					}
					String btn1=null;
					String btn2=null;
					String iscomplete="N";
					if(cm.getHandleStatus()!=null&& !cm.getHandleStatus().equals("")){
	             		if(cm.getHandleStatus().equals("0")||cm.getHandleStatus()=="0"){
	             			btn1="�� ��";
	             			btn2="ת ��";
	             			cp.setAssignUser(userInfo.getUserName());
	             			cp.setAssignUserTel(bd.getName(hs, "Loginuser", "phone", "userid", userInfo.getUserID()));
	             			
	             			request.setAttribute("assignTime", CommonUtil.getNowTime());
                            request.setAttribute("turnSendTime", CommonUtil.getNowTime());
	             		}else if(cm.getHandleStatus().equals("1")||cm.getHandleStatus()=="1"){
		             		btn1="�� ��";
		             		request.setAttribute("arriveDateTime", CommonUtil.getNowTime());
		             		
		             	 }else if(cm.getHandleStatus().equals("2")||cm.getHandleStatus()=="2"){
		             		btn1="ͣ ��";
		             		btn2="�� ��";
		             		request.setAttribute("stopTime", CommonUtil.getNowTime());
		             		iscomplete="Y";
		             		request.setAttribute("completeTime", CommonUtil.getNowTime());
		             		
		             	 }else if(cm.getHandleStatus().equals("3")||cm.getHandleStatus()=="3"){
		             		btn1="�� ��";
		             		iscomplete="Y";
		             		request.setAttribute("completeTime", CommonUtil.getNowTime());
		             	 }
	             		}else{
	             			btn1="�� ��";
	             			btn2="ת ��";
	             			cp.setAssignUser(userInfo.getUserName());
	             			cp.setAssignUserTel(bd.getName(hs, "Loginuser", "phone", "userid", userInfo.getUserID()));
                            request.setAttribute("assignTime", CommonUtil.getNowTime());
                            request.setAttribute("turnSendTime", CommonUtil.getNowTime());
	             			
	             			
	             			cm.setHandleStatus("0");
	             		}
					List HotlineFaultClassificationList=xj.getClasses(hs, "HotlineFaultClassification", "enabledFlag", "Y");
					request.setAttribute("HotlineFaultClassificationList", HotlineFaultClassificationList);
					  request.setAttribute("btn1", btn1);
					  request.setAttribute("btn2", btn2);
					  request.setAttribute("iscomplete", iscomplete);
					  request.setAttribute("turnName", bd.getName(hs, "Loginuser", "username", "userid", cp.getTurnSendId()));

					}			
				} catch (DataStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
			}
    request.setAttribute("CalloutMasterList", cm);
    request.setAttribute("CalloutProcessList", cp);
	if (!errors.isEmpty()) {
		saveErrors(request, errors);
	}
		forward = mapping.findForward("repairTasksRegistrationModify");
		return forward;
	}

	

	/**
	 * ����޸ĵķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toUpdateRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		DynaActionForm dform = (DynaActionForm) form;
		toUpdate(form,request,response,errors);// �������޸ļ�¼
		String btn =request.getParameter("btn");
		try {
			if(btn.equals("0")){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.succeed"));
				forward = mapping.findForward("returnList");
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.succeed"));
				forward = mapping.findForward("returnModify");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}


	/**
	 * �������ݷ���
	 * @param form
	 * @param request
	 * @return ActionErrors
	 */
	public void toUpdate(ActionForm form, HttpServletRequest request,HttpServletResponse response,ActionErrors errors) {
		
		DynaActionForm dform = (DynaActionForm) form;

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);	
		String calloutMasterNo =request.getParameter("calloutMasterNo");
		request.setAttribute("calloutMasterNo", calloutMasterNo);
		
		String btn =request.getParameter("btn");
		Session hs = null;
		Transaction tx = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if(calloutMasterNo!=null&&!calloutMasterNo.equals(""))
			{
				CalloutMaster cm=(CalloutMaster) hs.get(CalloutMaster.class, calloutMasterNo.trim());
				CalloutProcess cp=(CalloutProcess) hs.get(CalloutProcess.class, calloutMasterNo.trim());
				if(cm!=null)
				{
					if(btn.equals("1")){
                    cm.setHandleStatus("1");
					String assignTime=request.getParameter("assignTime");
                    cp.setAssignUser(userInfo.getUserID());
					cp.setAssignUserTel(bd.getName(hs, "Loginuser", "phone", "userid", userInfo.getUserID()));
					cp.setAssignTime(assignTime);
					}else if(btn.equals("0")){
						String turnSendTime=request.getParameter("turnSendTime");
						String turnSendId=request.getParameter("turnSendId");
						String turnSendTel=request.getParameter("turnSendTel");
						cm.setHandleStatus("0");
						cp.setAssignObject2(turnSendId);
						cp.setTurnSendId(turnSendId);
						cp.setTurnSendTel(turnSendTel);
                        cp.setTurnSendTime(turnSendTime);	
					}else if(btn.equals("2")){
						cm.setHandleStatus("2");	
						String arriveDateTime=request.getParameter("arriveDateTime");
						String arriveLocation=request.getParameter("arriveLocation");
						String deviceId=request.getParameter("deviceId");
						String faultCode=request.getParameter("faultCode");
						String faultStatus=request.getParameter("faultStatus");
						String hmtId=request.getParameter("hmtId");
						
						cp.setArriveDateTime(arriveDateTime);
						cp.setArriveLocation(arriveLocation);
						cp.setDeviceId(deviceId);
						cp.setFaultCode(faultCode);
						cp.setFaultStatus(faultStatus);
						cp.setHmtId(hmtId);
						
						
					}else if(btn.equals("3")){
						cm.setHandleStatus("3");
						String isStop=request.getParameter("isStop");
						String stopTime=request.getParameter("stopTime");
						String stopRem=request.getParameter("stopRem");
						if(isStop.trim().equals("Y")){
							cp.setIsStop(isStop);
							cp.setStopTime(stopTime);
							cp.setStopRem(stopRem);
						}else{
							cp.setIsStop(isStop);
						}
					}else if(btn.equals("4")){
						
						cm.setHandleStatus("5");
						//String hfcId=(String) dform.get("hfcId");
						String serviceRem=request.getParameter("serviceRem");
						String processDesc=request.getParameter("processDesc");
						//String money=request.getParameter("money");
						//String isToll=request.getParameter("isToll");
						//String newFittingName=request.getParameter("newFittingName");
						String fninishLocation=request.getParameter("fninishLocation");
						String completeTime=request.getParameter("completeTime");
						String hftId=request.getParameter("hftId");
						
						//cm.setHfcId(hfcId);
						cp.setIsgzbgs("Y");
						cp.setServiceRem(serviceRem);
						cp.setProcessDesc(processDesc);
						//cp.setMoney(Double.valueOf(money.trim()));
						//cp.setIsToll(isToll);
						//cp.setNewFittingName(newFittingName);
						cp.setFninishLocation(fninishLocation);
						cp.setCompleteTime(completeTime);
						cp.setHftId(hftId);
 
					}
					hs.update(cm);
					hs.flush();
					hs.update(cp);
					hs.flush();
				}
			}
			tx.commit();
		} catch (Exception e1) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
			try {
				tx.rollback();
			} catch (HibernateException e2) {
				log.error(e2.getMessage());
				DebugUtil.print(e2, "Hibernate Transaction rollback error!");
			}
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate region Insert error!");
		} finally {
			try {
				hs.close();
				String isreturn = request.getParameter("isreturn");
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}
		
	 }

	
	//��ȡ��һ�α���ʱ����ж��������
	private String togetAuditCircu(Session hs,String id,
			String str) {
		 String sMaintEndTime="";
		String sql="select mwpd.maintEndTime from MaintenanceWorkPlanDetail mwpd where mwpd.numno=(select numno-1 from MaintenanceWorkPlanDetail where numno='"+id+"' and billno='"+str+"' and numno !=(select MIN(numno)from MaintenanceWorkPlanDetail where billno='"+str+"'))";
		List list=hs.createSQLQuery(sql).list();
		if(list!=null&&list.size()>0)
	    {
			sMaintEndTime=(String) list.get(0);
			if(sMaintEndTime!=null&&!"".equals(sMaintEndTime)){
			sMaintEndTime=sMaintEndTime.substring(0, 10);
			}
	    }
	    return sMaintEndTime;
	}	
	
	/**
	 * �ϴ����渽��
	 * @param form
	 * @param request
	 * @param response
	 * @param folder
	 * @param billno
	 * @return
	 */
	public List savePicter(ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String folder, String singleno) {
		List returnList = new ArrayList();
		folder = PropertiesUtil.getProperty(folder).trim();//
		if(null == folder || "".equals(folder)){
			folder ="MaintenanceWorkPlan.file.upload.folder";
		}
		DynaActionForm dform = (DynaActionForm) form;
		FormFile formFile = null;
		Fileinfo file = null;
		if (form.getMultipartRequestHandler() != null) {
			Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			if (hash != null) {
				HandleFile hf = new HandleFileImpA();
				for(Iterator it = hash.keySet().iterator(); it.hasNext();){
					String key=(String)it.next();
					formFile=(FormFile)hash.get(key);
					Map map = new HashMap();
					if(formFile!=null){
						try {
							if(!formFile.getFileName().equals("")){
								String newFileName="MaintenanceWorkPlanDetail"+"_"+singleno+"_"+key+"_"+formFile.getFileName();
								map.put("oldfilename", formFile.getFileName());
								map.put("filepath", CommonUtil.getNowTime("yyyy-MM-dd")+"/");
								map.put("fileSize", new Double(formFile.getFileSize()));
								map.put("fileformat",formFile.getContentType());
								map.put("newFileName", newFileName);
								//�����ļ���ϵͳ
								hf.createFile(formFile.getInputStream(), folder+"/"+CommonUtil.getNowTime("yyyy-MM-dd")+"/", newFileName);
								returnList.add(map);
							}else{
								continue;
							}
							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		return returnList;
	}
		
	/**
	 * ���渽����Ϣ�����ݿ�
	 * @param hs
	 * @param request
	 * @param fileInfoList
	 * @param mtcId
	 * @param trsId
	 * @param seiid
	 * @param billno
	 * @return
	 */
	public boolean savePicterTodb(HttpServletRequest request,List fileInfoList,String singleno){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs = null;
		Transaction tx = null;
		boolean saveFlag = true;
		if(null != fileInfoList && fileInfoList.size()>0 && singleno!=null && !"".equals(singleno)){
			String sql="";
			try {
				hs=HibernateUtil.getSession();
				tx=hs.beginTransaction();
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					String newFilename=(String) map.get("newFileName");
					MaintenanceWorkPlanFileinfo fileInfo=new MaintenanceWorkPlanFileinfo();
					fileInfo.setOldFileName((String)map.get("oldfilename"));
					fileInfo.setNewFileName((String)map.get("newFileName"));
					fileInfo.setSingleno(singleno);
					fileInfo.setFileSize(Double.valueOf(map.get("fileSize").toString()));
					fileInfo.setFilePath((String)map.get("filepath"));
					fileInfo.setFileFormat((String)map.get("fileformat"));
					fileInfo.setUploadDate(CommonUtil.getNowTime());
					fileInfo.setUploader(userInfo.getUserID());
					hs.save(fileInfo);
				}
				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
				try {
					tx.rollback();
				} catch (HibernateException e2) {
					log.error(e2.getMessage());
				}
			}finally{
				if(hs!=null){
					hs.close();
				}
			}
		}
		return saveFlag;
	}
	
	/**
	 * ��ת���������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toAccessoriesRequisition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("navigator.location", " ��������Ǽ�>> �������");	
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ActionErrors errors = new ActionErrors();				
		DynaActionForm dform = (DynaActionForm) form;
		
		String id = request.getParameter("id");
		if(id==null || "".equals(id)){
			id=request.getAttribute("calloutMasterNo").toString().trim();
		}
		Session hs = null;
		try {
			    hs = HibernateUtil.getSession();
			    request.setAttribute("action", "repairTasksRegistrationAction");
			    request.setAttribute("id", id);
				HashMap map =new HashMap();
			    map.put("singleNo", id);
			    map.put("operId", userInfo.getUserName());
			    map.put("operDate", CommonUtil.getNowTime());
			    map.put("maintDivision", bd.getName(hs, "Company", "ComFullName", "ComID", userInfo.getComID()));
			    map.put("maintStation",  bd.getName(hs, "StorageID", "StorageName", "StorageID", userInfo.getStorageId()));
			    request.setAttribute("mapBean", map);	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toAccessoriesRequisition");
		
	}
	
	/**
	 * ����������뵥
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toAccessoriesRequisitionRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id=request.getParameter("id");
		String operDate=request.getParameter("operDate");
		String oldNo=request.getParameter("oldNo");
		Session hs = null;
		 Transaction tx = null;
		FormFile formFile = null;
		Fileinfo file = null;
		try {
			   hs = HibernateUtil.getSession();
			  
				tx=hs.beginTransaction();
			   String year1=CommonUtil.getToday().substring(2,4);
			   String billno1 = CommonUtil.getBillno(year1,"AccessoriesRequisition", 1)[0];	
			   String folder="AccessoriesRequisition.file.upload.folder";
				folder = PropertiesUtil.getProperty(folder).trim();//
			 //  Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			   formFile=(FormFile) dform.get("newfile");
			   HandleFile hf = new HandleFileImpA();
			   String newFileName=billno1+"_"+formFile.getFileName();			//�����ļ���ϵͳ
			   hf.createFile(formFile.getInputStream(), folder,newFileName);
			   
			   AccessoriesRequisition ar=new AccessoriesRequisition();
			   ar.setAppNo(billno1);
			   ar.setSingleNo(id);
			   ar.setOldNo(oldNo);
			   ar.setOldImage(newFileName);
			   ar.setOperId(userInfo.getUserID());
			   ar.setOperDate(operDate);
			   ar.setMaintDivision(userInfo.getComID());
			   ar.setMaintStation(userInfo.getStorageId());
			   hs.save(ar);
			   tx.commit();
               request.setAttribute("calloutMasterNo", id);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
		String isreturn = request.getParameter("isreturn");
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("accessoriesRequisition.success"));
				forward = mapping.findForward("returnModify");
			} else {
				// return modify page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("accessoriesRequisition.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				forward = mapping.findForward("returnAccessoriesRequisition");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	
	
	
	/**
	 * ��ת����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toTechnologySupport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("navigator.location", " ��������Ǽ�>> ���뼼��֧��");	
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ActionErrors errors = new ActionErrors();				
		DynaActionForm dform = (DynaActionForm) form;
		
		String id = request.getParameter("id");
		if(id==null || "".equals(id)){
			id=request.getAttribute("calloutMasterNo").toString().trim();
		}
		Session hs = null;
		try {
			    hs = HibernateUtil.getSession();
			    request.setAttribute("id", id);
			    CalloutProcess cp=(CalloutProcess) hs.get(CalloutProcess.class, id.trim());
			    
			    
				HashMap map =new HashMap();
			    map.put("singleNo", id);
			    map.put("operId", userInfo.getUserName());
			    map.put("operDate", CommonUtil.getNowTime());
			    
			    map.put("hmtIdName", bd.getName(hs,"HotlineMotherboardType", "hmtName", "hmtId", cp.getHmtId()));
			    map.put("hmtId",  cp.getHmtId());
			    map.put("faultCode", cp.getFaultCode());
			    map.put("faultStatus", cp.getFaultStatus());
			    map.put("operTel", bd.getName(hs, "Loginuser", "phone", "userid", userInfo.getUserID()));
			    map.put("maintDivision", bd.getName(hs, "Company", "ComFullName", "ComID", userInfo.getComID()));
			    map.put("maintStation",  bd.getName(hs, "StorageID", "StorageName", "StorageID", userInfo.getStorageId()));
			    request.setAttribute("mapBean", map);	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toTechnologySupport");
		
	}
	
	/**
	 * ���漼������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toTechnologySupportRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		/*�����ļ�*/
		String folder="TechnologySupport.file.upload.folder";
		folder = PropertiesUtil.getProperty(folder).trim();//
		String singleNo = request.getParameter("singleNo");
		String operDate = request.getParameter("operDate");
		String faultStatus = request.getParameter("faultStatus");
		String faultCode = request.getParameter("faultCode");
		String hmtId = request.getParameter("hmtId");
		
		DynaActionForm dform = (DynaActionForm) form;
		List returnList=new ArrayList();
		FormFile formFile = null;
		Fileinfo file = null;
		if (form.getMultipartRequestHandler() != null) {
			Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			if (hash != null) {
				HandleFile hf = new HandleFileImpA();
				for(Iterator it = hash.keySet().iterator(); it.hasNext();){
					String key=(String)it.next();
					formFile=(FormFile)hash.get(key);
					Map map = new HashMap();
					if(formFile!=null){
						try {
							if(!formFile.getFileName().equals("")){
								String newFileName="TechnologySupport"+"_"+singleNo+"_"+key+"_"+formFile.getFileName();
								map.put("oldfilename", formFile.getFileName());
								map.put("filepath", CommonUtil.getNowTime("yyyy-MM-dd")+"/");
								map.put("fileSize", new Double(formFile.getFileSize()));
								map.put("fileformat",formFile.getContentType());
								map.put("newFileName", newFileName);
								//�����ļ���ϵͳ
								hf.createFile(formFile.getInputStream(), folder+"/"+CommonUtil.getNowTime("yyyy-MM-dd")+"/", newFileName);
								returnList.add(map);
							}else{
								continue;
							}
							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		
		/*�������ݵ����ݿ�*/
		Session hs = null;
		 Transaction tx = null;
		try {
			   hs = HibernateUtil.getSession();
			  
			   tx=hs.beginTransaction();
			   String year1=CommonUtil.getToday().substring(2,4);
			   String billno1 = CommonUtil.getBillno(year1,"TechnologySupport", 1)[0];	
			   TechnologySupport ts=new TechnologySupport();
//			   ����״̬	ProStatus	varchar(64)	64		FALSE	FALSE	FALSE
			   
			   ts.setSingleNo(singleNo);
			   ts.setBillno(billno1);
			   ts.setAssignUser(userInfo.getUserID());
			   ts.setOperDate(operDate);
			   ts.setAssignUserTel(bd.getName(hs, "Loginuser", "phone", "userid", userInfo.getUserID()));
			   ts.setMaintDivision(userInfo.getComID());
			   ts.setMaintStation(userInfo.getStorageId());
			   ts.setFaultCode(faultCode);
			   ts.setFaultStatus(faultStatus);
			   ts.setHmtId(hmtId);
//			   ����״̬	ProStatus	varchar(64)	64		FALSE	FALSE	FALSE
			   String sql="select s.parentstorageid from Storageid s where s.storageid='"+userInfo.getStorageId().trim()+"'";
			   List list=hs.createQuery(sql).list();
			   if(list!=null&&list.size()>0){
				   String parentstorageid=(String) list.get(0);
				   if(parentstorageid.trim().equals("0")||parentstorageid.trim()=="0"){
					   ts.setProStatus("2"); 
				   }else{
					   ts.setProStatus("1"); 
				   }
			   }
			   hs.save(ts);
			   hs.flush();
			   //
			   if(returnList!=null&&returnList.size()>0){
				   int len = returnList.size();
					for(int i = 0 ; i < len ; i++){
						Map map = (Map)returnList.get(i);
						TechnologySupportFiles fileInfo=new TechnologySupportFiles();
						String newFilename=(String) map.get("newFileName");
						fileInfo.setOldFileName((String)map.get("oldfilename"));
						fileInfo.setNewFileName((String)map.get("newFileName"));
						fileInfo.setBillno(billno1);
						fileInfo.setFileSize(Double.valueOf(map.get("fileSize").toString()));
						fileInfo.setFilePath((String)map.get("filepath"));
						fileInfo.setFileFormat((String)map.get("fileformat"));
						fileInfo.setUploadDate(CommonUtil.getNowTime());
						fileInfo.setUploader(userInfo.getUserID());
						hs.save(fileInfo);
					}  
			   }
			   tx.commit();
               request.setAttribute("calloutMasterNo", singleNo);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
		String isreturn = request.getParameter("isreturn");
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("accessoriesRequisition.success"));
				forward = mapping.findForward("returnModify");
			} else {
				// return modify page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("accessoriesRequisition.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				forward = mapping.findForward("returnTechnologySupport");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	
}
