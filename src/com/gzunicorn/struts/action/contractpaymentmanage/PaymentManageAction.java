package com.gzunicorn.struts.action.contractpaymentmanage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.ResultSet;
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
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.pdfprint.BarCodePrint;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.NumberToCN;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.hibernate.basedata.receivablesname.ReceivablesName;
import com.gzunicorn.hibernate.contractpaymentmanage.contractpaymentmanage.ContractPaymentManage;
import com.gzunicorn.hibernate.contractpaymentmanage.contractpaymentprocess.ContractPaymentProcess;
import com.gzunicorn.hibernate.contractpaymentmanage.contractticketmanage.ContractTicketManage;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractmaster.EntrustContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.workflow.bo.JbpmExtBridge;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class PaymentManageAction extends DispatchAction {

	Log log = LogFactory.getLog(PaymentManageAction.class);
	
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
				SysRightsUtil.NODE_ID_FORWARD + "paymentmanage", null);
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
		
		request.setAttribute("navigator.location","������� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();
		
		request.setAttribute("showroleid", userInfo.getRoleID());

		if (tableForm.getProperty("genReport") != null
				&& tableForm.getProperty("genReport").toString().equals("Y")) {
			try {
				response = toExcelRecord(form,request,response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			forward = mapping.findForward("exportExcel");
			tableForm.setProperty("genReport","N");

		} else {
			HTMLTableCache cache = new HTMLTableCache(session, "paymentManageList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fPaymentManage");
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
			
			String jnlNo = tableForm.getProperty("jnlNo");// ��Ʊ��ˮ��
			String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
			String companyName = tableForm.getProperty("companyName");// �׷���λ
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			String status = tableForm.getProperty("status");// 
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
				String sql = "select cpm,c.companyName,compay.comfullname,ctm.jnlNo " +
						"from ContractPaymentManage cpm,ContractTicketManage ctm,Customer c,Company compay " +
						"where compay.comid=cpm.maintDivision and cpm.companyId=c.companyId and ctm.jnlNo=cpm.ctJnlNo";
				
				if (jnlNo != null && !jnlNo.equals("")) {
					sql += " and ctm.jnlNo like '%"+jnlNo.trim()+"%'";
				}
				if (contractNo != null && !contractNo.equals("")) {
					sql += " and cpm.entrustContractNo like '%"+contractNo.trim()+"%'";
				}
				if (companyName != null && !companyName.equals("")) {
					sql += " and (c.companyName like '%"+companyName.trim()+"%' or c.companyId like '%"+companyName.trim()+"%')";
				}
				if (status != null && !status.equals("")) {
					sql += " and cpm.status like '"+status.trim()+"'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and cpm.maintDivision like '"+maintDivision.trim()+"'";
				}
				if (submitType != null && !submitType.equals("")) {
					sql += " and cpm.submitType like '"+submitType.trim()+"'";
				}
				
				if(salesContractNo != null && !salesContractNo.equals("")){
					if (salesContractNoStr != null && !salesContractNoStr.equals("")) {
						sql += " and cpm.billNo in ("+salesContractNoStr+")";
					}else{
						sql += " and cpm.billNo = ''";
					}
				}

				
				if (table.getIsAscending()) {
					sql += " order by cpm."+ table.getSortColumn() +" desc";
				} else {
					sql += " order by cpm."+ table.getSortColumn() +" asc";
				}
				
				//System.out.println(">>>"+sql);
				
				query = hs.createQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List paymentManageList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					ContractPaymentManage cpm=(ContractPaymentManage) objs[0];
					cpm.setCompanyId((String) objs[1]);
					cpm.setMaintDivision((String) objs[2]);
					cpm.setR1((String) objs[3]);
					cpm.setR2(bd.getName_Sql("ViewFlowStatus", "typename", "typeid", cpm.getStatus().toString()));
					paymentManageList.add(cpm);
				}

				table.addAll(paymentManageList);
				session.setAttribute("paymentManageList", table);
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);
				// ����״̬�������б�
				request.setAttribute("processStatusList", bd.getProcessStatusList());

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
			forward = mapping.findForward("paymentManageList");
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
			HTMLTableCache cache = new HTMLTableCache(session, "paymentManageNextList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fPaymentManageNext");
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
			
			String jnlNo = tableForm.getProperty("jnlNo");// ��ˮ��
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

				
				String sql = "select ctm.jnlNo,ctm.EntrustContractNo,ctm.InvoiceMoney,company.ComFullName,customer.CompanyName "
						+ "from ContractTicketManage ctm,Company company,Customer customer where "
						+ " company.comid=ctm.MaintDivision "
						+ " and ctm.CompanyID=customer.companyId "
						+ " and ctm.AuditStatus='Y' "
						+ " and ctm.InvoiceMoney > (select isnull( sum(cpm.ParagraphMoney), 0 ) from ContractPaymentManage cpm where ctm.JnlNo=cpm.CT_JnlNo)";
				
				if (jnlNo != null && !jnlNo.equals("")) {
					sql += " and ctm.jnlNo like '%"+jnlNo.trim()+"%'";
				}
				if (entrustContractNo != null && !entrustContractNo.equals("")) {
					sql += " and ctm.entrustContractNo like '%"+entrustContractNo.trim()+"%'";
				}				
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and ctm.maintDivision like '"+maintDivision.trim()+"'";
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
				
				query = hs.createSQLQuery(sql);
				
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List paymentManageNextList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map map = new HashMap();
					map.put("jnlNo", objs[0]);
					map.put("entrustContractNo", objs[1]);
					map.put("invoiceMoney", objs[2]);
					map.put("maintDivision", objs[3]);
					map.put("companyID",objs[4]);
					paymentManageNextList.add(map);
				}

				table.addAll(paymentManageNextList);
				session.setAttribute("paymentManageNextList", table);
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
			forward = mapping.findForward("paymentManageNextList");
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
		forward = mapping.findForward("paymentManageDisplay");
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
		
		Session hs = null;
		Query query = null;
		try {
			hs = HibernateUtil.getSession();
			ContractPaymentManage master=new ContractPaymentManage();
			master.setOperId(userInfo.getUserID());
			request.setAttribute("operName", userInfo.getUserName());

			ContractTicketManage ctm=new ContractTicketManage();
			String sql1="from ContractTicketManage where jnlNo='"+request.getParameter("jnlNo")+"'";
			List list2=hs.createQuery(sql1).list();
			if(list2!=null && list2.size()>0){
				ctm=(ContractTicketManage) list2.get(0);
			}
			
			double invoiceMoney =ctm.getInvoiceMoney();
			EntrustContractMaster ecm=new EntrustContractMaster();
			String sql="from EntrustContractMaster where billNo='"+ctm.getBillNo()+"'";
			List list1=hs.createQuery(sql).list();
			if(list1!=null && list1.size()>0){
				ecm=(EntrustContractMaster) list1.get(0);
			}
			double contractTotal =ecm.getContractTotal();
			master.setBillNo(ecm.getBillNo().trim());
			master.setEntrustContractNo(ecm.getEntrustContractNo().trim());			
			master.setCompanyId(ecm.getCompanyId2());	
			master.setMaintDivision(ecm.getMaintDivision());
			master.setOperDate(CommonUtil.getNowTime());
			master.setParagraphDate(CommonUtil.getNowTime("yyyy-MM-dd"));
			request.setAttribute("contractTotal", contractTotal);
			request.setAttribute("invoiceMoney", invoiceMoney);			
			String hql="select isnull(sum(paragraphMoney),0) from ContractPaymentManage where CT_JnlNo='"+ctm.getJnlNo()+"'";
			List list=hs.createSQLQuery(hql).list();
			double builtReceivables=0.0;
			if(list!=null&&list.size()>0)
			{
			  builtReceivables=Double.valueOf(list.get(0).toString());
			}			
		    hql="select isnull(sum(paragraphMoney),0) from ContractPaymentManage where entrustContractNo='"+ecm.getEntrustContractNo()+"'";
			list=hs.createSQLQuery(hql).list();
			double invoiceReceivables=0.0;
			if(list!=null&&list.size()>0)
			{
				invoiceReceivables=Double.valueOf(list.get(0).toString());
			}	
			
			request.setAttribute("ctjnlNo", ctm.getJnlNo());
			request.setAttribute("invoiceNo", ctm.getInvoiceNo());
			request.setAttribute("invoiceType", bd.getName(hs, "InvoiceType", "inTypeName", "inTypeId", ctm.getInvoiceType()));
			request.setAttribute("invoiceReceivables", invoiceReceivables);
			request.setAttribute("noInvoiceReceivables", contractTotal-invoiceReceivables);		
			request.setAttribute("builtReceivables", builtReceivables);
			request.setAttribute("noBuiltReceivables", invoiceMoney-builtReceivables);		
			request.setAttribute("companyName", bd.getName(hs,"Customer","companyName", "companyId", ecm.getCompanyId2()));
			request.setAttribute("maintDivisionName", bd.getName(hs,"Company","comfullname", "comid", ecm.getMaintDivision()));
			request.setAttribute("receivablesList", this.getReceivablesNameList());
			session.setAttribute("paymentManageBean", master);
			
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
		
		return mapping.findForward("paymentManageAdd");
	}
	
	public List getReceivablesNameList(){
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
		
		request.setAttribute("navigator.location","������� >> �޸�");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		display(form, request, errors, "");

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
			
		request.setAttribute("returnMetho", "toSearchRecord");
		forward = mapping.findForward("paymentManageModify");
		
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
			Query query = hs.createQuery("from ContractPaymentManage where jnlNo = '"+id.trim()+"'");
			List list = query.list();
			if (list != null && list.size() > 0) {
				//ɾ�����������Ϣ
				String sqldel="delete from ContractPaymentProcess where jnlno='"+id.trim()+"'";
				hs.connection().prepareStatement(sqldel).executeUpdate();
				// ����Ϣ
				ContractPaymentManage master = (ContractPaymentManage) list.get(0);	
				hs.delete(master);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));
			}
			tx.commit();
		} catch (Exception e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.foreignkeyerror"));
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
		
		List paymentManageList = new ArrayList();
		Session hs = null;
		ServeTableForm tableForm = (ServeTableForm) form;

		String jnlNo = tableForm.getProperty("jnlNo");// ��Ʊ��ˮ��
		String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
		String companyName = tableForm.getProperty("companyName");// �׷���λ
		String submitType = tableForm.getProperty("submitType");// �ύ��־
		String status = tableForm.getProperty("status");//���״̬
		String maintDivision=tableForm.getProperty("maintDivision");//����ά���ֲ�
		String salesContractNo=tableForm.getProperty("salesContractNo");//���ۺ�ͬ��
		String salesContractNoStr="";
		
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
			String  sql = "select cpm.ParagraphDate,cpm.BydAuditEvaluate," +
					"cpm.HfAuditNum,cpm.HfAuditNum2,cpm.RxAuditNum,cpm.RxAuditNum2," +
					"cpm.JjthAuditEvaluate,cpm.FbzAuditEvaluate," +
			 		"c.companyName,compay.comname,c.qualiLevel_Wb,v.typename " +
					"from ContractPaymentManage cpm,ContractTicketManage ctm," +
					"Customer c,Company compay,ViewFlowStatus v " +
					"where compay.comid=cpm.maintDivision and cpm.companyId=c.companyId " +
					"and ctm.jnlNo=cpm.ct_JnlNo and cpm.Status=v.typeid ";
			
			if (jnlNo != null && !jnlNo.equals("")) {
				sql += " and ctm.jnlNo like '%"+jnlNo.trim()+"%'";
			}
			if (contractNo != null && !contractNo.equals("")) {
				sql += " and cpm.entrustContractNo like '%"+contractNo.trim()+"%'";
			}
			if (companyName != null && !companyName.equals("")) {
				sql += " and (c.companyName like '%"+companyName.trim()+"%' or c.companyId like '%"+companyName.trim()+"%')";
			}
			if (status != null && !status.equals("")) {
				sql += " and cpm.status like '"+status.trim()+"'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and cpm.maintDivision like '"+maintDivision.trim()+"'";
			}
			if (submitType != null && !submitType.equals("")) {
				sql += " and cpm.submitType like '"+submitType.trim()+"'";
			}
			
			if(salesContractNo != null && !salesContractNo.equals("")){
				if (salesContractNoStr != null && !salesContractNoStr.equals("")) {
					sql += " and cpm.billNo in ("+salesContractNoStr+")";
				}else{
					sql += " and cpm.billNo = ''";
				}
			}

			sql += " order by cpm.JnlNo desc";
			
			//System.out.println(">>>"+sql);

			ResultSet rs=hs.connection().prepareStatement(sql).executeQuery();
			while(rs.next()){
				HashMap map=new HashMap();
				map.put("comname", rs.getString("companyName"));//ί�е�λ����
				map.put("maintdivision", rs.getString("comname"));//�����ֲ�
				map.put("paragraphdate", rs.getString("ParagraphDate"));//�������� 
				
				String bydauditevaluate=rs.getString("BydAuditEvaluate");
				if(bydauditevaluate!=null && bydauditevaluate.trim().equals("WZ")){
					bydauditevaluate="����";
				}else if(bydauditevaluate!=null && bydauditevaluate.trim().equals("YB")){
					bydauditevaluate="һ��";
				}else if(bydauditevaluate!=null && bydauditevaluate.trim().equals("BWZ")){
					bydauditevaluate="������";
				}else{
					bydauditevaluate="";
				}
				map.put("bydauditevaluate",bydauditevaluate);//���������������
				map.put("hfauditnum", rs.getString("HfAuditNum"));//������������ĸ����������
				map.put("hfauditnum2", rs.getString("HfAuditNum2"));//��Ǽ�����������ĸ����������
				map.put("rxauditnum", rs.getString("RxAuditNum"));//�������������Ͷ�ߴ���
				map.put("rxauditnum2", rs.getString("RxAuditNum2"));//��Ǽ������������Ͷ�ߴ���
				
				String jjthauditevaluate=rs.getString("JjthAuditEvaluate");
				if(jjthauditevaluate!=null && jjthauditevaluate.trim().equals("THJS")){
					jjthauditevaluate="�˻ؼ�ʱ";
				}else if(jjthauditevaluate!=null && jjthauditevaluate.trim().equals("THYB")){
					jjthauditevaluate="һ��";
				}else if(jjthauditevaluate!=null && jjthauditevaluate.trim().equals("THBJS")){
					jjthauditevaluate="�˻ز���ʱ";
				}else{
					jjthauditevaluate="";
				}
				map.put("jjthauditevaluate", jjthauditevaluate);//�ɼ��˻��������

				String fbzauditevaluate=rs.getString("FbzAuditEvaluate");
				if(fbzauditevaluate!=null && fbzauditevaluate.trim().equals("PH")){
					fbzauditevaluate="���";
				}else if(fbzauditevaluate!=null && fbzauditevaluate.trim().equals("PHYB")){
					fbzauditevaluate="һ��";
				}else if(fbzauditevaluate!=null && fbzauditevaluate.trim().equals("BPH")){
					fbzauditevaluate="�����";
				}else{
					fbzauditevaluate="";
				}
				map.put("fbzauditevaluate", fbzauditevaluate);//�ֲ����������
				map.put("qualiLevelwb",rs.getString("qualiLevel_Wb"));//ά�����ʼ���
				map.put("typename", rs.getString("typename"));//���״̬
				
				paymentManageList.add(map);
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
		
		String[] titlename={"ί�е�λ����","�����ֲ�","��������","���������������","���лطý�����",
				"���߹�����","�ɼ��˻��������","�ֲ����������","ά�����ʼ���","���״̬"};
		String[] titleid={"comname","maintdivision","paragraphdate","bydauditevaluate","hfauditnum","hfauditnum2",
				"rxauditnum","rxauditnum2","jjthauditevaluate","fbzauditevaluate","qualiLevelwb","typename"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("�������");
        
        //������Ԫ����ʽ
        XSSFCellStyle cs = wb.createCellStyle();
        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
        XSSFFont f  = wb.createFont();
        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
        cs.setFont(f);
        
        //��������
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell0 = null;
        int ii=0;
		for(int i=0;i<titlename.length;i++){
			cell0 = row0.createCell((short)ii);
			//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			if(titlename[i].equals("���лطý�����") || titlename[i].equals("���߹�����")){
				//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
				sheet.addMergedRegion(new CellRangeAddress(0, 0, ii, ii+1));
				ii=ii+2;
			}else {
				//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
				sheet.addMergedRegion(new CellRangeAddress(0, 1, ii, ii));
				ii=ii+1;
			}
			cell0.setCellValue(titlename[i]);
			cell0.setCellStyle(cs);
		}

		XSSFRow row1 = sheet.createRow(1);
		XSSFCell cell1 = row1.createCell((short)4);
		cell1.setCellValue("������������ĸ����������");
		cell1.setCellStyle(cs);
		cell1 = row1.createCell((short)5);
		cell1.setCellValue("��Ǽ�����������ĸ����������");
		cell1.setCellStyle(cs);
		cell1 = row1.createCell((short)6);
		cell1.setCellValue("�������������Ͷ�ߴ���");
		cell1.setCellStyle(cs);
		cell1 = row1.createCell((short)7);
		cell1.setCellValue("��Ǽ������������Ͷ�ߴ���");
		cell1.setCellStyle(cs);
		
		//��������
		if (paymentManageList != null && paymentManageList.size()>0) {
			XSSFRow row = null;
			XSSFCell cell =null;
			for (int j = 0; j < paymentManageList.size(); j++) {
				HashMap map=(HashMap) paymentManageList.get(j);
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(j+2);
				for(int c=0;c<titleid.length;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				    if(titleid[c].equals("hfauditnum") || titleid[c].equals("hfauditnum2") 
				    		|| titleid[c].equals("rxauditnum") || titleid[c].equals("rxauditnum2")){
				    	String doustr=(String)map.get(titleid[c]);
				    	if(doustr!=null && !doustr.trim().equals("") 
				    			&& !doustr.trim().equals("null") && !doustr.trim().equals("NULL")){
				    		cell.setCellValue(Double.parseDouble(doustr));
				    	}else{
				    		cell.setCellValue(doustr);
				    	}
				    	
				    }else{
				    	cell.setCellValue((String)map.get(titleid[c]));
				    }
				}
			}
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("�������", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
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
				Query query = hs.createQuery("from ContractPaymentManage where jnlNo = '"+id.trim()+"'");
				List list = query.list();
				if (list != null && list.size() > 0) {					
					// ����Ϣ
					ContractPaymentManage master = (ContractPaymentManage) list.get(0);															
					ContractTicketManage ctm=(ContractTicketManage) hs.get(ContractTicketManage.class, master.getCtJnlNo());
					EntrustContractMaster ecm=new EntrustContractMaster();
					String sql="from EntrustContractMaster where billno='"+master.getBillNo()+"'";
					List list1=hs.createQuery(sql).list();
					double contractTotal =0;
					if(list1!=null && list1.size()>0){
						ecm=(EntrustContractMaster) list1.get(0);
						contractTotal =ecm.getContractTotal();
					}
					request.setAttribute("contractTotal", contractTotal);
					double invoiceMoney =ctm.getInvoiceMoney();			
					String hql="select isnull(sum(paragraphMoney),0) from ContractPaymentManage where entrustContractNo='"+ecm.getEntrustContractNo()+"'";
					List list2=hs.createSQLQuery(hql).list();
					double invoiceReceivables=0.0;
					if(list2!=null&&list2.size()>0)
					{
						invoiceReceivables=Double.valueOf(list2.get(0).toString());
					}	
					
					/*if(master.getAuditOperid()!=null && !master.getAuditOperid().equals("")){
						master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid", master.getAuditOperid()));
					}*/
					if("display".equals(flag)){
						master.setMaintDivision(bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));// ά���ֲ�	
						master.setCompanyId(bd.getName_Sql("Customer", "companyName", "companyId", master.getCompanyId()));
						master.setOperId(bd.getName(hs, "Loginuser", "username", "userid", master.getOperId()));
						hql="select isnull(sum(paragraphMoney),0) from ContractPaymentManage where entrustContractNo='"+ecm.getEntrustContractNo()+"'";
					} else {						
						request.setAttribute("companyName", bd.getName(hs,"Customer", "companyName", "companyId", master.getCompanyId()));
						request.setAttribute("maintDivisionName",bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision())); // ά���ֲ�����
						request.setAttribute("operName", bd.getName(hs, "Loginuser", "username", "userid", master.getOperId()));
						request.setAttribute("receivablesList", this.getReceivablesNameList());
					    //�޸�ʱ��sumѡ�м�¼Ӧ�����
						hql="select isnull(sum(paragraphMoney),0) from ContractPaymentManage where jnlNo!="+id.trim()+" and entrustContractNo='"+ecm.getEntrustContractNo()+"'";		
					}
					double builtReceivables=0.0;
					List preMoneyList=hs.createSQLQuery(hql).list();
					if(preMoneyList!=null&&preMoneyList.size()>0)
					{
					  builtReceivables=Double.valueOf(preMoneyList.get(0).toString());
					}
					
					//����������Ϣ
					query = hs.createQuery("from ContractPaymentProcess where jnlNo = '"+ master.getJnlNo() + "' order by itemId");
					List processApproveList = query.list();
					for (Object object : processApproveList) {
						ContractPaymentProcess process = (ContractPaymentProcess) object;
						process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
					}
					request.setAttribute("processApproveList",processApproveList);
					
					request.setAttribute("paymentManageBean", master);	
					request.setAttribute("ctjnlNo", ctm.getJnlNo());
					request.setAttribute("invoiceNo", ctm.getInvoiceNo());
					request.setAttribute("invoiceType", bd.getName(hs, "InvoiceType", "inTypeName", "inTypeId", ctm.getInvoiceType()));
					request.setAttribute("invoiceReceivables", invoiceReceivables);
					request.setAttribute("noInvoiceReceivables", contractTotal-invoiceReceivables);		
					request.setAttribute("builtReceivables", builtReceivables);
					request.setAttribute("noBuiltReceivables", invoiceMoney-builtReceivables);	
					request.setAttribute("invoiceMoney", invoiceMoney);
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
		ContractPaymentManage master = null;
		String id = (String) dform.get("id");
		String submitType=(String)dform.get("submitType");
		String ctjnlNo=(String)dform.get("ctJnlNo");
		String jnlNo = null;
		JbpmExtBridge jbpmExtBridge=null;
		String userid = userInfo.getUserID(); //��ǰ��¼�û�id
		Session hs = null;
		Transaction tx = null;
		
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			//ContractTicketManage ctm=(ContractTicketManage) hs.get(ContractTicketManage.class, ctjnlNo);
			if (id != null && !id.equals("")) { // �޸�		
				master = (ContractPaymentManage) hs.get(ContractPaymentManage.class, id);
				jnlNo = master.getJnlNo();
			} else { // ����
				master = new ContractPaymentManage();	
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				jnlNo = CommonUtil.getBillno(year,"ContractPaymentManage", 1)[0];// ������ˮ��
			}
			BeanUtils.populate(master, dform.getMap()); // ������������
			if(id==null || "".equals(id))
				master.setParagraphNo(this.getParagraphNo());
			
			master.setJnlNo(jnlNo);// ��ˮ��	
			master.setCtJnlNo(ctjnlNo);
			master.setSubmitType(submitType);
			
			if(submitType!=null && "Y".equals(submitType)){
				String processDefineID = Grcnamelist1.getProcessDefineID("contractpaymentmanage", master.getOperId());// ���̻���id
				if(processDefineID == null || processDefineID.equals("")){
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.string","<font color='red'>δ��������������Ϣ������������</font>"));
					throw new Exception();
				}
							
				/**=============== ����������ʵ����ʼ ===================**/
				HashMap paraMap = new HashMap();
				jbpmExtBridge=new JbpmExtBridge();
				ProcessBean pd = null;		
				pd = jbpmExtBridge.getPb();

				Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "��������������", "%");// ��������
				
				pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,id,"",paraMap);
				/**==================== ���̽��� =======================**/
				master.setProcessName(pd.getNodename());// ��������
				master.setStatus(pd.getStatus()); //����״̬
				master.setTokenId(pd.getToken());//��������
			}else{
				master.setProcessName("");// ��������
				master.setStatus(-1); //����״̬
				master.setTokenId(0l);//��������
			}
			
//			master.setAuditStatus("N");
//			master.setContractTicketManage(ctm);
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
			if (jbpmExtBridge != null) {
				jbpmExtBridge.setRollBack();
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
			if(jbpmExtBridge!=null){
				jbpmExtBridge.close();
			}
		}
		
	}
		
	public void refer(ActionForm form, HttpServletRequest request,ActionErrors errors){
		HttpSession httpsession = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)httpsession.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id = request.getParameter("id"); 
		
		if(id != null && !id.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			JbpmExtBridge jbpmExtBridge=null;
			String userid = userInfo.getUserID(); //��ǰ��¼�û�id
			ContractPaymentManage master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (ContractPaymentManage) hs.get(ContractPaymentManage.class, id);
				//master.setSubmitType("Y");
				String processDefineID = Grcnamelist1.getProcessDefineID("contractpaymentmanage", master.getOperId());// ���̻���id
				if(processDefineID == null || processDefineID.equals("")){
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.string","<font color='red'>δ��������������Ϣ������������</font>"));
					throw new Exception();
				}
							
				/**=============== ����������ʵ����ʼ ===================**/
				HashMap paraMap = new HashMap();
				jbpmExtBridge=new JbpmExtBridge();
				ProcessBean pd = null;		
				pd = jbpmExtBridge.getPb();

				Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "��������������", "%");// ��������
				
				pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,id,"",paraMap);
				/**==================== ���̽��� =======================**/
				
				master.setSubmitType("Y");// �ύ��־
				master.setProcessName(pd.getNodename());// ��������
				master.setStatus(pd.getStatus()); //����״̬
				master.setTokenId(pd.getToken());//��������
				hs.save(master);
				
				tx.commit();
			} catch (Exception e) {	
				try {
					tx.rollback();
				} catch (HibernateException e3) {
					e3.printStackTrace();
				}
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
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
			
		}		
	} 
	public String getParagraphNo(){
		String paragraphNo="";
		String prefix = "";
		String suffix = "";
		String year = CommonUtil.getNowTime("yyyy");
		String month=CommonUtil.getNowTime("MM");
		
		prefix = year+month;
		paragraphNo=CommonUtil.genNo("ContractPaymentManage", "paragraphNo", prefix, suffix, 3,"paragraphNo");
		return paragraphNo;
		
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
		
		//inserInformDate(form, request, errors, jnlNo);
		if(jnlNo!=null && !jnlNo.equals("")){
			try {
				hs=HibernateUtil.getSession();
				manage=(ContractTicketManage) hs.get(ContractTicketManage.class, jnlNo);
				String sql="select company.comfullname,ctm.entrustContractNo,c.companyName,c.bank,c.account,cpm.ParagraphMoney-ISNULL(cpm.DebitMoney,0) from ContractTicketManage ctm,ContractPaymentManage cpm,Customer c,Company company where c.companyId=ctm.companyId and company.comid=ctm.maintDivision and ctm.jnlNo=cpm.CT_JnlNo and cpm.jnlNo='"+jnlNo.trim()+"'";
				List list=hs.createSQLQuery(sql).list();
				if(list!=null&&list.size()>0){
					Map map=new HashMap();
					Object[] objs=(Object[]) list.get(0);
					map.put("comfullname", objs[0]);//�����ֲ�
					map.put("entrustContractNo", objs[1]);//ί�к�ͬ��
					map.put("companyName", objs[2]);//�ͻ���λ����
					map.put("bank", objs[3]);//�ͻ���������
					map.put("account", objs[4]);//�ͻ����к���
					map.put("invoiceMoney", objs[5]);//��Ʊ���
					BigDecimal numberOfMoney=(BigDecimal) objs[5];//��Ʊ���תΪ��ȷ������
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