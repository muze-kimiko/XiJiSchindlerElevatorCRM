package com.gzunicorn.struts.action.engcontractmanager.maintcontract;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
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
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.JSONUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SqlBeanUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.basedata.elevatorsales.ElevatorSalesInfo;
import com.gzunicorn.hibernate.contractpaymentmanage.contractpaymentmanage.ContractPaymentManage;
import com.gzunicorn.hibernate.custregistervisitplan.customervisitplandetail.CustomerVisitPlanDetail;
import com.gzunicorn.hibernate.custregistervisitplan.customervisitplanmaster.CustomerVisitPlanMaster;
import com.gzunicorn.hibernate.engcontractmanager.ContractFileinfo.ContractFileinfo;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.struts.action.query.SearchElevatorSaleAction;
import com.gzunicorn.workflow.bo.JbpmExtBridge;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class MaintContractAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractAction.class);
	
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
	 * ά����ͬ����
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
		
		String name = request.getParameter("method");
		
		if(!"toDisplayRecord".equals(name)){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "maintcontract", null);
			/** **********�����û�Ȩ�޹���*********** */
		}

		// Set default method is toSearchRecord
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
	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ���� >> ��ѯ�б�");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "maintContractList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fMaintContract");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("a.contractEdate");
			table.setIsAscending(true);
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
			
			String billNo = tableForm.getProperty("billNo");// ��ˮ��
			String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
			String companyName = tableForm.getProperty("companyName");// �׷���λ
			String attn = tableForm.getProperty("attn");// ������
			String maintDivision = tableForm.getProperty("maintDivision");// ����ά����		
			String maintstation = tableForm.getProperty("maintstation");// ����ά��վ	
			String contractStatus = tableForm.getProperty("contractStatus");// ��ͬ״̬			
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			String auditStatus = tableForm.getProperty("auditStatus");// ���״̬
			String taskSubFlag = tableForm.getProperty("taskSubFlag");// �����´��־
			String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
			String elevatorNo = tableForm.getProperty("elevatorNo");// ���ݱ��
			String warningStatus = tableForm.getProperty("warningStatus");//����״̬
			String contractEDate = tableForm.getProperty("contractEDate");//��ͬ��������
			String isEntrust = tableForm.getProperty("isEntrust");//�Ƿ�ί��
			String maintAddress = tableForm.getProperty("maintAddress");//ʹ�õ�λ����
			
			//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			if(maintDivision == null || maintDivision.equals("")){
				Map map = (Map)maintDivisionList.get(0);
				maintDivision = (String)map.get("grcid");
			}
			
			Session hs = null;
			SQLQuery query = null;
			try {

				hs = HibernateUtil.getSession();
				
				//��ѯά�����ں�ͬ   ���ݺ�ͬ����������ǰ3��������
				String today=DateUtil.getNowTime("yyyy-MM-dd");
				String datestr=DateUtil.getDate(today, "MM", 3);
				tableForm.setProperty("hiddatestr",datestr);
				
				String roleidshow="N";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					if(maintstation == null || maintstation.trim().equals("")){
						maintstation=userInfo.getStorageId();
					}
					roleidshow="Y";
					//��װά������  068 ���ܽ�����ǩ
					if(userInfo.getRoleID().equals("068")){
						roleidshow="N";
					}
				}
				request.setAttribute("roleidshow", roleidshow);

				List mainStationList=new ArrayList();
				//ά��վ������  A03=ά������,A48=ά��վ��Ա,ά��վ�� A49 
				if(vmlist!=null && vmlist.size()>0){
					String hql="select a from Storageid a where "
							+ "(a.storageid= '"+userInfo.getStorageId()+"' or "
							+ "a.storageid in(select parentstorageid from Storageid a where a.storageid= '"+userInfo.getStorageId()+"')) "
							+ "and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";	
					mainStationList=hs.createQuery(hql).list();
					
				}else{
					String hql="select a from Storageid a where a.comid like '"+maintDivision+"' " +
							"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
					mainStationList=hs.createQuery(hql).list();
				  
					 Storageid storid=new Storageid();
					 storid.setStorageid("%");
					 storid.setStoragename("ȫ��");
					 mainStationList.add(0,storid);
				}
				request.setAttribute("mainStationList", mainStationList);

				String sql = "select a.billNo,a.maintContractNo,a.contractSdate,a.contractEdate,"
						+ "isnull(a.warningStatus,'') as warningStatus,a.submitType,a.auditStatus,a.taskSubFlag,"
						+ "b.username as username,p.pullname as pullname,c.comname as comname,"
						+ "d.companyName as companyName,s.storagename as storagename, "
						+ "(select count(md.billNo) from MaintContractDetail md where md.billNo=a.billno) as connum, "
						+ "isnull(a.r2,'N') as r2 "
						+ "from MaintContractMaster a,"
						+ "Loginuser b,Company c,Customer d,Pulldown p,storageid s "
						+ "where a.attn = b.userid "
						+ " and a.maintDivision = c.comid "
						+ " and a.maintStation = s.storageid "
						+ " and a.companyId = d.companyId "
						+ " and a.contractStatus = p.pullid "
						+ " and p.typeflag = 'MaintContractMaster_ContractStatus'";
						//+ " and a.MaintStation like '"+maintStation+"'";
				
				if (isEntrust != null && !isEntrust.equals("")) {
					sql += " and isnull(a.r2,'N') like '"+isEntrust.trim()+"'";
				}
				if (contractEDate != null && !contractEDate.equals("")) {
					sql += " and a.contractEDate like '"+contractEDate.trim()+"%'";
				}
				if (contractEDate != null && !contractEDate.equals("")) {
					sql += " and a.contractEDate like '"+contractEDate.trim()+"%'";
				}
				if (billNo != null && !billNo.equals("")) {
					sql += " and a.billNo like '%"+billNo.trim()+"%'";
				}
				if (maintContractNo != null && !maintContractNo.equals("")) {
					sql += " and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
				}
				if (companyName != null && !companyName.equals("")) {
					sql += " and d.companyName like '%"+companyName.trim()+"%'";
				}
				if (attn != null && !attn.equals("")) {
					sql += " and b.username like '%"+attn.trim()+"%'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and a.maintDivision like '"+maintDivision.trim()+"'";
				}
				if (maintstation != null && !maintstation.equals("")) {
					sql += " and a.maintStation like '"+maintstation.trim()+"'";
				}
				if (contractStatus != null && !contractStatus.equals("")) {
					sql += " and a.contractStatus like '"+contractStatus.trim()+"'";
				}
				if (submitType != null && !submitType.equals("")) {
					sql += " and a.submitType like '"+submitType.trim()+"'";
				}
				if (auditStatus != null && !auditStatus.equals("")) {
					sql += " and a.auditStatus like '"+auditStatus.trim()+"'";
				}
				if (taskSubFlag != null && !taskSubFlag.equals("")) {
					sql += " and taskSubFlag like '"+taskSubFlag.trim()+"'";
				}
				if (warningStatus != null && !warningStatus.equals("")) {
					sql += " and a.warningStatus like '"+warningStatus.trim()+"'";
				}
				if (salesContractNo != null && !salesContractNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
				}
				if (elevatorNo != null && !elevatorNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractDetail where elevatorNo like '%"+elevatorNo.trim()+"%')";
				}
				if (maintAddress != null && !maintAddress.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractDetail where maintAddress like '%"+maintAddress.trim()+"%')";
				}
				
				String order = " order by ";
				
				if("a.contractEdate".equals(table.getSortColumn())){
					order += "case when a.contractStatus = 'ZB' then 0 " +
						"when a.contractStatus = 'XB' then 0 else 1 END," +
						"case when a.contractEDate<='"+datestr+"' and a.contractEDate>='"+today+"' then 0 " +
						"else 1 end,a.TaskSubFlag desc,";
				}
				order += table.getSortColumn();
				if (table.getIsAscending()) {
					sql += order;
				} else {
					sql += order + " desc";
				}
				
				//System.out.println(">>>"+sql);
				
				query = hs.createSQLQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;
                
				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List maintContractList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					
					Map master=new HashMap();
					master.put("billNo",String.valueOf(objs[0]));
					master.put("maintContractNo",String.valueOf(objs[1]));
					master.put("contractSdate",String.valueOf(objs[2]));
					master.put("contractEdate",String.valueOf(objs[3]));
					master.put("warningStatus",String.valueOf(objs[4]));
					master.put("submitType",String.valueOf(objs[5]));
					master.put("auditStatus",String.valueOf(objs[6]));
					master.put("taskSubFlag",String.valueOf(objs[7]));
					 
					master.put("attn", String.valueOf(objs[8]));
					master.put("contractStatus", String.valueOf(objs[9]));
					master.put("maintDivision", String.valueOf(objs[10]));
					master.put("companyId", String.valueOf(objs[11]));
					master.put("maintStation", String.valueOf(objs[12]));
					master.put("connum", String.valueOf(objs[13]));
					master.put("r2", String.valueOf(objs[14]));

//					String sqlmas="select maint_billNo,r1 from EntrustContractMaster where Maint_BillNo='"+String.valueOf(objs[0])+"' order by BillNo desc";
//					List ecmlist=hs.createSQLQuery(sqlmas).list();
//					if(ecmlist!=null && ecmlist.size()>0){
//						Object[] obj=(Object[])ecmlist.get(0);
//						String r1=obj[1].toString();
//						if(r1.equals("END")){
//							master.put("r1", "��ֹ");
//						}else{
//							master.put("r1", "��");
//						}
//						
//					}else{
//						master.put("r1", "��");
//					}
					
					maintContractList.add(master);
				}

				table.addAll(maintContractList);
				session.setAttribute("maintContractList", table);
				// ��ͬ�����������б�
				request.setAttribute("contractNatureOfList", bd.getPullDownList("MaintContractMaster_ContractNatureOf"));
				// ��ͬ״̬�������б�
				request.setAttribute("contractStatusList", bd.getPullDownList("MaintContractMaster_ContractStatus"));
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
			forward = mapping.findForward("maintContractList");
		}
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
	public ActionForward toSearchNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����������� >> ��ѯ�б� ");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "maintContractQuoteNextList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fMaintContractNext");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("applyDate");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String billNo = tableForm.getProperty("billNo");// ������ˮ��
			String companyName = tableForm.getProperty("companyName");// �׷���λ����
			String status = tableForm.getProperty("status");// ����״̬
			String maintDivision = tableForm.getProperty("maintDivision");// �׷���λid
			String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
			String elevatorNo = tableForm.getProperty("elevatorNo");// ���ݱ��
						
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

				String sql = "select a,b.username,c.comname"+
						" from MaintContractQuoteMaster a,Loginuser b,Company c" + 
						" where a.attn = b.userid"+
						" and a.maintDivision = c.comid"+
						" and a.status = 1" +
						" and billNo not in (select isnull(quoteBillNo,'') from MaintContractMaster)";
				
				if (billNo != null && !billNo.equals("")) {
					sql += " and billNo like '%"+billNo.trim()+"%'";
				}
				if (companyName != null && !companyName.equals("")) {
					sql += " and companyName like '%"+companyName.trim()+"%'";
				}				
				if (status != null && !status.equals("")) {
					sql += " and status = '"+Integer.valueOf(status)+"'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and comid like '"+maintDivision.trim()+"'";
				}
				if (salesContractNo != null && !salesContractNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractQuoteDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
				}
				if (elevatorNo != null && !elevatorNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractQuoteDetail where elevatorNo like '%"+elevatorNo.trim()+"%')";
				}
				
				if (table.getIsAscending()) {
					sql += " order by "+ table.getSortColumn() +" asc";
				} else {
					sql += " order by "+ table.getSortColumn() +" desc";
				}

				//System.out.println(sql);
				
				query = hs.createQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List maintContractQuoteList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map map = BeanUtils.describe(objs[0]); // MaintContractQuoteMaster
					map.put("attnName", String.valueOf(objs[1])); // ����������
					map.put("maintDivisionName", String.valueOf(objs[2])); // �ֲ�����
					maintContractQuoteList.add(map);
				}

				table.addAll(maintContractQuoteList);
				session.setAttribute("maintContractQuoteNextList", table);
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);
				// ����״̬�������б�
				request.setAttribute("processStatusList", bd.getProcessStatusList());

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
			forward = mapping.findForward("maintContractNextList");
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
		
		request.setAttribute("navigator.location","ά����ͬ���� >> �鿴");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		display(form, request, errors, "display");
		
		String hfflag = request.getParameter("HFflag");
		
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
				String upsql="update MaintContractMaster set workisdisplay='"+workisdisplay+"' where billno='"+id+"'";
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
		
		String typejsp= request.getParameter("typejsp");
		if(typejsp!=null){
			request.setAttribute("typejsp", typejsp);
		}
		if(hfflag!=null&&"Y".equals(hfflag)){
			
			forward = mapping.findForward("CustomerReturnDisplay");
		}else{
			
			forward = mapping.findForward("maintContractDisplay");
		}
		
		return forward;
	}
	
	/**
	 * ��ת���½�����
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

		request.setAttribute("navigator.location","ά����ͬ���� >> ���");
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		
		Session hs = null;
		Query query = null;
		try {
			hs = HibernateUtil.getSession();

			// ����Ϣ
			MaintContractMaster maintContractBean = new MaintContractMaster();
			maintContractBean.setAttn(userInfo.getUserName()); //������
			maintContractBean.setMaintDivision(userInfo.getComID()); //ά���ֲ�
			maintContractBean.setOperDate(CommonUtil.getNowTime());
			maintContractBean.setOtherFee(0.0);//��������
			maintContractBean.setContractTotal(0.0); //��ͬ�ܶ�

			String isSelectAdd = request.getParameter("isSelectAdd"); //�Ƿ��Ǵ�ά������ѡȡ¼���־
			request.setAttribute("isSelectAdd", isSelectAdd);
			String quoteBillNo = request.getParameter("billNo"); //ά��������ˮ��
			
			//�ҷ���λ��Ϣ
			List list1=hs.createQuery("from Customer where enabledFlag='Y' and cusNature='XJS'").list();
			if(list1!=null && list1.size()>0){
				Customer companyB=(Customer) list1.get(0);
				if(companyB!=null)
					request.setAttribute("companyB", companyB);
			}
			
			// ѡ���½�
			if(isSelectAdd != null && isSelectAdd.equals("Y") && quoteBillNo != null && !quoteBillNo.equals("")){
				
				MaintContractQuoteMaster quoteMaster = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, request.getParameter("billNo"));
				
				
				maintContractBean.setContractTotal(quoteMaster.getFinallyQuoteTotal()); //��ͬ�ܶ�
				maintContractBean.setMainMode("PAID"); //������ʽ �շ�
				maintContractBean.setQuoteBillNo(quoteMaster.getBillNo());// ������ˮ��
				maintContractBean.setMaintDivision(quoteMaster.getMaintDivision());// ����ά���ֲ���
				maintContractBean.setMaintStation(quoteMaster.getMaintStation());// ����ά��վ
				maintContractBean.setOtherFee(quoteMaster.getBusinessCosts());//��������
				maintContractBean.setQuoteSignWay(quoteMaster.getQuoteSignWay()); //����ǩ��ʽ
				
				if(quoteMaster.getHistoryBillNo()!=null && !"".equals(quoteMaster.getHistoryBillNo())){
					maintContractBean.setHistoryBillNo(quoteMaster.getHistoryBillNo());
					maintContractBean.setHistContractNo(quoteMaster.getHistContractNo());
					maintContractBean.setHistContractStatus(quoteMaster.getHistContractStatus());
					maintContractBean.setXqType(quoteMaster.getXqType());
				}
				
				String pmastr=quoteMaster.getPaymentMethodApply();
				if(pmastr!=null && pmastr.trim().contains("99")){//����
					maintContractBean.setPaymentMethodRem(quoteMaster.getPaymentMethodRem());//���ʽ��ע
				}
				String ccastrb=quoteMaster.getContractContentApply();
				if(ccastrb!=null && ccastrb.contains("99")){//����
					maintContractBean.setContractContentRem(quoteMaster.getContractContentRem());//��ͬ�������������ע
				}
				
				List pdlist=bd.getPullDownAllList("MaintContractQuoteMaster_PaymentMethodApply");
				String pmaname=bd.getOptionName(pmastr, pdlist);
				maintContractBean.setR4(pmaname);
				maintContractBean.setPaymentMethod(pmastr);//���ʽ
				//��ͬ������������
				String ccastrname="";
				String ccastr=quoteMaster.getContractContentApply();
				String newsignway = "";
				if(ccastr!=null && !ccastr.trim().equals("")){
					List ccalist=bd.getPullDownAllList("MaintContractQuoteMaster_ContractContentApply");
					String[] ccarr=ccastr.split(",");
					for(int i=0;i<ccarr.length;i++){
						if(i==ccarr.length-1){
							ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist);
						}else{
							ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist)+"��";
						}
						
						if("100".equals(ccarr[i].trim())){
							newsignway = "HF";
						}
					}
				}
				maintContractBean.setR5(ccastrname);
				maintContractBean.setContractTerms(ccastr);//���Ӻ�ͬ����
				
				// ά��վ����
				request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", quoteMaster.getMaintStation()));
				// ά���ֲ�����
				request.setAttribute("maintDivisionName", bd.getName(hs, "Company", "comname", "comid", quoteMaster.getMaintDivision()));
				
				// �׷���λ��Ϣ
				String sqlcom="from Customer where companyName='"+quoteMaster.getCompanyName()+"'";
				Customer companyA = new Customer();
				List cuslist=hs.createQuery(sqlcom).list();
				if(cuslist!=null && cuslist.size()>0){
					companyA=(Customer)cuslist.get(0);
					maintContractBean.setCompanyId(companyA.getCompanyId()); //�׷���λ����
				}
				request.setAttribute("companyA",companyA);
				
				// �������������Ϣ��ϸ�б�
				String hql = "Select a from MaintContractQuoteDetail a " +
						" where a.billNo like '"+request.getParameter("billNo")+"'";
				//System.out.println(">>>>>"+hql);
				List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// �������������б�
				List elevatorSalesInfoList = hs.createQuery(hql).list();
				List maintContractDetailList = new ArrayList();
				MaintContractQuoteDetail mcqd = null;
				MaintContractDetail deatail = null;
				
				for (int i=0;i<elevatorSalesInfoList.size();i++) {
					deatail = new MaintContractDetail();
					mcqd = (MaintContractQuoteDetail) elevatorSalesInfoList.get(i);
					BeanUtils.copyProperties(deatail, mcqd); //������������ֵ
					deatail.setElevatorParam(mcqd.getElevatorParam());
					deatail.setElevatorType(bd.getOptionName(deatail.getElevatorType()+"", elevatorTypeList));
					//deatail.setMaintAddress(mcqd.getProjectAddress());
					deatail.setMaintAddress(mcqd.getProjectName());
					
					String signway=mcqd.getSignWay();
					
					//������ί�����Ᵽǩ��ʽ��Ϊ�ָ�
					if (!"".equals(newsignway)) {
						deatail.setSignWay(newsignway);
						deatail.setR1(bd.getName("Pulldown", "pullname", "id.typeflag = 'MaintContractDetail_SignWay' and a.id.pullid", newsignway));
					}else {
						//�����۵�ǩ��ʽΪ��ʱ���ͱ�ʾ����ʷ��¼��
						if(signway==null || signway.trim().equals("")){
							SearchElevatorSaleAction sesa=new SearchElevatorSaleAction();
							HashMap remap=sesa.getElevatorSignWay(mcqd.getElevatorNo());
							deatail.setSignWay((String)remap.get("signWay"));
							deatail.setR1((String)remap.get("signWayName"));//ǩ��ʽ
						}else{
							deatail.setSignWay(signway);
							deatail.setR1(bd.getName("Pulldown", "pullname", "id.typeflag = 'MaintContractDetail_SignWay' and a.id.pullid", signway));
						}
					}
					
					maintContractDetailList.add(deatail);
				}					
				request.setAttribute("maintContractDetailList", maintContractDetailList);		

			}
			
			dform.set("isSelectAdd",isSelectAdd);
			request.setAttribute("maintContractBean", maintContractBean);	
									
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
						
		// ǩ��ʽ�������б�
		request.setAttribute("signWayList", bd.getPullDownList("MaintContractDetail_SignWay"));
		// ������ϸ�������б�
		request.setAttribute("elevatorNatureList", bd.getPullDownList("MaintContractDetail_ElevatorNature"));
		// ά��վ�����б�
		//request.setAttribute("maintStationList", bd.getMaintStationList(userInfo));
		// ά��վ�����б�
		request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));
		// �ֲ��������б�
		request.setAttribute("maintDivisionList", Grcnamelist1.getgrcnamelist("admin"));
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		return mapping.findForward("maintContractAdd");
	}
	
	/**
	 * ����½��ķ���
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

		//��ֹ���ظ��ύ
		if(isTokenValid(request, true)){
			addOrUpdate(form,request,errors);// �������޸ļ�¼
		}else{
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		}

		String isreturn = request.getParameter("isreturn");
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
				forward = mapping.findForward("returnList");
			} else {
				
				// return addnew page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				
				String isSelectAdd=request.getParameter("isSelectAdd");
				if("Y".equals(isSelectAdd)){
					forward = mapping.findForward("returnList");
				}else{
					forward = mapping.findForward("returnAdd");
				}
				
				
				
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
		
		request.setAttribute("navigator.location","ά����ͬ���� >> �޸�");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		display(form, request, errors, "");
		
		request.setAttribute("isupdate", "Y");
		request.setAttribute("maintDivisionName", userInfo.getComName()); // ά���ֲ�����
		request.setAttribute("signWayList", bd.getPullDownList("MaintContractDetail_SignWay"));// ǩ��ʽ�������б�	
		request.setAttribute("elevatorNatureList", bd.getPullDownList("MaintContractDetail_ElevatorNature"));// ǩ��ʽ�������б�
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
			
		forward = mapping.findForward("maintContractModify");
		
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
		
		addOrUpdate(form,request,errors);// �������޸ļ�¼
		
		String isreturn = request.getParameter("isreturn");		
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
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
	 * ��ת������ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPrepareRenewalRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ���� >> ��ǩ");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		

		display(form, request, errors, "renewal"); 

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		request.setAttribute("elevatorNatureList", bd.getPullDownList("MaintContractDetail_ElevatorNature"));// ǩ��ʽ�������б�
		request.setAttribute("doType", "renewal");
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		forward = mapping.findForward("maintContractRenewal");	
		return forward;
	}
	
	/**
	 * ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toRenewalRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		//��ֹ���ظ��ύ
		if(!isTokenValid(request, true)){
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		} else {					
		
			String id = request.getParameter("id"); 
			
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				Connection conn = null;
				PreparedStatement ps = null;
				
				MaintContractMaster master = null;				
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();
					
					master = (MaintContractMaster) hs.get(MaintContractMaster.class, id);
					
					//������ͬ
					MaintContractMaster newContract = (MaintContractMaster) BeanUtils.cloneBean(master);
					newContract.setCompanyId((String)dform.get("companyId"));
	
					String todayDate = CommonUtil.getToday();
					String year = todayDate.substring(2,4);
					String billNo = CommonUtil.getBillno(year,"MaintContractMaster", 1)[0];// ������ˮ��
					dform.set("id", billNo);
					
					//�µĺ�ͬ��
					String MaintContractNo=this.genMaintContractNo("PAID", master.getMaintDivision(), "XB");
					
					newContract.setBillNo(billNo);
					newContract.setMaintContractNo(MaintContractNo);
					newContract.setMainMode("PAID");
					newContract.setContractStatus("XB");//��Ϊ����
					newContract.setContractSdate((String)dform.get("contractSdate"));
					newContract.setContractEdate((String)dform.get("contractEdate"));
					newContract.setPaymentMethod((String)dform.get("paymentMethod"));
					newContract.setContractTerms((String)dform.get("contractTerms"));
					newContract.setContractPeriod((String)dform.get("contractPeriod"));//��ͬ��Ч�ڣ��£�
					newContract.setAuditStatus("N");
					newContract.setAuditOperid(null);
					newContract.setAuditDate(null);
					newContract.setAuditRem("");
					newContract.setTaskRem("");
					newContract.setTaskSubDate(null);
					newContract.setTaskSubFlag("N");
					newContract.setTaskUserId(null);
					newContract.setWarningStatus("");//����״̬
					newContract.setSurrenderDate("");
					newContract.setSurrenderRem("");
					newContract.setSurrenderUser("");
					newContract.setWorkisdisplay("");
					newContract.setWorkisdisplay2("");
					newContract.setHistoryBillNo(master.getBillNo());//��һ�ݺ�ͬ��ˮ��
					newContract.setHistContractNo(master.getMaintContractNo());//��һ��ά����ͬ��
					newContract.setHistContractStatus(master.getContractStatus());//��һ�ݺ�ͬ״̬
					newContract.setRem((String)dform.get("rem"));//��һ�ݺ�ͬ״̬
					newContract.setOtherFee(0d);
					newContract.setOperId(userInfo.getUserID());
					newContract.setOperDate(CommonUtil.getNowTime());
					
					newContract.setModifyId(null);//�޸���
					newContract.setModifyDate(null);//�޸�����
					newContract.setOldContractTotal(0d);//�ɺ�ͬ�ܶ�
					newContract.setOldOtherFee(0d);//����������
					newContract.setQuoteSignWay(null);//����ǩ��ʽ
					newContract.setXqType("ALL");//��ǩ���� PART��������ǩ,ALL��ȫ����ǩ
					newContract.setCustomizeRem(null);//���Ʊ�ע
					
					hs.save(newContract);
					hs.flush();
					
					// ��ͬ��ϸ
					String[] elevatorNos = request.getParameterValues("elevatorNo");
					String[] mainSdates = request.getParameterValues("mainSdate");
					String[] mainEdates = request.getParameterValues("mainEdate");
					String[] signWay=request.getParameterValues("signWay");
					String[] maintaddress=request.getParameterValues("maintAddress");
					String[] elevatorNature=request.getParameterValues("elevatorNature");
					
					String[] colNames = {
							"billNo",
							"mainSdate",
							"mainEdate",
							"signWay",
							"realityEdate",
							"assignedMainStation",
							"assignedSignFlag",
							"assignedSign",
							"assignedSignDate",
							"returnReason",
							"maintPersonnel",
							"maintAddress",
							"elevatorNature",
							"shippedDate"
							
					};
					
					Set<String> colNameSet = SqlBeanUtil.getColumnNamesSet(MaintContractDetail.class);
					colNameSet.remove("rowid");
					for (String str : colNames) {
						colNameSet.remove(str);
					}

					String colNames2 = StringUtils.join(colNameSet.iterator(), ",");
					
					String sql = "insert MaintContractDetail(billNo,shippedDate,mainSdate,mainEdate,signWay,maintaddress,elevatorNature,"+colNames2+")" +
							" select '"+billNo+"',?,?,?,?,?,?,"+colNames2+" from MaintContractDetail" +
							" where elevatorNo=? and billNo='"+master.getBillNo()+"'" ;
									
					conn = hs.connection();
					ps = conn.prepareStatement(sql);
	
					for(int i = 0; i < elevatorNos.length; i++){
						ps.setString(1, mainSdates[i]);
						ps.setString(2, mainSdates[i]);
						ps.setString(3, mainEdates[i]);
						ps.setString(4, signWay[i]);
						ps.setString(5, maintaddress[i]);
						ps.setString(6, elevatorNature[i]);
						ps.setString(7, elevatorNos[i]);
						ps.addBatch();
					}
					
					ps.executeBatch();
	
					//��ʷ��ͬ
					//master.setContractStatus("LS");//��ͬ״̬��Ϊ��ʷ
					//hs.save(master);
					
					tx.commit();
					
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","�����ɹ�! �º�ͬ�ţ�"+MaintContractNo)); //��ʾ�������ɹ�����
					
				} catch (Exception e) {				
					e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.renewal.failed")); //��ʾ������ʧ��!��
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
				
		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnDisplay");

	}
	
	/**
	 * ��ת������ҳ��2
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPrepareRenewalRecord2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά������������� >> ���");	
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String roleid=userInfo.getRoleID();
		String roleidshow="N";
		
		String id = request.getParameter("id");//(String) dform.get("id");
		String xqtype = request.getParameter("xqtype");
		if(xqtype==null || xqtype.equals("")){
			xqtype="ALL";
		}
		Session hs = null;
		List maintContractQuoteDetailList = new ArrayList();
		if (id != null && !id.equals("")) {
			try {
				hs = HibernateUtil.getSession();

				//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ��ά��վ��Ա A48
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					roleidshow="Y";
				}
				request.setAttribute("roleidshow", roleidshow);
				
				Query query = hs.createQuery("from MaintContractMaster where billNo = :billNo");
				query.setString("billNo", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					
					//����Ϣ
					MaintContractMaster master = (MaintContractMaster) list.get(0);
					request.setAttribute("attnName", userInfo.getUserName()); //����������
					request.setAttribute("maintDivisionName",bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision())); //ά���ֲ�����
					request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation()));
					
					MaintContractQuoteMaster qMaster=new MaintContractQuoteMaster();
					qMaster.setAttn(userInfo.getUserID());
					//qMaster.setCompanyId(master.getCompanyId());
					qMaster.setMaintDivision(master.getMaintDivision());
					qMaster.setMaintStation(master.getMaintStation());
					qMaster.setApplyDate(CommonUtil.getNowTime()); //��������
					qMaster.setHistContractNo(master.getMaintContractNo());
					qMaster.setHistoryBillNo(master.getBillNo());
					qMaster.setHistContractStatus(master.getContractStatus());
					qMaster.setPaymentMethodApply(master.getPaymentMethod());
					qMaster.setPaymentMethodRem(master.getPaymentMethodRem());
					qMaster.setContractContentApply(master.getContractTerms());
					qMaster.setContractContentRem(master.getContractContentRem());
					if("PART".equals(xqtype)){
						//������ǩ
						qMaster.setQuoteSignWay("BFXB"); //����ǩ��ʽ ��ǩ
					}else{
						//ȫ����ǩ
						qMaster.setQuoteSignWay("XB"); //����ǩ��ʽ ��ǩ
					}
					
					qMaster.setXqType(xqtype);//ȫ����ǩ
					
					// �׷���λ��Ϣ
					Customer companyA = (Customer)hs.get(Customer.class, master.getCompanyId());
					if(companyA!=null){
						qMaster.setCompanyName(companyA.getCompanyName());
						qMaster.setContacts(companyA.getContacts());
						qMaster.setContactPhone(companyA.getContactPhone());
					}	
					
					request.setAttribute("maintContractQuoteBean", qMaster);
					
					// ������Ϣ��ϸ�б�
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�

					//���ݲ�����ʷ�ģ������˱���
					query = hs.createQuery("from MaintContractDetail where billNo = '"+id+"' "
							+ "and isnull(isSurrender,'N')='N' and isnull(elevatorStatus,'')=''");
					List detailList = query.list();
					for (Object object : detailList) {
						MaintContractDetail detail = (MaintContractDetail)object;
						String elevatorno=detail.getElevatorNo();
						
						MaintContractQuoteDetail qDetail=new MaintContractQuoteDetail();
						qDetail.setElevatorNo(elevatorno);
						qDetail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
						
						String elesql="from ElevatorSalesInfo where elevatorNo='"+elevatorno+"'";
						List elelist=hs.createQuery(elesql).list();
						if(elelist!=null && elelist.size()>0){
							ElevatorSalesInfo esinfo=(ElevatorSalesInfo)elelist.get(0);
							qDetail.setFloor(esinfo.getFloor());
							qDetail.setStage(esinfo.getStage());
							qDetail.setDoor(esinfo.getDoor());
							qDetail.setHigh(esinfo.getHigh());
							qDetail.setWeight(esinfo.getWeight());
							qDetail.setSpeed(esinfo.getSpeed());
							qDetail.setElevatorParam(esinfo.getElevatorParam());//����ͺ�
							qDetail.setSalesContractNo(esinfo.getSalesContractNo());//���ۺ�ͬ��
							qDetail.setProjectName(esinfo.getSalesContractName());//��ͬ����
							qDetail.setProjectAddress(esinfo.getDeliveryAddress());//������ַ
						}else{
							qDetail.setFloor(detail.getFloor());
							qDetail.setStage(detail.getStage());
							qDetail.setDoor(detail.getDoor());
							qDetail.setHigh(detail.getHigh());
							qDetail.setWeight("");
							qDetail.setSpeed("");
							qDetail.setElevatorParam(detail.getElevatorParam());
							qDetail.setSalesContractNo(detail.getSalesContractNo());
							qDetail.setProjectName(detail.getProjectName());
							qDetail.setProjectAddress(detail.getMaintAddress());
						}
						
						SearchElevatorSaleAction sesa=new SearchElevatorSaleAction();
						HashMap remap=sesa.getElevatorSignWay(elevatorno);
						qDetail.setSignWay((String)remap.get("signWay"));
						qDetail.setR4((String)remap.get("signWayName"));//ǩ��ʽ
						
						maintContractQuoteDetailList.add(qDetail);
					}
										
					request.setAttribute("maintContractQuoteDetailList", maintContractQuoteDetailList);
						
					// ����ά��վ�б�	
//					request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));
				} else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				request.setAttribute("PaymentMethodList", bd.getPullDownList("MaintContractQuoteMaster_PaymentMethodApply"));
				request.setAttribute("ContractContentList", bd.getPullDownList("MaintContractQuoteMaster_ContractContentApply"));
			
				//��ȡ�����׼���۵����ϵ��
				request.setAttribute("returnhmap", bd.getCoefficientMap());
				
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
			
			if("PART".equals(xqtype)){
				//������ǩ
				//forward = mapping.findForward("maintContractRenewal3");
				request.setAttribute("navigator.location","ά����ͬ���� >> ������ǩ ");	
			}else{
				//ȫ����ǩ
				//forward = mapping.findForward("maintContractRenewal2");
				request.setAttribute("navigator.location","ά����ͬ���� >> ��ǩ ");	
			}
			
			forward = mapping.findForward("maintContractRenewal2");
			
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		return forward;
	}
	
	/**
	 * ��������2
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toRenewalRecord2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		/*//��ֹ���ظ��ύ
		if(!isTokenValid(request, true)){
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		} else {*/					
		
			String id = request.getParameter("id");
			String isreturn=(String) dform.get("isreturn");
			String xqType=(String) dform.get("xqType");
			
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				JbpmExtBridge jbpmExtBridge=null;
				String userid = userInfo.getUserID(); //��ǰ��¼�û�id
				
				MaintContractMaster master = null;			
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();
					
					MaintContractQuoteMaster qMaster = new MaintContractQuoteMaster();
					String todayDate = CommonUtil.getToday();
					String year = todayDate.substring(2,4);
					String billNo = CommonUtil.getBillno(year,"MaintContractQuoteMaster", 1)[0];// ������ˮ��	
					
					String ccastr="";
					String[] ccarr=request.getParameterValues("contractContentApply");
					if(ccarr!=null && ccarr.length>0){
						ccastr=Arrays.toString(ccarr);
						ccastr=ccastr.replaceAll("\\[", "");
						ccastr=ccastr.replaceAll("\\]", "");
					}
					
					BeanUtils.copyProperties(qMaster, dform); // ������������ֵ
					qMaster.setBillNo(billNo);// ��ˮ��
					qMaster.setStatus(new Integer(WorkFlowConfig.State_NoStart));// ����״̬
					qMaster.setOperId(userid);// ¼����
					qMaster.setOperDate(CommonUtil.getNowTime());// ¼��ʱ��
					qMaster.setContractContentApply(ccastr);
					
					if(isreturn.equals("N")){
						qMaster.setSubmitType("N");// �ύ��־
					}else{
						String processDefineID = Grcnamelist1.getProcessDefineID("enginequotemaster", qMaster.getAttn());// ���̻���id
						if(processDefineID == null || processDefineID.equals("")){
							errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.string","<font color='red'>δ��������������Ϣ������������</font>"));
							throw new Exception();
						}
									
						/**=============== ����������ʵ����ʼ ===================**/
						HashMap paraMap = new HashMap();
						jbpmExtBridge=new JbpmExtBridge();
						ProcessBean pd = null;		
						pd = jbpmExtBridge.getPb();
		
						//Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ά���ֲ������", userid);// ��������
						//Grcnamelist1.setJbpmAuditopers_class(pd, processDefineID, PropertiesUtil.getProperty("MaintStationManagerJbpm"), userInfo.getComID(), qMaster.getMaintStation());// ��������
						Grcnamelist1.setJbpmAuditopers_roleid(pd,"Y",qMaster.getMaintDivision());
						
						pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,billNo,"",paraMap);
						/**==================== ���̽��� =======================**/
						
						qMaster.setSubmitType("Y");// �ύ��־
						qMaster.setProcessName(pd.getNodename());// ��������
						qMaster.setStatus(pd.getStatus()); //����״̬
						qMaster.setTokenId(pd.getToken());//��������
					}
					
					hs.save(qMaster);
					
					// ��ͬ��ϸ
					String details = (String) dform.get("maintContractDetails");
					List list = JSONUtil.jsonToList(details, "details");
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
					
					for (Object object : list) {
						MaintContractQuoteDetail detail = new MaintContractQuoteDetail();					
						BeanUtils.copyProperties(detail, object);
						detail.setBillNo(billNo);
						//detail.setElevatorAge((double) 0);
						detail.setNum(1);
						detail.setElevatorType(bd.getOptionId(detail.getElevatorType(), elevatorTypeList));//�ѵ�����������ת��Ϊ����				
						hs.save(detail);				
					}
	
					// �����ļ�
					String path = "MaintContractQuoteMaster.file.upload.folder";
					String tableName = "MaintContractQuoteMaster";//���� ά�����������

					List fileInfoList = this.saveFile(dform,request,response, path, billNo);
					boolean istrue=this.saveFileInfo(hs,fileInfoList,tableName,billNo, userid);
					
					/**
					//�޸�ԭ��ͬ��״̬
					String historyBillNo=(String) dform.get("historyBillNo");
					historyBillNo=historyBillNo.replaceAll(",", "','");
					String sqlm="update MaintContractMaster set contractStatus='LS' where billno in('"+historyBillNo+"')";
					hs.connection().prepareStatement(sqlm).executeUpdate();
					*/
					tx.commit();
				} catch (Exception e) {				
					e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.renewal.failed")); //��ʾ������ʧ��!��
					if(tx != null){
						tx.rollback();
					}
					if (jbpmExtBridge != null) {
						jbpmExtBridge.setRollBack();
					}
				} finally {
					if(hs != null){
						hs.close();				
					}
					if(jbpmExtBridge!=null){
						jbpmExtBridge.close();
					}
				}
				
			} else {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
			}	
				
		/*}*/
		
		if (errors.isEmpty()){				
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.renewal.succeed")); //��ʾ�������ɹ�����
		}		
		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnList");

	}

	/**
	 * ��ת���´�ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPrepareAssignTasks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ���� >> �´�");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		display(form, request, errors, "assign");

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		request.setAttribute("doType", "assign");//�´�
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		forward = mapping.findForward("maintContractAssignTasks");	
		return forward;
	}
	
	/**
	 * �´﷽��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */	
	public ActionForward toAssignTasks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	
		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		//��ֹ���ظ��ύ
		if(!isTokenValid(request, true)){
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		} else {					
		
			String id = request.getParameter("id"); 
			
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				Connection conn = null;
				PreparedStatement ps = null;
				
				MaintContractMaster master = null;		
				//MaintContractDetail detail = null;				
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();
					
					// ����Ϣ
					master = (MaintContractMaster) hs.get(MaintContractMaster.class, id);
					
					String auditStatus = master.getAuditStatus();// ��˱�־
					String contractStatus = master.getContractStatus();// ��ͬ״̬
					String taskSubFlag = master.getTaskSubFlag();// �����´�״̬
					
					if("Y".equals(auditStatus) && !"LS".equals(contractStatus) && !"Y".equals(taskSubFlag)){
						master.setTaskSubDate(CommonUtil.getNowTime());// �����´�����
						master.setTaskRem((String)dform.get("taskRem"));// �����´ﱸע
						master.setTaskSubFlag("Y");// �����´��־
						master.setTaskUserId(userInfo.getUserID());// �����´���
						hs.save(master);	

						// ��ͬ��ϸ
						String[] rowids = request.getParameterValues("rowid");// ���ݱ��
						String[] assignedMainStations = request.getParameterValues("assignedMainStation");// �´�ά��վ
						
						String sql = "update MaintContractDetail set assignedMainStation = ?," +
								" AssignedSignFlag=null,AssignedSign=null,AssignedSignDate=null,ReturnReason=null" +
								" from MaintContractDetail" +
								" where rowid=? and isnull(assignedSignFlag,'') <> 'Y'";
										
						conn = hs.connection();
						ps = conn.prepareStatement(sql);
		
						for(int i = 0; i < rowids.length; i++){	
							ps.setString(1, assignedMainStations[i]);
							ps.setString(2, rowids[i]);
							ps.addBatch();
						}
						
						ps.executeBatch();
					} else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>��¼�����´�!</font>"));
					}					
	
					tx.commit();
				} catch (Exception e) {				
					e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.assignTasks.failed")); //��ʾ���´�ʧ��!��
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
		
		if (errors.isEmpty()){			
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.assignTasks.succeed")); //��ʾ���´�ɹ�����
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return mapping.findForward("returnDisplay");
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
	 * �˱�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toSurrenderRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
	
		String id = request.getParameter("id"); 
		
		if(id != null && !id.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			MaintContractMaster master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (MaintContractMaster) hs.get(MaintContractMaster.class, id);
				master.setContractStatus("TB");
				master.setSurrenderUser(userInfo.getUserID());
				master.setSurrenderDate(CommonUtil.getNowTime());
				if(master!=null){
					List list=hs.createQuery("from MaintContractDetail where billNo='"+master.getBillNo()+"'").list();
					if(list!=null && list.size()>0){
						for(int i=0;i<list.size();i++){
							MaintContractDetail detail=(MaintContractDetail) list.get(i);
							detail.setRealityEdate(CommonUtil.getToday());
							hs.save(detail);
						}
					}
				}
				hs.save(master);
				
				tx.commit();
			} catch (Exception e) {				
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.surrender.failed")); //��ʾ���˱�ʧ��!��
			} finally {
				if(hs != null){
					hs.close();				
				}				
			}
			
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
		}	
		
		if (errors.isEmpty()){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.surrender.succeed")); //��ʾ���˱��ɹ�����
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
			MaintContractMaster master = (MaintContractMaster) hs.get(MaintContractMaster.class, id);
			if (master != null) {
				hs.createQuery("delete from MaintContractDetail where billno='"+id+"'").executeUpdate();
				
				String hcs=master.getHistContractStatus();
				String quoteBillNo=master.getQuoteBillNo();//������ˮ��
				if(quoteBillNo==null || quoteBillNo.trim().equals("")){
					if(hcs!=null && !hcs.trim().equals("")){
						MaintContractMaster master2 = (MaintContractMaster) hs.get(MaintContractMaster.class, master.getHistoryBillNo());
						master2.setContractStatus(hcs.trim());
						hs.save(master2);
					}
				}
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
	
	/**
	 * ��ת�����ڴ���ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPrepareExpireRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("navigator.location","ά����ͬ���� >> ���ڴ���");
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		
		String id=request.getParameter("id");
		Session hs = null;
		Query query = null;
		CustomerVisitPlanDetail cvpDetail = new CustomerVisitPlanDetail();
		CustomerVisitPlanMaster cvpMaster = new CustomerVisitPlanMaster();
		MaintContractMaster master=null;
		String maintDivisionName="";
		String maintStationName="";
		//String vuserid="";
		//String vusername="";
		
		try {
			hs = HibernateUtil.getSession();
			if(id!=null){
				master=(MaintContractMaster) hs.get(MaintContractMaster.class, id);
				if(master!=null){
					maintStationName=bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation());
					maintDivisionName=bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision());

					//��ȡά��վ����,��������רԱ
					String userstr="from Loginuser " +
							"where grcid='"+master.getMaintDivision()+"' " +
							"and ((storageid like '"+master.getMaintStation()+"' " +
							"and class1id='20') or class1id='100') and enabledflag='Y'";
					List uslist=hs.createQuery(userstr).list();
					if(uslist!=null && uslist.size()>0){
						request.setAttribute("userlist", uslist);
					}
					
					Customer companyA=(Customer) hs.get(Customer.class, master.getCompanyId());
					cvpMaster.setCompanyId(companyA.getCompanyId());
					cvpMaster.setCompanyName(companyA.getCompanyName());
					cvpMaster.setPrincipalName(companyA.getPrincipalName());
					cvpMaster.setPrincipalPhone(companyA.getPrincipalPhone());
					cvpMaster.setCustLevel(companyA.getCustLevel());
					cvpMaster.setMaintDivision(master.getMaintDivision());
					cvpMaster.setMaintStation(master.getMaintStation());
					cvpMaster.setMaintContractNo(master.getMaintContractNo());
					//cvpDetail.setVisitStaff(vuserid);
					cvpDetail.setVisitPosition("20");
					cvpDetail.setVisitDate(CommonUtil.getToday());
					cvpDetail.setRem("ά����ͬ�ţ�"+master.getMaintContractNo()+",��ͬ��ʼ���ڣ�"+master.getContractSdate()+",��ͬ�������ڣ�"+master.getContractEdate());
				}
				
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

		request.setAttribute("maintStationName", maintStationName);
		request.setAttribute("maintDivisionName", maintDivisionName);
		//request.setAttribute("vusername", vusername);
		request.setAttribute("cvpm", cvpMaster);
		request.setAttribute("customerVisitPlanDetailBean", cvpDetail);
		request.setAttribute("id", master.getBillNo());
		
		return mapping.findForward("maintContractExpire");
	}
	
	/**
	 * ���ڴ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public ActionForward toExpireRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	
		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		DynaActionForm dform = (DynaActionForm) form;
		Session hs = null;
		Transaction tx = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String id=(String)dform.get("id");//ά����ˮ��
		String custLevel=(String) dform.get("custLevel");//�ͻ��ȼ�
		String companyName=(String) dform.get("companyName");//��λ����
		String companyId=(String) dform.get("companyId");//��λ����
		String principalName=(String) dform.get("principalName");//������
		String principalPhone=(String) dform.get("principalPhone");//��ϵ�绰
		String maintDivision=(String) dform.get("maintDivision");//ά���ֲ�
		String maintStation=(String) dform.get("maintStation");//ά��վ
		String maintContractNo=(String)dform.get("maintContractNo");//ά����ͬ��
		String visitDate=(String) dform.get("visitDate");//�ݷ�����
		String visitPosition=(String) dform.get("visitPosition");//�ݷ���Աְλ
		String visitStaff=(String) dform.get("visitStaff");//�ݷ���Ա
		String rem=(String) dform.get("rem");//��ע
		
		try {
			hs=HibernateUtil.getSession();
			tx=hs.beginTransaction();
			
			MaintContractMaster mc=(MaintContractMaster) hs.get(MaintContractMaster.class, id);
			String warningstatus=mc.getWarningStatus();
			
			if(warningstatus==null || warningstatus.trim().equals("")){
				mc.setWarningStatus("Y");
				hs.save(mc);
				
				String todayDate = CommonUtil.getToday();
				String year1 = todayDate.substring(2,4);
				String billno=CommonUtil.getBillno(year1, "CustomerVisitPlanMaster", 1)[0];
				String jnlno=CommonUtil.getBillno(year1, "CustomerVisitPlanDetail", 1)[0];
				//System.out.println(companyId);
				
				CustomerVisitPlanMaster master=new CustomerVisitPlanMaster();
				master.setBillno(billno);
				master.setCustLevel(custLevel);
				master.setCompanyId(companyId);
				master.setCompanyName(companyName);
				master.setPrincipalName(principalName);
				master.setPrincipalPhone(principalPhone);
				master.setMaintDivision(maintDivision);
				master.setMaintStation(maintStation);
				master.setMaintContractNo(maintContractNo);
				master.setOperDate(CommonUtil.getNowTime());
				master.setOperId(userInfo.getUserID());
				hs.save(master);
				
				CustomerVisitPlanDetail detail=new CustomerVisitPlanDetail();
				detail.setBillno(billno);
				detail.setJnlno(jnlno);
				detail.setVisitDate(visitDate);
				detail.setVisitPosition(visitPosition);//�ݷ���Աְλ
				detail.setVisitStaff(visitStaff);//�ݷ���Ա
				detail.setRem(rem);
				hs.save(detail);
				
				tx.commit();
			}else{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","�ѽ��ݷüƻ�������ʧ�ܣ�"));
			}
			
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

		String isreturn = request.getParameter("isreturn");
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
				forward = mapping.findForward("returnList");
			} else {
				
				// return addnew page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				
				forward = mapping.findForward("returnExpire");
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return forward;
	}
	
	public void display(ActionForm form, HttpServletRequest request ,ActionErrors errors ,String flag){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		String id=(String)dform.get("id");
		if(id==null || id.trim().equals("")){
			id = request.getParameter("id");
		}
		
		String maintcontractno = request.getParameter("maintcontractno");
		if(maintcontractno!=null && !maintcontractno.equals("")){
			//ת����������
			try {
				id=new String(maintcontractno.getBytes("ISO-8859-1"),"gbk");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		Session hs = null;
		Transaction tx = null;
		List maintContractDetailList = null;
	
		if (id != null && !id.equals("")) {			
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();//lijun add 20160430
				
				if(maintcontractno!=null && !maintcontractno.equals("")){
					String sqlkk="from MaintContractMaster where maintContractNo = '"+id.trim()+"'";
					List list = hs.createQuery(sqlkk).list();
					if (list != null && list.size() > 0) {
						MaintContractMaster master = (MaintContractMaster) list.get(0);	
						id=master.getBillNo();
					}
				}
				
				String sql="from MaintContractMaster where billNo = '"+id.trim()+"'";
				List list = hs.createQuery(sql).list();
				if (list != null && list.size() > 0) {

					// ����Ϣ
					MaintContractMaster master = (MaintContractMaster) list.get(0);															
					master.setAttn(bd.getName(hs, "Loginuser","username", "userid",master.getAttn()));// ������
					master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid",master.getAuditOperid()));//�����
					dform.set("id",id);
					// ά��վ����
					request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation()));
																
					if("assign".equals(flag)){// �´�		
						master.setTaskUserId(userInfo.getUserName());// ��������������
						master.setTaskSubDate(CommonUtil.getNowTime());// �����´�����
					}else{
						master.setTaskUserId(bd.getName(hs, "Loginuser","username", "userid",master.getTaskUserId()));// ��������������
					}
					//���ʽ
					String pmastr=master.getPaymentMethod();
					List pdlist=bd.getPullDownAllList("MaintContractQuoteMaster_PaymentMethodApply");
					String pmaname=bd.getOptionName(pmastr, pdlist);
					master.setR4(pmaname);
					//maintContractBean.setPaymentMethod(pmastr);//���ʽ
					//��ͬ�������������
					String ccastrname="";
					String ccastr=master.getContractTerms();
					if(ccastr!=null && !ccastr.trim().equals("")){
						List ccalist=bd.getPullDownAllList("MaintContractQuoteMaster_ContractContentApply");
						if(ccastr!=null && !ccastr.trim().equals("")){
							String[] ccarr=ccastr.split(",");
							for(int i=0;i<ccarr.length;i++){
								if(i==ccarr.length-1){
									ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist);
								}else{
									ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist)+"��";
								}
							}
						}
					}
					master.setR5(ccastrname);
					//master.setContractTerms(ccastr);//���Ӻ�ͬ����
					
					// �׷���λ��Ϣ
					Customer companyA = (Customer) hs.get(Customer.class,master.getCompanyId());															
					// �ҷ�����λ��Ϣ
					Customer companyB = (Customer) hs.get(Customer.class,master.getCompanyId2());
										
					// ��ϸ�б�
					String sqldeta = "from MaintContractDetail where billNo = '"+id+"'";
					if("renewal".equals(flag)){
						sqldeta = "from MaintContractDetail where billNo = '"+id+"' and isnull(isSurrender,'N')='N'";
						master.setOtherFee(0d);
					}
					
					maintContractDetailList = hs.createQuery(sqldeta).list();
					List signWayList = bd.getPullDownList("MaintContractDetail_SignWay");// ǩ��ʽ�����б�
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// �������������б�
					List elevatorNatureList = bd.getPullDownList("MaintContractDetail_ElevatorNature ");// �������������б�
					for (Object object : maintContractDetailList) {
						MaintContractDetail detail = (MaintContractDetail) object;
						if("display".equals(flag) || "assign".equals(flag)){
							detail.setSignWay(bd.getName("Pulldown", "pullname", "id.typeflag = 'MaintContractDetail_SignWay' and a.id.pullid", detail.getSignWay()));
							detail.setElevatorNature(bd.getOptionName(detail.getElevatorNature(), elevatorNatureList));
						}else if("renewal".equals(flag)){
							
							SearchElevatorSaleAction sesa=new SearchElevatorSaleAction();
							HashMap remap=sesa.getElevatorSignWay(detail.getElevatorNo());
							detail.setSignWay((String)remap.get("signWay"));
							detail.setR1((String)remap.get("signWayName"));//ǩ��ʽ
							
						}else{
							detail.setR1(bd.getName("Pulldown", "pullname", "id.typeflag = 'MaintContractDetail_SignWay' and a.id.pullid", detail.getSignWay()));
						}
						detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
						
						/*if(!"".equals(flag)){
							detail.setSignWay(bd.getOptionName(detail.getSignWay(), signWayList));
						}else{
							detail.setR1(bd.getName("Pulldown", "pullname", "id.pullid", detail.getSignWay()));							
						}*/
					}
					
					request.setAttribute("maintContractBean", master);	
					request.setAttribute("maintDivisionName", bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));
					request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation()));
					request.setAttribute("companyA",companyA);
					request.setAttribute("companyB",companyB);
					request.setAttribute("maintContractDetailList", maintContractDetailList);
					
					// ά��վ�����б�
					//request.setAttribute("maintStationList", bd.getMaintStationList(userInfo));
					// ά��վ�����б�
					request.setAttribute("maintStationList", bd.getMaintStationList(master.getMaintDivision()));
					// �ֲ��������б�
					request.setAttribute("maintDivisionList", Grcnamelist1.getgrcnamelist("admin"));
							
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(tx!=null){
					tx.rollback();//lijun add 20160430
				}
				try {
					hs.close();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			
		}		
		
	}
	


	/**
	 * �������ݷ���
	 * @param form
	 * @param request
	 * @return ActionErrors
	 */
	public void addOrUpdate(ActionForm form, HttpServletRequest request,ActionErrors errors) {
		
		DynaActionForm dform = (DynaActionForm) form;

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		MaintContractMaster master = null;
		MaintContractDetail detail = null;
		String id = (String) dform.get("id");
		String mainMode = (String)dform.get("mainMode");
		String billNo = null;

		Session hs = null;
		Transaction tx = null;
			
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			boolean flag = false;// �Ƿ�����ά����ͬ�ű�־
			
			if (id != null && !id.equals("")) { // �޸�		
				hs.createQuery("delete from MaintContractDetail where billNo='"+id+"'").executeUpdate();		
				master = (MaintContractMaster) hs.get(MaintContractMaster.class, id);
				billNo = master.getBillNo();
				flag = !master.getMainMode().equals(dform.get("mainMode"));
				
			} else { // ����
				master = new MaintContractMaster();	
				
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				billNo = CommonUtil.getBillno(year,"MaintContractMaster", 1)[0];// ������ˮ��		
				
				flag = true;
			}
				
			BeanUtils.populate(master, dform.getMap()); // ������������ֵ
			
			master.setBillNo(billNo);// ��ˮ��
			master.setAttn(userInfo.getUserID());// ������id
			//master.setMaintDivision(userInfo.getComID()); //ά���ֲ�id						
			master.setOperId(userInfo.getUserID());// ¼����
			master.setOperDate(CommonUtil.getNowTime());// ¼��ʱ��
			master.setSubmitType("N");// �ύ��־
			master.setAuditStatus("N");// ���״̬
			master.setTaskSubFlag("N");// �´�״̬
			if(master.getHistoryBillNo()!=null && !"".equals(master.getHistoryBillNo())){
				master.setContractStatus("XB");
			}else{
				master.setContractStatus("ZB");// ��ͬ״̬ ��ǩ
			}
			master.setContractNatureOf("ZB");// ��ͬ���� �Ա�
						
			if(flag){ // ����ά����ͬ��
				//�շ�ά����ͬ�Ź���2017-0001�����ٰ��ֲ������֣�   
				//���ά����ͬ�Ź���2017MB-0001�� ������ͬ�о�û�б�Ҫ��XB�� 
				if(master.getHistoryBillNo()!=null && !"".equals(master.getHistoryBillNo())){
					master.setMaintContractNo(genMaintContractNo("PAID", master.getMaintDivision(), "XB"));
				}else{
					master.setMaintContractNo(genMaintContractNo(master.getMainMode(), master.getMaintDivision(), ""));
				}
			}
			
			hs.save(master);

			// ��ͬ��ϸ
			String details = (String) dform.get("maintContractDetails");
			List list = JSONUtil.jsonToList(details, "details");
			List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
			String[] maintAddress=request.getParameterValues("projectAddress");
			String[] elevatorNature=request.getParameterValues("elevatorNature");
			String[] r4=request.getParameterValues("r4");
			String[] r5=request.getParameterValues("r5");
			for (int i=0;i<list.size();i++) {
				detail = new MaintContractDetail();
				BeanUtils.populate(detail, (Map)list.get(i));
				detail.setBillNo(billNo);
				detail.setElevatorNature(elevatorNature[i]);
				detail.setMaintAddress(maintAddress[i]);
				detail.setElevatorType(bd.getOptionId(detail.getElevatorType(), elevatorTypeList));//�ѵ�����������ת��Ϊ����	
				if(mainMode!=null && mainMode.equals("FREE")){
					detail.setSignWay("XQ");
				}
				detail.setShippedDate(detail.getMainSdate());//ά����ʼ����
				detail.setR4(r4[i]);
				detail.setR5(r5[i]);
				hs.save(detail);				
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
				if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
					refer(form,request,errors,billNo); //�ύ
				}
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}
		
	}
		
	public void refer(ActionForm form, HttpServletRequest request,ActionErrors errors, String id){

		if(id != null && !id.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			MaintContractMaster master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (MaintContractMaster) hs.get(MaintContractMaster.class, id);
				master.setSubmitType("Y");
				hs.save(master);
				
				tx.commit();
			} catch (Exception e) {				
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
			} finally {
				if(hs != null){
					hs.close();				
				}				
			}
			
		}		
	} 
	
	/**
	 * ����ά����ͬ��
	 * @param MainMode
	 * @param MaintDivision
	 * @return
	 */
	public String genMaintContractNo(String mainMode, String MaintDivision, String contractStatus){
		
		String maintContractNo = null;
		String prefix = "";
		String suffix = "";
		String year = CommonUtil.getNowTime("yyyy");
		
		//�շ�ά����ͬ�Ź���2017-0001�����ٰ��ֲ������֣�   
		//���ά����ͬ�Ź���2017MB-0001�� ������ͬ�о�û�б�Ҫ��XB�� 
		if("PAID".equals(mainMode)){// �Ա� �շ�
			//prefix = "XJWB"+"-"+MaintDivision+contractStatus+"-"+year.substring(2)+"-";
			prefix = year+"-";
		}else if("FREE".equals(mainMode)){// �Ա� ���
			//prefix = "XJWB"+"-"+MaintDivision+"MB-"+year.substring(2)+"-";
			prefix = year+"MB-";
		}

		maintContractNo = CommonUtil.genNo("MaintContractMaster", "maintContractNo", prefix, suffix, 4,"maintContractNo");
				
		return maintContractNo;		
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
	 * ajax ���� �ֲ���ά��վ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void toMaintContractDetailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session hs=null;
		String billnostr=request.getParameter("billnostr");

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs=HibernateUtil.getSession();
			
			MaintContractDetail mcd=null;
			ElevatorSalesInfo esinfo=null;
			
			if(billnostr!=null && !"".equals(billnostr)){
				String hql="from MaintContractDetail where billNo='"+billnostr+"' order by rowid";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						sb.append("<rows>");
						mcd=(MaintContractDetail)list.get(i);
						String elevatorno=mcd.getElevatorNo();
						
						SearchElevatorSaleAction sesa=new SearchElevatorSaleAction();
						HashMap remap=sesa.getElevatorSignWay(elevatorno);
						//ǩ��ʽ
						sb.append("<cols name='signWay' value='"+(String)remap.get("signWay")+"'>").append("</cols>");
						sb.append("<cols name='signWayName' value='"+(String)remap.get("signWayName")+"'>").append("</cols>");

						sb.append("<cols name='elevatorNo' value='"+elevatorno+"'>").append("</cols>");
						String etype=mcd.getElevatorType();
						if(etype!=null && etype.trim().equals("F")){
							etype="����";
						}else if(etype!=null && etype.trim().equals("T")){
							etype="ֱ��";
						}else{
							etype="";
						}
						sb.append("<cols name='elevatorType' value='"+etype+"'>").append("</cols>");
						
						String elesql="from ElevatorSalesInfo where elevatorNo='"+elevatorno+"'";
						List elelist=hs.createQuery(elesql).list();
						if(elelist!=null && elelist.size()>0){
							esinfo=(ElevatorSalesInfo)elelist.get(0);
							sb.append("<cols name='floor' value='"+esinfo.getFloor()+"'>").append("</cols>");
							sb.append("<cols name='stage' value='"+esinfo.getStage()+"'>").append("</cols>");
							sb.append("<cols name='door' value='"+esinfo.getDoor()+"'>").append("</cols>");
							sb.append("<cols name='high' value='"+esinfo.getHigh()+"'>").append("</cols>");
							sb.append("<cols name='weight' value='"+esinfo.getWeight()+"'>").append("</cols>");
							sb.append("<cols name='speed' value='"+esinfo.getSpeed()+"'>").append("</cols>");
							sb.append("<cols name='elevatorParam' value='"+esinfo.getElevatorParam()+"'>").append("</cols>");
							sb.append("<cols name='salesContractNo' value='"+esinfo.getSalesContractNo()+"'>").append("</cols>");
							sb.append("<cols name='projectName' value='"+esinfo.getSalesContractName()+"'>").append("</cols>");
							sb.append("<cols name='projectAddress' value='"+esinfo.getDeliveryAddress()+"'>").append("</cols>");
						}else{
							sb.append("<cols name='floor' value='"+mcd.getFloor()+"'>").append("</cols>");
							sb.append("<cols name='stage' value='"+mcd.getStage()+"'>").append("</cols>");
							sb.append("<cols name='door' value='"+mcd.getDoor()+"'>").append("</cols>");
							sb.append("<cols name='high' value='"+mcd.getHigh()+"'>").append("</cols>");
							sb.append("<cols name='weight' value=''>").append("</cols>");
							sb.append("<cols name='speed' value=''>").append("</cols>");
							sb.append("<cols name='elevatorParam' value='"+mcd.getElevatorParam()+"'>").append("</cols>");
							sb.append("<cols name='salesContractNo' value='"+mcd.getSalesContractNo()+"'>").append("</cols>");
							sb.append("<cols name='projectName' value='"+mcd.getProjectName()+"'>").append("</cols>");
							sb.append("<cols name='projectAddress' value='"+mcd.getMaintAddress()+"'>").append("</cols>");
						}
						sb.append("</rows>");
					}
				  }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			hs.close();
		}
		sb.append("</root>");

		response.setHeader("Content-Type","text/html; charset=GBK");
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
		String comid=request.getParameter("comid");
		response.setHeader("Content-Type","text/html; charset=GBK");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs=HibernateUtil.getSession();
			if(comid!=null && !"".equals(comid)){
//				String hql="select a from Storageid a where a.comid='"+comid+"' " +
//						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
//				List list=hs.createQuery(hql).list();
				List list=bd.getMaintStationList(comid);
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
	 * ���ά��������ڵķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayDateRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά����ͬ���� >> ά���������");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		display(form, request, errors, "display");
		
		request.setAttribute("display", "yes");
		request.setAttribute("doType", "aidate");//ά���������
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		forward = mapping.findForward("maintContractDisplay");
		return forward;
	}
	/**
	 * ����ά��������ڵķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toUpdateDateRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		DynaActionForm dform = (DynaActionForm) form;

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		MaintContractDetail detail = null;

		Session hs = null;
		Transaction tx = null;
			
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			String[] rowidarr=request.getParameterValues("rowid");
			String[] aiDatearr=request.getParameterValues("annualInspectionDate");
			for (int i=0;i<rowidarr.length;i++) {
				detail = (MaintContractDetail) hs.get(MaintContractDetail.class, Integer.parseInt(rowidarr[i]));
				detail.setAnnualInspectionDate(aiDatearr[i]);//�������
				detail.setAidateoperid(userInfo.getUserID());//�޸���
				detail.setAidateopertime(CommonUtil.getNowTime());//�޸�����
				hs.save(detail);				
			}
			
			tx.commit();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
		} catch (Exception e1) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
			try {
				tx.rollback();
			} catch (HibernateException e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				hex.printStackTrace();
			}
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		forward = mapping.findForward("returnList");
		return forward;
	}
	
}	