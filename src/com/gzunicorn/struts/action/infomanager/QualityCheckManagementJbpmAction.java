package com.gzunicorn.struts.action.infomanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
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

import org.apache.commons.beanutils.BeanUtils;
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
import com.gzunicorn.common.util.JSONUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.basedata.customer.Customer;

import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquoteprocess.MaintContractQuoteProcess;
import com.gzunicorn.hibernate.infomanager.markingscoreregister.MarkingScoreRegister;
import com.gzunicorn.hibernate.infomanager.markingscoreregisterdetail.MarkingScoreRegisterDetail;
import com.gzunicorn.hibernate.infomanager.markingscoreregisterfileinfo.MarkingScoreRegisterFileinfo;
import com.gzunicorn.hibernate.infomanager.qualitycheckmanagement.QualityCheckManagement;
import com.gzunicorn.hibernate.infomanager.qualitycheckprocess.QualityCheckProcess;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.workflow.bo.JbpmExtBridge;

public class QualityCheckManagementJbpmAction extends DispatchAction {

	Log log = LogFactory.getLog(QualityCheckManagementJbpmAction.class);
	
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
		if(!"toDownloadFileRecord1".equals(name)){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "myTaskOA", null);
			/** **********�����û�Ȩ�޹���*********** */
		}
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

		request.setAttribute("navigator.location", "ά������������ >> ��  ��");
		ActionErrors errors = new ActionErrors();
		
		display(form, request, errors);

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("toModifyApprove");
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


		ActionForward forward = null;
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		Session hs = null;
		Transaction tx = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		JbpmExtBridge jbpmExtBridge=null;

		//��鸽�����ܳ�������M��һ���ϴ����ļ��ܴ�С���ܳ���5M,����ʧ��!
		Boolean maxLengthExceeded = (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);			
		if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("doc.file.size.check.save"));
			forward = mapping.findForward("returnApproveModify");
		}else{
		
			String billno=(String)dform.get("billno");
			String elevatorNo = (String) dform.get("elevatorNo");
			String checksDateTime = (String) dform.get("checksDateTime");
			
			String supervOpinion = (String) dform.get("supervOpinion");
			double totalPoints = (Double)dform.get("totalPoints");
			String scoreLevel = (String)dform.get("scoreLevel");
			
			String taskname = (String) dform.get("taskname");// ��������
			Integer taskid = (Integer) dform.get("taskid");// ��������
			String approveresult = (String) dform.get("approveresult");// �������
			String approverem = (String) dform.get("approverem");// �쵼���
			
			String[] r5=request.getParameterValues("r5");//ѡ���ά��������Ա
			String r5str="";
			if(r5!=null && r5.length>0){
				for(int r=0;r<r5.length;r++){
   				 if(r==r5.length-1){
   					r5str+=r5[r];
   				 }else{
   					r5str+=r5[r]+",";
   				 }
   			 }
			}
			
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				this.dispose(form, request, errors, billno);
				List fileList=this.savePicter(form,request,response,"MTSComply.file.upload.folder", billno);
				boolean iswin=this.savePicterTodb(hs,request,fileList,billno);
				
				QualityCheckManagement manage=(QualityCheckManagement) hs.get(QualityCheckManagement.class, billno);
				manage.setElevatorNo(elevatorNo);//���ݱ��
				manage.setChecksDateTime(checksDateTime);//���ʱ��
				manage.setSupervOpinion(supervOpinion);//�������
				manage.setTotalPoints(totalPoints);//�ܵ÷�
				manage.setScoreLevel(scoreLevel);//�÷ֵȼ�
				manage.setR5(r5str);
				
				/*=============== ��������������ʼ =================*/
				jbpmExtBridge = new JbpmExtBridge();
				HashMap paraMap = new HashMap();
				ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskid").toString()));
				
				pd.addAppointActors("");// ����̬��ӵ���������
				String processDefineID = Grcnamelist1.getProcessDefineID("qualitycheckmanagement", manage.getSuperviseId());// ���̻���id
				
				Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�����鳤���", manage.getSuperviseId());
				pd = jbpmExtBridge.goToNext(taskid, approveresult, userInfo.getUserID(), paraMap);// ���
				/*=============== ���������������� =================*/
				manage.setTokenId(pd.getToken());
				manage.setStatus(pd.getStatus());
				manage.setProcessName(pd.getNodename());	
				hs.save(manage);
	
				// �����������������Ϣ
				QualityCheckProcess process = new QualityCheckProcess();
				process.setBillno(billno);
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
		}
		request.setAttribute("display", "Y");
		forward = mapping.findForward("returnApprove");
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

		request.setAttribute("navigator.location", "ά������������ >> �� ��");

		ActionErrors errors = new ActionErrors();

		display(form, request, errors); //�鿴����
		
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
		
		String billNo = (String) dform.get("billno");
		String taskname = (String) dform.get("taskname");// ��������
		Integer taskid = (Integer) dform.get("taskid");// ��������
		String approveresult = (String) dform.get("approveresult");// �������
		String approverem = (String) dform.get("approverem");// �������
		Double deductMoney=(Double)dform.get("deductMoney");
		String assessRem=(String)dform.get("assessRem");
		Double totalPoints=(Double)dform.get("totalPoints");
		String scoreLevel=(String)dform.get("scoreLevel");
		
			
		Session hs = null;
		Transaction tx = null;
		JbpmExtBridge jbpmExtBridge = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if (billNo != null && !billNo.trim().equals("")) {
				
				QualityCheckManagement master = (QualityCheckManagement) hs.get(QualityCheckManagement.class, billNo.trim());
				ViewLoginUserInfo maintPersonnel=(ViewLoginUserInfo) hs.get(ViewLoginUserInfo.class, master.getMaintPersonnel());

				String processDefineID = Grcnamelist1.getProcessDefineID("qualitycheckmanagement", master.getSuperviseId());// ���̻���id
				
				/*=============== ��������������ʼ =================*/
				jbpmExtBridge = new JbpmExtBridge();
				HashMap paraMap = new HashMap();
				ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskid").toString()));
				
				pd.addAppointActors("");// ����̬��ӵ���������
				if(!"��ֹ����".equals(approveresult)){
					if("���ϸ�".equals(scoreLevel)){
						if(taskname.equals("�����鳤���")){
							//ά���������
							pd.setSelpath("N2");
							Grcnamelist1.setJbpmAuditopers_class2(pd, processDefineID, "A03", master.getMaintDivision(), master.getMaintStation());
						}else if(taskname.equals("ά���������")){
							//ά���ֲ������
							Grcnamelist1.setJbpmAuditopers_roleid(pd,"Y",master.getMaintDivision());
						}else{
							if(taskname.equals("ά���ֲ������")){
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�����鳤���2", master.getSuperviseId());
							}else{
								Grcnamelist1.setJbpmAuditopers(pd, processDefineID, approveresult.replace("�ύ", ""), master.getMaintDivision());
							}
							
						}

					}else{
						pd.setSelpath("Y");
						master.setProcessStatus("3");
					}
				}else{
					Grcnamelist1.setJbpmAuditopers(pd, processDefineID, approveresult.replace("�ύ", ""), master.getMaintDivision());
					master.setProcessStatus("3");
				}
				
				pd = jbpmExtBridge.goToNext(taskid, approveresult, userInfo.getUserID(), paraMap);// ���
				/*=============== ���������������� =================*/
				
				// ��������Ϣ
				if(assessRem!=null && !"".equals(assessRem)){
					master.setAssessRem(assessRem);
				}
				if(deductMoney!=null){
					master.setDeductMoney(deductMoney);
				}
				if("�����鳤���".equals(taskname)){
					master.setTotalPoints(totalPoints);
					master.setScoreLevel(scoreLevel);
					String[] jnlnos=request.getParameterValues("jnlno");
					String[] isDeletes=request.getParameterValues("isDelete");
					String[] deleteRem=request.getParameterValues("deleteRem");
					if(jnlnos!=null && jnlnos.length>0){
						for(int i=0;i<jnlnos.length;i++){
							hs.createQuery("update MarkingScoreRegister set isDelete='"+isDeletes[i]+"',deleteRem='"+deleteRem[i]+"' where jnlno='"+jnlnos[i]+"'").executeUpdate();
							hs.flush();
						}
					}
					
				}
				if("ά���ֲ������".equals(taskname)){
					master.setProcessStatus("3");
				}
				if("�����鳤���2".equals(taskname)){
					master.setDeductMoney(deductMoney);
				}
				master.setTokenId(pd.getToken());
				master.setStatus(pd.getStatus());
				master.setProcessName(pd.getNodename());			
				hs.save(master);

				// �����������������Ϣ
				QualityCheckProcess process = new QualityCheckProcess();
				process.setBillno(billNo);
				process.setTaskId(pd.getTaskid().intValue());// �����
				process.setTaskName(taskname);// ��������
				process.setTokenId(pd.getToken());// ��������
				process.setUserId(userInfo.getUserID());
				process.setDate1(CommonUtil.getToday());
				process.setTime1(CommonUtil.getTodayTime());
				process.setApproveResult(approveresult);
				process.setApproveRem(approverem);
				hs.save(process);

				//����ɾ����������
				List fileList=this.savePicter(form,request,response,"MTSComply.file.upload.folder", billNo);
				boolean iswin=this.savePicterTodb(hs,request,fileList,billNo);
				
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
	public void display(ActionForm form, HttpServletRequest request,ActionErrors errors) throws UnsupportedEncodingException{
		
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String tokenid = request.getParameter("tokenid");// ��������
		if (tokenid == null) {
			tokenid = String.valueOf((Long) dform.get("tokenid"));
		}
		String taskid = request.getParameter("taskid");// ����id
		if (taskid == null) {
			taskid = String.valueOf((Integer) dform.get("taskid"));
		}
		String taskname = request.getParameter("taskname");// ��ǰ��������
		if (taskname == null) {
			taskname = (String) dform.get("taskname");
		}else{
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
		String CSheight = PropertiesUtil.getProperty("CSheight");
		String CSwidth = PropertiesUtil.getProperty("CSwidth");
		String CIheight = PropertiesUtil.getProperty("CIheight");
		String CIwidth = PropertiesUtil.getProperty("CIwidth");
		if (tokenid != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from QualityCheckManagement where tokenId = '"+ tokenid + "'");
				List list = query.list();
				if (list != null && list.size() > 0) {
					//����Ϣ
					QualityCheckManagement manage=(QualityCheckManagement) list.get(0);
					
					String r5str=manage.getR5();
					
					List userlist=new ArrayList();
					//���뱣����Ա
					String r5name="";
					 if(r5str!=null && !r5str.trim().equals("")){
						//ά��վ��Ա
						String whql="select l.userid,l.username,isnull(b.userid,'N') from loginuser l "
								+ "left join (select userid from loginuser "
								+ "where userid in('"+r5str.replaceAll(",", "','")+"')) b on l.userid=b.userid "
								+ "where l.storageid like '"+manage.getMaintStation()+"%' and l.enabledflag='Y' "
								+ "order by l.RoleID";
						List msuserlist=hs.createSQLQuery(whql).list();
						
						for(int l=0;l<msuserlist.size();l++){
							Object[] obj=(Object[])msuserlist.get(l);
							HashMap map=new HashMap();
							map.put("userid", obj[0]+"");
							map.put("username", obj[1]+"");
							map.put("isok", obj[2]+"");
							userlist.add(map);
						}
						 
                		 String sqls="select a from Loginuser a where a.userid in('"+r5str.replaceAll(",", "','")+"')";
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

					request.setAttribute("msUserList", userlist);
					manage.setR5(r5name);
					
					manage.setMaintDivision(bd.getName_Sql("Company", "comname", "comid",manage.getMaintDivision()));
					manage.setMaintStation(bd.getName_Sql("Storageid", "storagename", "storageid", manage.getMaintStation()));
					manage.setMaintPersonnel(bd.getName("Loginuser", "username", "userid", manage.getMaintPersonnel()));
					manage.setSuperviseId(bd.getName("Loginuser", "username", "userid", manage.getSuperviseId()));
					
					request.setAttribute("qualityCheckManagementBean", manage);
					
					//ά������ƽ�ֱ�
					List msrList=new ArrayList();
					String msrsql="from MarkingScoreRegister where billno='"+manage.getBillno()+"' and isDelete='N'";
					List list1=hs.createQuery(msrsql).list();
					
					if(list1!=null && list1.size()>0){
						for(Object object : list1){
							MarkingScoreRegister msr=(MarkingScoreRegister)object;
							Map m=new HashMap();
							m.put("jnlno", msr.getJnlno());
							m.put("msId", msr.getMsId());
							m.put("msName", msr.getMsName());
							m.put("rem", msr.getRem());
							m.put("fraction", msr.getFraction());
							List detailList=hs.createQuery("from MarkingScoreRegisterDetail where jnlno='"+msr.getJnlno()+"'").list();
							m.put("detailList", detailList);
							List fileList=hs.createQuery("from MarkingScoreRegisterFileinfo where jnlno='"+msr.getJnlno()+"'").list();
							m.put("fileList", fileList);
							msrList.add(m);
						}
					}
					request.setAttribute("markingItemsComplyList", msrList);
					
					List msrDeleList=new ArrayList();
					List list2=hs.createQuery("from MarkingScoreRegister where billno='"+manage.getBillno()+"' and isDelete='Y'").list();
					
					if(list2!=null && list2.size()>0){
						for(Object object : list2){
							MarkingScoreRegister msr=(MarkingScoreRegister)object;
							Map m=new HashMap();
							m.put("jnlno", msr.getJnlno());
							m.put("msId", msr.getMsId());
							m.put("msName", msr.getMsName());
							m.put("rem", msr.getRem());
							m.put("fraction", msr.getFraction());
							List detailList=hs.createQuery("from MarkingScoreRegisterDetail where jnlno='"+msr.getJnlno()+"'").list();
							m.put("detailList", detailList);
							List fileList=hs.createQuery("from MarkingScoreRegisterFileinfo where jnlno='"+msr.getJnlno()+"'").list();
							m.put("fileList", fileList);
							m.put("isDelet", msr.getIsDelete());
							m.put("deleteRem", msr.getDeleteRem());
							msrDeleList.add(m);
						}
					}
					request.setAttribute("deleList", msrDeleList);
					
					//����������Ϣ
					query = hs.createQuery("from QualityCheckProcess where billno = '"+ manage.getBillno() + "' order by itemId");
					List processApproveList = query.list();
					
					for (Object object : processApproveList) {
						QualityCheckProcess process = (QualityCheckProcess) object;
						process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
					}
					request.setAttribute("processApproveList",processApproveList);
					
					//�������������
					List tranList=this.getTransition(hs.connection(),3,null,Long.parseLong(taskid));
					request.setAttribute("ResultList",tranList);
					request.setAttribute("scoreLevel", manage.getScoreLevel());
					
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
				dform.set("tokenid",new Long(tokenid));
				dform.set("taskid",Integer.valueOf(taskid));				
				dform.set("taskname",taskname);
				request.setAttribute("taskname", taskname);
				dform.set("flowname", WorkFlowConfig.getProcessDefine("qualitycheckmanagementProcessName"));
				dform.set("status", status);
				dform.set("billno",billNo);
				dform.set("id",billNo);				
				dform.set("tasktype",tasktype);
			}
		
		} 
	}
	
	/**
	 * ���ظ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toDownloadFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

//		FileOperatingUtil uf = new FileOperatingUtil();
//		uf.toDownLoadFiles(mapping, form, request, response);
		String fileOldName=request.getParameter("fileOldName");
		fileOldName=URLDecoder.decode(fileOldName,"utf-8");
		String filename=request.getParameter("filesname");
		filename=URLDecoder.decode(filename,"utf-8");
		String folder=request.getParameter("folder");
		if(folder==null || "".equals(folder)){
			folder="MTSComply.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileOldName, "utf-8"));
		

		fis = new FileInputStream(folder+"MarkingScoreRegisterFileinfo"+"/"+filename);
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
	public ActionForward toDownloadFileRecord1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {
		
		Session hs = null;

		String filepath = request.getParameter("customerSignature");//��ˮ��
		String filepath1 = request.getParameter("customerImage");
		String localPath="";
		String oldname="";
		String folder = request.getParameter("folder");		//�ļ���
		if(null == folder || "".equals(folder)){
			folder ="QualityCheckManagement.file.upload.folder";
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
		
		
		}catch (Exception e) {
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
	
	/*
	 * 
	 */
	public void dispose(ActionForm form, HttpServletRequest request,ActionErrors errors,String billno){
		Session hs = null;
		Transaction tx = null;
		MarkingScoreRegister msr=null;
		MarkingScoreRegisterDetail detail=null;
		DynaActionForm dform = (DynaActionForm) form;

		String scores=(String) dform.get("Scores");
		String details=(String) dform.get("details");
		
		try {
			hs=HibernateUtil.getSession();
			tx=hs.beginTransaction();

			String jnlnostr="";
			List list = JSONUtil.jsonToList(scores, "scorce");
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					msr=new MarkingScoreRegister();
					Map m=(Map)list.get(i);

					BeanUtils.populate(msr, (Map)list.get(i));
					msr.setBillno(billno);
					msr.setRem((String)m.get("rem"));
					msr.setIsDelete("N");
					if(msr.getJnlno()==null || "".equals(msr.getJnlno())){
						msr.setJnlno(CommonUtil.getBillno(CommonUtil.getToday().substring(2,4), "MarkingScoreRegister", 1)[0]);
					}else{
						hs.createQuery("delete MarkingScoreRegisterDetail where jnlno='"+msr.getJnlno()+"'").executeUpdate();
						hs.delete(msr);
					}
					hs.save(msr);
					
					if(i==list.size()-1){
						jnlnostr+=msr.getJnlno();
					}else{
						jnlnostr+=msr.getJnlno()+"','";
					}
					
					List detailList=JSONUtil.jsonToList(details.split("\\|")[i],"detail"+i);
					if(detailList!=null && detailList.size()>0){
						for(int j=0;j<detailList.size();j++){
							detail=new MarkingScoreRegisterDetail();
							BeanUtils.populate(detail, (Map)detailList.get(j));
							if(detail.getJnlno()==null || "".equals(detail.getJnlno())){
								detail.setJnlno(msr.getJnlno());
							}
							hs.save(detail);
						}
					}
				}
				
				//ɾ����ϸ
				String delsql="delete a from MarkingScoreRegisterDetail a,MarkingScoreRegister b"
						+ " where a.jnlno=b.jnlno and  b.billno='"+billno+"' and b.jnlno not in('"+jnlnostr+"')";
				hs.connection().prepareStatement(delsql).execute();
				//ɾ������
				delsql="delete a from MarkingScoreRegisterFileinfo a,MarkingScoreRegister b"
						+ " where a.jnlno=b.jnlno and  b.billno='"+billno+"' and b.jnlno not in('"+jnlnostr+"')";
				hs.connection().prepareStatement(delsql).execute();
				//ɾ������Ϣ
				delsql="delete MarkingScoreRegister where billno='"+billno+"' and jnlno not in('"+jnlnostr+"')";
				hs.connection().prepareStatement(delsql).execute();
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
		} finally{
			if(hs!=null){
				hs.close();
			}
		}
	}
	
	/*�޸ķ��� */
	public static void registration(Session hs, String tablename, String w,
			String[] mac, String field, String[] values,String billno) {
		for (int i = 0; i < values.length; i++) {
			String hql = "update " + tablename + " set " + field + "='"
					+ values[i] + "' where " + w + "='" + mac[i] + "' and billno='"+billno+"'";
			hs.createQuery(hql).executeUpdate();
		}
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
		folder = PropertiesUtil.getProperty(folder).trim();
		
		FormFile formFile = null;
		Fileinfo file = null;
		//FileItem fileitem=null;
		if (form.getMultipartRequestHandler() != null) {
			Hashtable hash = form.getMultipartRequestHandler().getFileElements();

			if (hash != null) {
//				Iterator it = hash.values().iterator();
				HandleFile hf = new HandleFileImpA();
//				while (it.hasNext()) {
				for(Iterator it = hash.keySet().iterator(); it.hasNext();){
//					formFile = (FormFile) it.next();
					String key=(String)it.next();
					formFile=(FormFile)hash.get(key);
					
					Map map = new HashMap();
					if(formFile!=null){
						try {
							if(!formFile.getFileName().equals("")){
								String newFileName="MarkingScoreRegisterFileinfo"+"_"+billno+"_"+key+"_"+formFile.getFileName();

								String primary="";
								if(request.getParameter("primary_"+key)!=null){
									primary=request.getParameter("primary_"+key);
								}
								String isdelete="N";
								if(request.getParameter("isdelete_"+key)!=null){
									isdelete=request.getParameter("isdelete_"+key);
								}
								
								map.put("primary", primary);
								map.put("newFileName", newFileName);
								map.put("fileName", formFile.getFileName());
								map.put("fileType", formFile.getContentType());
								map.put("fileSize",formFile.getFileSize());
								map.put("filePath", folder+"MarkingScoreRegisterFileinfo"+"/"+newFileName);
								map.put("billno", billno);
								map.put("isdelete", isdelete);
								//�����ļ���ϵͳ
								hf.createFile(formFile.getInputStream(), folder+"MarkingScoreRegisterFileinfo"+"/", newFileName);
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
	public boolean savePicterTodb(Session hs,HttpServletRequest request,List fileInfoList,String billno){
		boolean saveFlag = true;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session
				.getAttribute(SysConfig.LOGIN_USER_INFO);
		if(null != fileInfoList && !fileInfoList.isEmpty()){
			String sql="";
			MarkingScoreRegister msr=null;
			try {
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					List list=hs.createQuery("from MarkingScoreRegister where billno='"+billno+"' and msId='"+(String)map.get("primary")+"'").list();
					if(list!=null && list.size()>0){
						for(int j=0;j<list.size();j++){
							msr=(MarkingScoreRegister) list.get(0);
						}
					}
					 
					MarkingScoreRegisterFileinfo fileInfo=new MarkingScoreRegisterFileinfo();
					fileInfo.setJnlno(msr.getJnlno());
					fileInfo.setOldFileName((String)map.get("fileName"));
					fileInfo.setNewFileName((String)map.get("newFileName"));
					fileInfo.setFileSize(Double.valueOf(map.get("fileSize").toString()));
					fileInfo.setFilePath((String)map.get("filePath"));
					fileInfo.setFileFormat((String)map.get("fileType"));
					fileInfo.setUploadDate(CommonUtil.getNowTime());
					fileInfo.setUploader(userInfo.getUserID());
					fileInfo.setExt1((String)map.get("isdelete"));
					
					hs.save(fileInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
			}
		}
		return saveFlag;
	}
	
	/**
	 * �ļ�ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDeleteFileRegistration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		Session hs = null;
		Transaction tx = null;
		String id = request.getParameter("filesid");
		List list=null;

		String folder = request.getParameter("folder");
		//�������������
        PrintWriter out=null;
        //������֤��������ͬ��������Ϣ	 
		if(null == folder || "".equals(folder)){
			folder ="MTSComply.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if(id!=null && id.length()>0){
				id=URLDecoder.decode(id, "utf-8");
				MarkingScoreRegisterFileinfo fileInfo=(MarkingScoreRegisterFileinfo) hs.get(MarkingScoreRegisterFileinfo.class, Integer.valueOf(id));
				String fileName=fileInfo.getNewFileName();
				if(fileInfo!=null){
					hs.createQuery("delete MarkingScoreRegisterFileinfo where fileSid='"+fileInfo.getFileSid()+"'").executeUpdate();
				}
				
				HandleFile hf = new HandleFileImpA();
				String localpath=folder+"MarkingScoreRegisterFileinfo"+"/"+fileName;
				hf.delFile(localpath);
			}
			
			tx.commit();
			response.setContentType("text/xml; charset=UTF-8");
			
			out = response.getWriter();
			
			out.println("<response>");
			out.println("<res>" + "Y" + "</res>");
			out.println("</response>");
			
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
	
}
