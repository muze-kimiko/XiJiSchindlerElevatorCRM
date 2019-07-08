package com.gzunicorn.struts.action.infomanager.elevatortransfercaseregister;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.dao.DBInterface;
import com.gzunicorn.common.dao.ObjectAchieveFactory;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.infomanager.elevatortransfercaseprocess.ElevatorTransferCaseProcess;
import com.gzunicorn.hibernate.infomanager.elevatortransfercaseregister.ElevatorTransferCaseRegister;
import com.gzunicorn.hibernate.infomanager.handoverelevatorcheckfileinfo.HandoverElevatorCheckFileinfo;
import com.gzunicorn.hibernate.infomanager.handoverelevatorcheckitemregister.HandoverElevatorCheckItemRegister;
import com.gzunicorn.hibernate.infomanager.handoverelevatorspecialregister.HandoverElevatorSpecialRegister;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.hibernate.wbfileinfo.Wbfileinfo;
import com.gzunicorn.workflow.bo.JbpmExtBridge;

public class ElevatorTransferCaseRegisterJbpmAction extends DispatchAction {

	Log log = LogFactory.getLog(ElevatorTransferCaseRegisterJbpmAction.class);
	
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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "myTaskOA", null);
		/** **********�����û�Ȩ�޹���*********** */
		ActionForward forward = super.execute(mapping, form, request,response);
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
		request.setAttribute("navigator.location", messages.getMessage(locale,
				navigation));
	}
	
	
	/**
	 * ׼��������ע�����Ƿ������������ݣ����Ƿ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toPrepareApprove(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws IOException, ServletException {

		request.setAttribute("navigator.location", "��װά�����ӵ��������� >> �� ��");

		ActionErrors errors = new ActionErrors();

		display(form, request, errors,"display"); //�鿴����
		
		request.setAttribute("approve", "Y");
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toApprove");
	}
	
	/**
	 * �޸�ά�����ӵ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toPrepareApprove2(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws IOException, ServletException {

		request.setAttribute("navigator.location", "��װά�����ӵ��������� >> �� ��");

		ActionErrors errors = new ActionErrors();

		display(form, request, errors,"modify"); //�鿴����
		
		request.setAttribute("approve", "Y");
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toApproveModify");
	}
	
	
	/**
	 * �����ύ��������
	 * �˷�������չΪ�����沢�ύ���򱣴治�ύ�������ύ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toSaveApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();

		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) request.getSession().getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String flowname = (String) dform.get("flowname");// ��������
		//if (flowname != null && !flowname.trim().equals("")) {
		//	flowname = new String(flowname.getBytes("ISO-8859-1"), "gbk");
		//}
		
		String billNo = (String) dform.get("billno");
		String taskname = (String) dform.get("taskName");// ��������
		Long taskid = (Long) dform.get("taskId");// ��������
		String approveResult = (String) dform.get("approveResult");// �������
		String approveRem = (String) dform.get("approveRem");// �쵼���
		String colseRem = request.getParameter("colseRem");// �쵼���
		String[] jnlno=request.getParameterValues("jnlno");
		String[] isDelete=request.getParameterValues("isDelete");
		String[] deleteRem=request.getParameterValues("deleteRem");
		
		
		String factoryCheckResult=(String) dform.get("factoryCheckResult");
		String factoryCheckResult2=(String) dform.get("factoryCheckResult2");
		String r2=(String)dform.get("r2");
		
		String auditor=request.getParameter("auditorId");
		if(auditor==null || "".equals(auditor)){
			auditor=(String)dform.get("auditor");
		}
		
		String isDeductions="";
		if("���첿�����".equals(taskname)){
			isDeductions=(String) dform.get("isDeductions");	
		}
		String deductMoney="0";
		if("��װ�������".equals(taskname)){
			deductMoney=(String) dform.get("deductMoney");
		}
		
		Session hs = null;
		Transaction tx = null;
		JbpmExtBridge jbpmExtBridge = null;
		if(isTokenValid(request, true)){
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if (billNo != null && !billNo.trim().equals("")) {
				
				ElevatorTransferCaseRegister register = (ElevatorTransferCaseRegister) hs.get(ElevatorTransferCaseRegister.class, billNo.trim());
				
				String processDefineID = Grcnamelist1.getProcessDefineID("elevatortransfercaseregister", register.getStaffName());// ���̻���id
				String traname="";
				for(Object object : this.getTransition(hs.connection(),3,null,taskid)){
					traname = (String) ((HashMap) object).get("tranname");
				}
				/*=============== ��������������ʼ =================*/
				jbpmExtBridge = new JbpmExtBridge();
				HashMap paraMap = new HashMap();
				ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskId").toString()));
				
				pd.addAppointActors("");// ����̬��ӵ�����������
				if(!"����".equals(approveResult)){
					if(!"��ͨ��".equals(approveResult)){
						if("��ز������".equals(taskname)){
							
							if(register.getIsxjs().equals("Y")){
								pd.setSelpath("Y1");//��Ѹ�ﰲװ
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "��װ��˾�������", "%");// ��������
							}else{
								if(register.getIsDeductions().equals("Y")){
									pd.setSelpath("NK");//�񡢿ۿ�
									Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ִ�пۿ�", "%");// ��������
								}else{
									pd.setSelpath("NB");//�񡢲��ۿ�
									register.setProcessStatus("3");//����ͨ�������ô���״̬Ϊ�������
								}
							}					
						}else if("��װ�������".equals(taskname)){
							Grcnamelist1.setJbpmAuditopers(pd, processDefineID, "��ز������", register.getDepartment());/*(pd, processDefineID, approveResult.replace("�ύ", ""), "%" );*/// ��������
						}else if("�޸�ά�����ӵ������".equals(taskname)){
							if(register.getFactoryCheckResult().trim().equals("�ϸ�")){
								pd.setSelpath("Y");
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�ر��������", "%");// ��������
							}else if(register.getFactoryCheckResult().trim().equals("���ϸ�")){
								pd.setSelpath("N");
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "���첿�����", "%");// ��������
							}
						}else if("��װ��˾�������".equals(taskname)){
							if(register.getIsDeductions().equals("Y")){
								pd.setSelpath("Y2");
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ִ�пۿ�", "%");// ��������
							}else{
								pd.setSelpath("N2");
								register.setProcessStatus("3");//����ͨ�������ô���״̬Ϊ�������
							}
						}
						else{
							Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, approveResult.replace("�ύ", ""), "%");// ��������
						}
					}else{
						Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "���첿�����", "%");// ��������
					}
				}else{
					pd.addAppointActors(register.getStaffName());//���ظ�������Ա�޸�
				}
			
				pd=jbpmExtBridge.goToNext(taskid,approveResult,userInfo.getUserID(),null);//���
				/*=============== ���������������� =================*/

				if("���첿�����".equals(taskname)){
					List fileList=savePicter(dform, request, response, "HandoverElevatorCheckItemRegister.file.upload.folder", billNo);
					savePicterTodb(request, fileList, billNo);
					register.setIsDeductions(isDeductions);
					register.setFactoryCheckResult(factoryCheckResult);
					register.setFactoryCheckResult2(factoryCheckResult2);
					register.setR2(r2);
					register.setR1("Y");
					register.setCheckRem((String) dform.get("checkRem"));//��ע
					for(int i=0;i<jnlno.length;i++){
						hs.createQuery("update HandoverElevatorCheckItemRegister hecir set hecir.isDelete='"+isDelete[i]+"',hecir.deleteRem='"+deleteRem[i]+"' where hecir.jnlno='"+jnlno[i]+"'").executeUpdate();
					}
					hs.flush();
				}

				if("��װ�������".equals(taskname)){
					if(deductMoney==null || deductMoney.trim().equals("")){
						deductMoney="0";
					}
					register.setDeductMoney(Double.parseDouble(deductMoney));
				}
				if("ִ�пۿ�".equals(taskname)){
					register.setProcessResult((String)dform.get("processResult"));
				}
				if("�ر�����".equals(taskname)){
					register.setColseRem(colseRem);
					register.setProcessStatus("3");//����ͨ�������ô���״̬Ϊ�������
				}
				if("�ر��������".equals(taskname)){
					register.setProcessStatus("3");//����ͨ�������ô���״̬Ϊ�������
				}
				
				register.setStatus(pd.getStatus());
				register.setTokenId(pd.getToken());
				register.setProcessName(pd.getNodename());
				hs.save(register);

				// �����������������Ϣ
				ElevatorTransferCaseProcess process = new ElevatorTransferCaseProcess();
				process.setElevatorTransferCaseRegister(register);
				process.setTaskId(pd.getTaskid().intValue());// �����
				process.setTaskName(taskname);// ��������
				process.setTokenId(pd.getToken());// ��������
				process.setUserId(userInfo.getUserID());
				process.setDate1(CommonUtil.getToday());
				process.setTime1(CommonUtil.getTodayTime());
				process.setApproveResult(approveResult);
				process.setApproveRem(approveRem);
				hs.save(process);
				tx.commit();
				
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (jbpmExtBridge != null) {
				jbpmExtBridge.setRollBack();
			}
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("application.submit.fail"));
			e.printStackTrace();
		} finally {
			if (jbpmExtBridge != null) {
				jbpmExtBridge.close();
			}
			if (hs != null) {
				hs.close();
			}
		}
		
	    }else{
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));//�����ظ��ύ
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("application.submit.sucess"));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		request.setAttribute("display", "Y");
		forward = mapping.findForward("returnApprove");
		return forward;
	}
	
	
	/**
	 * ҳ��鿴����
	 * @param form
	 * @param request
	 * @param errors
	 * @throws UnsupportedEncodingException
	 */
	public void display(ActionForm form, HttpServletRequest request,
			ActionErrors errors,String type) throws UnsupportedEncodingException{
		
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String tokenid = request.getParameter("tokenid");// ��������
		if (tokenid == null) {
			tokenid =  String.valueOf(Long.valueOf( dform.get("tokenId").toString()));
		}
		String taskid = request.getParameter("taskid");// ����id
		if (taskid == null) {
			taskid = String.valueOf(Long.valueOf( dform.get("taskId").toString()));
			
		}
		String taskname = request.getParameter("taskname");// ��ǰ��������
		
		if (taskname != null) {
			taskname = new String(taskname.getBytes("ISO-8859-1"), "gbk");
		}
		if (taskname == null) {
			taskname = (String) dform.get("taskName");
		}
		String tasktype = request.getParameter("tasktype");// ��������
		if (tasktype == null) {
			tasktype = (String) dform.get("tasktype");
		}

		Session hs = null;
		String billno="";
		String[] typeflag={"a.id.typeflag='HandoverElevatorCheckItem_ExamType'"};
		Integer status = null;
		List hecirList = new ArrayList();
		List hecirDeketeList = new ArrayList();
		List etcpList=new ArrayList();
		String CSheight = PropertiesUtil.getProperty("CSheight");
		String CSwidth = PropertiesUtil.getProperty("CSwidth");
		String CIheight = PropertiesUtil.getProperty("CIheight");
		String CIwidth = PropertiesUtil.getProperty("CIwidth");
		if (tokenid != null) {			
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from ElevatorTransferCaseRegister where tokenid = '"+tokenid+"' order by billNo desc");
				List list = query.list();
				if (list != null && list.size() > 0) {
					// ����Ϣ
					ElevatorTransferCaseRegister register = (ElevatorTransferCaseRegister) list.get(0);	
					billno=register.getBillno();
					status=register.getStatus();
					register.setAuditor(bd.getName(hs, "Loginuser","username", "userid",register.getAuditor()));// �����	
					register.setOperId(bd.getName(hs, "Loginuser","username", "userid",register.getOperId()));
					register.setStaffName(bd.getName(hs, "Loginuser","username", "userid",register.getStaffName()));
					register.setTransferId(bd.getName(hs, "Loginuser","username", "userid",register.getTransferId()));
					register.setDepartment(bd.getName_Sql("Company", "comname", "comid",register.getDepartment()));
					
					String CheckDate=register.getCheckDate();
					if(CheckDate==null){
						CheckDate="";
					}
					String CheckTime2=register.getCheckTime2();
					if(CheckTime2==null){
						CheckTime2="";
					}
					register.setCheckDate(CheckDate+" "+CheckTime2);
					
					//���ӵ��ݼ����Ŀ
					String sql = "select b.ExamType,b.CheckItem,b.IssueCoding,b.IssueContents issueContents1"
							+ ",b.rem,b.isRecheck,b.isDelete,b.jnlno,b.deleteRem,h.itemgroup,p.pullname,isnull(b.isyzg,'') "
							+ "from HandoverElevatorCheckItemRegister b,HandoverElevatorCheckItem h,pulldown p "
							+ " where b.ExamType=h.ExamType and b.CheckItem=h.CheckItem and b.IssueCoding=h.IssueCoding"
							+ " and b.billno='"+billno+"' "
							+ " and p.pullid=b.examType and p.typeflag='HandoverElevatorCheckItem_ExamType' "
							+ " order by b.isRecheck desc,p.orderby,b.issueCoding ";
					query = hs.createSQLQuery(sql);	
					List list1 = query.list();
					if(list1!=null&&list1.size()>0){
						for(int i=0;i<list1.size();i++){
						Object[] values=(Object[])list1.get(i);
						Map map=new HashMap();
						map.put("examType",values[0]);
						map.put("examTypeName", values[10]);
						map.put("checkItem", values[1]);
						map.put("issueCoding", values[2]);
						map.put("issueContents1", values[3]);
						map.put("rem", values[4]);
						map.put("isRecheck", values[5]);
						map.put("jnlno", values[7]);
						map.put("deleteRem", values[8]);
						map.put("itemgroup", values[9]);
						map.put("isyzg", values[11]);
						
						if(type.equals("modify")){
							sql="from HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno ='"+(String)values[7]+"' and ext1='N' ";
						}else{
							sql="from HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno ='"+(String)values[7]+"'";
						}
						
						List fileList=hs.createQuery(sql).list();
						if(fileList!=null&&fileList.size()>0){
							map.put("fileList", fileList);
						}
						int cn=register.getCheckNum();
						if(cn>1){
							if(values[5].equals("N"))
							{
							map.put("color", "red");
							}else{
							map.put("color", "black");	
							}
						}
						
						if(type.equals("display")){
							if(values[6].equals("Y")){
								hecirDeketeList.add(map);
							}else{
								hecirList.add(map);
							}
						}else{
							hecirList.add(map);
						}
						
						}	
					}
					sql="select r,c.scName from HandoverElevatorSpecialClaim c,HandoverElevatorSpecialRegister r where r.scId=c.scId and r.elevatorTransferCaseRegister.billno='"+register.getBillno()+"'";
					list1=hs.createQuery(sql).list();
					List specialRegister =new ArrayList();
					if(list1!=null&&list1.size()>0)
					{
						for(int i=0;i<list1.size();i++){
							Object[] objects=(Object[]) list1.get(i);
							HandoverElevatorSpecialRegister r=(HandoverElevatorSpecialRegister) objects[0];
							r.setR1((String)objects[1]);
							specialRegister.add(r);
						}
						if(specialRegister!=null&&specialRegister.size()>0){
							request.setAttribute("specialRegister", specialRegister);
						}
					}
					
					
					sql="from ElevatorTransferCaseProcess where billno='"+billno+"' order by taskId";
					list1=hs.createQuery(sql).list();
					for(Object object : list1){
						ElevatorTransferCaseProcess etcp=(ElevatorTransferCaseProcess)object;
						etcp.setUserId(bd.getName(hs, "Loginuser","username", "userid",etcp.getUserId()));
						etcp.setDate1(etcp.getDate1()+" "+etcp.getTime1());
						etcpList.add(etcp);
					}
					request.setAttribute("elevatorTransferCaseRegisterBean", register);	
					request.setAttribute("hecirList", hecirList);
					if(hecirDeketeList!=null&&hecirDeketeList.size()>0){
					request.setAttribute("hecirDeketeList", hecirDeketeList);
					}
					if(taskname.trim().equals("���첿�����")){	
					}
				    
					request.setAttribute("etcpList", etcpList);
					/*request.setAttribute("etcpBean", etcp1);*/
					request.setAttribute("ResultList", this.getTransition(hs.connection(),3,null,Long.parseLong(taskid)));
					request.setAttribute("auditorName", userInfo.getUserName());
					request.setAttribute("auditorId", userInfo.getUserID());
					
					//���첿���ϴ��ĸ���
					String filesql="from Wbfileinfo where busTable='ElevatorTransferCaseRegister' and materSid='"+billno+"'";
					List wbfilelist=hs.createQuery(filesql).list();
					if(wbfilelist!=null && wbfilelist.size()>0){
						request.setAttribute("wbfilelist", wbfilelist);
					}
							
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				request.setAttribute("CSheight",CSheight);
				request.setAttribute("CSwidth", CSwidth);
				request.setAttribute("CIheight", CIheight);
				request.setAttribute("CIwidth", CIwidth);
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
			
		
			
			if(request.getAttribute("error") == null || request.getAttribute("error").equals(""))//׼����������ҳ��
			{
				dform.set("tokenId",new Long(tokenid));
				dform.set("taskId",new Long(taskid));
				dform.set("taskName",taskname);
				request.setAttribute("taskName", taskname);
				dform.set("flowname", WorkFlowConfig.getProcessDefine("elevatortransfercaseregisterProcessName"));
				dform.set("status", status);
				dform.set("billno",billno);
				dform.set("id",billno);				
				dform.set("tasktype",tasktype);
				
			}
		
		} 
	}
	
	
	/**
	 * �����޸�ά�����ӵ����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toSaveApproveModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();

		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) request.getSession().getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String flowname = (String) dform.get("flowname");// ��������
		//if (flowname != null && !flowname.trim().equals("")) {
		//	flowname = new String(flowname.getBytes("ISO-8859-1"), "gbk");
		//}
		
		String auditor=request.getParameter("auditorId");
		if(auditor==null || "".equals(auditor)){
			auditor=(String)dform.get("auditor");
		}

		Session hs = null;
		Transaction tx = null;
		JbpmExtBridge jbpmExtBridge = null;
		ElevatorTransferCaseRegister register=null;
		if(isTokenValid(request, true)){
		//��鸽�����ܳ�������M��һ���ϴ����ļ��ܴ�С���ܳ���5M,����ʧ��!
		Boolean maxLengthExceeded = (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);			
		if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("doc.file.size.check.save"));
			forward = mapping.findForward("returnApproveModify");
		}else{
			String billno=(String)dform.get("billno");
			String factoryCheckResult=(String)dform.get("factoryCheckResult");
			String factoryCheckResult2=(String)dform.get("factoryCheckResult2");
			String r2=(String)dform.get("r2");
			String checkDate=(String)dform.get("checkDate");
			String processStatus=(String)dform.get("processStatus");
			Long taskid = (Long) dform.get("taskId");// ��������
			String taskname = (String) dform.get("taskName");// ��������
			String approveResult = (String) dform.get("approveResult");// �������
			String approveRem = (String) dform.get("approveRem");// �쵼���
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				dispose(dform, request, errors, billno);
				
				List list=savePicter(dform, request, response, "HandoverElevatorCheckItemRegister.file.upload.folder", billno);
				boolean b=false;
				String processName="";
				if(list!=null || list.size()>0){
					b=savePicterTodb(request, list, billno);
						register=(ElevatorTransferCaseRegister) hs.get(ElevatorTransferCaseRegister.class, billno);
						String processDefineID = Grcnamelist1.getProcessDefineID("elevatortransfercaseregister", register.getStaffName());
						String traname="";
						for(Object object : this.getTransition(hs.connection(),3,null,taskid)){
							traname = (String) ((HashMap) object).get("tranname");
						}
						/*=============== ��������������ʼ =================*/
						jbpmExtBridge = new JbpmExtBridge();
						HashMap paraMap = new HashMap();
						ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskId").toString()));
						
						pd.addAppointActors("");// ����̬��ӵ�����������
						if(factoryCheckResult.equals("�ϸ�")){
							pd.setSelpath("Y");
							processName="�ر��������";
							Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�ر��������", "%");// ��������
						}else if(factoryCheckResult.equals("���ϸ�")){
							pd.setSelpath("N");
							processName="���첿�����";
							Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "���첿�����", "%");// ��������
						}
						
						pd = jbpmExtBridge.goToNext(taskid, approveResult, userInfo.getUserID(), paraMap);// ���
						/**==================== ���̽��� =======================**/
	//					String hql="update ElevatorTransferCaseRegister set checkTime='"+CommonUtil.getNowTime()+"',factoryCheckResult='"+factoryCheckResult+"',processStatus='"+processStatus+"',processName='"+processName+"',status='"+pd.getStatus()+"',tokenId='"+pd.getToken()+"' where billno='"+billno+"'";
						register.setFactoryCheckResult(factoryCheckResult);		
						register.setFactoryCheckResult2(factoryCheckResult2);	
						register.setR2(r2);
						register.setStatus(pd.getStatus());
						register.setTokenId(pd.getToken());
						register.setProcessName(pd.getNodename());
						register.setIsDeductions("");
						register.setDeductMoney(null);
						register.setProcessResult("");
						
						String[] datearr=checkDate.split(" ");
						register.setCheckDate(datearr[0]);
						register.setCheckTime2(datearr[1]);
						
						hs.save(register);
						
						ElevatorTransferCaseProcess process = new ElevatorTransferCaseProcess();
						process.setElevatorTransferCaseRegister(register);
						process.setTaskId(pd.getTaskid().intValue());// �����
						process.setTaskName(taskname);// ��������
						process.setTokenId(pd.getToken());// ��������
						process.setUserId(userInfo.getUserID());
						process.setDate1(CommonUtil.getToday());
						process.setTime1(CommonUtil.getTodayTime());
						process.setApproveResult(approveResult);
						process.setApproveRem(approveRem);
						hs.save(process);
				}else{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.uploading.failed"));
				}
					tx.commit();			
	
			} catch (Exception e1) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
				try {
					tx.rollback();
					if (jbpmExtBridge != null) {
						jbpmExtBridge.setRollBack();
					}
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
					if(jbpmExtBridge!=null){
						jbpmExtBridge.close();
					}
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "Hibernate close error!");
				}
			}
		  }
		}else{
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));//�����ظ��ύ
		}

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("application.submit.sucess"));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		request.setAttribute("display", "Y");
		forward = mapping.findForward("returnApprove");
		return forward;
	}
	
	public void dispose(ActionForm form, HttpServletRequest request,ActionErrors errors,String billno){
		Session hs = null;
		Transaction tx = null;
		
		String[] hecirJnlno=request.getParameterValues("jnlno");
		String[] examType=request.getParameterValues("examType");
		String[] checkItem=request.getParameterValues("checkItem");
		String[] issueCoding=request.getParameterValues("issueCoding");
		String[] issueContents=request.getParameterValues("issueContents");
		String[] rem=request.getParameterValues("rem");
		String[] isRecheck=request.getParameterValues("isRecheck");
		String[] isOk=request.getParameterValues("isOk");
		String[] scjnlno=request.getParameterValues("scjnlno");
		String[] isyzg=request.getParameterValues("isyzg");//�Ƿ�������
		String elevatorAddress=request.getParameter("elevatorAddress");
		String projectProvince=request.getParameter("projectProvince");
		String hecirJnlnos="";
		if(hecirJnlno!=null && !hecirJnlno.equals("")){
			for(int i=0;i<hecirJnlno.length;i++){
				if(hecirJnlno[i]!=null&&!"".equals(hecirJnlno[i])){
				hecirJnlnos+=i==0 ? hecirJnlno[i] : "','"+hecirJnlno[i];
			    }
			}
		}
		
		HandoverElevatorCheckItemRegister hecir = null;
		try {
			hs=HibernateUtil.getSession();
			tx=hs.beginTransaction();
			ElevatorTransferCaseRegister register=(ElevatorTransferCaseRegister) hs.get(ElevatorTransferCaseRegister.class, billno);
			if(!hecirJnlnos.equals("")){
				hs.createQuery("delete HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno in(select jnlno from HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"'))").executeUpdate();
				hs.createQuery("delete HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"')").executeUpdate();
			}else
			{
				hs.createQuery("delete HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno in(select jnlno from HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"')").executeUpdate();
				hs.createQuery("delete HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"'").executeUpdate();
			}
			//hs.createQuery("delete ElevatorTransferCaseProcess h where h.elevatorTransferCaseRegister.billno='"+billno+"'").executeUpdate();
			register.setElevatorAddress(elevatorAddress);//����λ��
			register.setProjectProvince(projectProvince);//��Ŀʡ��
			hs.update(register);
			hs.flush();
			   
			for(int i=0;i<examType.length;i++){
               if(hecirJnlno[i]!=null && !hecirJnlno[i].equals("")){
                		hecir = (HandoverElevatorCheckItemRegister) hs.get(HandoverElevatorCheckItemRegister.class, hecirJnlno[i]);	
                    	hecir.setRem(rem[i]);
                    	hecir.setIsDelete("N");
                    	hecir.setDeleteRem("");
                    	hecir.setIsyzg(isyzg[i]);//�Ƿ�������
                    	hs.update(hecir);
                }else{
                hecir = new HandoverElevatorCheckItemRegister();
				String jnlno="";
				try {
					String[] billno1 = CommonUtil.getBillno(CommonUtil.getToday().substring(2,4),"HandoverElevatorCheckItemRegister", 1);	
					jnlno=billno1[0];
					}catch (Exception e) {
						e.printStackTrace();
					}
				hecir.setJnlno(jnlno);
				hecir.setIsDelete("N");
				hecir.setExamType(examType[i]);
				hecir.setIssueCoding(issueCoding[i]);
				hecir.setCheckItem(checkItem[i]);
				hecir.setIsRecheck(isRecheck[i]);
				hecir.setIssueContents(issueContents[i]);
				hecir.setRem(rem[i]);
				hecir.setIsyzg(isyzg[i]);//�Ƿ�������
				hecir.setElevatorTransferCaseRegister(register);
				hs.save(hecir);
				hs.flush();
                }
			} 
             if(isOk!=null&&isOk.length>0){
            	 for(int i=0 ; i<isOk.length;i++){
            		 HandoverElevatorSpecialRegister r=(HandoverElevatorSpecialRegister) hs.get(HandoverElevatorSpecialRegister.class,scjnlno[i]) ;
            	     r.setIsOk(isOk[i]);
            	     hs.update(r);
            	     hs.flush();
            	 }
             }  
			//tx.rollback();
			tx.commit();
			
		} catch (DataStoreException e1) {
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
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
	}
	/**
	 * �ļ�ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDeleteFileDispose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		Session hs = null;
		Transaction tx = null;
		String id = request.getParameter("filesid");
		String iswbfile=request.getParameter("iswbfile");
		
		try {
			id=URLDecoder.decode(id, "utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		List list=null;
		String folder = request.getParameter("folder");
		//�������������
        PrintWriter out=null;
        //������֤��������ͬ��������Ϣ	 
		if(null == folder || "".equals(folder)){
			folder ="HandoverElevatorCheckItemRegister.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		int zNum=0,yNum=0;
		
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if(id!=null && id.length()>0){
				
				String fileName="";
				String filePath="";
				if(iswbfile!=null && iswbfile.equals("Y")){
					Wbfileinfo ebff=(Wbfileinfo) hs.get(Wbfileinfo.class,Integer.valueOf(id));
					fileName=ebff.getNewFileName();
					filePath=ebff.getFilePath();
					hs.delete(ebff);
				}else{
					HandoverElevatorCheckFileinfo hecf=(HandoverElevatorCheckFileinfo) hs.get(HandoverElevatorCheckFileinfo.class,Integer.valueOf(id));
					fileName=hecf.getNewFileName();
					filePath=hecf.getFilePath();
					hs.delete(hecf);
				}
				
				HandleFile hf = new HandleFileImpA();
				String localpath=folder+"/"+filePath+fileName;
				hf.delFile(localpath);
			}
			
			response.setContentType("text/xml; charset=UTF-8");
			
			out = response.getWriter();
			
			out.println("<response>");
			out.println("<res>" + "Y" + "</res>");
			out.println("</response>");
			
		     tx.commit();
		} catch (Exception e) {
			try {
				out.println("<response>");
				out.println("<res>" + "N" + "</res>");
				out.println("</response>");
				tx.rollback();
				
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(out!=null){
					out.close();					
				}
				if(hs!=null){
					hs.close();
				}
			} catch (HibernateException hex) {
				DebugUtil.print(hex, "Hibernate close error!");
			}
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
	public ActionForward toDownloadFileDispose(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String filename=request.getParameter("filesname");
		String fileOldName=request.getParameter("fileOldName");
		String filePath=request.getParameter("filePath");
		String folder=request.getParameter("folder");
		if(folder==null || "".equals(folder)){
			folder="HandoverElevatorCheckItemRegister.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		filename=URLDecoder.decode(filename, "utf-8");
		filePath=URLDecoder.decode(filePath, "utf-8");
		fileOldName=URLDecoder.decode(fileOldName, "utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileOldName, "utf-8"));

		fis = new FileInputStream(folder+"/"+filePath+filename);
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
		return null;
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
			HttpServletResponse response, String folder, String billno) {
		List returnList = new ArrayList();
		folder = PropertiesUtil.getProperty(folder).trim();//
		DynaActionForm dform = (DynaActionForm) form;
		Integer checkNum=(Integer) dform.get("checkNum");
		
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
								String isdelete=request.getParameter("isdelete_"+key);
								String checkItem=request.getParameter("item_"+key);
								String examType=request.getParameter("type_"+key);
								String issueCoding=request.getParameter("coding_"+key);
								String newFileName="HandoverElevatorCheckItemRegister"+"_"+billno+"_"+checkNum.toString()+"_"+key+"_"+formFile.getFileName();
								map.put("checkItem", checkItem);
								map.put("oldfilename", formFile.getFileName());
								map.put("filepath", CommonUtil.getNowTime("yyyy-MM-dd")+"/");
								map.put("fileSize", new Double(formFile.getFileSize()));
								map.put("fileformat",formFile.getContentType());
								map.put("examType", examType);
								map.put("issueCoding", issueCoding);
								map.put("newFileName", newFileName);
								map.put("isdelete", isdelete);
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
	public boolean savePicterTodb(HttpServletRequest request,List fileInfoList,String billno){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs = null;
		Transaction tx = null;
		boolean saveFlag = true;
		if(null != fileInfoList && fileInfoList.size()>0){
			String sql="";
			HandoverElevatorCheckItemRegister msr=null;
			HandoverElevatorCheckFileinfo fileInfo=null;
			Wbfileinfo wbinfo=null;
			try {
				hs=HibernateUtil.getSession();
				tx=hs.beginTransaction();
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					
					String checkItem=(String)map.get("checkItem");
					String issueCoding=(String)map.get("issueCoding");
					String examType=(String)map.get("examType");
					String isdelete=(String)map.get("isdelete");//�Ƿ񳧼첿��ɾ�����ϴ��ĸ���
					
					if(checkItem.equals("Wbfileinfo") && issueCoding.equals("Wbfileinfo") && examType.equals("Wbfileinfo") ){
						//���첿���ϴ��ĸ��� ���浽��ά��ģ���ϴ��ļ���Ϣ��
						wbinfo=new Wbfileinfo();
						wbinfo.setBusTable("ElevatorTransferCaseRegister");
						wbinfo.setMaterSid(billno);
						wbinfo.setOldFileName((String)map.get("oldfilename"));
						wbinfo.setNewFileName((String)map.get("newFileName"));
						wbinfo.setFileSize(Double.valueOf(map.get("fileSize").toString()));
						wbinfo.setFilePath((String)map.get("filepath"));
						wbinfo.setFileFormat((String)map.get("fileformat"));
						wbinfo.setUploadDate(CommonUtil.getNowTime());
						wbinfo.setUploader(userInfo.getUserID());
						wbinfo.setRemarks("���첿���ϴ�����");
						hs.save(wbinfo);
						
					}else{
					
						String hql="from HandoverElevatorCheckItemRegister hecir where hecir.elevatorTransferCaseRegister.billno='"+billno+"'" +
								" and hecir.checkItem='"+checkItem+"' and hecir.issueCoding ='"+issueCoding+"'" +
								" and hecir.examType ='"+examType+"'";
						List list=hs.createQuery(hql).list();
	
						if(list!=null && list.size()>0){
							msr=(HandoverElevatorCheckItemRegister) list.get(0);
							//String newFilename=(String) map.get("newFileName");
							fileInfo=new HandoverElevatorCheckFileinfo();
							fileInfo.setHandoverElevatorCheckItemRegister(msr);
							fileInfo.setOldFileName((String)map.get("oldfilename"));
							fileInfo.setNewFileName((String)map.get("newFileName"));
							fileInfo.setFileSize(Double.valueOf(map.get("fileSize").toString()));
							fileInfo.setFilePath((String)map.get("filepath"));
							fileInfo.setFileFormat((String)map.get("fileformat"));
							fileInfo.setUploadDate(CommonUtil.getNowTime());
							fileInfo.setUploader(userInfo.getUserID());
							fileInfo.setExt1(isdelete);
							hs.save(fileInfo);
						}
					}
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
	 * Type 0:�������̶��壬node start��; 1:����task id��;2:����node id ��,3:TaskInstance
	 * @param type
	 * @param process	����
	 * @param tasknode  task/node
	 * @return
	 * @throws SQLException 
	 */
	public List getTransition(Connection con,int type,String process,long tasknode) throws SQLException{
		DBInterface db=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		db.setCon(con);
		String sql="Sp_JbpmGetTransition "+type+",'"+process+"',"+tasknode;
		return db.queryToList(sql);
		
	}
	
}
