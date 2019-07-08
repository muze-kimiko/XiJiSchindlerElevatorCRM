package com.gzunicorn.struts.action.hotlinemanagement.hotcalloutmaster;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.pdfprint.BarCodePrint;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.basedata.elevatorcoordinatelocation.ElevatorCoordinateLocation;
import com.gzunicorn.hibernate.basedata.hotlinefaulttype.HotlineFaultType;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutprocess.CalloutProcess;
import com.gzunicorn.hibernate.hotlinemanagement.calloutsms.CalloutSms;
import com.gzunicorn.hibernate.hotlinemanagement.smshistory.SmsHistory;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.struts.action.xjsgg.SmsService;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class hotCalloutMasterjx  extends DispatchAction{
	XjsggAction xj=new XjsggAction();
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
	 * ϵͳ����ĸ����
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		String name = request.getParameter("method");
		String typejsp = request.getParameter("typejsp");
		
		if(!"toDownloadFileRecord".equals(name) && !"toDisplayRecord".equals(name) 
				&& (typejsp!=null && !typejsp.equals("display")) ){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "hotphone", null);
			/** **********�����û�Ȩ�޹���*********** */
		}
		if ((typejsp==null || typejsp.equals("")) && (name == null || name.equals(""))) {
			name = "toSearchRecord";
			return dispatchMethod(mapping, form, request, response, name);
		} else {
			if (typejsp!=null && typejsp.equals("add")) {
				name = "toAddRecord";
				return dispatchMethod(mapping, form, request, response, name);
			}else if(typejsp!=null && (typejsp.equals("display")|| typejsp.equals("sh") || typejsp.equals("ps"))){
				name = "toDisplayRecord";
				return dispatchMethod(mapping, form, request, response, name);	
			}else if(typejsp!=null && typejsp.equals("mondity")){
				name = "toUpdateRecord";
				return dispatchMethod(mapping, form, request, response, name);	
			}else if(typejsp!=null && (typejsp.equals("shsave")||typejsp.equals("pssave"))){
				name = "tosaveshps";
				return dispatchMethod(mapping, form, request, response, name);	
			}else if(typejsp!=null &&  typejsp.equals("toSearchRecord")){
				name = "toSearchRecord";
				return dispatchMethod(mapping, form, request, response, name);	
			}				
			ActionForward forward = super.execute(mapping, form, request,response);
			return forward;
		}

	}
	
	
	/**
	 * ���޲�ѯ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("navigator.location","�������̹��� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();
        List list2=new ArrayList();
        List list3=new ArrayList();
        List PulldownList=null;
        List RepairModeList=null;
		List ServiceObjectsList=null;
        List storageidList=null;
		if (tableForm.getProperty("genReport") != null
				&& !tableForm.getProperty("genReport").equals("")) {
			try {
				response = toExcelRecord(form,request,response);
				forward = mapping.findForward("exportExcel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = mapping.findForward("exportExcel");
			tableForm.setProperty("genReport","");

		} else {
			HTMLTableCache cache = new HTMLTableCache(session, "CalloutMasterList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fCalloutMaster");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("calloutMasterNo");
			table.setIsAscending(false);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			}else if (action.equals("Submit")) {
				cache.loadForm(tableForm);
			}else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			String companyName = tableForm.getProperty("companyId");// ���޵�λ����
			if(companyName!=null && !"".equals(companyName)){
				companyName="%"+companyName.trim()+"%";
			}else{
				companyName="%";
			}
			String calloutMasterNo = tableForm.getProperty("calloutMasterNo");// ���ޱ��
			if(calloutMasterNo!=null && !"".equals(calloutMasterNo)){
				calloutMasterNo="%"+calloutMasterNo.trim()+"%";
			}else{
				calloutMasterNo="%";
			}
			String operId = tableForm.getProperty("operId");// �����
			if(operId!=null && !"".equals(operId)){
				operId="%"+operId.trim()+"%";
			}else{
				operId="%";
			}
			String handleStatus = tableForm.getProperty("handleStatus");// ����״̬
			if(handleStatus==null){
				handleStatus="%";
			}

			String submitType = tableForm.getProperty("SubmitType");// �ύ��־
			if(submitType!=null && !"".equals(submitType)){
				submitType="%"+submitType.trim()+"%";
			}else{
				submitType="%";
			}
			
			String maintStation = tableForm.getProperty("maintStation");// ά��վ
			if(maintStation!=null && !"".equals(maintStation)){
				maintStation="%"+maintStation.trim()+"%";
			}else{
				maintStation="%";
			}
			String repairMode = tableForm.getProperty("repairMode");// ���޷�ʽ
			if(repairMode!=null && !"".equals(repairMode)){
				repairMode="%"+repairMode.trim()+"%";
			}else{
				repairMode="%";
			}
			String serviceObjects = tableForm.getProperty("serviceObjects");// �������
			if(serviceObjects!=null && !"".equals(serviceObjects)){
				serviceObjects="%"+serviceObjects.trim()+"%";
			}else{
				serviceObjects="%";
			}

			String elevatorNo = tableForm.getProperty("elevatorNo");// ���ݱ��
			if(elevatorNo!=null && !"".equals(elevatorNo)){
				elevatorNo="%"+elevatorNo.trim()+"%";
			}else{
				elevatorNo="%";
			}
			String r5 = tableForm.getProperty("r5");//ά����Ա
			if(r5!=null && !"".equals(r5)){
				r5="%"+r5.trim()+"%";
			}else{
				r5="%";
			}
			
			String sdate1=(String) tableForm.getProperty("sdate1");
			String edate1=(String) tableForm.getProperty("edate1");
			if((sdate1==null || sdate1.trim().equals("")) 
					&& (edate1==null || edate1.trim().equals(""))){
				String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
				String day1=DateUtil.getDate(day, "MM", -2);//��ǰ�����·ݼ�1 ��
				//String day2=DateUtil.getDate(day, "MM", +1);//��ǰ�����·ݼ�1 ��
				sdate1=day1;
				edate1=day;
				tableForm.setProperty("sdate1", day1);
				tableForm.setProperty("edate1", day);
			}
			
			String orderby="";
			 if(table.getIsAscending()){
				 orderby=" order by  case when handleStatus='3' then '0' else '1' end,"+table.getSortColumn()+"";
             }else{
            	 orderby=" order by case when handleStatus='3' then '0' else '1' end,"+table.getSortColumn()+" desc";
             }
				
			Session hs = null;
			Connection con=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {

				hs = HibernateUtil.getSession();
				con=hs.connection();
				
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					maintStation=userInfo.getStorageId();
				}
				
				String comid=userInfo.getComID();
				if("00".equals(comid)){
					comid="%";
				}
				
				String sql="exec HL_callhotsearch_new ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
				ps=con.prepareStatement(sql);
				ps.setString(1, companyName);
				ps.setString(2, calloutMasterNo);
				ps.setString(3, operId);
				ps.setString(4, handleStatus);
				ps.setString(5, submitType);
				ps.setString(6, maintStation);
				ps.setString(7, repairMode);
				ps.setString(8, serviceObjects);
				ps.setString(9, orderby);
				ps.setString(10, "1");
				ps.setString(11, elevatorNo);
				ps.setString(12, comid);
				ps.setString(13, r5);
				ps.setString(14, sdate1);
				ps.setString(15, edate1);
				rs=ps.executeQuery();
				//System.out.println(">>>"+ps.toString());
				int i=0;
				int FirstResult=table.getFrom();
				int MaxResults=table.getFrom()+table.getLength();
               while(rs.next()){
            	   if(i>=FirstResult && i<MaxResults){
            		   HashMap hm=new HashMap();
            		   hm.put("calloutMasterNo", rs.getString(1));
            		   hm.put("companyName", rs.getString(2));
            		   hm.put("operName", rs.getString(3));
            		   String handle=rs.getString(4);
            		   hm.put("handleStatus", handle);       		   
            		   if(!handle.equals("5") && !handle.equals("6")){
            			   hm.put("handle", "Y");   
            		   }else{
            			   hm.put("handle", "N");   
            		   }        		   
            		   hm.put("handleStatusName", rs.getString("handleStatusname"));
            		   hm.put("isTrap", rs.getString(5).equals("Y")?"����":"������");            		              		        
            		   hm.put("maintStation", rs.getString(6));
            		   hm.put("repairMode", rs.getString(7));
            		   hm.put("serviceObjects", rs.getString(8));
            		   hm.put("completeTime", rs.getString(9));
            		   String isStop=String.valueOf(rs.getString(10));
            		   if(isStop!=null && "Y".equals(isStop)){
            			   hm.put("isStop","ͣ��" );
            		   }else{
            			   hm.put("isStop", isStop.equals("N")?"��ͣ��":"");  
            		   }
            		   hm.put("submitType", rs.getString(11).equals("Y")?"���ύ":"δ�ύ");
            		   hm.put("operDate", rs.getString(12));
            		   hm.put("elevatorNo", rs.getString("ElevatorNo"));

      	           	   /**
            		   String r5id=rs.getString("r5");//���޲�����Ա
      	           	   String r5name="";
      	           	   if(r5id!=null && !r5id.trim().equals("")){
      	           		 String sqls="select a from Loginuser a where a.userid in('"+r5id.replaceAll(",", "','")+"')";
      	           		 List loginlist=hs.createQuery(sqls).list();
      	           		 if(loginlist!=null && loginlist.size()>0){
      	           			 for(int l=0;l<loginlist.size();l++){
      	           				 Loginuser login=(Loginuser)loginlist.get(l);
      	           				 if(l==loginlist.size()-1){
      	           					 r5name+=login.getUsername();
      	           				 }else{
      	           					 r5name+=login.getUsername()+",";
      	           				 }
      	           			 }
      	           		 }
      	           	 }
      	           	 */
      	           	 hm.put("r5name", rs.getString("r5name"));//ά����Ա
            		   
            		 list2.add(hm);  
            	   }
            	   i++;
               }
               
               table.setVolume(i);// ��ѯ�ó����ݼ�¼��;
			   cache.check(table);
			    list3.addAll(list2);				
				table.addAll(list3);

				//����״̬
				PulldownList=hs.createQuery("select a from Pulldown a where "
						+ "a.id.typeflag='CalloutMaster_HandleStatus' and a.enabledflag='Y' order by orderby").list();
				//���޷�ʽ
				RepairModeList=hs.createQuery("select a from Pulldown a where "
						+ "a.id.typeflag='CalloutMaster_RepairMode' and a.enabledflag='Y' order by orderby").list();
				//�������
				ServiceObjectsList=hs.createQuery("select a from Pulldown a where "
						+ "a.id.typeflag='CalloutMaster_ServiceObjects' and a.enabledflag='Y' order by orderby").list();
				//ά��վ
				storageidList=bd.getMaintStationList(userInfo);
				
				request.setAttribute("rmList", RepairModeList);
				request.setAttribute("soList", ServiceObjectsList);
				session.setAttribute("CalloutMasterList", table);
				request.setAttribute("PulldownList", PulldownList);
				request.setAttribute("storageidList", storageidList);
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
			forward = mapping.findForward("toList");
		}
		return forward;
}
	
	/**
	 * �½��������������õķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toPrepareAddRecord(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		request.setAttribute("navigator.location","�������̹��� >> �½�");
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session=request.getSession();
		List RepairModeList=null;
		List ServiceObjectsList=null;
		 List storageidList=null;
		Session hs=null;
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		CalloutMaster cm=new CalloutMaster();
		cm.setOperId(userInfo.getUserName());
		cm.setRepairTime(xj.getdatetime());
		cm.setServiceObjects(String.valueOf(request.getAttribute("serviceObjects")));
		request.setAttribute("CalloutMasterList", cm);
		try {
			hs=HibernateUtil.getSession();
			//���޷�ʽ
			RepairModeList=hs.createQuery("select a from Pulldown a where "
					+ "a.id.typeflag='CalloutMaster_RepairMode' and a.enabledflag='Y' order by orderby").list();
			//�������
			ServiceObjectsList=hs.createQuery("select a from Pulldown a where "
					+ "a.id.typeflag='CalloutMaster_ServiceObjects' and a.enabledflag='Y' order by orderby").list();
			
			//ά��վ
			storageidList=bd.getMaintStationList(userInfo.getComID());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("rmList", RepairModeList);
		request.setAttribute("soList", ServiceObjectsList);
		request.setAttribute("storageidList", storageidList);
        request.setAttribute("typejsp", "add");
		return mapping.findForward("tohotphone");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public ActionForward toAddRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);		
		Session hs = null;
		Transaction tx = null;
		CalloutMaster cm = null;
		CalloutSms cs=null;
		SmsHistory sh=null;
		String isreturn=request.getParameter("isreturn");
		String calloutMasterNo="";//���޵���
		String serviceObjects="";
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			cm = new CalloutMaster();	
			String datetime = request.getParameter("repairTime");//����ʱ��
			String repairMode = request.getParameter("repairMode");//���޷�ʽ
			String repairUser = request.getParameter("repairUser");//������
			String repairTel = request.getParameter("repairTel");//���޵绰
			if(repairUser==null){repairUser="";};
			if(repairTel==null){repairTel="";};
			serviceObjects = request.getParameter("serviceObjects");//�������
			String companyId = request.getParameter("companyId");//���޵�λ
			String projectAddress = request.getParameter("projectAddress");//��Ŀ��ַ
			String salesContractNo = request.getParameter("salesContractNo");//���ۺ�ͬ��
			String elevatorNo = request.getParameter("elevatorNo");//���ݱ��
			String elevatorParam = request.getParameter("elevatorParam");//����ͺ�
			//String maintStation = request.getParameter("maintStation");//ά��վ
			String maintStation = request.getParameter("assignMaintStation");//ά��վ
			String assignObject = request.getParameter("assignObject");//�ɹ�����
			String phone = request.getParameter("phone");//�ɹ��绰
			String isTrap = request.getParameter("isTrap");//�Ƿ�����
			String repairDesc = request.getParameter("repairDesc");//��������
			String isSendSms2=request.getParameter("isSendSms2");
			String projectName=request.getParameter("projectName");
			
			calloutMasterNo=XjsggAction.genCalloutMasterNum();//�½����޵���
            cm.setCalloutMasterNo(calloutMasterNo);
            cm.setOperId(userInfo.getUserID());
            cm.setOperDate(datetime);
            cm.setRepairTime(datetime);
            cm.setRepairMode(repairMode);
            cm.setRepairUser(repairUser);
            cm.setRepairTel(repairTel);
            cm.setServiceObjects(serviceObjects);
            cm.setCompanyId(companyId);
            cm.setProjectAddress(projectAddress);
            cm.setSalesContractNo(salesContractNo);
            cm.setElevatorNo(elevatorNo);
            cm.setElevatorParam(elevatorParam);
            cm.setMaintStation(maintStation);
            cm.setAssignObject(assignObject);
            cm.setPhone(phone);
            cm.setIsTrap(isTrap);
            cm.setRepairDesc(repairDesc);
            cm.setIsSendSms2(isSendSms2);
            cm.setProjectName(projectName);
            
            if("Y".equals(isreturn)){
            	cm.setSubmitType("Y");
            	if(assignObject==null || "".equals(assignObject)){
            		cm.setHandleStatus("6");//����״̬	
	            }else{
	            	
	            	/****************************���Ͷ��Ÿ�ά���� ��ʼ**********************************/
	            	System.out.println(">>>���Ͷ��Ÿ�ά����");
	            	String istraptext="������";
	            	if(isTrap!=null && isTrap.equals("Y")){
	            		istraptext="����";
	            	}
	            	String smsmes="���������"+istraptext+"�����ݱ�ţ�"+elevatorNo+"����Ŀ���Ƽ�¥���ţ�"+projectAddress+"���������ĵ����й��ϣ��뼰ʱ���� [����Ѹ��]";
	            	boolean issms=SmsService.sendSMS(istraptext,elevatorNo,projectAddress,phone);
	            	
	            	//������ʷ��
					sh=new SmsHistory();
					sh.setSmsContent(smsmes);
					sh.setSmsSendTime(CommonUtil.getNowTime());
					sh.setSmsTel(phone);
					if(issms){
						sh.setFlag(1);
					}else{
						sh.setFlag(0);	
					}
					hs.save(sh);
					hs.flush();
					/****************************���Ͷ��Ÿ�ά���� ����**********************************/
	
	            	cm.setHandleStatus("");//����״̬
	            	String username=bd.getName("Loginuser", "username", "userid", assignObject);
		            /****************************���Ͷ��ſ�ʼ**********************************/	
		            if(isSendSms2!=null && "Y".equals(isSendSms2)){
		            	String pattern = "^(13[0-9]|15[01]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$";
		            	if(repairTel!=null && repairTel.matches(pattern)){
							//String smsContent="����Ѹ�����: ����,�������� ά����"+username+",�绰"+phone+" ���ֳ��ṩ���޷����������ĵȴ���";
							String smsContent="�𾴵��û��������Ѿ��յ����ı��޲�֪ͨ��ά����Ա["+username+","+phone+"]�����ܿ�ͻᵽ���ֳ����������ĵȴ�����Ҫ����Ƕ������š� ";
							String telNo=cm.getRepairTel();
							
							System.out.println(">>>���Ͱ�������");
							boolean iscg=true;
							//boolean iscg=SmsService.sendSMS(smsContent, telNo);
							//boolean iscg=XjsggAction.sendMessage(smsContent, telNo);
							
							//���뼱�޶��ű�
							String time=xj.getdatetime();
							cs=new CalloutSms();
							cs.setCalloutMasterNo(calloutMasterNo);
							cs.setSmsTel(telNo);//�����绰
							cs.setSmsSendTime(time);//�������Ͷ���ʱ��
							cs.setSmsContent(smsContent);//������������
							hs.save(cs);
							hs.flush();
							
							//������ʷ��
							sh=new SmsHistory();
							sh.setSmsContent(smsContent);
							sh.setSmsSendTime(time);
							sh.setSmsTel(telNo);
							if(iscg){
								sh.setFlag(1);
							}else{
								sh.setFlag(0);	
							}
							hs.save(sh);
							hs.flush();
		            	}else{
		            		//System.out.println("���޵绰�����ֻ����룬���ܷ�����Ϣ��");
		            		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","���޵绰�����ֻ����룬���ܷ�����Ϣ��"));
		            	}
		            }
		            /***********************���Ͷ��Ž���************************************/										
		
		         }
            }else if("N".equals(isreturn)){
	            cm.setSubmitType("N");
	            cm.setHandleStatus("");//����״̬
            }
            hs.save(cm);
            hs.flush();
            
            CalloutProcess cp=new CalloutProcess();
            cp.setCalloutMasterNo(calloutMasterNo);
            cp.setAssignObject2(assignObject);
            hs.save(cp);
            
			tx.commit();		
		} catch (Exception e1) {
			tx.rollback();
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate region Insert error!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ʧ�ܣ�"));
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = null;
		try {				
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"insert.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
							
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
		if("Y".equals(isreturn)){
			forward = mapping.findForward("returnList");
		}else if("N".equals(isreturn)){
			request.setAttribute("serviceObjects", serviceObjects);
			forward = mapping.findForward("returnAdd");
		}
		return forward;
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
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id = (String) dform.get("id");
		if(id==null && "".equals(id)){
			id=request.getParameter("id");
		}
		String typejsp = request.getParameter("typejsp");
		
		//�鿴������ʾ�رհ�ť
		String isopenshow = request.getParameter("isopenshow");
		if(isopenshow!=null && isopenshow.equals("Yes")){
			request.setAttribute("isopenshow", isopenshow);
		}
		HashMap hm=new HashMap();
		Session hs = null;
		CalloutMaster cm=null;
		CalloutProcess cp=null;
		CalloutSms cs=null;
		CalloutSms sms=null;
		String CSheight = PropertiesUtil.getProperty("CSheight");
		String CSwidth = PropertiesUtil.getProperty("CSwidth");
		String CIheight = PropertiesUtil.getProperty("CIheight");
		String CIwidth = PropertiesUtil.getProperty("CIwidth");
	   if (id != null) {				
		try {
			hs = HibernateUtil.getSession();
			cm=(CalloutMaster)hs.get(CalloutMaster.class, id);
			String sqlsel="select "
					+ "l.username as username1,"
					+ "p.pullname as pullname,"
					+ "p1.pullname as pullname1,"
					+ "c.companyName as companyName,"
					+ "s.storagename as storagename,"
					+ "l2.username as username2,"
					+ "e.rem as rem,"
					+ "l3.username as username3,"
					+ "h.hfcName as hfcName,"
					+ "l4.username as username4,"
					+ "l5.username as username5 "
					+ "from CalloutMaster a "
					+ "left join Loginuser l on l.userid=a.operId "
					+ "left join Pulldown p on p.typeflag='CalloutMaster_RepairMode' and p.pullid=a.repairMode "
					+ "left join Pulldown p1 on p1.typeflag='CalloutMaster_ServiceObjects' and p1.pullid=a.serviceObjects "
					+ "left join Customer c on c.companyId=a.companyId "
					+ "left join Storageid s on s.storageid=a.maintStation "
					+ "left join Loginuser l2 on l2.userid=a.assignObject "
					+ "left join ElevatorCoordinateLocation e on e.elevatorNo=a.elevatorNo "
					+ "left join Loginuser l3 on l3.userid=a.auditOperid "
					+ "left join HotlineFaultClassification h on h.hfcId=a.hfcId "
					+ "left join Loginuser l4 on l4.userid=a.smAuditOperid "
					+ "left join Loginuser l5 on l5.userid=a.stophfOperid "
					+ "where a.calloutMasterNo='"+id.trim()+"' ";
			System.out.println(sqlsel);
			
			List cmlist=hs.createSQLQuery(sqlsel).list();
			Object[] cmobj=(Object[])cmlist.get(0);

			cm.setOperId((String)cmobj[0]);
			if(!"sh".equals(typejsp)){
				cm.setRepairMode((String)cmobj[1]);//���޷�ʽ
				cm.setServiceObjects((String)cmobj[2]);//�������
				cm.setIsTrap(String.valueOf(cm.getIsTrap()).equals("Y")?"����":"������");
			}
			String companyName=(String)cmobj[3];
			if(companyName==null || "".equals(companyName) || "NULL".equals(companyName)){
				companyName=cm.getCompanyId();
			}
			cm.setCompanyId(companyName);
			cm.setMaintStation((String)cmobj[4]);
			cm.setAssignObject((String)cmobj[5]);
			cm.setProjectAddress((String)cmobj[6]);//��Ŀ���Ƽ�¥����
			
			
			if(typejsp!=null && "sh".equals(typejsp)){
				cm.setAuditOperid(userInfo.getUserName());
				cm.setAuditDate(xj.getdatetime());
				request.setAttribute("navigator.location","�������̹��� >> �������");
				List HotlineFaultClassificationList=xj.getClasses(hs, "HotlineFaultClassification", "enabledFlag", "Y");
				request.setAttribute("HotlineFaultClassificationList", HotlineFaultClassificationList);
				request.setAttribute("rmList", bd.getPullDownList("CalloutMaster_RepairMode"));
				request.setAttribute("soList", bd.getPullDownList("CalloutMaster_ServiceObjects"));
			}else if(typejsp!=null && "ps".equals(typejsp)){
				request.setAttribute("navigator.location","�������̹��� >> �ط����");
				cm.setAuditOperid((String)cmobj[7]);
				cm.setHfcId((String)cmobj[8]);//���Ϸ���
			}else{
				typejsp="display";
				cm.setAuditOperid((String)cmobj[7]);
				request.setAttribute("navigator.location","�������̹��� >> �鿴");
				cm.setHfcId((String)cmobj[8]);//���Ϸ���
			}

			cm.setSmAuditOperid((String)cmobj[9]);//��ȫ�������
			cm.setStophfOperid((String)cmobj[10]);//ͣ�ݻط���
			
			String IsSendSms=cm.getIsSendSms();
			if(IsSendSms!=null && !"".equals(IsSendSms)){
				cm.setIsSendSms(String.valueOf(cm.getIsSendSms()).equals("Y")?"�ѷ���":"δ����");
			}else{
				cm.setIsSendSms("");
			}	
			String isSubSM=cm.getIsSubSM();
			if(isSubSM!=null && !"".equals(isSubSM)){
				cm.setIsSubSM(String.valueOf(cm.getIsSubSM()).equals("Y")?"��":"��");
			}else{
				cm.setIsSubSM("");
			}
			String ServiceAppraisal=cm.getServiceAppraisal();
			if(ServiceAppraisal!=null && !"".equals(ServiceAppraisal)){
				switch(Integer.valueOf(ServiceAppraisal).intValue()){
				case 1:cm.setServiceAppraisal("�ǳ�����");break;
				case 2:cm.setServiceAppraisal("����");break;
				case 3:cm.setServiceAppraisal("һ��");break;
				case 4:cm.setServiceAppraisal("������");break;
				case 5:cm.setServiceAppraisal("�ǳ�������");break;
				}
			}
			String FittingSituation=cm.getFittingSituation();
			if(FittingSituation!=null && !"".equals(FittingSituation)){
				cm.setFittingSituation(String.valueOf(cm.getFittingSituation()).equals("1")?"��ʵ":"����ʵ");
			}else{
				cm.setFittingSituation("");
			}
			String TollSituation=cm.getTollSituation();
			if(TollSituation!=null && !"".equals(TollSituation)){
				cm.setTollSituation(String.valueOf(cm.getTollSituation()).equals("1")?"��ʵ":"����ʵ");
			}else{
				cm.setTollSituation("");
			}
			String IsColse=cm.getIsColse();
			if(IsColse!=null && !"".equals(IsColse)){
				cm.setIsColse(String.valueOf(cm.getIsColse()).equals("1")?"�ر�":"���ر�");
			}else{
				cm.setIsColse("");
			}

			cp=(CalloutProcess)hs.get(CalloutProcess.class, id);
			String sqlpro="select "
					+ "lu.username as username,"
					+ "lu2.username as username2,"
					+ "h.hmtName as hmtName "
					+ "from CalloutProcess p "
					+ "left join Loginuser lu on lu.userid=p.turnSendId "
					+ "left join Loginuser lu2 on lu2.userid=p.assignUser "
					+ "left join HotlineMotherboardType h on h.hmtId=p.hmtId "
					+ "where p.calloutMasterNo='"+id.trim()+"' ";
			List cplist=hs.createSQLQuery(sqlpro).list();
			
			if(cp!=null){
				Object[] objpro=(Object[])cplist.get(0);
				
				cp.setTurnSendId((String)objpro[0]);
				cp.setAssignUser((String)objpro[1]);
				String IsStop=cp.getIsStop();
				if(!"sh".equals(typejsp)){
					if(IsStop!=null && !"".equals(IsStop)){
						cp.setIsStop(IsStop.equals("Y")?"ͣ��":"��ͣ��");
					}else{
						cp.setIsStop("");	
					}
					
					String hftid=cp.getHftId();//��������
		           	 String hftdesc="";
		           	 if(hftid!=null && !hftid.trim().equals("")){
		           		 String sqls="select a from HotlineFaultType a where a.hftId in('"+hftid.replaceAll(",", "','")+"')";
		           		 List loginlist=hs.createQuery(sqls).list();
		           		 if(loginlist!=null && loginlist.size()>0){
		           			 for(int l=0;l<loginlist.size();l++){
		           				HotlineFaultType hft=(HotlineFaultType)loginlist.get(l);
		           				 if(l==loginlist.size()-1){
		           					hftdesc+=hft.getHftDesc();
		           				 }else{
		           					hftdesc+=hft.getHftDesc()+",";
		           				 }
		           			 }
		           		 }
		           	 }
		           	cp.setHftId(hftdesc);
					
					cp.setHmtId((String)objpro[2]);//��������
				}else{
					String hftid=cp.getHftId();//���޲�����Ա
		           	 String hftdesc="";
		           	 if(hftid!=null && !hftid.trim().equals("")){
		           		 String sqls="select a from HotlineFaultType a where a.hftId in('"+hftid.replaceAll(",", "','")+"')";
		           		 List loginlist=hs.createQuery(sqls).list();
		           		 if(loginlist!=null && loginlist.size()>0){
		           			 for(int l=0;l<loginlist.size();l++){
		           				HotlineFaultType hft=(HotlineFaultType)loginlist.get(l);
		           				 if(l==loginlist.size()-1){
		           					hftdesc+=hft.getHftDesc();
		           				 }else{
		           					hftdesc+=hft.getHftDesc()+",";
		           				 }
		           			 }
		           		 }
		           	 }
		           	request.setAttribute("hftId", hftdesc);
					
					request.setAttribute("hmtId", (String)objpro[2]);
				}
				
				
				String IsToll=cp.getIsToll();
				if(IsToll!=null && !"".equals(IsToll)){
					cp.setIsToll(String.valueOf(cp.getIsToll()).equals("Y")?"�շ�":"���շ�");
				}else{
					cp.setIsToll("");
				} 
				
				//Double ArriveLongitude=cp.getArriveLongitude();
				//Double ArriveLatitude=cp.getArriveLatitude();
				//Double FninishLongitude=cp.getFninishLongitude();
				//Double FninishLatitude=cp.getFninishLatitude();
				/**
				String r5=cp.getR5();//���޲�����Ա
	           	 String r5name="";
	           	 if(r5!=null && !r5.trim().equals("")){
	           		 String sqls="select a from Loginuser a where a.userid in('"+r5.replaceAll(",", "','")+"')";
	           		 List loginlist=hs.createQuery(sqls).list();
	           		 if(loginlist!=null && loginlist.size()>0){
	           			 for(int l=0;l<loginlist.size();l++){
	           				 Loginuser login=(Loginuser)loginlist.get(l);
	           				 if(l==loginlist.size()-1){
	           					 r5name+=login.getUsername();
	           				 }else{
	           					 r5name+=login.getUsername()+",";
	           				 }
	           			 }
	           		 }
	           	 }
	           	 */
	           	hm.put("r5name",cp.getR5());//���޲�����Ա

				hm.put("elevatorLocation",cp.getArriveLocation());
				hm.put("elevatorLocation2", cp.getFninishLocation());
			}
			cs=(CalloutSms)hs.get(CalloutSms.class, id);
			if(cs!=null){
			String ServiceRating=cs.getServiceRating();//�û�����
			if(ServiceRating!=null && !"".equals(ServiceRating)){
				switch(Integer.valueOf(ServiceRating).intValue()){
				case 1:cs.setServiceRating("�ǳ�����");break;
				case 2:cs.setServiceRating("����");break;
				case 3:cs.setServiceRating("һ��");break;
				case 4:cs.setServiceRating("������");break;
				case 5:cs.setServiceRating("�ǳ�������");break;
				}
			}
			}
			sms=(CalloutSms) hs.get(CalloutSms.class, cm.getCalloutMasterNo());
			
			Query queryfiles = hs.createQuery("from CalloutFileinfo ");
			List listfiles = queryfiles.list();
			request.setAttribute("CalloutFileinfo", listfiles);
			
		
		} catch (DataStoreException e) {
				e.printStackTrace();
			}				
		
	}else{
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
	}

	//ά��վ
	List storageidList=bd.getMaintStationList(userInfo.getComID());
	request.setAttribute("storageidList", storageidList);
	
    request.setAttribute("CalloutMasterList", cm);
    request.setAttribute("CalloutProcessList", cp);
    request.setAttribute("CalloutSmsList", cs);
    request.setAttribute("hashmapbean", hm);
    request.setAttribute("calloutSmsBean", sms);
	request.setAttribute("typejsp",typejsp);
	request.setAttribute("CSheight",CSheight);
	request.setAttribute("CSwidth", CSwidth);
	request.setAttribute("CIheight", CIheight);
	request.setAttribute("CIwidth", CIwidth);
	if (!errors.isEmpty()) {
		saveErrors(request, errors);
	}
		forward = mapping.findForward("tohotphone");
		return forward;
	}
	
	/**
	 * ��ת���޸ļ���ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPrepareUpdateRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","�������̹��� >> �޸�");	
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session=request.getSession();		
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		String id = (String) dform.get("id");
		if(id==null && "".equals(id)){
			id=request.getParameter("id");
		}
		String error = (String) request.getAttribute("error");
		Session hs = null;
		CalloutMaster cm=null;
		List RepairModeList=null;
		List ServiceObjectsList=null;
		List storageidList=null;
		HashMap hm=null;
		if (id != null) {
				try {
					hs = HibernateUtil.getSession();
					cm=(CalloutMaster)hs.get(CalloutMaster.class, id);
					cm.setOperId(userInfo.getUserName());
					cm.setRepairTime(xj.getdatetime());
					cm.setProjectAddress(bd.getName("ElevatorCoordinateLocation", "rem", "elevatorNo", cm.getElevatorNo()));//��Ŀ���Ƽ�¥����
					
					hm=new HashMap();
					String companyName=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
					if(companyName==null || "".equals(companyName)){
						companyName=cm.getCompanyId();
					}
					hm.put("companyName", companyName);//���޵�λ
					hm.put("storageName", bd.getName("Storageid", "storagename", "storageid", cm.getMaintStation()));//ά��վ
					//���޷�ʽ
					RepairModeList=hs.createQuery("select a from Pulldown a where "
							+ "a.id.typeflag='CalloutMaster_RepairMode' and a.enabledflag='Y' order by orderby").list();
					//�������
					ServiceObjectsList=hs.createQuery("select a from Pulldown a where "
							+ "a.id.typeflag='CalloutMaster_ServiceObjects' and a.enabledflag='Y' order by orderby").list();
					//ά��վ
					storageidList=bd.getMaintStationList(userInfo.getComID());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
		}else{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
		}
        request.setAttribute("CalloutMasterList",cm);
        request.setAttribute("hashmapbean", hm);
        request.setAttribute("rmList", RepairModeList);
		request.setAttribute("soList", ServiceObjectsList);
		request.setAttribute("storageidList", storageidList);
		request.setAttribute("typejsp", "mondity");
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		forward = mapping.findForward("tohotphone");
		return forward;
	}
	
	/**
	 * ���������޸�
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
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs = null;
		Transaction tx = null;
		CalloutMaster cm = null;
		CalloutSms cs=null;
		SmsHistory sh=null;
		String calloutMasterNo=request.getParameter("calloutMasterNo");
		String isreturn=request.getParameter("isreturn");
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();				
			String datetime = request.getParameter("repairTime");//����ʱ��
			String repairMode = request.getParameter("repairMode");//���޷�ʽ
			String repairUser = request.getParameter("repairUser");//������
			String repairTel = request.getParameter("repairTel");//���޵绰
			String serviceObjects = request.getParameter("serviceObjects");//�������
			String companyId = request.getParameter("companyId");//���޵�λ
			String projectAddress = request.getParameter("projectAddress");//��Ŀ��ַ
			String salesContractNo = request.getParameter("salesContractNo");//���ۺ�ͬ��
			String elevatorNo = request.getParameter("elevatorNo");//���ݱ��
			String elevatorParam = request.getParameter("elevatorParam");//����ͺ�
			//String maintStation = request.getParameter("maintStation");//ά��վ
			String maintStation = request.getParameter("assignMaintStation");//ά��վ
			String assignObject = request.getParameter("assignObject");//�ɹ�����
			String phone = request.getParameter("phone");//�ɹ��绰
			String isTrap = request.getParameter("isTrap");//�Ƿ�����
			String repairDesc = request.getParameter("repairDesc");//��������
			String isSendSms2=request.getParameter("isSendSms2");
			
			cm=(CalloutMaster)hs.get(CalloutMaster.class, calloutMasterNo);
            cm.setOperId(userInfo.getUserID());
            cm.setOperDate(datetime);
            cm.setRepairTime(datetime);
            cm.setRepairMode(repairMode);
            cm.setRepairUser(repairUser);
            cm.setRepairTel(repairTel);
            cm.setServiceObjects(serviceObjects);
            cm.setCompanyId(companyId);
            cm.setProjectAddress(projectAddress);
            cm.setSalesContractNo(salesContractNo);
            cm.setElevatorNo(elevatorNo);
            cm.setElevatorParam(elevatorParam);
            cm.setMaintStation(maintStation);
            cm.setAssignObject(assignObject);
            cm.setPhone(phone);
            cm.setIsTrap(isTrap);
            cm.setRepairDesc(repairDesc);
            
            if("Y".equals(isreturn)){
            	cm.setSubmitType("Y");
	            if(assignObject==null ||"".equals(assignObject)){
	                cm.setHandleStatus("6");//����״̬	
		        }else{
		        	/****************************���Ͷ��Ÿ�ά���� ��ʼ**********************************/
	            	System.out.println(">>>���Ͷ��Ÿ�ά����");
	            	String istraptext="������";
	            	if(isTrap!=null && isTrap.equals("Y")){
	            		istraptext="����";
	            	}
	            	String smsmes="���������"+istraptext+"�����ݱ�ţ�"+elevatorNo+"����Ŀ���Ƽ�¥���ţ�"+projectAddress+"���������ĵ����й��ϣ��뼰ʱ���� [����Ѹ��]";
	            	boolean issms=SmsService.sendSMS(istraptext,elevatorNo,projectAddress,phone);
	            	
	            	//������ʷ��
					sh=new SmsHistory();
					sh.setSmsContent(smsmes);
					sh.setSmsSendTime(CommonUtil.getNowTime());
					sh.setSmsTel(phone);
					if(issms){
						sh.setFlag(1);
					}else{
						sh.setFlag(0);	
					}
					hs.save(sh);
					hs.flush();
					/****************************���Ͷ��Ÿ�ά���� ����**********************************/
	            	
	                cm.setHandleStatus("");//����״̬
	                String username=bd.getName("Loginuser", "username", "userid", assignObject);
	                /***************���Ͷ��ſ�ʼ***********************/
	                if(isSendSms2!=null && "Y".equals(isSendSms2)){
	                	String pattern = "^(13[0-9]|15[01]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$";
	                	if(repairTel!=null && repairTel.matches(pattern)){
			    			//String smsContent="����Ѹ�����: ����,�������� ά����"+username+",�绰"+phone+" ���ֳ��ṩ���޷����������ĵȴ���";
							String smsContent="�𾴵��û��������Ѿ��յ����ı��޲�֪ͨ��ά����Ա["+username+","+phone+"]�����ܿ�ͻᵽ���ֳ����������ĵȴ�����Ҫ����Ƕ������š� ";
			    			String telNo=cm.getRepairTel();
			    			
			    			System.out.println(">>>���Ͱ�������");
			    			boolean iscg=true;
			    			//boolean iscg=SmsService.sendSMS(smsContent, telNo);
			    			//boolean iscg=XjsggAction.sendMessage(smsContent, telNo);
			    			
			    			//���뼱�޶��ű�
			    			String time=xj.getdatetime();
			    			cs=new CalloutSms();
			    			cs.setCalloutMasterNo(calloutMasterNo);
			    			cs.setSmsTel(telNo);//�����绰
			    			cs.setSmsSendTime(time);//�������Ͷ���ʱ��
			    			cs.setSmsContent(smsContent);//������������
			    			hs.save(cs);
			    			hs.flush();
			    			
			    			//������ʷ��
			    			sh=new SmsHistory();
			    			sh.setSmsContent(smsContent);
			    			sh.setSmsSendTime(time);
			    			sh.setSmsTel(telNo);
			    			if(iscg){
			    				sh.setFlag(1);
			    			}else{
			    				sh.setFlag(0);	
			    			}
			    			hs.save(sh);
			    			hs.flush();
			    			/********************���Ͷ��Ž���****************************************/
	                	}else{
	                		//System.out.println("���޵绰�����ֻ����룬���ܷ�����Ϣ��");
	                		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","���޵绰�����ֻ����룬���ܷ�����Ϣ��"));
	                	}
	                }
	
	            }
            }else if("N".equals(isreturn)){
	            cm.setSubmitType("N");
	            cm.setHandleStatus("");//����״̬
            }
            hs.save(cm);			
            hs.flush();
            
            CalloutProcess cp=(CalloutProcess)hs.get(CalloutProcess.class, calloutMasterNo);
            cp.setCalloutMasterNo(calloutMasterNo);
            cp.setAssignObject2(assignObject);
            hs.save(cp);
			tx.commit();
		} catch (Exception e1) {
			tx.rollback();
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate region Insert error!");
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.string","<font color='red'>����ʧ�ܣ�</font>"));
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"error.string","<font color='red'>�����쳣��</font>"));
			}
		}

		ActionForward forward = null;
		try {
			
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"insert.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		if("Y".equals(isreturn)){
			forward = mapping.findForward("returnList");
		}else if("N".equals(isreturn)){
			dform.set("id", calloutMasterNo);
			forward = mapping.findForward("returnMondity");
		}
		return forward;
	}

	/**
	 * ɾ����������
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
		ActionErrors errors = new ActionErrors();
		Session hs = null;
		Transaction tx = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();			
			String id = request.getParameter("id");			
			CalloutProcess cp = (CalloutProcess) hs.get(CalloutProcess.class, id);
			if (cp != null) {
                hs.delete(cp);										
			}
			CalloutMaster cm = (CalloutMaster) hs.get(CalloutMaster.class, id);
			if (cm != null) {
                hs.delete(cm);														
			}
			tx.commit();
		} catch (HibernateException e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"delete.foreignkeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Update error!");
		} catch (DataStoreException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate region Update error!");

		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));
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
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;

		String companyName = tableForm.getProperty("companyId");// ���޵�λ����
		if(companyName!=null && !"".equals(companyName)){
			companyName="%"+companyName+"%";
		}else{
			companyName="%";
		}
		String calloutMasterNo = tableForm.getProperty("calloutMasterNo");// ���ޱ��
		if(calloutMasterNo!=null && !"".equals(calloutMasterNo)){
			calloutMasterNo="%"+calloutMasterNo+"%";
		}else{
			calloutMasterNo="%";
		}
		String operId = tableForm.getProperty("operId");// �����
		if(operId!=null && !"".equals(operId)){
			operId="%"+operId+"%";
		}else{
			operId="%";
		}
		String handleStatus = tableForm.getProperty("handleStatus");// ����״̬
		if(handleStatus==null){
			handleStatus="%";
		}

		String submitType = tableForm.getProperty("SubmitType");// �ύ��־
		if(submitType!=null && !"".equals(submitType)){
			submitType="%"+submitType+"%";
		}else{
			submitType="%";
		}
		String maintStation = tableForm.getProperty("maintStation");// ά��վ
		if(maintStation!=null && !"".equals(maintStation)){
			maintStation="%"+maintStation+"%";
		}else{
			maintStation="%";
		}
		String repairMode = tableForm.getProperty("repairMode");// ���޷�ʽ
		if(repairMode!=null && !"".equals(repairMode)){
			repairMode="%"+repairMode+"%";
		}else{
			repairMode="%";
		}
		String serviceObjects = tableForm.getProperty("serviceObjects");// �������
		if(serviceObjects!=null && !"".equals(serviceObjects)){
			serviceObjects="%"+serviceObjects+"%";
		}else{
			serviceObjects="%";
		}
		
		String elevatorNo = tableForm.getProperty("elevatorNo");// ���ݱ��
		if(elevatorNo!=null && !"".equals(elevatorNo)){
			elevatorNo="%"+elevatorNo.trim()+"%";
		}else{
			elevatorNo="%";
		}
		String r5 = tableForm.getProperty("r5");//ά����Ա
		if(r5!=null && !"".equals(r5)){
			r5="%"+r5.trim()+"%";
		}else{
			r5="%";
		}
		String sdate1=(String) tableForm.getProperty("sdate1");
		String edate1=(String) tableForm.getProperty("edate1");
		if((sdate1==null || sdate1.trim().equals("")) 
				&& (edate1==null || edate1.trim().equals(""))){
			String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			String day1=DateUtil.getDate(day, "MM", -2);//��ǰ�����·ݼ�1 ��
			//String day2=DateUtil.getDate(day, "MM", +1);//��ǰ�����·ݼ�1 ��
			sdate1=day1;
			edate1=day;
		}
		
		String orderby="order by calloutMasterNo desc";
		 
		Session hs = null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List list=new ArrayList();
		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			hs = HibernateUtil.getSession();
			
			String comid=userInfo.getComID();
			if("00".equals(comid)){
				comid="%";
			}
			
			String sql="exec HL_callhotsearch_new ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
			con=hs.connection();
			ps=con.prepareStatement(sql);
			ps.setString(1, companyName);
			ps.setString(2, calloutMasterNo);
			ps.setString(3, operId);
			ps.setString(4, handleStatus);
			ps.setString(5, submitType);
			ps.setString(6, maintStation);
			ps.setString(7, repairMode);
			ps.setString(8, serviceObjects);
			ps.setString(9, orderby);
			ps.setString(10, "2");
			ps.setString(11, elevatorNo);
			ps.setString(12, comid);
			ps.setString(13, r5);
			ps.setString(14, sdate1);
			ps.setString(15, edate1);
			rs=ps.executeQuery();
			//System.out.println(ps.toString());
			
			XSSFSheet sheet = wb.createSheet("�������̹�����Ϣ");
			String[] table={"������Ϣ","��ת��Ҫ��Ϣ","�ֳ�ά����Ϣ","ά�޹�����Ϣ","�û��ظ���Ϣ","�ط��û���Ϣ"};			
			String[] table01={"���޵���","¼����","����ʱ��","���޷�ʽ","������","���޵绰","�������","���޵�λ","��Ŀ���Ƽ�¥����","���ۺ�ͬ��","���ݱ��","����ͺ�","ά��վ","�ɹ�����","�绰","�Ƿ�����","��������","ά����Ա"};					
			String[] table02={"ת��ʱ��","ת����","ת�ɵ绰","�ɹ�����ʱ��","�ɹ�������","�����˵绰"};					
			String[] table03={"���ֳ�ʱ��","�豸���","��������","��������","����γ��","����λ��","���ϴ���","����״̬","�Ƿ�ͣ��"};			
			String[] table04={"ά�����ʱ��","�»��������","����","�깤����","�깤γ��","����λ��","��������","�Ƿ��շ�","�շѽ��","ά�޹�������","ά�ޱ�ע","�����","�������","�Ƿ��Ͷ���"};			
			String[] table05={"�û���������","�������"};
			String[] table06={"���Ϸ���","�ط��û���������","�ط�����������","�ط��շ����","�طñ�ע","�Ƿ�رջ���"};
			String[][] table1={table01,table02,table03,table04,table05,table06};
			String[] keys={"CalloutMasterNo",
					"OperId",
					"RepairTime",
					"RepairMode",
					"RepairUser",
					"RepairTel",
					"ServiceObjects",
					"CompanyID",
					"projectAddress",
					"SalesContractNo",
					"ElevatorNo",
					"ElevatorParam",
					"MaintStation",
					"AssignObject",
					"Phone",
					"IsTrap",
					"RepairDesc",
					"r5name",
					"AuditOperid",
					"AuditDate",
					"IsSendSms",
					"hfcId",
					"ServiceAppraisal",
					"FittingSituation",
					"TollSituation",
					"VisitRem",
					"IsColse",					
					"TurnSendTime",
					"TurnSendId",
					"TurnSendTel",
					"AssignTime",
					"AssignUser",
					"AssignUserTel",					
					"ArriveDateTime",
					"DeviceId",
					"hmtId",
					"ArriveLongitude",
					"ArriveLatitude",
					"ElevatorLocation1",
					"FaultCode",
					"FaultStatus",
					"IsStop",			
					"CompleteTime",
					"NewFittingName",
					"num",
					"FninishLongitude",
					"FninishLatitude",
					"ElevatorLocation2",
					"hftId",
					"IsToll",
					"Money",
					"ProcessDesc",
					"ServiceRem",		
					"ServiceRating",
					"OtherRem"};
			
			int j=table01.length-1;
			int k=0;
			CellRangeAddress region=null;
			//��ͷ
			XSSFRow row0 = sheet.createRow( 0);
			XSSFCell cell0=null;
			XSSFCellStyle CellStyle0=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//�Ӵ�
			CellStyle0.setFont(font);
			CellStyle0.setAlignment(HSSFCellStyle.ALIGN_CENTER);//ˮƽ����

//		    CellStyle0.setBorderBottom(HSSFCellStyle.BORDER_THICK);// �±߿�
//		    CellStyle0.setBorderLeft(HSSFCellStyle.BORDER_THICK);//��߿�
//		    CellStyle0.setBorderRight(HSSFCellStyle.BORDER_THICK);//�ұ߿�
//		    CellStyle0.setBorderTop(HSSFCellStyle.BORDER_THICK);//�ϱ߿�
			for(int i=0;i<table.length;i++){	
				cell0=row0.createCell((short)k);
				region= new CellRangeAddress(0, 0, k,j );
				sheet.addMergedRegion(region);
				cell0.setCellValue(table[i]);
				cell0.setCellStyle(CellStyle0);
				k+=table1[i].length;				
				if(i!=table.length-1){
				j+=table1[i+1].length;
				}
			}
			//�ڶ���
			 row0 = sheet.createRow(1);	
			 int q=0;
			 for(int i=0;i<table1.length;i++){				 
				 for(int jj=0;jj<table1[i].length;jj++){
					 cell0 =row0.createCell((short)q);	
					 cell0.setCellValue(table1[i][jj]);

					 q++;
				 }
			 }
			//��������
			 int w=2;
			 while(rs.next()){	
				 row0 = sheet.createRow((w));
				 for(int i=0;i<keys.length;i++){
					XSSFCell cell2=row0.createCell((short)i);
					
					cell2.setCellValue(rs.getString(i+1));
					/**
					if((i+1)==18){
						String r5id=rs.getString(i+1);//���޲�����Ա
	      	           	String r5name="";
	      	           	if(r5id!=null && !r5id.trim().equals("")){
	      	           		 String sqls="select a from Loginuser a where a.userid in('"+r5id.replaceAll(",", "','")+"')";
	      	           		 List loginlist=hs.createQuery(sqls).list();
	      	           		 if(loginlist!=null && loginlist.size()>0){
	      	           			 for(int l=0;l<loginlist.size();l++){
	      	           				 Loginuser login=(Loginuser)loginlist.get(l);
	      	           				 if(l==loginlist.size()-1){
	      	           					 r5name+=login.getUsername();
	      	           				 }else{
	      	           					 r5name+=login.getUsername()+",";
	      	           				 }
	      	           			 }
	      	           		 }
	      	           	 }
						cell2.setCellValue(r5name);
					}else{
						cell2.setCellValue(rs.getString(i+1));
					}
					*/
				 }
				 w++;
			 }
						
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("�������̹�����Ϣ", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		//response.getOutputStream().flush(); 
		//response.getOutputStream().close(); 
		return response;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public ActionForward tosaveshps(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ActionForward  Forward=null;
		ActionErrors errors = new ActionErrors();
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String CalloutMasterNo= request.getParameter("id");
		String typejsp=request.getParameter("typejsp");
		String value=request.getParameter("value");		
		String isreturn=request.getParameter("isreturn");
		
		CalloutMaster cm=null;
		CalloutProcess cp=null;
		CalloutSms cs=null;
		SmsHistory sh=null;
		Session hs=null;
		Transaction tx=null;
		if(CalloutMasterNo!=null){
			try {
				hs=HibernateUtil.getSession();			
			    tx=hs.beginTransaction();
			    cm=(CalloutMaster)hs.get(CalloutMaster.class, CalloutMasterNo);

				if(typejsp!=null && "shsave".equals(typejsp)){//���
					cp=(CalloutProcess) hs.get(CalloutProcess.class, cm.getCalloutMasterNo());
					if(isreturn !=null && isreturn.trim().equals("R")){//����ά�޵�
						String bhAuditRem=request.getParameter("bhAuditRem");
						cm.setBhAuditRem(bhAuditRem);//��˲������
						cp.setIsgzbgs("N");
						cm.setHandleStatus("4");
						hs.save(cp);
						hs.save(cm);
					}else{
						String isSubSM=request.getParameter("isSubSM");//�ύ����������ȫ����
						String auditRem=request.getParameter("auditRem");
						String hfcId=request.getParameter("hfcId");

						String hftId=request.getParameter("hftId");
						String processDesc=request.getParameter("processDesc");
						String serviceRem=request.getParameter("serviceRem");
						String repairMode=request.getParameter("repairMode");
						String repairUser=request.getParameter("repairUser");
						String repairTel=request.getParameter("repairTel");
						String serviceObjects=request.getParameter("serviceObjects");
						String isTrap=request.getParameter("isTrap");
						String repairDesc=request.getParameter("repairDesc");
						
						String deviceId=request.getParameter("deviceId");
						String faultCode=request.getParameter("faultCode");
						String faultStatus=request.getParameter("faultStatus");
						String hmtId=request.getParameter("hmtId");
						String isStop=request.getParameter("isStop");
						String stopTime=request.getParameter("stopTime");
						String stopRem=request.getParameter("stopRem");
						
						cp.setDeviceId(deviceId);
						cp.setFaultCode(faultCode);
						cp.setFaultStatus(faultStatus);
						cp.setHmtId(hmtId);
						cp.setIsStop(isStop);
						cp.setStopTime(stopTime);
						cp.setStopRem(stopRem);
						cp.setHftId(hftId);
						cp.setProcessDesc(processDesc);
						cp.setServiceRem(serviceRem);
						
						cm.setRepairMode(repairMode);
						cm.setRepairTel(repairTel);
						cm.setRepairUser(repairUser);
						cm.setServiceObjects(serviceObjects);
						cm.setIsTrap(isTrap);
						cm.setRepairDesc(repairDesc);
						cm.setAuditOperid(userInfo.getUserID());
						cm.setAuditDate(xj.getdatetime());
						cm.setIsSendSms(value);
						cm.setAuditRem(auditRem);
						cm.setHfcId(hfcId);
						
						if(isSubSM!=null && isSubSM.trim().equals("Y")){
							cm.setIsSubSM(isSubSM);
							cm.setHandleStatus("8");//8 ����������ȫ����
						}else{
							cm.setHandleStatus("6");
						}
						
						hs.save(cp);
						hs.save(cm);
					    hs.flush();
						if(value!=null && "Y".equals(value)){
							/******************���Ͷ��ſ�ʼ**************************************/					
							String smsContent="����Ѹ�����: ����,���ǵ�ά����Ա��Ϊ������˼��޷���,�����ظ�����01-05��"
									+ "���ĸôη����������ۡ�01,�ǳ�����  02,����  03,һ��  04,������  05,�ǳ������⡣"
									+ "   ��Ҳ���Իظ�\"0\"+����,Ϊ�����������ͽ��顣����Ѹ�ｫ�߳�Ϊ������";
							String telNo=cm.getRepairTel();
							
							System.out.println(">>>���ͻطö���");
							boolean iscg=true;
							//boolean iscg=SmsService.sendSMS(smsContent, telNo);
							//boolean iscg=XjsggAction.sendMessage(smsContent, telNo);
							
							//���뼱�޶��ű�
							String time=xj.getdatetime();
							cs=(CalloutSms)hs.get(CalloutSms.class, CalloutMasterNo);
							cs.setSmsTel2(telNo);//�طõ绰
							cs.setSmsSendTime2(time);//�ط÷��Ͷ���ʱ��
							cs.setSmsContent2(smsContent);//�طö�������
							hs.save(cs);
							hs.flush();
							
							//������ʷ��
							sh=new SmsHistory();
							sh.setSmsContent(smsContent);
							sh.setSmsSendTime(time);
							sh.setSmsTel(telNo);
							if(iscg){
								sh.setFlag(1);
							}else{
								sh.setFlag(0);	
							}
							hs.save(sh);
							/***********************���Ͷ��Ž���******************************************/		
						}
					}
				}else if(typejsp!=null && "pssave".equals(typejsp)){ //�ط�����
					String serviceAppraisal=request.getParameter("serviceAppraisal");
					String fittingSituation=request.getParameter("fittingSituation");
					String tollSituation=request.getParameter("tollSituation");
					String visitRem=request.getParameter("visitRem");
					String isColse=request.getParameter("isColse");
					cm.setHandleStatus("7");
					cm.setServiceAppraisal(serviceAppraisal);
					cm.setFittingSituation(fittingSituation);
					cm.setTollSituation(tollSituation);
					cm.setVisitRem(visitRem);
					cm.setIsColse(isColse);
					hs.save(cm);
				}

			tx.commit();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ʧ�ܣ�"));
			}finally{
				hs.close();
				
			}
			
		}else{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ʧ�ܣ�"));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		if(isreturn!=null && "Y".equals(isreturn)){
			Forward=mapping.findForward("returnList");			
		}else if(isreturn!=null && "R".equals(isreturn)){
			Forward=mapping.findForward("returnList");			
		}else if("N".equals(isreturn)){
			Forward=mapping.findForward("returndisplay");
		}
		return Forward;
	}
	
	public static String getelecadrr(Session hs,String elevatorNo,double longs,double lats){
		String address="";
		String hql="select a from ElevatorCoordinateLocation a where elevatorNo='"+elevatorNo+"'"
				+ " and beginLongitude>= "+longs+" and endLongitude<="+longs+" and beginDimension>="+lats+""
				+ " and endDimension<= "+lats;
		List list=hs.createQuery(hql).list();
		if(list!=null && list.size()>0){
			ElevatorCoordinateLocation ec=(ElevatorCoordinateLocation)list.get(0);
			address=ec.getElevatorLocation();
		}
		return address;
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
	public void toComidStorageIDList(ActionMapping mapping, ActionForm form,
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
		String storageid=request.getParameter("storageid");
		response.setHeader("Content-Type","text/html; charset=GBK");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs=HibernateUtil.getSession();
			if(storageid!=null && !"".equals(storageid)){
				//ֻ�� ά��վ��A49��ά����A50,ά������ A03��ά�޼���ԱA53
				String hql="select a from Loginuser a where a.storageid like '"+storageid+"%' " +
						"and a.enabledflag='Y' and a.roleid in('A49','A50','A03','A53') order by a.roleid,a.userid";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0){
					sb.append("<rows>");
					for(int i=0;i<list.size();i++){
						Loginuser sid=(Loginuser)list.get(i);
						sb.append("<cols name='"+sid.getUsername()+"' value='"+sid.getUserid()+"' phone='"+sid.getPhone()+"'>").append("</cols>");
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
	
	/**
	 * ��ӡ֪ͨ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	/*@SuppressWarnings("unchecked")*/
	public ActionForward toPreparePrintRecord(ActionMapping mapping,ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			    	ActionErrors errors = new ActionErrors();
			    	HttpSession session = request.getSession();
					ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
					Session hs = null;
					DynaActionForm dform = (DynaActionForm) form;		
					String id = request.getParameter("id");
					List etcpList=new ArrayList();
					if (id != null && !id.equals("")) {			
						try {
							hs = HibernateUtil.getSession();
							
							Query query = hs.createQuery("from CalloutMaster where calloutMasterNo = '"+id.trim()+"'");
							List list = query.list();
							if (list != null && list.size() > 0) {

								// ����Ϣ
								CalloutMaster CM = (CalloutMaster) list.get(0);	
								
								CM.setRepairMode(CM.getRepairMode().equals("1")?"��������":"��������");
								CM.setCompanyId(bd.getName(hs, "Customer", "companyName", "companyId",CM.getCompanyId()));
								CM.setIsTrap(CM.getIsTrap().equals("Y")?"����":"������");
								CM.setProjectAddress(bd.getName("ElevatorCoordinateLocation", "rem", "elevatorNo", CM.getElevatorNo()));//��Ŀ���Ƽ�¥����

								Query query1 = hs.createQuery("from CalloutProcess where calloutMasterNo = '"+id.trim()+"'");	
								List list1 = query1.list();
								CalloutProcess CP = (CalloutProcess) list1.get(0);
								CP.setAssignObject2(bd.getName(hs, "Loginuser","username", "userid",CP.getAssignObject2()));
								/**
								String hftid=CP.getHftId();//��������
					           	 String hftdesc="";
					           	 if(hftid!=null && !hftid.trim().equals("")){
					           		 String sqls="select a from HotlineFaultType a where a.hftId in('"+hftid.replaceAll(",", "','")+"')";
					           		 List loginlist=hs.createQuery(sqls).list();
					           		 if(loginlist!=null && loginlist.size()>0){
					           			 for(int l=0;l<loginlist.size();l++){
					           				HotlineFaultType hft=(HotlineFaultType)loginlist.get(l);
					           				 if(l==loginlist.size()-1){
					           					hftdesc+=hft.getHftDesc();
					           				 }else{
					           					hftdesc+=hft.getHftDesc()+",";
					           				 }
					           			 }
					           		 }
					           	 }
					           	CP.setHftId(hftdesc);
					           	*/
								/**
					           	String r5=CP.getR5();//���޲�����Ա
					           	 String r5name="";
					           	 if(r5!=null && !r5.trim().equals("")){
					           		 String sqls="select a from Loginuser a where a.userid in('"+r5.replaceAll(",", "','")+"')";
					           		 List loginlist=hs.createQuery(sqls).list();
					           		 if(loginlist!=null && loginlist.size()>0){
					           			 for(int l=0;l<loginlist.size();l++){
					           				 Loginuser login=(Loginuser)loginlist.get(l);
					           				 if(l==loginlist.size()-1){
					           					 r5name+=login.getUsername();
					           				 }else{
					           					 r5name+=login.getUsername()+",";
					           				 }
					           			 }
					           		 }
					           	 }
					           	CP.setR5(r5name);
					           	*/
								//��barCodeList��noticeList����
								BarCodePrint dy = new BarCodePrint();
								List barCodeList = new ArrayList();
								barCodeList.add(CM);
								barCodeList.add(CP);
	/*							barCodeList.add(specialRegisters);*/
								dy.toPrintTwoRecord5(request,response, barCodeList,id);
								//register hecirList
								
							
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
						
					}		
					
			                  
							
							
					
					
					if (!errors.isEmpty()) {
						saveErrors(request, errors);
					}
			    	return null;
			    }	
	
	/**
	 * �����ļ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toDownloadFileRecord(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {
		
		Session hs = null;

		String filepath = request.getParameter("customerSignature");//��ˮ��
		String filepath1 = request.getParameter("customerImage");
		String localPath="";
		String oldname="";
		
		String folder = request.getParameter("folder");		//�ļ���
		if(null == folder || "".equals(folder)){
			folder ="CalloutProcess.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
				String root=folder;//�ϴ�Ŀ¼
				if(filepath != null && !filepath.equals("")){
					localPath = root+filepath;
				}else{
					localPath = root+filepath1;
				}
				
			
				/*localPath = filepath+newnamefile;*/
				
			/*}*/
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(oldname, "utf-8"));
		//OutputStream os = new FileOutputStream(new File(localPath));
		
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
		return null;
	}
	
	/**
	 * ����Ǽ�ͣ�ݻطñ�ע�ķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPrepareStophfRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		request.setAttribute("navigator.location","�������̹��� >> ͣ�ݻط�");	
		
		String id = (String) dform.get("id");
		if(id==null && "".equals(id)){
			id=request.getParameter("id");
		}
		String typejsp = request.getParameter("typejsp");
		
		HashMap hm=new HashMap();
		Session hs = null;
		CalloutMaster cm=null;
		CalloutProcess cp=null;
		CalloutSms cs=null;
		CalloutSms sms=null;
		String CSheight = PropertiesUtil.getProperty("CSheight");
		String CSwidth = PropertiesUtil.getProperty("CSwidth");
		String CIheight = PropertiesUtil.getProperty("CIheight");
		String CIwidth = PropertiesUtil.getProperty("CIwidth");
	   if (id != null) {				
		try {
			hs = HibernateUtil.getSession();
			cm=(CalloutMaster)hs.get(CalloutMaster.class, id);
			cm.setOperId(bd.getName("Loginuser", "username", "userid", cm.getOperId()));
			
			if(!"sh".equals(typejsp)){
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
				cm.setIsTrap(String.valueOf(cm.getIsTrap()).equals("Y")?"����":"������");
			}
			String companyName=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
			if(companyName==null || "".equals(companyName)){
				companyName=cm.getCompanyId();
			}
			cm.setCompanyId(companyName);
			cm.setMaintStation(bd.getName("Storageid", "storagename", "storageid", cm.getMaintStation()));
			cm.setAssignObject(bd.getName("Loginuser", "username", "userid", cm.getAssignObject()));
			cm.setProjectAddress(bd.getName("ElevatorCoordinateLocation", "rem", "elevatorNo", cm.getElevatorNo()));//��Ŀ���Ƽ�¥����
			
			if(typejsp!=null && "sh".equals(typejsp)){
				
				cm.setAuditOperid(bd.getName("Loginuser", "username", "userid", cm.getAuditOperid()));
				List HotlineFaultClassificationList=xj.getClasses(hs, "HotlineFaultClassification", "enabledFlag", "Y");
				request.setAttribute("HotlineFaultClassificationList", HotlineFaultClassificationList);
				request.setAttribute("rmList", bd.getPullDownList("CalloutMaster_RepairMode"));
				request.setAttribute("soList", bd.getPullDownList("CalloutMaster_ServiceObjects"));
			}else if(typejsp!=null && "ps".equals(typejsp)){
				cm.setAuditOperid(bd.getName("Loginuser", "username", "userid", cm.getAuditOperid()));
				cm.setHfcId(bd.getName("HotlineFaultClassification", "hfcName", "hfcId", String.valueOf(cm.getHfcId())));//���Ϸ���
			}else{
				cm.setStophfOperid(userInfo.getUserName());
				cm.setStophfDate(xj.getdatetime());
				cm.setSmAuditOperid(bd.getName("Loginuser", "username", "userid", cm.getSmAuditOperid()));
				typejsp="display";
				cm.setAuditOperid(bd.getName("Loginuser", "username", "userid", cm.getAuditOperid()));
				cm.setHfcId(bd.getName("HotlineFaultClassification", "hfcName", "hfcId", String.valueOf(cm.getHfcId())));//���Ϸ���
			}
			String IsSendSms=cm.getIsSendSms();
			if(IsSendSms!=null && !"".equals(IsSendSms)){
				cm.setIsSendSms(String.valueOf(cm.getIsSendSms()).equals("Y")?"�ѷ���":"δ����");
			}else{
				cm.setIsSendSms("");
			}	
			String isSubSM=cm.getIsSubSM();
			if(isSubSM!=null && !"".equals(isSubSM)){
				cm.setIsSubSM(String.valueOf(cm.getIsSubSM()).equals("Y")?"��":"��");
			}else{
				cm.setIsSubSM("");
			}
			String ServiceAppraisal=cm.getServiceAppraisal();
			if(ServiceAppraisal!=null && !"".equals(ServiceAppraisal)){
				switch(Integer.valueOf(ServiceAppraisal).intValue()){
				case 1:cm.setServiceAppraisal("�ǳ�����");break;
				case 2:cm.setServiceAppraisal("����");break;
				case 3:cm.setServiceAppraisal("һ��");break;
				case 4:cm.setServiceAppraisal("������");break;
				case 5:cm.setServiceAppraisal("�ǳ�������");break;
				}
			}
			String FittingSituation=cm.getFittingSituation();
			if(FittingSituation!=null && !"".equals(FittingSituation)){
				cm.setFittingSituation(String.valueOf(cm.getFittingSituation()).equals("1")?"��ʵ":"����ʵ");
			}else{
				cm.setFittingSituation("");
			}
			String TollSituation=cm.getTollSituation();
			if(TollSituation!=null && !"".equals(TollSituation)){
				cm.setTollSituation(String.valueOf(cm.getTollSituation()).equals("1")?"��ʵ":"����ʵ");
			}else{
				cm.setTollSituation("");
			}
			String IsColse=cm.getIsColse();
			if(IsColse!=null && !"".equals(IsColse)){
				cm.setIsColse(String.valueOf(cm.getIsColse()).equals("1")?"�ر�":"���ر�");
			}else{
				cm.setIsColse("");
			}           			
			cp=(CalloutProcess)hs.get(CalloutProcess.class, id);
			if(cp!=null){
				cp.setTurnSendId(bd.getName("Loginuser", "username", "userid", String.valueOf(cp.getTurnSendId())));
				cp.setAssignUser(bd.getName("Loginuser", "username", "userid", String.valueOf(cp.getAssignUser())));
				String IsStop=cp.getIsStop();
				if(!"sh".equals(typejsp)){
					if(IsStop!=null && !"".equals(IsStop)){
						cp.setIsStop(IsStop.equals("Y")?"ͣ��":"��ͣ��");
					}else{
						cp.setIsStop("");	
					}
					
					//cp.setHftId(bd.getName("HotlineFaultType", "hftDesc", "hftId",String.valueOf(cp.getHftId())));//��������
					String hftid=cp.getHftId();//��������
		           	 String hftdesc="";
		           	 if(hftid!=null && !hftid.trim().equals("")){
		           		 String sqls="select a from HotlineFaultType a where a.hftId in('"+hftid.replaceAll(",", "','")+"')";
		           		 List loginlist=hs.createQuery(sqls).list();
		           		 if(loginlist!=null && loginlist.size()>0){
		           			 for(int l=0;l<loginlist.size();l++){
		           				HotlineFaultType hft=(HotlineFaultType)loginlist.get(l);
		           				 if(l==loginlist.size()-1){
		           					hftdesc+=hft.getHftDesc();
		           				 }else{
		           					hftdesc+=hft.getHftDesc()+",";
		           				 }
		           			 }
		           		 }
		           	 }
		           	cp.setHftId(hftdesc);
					
					
					cp.setHmtId(bd.getName("HotlineMotherboardType", "hmtName", "hmtId", String.valueOf(cp.getHmtId())));//��������
				}else{
					//request.setAttribute("hftId", bd.getName("HotlineFaultType", "hftDesc", "hftId",String.valueOf(cp.getHftId())));
					String hftid=cp.getHftId();//���޲�����Ա
		           	 String hftdesc="";
		           	 if(hftid!=null && !hftid.trim().equals("")){
		           		 String sqls="select a from HotlineFaultType a where a.hftId in('"+hftid.replaceAll(",", "','")+"')";
		           		 List loginlist=hs.createQuery(sqls).list();
		           		 if(loginlist!=null && loginlist.size()>0){
		           			 for(int l=0;l<loginlist.size();l++){
		           				HotlineFaultType hft=(HotlineFaultType)loginlist.get(l);
		           				 if(l==loginlist.size()-1){
		           					hftdesc+=hft.getHftDesc();
		           				 }else{
		           					hftdesc+=hft.getHftDesc()+",";
		           				 }
		           			 }
		           		 }
		           	 }
		           	request.setAttribute("hftId", hftdesc);
					
					request.setAttribute("hmtId", bd.getName("HotlineMotherboardType", "hmtName", "hmtId", String.valueOf(cp.getHmtId())));
				}
				
				String IsToll=cp.getIsToll();
				if(IsToll!=null && !"".equals(IsToll)){
					cp.setIsToll(String.valueOf(cp.getIsToll()).equals("Y")?"�շ�":"���շ�");
				}else{
					cp.setIsToll("");
				} 
				Double ArriveLongitude=cp.getArriveLongitude();
				Double ArriveLatitude=cp.getArriveLatitude();
				Double FninishLongitude=cp.getFninishLongitude();
				Double FninishLatitude=cp.getFninishLatitude();
				/**
				String r5=cp.getR5();//���޲�����Ա
	           	 String r5name="";
	           	 if(r5!=null && !r5.trim().equals("")){
	           		 String sqls="select a from Loginuser a where a.userid in('"+r5.replaceAll(",", "','")+"')";
	           		 List loginlist=hs.createQuery(sqls).list();
	           		 if(loginlist!=null && loginlist.size()>0){
	           			 for(int l=0;l<loginlist.size();l++){
	           				 Loginuser login=(Loginuser)loginlist.get(l);
	           				 if(l==loginlist.size()-1){
	           					 r5name+=login.getUsername();
	           				 }else{
	           					 r5name+=login.getUsername()+",";
	           				 }
	           			 }
	           		 }
	           	 }
	           	 */
	           	hm.put("r5name",cp.getR5());//���޲�����Ա

				hm.put("elevatorLocation",cp.getArriveLocation());
				hm.put("elevatorLocation2", cp.getFninishLocation());
			}
			cs=(CalloutSms)hs.get(CalloutSms.class, id);
			if(cs!=null){
			String ServiceRating=cs.getServiceRating();//�û�����
			if(ServiceRating!=null && !"".equals(ServiceRating)){
				switch(Integer.valueOf(ServiceRating).intValue()){
				case 1:cs.setServiceRating("�ǳ�����");break;
				case 2:cs.setServiceRating("����");break;
				case 3:cs.setServiceRating("һ��");break;
				case 4:cs.setServiceRating("������");break;
				case 5:cs.setServiceRating("�ǳ�������");break;
				}
			}
			}
			sms=(CalloutSms) hs.get(CalloutSms.class, cm.getCalloutMasterNo());
			
			Query queryfiles = hs
					.createQuery("from CalloutFileinfo ");
					List listfiles = queryfiles.list();
					request.setAttribute("CalloutFileinfo", listfiles);
			
		
		} catch (DataStoreException e) {
				e.printStackTrace();
			}				
		
	}else{
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
	}

	//ά��վ
	List storageidList=bd.getMaintStationList(userInfo.getComID());
	request.setAttribute("storageidList", storageidList);
	
    request.setAttribute("CalloutMasterList", cm);
    request.setAttribute("CalloutProcessList", cp);
    request.setAttribute("CalloutSmsList", cs);
    request.setAttribute("hashmapbean", hm);
    request.setAttribute("calloutSmsBean", sms);
	request.setAttribute("typejsp",typejsp);
	request.setAttribute("CSheight",CSheight);
	request.setAttribute("CSwidth", CSwidth);
	request.setAttribute("CIheight", CIheight);
	request.setAttribute("CIwidth", CIwidth);
	if (!errors.isEmpty()) {
		saveErrors(request, errors);
	}
		forward = mapping.findForward("toStophf");
		return forward;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public ActionForward tosavestophf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ActionForward  Forward=null;
		ActionErrors errors = new ActionErrors();
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String calloutMasterNo= request.getParameter("calloutMasterNo");
		String stophfRem=request.getParameter("stophfRem");	
		
		CalloutMaster cm=null;

		Session hs=null;
		Transaction tx=null;
		if(calloutMasterNo!=null){
			try {
				hs=HibernateUtil.getSession();			
			    tx=hs.beginTransaction();
			    
			    cm=(CalloutMaster)hs.get(CalloutMaster.class, calloutMasterNo);
				
				cm.setStophfRem(stophfRem);
				cm.setStophfOperid(userInfo.getUserID());
				cm.setStophfDate(xj.getdatetime());
				
				hs.save(cm);
			    hs.flush();

			tx.commit();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>����ʧ�ܣ�</font>"));
			}finally{
				hs.close();
			}
			
		}else{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>����ʧ�ܣ�</font>"));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		Forward=mapping.findForward("returnList");			

		return Forward;
	}
	
	
}
