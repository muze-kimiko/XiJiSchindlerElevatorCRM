package com.gzunicorn.struts.action.contractpayment.contractparagraphmanage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.struts.util.MessageResources;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.basedata.invoicetype.InvoiceType;
import com.gzunicorn.hibernate.contractpayment.contractparagraphmanage.ContractParagraphManage;
import com.gzunicorn.hibernate.contractpayment.procontractarfeemaster.ProContractArfeeMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class ContractParagraphManageAction extends DispatchAction {

	Log log = LogFactory.getLog(ContractParagraphManageAction.class);
	
	BaseDataImpl bd = new BaseDataImpl();
	DecimalFormat df = new DecimalFormat("##.##"); 
	
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


		// Set default method is toSearchRecord
		String name = request.getParameter("method");
		String authority="contractparagraphmanage";
		if(name != null && name.contains("Audit")){
			authority = "contractparagraphaudit";
		}
		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + authority, null);
		/** **********�����û�Ȩ�޹���*********** */
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
		
		request.setAttribute("navigator.location","������� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();
		
		request.setAttribute("showroleid", userInfo.getRoleID());

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
			HTMLTableCache cache = new HTMLTableCache(session, "contractParagraphManageList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fContractParagraphManage");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("ARF_JnlNo");
			table.setIsAscending(true);
			cache.updateTable(table);
			/** �÷����Ǽ�ס����ʷ��ѯ����*/
			if (action.equals(ServeTableForm.NAVIGATE) || action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else if (action.equals("Submit")) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String ARF_JnlNo = tableForm.getProperty("ARF_JnlNo");// Ӧ�տ���ˮ��
			String contractType = tableForm.getProperty("contractType");// ��ͬ����
			String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
			String paragraphNo = tableForm.getProperty("paragraphNo");// ƾ֤��
			String paragraphMoney = tableForm.getProperty("paragraphMoney");// ������
			String paragraphDate = tableForm.getProperty("paragraphDate");// ��������
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			String auditStatus = tableForm.getProperty("auditStatus");// ���״̬
			String maintDivision=tableForm.getProperty("maintDivision");//

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

				String sql = "select b.jnlNo,b.ARF_JnlNo,a.preDate,b.contractNo,b.contractType,b.paragraphNo,b.paragraphMoney,b.paragraphDate,b.rem,b.maintDivision,b.submitType,b.auditStatus from ProContractARFeeMaster a,ContractParagraphManage b where a.JnlNo=b.ARF_JnlNo";
				
				if (ARF_JnlNo != null && !ARF_JnlNo.equals("")) {
					sql += " and b.ARF_JnlNo like '%"+ARF_JnlNo.trim()+"%'";
				}
				if (contractType != null && !contractType.equals("")) {
					sql += " and b.contractType like '%"+contractType.trim()+"%'";
				}
				if (contractNo != null && !contractNo.equals("")) {
					sql += " and b.contractNo like '%"+contractNo.trim()+"%'";
				}
				if (paragraphMoney != null && !paragraphMoney.equals("")) {
					sql += " and b.paragraphMoney ="+Double.valueOf(paragraphMoney.trim());
				}
				if (paragraphDate != null && !paragraphDate.equals("")) {
					sql += " and b.paragraphDate like '"+paragraphDate.trim()+"'";
				}
				if (paragraphNo != null && !paragraphNo.equals("")) {
					sql += " and b.paragraphNo like '"+paragraphNo.trim()+"'";
				}
				if (submitType != null && !submitType.equals("")) {
					sql += " and b.submitType like '"+submitType.trim()+"'";
				}
				if (auditStatus != null && !auditStatus.equals("")) {
					sql += " and b.auditStatus like '"+auditStatus.trim()+"'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and b.maintDivision like '"+maintDivision.trim()+"'";
				}
				if (table.getIsAscending()) {
					sql += " order by b."+ table.getSortColumn() +" desc";
				} else {
					sql += " order by b."+ table.getSortColumn() +" asc";
				}
				
				query = hs.createSQLQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List contractParagraphManageList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map master=new HashMap();
					master.put("jnlNo", objs[0]);
					master.put("ARF_JnlNo", objs[1]);
					master.put("preDate", objs[2]);
					master.put("contractNo", objs[3]);
					master.put("contractType", objs[4]);
					master.put("paragraphNo", objs[5]);
					master.put("paragraphMoney", objs[6]);
					master.put("paragraphDate", objs[7]);
					master.put("rem", objs[8]);
					master.put("maintDivision", bd.getName("Company", "comname", "comid",objs[9].toString()));
					master.put("submitType", objs[10]);
					master.put("auditStatus", objs[11]);
					contractParagraphManageList.add(master);
				}

				table.addAll(contractParagraphManageList);
				session.setAttribute("contractParagraphManageList", table);
				/*// ��ͬ�����������б�
				request.setAttribute("contractNatureOfList", bd.getPullDownList("MaintContractMaster_ContractNatureOf"));*/
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);

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
			forward = mapping.findForward("contractParagraphManageList");
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
		
		request.setAttribute("navigator.location","��Ʊ >> ��ѯ�б� ");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "contractParagraphManageNextList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fContractParagraphManageNext");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("contractNo");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String jnlNo = tableForm.getProperty("jnlNo");// Ӧ�տ���ˮ��
			String maintDivision=tableForm.getProperty("maintDivision");//�����ֲ�
			String contractNo=tableForm.getProperty("contractNo");//��ͬ��
			String contractType=tableForm.getProperty("contractType");//��ͬ����
			String invoiceName=tableForm.getProperty("invoiceName");//��Ʊ����
			String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
						
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

				String sql = "select a.jnlNo,a.contractNo,a.contractType,a.companyId,a.recName,a.preDate," +
						"a.maintDivision,a.preMoney,c.invoiceName from ProContractArfeeMaster a, Customer c " +
						"where a.auditStatus='Y' and a.companyId=c.companyId " +
						"and a.jnlNo not in (select c.ARF_JnlNo from ProContractARFeeMaster p," +
						"(select ARF_JnlNo,SUM(paragraphMoney) paragraphMoney from ContractParagraphManage" +
						" group by ARF_JnlNo) c where p.JnlNo=c.ARF_JnlNo and p.PreMoney=c.paragraphMoney) ";
				
				if(salesContractNo != null && !salesContractNo.equals("")){
                   sql += " and billNo in (select d.billNo from MaintContractDetail d where d.salesContractNo like '%"+salesContractNo+"%')";
				}
				if (invoiceName != null && !invoiceName.equals("")) {
					sql+=" and (c.companyId like '%"+invoiceName.trim()+"%' or c.invoiceName like '%"+invoiceName.trim()+"%')";
				}
				if (jnlNo != null && !jnlNo.equals("")) {
					sql+=" and jnlNo like '%"+jnlNo.trim()+"%'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql+=" and maintDivision like '"+maintDivision.trim()+"'";
				}
				if (contractNo != null && !contractNo.equals("")) {
					sql+=" and contractNo like '%"+contractNo.trim()+"%'";
				}
				if (contractType != null && !contractType.equals("")) {
					sql+=" and contractType like '%"+contractType.trim()+"%'";
				}
				if (table.getIsAscending()) {
					sql += " order by "+ table.getSortColumn() +" desc";
				} else {
					sql += " order by "+ table.getSortColumn() +" asc";
				}
				
				//System.out.println(">>>>"+sql);
				
				query = hs.createSQLQuery(sql);
				
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List contractParagraphManageNextList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map map = new HashMap();
					
					String preMoney=objs[7].toString();
					map.put("jnlNo", objs[0]);
					map.put("contractNo", objs[1]);
					map.put("contractType", objs[2]);
					map.put("companyId", objs[3].toString());
					map.put("companyName",objs[8].toString());
					map.put("recName",bd.getName_Sql("ReceivablesName", "recName", "recId", objs[4].toString()));
					map.put("preDate", objs[5]);
					map.put("maintDivision",bd.getName_Sql("Company", "comname", "comid", objs[6].toString()));
					map.put("preMoney", preMoney);
					
					String notsql="select isnull(SUM(paragraphMoney),0) invoiceMoney from ContractParagraphManage where ARF_JnlNo='"+objs[0].toString().trim()+"'";
					List notlist=hs.createSQLQuery(notsql).list();
					String paragraphMoney=notlist.get(0).toString();
					map.put("notParMoney",Double.parseDouble(preMoney)-Double.parseDouble(paragraphMoney));
					
					contractParagraphManageNextList.add(map);
				}

				table.addAll(contractParagraphManageNextList);
				session.setAttribute("contractParagraphManageNextList", table);
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);
				//��Ʊ����
				request.setAttribute("invoiceTypeList", this.getInvoiceTypeList());

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
			forward = mapping.findForward("contractParagraphManageNextList");
		}
		return forward;
	}
	
	public List getInvoiceTypeList(){
		Session hs = null;
		Query query = null;
		List list=null;
		try {
			hs=HibernateUtil.getSession();
			String hql="from InvoiceType where enabledFlag='Y'";
			list=hs.createQuery(hql).list();
			for(Object object : list){
				InvoiceType invoicetype=(InvoiceType)object;
			}
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
	public ActionForward toSearchRecordAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("navigator.location","�������>> ��ѯ�б�");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "contractParagraphAuditList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fcontractParagraphAudit");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("ARF_JnlNo");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String ARF_JnlNo = tableForm.getProperty("ARF_JnlNo");// Ӧ�տ���ˮ��
			String contractType = tableForm.getProperty("contractType");// ��ͬ����
			String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
			String paragraphNo = tableForm.getProperty("paragraphNo");// ƾ֤��
			String paragraphMoney = tableForm.getProperty("paragraphMoney");// ������
			String paragraphDate = tableForm.getProperty("paragraphDate");// ��������
			String auditStatus = tableForm.getProperty("auditStatus");// ���״̬
			String maintDivision=tableForm.getProperty("maintDivision");//

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

				String sql = "select b.ARF_JnlNo,a.preDate,b.contractNo,b.contractType,b.paragraphNo,b.paragraphMoney,b.paragraphDate,b.rem,b.maintDivision,b.submitType,b.auditStatus from ProContractARFeeMaster a,ContractParagraphManage b where a.JnlNo=b.ARF_JnlNo and b.submitType='Y'";
				
				if (ARF_JnlNo != null && !ARF_JnlNo.equals("")) {
					sql += " and b.ARF_JnlNo like '%"+ARF_JnlNo.trim()+"%'";
				}
				if (contractType != null && !contractType.equals("")) {
					sql += " and b.contractType like '%"+contractType.trim()+"%'";
				}
				if (contractNo != null && !contractNo.equals("")) {
					sql += " and b.contractNo like '%"+contractNo.trim()+"%'";
				}
				if (paragraphMoney != null && !paragraphMoney.equals("")) {
					sql += " and b.paragraphMoney like '"+paragraphMoney.trim()+"'";
				}
				if (paragraphDate != null && !paragraphDate.equals("")) {
					sql += " and b.paragraphDate like '"+paragraphDate.trim()+"'";
				}
				if (paragraphNo != null && !paragraphNo.equals("")) {
					sql += " and b.paragraphNo like '"+paragraphNo.trim()+"'";
				}
				if (auditStatus != null && !auditStatus.equals("")) {
					sql += " and b.auditStatus like '"+auditStatus.trim()+"'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and b.maintDivision like '"+maintDivision.trim()+"'";
				}
				if (table.getIsAscending()) {
					sql += " order by b."+ table.getSortColumn() +" asc";
				} else {
					sql += " order by b."+ table.getSortColumn() +" desc";
				}
				
				query = hs.createSQLQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List contractParagraphAuditList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map master=new HashMap();
					master.put("ARF_JnlNo", objs[0]);
					master.put("preDate", objs[1]);
					master.put("contractNo", objs[2]);
					master.put("contractType", objs[3]);
					master.put("paragraphNo", objs[4]);
					master.put("paragraphMoney", objs[5]);
					master.put("paragraphDate", objs[6]);
					master.put("rem", objs[7]);
					master.put("maintDivision", bd.getName("Company", "comname", "comid",objs[8].toString()));
					master.put("submitType", objs[9]);
					master.put("auditStatus", objs[10]);
					contractParagraphAuditList.add(master);
				}

				table.addAll(contractParagraphAuditList);
				session.setAttribute("contractParagraphAuditList", table);
				
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);
				
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
			forward = mapping.findForward("contractParagraphAuditList");
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
		
		request.setAttribute("navigator.location","������� >> �鿴");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		
		display(form, request, errors, "display");
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		request.setAttribute("display", "yes");
		forward = mapping.findForward("contractParagraphManageDisplay");
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

		request.setAttribute("navigator.location","������� >> ���");
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		String billno=request.getParameter("billno");
		if(billno==null || "".equals(billno))
			billno=(String) request.getAttribute("arf_JnlNo");
			//billno=(String) dform.get("ARF_JnlNo");
		
		Session hs = null;
		Query query = null;
		try {
			hs = HibernateUtil.getSession();
			ContractParagraphManage master=new ContractParagraphManage();
			master.setOperId(userInfo.getUserID());
			request.setAttribute("operName", userInfo.getUserName());
			master.setOperDate(CommonUtil.getNowTime());
			master.setParagraphDate(CommonUtil.getToday());
			Map contractBean=new HashMap();
			String sql="select a.jnlNo,a.recName,a.preMoney,a.contractNo,a.contractType,a.companyID,a.maintDivision," +
					"b.contractTotal,d.invoiceTotal,c.amount,b.billno,a.maintScope,a.maintStation from ProContractARFeeMaster a "+
					"left join (select ARF_JnlNo,SUM(paragraphMoney) amount from ContractParagraphManage group by ARF_JnlNo) c on a.jnlNo=c.ARF_JnlNo "+
					"left join (select contractNo,SUM(paragraphMoney) invoiceTotal from ContractParagraphManage group by ContractNo) d on a.contractNo=d.contractNo,ViewCompact b "+
					"where a.billNo=b.billno and a.jnlNo='"+billno+"'";

			List list=hs.createSQLQuery(sql).list();
			if(list!=null && list.size()>0){
				for(Object object : list){
					Object[] objs=(Object[])object;
					contractBean.put("ARF_JnlNo", objs[0]);
					contractBean.put("recName", bd.getName("ReceivablesName", "recName", "recId", objs[1].toString()));
					contractBean.put("preMoney", objs[2]);
					master.setContractNo(objs[3].toString());
					master.setContractType(objs[4].toString());
					master.setCompanyId(objs[5].toString());
					master.setMaintDivision(objs[6].toString());
					master.setBillNo(objs[10].toString());
					master.setMaintStation((String)objs[12]);
					/*contractBean.put("contractNo", objs[3]);
					contractBean.put("contractType", objs[4]);
					contractBean.put("companyID", objs[5]);
					contractBean.put("maintDivision", objs[6]);*/
					contractBean.put("contractTotal", objs[7]);
					contractBean.put("invoiceTotal", objs[8]);
					contractBean.put("amount", objs[9]);
					contractBean.put("noInvoiceTotal", df.format((objs[7]==null ? 0 : Double.valueOf(objs[7].toString()))-(objs[8]==null ? 0 : Double.valueOf(objs[8].toString()))));
					contractBean.put("arrearsMoney", df.format((objs[2]==null ? 0 : Double.valueOf(objs[2].toString()))-(objs[9]==null ? 0 : Double.valueOf(objs[9].toString()))));
					contractBean.put("maintScope", objs[11]);
					
				}
			}
			request.setAttribute("maintDivisionName", bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));
			request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid",master.getMaintStation()));
			request.setAttribute("companyName", bd.getName_Sql("Customer", "companyName", "companyId", master.getCompanyId()));
			request.setAttribute("contractBean", contractBean);
			session.setAttribute("contractParagraphManageBean", master);
			
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		request.setAttribute("returnMethod", "toSearchRecord");
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		return mapping.findForward("contractParagraphManageAdd");
	}
	
/*	public List getReceivablesNameList(){
		Session hs = null;
		Query query = null;
		List list=null;
		try {
			hs=HibernateUtil.getSession();
			String hql="from ReceivablesName where enabledFlag='Y'";
			list=hs.createQuery(hql).list();
			for(Object object : list){
				ReceivablesName receivables=(ReceivablesName)object;
			}
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}*/
	
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

		//��ֹҳ���ظ��ύ
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
		
		request.setAttribute("navigator.location","������� >> �޸�");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		display(form, request, errors, "");

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
			
		request.setAttribute("returnMetho", "toSearchRecord");
		forward = mapping.findForward("contractParagraphManageModify");
		
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
	
	public ActionForward toReferRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		refer(form,request,errors); //�ύ

		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnList");

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
			
			String id = (String) dform.get("id");
			ContractParagraphManage master = (ContractParagraphManage) hs.get(ContractParagraphManage.class, id);
			if (master != null) {
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
	 * ��ת�����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPrepareAuditRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","������� >> ���");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		display(form, request, errors, "display");
		request.setAttribute("auditOpername", userInfo.getUserName());
		request.setAttribute("auditDate", CommonUtil.getNowTime());

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
			
		request.setAttribute("returnMetho", "toSearchRecordAudit");
		forward = mapping.findForward("contractParagraphAudit");
		
		return forward;
	}
	
	/**
	 * �����˵ķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toAuditRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		
		DynaActionForm dform = (DynaActionForm) form;

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		String jnlNo = (String) dform.get("jnlNo");
		String auditRem=(String)dform.get("auditRem");
		String auditStatus=(String)dform.get("auditStatus");
		String submitType=(String)dform.get("submitType");

		Session hs = null;
		Transaction tx = null;
			
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			ProContractArfeeMaster master=(ProContractArfeeMaster) hs.get(ProContractArfeeMaster.class, jnlNo.trim());
			if(master!=null){
				master.setSubmitType(submitType);
				master.setAuditOperid(userInfo.getUserID());
				master.setAuditDate(CommonUtil.getNowTime());
				master.setAuditRem(auditRem);
				master.setAuditStatus(auditStatus);
				hs.save(master);
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
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}
		String isreturn = request.getParameter("isreturn");		
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
				forward = mapping.findForward("returnAuditList");
			} else {
				// return modify page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				forward = mapping.findForward("returnAuditList");
				/*request.setAttribute("returnMethod", "toSearchRecordAudit");
				request.setAttribute("id", jnlNo);
				forward = mapping.findForward("returnAuditDisplay");*/
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
		Session hs = null;
		
		if (id != null && !id.equals("")) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from ContractParagraphManage where jnlNo = '"+id.trim()+"'");
				List list = query.list();
				if (list != null && list.size() > 0) {					
					// ����Ϣ
					ContractParagraphManage master = (ContractParagraphManage) list.get(0);	
					Map contractBean=new HashMap();
					String sql="select a.jnlNo,a.recName,a.preMoney,a.contractNo,a.contractType,a.companyID,a.maintDivision," +
							"b.contractTotal,d.invoiceTotal,c.amount,a.maintScope,a.maintStation from ProContractARFeeMaster a,ViewCompact b,"+
							"(select ARF_JnlNo,SUM(paragraphMoney) amount from ContractParagraphManage group by ARF_JnlNo) c,"+
							"(select contractNo,SUM(paragraphMoney) invoiceTotal from ContractParagraphManage group by ContractNo) d"+
							" where a.billNo=b.billno and a.jnlNo=c.ARF_JnlNo and a.contractNo=d.contractNo and a.jnlNo='"+master.getArfJnlNo()+"'";
					
					//System.out.println(">>>"+sql);
					
					List list1=hs.createSQLQuery(sql).list();
					if(list!=null && list1.size()>0){
						for(Object object : list1){
							Object[] objs=(Object[])object;
							contractBean.put("ARF_JnlNo", objs[0]);
							contractBean.put("recName", bd.getName("ReceivablesName", "recName", "recId", objs[1].toString()));
							contractBean.put("preMoney", objs[2]);
							/*contractBean.put("contractNo", objs[3]);
							contractBean.put("contractType", objs[4]);
							contractBean.put("companyID", objs[5]);
							contractBean.put("maintDivision", objs[6]);*/
							contractBean.put("contractTotal", objs[7]);
							contractBean.put("invoiceTotal", objs[8]);
							contractBean.put("amount", objs[9]);
							contractBean.put("noInvoiceTotal", df.format(Double.valueOf(objs[7].toString())-Double.valueOf(objs[8].toString())));
							double arrearsMoney=Double.valueOf(objs[2].toString())-Double.valueOf(objs[9].toString())+master.getParagraphMoney();
							if("display".equals(flag)){
								arrearsMoney=Double.valueOf(objs[2].toString())-Double.valueOf(objs[9].toString());
							}
							contractBean.put("maintScope", objs[10]);
							contractBean.put("arrearsMoney", df.format(arrearsMoney));
						}
					}
					request.setAttribute("contractBean", contractBean);
					if(master.getAuditOperid()!=null && !master.getAuditOperid().equals("")){
						master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid", master.getAuditOperid()));
					}
														
					if("display".equals(flag)){
						master.setMaintDivision(bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));// ά���ֲ�
						master.setMaintStation(bd.getName(hs, "Storageid", "storagename", "storageid",master.getMaintStation()));
						master.setCompanyId(bd.getName_Sql("Customer", "companyName", "companyId", master.getCompanyId()));
						master.setOperId(bd.getName(hs, "Loginuser", "username", "userid", master.getOperId()));
//						master.setRecName(bd.getName_Sql("ReceivablesName", "recName", "recId", master.getRecName()));
						
					} else {						
						request.setAttribute("companyName", bd.getName(hs,"Customer", "companyName", "companyId", master.getCompanyId()));
						request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid",master.getMaintStation()));
						request.setAttribute("maintDivisionName",bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision())); // ά���ֲ�����
						request.setAttribute("operName", bd.getName(hs, "Loginuser", "username", "userid", master.getOperId()));
//						request.setAttribute("receivablesList", this.getReceivablesNameList());
						
					}
					request.setAttribute("contractParagraphManageBean", master);	
					
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
		ContractParagraphManage master = null;
		String id = (String) dform.get("id");
		String ARF_JnlNo=(String)dform.get("ARF_JnlNo");
		String submitType=(String)dform.get("submitType");
		String jnlNo = null;

		Session hs = null;
		Transaction tx = null;
			
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if (id != null && !id.equals("")) { // �޸�		
				master = (ContractParagraphManage) hs.get(ContractParagraphManage.class, id);
				jnlNo = master.getJnlNo();
			} else { // ����
				master = new ContractParagraphManage();
				
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				jnlNo = CommonUtil.getBillno(year,"ContractParagraphManage", 1)[0];// ������ˮ��
			}
			ProContractArfeeMaster pcam=(ProContractArfeeMaster) hs.get(ProContractArfeeMaster.class, ARF_JnlNo);
			BeanUtils.populate(master, dform.getMap()); // ������������
			
			if(id==null || "".equals(id))
				master.setParagraphNo(this.getParagraphNo());
			
			master.setJnlNo(jnlNo);// ��ˮ��	
			master.setArfJnlNo(ARF_JnlNo);
			master.setSubmitType(submitType);
			master.setAuditStatus("N");
			hs.save(master);
			request.setAttribute("arf_JnlNo", master.getArfJnlNo());
		
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
		
	public void refer(ActionForm form, HttpServletRequest request,ActionErrors errors){
		
		String id = request.getParameter("id"); 
		
		if(id != null && !id.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			ContractParagraphManage master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (ContractParagraphManage) hs.get(ContractParagraphManage.class, id);
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
	
	//��ȡƾ֤��
	public String getParagraphNo(){
		String paragraphNo="";
		String prefix = "";
		String suffix = "";
		String year = CommonUtil.getNowTime("yyyy");
		String month=CommonUtil.getNowTime("MM");
		//System.out.println(month);
		
		prefix = year+month;
		paragraphNo=CommonUtil.genNo("ContractParagraphManage", "paragraphNo", prefix, suffix, 4,"paragraphNo");
		return paragraphNo;
	}
}	