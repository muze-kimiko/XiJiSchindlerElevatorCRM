package com.gzunicorn.struts.action.basedata;

import java.io.IOException;
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.logic.MakeUpXML;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.basedata.country.Country;
import com.gzunicorn.hibernate.basedata.province.Province;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class ProvinceAction extends DispatchAction {

	Log log = LogFactory.getLog(ProvinceAction.class);
	
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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "province", null);
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
//		this.setNavigation(request, "navigator.base.province.list");
		request.setAttribute("navigator.location","����ά��-ʡ�� >> ��ѯ�б�");
		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		if (tableForm.getProperty("genReport") != null
				&& !tableForm.getProperty("genReport").equals("")) {

		} else {
			HTMLTableCache cache = new HTMLTableCache(session, "provinceList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fProvince");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("provinceId");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);

			String provinceName = tableForm.getProperty("provinceName");
			String provinceId = tableForm.getProperty("provinceId");
			String enabledflag = tableForm.getProperty("enabledflag");
			String rem = tableForm.getProperty("rem");
			String countryName = tableForm.getProperty("country");
			Session hs = null;

			try {

				hs = HibernateUtil.getSession();

				Criteria criteria = hs.createCriteria(Province.class);

				if (provinceId != null && !provinceId.equals("")) {
					criteria.add(Expression.like("provinceId", "%" + provinceId.trim()+ "%"));
				}
				if (provinceName != null && !provinceName.equals("")) {
					criteria.add(Expression.like("provinceName", "%"+ provinceName.trim() + "%"));
				}
				if (enabledflag != null && !enabledflag.equals("")) {
					criteria.add(Expression.eq("enabledFlag", enabledflag));
				}
				if (rem != null && !rem.equals("")) {
					criteria.add(Expression.eq("rem", rem));
				}
				if (countryName != null && !countryName.equals("")) {
					//criteria.add(Expression.like("country.countryName", "%"+ countryName + "%"));
					criteria.createAlias("country", "c").add(Restrictions.like("c.countryName","%"+ countryName + "%")); 
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

				List provinceList = criteria.list();
				if (provinceList != null) {
					for (int i = 0; i < provinceList.size(); i++) {
						Province p = (Province)provinceList.get(i);
						p.getCountry().getCountryName();
					}
				}

				table.addAll(provinceList);
				session.setAttribute("provinceList", table);

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
			// request.setAttribute("viewareaidrefList",bd.getViewAreaRefList());
			forward = mapping.findForward("provinceList");
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

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();

//		this.setNavigation(request, "navigator.base.province.view");
		request.setAttribute("navigator.location","����ά��-ʡ�� >> �鿴");

		ActionForward forward = null;
		String id = (String) dform.get("id");
		Session hs = null;
//		Country country = null;
		Province province = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				province = (Province) hs.get(Province.class, (String) dform.get("id"));

				if (province == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"province.display.recordnotfounterror"));
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
			request.setAttribute("provinceBean", province);
			forward = mapping.findForward("provinceDisplay");

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

//		this.setNavigation(request, "navigator.base.province.add");
		request.setAttribute("navigator.location","����ά��-ʡ�� >> ���");
		
		DynaActionForm dform = (DynaActionForm) form;
		// dform.reset(mapping,null);
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
			dform.set("enabledflag", "Y");
		}
		dform.set("enabledflag", "Y");
		java.util.List cfresult = new java.util.ArrayList();
//		java.util.List result = new java.util.ArrayList();
		try{
			cfresult=bd.getCountryList("Y"); //����
		}
		catch (Exception e) {
			log.error(e.getMessage());
			DebugUtil.print(e, "Get CountryList error!");
		}
		request.setAttribute("countryList",cfresult);

		return mapping.findForward("provinceAdd");
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
		Session hs = null;
		Transaction tx = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
//			Country country = new Country();
			Province province = new Province();
			String provinceId = bd.genProvinceNum((String) dform.get("countryId"));
			province.setProvinceId(provinceId);
			province.setProvinceName((String) dform.get("provinceName"));
			province.setEnabledFlag((String) dform.get("enabledflag"));
			province.setRem((String) dform.get("rem"));
			Country country = (Country) hs.get(Country.class, (String) dform.get("countryId"));
			province.setCountry(country);
			hs.save(province);
			tx.commit();
		} catch (HibernateException e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"province.insert.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate Province Insert error!");
		} catch (DataStoreException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate Province Insert error!");
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
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
//		this.setNavigation(request, "navigator.base.province.modify");
		request.setAttribute("navigator.location","����ά��-ʡ�� >> �޸�");
		
		ActionForward forward = null;
		String id = null;

		if (dform.get("isreturn") != null
				&& ((String) dform.get("isreturn")).equals("N")) {
			id = (String) dform.get("provinceId");
		} else {
			id = (String) dform.get("id");
		}

		Session hs = null;
		if (id != null) {
			if (request.getAttribute("error") == null
					|| request.getAttribute("error").equals("")) {
				try {
					hs = HibernateUtil.getSession();
					Query query = hs
							.createQuery("from Province p where p.provinceId = :provinceId");
					query.setString("provinceId", id);
					java.util.List list = query.list();
					if (list != null && list.size() > 0) {
//						Country country = (Country) list.get(0);
						Province province = (Province) list.get(0);
						dform.set("id", province.getProvinceId());
						dform.set("provinceId", province.getProvinceId());
						dform.set("provinceName", province.getProvinceName());
						dform.set("countryId", province.getCountry().getCountryId());
						dform.set("enabledflag", province.getEnabledFlag());
						dform.set("rem", province.getRem());
					} else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								"display.recordnotfounterror"));
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
			}
			request.setAttribute("countryList", bd.getCountryList("Y"));
			forward = mapping.findForward("provinceModify");
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
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
//			Country country = (Country) hs.get(Country.class, (String) dform.get("id"));
			Province province = (Province) hs.get(Province.class, (String) dform.get("id"));
			if (dform.get("id") != null
					&& dform.get("provinceId") != null
					&& !((String) dform.get("id")).equals((String) dform.get("provinceId"))) {
				hs.delete(province);
				province = new Province();
			}
			province.setProvinceId((String) dform.get("provinceId"));
			province.setProvinceName((String) dform.get("provinceName"));
			province.setEnabledFlag((String) dform.get("enabledflag"));
			province.setRem((String) dform.get("rem"));
			Country country = (Country) hs.get(Country.class, (String) dform.get("countryId"));
			province.setCountry(country);
			hs.save(province);

			tx.commit();
		} catch (HibernateException e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"province.update.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate Country Update error!");
		} catch (DataStoreException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate Country Update error!");
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
				"update.success"));
				forward = mapping.findForward("returnList");
			} else {
				// return modify page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"update.success"));
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
//			Country country = (Country) hs.get(Country.class, (String) dform.get("id"));
			Province province = (Province) hs.get(Province.class, (String) dform.get("id"));
			if (province != null) {
				hs.delete(province);
				 errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"delete.succeed"));
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
			DebugUtil.print(e2, "Hibernate Province Update error!");
		} catch (DataStoreException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate Province Update error!");

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
	
	@SuppressWarnings("unchecked")
	/**
	 * ajax���ݹ���ID����ʡ���б�
	 */
	public ActionForward toProvinceSort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String roots = "tables";

		MakeUpXML makexml = new MakeUpXML(roots);
		String key = request.getParameter("key");
		if (key == null || "".equals(key))
			return null;

		List provinceList = bd.getProvinceList("Y", key);
		for (int i = 0; i < provinceList.size(); i++) {
			Province prov = (Province) provinceList.get(i);
			if (prov != null) {
				makexml.setCol(String.valueOf(i), "provinceId", prov.getProvinceId());
				makexml.setCol(String.valueOf(i), "provinceName", prov.getProvinceName());
			}
		}
		String xmlStr = makexml.getXml();
		response.setContentType("application/xml;charset=GBK");
		try {
			response.getWriter().write(xmlStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
