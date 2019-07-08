package com.gzunicorn.struts.action.engcontractmanager.maintcontractquote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.apache.commons.beanutils.BeanUtils;
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

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.dao.DBInterface;
import com.gzunicorn.common.dao.ObjectAchieveFactory;
import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.basedata.customer.Customer;

import com.gzunicorn.hibernate.engcontractmanager.ContractFileinfo.ContractFileinfo;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquoteprocess.MaintContractQuoteProcess;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.workflow.bo.JbpmExtBridge;

public class MaintContractQuoteJbpmAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractQuoteJbpmAction.class);
	
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
	 * �����޸Ľ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	public ActionForward  toPrepareUpdateApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  throws IOException, ServletException{

		request.setAttribute("navigator.location", "ά�������������� >> ��  ��");
		ActionErrors errors = new ActionErrors();
		
		display(form, request, errors,"Y");
		request.setAttribute("approve", "N");
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toApproveModify");
	}
	/**
	 * �����޸�
	 * page or modifiy page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toSaveUpdateApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		DynaActionForm dform = (DynaActionForm)form;
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ActionErrors errors = new ActionErrors();
		
		Session hs = null;
		Transaction tx = null;
		
		String approveresult=(String)dform.get("approveresult");
		String taskname=(String)dform.get("taskname");
		String billNo=(String)dform.get("billNo");

		String flowname=(String)dform.get("flowname");
		//if(flowname!=null && !flowname.trim().equals("")){
		//	flowname=new String(flowname.getBytes("ISO-8859-1"),"gbk"); 
		//}

		JbpmExtBridge jbpmExtBridge=null;
		
		try {
			
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();			

			MaintContractQuoteMaster master = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, billNo);
			hs.createQuery("delete from MaintContractQuoteDetail where billNo='"+billNo+"'").executeUpdate();//ɾ������ˮ��������ϸ��Ϣ	
		
			String processDefineID = Grcnamelist1.getProcessDefineID("enginequotemaster", master.getOperId());// ���̻���id

    		/**======== ��������������ʼ ========*/
			jbpmExtBridge=new JbpmExtBridge();
	    	ProcessBean pd=jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskid").toString()));
	    	
	    	pd.addAppointActors("");// ����̬��ӵ�����������
	    	//Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ά���ֲ������", master.getOperId());// ��������
			//Grcnamelist1.setJbpmAuditopers_class(pd, processDefineID, PropertiesUtil.getProperty("MaintStationManagerJbpm"), master.getMaintDivision(), master.getMaintStation());// ��������
	    	Grcnamelist1.setJbpmAuditopers_roleid(pd,"Y",master.getMaintDivision());
	    	
	    	pd=jbpmExtBridge.goToNext(Long.parseLong(dform.get("taskid").toString()),approveresult,userInfo.getUserID(),null);//���
	    	/**======== ���������������� ========*/
	    	
	    	//�����������������Ϣ
	    	MaintContractQuoteProcess process=new MaintContractQuoteProcess();
			process.setBillNo(billNo.trim());
			process.setTaskId(new Integer(pd.getTaskid().intValue()));//�����
			process.setTaskName(taskname);//��������
			process.setTokenId(pd.getToken());//��������
			process.setUserId(userInfo.getUserID());
			process.setDate1(CommonUtil.getToday());
			process.setTime1(CommonUtil.getTodayTime());
			process.setApproveResult(approveresult);
			process.setApproveRem((String)dform.get("approverem"));
			hs.save(process);
			
			/** ������ֹ����ʱ���ı���һ�ݺ�ͬ��״̬
			if(approveresult!=null && approveresult.trim().equals("��ֹ����")){
				String hcs=master.getHistContractStatus();
				if(hcs!=null && !hcs.trim().equals("")){
					MaintContractMaster master2 = (MaintContractMaster) hs.get(MaintContractMaster.class, master.getHistoryBillNo());
					master2.setContractStatus(hcs.trim());
					hs.save(master2);
				}
	    	}
			 */
	    	//��������Ϣ
			String ccastr="";
			String[] ccarr=request.getParameterValues("contractContentApply");
			if(ccarr!=null && ccarr.length>0){
				ccastr=Arrays.toString(ccarr);
				ccastr=ccastr.replaceAll("\\[", "");
				ccastr=ccastr.replaceAll("\\]", "");
			}

			//String customerArea=request.getParameter("customerArea");//�ͻ�����
			
			BeanUtils.copyProperties(master, dform); // ������������ֵ
			master.setContractContentApply(ccastr);
			master.setCustomerArea("");
			master.setBillNo(billNo);
			master.setSubmitType("Y");// �ύ��־
	    	master.setTokenId(pd.getToken());
	    	master.setStatus(pd.getStatus());
	    	master.setProcessName(pd.getNodename());
			master.setOperId(userInfo.getUserID());// ¼����
			master.setOperDate(CommonUtil.getNowTime());// ¼��ʱ��
			hs.saveOrUpdate(master);
			
			String ccapply=master.getContractContentApply();//��ͬ�������������
					
			// ������Ϣ
			MaintContractQuoteDetail detail = null;
			
			String[] elevatorNo = request.getParameterValues("elevatorNo");// ���ݱ��
			String[] elevatorType = request.getParameterValues("elevatorType");// ��������
			String[] floor = request.getParameterValues("floor");// ��
			String[] stage = request.getParameterValues("stage");// վ
			String[] door = request.getParameterValues("door");// ��
			String[] high = request.getParameterValues("high");// �����߶�
			//String[] num = request.getParameterValues("num");// ̨��
			String[] weight = request.getParameterValues("weight");// ����
			String[] speed = request.getParameterValues("speed");// �ٶ�
			String[] elevatorAge = request.getParameterValues("elevatorAge");// ��������
			String[] jyMoney = request.getParameterValues("jyMoney");//����
			String[] standardQuoteDis = request.getParameterValues("standardQuoteDis");// ��׼���ۼ�������
			//String[] customerArea = request.getParameterValues("customerArea");// �ͻ�����
			String[] standardQuote = request.getParameterValues("standardQuote");// ��׼����
			String[] finallyQuote = request.getParameterValues("finallyQuote");// ���ձ���
			String[] salesContractNo = request.getParameterValues("salesContractNo");// ���ۺ�ͬ��
			String[] projectName = request.getParameterValues("projectName");// ��Ŀ����
			String[] projectAddress = request.getParameterValues("projectAddress");// ��Ŀ��ַ
			String[] contractPeriod = request.getParameterValues("contractPeriod");//��ͬ��Ч��
			String[] elevatorParam = request.getParameterValues("elevatorParam");
			String[] signWay = request.getParameterValues("signWay");
			
			List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
						
			if(elevatorNo != null && elevatorNo.length > 0){								
				for (int i = 0; i < elevatorNo.length; i++) {
					detail = new MaintContractQuoteDetail();
					detail.setBillNo(billNo);
					detail.setElevatorNo(elevatorNo[i]);					
					detail.setElevatorType(bd.getOptionId(elevatorType[i], elevatorTypeList));//�������ʹ���	
					detail.setFloor(Integer.valueOf(floor[i]));
					detail.setStage(Integer.valueOf(stage[i]));
					detail.setDoor(Integer.valueOf(door[i]));					
					detail.setHigh(Double.valueOf(high[i]));
					detail.setElevatorParam(elevatorParam[i]);
					detail.setNum(1);		
					//detail.setElevatorAge(Double.valueOf(0));
					//detail.setCustomerArea(customerArea[i]);
					detail.setStandardQuote(Double.valueOf(standardQuote[i]));
					detail.setFinallyQuote(Double.valueOf(finallyQuote[i]));	
					detail.setSalesContractNo(salesContractNo[i]);
					detail.setProjectName(projectName[i]);
					detail.setProjectAddress(projectAddress[i]);
					detail.setContractPeriod(contractPeriod[i]);
					detail.setSignWay(signWay[i]);
					
					detail.setWeight(weight[i]);
					detail.setSpeed(speed[i]);
					detail.setElevatorAge(Double.parseDouble(elevatorAge[i]));
					detail.setStandardQuoteDis(standardQuoteDis[i]);
					if(jyMoney!=null && !jyMoney[i].trim().equals("")){
						detail.setJyMoney(Double.parseDouble(jyMoney[i]));
					}
					
					//��ͬ������������������������ί���ҷ����Ᵽ������ôǩ��ʽ�޸�Ϊ�����Ᵽ��
					if(ccapply.indexOf("100")>-1){
						detail.setSignWay("XQ");
					}
					
					hs.save(detail);
				}
			}	
			
			// �����ļ�
			String path = "MaintContractQuoteMaster.file.upload.folder";
			String tableName = "MaintContractQuoteMaster";//���� ά�����������
			String userid = userInfo.getUserID();

			List fileInfoList = this.saveFile(dform,request,response, path, billNo);
			boolean istrue=this.saveFileInfo(hs,fileInfoList,tableName,billNo, userid);

			tx.commit();
	
		} catch (Exception e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
			if (jbpmExtBridge != null) {
				jbpmExtBridge.setRollBack();
			}
			if (tx != null) {
				tx.rollback();
			}
			e2.printStackTrace();
			log.error(e2.getMessage());
			
		} finally {
			if(jbpmExtBridge!=null){				
				jbpmExtBridge.close();
			}
			try {
				if(hs!=null){
					hs.close();
				}
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = null;

		if(errors.isEmpty()){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("application.submit.sucess"));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		request.setAttribute("display","Y");
		forward=mapping.findForward("returnApprove");
		
		return forward;
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

		request.setAttribute("navigator.location", "ά�������������� >> �� ��");

		ActionErrors errors = new ActionErrors();

		display(form, request, errors,"N"); //�鿴����
		
		request.setAttribute("approve", "Y");
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toApprove");
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
		
		String billNo = (String) dform.get("billNo");
		String taskname = (String) dform.get("taskname");// ��������
		String taskid = (String) dform.get("taskid");// ��������
		String approveresult = (String) dform.get("approveresult");// �������
		String approverem = (String) dform.get("approverem");// �������
			
		Session hs = null;
		Transaction tx = null;
		JbpmExtBridge jbpmExtBridge = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if (billNo != null && !billNo.trim().equals("")) {
				
				MaintContractQuoteMaster master = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, billNo.trim());
				
				String processDefineID = Grcnamelist1.getProcessDefineID("enginequotemaster", master.getOperId());// ���̻���id
				
				/*=============== ��������������ʼ =================*/
				jbpmExtBridge = new JbpmExtBridge();
				HashMap paraMap = new HashMap();
				ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskid").toString()));
				
				pd.addAppointActors("");// ����̬��ӵ�����������				
				if(approveresult!=null && approveresult.trim().equals("��ͬ��")){
					pd.addAppointActors(master.getOperId());
				}else if("ά���ֲ������".equals(taskname)){
					if(master.getBusinessCosts()>0){
						pd.setSelpath("Y");
						//Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ά���ܲ������", master.getOperId());// ��������
						Grcnamelist1.setJbpmAuditopers_roleid(pd,"N","");
					}else{
						pd.setSelpath("N");
					}					
				}else{
					Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, approveresult.replace("�ύ", ""), master.getOperId());// ��������
				}
				
				pd = jbpmExtBridge.goToNext(Long.parseLong(dform.get("taskid").toString()), approveresult, userInfo.getUserID(), paraMap);// ���
				/*=============== ���������������� =================*/
				
				// ��������Ϣ
				master.setTokenId(pd.getToken());
				master.setStatus(pd.getStatus());
				master.setProcessName(pd.getNodename());			
				hs.save(master);

				// �����������������Ϣ
				MaintContractQuoteProcess process = new MaintContractQuoteProcess();
				process.setBillNo(billNo);
				process.setTaskId(pd.getTaskid().intValue());// �����
				process.setTaskName(taskname);// ��������
				process.setTokenId(pd.getToken());// ��������
				process.setUserId(userInfo.getUserID());
				process.setDate1(CommonUtil.getToday());
				process.setTime1(CommonUtil.getTodayTime());
				process.setApproveResult(approveresult);
				process.setApproveRem(approverem);
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
	public void display(ActionForm form, HttpServletRequest request,ActionErrors errors,String isupdate)
			throws UnsupportedEncodingException{
		
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String tokenid = request.getParameter("tokenid");// ��������
		if (tokenid == null) {
			tokenid = (String) dform.get("tokenid");
		}
		String taskid = request.getParameter("taskid");// ����id
		if (taskid == null) {
			taskid = (String) dform.get("taskid");
		}
		String taskname = request.getParameter("taskname");// ��ǰ��������
		if (taskname == null) {
			taskname = (String) dform.get("taskname");
		}else {
			taskname = new String(taskname.getBytes("ISO-8859-1"), "gbk");
		}
		String tasktype = request.getParameter("tasktype");// ��������
		if (tasktype == null) {
			tasktype = (String) dform.get("tasktype");
		}

		Session hs = null;
		MaintContractQuoteMaster master = null;
		List maintContractQuoteDetailList = null;
		String billNo="";
		Integer status = null;
		
		if (tokenid != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from MaintContractQuoteMaster where tokenId = '"+ tokenid + "'");
				List list = query.list();
				if (list != null && list.size() > 0) {

					// ����Ϣ
					master = (MaintContractQuoteMaster) list.get(0);
					
					//�Ƿ��޸�
					if(isupdate!=null && isupdate.trim().equals("N")){
						List pdlist=bd.getPullDownAllList("MaintContractQuoteMaster_PaymentMethodApply");
						String pmaname=bd.getOptionName(master.getPaymentMethodApply(), pdlist);
						master.setR4(pmaname);
						
						//��ͬ������������
						String ccastrname="";
						String ccastr=master.getContractContentApply();
						if(ccastr!=null &&!ccastr.trim().equals("")){
							List ccalist=bd.getPullDownAllList("MaintContractQuoteMaster_ContractContentApply");
							String[] ccarr=ccastr.split(",");
							for(int i=0;i<ccarr.length;i++){
								if(i==ccarr.length-1){
									ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist);
								}else{
									ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist)+"��";
								}
							}
						}
						master.setContractContentApply(ccastrname);
					}else{
						request.setAttribute("PaymentMethodList", bd.getPullDownList("MaintContractQuoteMaster_PaymentMethodApply"));
						request.setAttribute("ContractContentList", bd.getPullDownList("MaintContractQuoteMaster_ContractContentApply"));
					}
					
					//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ
					String roleid=userInfo.getRoleID();
					if(roleid!=null && roleid.trim().equals("A03")){
						master.setR2(bd.getName(hs, "Storageid","storageName", "storageID", master.getMaintStation()));
						master.setR1(roleid);
					}
					
					billNo = master.getBillNo();
					status = master.getStatus();
					request.setAttribute("maintContractQuoteBean", master);
					request.setAttribute("attnName", bd.getName(hs, "Loginuser","username", "userid",master.getAttn())); //����������
					request.setAttribute("maintDivisionName", bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision())); //ά���ֲ�����
					if(!"toPrepareUpdateApprove".equals(request.getParameter("method"))){
						request.setAttribute("maintStationName", bd.getName(hs, "Storageid","storageName", "storageID", master.getMaintStation())); //ά��վ����
					}
					
					// ����ά��վ�б�	
					request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));	

					// ������Ϣ��ϸ�б�
					query = hs.createQuery("from MaintContractQuoteDetail where billNo = '"+ billNo + "'");
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
					maintContractQuoteDetailList = query.list();
					for (Object object : maintContractQuoteDetailList) {
						MaintContractQuoteDetail detail = (MaintContractQuoteDetail) object;
						detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
						detail.setR4(bd.getName("Pulldown", "pullname", "id.pullid",detail.getSignWay()));//ǩ��ʽ
					}
					request.setAttribute("maintContractQuoteDetailList",maintContractQuoteDetailList);
					
					//����������Ϣ
					query = hs.createQuery("from MaintContractQuoteProcess where billNo = '"+ billNo + "' order by itemId");
					List processApproveList = query.list();
					for (Object object : processApproveList) {
						MaintContractQuoteProcess process = (MaintContractQuoteProcess) object;
						process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
					}
					request.setAttribute("processApproveList",processApproveList);
					
					//�������������
					List tranList=this.getTransition(hs.connection(),3,null,Long.parseLong(taskid));
					request.setAttribute("ResultList",tranList);
					
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				
				//��ȡ�Ѿ��ϴ��ĸ���
				List filelst=this.FileinfoList(hs, billNo, "MaintContractQuoteMaster");
				request.setAttribute("updatefileList", filelst);
				
				//��ȡ�����׼���۵����ϵ��
				request.setAttribute("returnhmap", bd.getCoefficientMap());
				
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
				dform.set("tokenid",tokenid);
				dform.set("taskid",taskid);				
				dform.set("taskname",taskname);
				dform.set("flowname", WorkFlowConfig.getProcessDefine("enginequotemasterProcessName"));
				dform.set("status", status);
				dform.set("billNo",billNo);
				dform.set("id",billNo);				
				dform.set("tasktype",tasktype);
			}
		
		} 
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
		//System.out.println(sql);
		return db.queryToList(sql);
		
	}

	/**
	 * �����ϴ��ļ�
	 */
	public List saveFile(ActionForm form,HttpServletRequest request, HttpServletResponse response,
			String path,String billno){
		List returnList = new ArrayList();
		int filenum=0;
		int fileCount=0;

		path =  PropertiesUtil.getProperty(path).trim();//�ϴ�Ŀ¼ 

		 FormFile formFile = null;
		 Fileinfo file=null;
		 if (form.getMultipartRequestHandler() != null) {
			 Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			 if (hash != null) {
				 
				 Iterator it = hash.values().iterator();
				 HandleFile hf = new HandleFileImpA();
				 while (it.hasNext()) {				 
					 
					 fileCount++;
					 formFile = (FormFile) it.next();
					 if (formFile != null) {
						 try {
							 String today=DateUtil.getCurDate();
							 String time=DateUtil.getDateTime("yyyyMMddHHmmss");
							 String newfilename=time+"_"+fileCount+"_"+formFile.getFileName();
							 Map map = new HashMap();
							 map.put("oldfilename", formFile.getFileName());
							 map.put("newfilename",newfilename);
							 map.put("filepath", today.substring(0,7)+"/");
							 map.put("filesize", new Double(formFile.getFileSize()));
							 map.put("fileformat",formFile.getContentType());
							 map.put("rem","");

							// �����ļ����ļ�ϵͳ
							hf.createFile(formFile.getInputStream(),path+today.substring(0,7)+"/", newfilename);
							returnList.add(map);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
					 }
				 }
			 }
		 }
		return returnList;
	}
	
	/**
	 * �����ļ���Ϣ�����ݿ�
	 */
	public boolean saveFileInfo(Session hs,List fileInfoList,String tableName,String billno,String userid){
		boolean saveFlag = true;
		ContractFileinfo file=null;
		if(null != fileInfoList && !fileInfoList.isEmpty()){
			
			try {
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					 String oldfilename =(String) map.get("oldfilename");
					 String newfilename =(String) map.get("newfilename");
					 String filepath =(String) map.get("filepath");
					 Double filesize =(Double) map.get("filesize");
					 String fileformat =(String) map.get("fileformat");
					 String rem =(String) map.get("rem");
					 
					 
					 file = new ContractFileinfo();
					 file.setMaterSid(billno);
					 file.setBusTable(tableName);
					 file.setOldFileName(oldfilename);
					 file.setNewFileName(newfilename);
					 file.setFilePath(filepath);
					 file.setFileSize(filesize);
					 file.setFileFormat(fileformat);
					 file.setUploadDate(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
					 file.setUploader(userid);
					 file.setRemarks(rem);
					 
					 hs.save(file);
					 hs.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
			}
		}
		return saveFlag;
	}
	/**
	 * ��ȡ���ϴ������б�
	 */
	public List FileinfoList(Session hs, String MaterSid, String BusTable)
	  {
	    List rt = new ArrayList();
	    Connection con = null;
	    try {
	      con = hs.connection();
	      String sql = "select a.*,b.username as UploaderName  from  ContractFileinfo a ,loginuser b " +
	      		"where a.Uploader = b.userid and a.MaterSid = '" + MaterSid + "'  and a.BusTable = '" + BusTable + "'";
	      DataOperation op = new DataOperation();
	      op.setCon(con);
	      rt = op.queryToList(sql);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return rt;
	  }

	
}
