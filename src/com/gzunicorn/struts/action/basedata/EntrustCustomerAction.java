package com.gzunicorn.struts.action.basedata;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.hibernate.criterion.Restrictions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
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

import com.gzunicorn.common.dataimport.importutil.ComUtil;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.hibernate.basedata.city.City;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.basedata.principal.Principal;
import com.gzunicorn.hibernate.basedata.region.Region;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class EntrustCustomerAction extends DispatchAction {

	Log log = LogFactory.getLog(EntrustCustomerAction.class);
	
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
				SysRightsUtil.NODE_ID_FORWARD + "entrustcustomer", null);
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

		request.setAttribute("navigator.location","ί�е�λ���� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();
		
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		request.setAttribute("userroleid", userInfo.getRoleID());

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
			HTMLTableCache cache = new HTMLTableCache(session, "entrustCustomerList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fEntrustCustomer");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("companyId");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);

			String companyId = tableForm.getProperty("companyId");
			String companyName = tableForm.getProperty("companyName");
			String enabledFlag = tableForm.getProperty("enabledFlag");
			Session hs = null;

			try {

				hs = HibernateUtil.getSession();

				Criteria criteria = hs.createCriteria(Customer.class).add(Expression.eq("cusNature", "WT"));
				
				if (companyId != null && !companyId.equals("")) {
					criteria.add(Expression.like("companyId", "%" + companyId.trim()
							+ "%"));
				}
				if (companyName != null && !companyName.equals("")) {
					criteria.add(Expression.like("companyName", "%"
							+ companyName.trim() + "%"));
				}
				if (enabledFlag != null && !enabledFlag.equals("")) {
					criteria.add(Expression.eq("enabledFlag", enabledFlag));
				}

				if (table.getIsAscending()) {
					criteria.addOrder(Order.asc(table.getSortColumn()));
				} else {
					criteria.addOrder(Order.desc(table.getSortColumn()));
				}
				table.setVolume(criteria.list().size());// ��ѯ�ó����ݼ�¼��;
				
				// �ó���һҳ�����һ����¼����;
				criteria.setFirstResult(table.getFrom()); // pagefirst
				criteria.setMaxResults(table.getLength());

				cache.check(table);

				List entrustCustomerList = criteria.list();

				table.addAll(entrustCustomerList);
				session.setAttribute("entrustCustomerList", table);

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
			forward = mapping.findForward("entrustCustomerList");
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
		
		request.setAttribute("navigator.location","ί�е�λ���� >> �鿴");
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		if(id==null||id.equals("")){
			id = (String) dform.get("id");
		}
		
		//String id = (String) dform.get("id");
		
		Session hs = null;
		Customer customer = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				customer = (Customer) hs.get(Customer.class, id);

				if (customer == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"customer.display.recordnotfounterror"));
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

			request.setAttribute("display", "yes");
			request.setAttribute("entrustCustomerBean", customer);
			forward = mapping.findForward("entrustCustomerDisplay");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
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

		request.setAttribute("navigator.location","ί�е�λ���� >> ���");
		
		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
			dform.set("enabledFlag", "Y");
		}
		dform.set("enabledFlag", "Y");

		
        Session hs = null;
		
		try{
			hs = HibernateUtil.getSession();
		   String companyId ="WT";
		   final String STR_FORMAT = "000000";   
		   String sql="select MAX(c.companyId) from Customer c where C.CusNature='WT' ";
		 //wt0001
		   List list=hs.createSQLQuery(sql).list();
		   String no=(String) list.get(0);
		   if(no!=null&&!no.equals(""))
		   {
			   if(no.length()==8){
			   Integer intHao = Integer.parseInt(no.substring(2, 8));  
			   intHao++;  
			   DecimalFormat df = new DecimalFormat(STR_FORMAT);  
			   companyId+=df.format(intHao);
			   }else{
				   companyId="WT000001"; 
			   }
		   }else
		   {
			   companyId="WT000001";   
		   } 
		request.setAttribute("companyId", companyId);
		dform.set("enabledFlag", "Y");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}
		//dform.set("enabledFlag", "Y");
		//request.setAttribute("enabledFlag","Y");
		return mapping.findForward("entrustCustomerAdd");
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
		
		String companyId = (String) dform.get("companyId");//��λ����
		String companyName = (String) dform.get("companyName");//��λ����
		String address = (String) dform.get("address");//��λ��ַ
		String legalPerson = (String) dform.get("legalPerson");//����
		String client = (String) dform.get("client");//ί����
		String contacts = (String) dform.get("contacts");//��ϵ��
		String contactPhone = (String) dform.get("contactPhone");//��ϵ�˵绰
		String principalId = (String) dform.get("principalId");//�����˴���
		String principalName = (String) dform.get("principalName");//����������
		String principalPhone = (String) dform.get("principalPhone");//�����˵绰
		String fax = (String) dform.get("fax");//����
		String postCode = (String) dform.get("postCode");//�ʱ�
		String bank = (String) dform.get("bank");//Bank
		String accountHolder = (String) dform.get("accountHolder");//��������
		String account = (String) dform.get("account");//�����ʺ�
		String taxId = (String) dform.get("taxId");//˰��
		String enabledFlag = (String) dform.get("enabledFlag");//���ñ�־
		String rem = (String) dform.get("rem");//��ע
		String invoiceName = (String) dform.get("invoiceName");//��Ʊ����
		String qualiLevelAz=(String)dform.get("qualiLevelAz");
		String qualiLevelWb=(String)dform.get("qualiLevelWb");
		String qualiLevelWg=(String)dform.get("qualiLevelWg");
	

		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();

			String sql="from Customer where companyName='"+companyName.trim()+"' and cusNature='WT' ";
			List sqlist=hs.createQuery(sql).list();
			if(sqlist!=null && sqlist.size()>0){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>��λ���� �Ѿ����ڣ�����ʧ�ܣ�</font>"));
			}else{
				Customer customer = new Customer();
				customer.setCompanyId(companyId);
				customer.setCompanyName(companyName);
				customer.setAddress(address);
				customer.setLegalPerson(legalPerson);
				customer.setClient(client);
				customer.setContacts(contacts);
				customer.setContactPhone(contactPhone);
				customer.setPrincipalId(principalId);		
				customer.setPrincipalName(principalName);
				customer.setPrincipalPhone(principalPhone);
				customer.setFax(fax);
				customer.setPostCode(postCode);		
				customer.setBank(bank);
				customer.setAccountHolder(accountHolder);
				customer.setAccount(account);
				customer.setTaxId(taxId);
				customer.setEnabledFlag(enabledFlag);
				customer.setRem(rem);
				customer.setCusNature("WT");
				customer.setInvoiceName(invoiceName);//��Ʊ����
				customer.setOperId(userInfo.getUserID());//¼����
				customer.setOperDate(CommonUtil.getToday());//¼��ʱ��
				customer.setQualiLevelAz(qualiLevelAz);
				customer.setQualiLevelWb(qualiLevelWb);
				customer.setQualiLevelWg(qualiLevelWg);
	
				hs.save(customer);
				tx.commit();
			}
		} catch (HibernateException e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("customer.insert.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Insert error!");
		} catch (Exception e1) {
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
		String isreturn = request.getParameter("isreturn");
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
				"insert.success"));
				forward = mapping.findForward("returnList");
			} else {
				// return addnew page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"insert.success"));
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
		
		request.setAttribute("navigator.location","ί�е�λ���� >> �޸�");
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();

		ActionForward forward = null;
		String id = null;

		if (dform.get("isreturn") != null
				&& ((String) dform.get("isreturn")).equals("N")) {
			id = (String) dform.get("companyId");
		} else {
			id = (String) dform.get("id");
		}
		
		Session hs = null;
		Customer customer = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				customer = (Customer) hs.get(Customer.class, id);

				if (customer == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"customer.display.recordnotfounterror"));
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
					DebugUtil
							.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			request.setAttribute("entrustCustomerBean", customer);
			forward = mapping.findForward("entrustCustomerModify");
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
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
		Session hs = null;
		Transaction tx = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String companyId = (String) dform.get("companyId");//��λ����
		String companyName = (String) dform.get("companyName");//��λ����
		String address = (String) dform.get("address");//��λ��ַ
		String legalPerson = (String) dform.get("legalPerson");//����
		String client = (String) dform.get("client");//ί����
		String contacts = (String) dform.get("contacts");//��ϵ��
		String contactPhone = (String) dform.get("contactPhone");//��ϵ�˵绰
		String principalId = (String) dform.get("principalId");//�����˴���
		String principalName = (String) dform.get("principalName");//����������
		String principalPhone = (String) dform.get("principalPhone");//�����˵绰
		String fax = (String) dform.get("fax");//����
		String postCode = (String) dform.get("postCode");//�ʱ�
		String bank = (String) dform.get("bank");//Bank
		String accountHolder = (String) dform.get("accountHolder");//��������
		String account = (String) dform.get("account");//�����ʺ�
		String taxId = (String) dform.get("taxId");//˰��
		String enabledFlag = (String) dform.get("enabledFlag");//���ñ�־
		String rem = (String) dform.get("rem");//��ע
		String invoiceName = (String) dform.get("invoiceName");//��Ʊ����
		String qualiLevelAz=(String)dform.get("qualiLevelAz");
		String qualiLevelWb=(String)dform.get("qualiLevelWb");
		String qualiLevelWg=(String)dform.get("qualiLevelWg");
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			String sql="from Customer where companyName='"+companyName.trim()+"' and cusNature='WT' and companyId not in('"+companyId+"')";
			List sqlist=hs.createQuery(sql).list();
			if(sqlist!=null && sqlist.size()>0){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>��λ���� �Ѿ����ڣ�����ʧ�ܣ�</font>"));
			}else{
				Customer customer = (Customer) hs.get(Customer.class, (String) dform.get("id"));
				
				customer.setCompanyId(companyId);
				customer.setCompanyName(companyName);
				customer.setAddress(address);
				customer.setLegalPerson(legalPerson);
				customer.setClient(client);
				customer.setContacts(contacts);
				customer.setContactPhone(contactPhone);
				customer.setPrincipalId(principalId);		
				customer.setPrincipalName(principalName);
				customer.setPrincipalPhone(principalPhone);
				customer.setFax(fax);
				customer.setPostCode(postCode);		
				customer.setBank(bank);
				customer.setAccountHolder(accountHolder);
				customer.setAccount(account);
				customer.setTaxId(taxId);
				customer.setEnabledFlag(enabledFlag);
				customer.setRem(rem);
				customer.setCusNature("WT");
				customer.setInvoiceName(invoiceName);
				customer.setOperId(userInfo.getUserID());//¼����
				customer.setOperDate(CommonUtil.getToday());//¼��ʱ��
				customer.setQualiLevelAz(qualiLevelAz);
				customer.setQualiLevelWb(qualiLevelWb);
				customer.setQualiLevelWg(qualiLevelWg);
	
				hs.save(customer);
	
				tx.commit();
			}
		} catch (HibernateException e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("customer.update.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Update error!");
		} catch (Exception e1) {
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

		ActionForward forward = null;
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

			Customer customer = (Customer) hs.get(Customer.class, (String) dform.get("id"));
			if (customer != null) {
				List list=hs.createQuery("from EntrustContractQuoteMaster where companyId='"+customer.getCompanyId()+"'").list();
				if(list!=null && list.size()>0)
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.entrust.succeed"));
				else{
					hs.delete(customer);
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"delete.succeed"));
				}
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

		String companyId = tableForm.getProperty("companyId");//��λ����
		String companyName = tableForm.getProperty("companyName");//��λ����
		String enabledFlag = tableForm.getProperty("enabledFlag");//���ñ�־
		
		Session hs = null;
		XSSFWorkbook wb = new XSSFWorkbook();

		try {
			hs = HibernateUtil.getSession();

			Criteria criteria = hs.createCriteria(Customer.class).add(Expression.eq("cusNature", "WT"));
			if (companyId != null && !companyId.equals("")) {
				criteria.add(Expression.like("companyId", "%" + companyId.trim() + "%"));
			}
			if (companyName != null && !companyName.equals("")) {
				criteria.add(Expression.like("companyName", "%" + companyName.trim()
						+ "%"));
			}
			if (enabledFlag != null && !enabledFlag.equals("")) {
				criteria.add(Expression.eq("enabledFlag", enabledFlag));
			}

			criteria.addOrder(Order.asc("companyId"));

			List roleList = criteria.list();

			XSSFSheet sheet = wb.createSheet("ί�е�λ����");
			
			Locale locale = this.getLocale(request);
			MessageResources messages = getResources(request);

			if (roleList != null && !roleList.isEmpty()) {
				int l = roleList.size();
				XSSFRow row0 = sheet.createRow( 0);
				XSSFCell cell0 = row0.createCell((short)0);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.companyId"));
				
				cell0 = row0.createCell((short)1);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.companyName"));
				
				cell0 = row0.createCell((short)2);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.address"));

				cell0 = row0.createCell((short)3);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.invoiceName"));
		
				cell0 = row0.createCell((short)4);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.legalPerson"));
		
				cell0 = row0.createCell((short)5);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.client"));
				
				cell0 = row0.createCell((short)6);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.contacts"));
				
				cell0 = row0.createCell((short)7);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.contactPhone"));
				
				cell0 = row0.createCell((short)8);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"principal.principalId"));
				
				cell0 = row0.createCell((short)9);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"principal.principalName"));
				
				cell0 = row0.createCell((short)10);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"principal.phone"));
				
				cell0 = row0.createCell((short)11);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.fax"));
				
				cell0 = row0.createCell((short)12);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.postCode"));
				
				cell0 = row0.createCell((short)13);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.bank"));
				
				cell0 = row0.createCell((short)14);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.accountHolder"));

				cell0 = row0.createCell((short)15);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.account"));
				
				cell0 = row0.createCell((short)16);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.taxId"));
				
				cell0 = row0.createCell((short)17);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.enabledFlag"));
				
				cell0 = row0.createCell((short)18);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.rem"));
				
				cell0 = row0.createCell((short)19);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue("��װ���ʼ���");
				
				cell0 = row0.createCell((short)20);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue("ά�����ʼ���");
				
				cell0 = row0.createCell((short)21);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue("ά�����ʼ���");
				
				cell0 = row0.createCell((short)22);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.operId"));
				
				cell0 = row0.createCell((short)23);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(messages.getMessage(locale,"customer.operDate"));
				
				for (int i = 0; i < l; i++) {
					Customer customer = (Customer) roleList.get(i);
					// ����Excel�У���0�п�ʼ
					XSSFRow row = sheet.createRow( i+1);
	
					// ����Excel��
					XSSFCell cell = row.createCell((short)0);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getCompanyId());
					
					cell = row.createCell((short)1);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getCompanyName());
					
					cell = row.createCell((short)2);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getAddress());
					
					cell = row.createCell((short)3);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getInvoiceName());
					
					cell = row.createCell((short)4);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getLegalPerson());
					
					cell = row.createCell((short)5);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getClient());
					
					cell = row.createCell((short)6);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getContacts());
					
					cell = row.createCell((short)7);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getContactPhone());
					
					cell = row.createCell((short)8);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getPrincipalId());
					
					cell = row.createCell((short)9);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getPrincipalName());
					
					cell = row.createCell((short)10);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getPrincipalPhone());
					
					cell = row.createCell((short)11);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getFax());
					
					cell = row.createCell((short)12);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getPostCode());
					
					cell = row.createCell((short)13);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getBank());
					
					cell = row.createCell((short)14);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getAccountHolder());
					
					cell = row.createCell((short)15);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getAccount());
					
					cell = row.createCell((short)16);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getTaxId());
					
					cell = row.createCell((short)17);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(CommonUtil.tranEnabledFlag(customer.getEnabledFlag()));
					
					cell = row.createCell((short)18);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getRem());
					
					cell = row.createCell((short)19);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getQualiLevelAz());
					
					cell = row.createCell((short)20);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getQualiLevelWb());
					
					cell = row.createCell((short)21);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getQualiLevelWg());

					cell = row.createCell((short)22);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(bd.getName(hs, "LoginUser", "userName", "userId",customer.getOperId()));

					cell = row.createCell((short)23);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(customer.getOperDate());

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
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("ί�е�λ����", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	
	/**
	 * ��ȡ�����˵��������б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPrincipalSelectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("navigator.location","");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
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
			HTMLTableCache cache = new HTMLTableCache(session, "principalList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fPrincipalSel");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("principalId");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);

			String principalName = tableForm.getProperty("principalName");
			String principalId = tableForm.getProperty("principalId");
			Session hs = null;
			try {
				hs = HibernateUtil.getSession();

				Criteria criteria = hs.createCriteria(Principal.class);
				criteria.add(Expression.eq("enabledFlag", "Y"));
				if (principalId != null && !principalId.equals("")) {
					criteria.add(Expression.like("principalId", "%" + principalId.trim()
							+ "%"));
				}
				if (principalName != null && !principalName.equals("")) {
					criteria.add(Expression.like("principalName", "%"
							+ principalName.trim() + "%"));
				}
				if (table.getIsAscending()) {
					criteria.addOrder(Order.asc(table.getSortColumn()));
				} else {
					criteria.addOrder(Order.desc(table.getSortColumn()));
				}
				table.setVolume(criteria.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				criteria.setFirstResult(table.getFrom()); // pagefirst
				criteria.setMaxResults(table.getLength());

				cache.check(table);

				List principalList = criteria.list();

				table.addAll(principalList);
				session.setAttribute("principalList", table);

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
			forward = mapping.findForward("principalEntrustSelect");
		}
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
	public ActionForward toPrepareImportRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.setAttribute("navigator.location","ί�е�λ���� >> ����");
		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		return mapping.findForward("entrustCustomerImport");
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
	
	public ActionForward toImportRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		FormFile file = (FormFile) dform.get("file"); //��ȡ�ϴ��ļ�
		String fileName = file.getFileName();
		String fileFromt=fileName.substring(
				fileName.lastIndexOf(".")+1,fileName.length()); //��ȡ�ϴ��ļ��ĺ�׺��
		
		InputStream in = null;
		Session hs = null;
		Transaction tx = null;
		StringBuffer reStr = new StringBuffer(); //���󷵻���Ϣ
	
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();

			if (fileFromt!=null && fileFromt.equals("xlsx")) {//excel 2007
				Customer master = null; //���ӵ�����Ϣ����
				List<Customer> list = new ArrayList<Customer>();
				
				in = file.getInputStream();
				XSSFWorkbook wb = new XSSFWorkbook(in);
				XSSFSheet sheet = wb.getSheetAt(0);
				XSSFRow row = null;			
				
				int rowSum = sheet.getLastRowNum()+1; //�������
				
				String id1="";
				String userid=userInfo.getUserID();
				String today=CommonUtil.getToday();
				
				   Integer intHao=0; 
				   final String STR_FORMAT = "000000";   
				   String sql="select MAX(c.companyId) from Customer c where C.CusNature='WT' ";
				   List companyIdList=hs.createSQLQuery(sql).list();
				   String no=(String) companyIdList.get(0);
				   if(no!=null&&!no.equals(""))
				   {
					   if(no.length()==8)
					   {intHao = Integer.parseInt(no.substring(2, 8));}
				   }
				   
				for(int rowNum = 1; rowNum < rowSum; rowNum++){					
					row = sheet.getRow(rowNum);				
					master = new Customer();
					//String companyId=cellValueToString(row, 0, reStr);//��λ����
				     String companyName=cellValueToString(row, 0, reStr);//��λ����
				     String address=cellValueToString(row, 1, reStr);//��λ��ַ
				     String invoiceName=cellValueToString(row, 2, reStr);//��Ʊ����
				     String legalPerson=cellValueToString(row, 3, reStr);//����
				     String client=cellValueToString(row, 4, reStr);//ί����
				     String contacts=cellValueToString(row, 5, reStr);//��ϵ��
				     String contactPhone=cellValueToString(row, 6, reStr);//��ϵ�˵绰
				     String principalName=cellValueToString(row, 7, reStr);//����������
				     String principalID=bd.getName(hs, "Loginuser", "userid", "username", principalName);//�����˴���
				     String principalPhone=cellValueToString(row, 8, reStr);//�����˵绰
				     String fax=cellValueToString(row, 9);//����
				     String postCode=cellValueToString(row, 10);//�ʱ�
				     String bank=cellValueToString(row, 11);//��������
				     String accountHolder=cellValueToString(row, 12);//��������
				     String account=cellValueToString(row, 13);//�����ʺ�
				     String taxId=cellValueToString(row, 14);//˰��  
				     String qualiLevel_AZ=cellValueToString(row, 15);//��װ���ʼ���
				     String qualiLevel_WB=cellValueToString(row, 16);//ά�����ʼ���
				     String qualiLevel_WG=cellValueToString(row, 17);//ά�����ʼ���
				     String rem=cellValueToString(row, 18);//��ע
                    /* ���ɵ�λ���� */
				   
				     
					//master.setCompanyId(companyId);
					master.setCompanyName(companyName);
					master.setAddress(address);
					master.setInvoiceName(invoiceName);
					master.setLegalPerson(legalPerson);
					master.setClient(client);
					master.setContacts(contacts);
					master.setContactPhone(contactPhone);
					master.setPrincipalId(principalID);
					master.setPrincipalName(principalName);
					master.setPrincipalPhone(principalPhone);
					master.setFax(fax);
					master.setPostCode(postCode);
					master.setBank(bank);
					master.setAccountHolder(accountHolder);
					master.setAccount(account);
					master.setTaxId(taxId);
					master.setQualiLevelAz(qualiLevel_AZ);
					master.setQualiLevelWb(qualiLevel_WB);
					master.setQualiLevelWg(qualiLevel_WG);
					master.setRem(rem);
					master.setOperId(userid);
					master.setOperDate(today);
					master.setEnabledFlag("Y");
					master.setCusNature("WT");
					if(reStr != null && reStr.length() > 0){
						break;
					}
					list.add(master);	
					id1 +=  rowNum < rowSum-1 ? "'" + companyName + "'," : "'" + companyName + "'";
				}
	
				if(reStr == null || reStr.length() == 0){
					String hql = "select companyId,companyName from Customer where cusNature='WT' and companyName in ("+id1+")"; //ɾ���ɰ汾����
				    List CustomerList = hs.createQuery(hql).list(); 
					for (Customer customer : list) {
						if(CustomerList!=null&&CustomerList.size()>0){
						 for(int i=0;i<CustomerList.size();i++){
							Object[] object=(Object[]) CustomerList.get(i);
							String companyId=(String) object[0];
							String companyName=(String) object[1];
							if(customer.getCompanyName().trim().equals(companyName.trim()))
							{customer.setCompanyId(companyId);
							  hs.update(customer); 
							  hs.flush();
							  break;
							}
							if(i==CustomerList.size()-1)
							 {
								 intHao++;  
								 DecimalFormat df = new DecimalFormat(STR_FORMAT);  
								 String companyIdno ="WT";
								 companyIdno+=df.format(intHao);
								 customer.setCompanyId(companyIdno);
								hs.save(customer);
								hs.flush();	
							 }
						 }
					   }else{
						   intHao++;  
						   DecimalFormat df = new DecimalFormat(STR_FORMAT);  
						   String companyIdno ="WT";
						   companyIdno+=df.format(intHao);
						   customer.setCompanyId(companyIdno);
						   hs.save(customer);
						   hs.flush();
					   }
						hs.flush();
					}
				} else {
					request.setAttribute("reStr", reStr);//���󷵻���Ϣ
				}
			
			}

			tx.commit();
		} catch (Exception e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("customer.import.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Insert error!");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = mapping.findForward("returnImport");
		
		if (errors.isEmpty() && reStr.length() == 0) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
				
		} else {
			request.setAttribute("error", "Yes");
		}
			
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return forward;
	}
	
	
	
	/**
	 * ���ص�Ԫ���ַ���ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellValueToString(XSSFRow row, int cellNum, StringBuffer reStr){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) { 
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = String.valueOf((int) cell.getNumericCellValue());
		}else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ�ַ���;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();
	}
	/**
	 * ���ص�Ԫ���ַ���ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellValueToString(XSSFRow row, int cellNum){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		try{
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = String.valueOf((int) cell.getNumericCellValue());
		}
		}catch (Exception e){
			value = "";
		}
		return value.trim();
	}
	/**
	 * ���ص�Ԫ������ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public int cellValueToInt(XSSFRow row, int cellNum, StringBuffer reStr){
		int value = 0;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = (int) cell.getNumericCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ��ֵ;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value;
	}

	/**
	 * ����Ԫ������ת��Ϊ��д��ĸ
	 * @param cellNum ���� 
	 * @return char
	 */
	public char getCellChar(int cellNum){
		return (char) (cellNum + 65);
	}

}
