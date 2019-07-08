package com.gzunicorn.struts.action.query;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.basedata.markingitems.MarkingItems;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class SearchShouldExamineItemsAction extends DispatchAction {
Log log = LogFactory.getLog(SearchShouldExamineItemsAction.class);
	
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
		//SysRightsUtil.filterModuleRight(request, response, SysRightsUtil.NODE_ID_FORWARD + "", null);
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchRecord";
			return dispatchMethod(mapping, form, request, response, name);
		} else {
			ActionForward forward = super.execute(mapping, form, request, response);
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

		request.setAttribute("navigator.location","��ѯ >> ά��������鰲ȫ������");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session, "searchShouldExamineItemsList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fSearchShouldExamineItems");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		table.setSortColumn("seiid");
		table.setIsAscending(true);
		cache.updateTable(table);

		if (action.equals(ServeTableForm.NAVIGATE)
				|| action.equals(ServeTableForm.SORT)) {
			cache.loadForm(tableForm);
		} else {
			table.setFrom(0);
		}
		cache.saveForm(tableForm);

		String seiid = tableForm.getProperty("seiid");
		String seiDetail = tableForm.getProperty("seiDetail");
		
		String seiids=request.getParameter("QualityNos");
		if(seiids==null || seiids.trim().equals("")){
			seiids=tableForm.getProperty("QualityNos");
		}else{
			tableForm.setProperty("QualityNos", seiids);
		}
		
		Session hs = null;
		Query query = null;
		try {

			hs = HibernateUtil.getSession();

			String hql = "from ShouldExamineItems where enabledFlag='Y'";
			
			if(seiids!=null && !seiids.equals("")){
				hql+="and seiid not in ("+seiids.replace("|", "'")+")";
			}
			if (seiid != null && !seiid.equals("")) {
				hql += " and seiid like '%"+seiid.trim()+"%'";
			}
			if (seiDetail != null && !seiDetail.equals("")) {
				hql += " and seiDetail like '%"+seiDetail.trim()+"%'";
			}
			
			//System.out.println(table.getSortColumn());
			if (table.getIsAscending()) {
				hql += " order by "+ table.getSortColumn() +" asc";
			} else {
				hql += " order by "+ table.getSortColumn() +" desc";
			}

			query = hs.createQuery(hql);
			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

			// �ó���һҳ�����һ����¼����;
			query.setFirstResult(table.getFrom()); // pagefirst
			query.setMaxResults(table.getLength());

			cache.check(table);

			List shouldExamineItemsList = query.list();
			table.addAll(shouldExamineItemsList);
			session.setAttribute("searchShouldExamineItemsList", table);

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
		forward = mapping.findForward("searchShouldExamineItemsList");
		
		return forward;
	}
}
