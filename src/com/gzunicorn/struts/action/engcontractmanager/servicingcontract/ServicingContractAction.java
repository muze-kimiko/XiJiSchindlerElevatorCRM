package com.gzunicorn.struts.action.engcontractmanager.servicingcontract;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractdetail.ServicingContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractmaster.ServicingContractMaster;
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

	public class ServicingContractAction extends DispatchAction {

		Log log = LogFactory.getLog(ServicingContractAction.class);
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

			String name = request.getParameter("method");
			if(!"toDisplayRecord".equals(name)){
				/** **********��ʼ�û�Ȩ�޹���*********** */
				SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "wgchangeContract", null);
				/** **********�����û�Ȩ�޹���*********** */
			}
			// Set default method is toSearchRecord
			
			String typejsp=request.getParameter("typejsp");
			if ((typejsp==null || typejsp.equals("")) && (name == null || name.equals(""))) {
				name = "toSearchRecord";
				return dispatchMethod(mapping, form, request, response, name);
			} else {
				if( "add".equals(typejsp)){
					name="toAddRecord";
					return dispatchMethod(mapping, form, request, response, name);
				}else if("mondity".equals(typejsp)){
					name="toUpdateRecord";
					return dispatchMethod(mapping, form, request, response, name);
				}else if("totask".equals(typejsp)){
					name="tosaveTask";
					return dispatchMethod(mapping, form, request, response, name);
				}
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
			
			request.setAttribute("navigator.location","ά�ĺ�ͬ���� >> ��ѯ�б�");		
			ActionForward forward = null;
			ActionErrors error=new ActionErrors();
			HttpSession session = request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			ServeTableForm tableForm = (ServeTableForm) form;
			String action = tableForm.getAction();
            List list2=new ArrayList();
			if (tableForm.getProperty("genReport") != null
					&& !tableForm.getProperty("genReport").equals("")) {
				try {
					response = toExcelRecord(form,request,response);
				} catch (IOException e) {
					e.printStackTrace();
				}
				forward = mapping.findForward("exportExcel");
				tableForm.setProperty("genReport","");

			} else {
				HTMLTableCache cache = new HTMLTableCache(session, "wgchangeContractList");

				DefaultHTMLTable table = new DefaultHTMLTable();
				table.setMapping("fServicingContractSearch");
				table.setLength(SysConfig.HTML_TABLE_LENGTH);
				cache.updateTable(table);
				table.setSortColumn("a.wgBillno");
				table.setIsAscending(false);
				cache.updateTable(table);

				if (action.equals(ServeTableForm.NAVIGATE)
						|| action.equals(ServeTableForm.SORT)) {
					cache.loadForm(tableForm);
				} else {
					table.setFrom(0);
				}
				cache.saveForm(tableForm);
				String companyId = tableForm.getProperty("companyname");// �׷���λ����
				String maintDivision = tableForm.getProperty("maintDivision");//����ά���ֲ�
				String wgBillno = tableForm.getProperty("wgBillno");//��ˮ��
				String MaintContractNo = tableForm.getProperty("MaintContractNo");//ά�ĺ�ͬ��
				String attn = tableForm.getProperty("attn");//������
				String submitType = tableForm.getProperty("submitType");//�ύ��־
				String auditStatus = tableForm.getProperty("auditStatus");//���״̬
				String taskSubFlag = tableForm.getProperty("taskSubFlag");//�����´�״̬
				String busType = tableForm.getProperty("busType");//ҵ�����
				 if(maintDivision==null){
                 	List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
         			if(maintDivision == null || maintDivision.equals("")){
         				Map map = (Map)maintDivisionList.get(0);
         				maintDivision = (String)map.get("grcid");
         			}
                 }
				Session hs = null;
                Query qu=null;
				try {

					hs = HibernateUtil.getSession();
                    String sql=" select a,b,c,d from ServicingContractMaster a,Loginuser b,Customer c,Company d where 1=1"
                    		+ " and a.attn=b.userid and a.companyId =c.companyId and a.maintDivision=d.comid";
                    sql+=xj.getsql("a.wgBillno", wgBillno);
                    sql+=xj.getnullsql("a.submitType", submitType);                    
                    sql+=xj.getnullsql("a.taskSubFlag", taskSubFlag);
                    if(companyId==null){
                    	companyId="%";
                    }
                    sql+=" and (c.companyName like '%"+companyId+"%' or c.companyId ='"+companyId+"')";
                    sql+=xj.getsql("d.comid", maintDivision);
                    sql+=xj.getsql("a.busType", busType);
                    sql+=xj.getsql("a.maintContractNo", MaintContractNo);
                    if(attn!=null && !"".equals(attn)){
            			sql+=" and  (a.attn like '%"+attn+"%' or b.username like '%"+attn+"%')";
            		}else{
            			sql+=" and  a.attn like '%'";
            		}
                    if(auditStatus!=null && !"".equals(auditStatus)){
            			sql+=" and  isnull(a.auditStatus,'N') like '%"+auditStatus.trim()+"%'";
            		}else{
            			sql+=" and  isnull(a.auditStatus,'N') like '%'";
            		}
                    if(table.getIsAscending()){
                    	sql+=" order by "+table.getSortColumn()+"";
                    }else{
                    	sql+=" order by "+table.getSortColumn()+" desc";
                    }
                    //System.out.println(sql);
                    qu=hs.createQuery(sql);
					table.setVolume(qu.list().size());// ��ѯ�ó����ݼ�¼��;

					// �ó���һҳ�����һ����¼����;
					qu.setFirstResult(table.getFrom()); // pagefirst
					qu.setMaxResults(table.getLength());

					cache.check(table);

					List wgchangeContractList = qu.list();
					if(wgchangeContractList!=null && wgchangeContractList.size()>0){
						for(int i=0;i<wgchangeContractList.size();i++){
							Object[] obj=(Object[])wgchangeContractList.get(i);
							ServicingContractMaster scqm=(ServicingContractMaster)obj[0];
							Loginuser lo=(Loginuser)obj[1];
							Customer  ct=(Customer)obj[2];
							Company  cpy=(Company)obj[3];
							scqm.setAttn(lo.getUsername());
                            scqm.setCompanyId(ct.getCompanyName());
						    scqm.setMaintDivision(cpy.getComname());
						    scqm.setBusType(scqm.getBusType().equals("G")?"����":"ά��");
						    if(scqm.getSubmitType().equals("R")){
						    	scqm.setSubmitType("����");
						    }else{
						    	scqm.setSubmitType(scqm.getSubmitType().equals("N")?"δ�ύ":"���ύ");
						    }
						    scqm.setTaskSubFlag(scqm.getTaskSubFlag().equals("Y")?"���´�":"δ�´�");
						    scqm.setAuditStatus(scqm.getAuditStatus().equals("Y")?"�����":"δ���");
						    scqm.setCompanyId(scqm.getCompanyId());
						    list2.add(scqm);
						}
	
					}					
					table.addAll(list2);
					session.setAttribute("wgchangeContractList", table);
					request.setAttribute("maintDivisionList", Grcnamelist1.getgrcnamelist(userInfo.getUserID()));
				} catch (DataStoreException e) {
					e.printStackTrace();
				} catch (HibernateException e1) {

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
				forward = mapping.findForward("toServicingContractList");
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
		 */
		public ActionForward toDisplayRecord(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			
			request.setAttribute("navigator.location","ά�ĺ�ͬ���� >> �鿴");
			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();
			ActionForward forward = null;			

			String id=(String)dform.get("id");
			if(id==null || id.trim().equals("")){
				id = request.getParameter("id");
			}
			
			Session hs = null;
			ServicingContractMaster scm=null;
			Customer ctj=null;
			Customer cty=null;
			List ServicingContractDetailList = new ArrayList();
			
		   if (id != null) {				
			try {
				hs = HibernateUtil.getSession();
				scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
				scm.setAttn(bd.getName("Loginuser", "username", "userid", scm.getAttn()));
				scm.setMaintDivision(bd.getName("Company", "comname", "comid", scm.getMaintDivision()));
				scm.setTaskUserId(bd.getName("Loginuser", "username", "userid", scm.getTaskUserId()));					
			    scm.setTaskSubFlag(scm.getTaskSubFlag().equals("N")?"δ�´�":"���´�");
			    scm.setAuditOperid(bd.getName("Loginuser", "username", "userid", scm.getAuditOperid()));
			    scm.setAuditStatus(scm.getAuditStatus().equals("N")?"δ���":"�����");
			    request.setAttribute("maintStationName", bd.getName(hs, "Storageid","storageName", "storageID", scm.getMaintStation())); //ά��վ����
			    //�׷���λ
				String companyId=scm.getCompanyId();
				if(companyId!=null && !"".equals(companyId)){
					ctj=(Customer)hs.get(Customer.class, companyId);
				}
				//�ҷ���λ
				String companyId2=scm.getCompanyId2();
				if(companyId2!=null && !"".equals(companyId2)){
					cty=(Customer)hs.get(Customer.class, companyId2);
				}
				
				List list=hs.createQuery("select a from ServicingContractDetail a where a.servicingContractMaster.wgBillno='"+id+"'").list();	
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						ServicingContractDetail scd=(ServicingContractDetail)list.get(i);
						ServicingContractDetailList.add(scd);
					}
				}
				
			} catch (Exception e) {
					e.printStackTrace();
				}				
			
		}else{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
		}
		request.setAttribute("ServicingContractMasterList", scm);
		request.setAttribute("CustomerList", ctj);
		request.setAttribute("CustomerList2", cty);
		request.setAttribute("ServicingContractDetailList", ServicingContractDetailList);
		String typejsp=request.getParameter("typejsp");
		if(typejsp!=null && !"".equals(typejsp)){
			request.setAttribute("type", typejsp);
		}
		request.setAttribute("typejsp", "display");			
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
			forward = mapping.findForward("toContractDisplayAddMondity");
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
		
		@SuppressWarnings("unchecked")
		public ActionForward toPrepareAddRecord(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {

			request.setAttribute("navigator.location","ά�ĺ�ͬ���� >> ���");
			/*
			String billNo=request.getParameter("billNo");
			if(billNo==null){
				billNo=(String)dform.get("billNo");
			}
            Session hs=null;
            List PulldownList=null;			
			ServicingContractQuoteMaster scqm = null;
			Customer ct=null;
			ServicingContractMaster scm=new ServicingContractMaster();
			ServicingContractQuoteDetail scqd=null;
			ServicingContractDetail scd=null;
			List list2=new ArrayList();
			if(billNo!=null && !"".equals(billNo)){
				try {
					hs=HibernateUtil.getSession();
					scqm=(ServicingContractQuoteMaster)hs.get(ServicingContractQuoteMaster.class, billNo);
					BeanUtils.copyProperties(scm, scqm);
					scm.setBusType(scqm.getBusType());
					scm.setBillNo(billNo);
					scm.setContractTotal(scqm.getFinallyQuoteTotal());
					scm.setOtherFee(scqm.getBusinessCosts());
					scm.setMaintDivision(userInfo.getComID());
					//�׷���λ
					String companyId=scqm.getCompanyId();
					if(companyId!=null && !"".equals(companyId)){
						ct=(Customer)hs.get(Customer.class, companyId);
					}
				//������ϸ
					List list=hs.createQuery("select a from ServicingContractQuoteDetail a where a.servicingContractQuoteMaster.billNo="+billNo).list();
				    if(list!=null && list.size()>0){
				    	for(int i=0;i<list.size();i++){
				    	scd=new ServicingContractDetail();
				    	scqd=(ServicingContractQuoteDetail)list.get(i);
				    	BeanUtils.copyProperties(scd, scqd);
				    	list2.add(scd);
				    	}
				      }
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				request.setAttribute("ServicingContractQuoteMasterList", scqm);
				request.setAttribute("ServicingContractMasterList", scm);
				request.setAttribute("ServicingContractDetailList", list2);
				request.setAttribute("userInfoList", userInfo);
				request.setAttribute("CustomerList", ct);
                request.setAttribute("typejsp", "add");	
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","ѡȡ����ʧ�ܣ�"));
				
			}*/
			
			
			DynaActionForm dform = (DynaActionForm) form;
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
            Session hs=null;
			Customer ct=null;
			List list2=new ArrayList();
		
				try {
					hs=HibernateUtil.getSession();
					
					
						ct=(Customer)hs.get(Customer.class, "xjs000");


				} catch (Exception e) {
					e.printStackTrace();
				}
			
			request.setAttribute("CustomerList", ct);
			request.setAttribute("typejsp", "add");
			request.setAttribute("userInfoList", userInfo);
			request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));// ����ά��վ�б�
			return mapping.findForward("toContractDisplayAddMondity");
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
			boolean istrue = false;
			ServicingContractMaster scm = null;
			String isreturn=request.getParameter("isreturn");
			String[] elevatorNos=request.getParameterValues("elevatorNo");
			String[] salesContractNos=request.getParameterValues("salesContractNo");
			String[] projectNames=request.getParameterValues("projectName");
			String[] contentss=request.getParameterValues("contents");
//			String billNo=(String)dform.get("billNo");
			String wgbillno="";
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				//System.out.println(dform.toString());
				scm = new ServicingContractMaster();
				BeanUtils.copyProperties(scm, dform); // ��������ֵ				
				String datetime = xj.getdatetime();
				String year = datetime.substring(2,4);
				String mouse=datetime.substring(5,7);
				wgbillno = CommonUtil.getBillno(year,"ServicingContractMaster", 1)[0];// ������ˮ��	
				//����ά�ĺ�ͬ��
				String maintContractNo="WG";
				maintContractNo+=userInfo.getComID();
				maintContractNo+=year;
				maintContractNo+=mouse;
				maintContractNo+="-";
                List list2=hs.createQuery("select a from ServicingContractMaster a where a.maintContractNo like '"+maintContractNo+"%' order by a.wgBillno desc").list();
				if(list2!=null && list2.size()>0){
					ServicingContractMaster scm2=(ServicingContractMaster)list2.get(0);
					String[] num=scm2.getMaintContractNo().split("-");
					int i=0;
					i=Integer.valueOf(num[1])+1;
					maintContractNo+=String.format("%03d",i);
				}else{
					maintContractNo+="001";
				}
				
				scm.setWgBillno("WG"+wgbillno);// ��ˮ��
				scm.setMaintContractNo(maintContractNo);//ά�ĺ�ͬ��
				scm.setOperId(userInfo.getUserID());// ¼����
				scm.setAttn(userInfo.getUserID());//������
				scm.setMaintDivision(userInfo.getComID());
				scm.setOperDate(datetime);// ¼��ʱ��
				scm.setCompanyId2("xjs000");
				scm.setAuditStatus("N");//δ���
				scm.setTaskSubFlag("N");//δ�´�
				scm.setFinishFlag("N");//�깤��־
				if(isreturn!=null && "Y".equals(isreturn)){
					scm.setSubmitType("Y");
				}else{
					scm.setSubmitType("N");	
				}
				hs.save(scm);
				hs.flush();
				//���������Ϣ
				if(elevatorNos!=null && elevatorNos.length>0){
					for(int i=0;i<elevatorNos.length;i++){
						ServicingContractDetail scd=new ServicingContractDetail();
						scd.setServicingContractMaster(scm);
						scd.setElevatorNo(elevatorNos[i]);
						scd.setSalesContractNo(salesContractNos[i]);
						scd.setProjectName(projectNames[i]);
						scd.setContents(contentss[i]);
						scd.setServicingContractMaster(scm);
						hs.save(scd);
					}
				}
				tx.commit();		
			} catch (Exception e1) {
				tx.rollback();
				e1.printStackTrace();
				log.error(e1.getMessage());
				DebugUtil.print(e1, "Hibernate region Insert error!");
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
					if(isreturn!=null && "Y".equals(isreturn)){
					forward = mapping.findForward("returnList");	
					}else{
					forward = mapping.findForward("returnAdd");		
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
			
			request.setAttribute("navigator.location","ά�ĺ�ͬ���� >> �޸�");	
			DynaActionForm dform = (DynaActionForm) form;
			HttpSession session=request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			ActionErrors errors = new ActionErrors();		
			ActionForward forward = null;
			String id = request.getParameter("id");
			if(id==null){
				id=(String)dform.get("wgBillno");
			}
			String error = (String) request.getAttribute("error");
			Session hs = null;
			ServicingContractMaster scm=null;
			Customer ctj=null;
			Customer cty=null;
			List ServicingContractDetailList = new ArrayList();
			if (id != null) {
					try {
						hs = HibernateUtil.getSession();
						scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
						//�׷���λ
						String companyId=scm.getCompanyId();
						if(companyId!=null && !"".equals(companyId)){
							ctj=(Customer)hs.get(Customer.class, companyId);
						}
						//�ҷ���λ
						String companyId2=scm.getCompanyId2();
						if(companyId2!=null && !"".equals(companyId2)){
							cty=(Customer)hs.get(Customer.class, companyId2);
						}
						
						List list=hs.createQuery("select a from ServicingContractDetail a where a.servicingContractMaster.wgBillno='"+id+"'").list();	
						if(list!=null && list.size()>0){
							for(int i=0;i<list.size();i++){
								ServicingContractDetail scd=(ServicingContractDetail)list.get(i);
								ServicingContractDetailList.add(scd);
							}
						}
                     
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
			}
			request.setAttribute("ServicingContractMasterList", scm);
			request.setAttribute("CustomerList", ctj);
			request.setAttribute("CustomerList2", cty);
			request.setAttribute("ServicingContractDetailList", ServicingContractDetailList);
			request.setAttribute("userInfoList", userInfo);
			request.setAttribute("typejsp", "mondity");
			request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			forward = mapping.findForward("toContractDisplayAddMondity");
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
			String id = (String) dform.get("wgBillno");
			
			String[] elevatorNos=request.getParameterValues("elevatorNo");
			String[] contentss =request.getParameterValues("contents");	
			String[] salesContractNos=request.getParameterValues("salesContractNo");
			String[] projectNames =request.getParameterValues("projectName");
			boolean istrue = false;
			ServicingContractMaster scm = null;
			String isreturn=request.getParameter("isreturn");
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				hs.createQuery("delete from ServicingContractDetail a where a.servicingContractMaster.wgBillno='"+id+"'").executeUpdate();
				hs.flush();
				scm =(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
				BeanUtils.copyProperties(scm, dform); // ��������ֵ
				String todayDate = xj.getdatetime();
				String year = todayDate.substring(2,4);	
				scm.setOperId(userInfo.getUserID());// ¼����
				scm.setOperDate(todayDate);// ¼��ʱ��		
				scm.setAttn(userInfo.getUserID());//������
				scm.setFinishFlag("N");//�깤��־
		        if(isreturn!=null && "Y".equals(isreturn)){
					scm.setSubmitType("Y");
					}else{
					scm.setSubmitType("N");	
					}
				hs.save(scm);	
				//������ϸ
				if(elevatorNos!=null && elevatorNos.length>0 ){
					for(int i=0;i<elevatorNos.length;i++){
						ServicingContractDetail scd=new ServicingContractDetail();
						scd.setServicingContractMaster(scm);
						scd.setElevatorNo(elevatorNos[i]);
						scd.setSalesContractNo(salesContractNos[i]);
						scd.setProjectName(projectNames[i]);
						scd.setContents(contentss[i]);
						hs.save(scd);
					}
				}
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
					
					if(isreturn!=null && "Y".equals(isreturn)){
						forward = mapping.findForward("returnList");	
					}else{
						forward = mapping.findForward("returnMondity");		
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

			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();
			Session hs = null;
			Transaction tx = null;
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				String id = request.getParameter("id");
				ServicingContractMaster scqm = (ServicingContractMaster) hs.get(ServicingContractMaster.class, id);
				if (scqm != null) {
					hs.createQuery("delete from ServicingContractDetail a where a.servicingContractMaster.wgBillno='"+id+"'").executeUpdate();
					hs.delete(scqm);					
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));						
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

			String naigation = new String();
			ActionForward forward = null;
			HttpSession session = request.getSession();
			ServeTableForm tableForm = (ServeTableForm) form;
			String action = tableForm.getAction();

			String name = tableForm.getProperty("name");// ����
			String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
			String startDates = tableForm.getProperty("startDates");// ��ʼ�볧����
			String startDatee = tableForm.getProperty("startDatee");// �����볧����
			String enabledFlag = tableForm.getProperty("enabledFlag");// ���ñ�־
			Session hs = null;
			XSSFWorkbook wb = new XSSFWorkbook();

			try {
				hs = HibernateUtil.getSession();

				Criteria criteria = hs.createCriteria(PersonnelManageMaster.class);
				if (contractNo != null && !contractNo.equals("")) {
					criteria.add(Expression.like("contractNo", "%" + contractNo.trim() + "%"));
				}
				if (name != null && !name.equals("")) {
					criteria.add(Expression.like("name", "%" + name.trim() + "%"));
				}
				if (startDates != null && !startDates.equals("")) {
					criteria.add(Expression.ge("startDate", startDates.trim()));
				}
				if (startDatee != null && !startDatee.equals("")) {
					criteria.add(Expression.le("startDate", startDatee.trim()));
				}
				if (enabledFlag != null && !enabledFlag.equals("")) {
					criteria.add(Expression.eq("enabledFlag", enabledFlag));
				}

				criteria.addOrder(Order.asc("billno"));

				List roleList = criteria.list();

				XSSFSheet sheet = wb.createSheet("ά�������������Ϣ");
				
				Locale locale = this.getLocale(request);
				MessageResources messages = getResources(request);
				
				String[] columnNames = { "billno", "name", "education", "idCardNo",
						"familyAddress", "phone", "contractProperties",
						"contractNo", "startDate", "endDate", "years", "sector",
						"workAddress", "jobTitle", "level", "enabledFlag", "rem",
						"operId", "operDate" };

				if (roleList != null && !roleList.isEmpty()) {
					int l = roleList.size();
					XSSFRow row0 = sheet.createRow( 0);
									
					for (int i = 0; i < columnNames.length; i++) {
						XSSFCell cell0 = row0.createCell((short)i);
						cell0.setCellValue(messages.getMessage(locale,"personnelManage." + columnNames[i]));
					}
					
					Class<?> superClazz = PersonnelManageMaster.class.getSuperclass();
					for (int i = 0; i < l; i++) {
						PersonnelManageMaster master = (PersonnelManageMaster) roleList.get(i);
						// ����Excel�У���0�п�ʼ
						XSSFRow row = sheet.createRow( i+1);					
						for (int j = 0; j < columnNames.length; j++) {
							// ����Excel��
							XSSFCell cell = row.createCell((short)j);
							cell.setCellValue(getValue(master, superClazz, columnNames[j]));						
						}
					}
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
			
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("ά�������������Ϣ", "utf-8") + ".xlsx");
			wb.write(response.getOutputStream());
			
			return response;
		}	
		
		/**
		 * �ύ���������
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return ActionForward
		 */

		public ActionForward toReferRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();
			Session hs = null;
			Transaction tx = null;
			ActionForward forward = null;
			HttpSession session = request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
			ServicingContractMaster scm=null;
			String id = request.getParameter("id");
			if (id != null && id.length() > 0) {
				try {
					hs=HibernateUtil.getSession();	
					tx=hs.getTransaction();
					tx.begin();
					scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
					scm.setSubmitType("Y");
					hs.saveOrUpdate(scm);
					tx.commit();
				} catch (Exception e) {
					tx.rollback();
					e.printStackTrace();
				} finally {
                    hs.close();		
			    }			
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","�ύʧ�ܣ�"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			forward = mapping.findForward("returnList");
			return forward;
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
		 * Method toSearchRecord execute, Search record
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return ActionForward
		 */

		@SuppressWarnings("unchecked")
		public ActionForward toNextSearchRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			
			request.setAttribute("navigator.location","ά�ĺ�ͬ����  >> ά�ı���ѡȡ¼��");		
			ActionForward forward = null;
			HttpSession session = request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			ServeTableForm tableForm = (ServeTableForm) form;
			String action = tableForm.getAction();
            List list2=new ArrayList();
			if (tableForm.getProperty("genReport") != null
					&& !tableForm.getProperty("genReport").equals("")) {
				try {
					response = toExcelRecord(form,request,response);
				} catch (IOException e) {
					e.printStackTrace();
				}
				forward = mapping.findForward("exportExcel");
				tableForm.setProperty("genReport","");

			} else {
				HTMLTableCache cache = new HTMLTableCache(session, "wgchangeContractQuoteList2");

				DefaultHTMLTable table = new DefaultHTMLTable();
				table.setMapping("fServicingContractNextSearch");
				table.setLength(SysConfig.HTML_TABLE_LENGTH);
				cache.updateTable(table);
				table.setSortColumn("a.billNo");
				table.setIsAscending(false);
				cache.updateTable(table);

				if (action.equals(ServeTableForm.NAVIGATE)
						|| action.equals(ServeTableForm.SORT)) {
					cache.loadForm(tableForm);
				} else {
					table.setFrom(0);
				}
				cache.saveForm(tableForm);				
				String companyId = tableForm.getProperty("companyName");// �׷���λ����
				String billNo = tableForm.getProperty("billNo");// ������ˮ��
				String maintDivision = tableForm.getProperty("maintDivision");//����ά���ֲ�
				 if(maintDivision==null){
                 	List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
         			if(maintDivision == null || maintDivision.equals("")){
         				Map map = (Map)maintDivisionList.get(0);
         				maintDivision = (String)map.get("grcid");
         			}
                 }				 
				Session hs = null;
                Query qu=null;
				try {

					hs = HibernateUtil.getSession();
                    String sql=" select a,b,c,d from ServicingContractQuoteMaster a,Loginuser b,Customer c,Company d where 1=1"
                    		+ " and a.attn=b.userid and a.companyId =c.companyId and a.maintDivision=d.comid and a.status=1 "
                    		+ " and a.billNo not in(select billNo from ServicingContractMaster)";
                    sql+=xj.getsql("c.companyName", companyId);
                    sql+=xj.getsql("a.billNo", billNo);
                    sql+=xj.getsql("d.comid", maintDivision);
                    if(table.getIsAscending()){
                    	sql+=" order by "+table.getSortColumn()+"";
                    }else{
                    	sql+=" order by "+table.getSortColumn()+" desc";
                    }
                    qu=hs.createQuery(sql);
					table.setVolume(qu.list().size());// ��ѯ�ó����ݼ�¼��;

					// �ó���һҳ�����һ����¼����;
					qu.setFirstResult(table.getFrom()); // pagefirst
					qu.setMaxResults(table.getLength());

					cache.check(table);

					List wgchangeContractQuoteList = qu.list();
					if(wgchangeContractQuoteList!=null && wgchangeContractQuoteList.size()>0){
						for(int i=0;i<wgchangeContractQuoteList.size();i++){
							Object[] obj=(Object[])wgchangeContractQuoteList.get(i);
							ServicingContractQuoteMaster scqm=(ServicingContractQuoteMaster)obj[0];
							Loginuser lo=(Loginuser)obj[1];
							Customer  ct=(Customer)obj[2];
							Company  cpy=(Company)obj[3];
							scqm.setAttn(lo.getUsername());
							scqm.setCompanyId(ct.getCompanyName());
						    scqm.setMaintDivision(cpy.getComname());
						    list2.add(scqm);
						}
	
					}					
					table.addAll(list2);
					session.setAttribute("wgchangeContractQuoteList2", table);
					request.setAttribute("maintDivisionList", Grcnamelist1.getgrcnamelist(userInfo.getUserID()));

			
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
				forward = mapping.findForward("tonextList");
			}
			return forward;
		}
		
		/*
		 * 
		 * �����´�
		 * 
		 * 
		 */
		public ActionForward  totask(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){
			    request.setAttribute("navigator.location","ά�ĺ�ͬ����  >> �����´�");
			    DynaActionForm dform = (DynaActionForm) form;
				ActionErrors errors = new ActionErrors();
				ActionForward forward = null;			
				String id = (String) dform.get("id");
				Session hs = null;
				ServicingContractMaster scm=null;
				Customer ctj=null;
				Customer cty=null;
				List ServicingContractDetailList = new ArrayList();
			   if (id != null) {				
				try {
					hs = HibernateUtil.getSession();
					scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
					scm.setAttn(bd.getName("Loginuser", "username", "userid", scm.getAttn()));
					scm.setMaintDivision(bd.getName("Company", "comname", "comid", scm.getMaintDivision()));
					scm.setTaskUserId(bd.getName("Loginuser", "username", "userid", scm.getTaskUserId()));					
				    scm.setTaskSubFlag(scm.getTaskSubFlag().equals("N")?"δ�´�":"���´�");
				    scm.setAuditOperid(bd.getName("Loginuser", "username", "userid", scm.getAuditOperid()));
				    scm.setAuditStatus(scm.getAuditStatus().equals("N")?"δ���":"�����");
				    request.setAttribute("maintStationName", bd.getName(hs, "Storageid","storageName", "storageID", scm.getMaintStation())); //ά��վ����
					//�׷���λ
					String companyId=scm.getCompanyId();
					if(companyId!=null && !"".equals(companyId)){
						ctj=(Customer)hs.get(Customer.class, companyId);
					}
					//�ҷ���λ
					String companyId2=scm.getCompanyId2();
					if(companyId2!=null && !"".equals(companyId2)){
						cty=(Customer)hs.get(Customer.class, companyId2);
					}
					
					List list=hs.createQuery("select a from ServicingContractDetail a where a.servicingContractMaster.wgBillno='"+id+"'").list();	
					if(list!=null && list.size()>0){
						for(int i=0;i<list.size();i++){
							ServicingContractDetail scd=(ServicingContractDetail)list.get(i);
							ServicingContractDetailList.add(scd);
						}
					}
					
				} catch (DataStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
			}
			request.setAttribute("ServicingContractMasterList", scm);
			request.setAttribute("CustomerList", ctj);
			request.setAttribute("CustomerList2", cty);
			request.setAttribute("ServicingContractDetailList", ServicingContractDetailList);
			request.setAttribute("typejsp", "totask");
			dform.set("id", id);
			dform.set("taskSubFlag", scm.getTaskSubFlag());
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
				forward = mapping.findForward("toContractDisplayAddMondity");
				return forward;
		
		}
		/**
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward tosaveTask(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			ActionForward forward = null;
			DynaActionForm dform = (DynaActionForm) form;
			ActionErrors errors = new ActionErrors();
			HttpSession session=request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			String id=(String)dform.get("id");
			String taskRem=request.getParameter("taskRem");//������
			String isreturn=request.getParameter("isreturn");
			ServicingContractMaster scm=null;
		    Session hs=null;
			Transaction tx=null;			
			try {
				hs=HibernateUtil.getSession();
				tx=hs.beginTransaction();
				scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
				String taskSubDate=xj.getdatetime();//���ʱ��
				String taskUserId=userInfo.getUserID();//�����
				scm.setTaskUserId(taskUserId);
				scm.setTaskSubFlag("Y");
				scm.setTaskSubDate(taskSubDate);
				scm.setTaskRem(taskRem);
				hs.save(scm);
				tx.commit();
				
			} catch (Exception e) {
				tx.rollback();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				hs.close();
			}
			if (errors.isEmpty()) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","�´�ɹ���"));
			} else {
				request.setAttribute("error", "Yes");
			}
			if(!errors.isEmpty()){
				saveErrors(request,errors);
			}
			if(isreturn!=null && isreturn.equals("Y")){
				forward=mapping.findForward("returnList");
			}else{
				forward=mapping.findForward("returntotask");
			}			
			return forward;
		}
	}

