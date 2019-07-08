package com.gzunicorn.struts.action.engcontractmanager.ServicingContractQuoteMaster;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
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

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.dao.DBInterface;
import com.gzunicorn.common.dao.ObjectAchieveFactory;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.FileOperatingUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.file.xjfile;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.basedata.certificateexam.CertificateExam;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.basedata.jobhistory.JobHistory;
import com.gzunicorn.hibernate.basedata.personnelmanage.PersonnelManageMaster;
import com.gzunicorn.hibernate.basedata.traininghistory.TrainingHistory;
import com.gzunicorn.hibernate.engcontractmanager.ContractFileinfo.ContractFileinfo;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquoteprocess.MaintContractQuoteProcess;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractquotedetail.ServicingContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractquotemaster.ServicingContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractquotematerialsdetail.ServicingContractQuoteMaterialsDetail;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractquoteotherdetail.ServicingContractQuoteOtherDetail;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractquoteprocess.ServicingContractQuoteProcess;
import com.gzunicorn.hibernate.sysmanager.Company;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;
import com.gzunicorn.workflow.bo.JbpmExtBridge;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

	public class ServicingContractQuoteMasterJbpmAction extends DispatchAction {

		Log log = LogFactory.getLog(ServicingContractQuoteMasterJbpmAction.class);
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
					SysRightsUtil.NODE_ID_FORWARD + "myTaskOA", null);
			/** **********�����û�Ȩ�޹���*********** */

			// Set default method is toSearchRecord
			String name = request.getParameter("method");
			String typejsp=request.getParameter("typejsp");			
				if( "display".equals(typejsp)){
					name="toSaveApprove";
					return dispatchMethod(mapping, form, request, response, name);
				}else if("mondity".equals(typejsp)){
					name="toSaveUpdateApprove";
					return dispatchMethod(mapping, form, request, response, name);
				}
				ActionForward forward = super.execute(mapping, form, request,
						response);
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
		 * ����鿴�ķ���
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		public ActionForward toDisplayRecord(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException {
			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();
			ActionForward forward = null;
			
			String tokenid = request.getParameter("tokenid");// ��������
			if (tokenid == null) {
				tokenid = String.valueOf(dform.get("tokenId"));
			}
			String taskid = request.getParameter("taskid");// ����id
			if (taskid == null) {
				taskid = String.valueOf(dform.get("taskId"));
			}
			String taskname = request.getParameter("taskname");// ��ǰ��������
			if (taskname == null) {
				taskname = (String) dform.get("taskName");
			}else{
				taskname = new String(taskname.getBytes("ISO-8859-1"), "gbk");
			}
			String tasktype = request.getParameter("tasktype");// ��������
			if (tasktype == null) {
				tasktype = (String) dform.get("taskType");
			}
			
			Session hs = null;
			ServicingContractQuoteMaster scqm=null;
			String id="";
			Query query=null;
			List scqodList = null;
			List scqdList = null;
			List scqmdList = null;
		   if (tokenid != null) {				
			try {
				hs = HibernateUtil.getSession();
				query = hs.createQuery("from ServicingContractQuoteMaster where tokenId = '"+ tokenid + "'");
				List list = query.list();
				if (list != null && list.size() > 0) {
				scqm = (ServicingContractQuoteMaster) list.get(0);
				id=scqm.getBillNo();
				scqm.setBusType(scqm.getBusType().equals("W")?"ά��":"����");
				scqm.setAttn(bd.getName("Loginuser", "username", "userid", scqm.getAttn()));
				List scqmList2=xj.getClasses(hs, "Customer", "companyId", scqm.getCompanyId());
                if(scqmList2!=null && scqmList2.size()>0){
                	Customer ct=(Customer)scqmList2.get(0);
                	scqm.setCompanyId(ct.getCompanyName());
                	scqm.setMaintDivision(bd.getName("Company", "comname", "comid", scqm.getMaintDivision()));
                	request.setAttribute("contacts", ct.getContacts());
                	request.setAttribute("contactPhone", ct.getContactPhone());
					request.setAttribute("maintStationName", bd.getName(hs, "Storageid","storageName", "storageID", scqm.getMaintStation())); //ά��վ����
                }
				scqodList=xj.getClasses(hs, "ServicingContractQuoteOtherDetail", "servicingContractQuoteMaster.billNo", id);
				if(scqodList!=null && scqodList.size()>0){
					for(int i=0;i<scqodList.size();i++){
						ServicingContractQuoteOtherDetail sc=(ServicingContractQuoteOtherDetail)scqodList.get(i);
						sc.setFeeName(xj.getValue(hs, "Pulldown", "pullname", "pullid", sc.getFeeName()));
					}
				}
				scqdList=xj.getClasses(hs, "ServicingContractQuoteDetail", "servicingContractQuoteMaster.billNo", id);
				scqmdList=xj.getClasses(hs, "ServicingContractQuoteMaterialsDetail", "servicingContractQuoteMaster.billNo", id);			
				//����

				List updatefileList2 = xj.getfile(hs,"ContractFileinfo","ServicingContractQuoteMaster",id);
				List updatefileList=new ArrayList();
				if(updatefileList2!=null && updatefileList2.size()>0){
					for(int i=0;i<updatefileList2.size();i++){
						Object[] obj=(Object[])updatefileList2.get(i);
						HashMap hm=new HashMap();
						hm.put("oldfilename", obj[3]);
						hm.put("uploadername", obj[21]);
						hm.put("uploaddate", obj[8]);
						hm.put("filesid", obj[0]);
						updatefileList.add(hm);
					}
				request.setAttribute("updatefileList", updatefileList);			
				}
				
				//����������Ϣ
				query = hs.createQuery("from ServicingContractQuoteProcess where billNo = '"+ id + "' order by itemId");
				List processApproveList = query.list();
				for (Object object : processApproveList) {
					ServicingContractQuoteProcess process = (ServicingContractQuoteProcess) object;
					process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
				}
				request.setAttribute("processApproveList",processApproveList);
				
				//�������������
				List tranList=this.getTransition(hs.connection(),3,null,Long.parseLong(taskid));
				request.setAttribute("ResultList",tranList);
				}
			}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			} else {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
			}
		    request.setAttribute("ServicingContractQuoteMaster", scqm);
		    request.setAttribute("scqodList", scqodList);
		    request.setAttribute("scqdList", scqdList);
		    request.setAttribute("scqmdList", scqmdList);
			request.setAttribute("typejsp", "display");
			
			if(request.getAttribute("error") == null || request.getAttribute("error").equals(""))//׼����������ҳ��
			{
				dform.set("tokenid",new Long(tokenid));
				dform.set("taskid",new Long(taskid));				
				dform.set("taskname",taskname);
				dform.set("tokenId",new Long(tokenid));
				dform.set("taskId",new Long(taskid));				
				dform.set("taskName",taskname);
				dform.set("flowname", WorkFlowConfig.getProcessDefine("servicingcontractquotemasterProcessName"));
				dform.set("status", scqm.getStatus());
				dform.set("billNo",id);
				dform.set("id",id);				
				dform.set("tasktype",tasktype);
				dform.set("taskType",tasktype);
			}
			forward = mapping.findForward("toWGchangeJbpmDisplay");
			return forward;
		}
		
		
		/**
		 * ��ת���޸ļ���ҳ��
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		@SuppressWarnings("unchecked")
		public ActionForward toPrepareUpdateApprove(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException {
			
			request.setAttribute("navigator.location","ά�ı���������� >> �޸�");	
			HttpSession session = request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();		
			ActionForward forward = null;
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
			ServicingContractQuoteMaster ServicingContractQuoteMaster=null;
			List scqodList = null;
			List scqdList = null;
			List scqmdList = null;
			if (taskid != null) {
					try {
						hs = HibernateUtil.getSession();
						Query query = hs.createQuery("select p from ServicingContractQuoteMaster p where p.tokenId = :tokenid");
						query.setString("tokenid", tokenid);
						List list = query.list();
						if (list != null && list.size() > 0) {
							ServicingContractQuoteMaster = (ServicingContractQuoteMaster) list.get(0);
							ServicingContractQuoteMaster.setAttn(bd.getName("Loginuser", "username", "userid", ServicingContractQuoteMaster.getAttn()));
							ServicingContractQuoteMaster.setMaintDivision(bd.getName("Company", "comname", "comid", ServicingContractQuoteMaster.getMaintDivision()));
							List scqmList2=xj.getClasses(hs, "Customer", "companyId", ServicingContractQuoteMaster.getCompanyId());
			                if(scqmList2!=null && scqmList2.size()>0){
			                	Customer ct=(Customer)scqmList2.get(0);
			                	request.setAttribute("companyName", ct.getCompanyName());
			                	request.setAttribute("contacts", ct.getContacts());
			                	request.setAttribute("contactPhone", ct.getContactPhone());
			                	request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));						
			                }	
			                String hql="from Pulldown where typeflag='ServicingContractQuoteOtherDetail_FeeName' and enabledflag='Y' order by orderby";
							
							query = hs.createQuery("from ServicingContractQuoteDetail p where p.servicingContractQuoteMaster.billNo = '"+ServicingContractQuoteMaster.getBillNo()+"'");
							scqdList = query.list();//������ϸ
							
							query = hs.createQuery("from ServicingContractQuoteMaterialsDetail p where p.servicingContractQuoteMaster.billNo = '"+ServicingContractQuoteMaster.getBillNo()+"'");
							scqmdList = query.list();//������ϸ
							
							query = hs.createQuery("from ServicingContractQuoteOtherDetail p where p.servicingContractQuoteMaster.billNo = '"+ServicingContractQuoteMaster.getBillNo()+"'order by p.rowid");
							scqodList = query.list();//����������ϸ
							if(scqodList!=null && scqodList.size()>0){
								int j=1;
								for(int i=0;i<scqodList.size();i++){
									ServicingContractQuoteOtherDetail scqod=(ServicingContractQuoteOtherDetail)scqodList.get(i);
									scqod.setR4(xj.getValue(hs, "Pulldown", "pullname", "pullid", scqod.getFeeName()));
									scqod.setR9(j);
									j++;
								}
							}
							//����

							List updatefileList2 = xj.getfile(hs,"ContractFileinfo","ServicingContractQuoteMaster",ServicingContractQuoteMaster.getBillNo());
							List updatefileList=new ArrayList();
							if(updatefileList2!=null && updatefileList2.size()>0){
								for(int i=0;i<updatefileList2.size();i++){
									Object[] obj=(Object[])updatefileList2.get(i);
									HashMap hm=new HashMap();
									hm.put("oldfilename", obj[3]);
									hm.put("uploadername", obj[21]);
									hm.put("uploaddate", obj[8]);
									hm.put("filesid", obj[0]);
									updatefileList.add(hm);
								}
							request.setAttribute("updatefileList", updatefileList);			
							}
							
							//����������Ϣ
							query = hs.createQuery("from ServicingContractQuoteProcess where billNo = '"+ ServicingContractQuoteMaster.getBillNo() + "' order by itemId");
							List processApproveList = query.list();
							for (Object object : processApproveList) {
								ServicingContractQuoteProcess process = (ServicingContractQuoteProcess) object;
								process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
							}
							request.setAttribute("processApproveList",processApproveList);
							
							//�������������
							List tranList=this.getTransition(hs.connection(),3,null,Long.parseLong(taskid));
							request.setAttribute("ResultList",tranList);						
						} 

					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						try {
							hs.close();
						} catch (HibernateException hex) {
							log.error(hex.getMessage());
							DebugUtil
									.print(hex, "HibernateUtil Hibernate Session ");
						}
					}
					}else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
					}
							
					request.setAttribute("ServicingContractQuoteMaster", ServicingContractQuoteMaster);
					request.setAttribute("scqodList", scqodList);
					request.setAttribute("scqdList", scqdList);
					request.setAttribute("scqmdList", scqmdList);
					request.setAttribute("typejsp", "mondity");
					
					if(request.getAttribute("error") == null || request.getAttribute("error").equals(""))//׼����������ҳ��
					{
						dform.set("tokenid",new Long(tokenid));
						dform.set("taskid",new Long(taskid));				
						dform.set("taskname",taskname);
						dform.set("tokenId",new Long(tokenid));
						dform.set("taskId",new Long(taskid));				
						dform.set("taskName",taskname);
						dform.set("flowname", WorkFlowConfig.getProcessDefine("enginequotemasterProcessName"));
						dform.set("status", ServicingContractQuoteMaster.getStatus());
						dform.set("billNo",ServicingContractQuoteMaster.getBillNo());
						dform.set("id",ServicingContractQuoteMaster.getBillNo());				
						dform.set("tasktype",tasktype);
						dform.set("taskType",tasktype);
					}
					if (!errors.isEmpty()) {
						saveErrors(request, errors);
					}
			forward = mapping.findForward("toWGchangeJbpmDisplay");
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
		public ActionForward toSaveUpdateApprove(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();
			HttpSession session = request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			Session hs = null;
			Transaction tx = null;
			JbpmExtBridge jbpmExtBridge=null;
			String attn=request.getParameter("attn");
			String id = request.getParameter("billNo");
			boolean istrue = false;
			ServicingContractQuoteMaster scqm = null;
			String approveresult=(String)dform.get("approveresult");
			String taskname=(String)dform.get("taskname");
			String flowname=(String) dform.get("flowname");
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				hs.createQuery("delete from ServicingContractQuoteMaterialsDetail a where a.servicingContractQuoteMaster.billNo='"+id+"'").executeUpdate();
				hs.createQuery("delete from ServicingContractQuoteDetail a where a.servicingContractQuoteMaster.billNo='"+id+"'").executeUpdate();
				hs.createQuery("delete from ServicingContractQuoteOtherDetail a where a.servicingContractQuoteMaster.billNo='"+id+"'").executeUpdate();				
				hs.flush();
				scqm =(ServicingContractQuoteMaster)hs.get(ServicingContractQuoteMaster.class, id);
				String datetime=scqm.getOperDate();
				BeanUtils.copyProperties(scqm, dform); // ��������ֵ
				String processDefineID = Grcnamelist1.getProcessDefineID("servicingcontractquotemaster",userInfo.getUserID());// ���̻���id
				/**======== ��������������ʼ ========*/
				jbpmExtBridge=new JbpmExtBridge();
		    	ProcessBean pd=jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskid").toString()));
		    	
		    	pd.addAppointActors("");// ����̬��ӵ�����������
				Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ά���ֲ������", userInfo.getUserID());// ��������

		    	pd=jbpmExtBridge.goToNext(Long.parseLong(dform.get("taskid").toString()),approveresult,userInfo.getUserID(),null);//���
		    	/**======== ���������������� ========*/
		    	//�����������������Ϣ
		    	ServicingContractQuoteProcess process=new ServicingContractQuoteProcess();
				process.setServicingContractQuoteMaster(scqm);
				process.setTaskId(new Integer(pd.getTaskid().intValue()));//�����
				process.setTaskName(taskname);//��������
				process.setTokenId(pd.getToken());//��������
				process.setUserId(userInfo.getUserID());
				process.setDate1(CommonUtil.getToday());
				process.setTime1(CommonUtil.getTodayTime());
				process.setApproveResult(approveresult);
				process.setApproveRem((String)dform.get("approverem"));
				hs.save(process);
		    	
		    	
		    	
				String todayDate = xj.getdatetime();
				String year = todayDate.substring(2,4);	
				scqm.setStatus(new Integer(WorkFlowConfig.State_NoStart));
				scqm.setOperId(userInfo.getUserID());// ¼����
				scqm.setOperDate(datetime);// ¼��ʱ��		
				scqm.setAttn(userInfo.getUserID());//������
				scqm.setOperDate(datetime);// ����ʱ��	
				scqm.setMaintDivision(bd.getName("Company", "comid", "comname", scqm.getMaintDivision()));//����ά���ֲ�
				scqm.setTokenId(pd.getToken());
				scqm.setStatus(pd.getStatus());
				scqm.setProcessName(pd.getNodename());
				scqm.setSubmitType("Y");					
				hs.save(scqm);	
				//���������Ϣ
				saveServicingContractQuoteDetail(request,hs,id);
				//����������ϸ
				saveServicingContractQuoteMaterialsDetail(request,hs,id);
				//����������ϸ
				saveServicingContractQuoteOtherDetail(request,hs,id);
				//���渽��
				String path = "ServicingContractQuoteMaster.file.upload.folder";
				String tableName = "ServicingContractQuoteMaster";//����
				String userid = userInfo.getUserID();

				FileOperatingUtil foutil = new FileOperatingUtil();
				List fileInfoList = foutil.saveFile(dform, request,	response, path, id);
				istrue=this.saveFileInfo(hs, fileInfoList, tableName,	id, userid);
				tx.commit();
		
			} catch (Exception e1) {
				tx.rollback();
				jbpmExtBridge.setRollBack();
				e1.printStackTrace();
				log.error(e1.getMessage());
				DebugUtil.print(e1, "Hibernate region Insert error!");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"error.string","<font color='red'>����ʧ�ܣ�</font>"));
			} finally {
				try {
					hs.close();
					jbpmExtBridge.close();
					
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
					forward = mapping.findForward("returnApprove");
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			request.setAttribute("display","Y");
			return forward;
		}
		/*
		 * 
		 * �������ҳ��
		 * 
		 * 
		 */
		public ActionForward toPrepareApprove(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {

				request.setAttribute("navigator.location", "ά�ı���������� >> �� ��");

				ActionErrors errors = new ActionErrors();
				return toDisplayRecord(mapping,form,request,response);
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
			
			
			String billNo = (String) dform.get("billNo");
			String taskname = (String) dform.get("taskname");// ��������
			Long taskid = (Long) dform.get("taskid");// ��������
			String approveresult = (String) dform.get("approveresult");// �������
			String approverem = (String) dform.get("approverem");// �������
				
			Session hs = null;
			Transaction tx = null;
			JbpmExtBridge jbpmExtBridge = null;
			ServicingContractQuoteMaster master=null;
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				if (billNo != null && !billNo.trim().equals("")) {
					
					master = (ServicingContractQuoteMaster) hs.get(ServicingContractQuoteMaster.class, billNo.trim());
					
					String processDefineID = Grcnamelist1.getProcessDefineID("servicingcontractquotemaster", master.getOperId());// ���̻���id
					
					/*=============== ��������������ʼ =================*/
					jbpmExtBridge = new JbpmExtBridge();
					HashMap paraMap = new HashMap();
					ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(Long.parseLong(dform.get("taskid").toString()));					
					pd.addAppointActors("");// ����̬��ӵ�����������
                    if(approveresult!=null && approveresult.trim().indexOf("��ͬ��")>-1){			    		
			    		pd.addAppointActors(master.getOperId());//���������			    		
			    	}else{
					Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, approveresult.substring(2), master.getOperId());// ��������
			    	}
					pd = jbpmExtBridge.goToNext(taskid, approveresult, userInfo.getUserID(), paraMap);// ���
					/*=============== ���������������� =================*/
					
					// ��������Ϣ
					master.setTokenId(pd.getToken());
					master.setStatus(pd.getStatus());
					master.setProcessName(pd.getNodename());	
					hs.save(master);

					// �����������������Ϣ
					ServicingContractQuoteProcess process = new ServicingContractQuoteProcess();
					process.setServicingContractQuoteMaster(master);
					
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
		/*
		 * ���������ϸ
		 * 
		 * 
		 */
		public void saveServicingContractQuoteDetail(HttpServletRequest request,Session hs,String billno){
            String[] elevatorNos=request.getParameterValues("elevatorNo");
            String[] salesContractNos=request.getParameterValues("salesContractNo");
            String[] projectNames=request.getParameterValues("projectName");
			String[] contentss=request.getParameterValues("contents");
             for(int i=0;i<elevatorNos.length;i++){
				ServicingContractQuoteDetail scq=new ServicingContractQuoteDetail();
				scq.setServicingContractQuoteMaster((ServicingContractQuoteMaster)hs.get(ServicingContractQuoteMaster.class,billno));
				scq.setElevatorNo(elevatorNos[i]);
				scq.setSalesContractNo(salesContractNos[i]);
				scq.setProjectName(projectNames[i]);
				scq.setContents(contentss[i]);
				hs.saveOrUpdate(scq);
             }		
		}

		/*
		 * ����������ϸ
		 * 
		 * 
		 * 
		 */
		public void saveServicingContractQuoteMaterialsDetail(HttpServletRequest request,Session hs,String billno){
            String[] materialName=request.getParameterValues("materialName");
			String[] materialsStandard=request.getParameterValues("materialsStandard");
			String[] quantity=request.getParameterValues("quantity");
			String[] unit=request.getParameterValues("unit");
			String[] unitPrice=request.getParameterValues("unitPrice");
			String[] finalPrice2=request.getParameterValues("finalPrice2");
			String[] price2=request.getParameterValues("price2");
			if(materialName!=null && materialName.length>0){
			 for(int i=0;i<materialName.length;i++){
				 ServicingContractQuoteMaterialsDetail scq=new ServicingContractQuoteMaterialsDetail();
					scq.setServicingContractQuoteMaster((ServicingContractQuoteMaster)hs.get(ServicingContractQuoteMaster.class,billno));
					scq.setMaterialName(materialName[i]);
					scq.setMaterialsStandard(materialsStandard[i]);
					scq.setQuantity(Double.valueOf(quantity[i]));
					scq.setUnit(unit[i]);
					scq.setUnitPrice(Double.valueOf(unitPrice[i]));
					scq.setFinalPrice(Double.valueOf(finalPrice2[i]));
					scq.setPrice(Double.valueOf(price2[i]));
					hs.saveOrUpdate(scq);
	             }	
			}
		}
		/*
		 * 
		 * ����������ϸ
		 * 
		 */
		public void saveServicingContractQuoteOtherDetail(HttpServletRequest request,Session hs,String billno){
            String[] pullid=request.getParameterValues("pullid");
            String[] price=request.getParameterValues("price");
            String[] finalPrice=request.getParameterValues("finalPrice");
           if(pullid!=null && pullid.length>0){
			 for(int i=0;i<pullid.length;i++){
				 ServicingContractQuoteOtherDetail scq=new ServicingContractQuoteOtherDetail();
					scq.setServicingContractQuoteMaster((ServicingContractQuoteMaster)hs.get(ServicingContractQuoteMaster.class,billno));
					scq.setPrice(Double.valueOf(price[i]));
					scq.setFeeName(pullid[i]);
					scq.setFinalPrice(Double.valueOf(finalPrice[i]));
					hs.saveOrUpdate(scq);
			 }
          }
		}
		/**
		 * ��ö���ָ����get����������ִ�и�get�������ֵ
		 * @param object javabean����
		 * @param superClazz object���࣬����û����Ӧget����ʱ�봫��object�ĸ���
		 * @param fieldName ������
		 * @return ActionForward
		 */
		private String getValue(Object object, Class<?> superClazz, String fieldName){
			String value = null;	
			String methodName = "get" + fieldName.replaceFirst(fieldName.substring(0, 1),fieldName.substring(0, 1).toUpperCase());
			try {
				Method method = superClazz.getMethod(methodName);
				value = method.invoke(object, null) + "";
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return value;

		}
		/**
		 * �����ļ���Ϣ�����ݿ�
		 * @param fileInfoList
		 */
		public boolean saveFileInfo(Session hs,List fileInfoList,String tableName,String billno,String userid){
			boolean saveFlag = true;
			ContractFileinfo file=null;
			if(null != fileInfoList && !fileInfoList.isEmpty()){
				
				try {
					int len = fileInfoList.size();
					for(int i = 0 ; i < len ; i++){
						Map map = (Map)fileInfoList.get(i);
						 String title =(String) map.get("title");
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
						 file.setUploadDate(CommonUtil.getToday());
						 file.setUploader(userid);
						 file.setRemarks(rem);
						 
						 hs.save(file);
						 hs.flush();
					}
				} catch (ParseException e) {
					e.printStackTrace();
					saveFlag = false;
				} catch (Exception e) {
					e.printStackTrace();
					saveFlag = false;
				}
			}
			return saveFlag;
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
				tokenid = (String) dform.get("tokenid");
			}
			String taskid = request.getParameter("taskid");// ����id
			if (taskid == null) {
				taskid = (String) dform.get("taskid");
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
			
			if (tokenid != null) {
				try {
					hs = HibernateUtil.getSession();
					Query query = hs.createQuery("from MaintContractQuoteMaster where tokenId = '"+ tokenid + "'");
					List list = query.list();
					if (list != null && list.size() > 0) {

						// ����Ϣ
						master = (MaintContractQuoteMaster) list.get(0);
						billNo = master.getBillNo();
						status = master.getStatus();
						request.setAttribute("maintContractQuoteBean", master);
						request.setAttribute("attnName", bd.getName(hs, "Loginuser","username", "userid",master.getAttn())); //����������
						request.setAttribute("maintDivisionName", bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision())); //ά���ֲ�����
						if(!"toPrepareUpdateApprove".equals(request.getParameter("method"))){
							request.setAttribute("maintStationName", bd.getName(hs, "Storageid","storageName", "storageID", master.getMaintStation())); //ά��վ����
						}

						// �׷���λ��Ϣ
						query = hs.createQuery("from Customer where companyId = '"+ master.getCompanyId() + "'");
						list = query.list();
						if (list != null && list.size() > 0) {
							Customer customer = (Customer) list.get(0);
							request.setAttribute("companyName",customer.getCompanyName());// �׷���λ����
							request.setAttribute("companyContacts",customer.getContacts());// �׷���ϵ��
							request.setAttribute("companyPhone",customer.getContactPhone());// �׷���ϵ�绰
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
					dform.set("taskid",new Long(taskid));				
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
	}

