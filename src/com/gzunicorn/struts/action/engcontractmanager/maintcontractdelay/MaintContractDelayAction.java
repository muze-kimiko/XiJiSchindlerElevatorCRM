package com.gzunicorn.struts.action.engcontractmanager.maintcontractdelay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.commons.lang.StringUtils;
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
import com.gzunicorn.bean.ProcessBean;
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
import com.gzunicorn.hibernate.basedata.customer.Customer;

import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelaydetail.MaintContractDelayDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelaymaster.MaintContractDelayMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelayprocess.MaintContractDelayProcess;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.workflow.bo.JbpmExtBridge;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class MaintContractDelayAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractDelayAction.class);
	
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
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "maintcontractdelay", null);
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchRecord";
			return dispatchMethod(mapping, form, request, response, name);
		} else {
			ActionForward forward = super.execute(mapping, form, request,response);
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
		
		request.setAttribute("navigator.location","ά����ͬ�ӱ����� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

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
			HTMLTableCache cache = new HTMLTableCache(session, "maintContractDelayList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fMaintContractDelay");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("jnlno");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String jnlno = tableForm.getProperty("jnlno");// ��ˮ��
			String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
			String companyName = tableForm.getProperty("companyName");// �׷���λ
			String operId = tableForm.getProperty("operId");// ¼����
			String maintDivision = tableForm.getProperty("maintDivision");// �����ֲ�	
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			//String status = tableForm.getProperty("status");// ���״̬
			String auditStatus = tableForm.getProperty("auditStatus");// ���״̬

			//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			if(maintDivision == null || maintDivision.equals("")){
				Map map = (Map)maintDivisionList.get(0);
				maintDivision = (String)map.get("grcid");
			}
			
			Session hs = null;
			Query query = null;
			try {

				hs = HibernateUtil.getSession();
				
				String maintStation="%";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					maintStation=userInfo.getStorageId();
				}
				
				String[] colNames = {
						"a.jnlno as jnlno",
						"a.billno as billno",
						"f.maintContractNo as maintContractNo",
						"f.contractSdate as contractSdate",
						"f.contractEdate as contractEdate",
						"b.username as operName",
						"a.submitType as submitType",
						"a.auditStatus as auditStatus",
//						"a.processName as processName",	
//						"d.typename as statusName",								
						"e.storagename as maintStationName",
						"c.comname as maintDivisionName",
						"g.companyName as companyName"
					};
				
				String sql = "select "+StringUtils.join(colNames, ",")+
						" from MaintContractDelayMaster a,Loginuser b,Company c," +/*ViewFlowStatus d,*/
						" Storageid e,MaintContractMaster f,Customer g" + 
						" where a.operId = b.userid"+
						" and f.maintDivision = c.comid"+
						" and f.companyId = g.companyId"+
						" and a.maintStation = e.storageid"+
						" and a.billno = f.billNo"+
						" and f.MaintStation like '"+maintStation+"'";
				
				if (jnlno != null && !jnlno.equals("")) {
					sql += " and jnlno like '%"+jnlno.trim()+"%'";
				}
				if (maintContractNo != null && !maintContractNo.equals("")) {
					sql += " and maintContractNo like '%"+maintContractNo.trim()+"%'";
				}
				if (companyName != null && !companyName.equals("")) {
					sql += " and companyName like '%"+companyName.trim()+"%'";
				}
				if (operId != null && !operId.equals("")) {
					sql += " and username like '%"+operId.trim()+"%'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and f.maintDivision like '"+maintDivision.trim()+"'";
				}
				if (submitType != null && !submitType.equals("")) {
					sql += " and a.submitType like '"+submitType.trim()+"'";
				}
				/*if (status != null && !status.equals("")) {
					sql += " and status like '"+status.trim()+"'";
				}*/
				if(auditStatus!=null && !auditStatus.equals("")){
					sql+=" and a.auditStatus like '"+auditStatus.trim()+"'";
				}
				if (table.getIsAscending()) {
					sql += " order by "+ table.getSortColumn() +" desc";
				} else {
					sql += " order by "+ table.getSortColumn() +" asc";
				}
				
				query = hs.createSQLQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List maintContractDelayList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map<String,String> map = new HashMap<String,String>();
					for(int i=0; i<colNames.length; i++){
						map.put(colNames[i].split(" as ")[1].trim(), String.valueOf(objs[i]));
					}
					maintContractDelayList.add(map);
				}

				table.addAll(maintContractDelayList);
				session.setAttribute("maintContractDelayList", table);

				// ����״̬�������б�
				request.setAttribute("processStatusList", bd.getProcessStatusList());
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);
				// ά��վ�������б�
				request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));// ά��վ�����б�

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
		return mapping.findForward("toList");
	}
	
	/**
	 * ����½�����Ĳ�ѯ���淽��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	@SuppressWarnings("unchecked")
	public ActionForward toSearchNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ���� >> ��ѯ�б� ");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session, "maintContractDelayNextList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fMaintContractDelayNext");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		cache.updateTable(table);
		table.setSortColumn("contractEdate");
		table.setIsAscending(true);
		cache.updateTable(table);

		if (action.equals(ServeTableForm.NAVIGATE)
				|| action.equals(ServeTableForm.SORT)) {
			cache.loadForm(tableForm);
		} else {
			table.setFrom(0);
		}
		cache.saveForm(tableForm);
		
		String billNo = tableForm.getProperty("billNo");// ��ˮ��
		String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
		String maintDivision = tableForm.getProperty("maintDivision");// ����ά��վ		
		String companyID = tableForm.getProperty("companyID");//�׷���λ	
		String projectName = tableForm.getProperty("projectName");// ��Ŀ����
		String salesContractNo = tableForm.getProperty("salesContractNo");//���ۺ�ͬ��

		//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
		List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
		if(maintDivision == null || maintDivision.equals("")){
			Map map = (Map)maintDivisionList.get(0);
			maintDivision = (String)map.get("grcid");
		}
		
		Session hs = null;
		Query query = null;
		try {

			hs = HibernateUtil.getSession();
			
			String maintStation="%";
			//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
			String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
			List vmlist=hs.createSQLQuery(sqlss).list();
			if(vmlist!=null && vmlist.size()>0){
				maintStation=userInfo.getStorageId();
			}
			
			String sql = "select a,b.username as attn,c.comname as maintDivision,s.storagename,ct.companyName"+
					" from MaintContractMaster a,Loginuser b,Company c,Storageid s,Customer ct " + 
					" where a.attn = b.userid and a.companyId=ct.companyId " +
					" and a.maintDivision = c.comid" +
					" and a.contractStatus in ('ZB','XB')" + 
					" and a.auditStatus = 'Y'" +
					" and a.taskSubFlag = 'Y'" +
					" and a.maintStation like '"+maintStation+"'" +
					" and a.maintStation=s.storageid ";
			
			if (billNo != null && !billNo.equals("")) {
				sql += " and a.billNo like '%"+billNo.trim()+"%'";
			}
			if (maintContractNo != null && !maintContractNo.equals("")) {
				sql += " and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
			}
			if (companyID != null && !companyID.equals("")) {
				sql += " and ct.companyName like '%"+companyID.trim()+"%'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and a.maintDivision like '"+maintDivision.trim()+"'";
			}
			
			if (salesContractNo != null && !salesContractNo.equals("")) {
				sql += " and a.billNo in(select distinct billNo from MaintContractDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
			}
			if (projectName != null && !projectName.equals("")) {
				sql += " and a.billNo in(select distinct billNo from MaintContractDetail where projectName like '%"+projectName.trim()+"%')";
			}
			
			if (table.getIsAscending()) {
				sql += " order by a."+ table.getSortColumn() +" asc";
			} else {
				sql += " order by a."+ table.getSortColumn() +" desc";
			}
			
			//System.out.println(">>>"+sql);
			
			query = hs.createQuery(sql);
			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

			// �ó���һҳ�����һ����¼����;
			query.setFirstResult(table.getFrom()); // pagefirst
			query.setMaxResults(table.getLength());

			cache.check(table);

			List list = query.list();
			List maintContractDelayNextList = new ArrayList();
			for (Object object : list) {
				Object[] objs = (Object[])object;
				MaintContractMaster master = (MaintContractMaster) objs[0];
				master.setAttn(String.valueOf(objs[1]));
				master.setMaintDivision(String.valueOf(objs[2]));	
				master.setMaintStation(String.valueOf(objs[3]));
				master.setCompanyId(String.valueOf(objs[4]));
				maintContractDelayNextList.add(master);
			}

			table.addAll(maintContractDelayNextList);
			session.setAttribute("maintContractDelayNextList", table);
			// �ֲ��������б�
			request.setAttribute("maintDivisionList", maintDivisionList);

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
		forward = mapping.findForward("toNextList");
		
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
		
		request.setAttribute("navigator.location","ά����ͬ�ӱ����� >> �鿴");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		display(form, request, errors, "display");
		
		//lijun add ��ҳת����ת�鿴�󣬲���Ҫ�ٴγ�������ҳ
		String workisdisplay=request.getParameter("workisdisplay");
		if(workisdisplay!=null && workisdisplay.equals("Y")){
			request.setAttribute("workisdisplay", workisdisplay);
			Session hs = null;
			Transaction tx = null;
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				String id=request.getParameter("id");
				String upsql="update MaintContractDelayMaster set workisdisplay='"+workisdisplay+"' where jnlno='"+id+"'";
				hs.connection().prepareStatement(upsql).executeUpdate();
				
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
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "Hibernate close error!");
				}
			}
		}
				
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		request.setAttribute("display", "yes");

		forward = mapping.findForward("toDisplay");
		return forward;
	}
	
	/**
	 * ��ת���½�ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPrepareAddRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ�ӱ����� >> �½�");	
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		display(form, request, errors, "delay");

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		dform.set("id",request.getParameter("id"));
		request.setAttribute("doType", "delay");//�ӱ�
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		forward = mapping.findForward("toAdd");	
		return forward;
	}
	
	/**
	 * �½�����
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
	
		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String jnlnos = ""; //�ӱ���ˮ��
		
		//��ֹ���ظ��ύ
		if(!isTokenValid(request, true)){
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		} else {					

			String maintStation = request.getParameter("maintStation");// ����ά��վ
			String id = (String) dform.get("id");
			String rem = (String) dform.get("rem"); 
			String[] rowids = request.getParameterValues("rowid");// ά�������ͬ��ϸ�к�
			String[] elevatorNos = request.getParameterValues("elevatorNo");// ���ݱ��
			String[] realityEdates = request.getParameterValues("realityEdate");// ʵ�ʽ�������
			String[] assignedMainStations = request.getParameterValues("assignedMainStation");// �´�ά��վ
			
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				Connection conn = null;
				PreparedStatement ps = null;
				
				MaintContractDelayMaster master = null;		
				MaintContractDelayDetail detail = null;				
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();


					String year = CommonUtil.getToday().substring(2,4);
					jnlnos = "YB"+CommonUtil.getBillno(year,"MaintContractDelayMaster", 1)[0];// ������ˮ��	

						
					// ά���ӱ�����Ϣ
					master = new MaintContractDelayMaster();
					master.setJnlno(jnlnos);// ά���ӱ���ˮ��
					master.setBillno(id);// ά��������ˮ��
					master.setOperId(userInfo.getUserID());// ¼����
					master.setOperDate(CommonUtil.getNowTime());// ¼������
					master.setMaintStation(maintStation);// ����ά��վ
					master.setStatus(WorkFlowConfig.State_NoStart);// ����״̬
					master.setSubmitType("N");// �ύ��־
					master.setAuditStatus("N");//���״̬
					master.setTokenId(new Long(0));// ��������
					master.setProcessName("");// ��������
					master.setRem(rem);
					master.setWorkisdisplay(null);
					master.setWorkisdisplay2(null);
					master.setAuditDate("");
					master.setAuditOperid("");
					master.setAuditRem("");
					hs.save(master);
				
					// ά���ӱ���ϸ
					for(int j=0; j<assignedMainStations.length; j++){
						detail = new MaintContractDelayDetail();
						detail.setJnlno(jnlnos);// ά���ӱ���ˮ��
						detail.setRowid(Integer.parseInt(rowids[j]));// ά��������ϸ�к�
						detail.setDelayEdate(realityEdates[j]);// �ӱ���������
						hs.save(detail);
					}
					request.setAttribute("id", master.getJnlno());
					
					tx.commit();
				} catch (Exception e) {				
					e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
					if(tx != null){
						tx.rollback();
					}
				} finally {
					if(hs != null){
						hs.close();				
					}				
				}
				
			} else {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
			}	

		}	

		String isreturn = request.getParameter("isreturn");
		
		if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
			refer(form,request,errors,jnlnos); //�ύ
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			forward = mapping.findForward("returnList");
		} else {
			
			// return addnew page
			if (errors.isEmpty()) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			} else {
				request.setAttribute("error", "Yes");
			}

			String urlstr="/"+SysConfig.WEB_APPNAME+"/maintContractDelayAction.do?id="+jnlnos
					+"&method=toPrepareUpdateRecord&issuccess=Y";
			response.sendRedirect(urlstr);
			//forward = mapping.findForward("returnModify");	
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return forward;
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
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ�ӱ����� >> �޸�");	
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		display(form, request, errors, "update");
		
		request.setAttribute("maintDivisionName", userInfo.getComName()); // ά���ֲ�����	
		
		String issuccess=request.getParameter("issuccess");
		if(issuccess!=null && issuccess.trim().equals("Y")){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		request.setAttribute("doType", "delay");//�ӱ�
		saveToken(request); //�������ƣ���ֹ���ظ��ύ	
		
		//��ҳ����������
		String isclosework=request.getParameter("isclosework");
		if(isclosework!=null && isclosework.equals("Y")){
			request.setAttribute("isclosework", isclosework);
		}
		
		forward = mapping.findForward("toModify");
		
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
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String id = (String) dform.get("id"); //ά���ӱ���ˮ��
		String rem=(String) dform.get("rem");
	
		//��ֹ���ظ��ύ
		if(!isTokenValid(request, true)){
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		} else {					

			dform.set("id", id); //ά���ӱ���ˮ��
			String[] rowids = request.getParameterValues("rowid");// ά��������ϸ�к�
			String[] realityEdates = request.getParameterValues("realityEdate");// ʵ�ʽ�������
			
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				Connection conn = null;
				PreparedStatement ps = null;
				
				MaintContractDelayMaster master = null;		
//				MaintContractDelayDetail detail = null;				
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();
					
					master = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
					master.setRem(rem);
					master.setOperId(userInfo.getUserID());// ¼����
					master.setOperDate(CommonUtil.getNowTime());// ¼������
					master.setWorkisdisplay(null);
					master.setWorkisdisplay2(null);
					master.setAuditDate("");
					master.setAuditOperid("");
					master.setAuditRem("");
					
					if(master != null ){
						// ά���ӱ���ͬ��ϸ					
						String sql = "update MaintContractDelayDetail set delayEdate = ? from MaintContractDelayDetail where rowid=?";									
						conn = hs.connection();
						ps = conn.prepareStatement(sql);	
						for(int i = 0; i < rowids.length; i++){	
							ps.setString(1, realityEdates[i]);
							ps.setString(2, rowids[i]);
							ps.addBatch();
						}
						ps.executeBatch();	
					}
					hs.save(master);

					tx.commit();
					
				} catch (Exception e) {				
					e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
					if(tx != null){
						tx.rollback();
					}
				} finally {
					if(hs != null){
						hs.close();				
					}				
				}
				
			} else {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
			}	

		}

		try {
			String isreturn = request.getParameter("isreturn");					
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				refer(form, request, errors, id);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
				forward = mapping.findForward("returnList");
				
			} else {
				// return modify page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				
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
	 * �ύ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws HibernateException
	 */
	public ActionForward toReferRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		refer(form,request,errors,request.getParameter("id")); //�ύ

		if (!errors.isEmpty()){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.submitToAudit.failed")); //��ʾ���ύ���ʧ�ܣ���
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.submitToAudit.succeed")); //��ʾ���ύ��˳ɹ�����
		}
		
		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnList");

	}
	
	/**
	 * ɾ����¼
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
			
			String id = (String) dform.get("id");
			MaintContractDelayMaster master = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
			if (master != null && "N".equals(master.getSubmitType())) {
				hs.createQuery("delete from MaintContractDelayDetail where jnlno='"+id+"'").executeUpdate();
				hs.delete(master);
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
		
		return response;
	}

	public void display(ActionForm form, HttpServletRequest request ,ActionErrors errors ,String flag){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		String id = request.getParameter("id");
		if(id==null || "".equals(id)){
			id=(String) request.getAttribute("id");
		}

		String billNo = null;
		Session hs = null;
		List maintContractDetailList = null;

		try {
			hs = HibernateUtil.getSession();
			//ά����ͬ�ӱ�����
			MaintContractDelayMaster delayMaster = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
			
			billNo = delayMaster != null ? delayMaster.getBillno() : id;// ά����ͬ������ˮ��
			

			Query query = hs.createQuery("from MaintContractMaster where billNo = '"+billNo+"'");
			List list = query.list();
			if (list != null && list.size() > 0) {

				// ά����ͬ��������Ϣ
				MaintContractMaster master = (MaintContractMaster) list.get(0);															
				master.setAttn(bd.getName(hs, "Loginuser","username", "userid",master.getAttn()));// ������
				master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid",master.getAuditOperid()));// �����
				master.setTaskUserId(bd.getName(hs, "Loginuser", "username", "userid",master.getTaskUserId()));// �´���	
				master.setMaintDivision(bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));// ά���ֲ�													
				master.setOperId(bd.getName(hs, "Loginuser","username", "userid",master.getOperId()));
				request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation()));//����ά��վ
				
				if(delayMaster != null){
					request.setAttribute("username", bd.getName(hs, "Loginuser","username", "userid",delayMaster.getOperId()));
					request.setAttribute("daytime", delayMaster.getOperDate());
					request.setAttribute("delayrem", delayMaster.getRem());
					dform.set("rem", delayMaster.getRem());
				}else{
					String username=userInfo.getUserName();
					String daytime=CommonUtil.getNowTime();
					request.setAttribute("username", username);
					request.setAttribute("daytime", daytime);
					request.setAttribute("delayrem", "");
				}
				
				//���ʽ
				String pmastr=master.getPaymentMethod();
				List pdlist=bd.getPullDownAllList("MaintContractQuoteMaster_PaymentMethodApply");
				String pmaname=bd.getOptionName(pmastr, pdlist);
				master.setR4(pmaname);
				//maintContractBean.setPaymentMethod(pmastr);//���ʽ
				//��ͬ������������
				String ccastrname="";
				String ccastr=master.getContractTerms();
				if(ccastr!=null && !ccastr.trim().equals("")){
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
				master.setR5(ccastrname);
				
				// �׷���λ��Ϣ
				Customer companyA = (Customer) hs.get(Customer.class,master.getCompanyId());															
				// �ҷ�����λ��Ϣ
				Customer companyB = (Customer) hs.get(Customer.class,master.getCompanyId2());
									
				// ά���ӱ�������ϸ�б�
				String sql = "select a,a.realityEdate from MaintContractDetail a where a.billNo = '"+billNo+"'";
				if(delayMaster != null){
					delayMaster.setAuditOperid(bd.getName(hs, "Loginuser","username", "userid",delayMaster.getAuditOperid()));
					sql = "select a,b.delayEdate from MaintContractDetail a,MaintContractDelayDetail b" +
							" where a.rowid = b.rowid" +
							" and b.jnlno = '"+delayMaster.getJnlno()+"'" + 
							" and a.assignedMainStation like '"+delayMaster.getMaintStation()+"'";
				}
				
				query = hs.createQuery(sql);	
				list = query.list();
				maintContractDetailList = new ArrayList();
				List signWayList = bd.getPullDownList("MaintContractDetail_SignWay");// ǩ��ʽ�����б�
				List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// �������������б�
				for (Object object : list) {
					Object[] objs = (Object[]) object;
					MaintContractDetail detail = (MaintContractDetail) objs[0];
					detail.setRealityEdate((String) objs[1]);
					detail.setSignWay(bd.getOptionName(detail.getSignWay(), signWayList));
					detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
					maintContractDetailList.add(detail);
				}
				
				/*if(delayMaster != null){
					//����������Ϣ
					query = hs.createQuery("from MaintContractDelayProcess where jnlno = '"+ delayMaster.getJnlno() + "' order by itemId");
					List processApproveList = query.list();
					for (Object object : processApproveList) {
						MaintContractDelayProcess process = (MaintContractDelayProcess) object;
						process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
					}
					request.setAttribute("processApproveList",processApproveList);
				}*/
				if(delayMaster==null){
					delayMaster=new MaintContractDelayMaster();
					delayMaster.setAuditStatus("N");
					delayMaster.setAuditDate("");
					delayMaster.setAuditOperid("");
					delayMaster.setAuditRem("");
				}

				request.setAttribute("delayBean", delayMaster);
				request.setAttribute("maintContractBean", master);	
				request.setAttribute("companyA",companyA);
				request.setAttribute("companyB",companyB);
				request.setAttribute("maintContractDetailList", maintContractDetailList);
				request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));// ά��վ�����б�
						
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
			
				
		
	}

		
	public void refer(ActionForm form, HttpServletRequest request,ActionErrors errors, String id){

		HttpSession httpsession = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)httpsession.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		if(id != null && !id.equals("")){

			Session hs = null;
			Transaction tx = null;
			JbpmExtBridge jbpmExtBridge=null;
			String userid = userInfo.getUserID(); //��ǰ��¼�û�id
			MaintContractDelayMaster master = null;
			
			try {
				hs = HibernateUtil.getSession();
										
				master = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
				
				if(!"Y".equals(master.getSubmitType())){
					tx = hs.beginTransaction();
					
					/*String processDefineID = Grcnamelist1.getProcessDefineID("maintcontractdelaymaster", master.getOperId());// ���̻���id
					if(processDefineID == null || processDefineID.equals("")){
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.string","<font color='red'>δ��������������Ϣ������������</font>"));
						throw new Exception();
					}
								
					//**=============== ����������ʵ����ʼ ===================**//*
					HashMap paraMap = new HashMap();
					jbpmExtBridge=new JbpmExtBridge();
					ProcessBean pd = null;		
					pd = jbpmExtBridge.getPb();
					
					Grcnamelist1.setJbpmAuditopers(pd, processDefineID, PropertiesUtil.getProperty("MaintStationManagerJbpm"), userInfo.getComID(), master.getMaintStation());// ��������
					
					pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,id,"",paraMap);
					//**==================== ���̽��� =======================**/
					
					master.setSubmitType("Y");// �ύ��־
					/*master.setProcessName(pd.getNodename());// ��������
					master.setStatus(pd.getStatus()); //����״̬
					master.setTokenId(pd.getToken());//��������*/
					hs.save(master);
					
					tx.commit();
				}
				
			} catch (Exception e) {				
				try {
					tx.rollback();
				} catch (HibernateException e3) {
					e3.printStackTrace();
				}
				if (jbpmExtBridge != null) {
					jbpmExtBridge.setRollBack();
				}
				e.printStackTrace();
			} finally {
				hs.close();
				if(jbpmExtBridge!=null){
					jbpmExtBridge.close();
				}
				
			}
			
		}		
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
	public void toAjaxDeleteRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session hs=null;
		Transaction tx = null;
		String jnlnostr=request.getParameter("jnlnostr");

		String isdele="Y";
		try {
			hs=HibernateUtil.getSession();
			if(jnlnostr!=null && !"".equals(jnlnostr)){
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				MaintContractDelayMaster master = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, jnlnostr);
				if (master != null) {
					hs.createQuery("delete from MaintContractDelayDetail where jnlno='"+jnlnostr+"'").executeUpdate();
					hs.delete(master);
				}
				
				tx.commit();
			}
		} catch (Exception e) {
			isdele="N";
			e.printStackTrace();
		} finally{
			hs.close();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		sb.append("<rows>");
		sb.append("<cols name='isdele' value='"+isdele+"'>").append("</cols>");
		sb.append("</rows>");
		sb.append("</root>");

		response.setHeader("Content-Type","text/html; charset=GBK");
		response.setCharacterEncoding("gbk"); 
		response.setContentType("text/xml;charset=gbk");
		response.getWriter().write(sb.toString());
	}
	
}	