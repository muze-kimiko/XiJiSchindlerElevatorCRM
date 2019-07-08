package com.gzunicorn.struts.action.contractpaymentmanage;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.gzunicorn.common.pdfprint.BarCodePrint;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.NumberToCN;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.basedata.invoicetype.InvoiceType;
import com.gzunicorn.hibernate.contractpaymentmanage.contractticketmanage.ContractTicketManage;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractmaster.EntrustContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class InvoiceManageAction extends DispatchAction {

	Log log = LogFactory.getLog(InvoiceManageAction.class);
	
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
				SysRightsUtil.NODE_ID_FORWARD + "invoicemanage", null);
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
		
		request.setAttribute("navigator.location","��Ʊ���� >> ��ѯ�б�");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "invoiceManageList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fInvoiceManage");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("jnlNo");
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
			
			String jnlNo = tableForm.getProperty("jnlNo");// Ӧ�տ���ˮ��
			String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
			String companyName = tableForm.getProperty("companyName");// �׷���λ
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			String auditStatus = tableForm.getProperty("auditStatus");// ���״̬
			String maintDivision=tableForm.getProperty("maintDivision");//����ά���ֲ�
			String salesContractNo=tableForm.getProperty("salesContractNo");//���ۺ�ͬ��
			String salesContractNoStr="";
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

				if (salesContractNo != null && !salesContractNo.equals("")) {
					String hql = "select distinct ecd.billNo from EntrustContractDetail ecd where ecd.salesContractNo like '%"+salesContractNo.trim()+"%'"
							   + " UNION ALL "
							   + "select distinct ocd.billno from OutsourceContractDetail ocd where ocd.salesContractNo like '%"+salesContractNo.trim()+"%'";
					List salesContractNoList=hs.createSQLQuery(hql).list();
				   if(salesContractNoList!=null&&salesContractNoList.size()>0){
					   for(int i=0;i<salesContractNoList.size();i++){
						   salesContractNoStr+=i==salesContractNoList.size()-1?"'"+salesContractNoList.get(i)+"'":"'"+salesContractNoList.get(i)+"',";
					   }
				   }
				
				}
				String sql = "select ctm,c.companyName,compay.comfullname,it.inTypeName from ContractTicketManage ctm,Customer c,Company compay,InvoiceType it where it.inTypeId=ctm.invoiceType and compay.comid=ctm.maintDivision and ctm.companyId=c.companyId";
				
				if (jnlNo != null && !jnlNo.equals("")) {
					sql += " and ctm.jnlNo like '%"+jnlNo.trim()+"%'";
				}
				if (contractNo != null && !contractNo.equals("")) {
					sql += " and ctm.entrustContractNo like '%"+contractNo.trim()+"%'";
				}
				if (companyName != null && !companyName.equals("")) {
					sql += " and (c.companyName like '%"+companyName.trim()+"%' or c.companyId like '%"+companyName.trim()+"%')";
				}
				if (auditStatus != null && !auditStatus.equals("")) {
					sql += " and ctm.auditStatus like '"+auditStatus.trim()+"'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and ctm.maintDivision like '"+maintDivision.trim()+"'";
				}
				if (submitType != null && !submitType.equals("")) {
					sql += " and ctm.submitType like '"+submitType.trim()+"'";
				}
				
				if(salesContractNo != null && !salesContractNo.equals("")){
					if (salesContractNoStr != null && !salesContractNoStr.equals("")) {
						sql += " and ctm.billNo in ("+salesContractNoStr+")";
					}else{
						sql += " and ctm.billNo = ''";
					}
				}

				
				
				if (table.getIsAscending()) {
					sql += " order by ctm."+ table.getSortColumn() +" desc";
				} else {
					sql += " order by ctm."+ table.getSortColumn() +" asc";
				}
				
				
				query = hs.createQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List invoiceManageList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					ContractTicketManage ctm=(ContractTicketManage) objs[0];
					ctm.setCompanyId((String) objs[1]);
					ctm.setMaintDivision((String) objs[2]);
					ctm.setInvoiceType((String) objs[3]);
					invoiceManageList.add(ctm);
				}

				table.addAll(invoiceManageList);
				session.setAttribute("invoiceManageList", table);
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
			forward = mapping.findForward("invoiceManageList");
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
		
		request.setAttribute("navigator.location","��ͬ >> ��ѯ�б� ");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "invoiceManageNextList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fInvoiceManageNext");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("billno");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String billno = tableForm.getProperty("billno");// ��ˮ��
			String entrustContractNo = tableForm.getProperty("entrustContractNo");// ��ͬ��
			String maintDivision =tableForm.getProperty("maintDivision");// ά���ֲ�
			String salesContractNo=tableForm.getProperty("salesContractNo");//���ۺ�ͬ��
			String salesContractNoStr="";
			/*if(maintDivision!=null && maintDivision.equals("00")){
				maintDivision="";
			}*/
						
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
				
				if (salesContractNo != null && !salesContractNo.equals("")) {
					String hql = "select distinct ecd.billNo from EntrustContractDetail ecd where ecd.salesContractNo like '%"+salesContractNo.trim()+"%'"
							   + " UNION ALL "
							   + "select distinct ocd.billno from OutsourceContractDetail ocd where ocd.salesContractNo like '%"+salesContractNo.trim()+"%'";
					List salesContractNoList=hs.createSQLQuery(hql).list();
				   if(salesContractNoList!=null&&salesContractNoList.size()>0){
					   for(int i=0;i<salesContractNoList.size();i++){
						   salesContractNoStr+=i==salesContractNoList.size()-1?"'"+salesContractNoList.get(i)+"'":"'"+salesContractNoList.get(i)+"',";
					   }
				   }
				}
				
				
				
				String sql = "select e.billno,e.contractNo,e.num,e.comfullname,e.companyName,e.maintDivision,e.contractTotal,e.companyId2 from Payables_view e "
						   + " where e.ContractTotal>(select isnull(SUM(ctm.InvoiceMoney),0) from ContractTicketManage ctm where ctm.BillNo=e.BillNo and (auditStatus='N' or auditStatus='Y'))";
				
				if (billno != null && !billno.equals("")) {
					sql += " and e.billno like '%"+billno.trim()+"%'";
				}
				if (entrustContractNo != null && !entrustContractNo.equals("")) {
					sql += " and e.contractNo like '%"+entrustContractNo.trim()+"%'";
				}				
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and e.maintDivision like '"+maintDivision.trim()+"'";
				}	
				
				
				if(salesContractNo != null && !salesContractNo.equals("")){
					if (salesContractNoStr != null && !salesContractNoStr.equals("")) {
						sql += " and e.billNo in ("+salesContractNoStr+")";
					}else{
						sql += " and e.billNo = ''";
					}
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
				List invoiceManageNextList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map map = new HashMap();
					map.put("billno", objs[0]);
					map.put("entrustContractNo", objs[1]);
					map.put("num", objs[2]);
					map.put("maintDivision", objs[3]);
					map.put("companyID",objs[4]);
					invoiceManageNextList.add(map);
				}

				table.addAll(invoiceManageNextList);
				session.setAttribute("invoiceManageNextList", table);
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
			forward = mapping.findForward("invoiceManageNextList");
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
		
		request.setAttribute("navigator.location","��Ʊ���� >> �鿴");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		
		display(form, request, errors, "display");
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		request.setAttribute("display", "yes");
		forward = mapping.findForward("invoiceManageDisplay");
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

		request.setAttribute("navigator.location","��Ʊ���� >> ���");
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
			ContractTicketManage master=new ContractTicketManage();
			master.setOperId(userInfo.getUserID());
			request.setAttribute("operName", userInfo.getUserName());
			HashMap ecm=new HashMap();
			String sql="select e.billno,e.contractNo,e.num,e.comfullname,e.companyName,e.maintDivision,e.contractTotal,e.companyId2 from Payables_view e where  e.billno='"+request.getParameter("billno")+"'";
			List list1=hs.createSQLQuery(sql).list();
			if(list1!=null && list1.size()>0){
				Object[] obj=(Object[]) list1.get(0); 				
				ecm.put("billno", obj[0]);
				ecm.put("contractNo", obj[1]);
				ecm.put("num", obj[2]);
				ecm.put("comfullname", obj[3]);
				ecm.put("companyName", obj[4]);
				ecm.put("maintDivision", obj[5]);
				ecm.put("contractTotal", obj[6]);
				ecm.put("companyId2", obj[7]);
			}
			
			
			double contractTotal =((BigDecimal) ecm.get("contractTotal")).doubleValue();
			master.setBillNo(((String) ecm.get("billno")).trim());
			master.setEntrustContractNo(((String) ecm.get("contractNo")).trim());
			master.setCompanyId(((String) ecm.get("companyId2")).trim());
			master.setMaintDivision(((String) ecm.get("maintDivision")).trim());
			master.setOperDate(CommonUtil.getNowTime());
			master.setInvoiceDate(CommonUtil.getNowTime("yyyy-MM-dd"));
			request.setAttribute("contractTotal", contractTotal);
			String hql="select isnull(sum(invoiceMoney),0) from ContractTicketManage where entrustContractNo='"+ecm.get("contractNo")+"' and (auditStatus='N' or auditStatus='Y')  group by entrustContractNo";
			List list=hs.createSQLQuery(hql).list();
			double builtReceivables=0.0;
			if(list!=null&&list.size()>0)
			{
			  builtReceivables=Double.valueOf(list.get(0).toString());
			}			
			request.setAttribute("builtReceivables", builtReceivables);
			request.setAttribute("noBuiltReceivables", contractTotal-builtReceivables);		
			request.setAttribute("companyName",  ecm.get("companyName"));
			request.setAttribute("maintDivisionName",ecm.get("comfullname"));
			request.setAttribute("receivablesList", this.getReceivablesNameList());
			session.setAttribute("invoiceManageBean", master);
			
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}	
		request.setAttribute("returnMethod", "toSearchRecord");		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		return mapping.findForward("invoiceManageAdd");
	}
	
	public List getReceivablesNameList(){
		Session hs = null;
		Query query = null;
		List list=null;
		try {
			hs=HibernateUtil.getSession();			
			
			String hql="from InvoiceType where enabledFlag='Y'";
			list=hs.createQuery(hql).list();
			for(Object object : list){
				InvoiceType receivables=(InvoiceType)object;
			}
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
		
		request.setAttribute("navigator.location","ά����ͬ���� >> �޸�");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		display(form, request, errors, "");

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
			
		request.setAttribute("returnMetho", "toSearchRecord");
		forward = mapping.findForward("invoiceManageModify");
		
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
	 * ����ύ�ķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
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
			
			//ɾ����ͬ����������������Ϣ
			String sqldel="delete a from ContractPaymentProcess a,ContractPaymentManage b "
					+ "where a.jnlno=b.jnlno and b.ct_jnlno='"+id.trim()+"'";
			hs.connection().prepareStatement(sqldel).executeUpdate();
			//ɾ����ͬ�������
			String sqldel1="delete from ContractPaymentManage where ct_jnlno='"+id.trim()+"'";
			hs.connection().prepareStatement(sqldel1).executeUpdate();
			//ɾ����ͬ��Ʊ����
			String sqldel2="delete from ContractTicketManage where jnlno='"+id.trim()+"'";
			hs.connection().prepareStatement(sqldel2).executeUpdate();

			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));
			
			tx.commit();
		} catch (Exception e2) {
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
		Session hs = null;
		
		if (id != null && !id.equals("")) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from ContractTicketManage where jnlNo = '"+id.trim()+"'");
				List list = query.list();
				if (list != null && list.size() > 0) {					
					// ����Ϣ
					ContractTicketManage master = (ContractTicketManage) list.get(0);															
					HashMap ecm=new HashMap();
					String sql="select e.billno,e.contractNo,e.num,e.comfullname,e.companyName,e.maintDivision,e.contractTotal,e.companyId2 from Payables_view e where  e.billno='"+master.getBillNo()+"'";
					List list1=hs.createSQLQuery(sql).list();
					if(list1!=null && list1.size()>0){
						Object[] obj=(Object[]) list1.get(0); 				
						ecm.put("billno", obj[0]);
						ecm.put("contractNo", obj[1]);
						ecm.put("num", obj[2]);
						ecm.put("comfullname", obj[3]);
						ecm.put("companyName", obj[4]);
						ecm.put("maintDivision", obj[5]);
						ecm.put("contractTotal", obj[6]);
						ecm.put("companyId2", obj[7]);
					}
					
					
					request.setAttribute("contractTotal", ecm.get("contractTotal"));
					double builtReceivables=0.0;
					String hql="";
					if(master.getAuditOperid()!=null && !master.getAuditOperid().equals("")){
						master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid", master.getAuditOperid()));
					}
					if("display".equals(flag)){
						master.setMaintDivision(bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));// ά���ֲ�	
						master.setCompanyId(bd.getName_Sql("Customer", "companyName", "companyId", master.getCompanyId()));
						master.setOperId(bd.getName(hs, "Loginuser", "username", "userid", master.getOperId()));
						master.setInvoiceType(bd.getName_Sql("InvoiceType", "InTypeName", "InTypeId", master.getInvoiceType()));
						hql="select isnull(sum(invoiceMoney),0) from ContractTicketManage where entrustContractNo='"+ecm.get("contractNo")+"' and (auditStatus='N' or auditStatus='Y') group by entrustContractNo";
					} else {						
						request.setAttribute("companyName", bd.getName(hs,"Customer", "companyName", "companyId", master.getCompanyId()));
						request.setAttribute("maintDivisionName",bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision())); // ά���ֲ�����
						request.setAttribute("operName", bd.getName(hs, "Loginuser", "username", "userid", master.getOperId()));
						request.setAttribute("receivablesList", this.getReceivablesNameList());
					    //�޸�ʱ��sumѡ�м�¼Ӧ�����
						hql="select isnull(sum(invoiceMoney),0) from ContractTicketManage where jnlNo ! = '"+id.trim()+"' and entrustContractNo='"+ecm.get("contractNo")+"' and (auditStatus='N' or auditStatus='Y') group by entrustContractNo";		
					}
					List preMoneylist=hs.createSQLQuery(hql).list();
					if(preMoneylist!=null&&preMoneylist.size()>0)
					{
					  builtReceivables=Double.valueOf(preMoneylist.get(0).toString());
					}	
					request.setAttribute("builtReceivables", builtReceivables);
					request.setAttribute("noBuiltReceivables", ((BigDecimal) ecm.get("contractTotal")).doubleValue()-builtReceivables);	
					request.setAttribute("invoiceManageBean", master);	
					
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
		ContractTicketManage master = null;
		String id = (String) dform.get("id");
		String submitType=(String)dform.get("submitType");
		String jnlNo = null;
		Session hs = null;
		Transaction tx = null;
			
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if (id != null && !id.equals("")) { // �޸�		
				master = (ContractTicketManage) hs.get(ContractTicketManage.class, id);
				jnlNo = master.getJnlNo();
			} else { // ����
				master = new ContractTicketManage();	
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				jnlNo = CommonUtil.getBillno(year,"ContractTicketManage", 1)[0];// ������ˮ��
			}
			BeanUtils.populate(master, dform.getMap()); // ������������			
			master.setJnlNo(jnlNo);// ��ˮ��	
			master.setSubmitType(submitType);
			master.setAuditStatus("N");
			hs.save(master);
		
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
			ContractTicketManage master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (ContractTicketManage) hs.get(ContractTicketManage.class, id);
				master.setSubmitType("Y");
				master.setAuditDate("");
				master.setAuditOperid("");
				master.setAuditRem("");
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

	//����֪ͨ������
    public void inserInformDate(ActionForm form, HttpServletRequest request,ActionErrors errors,String jnlNo){
		 
		
		if(jnlNo != null && !jnlNo.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			ContractTicketManage master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (ContractTicketManage) hs.get(ContractTicketManage.class, jnlNo);
				master.setInformDate(CommonUtil.getToday());
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
	
	//��ӡ����
    public ActionForward toPrintRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	ActionErrors errors = new ActionErrors();
    	HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs = null;
		Transaction tx = null;
		List barCodeList = new ArrayList();
		List noticeList=new ArrayList();
		ContractTicketManage manage=null;
		String jnlNo=request.getParameter("id");
		
		inserInformDate(form, request, errors, jnlNo);
		if(jnlNo!=null && !jnlNo.equals("")){
			try {
				hs=HibernateUtil.getSession();
				manage=(ContractTicketManage) hs.get(ContractTicketManage.class, jnlNo);
				String sql="select company.comfullname,ctm.entrustContractNo,c.companyName,c.bank,c.account,ctm.invoiceMoney from ContractTicketManage ctm,Customer c,Company company where c.companyId=ctm.companyId and company.comid=ctm.maintDivision and ctm.jnlNo='"+jnlNo.trim()+"'";
				List list=hs.createQuery(sql).list();
				if(list!=null&&list.size()>0){
					Map map=new HashMap();
					Object[] objs=(Object[]) list.get(0);
					map.put("comfullname", objs[0]);//�����ֲ�
					map.put("entrustContractNo", objs[1]);//ί�к�ͬ��
					map.put("companyName", objs[2]);//�ͻ���λ����
					map.put("bank", objs[3]);//�ͻ���������
					map.put("account", objs[4]);//�ͻ����к���
					map.put("invoiceMoney", objs[5]);//��Ʊ���
					BigDecimal numberOfMoney=BigDecimal.valueOf((Double)objs[5]);//��Ʊ���תΪ��ȷ������
				    String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);//��Ʊ���תΪ����д����
				    map.put("invoiceMoney_CN", s);//���ķ�Ʊ���
				    map.put("userName", userInfo.getUserName());//��ǰ��¼��
				    barCodeList.add(map);
				}
                  
				//��barCodeList��noticeList����
				BarCodePrint dy = new BarCodePrint();
				dy.toPrintTwoRecord3(response, barCodeList);
			} catch (DataStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
    	return null;
    }	
}	