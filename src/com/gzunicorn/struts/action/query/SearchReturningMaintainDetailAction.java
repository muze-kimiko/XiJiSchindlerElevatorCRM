package com.gzunicorn.struts.action.query;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.basedata.elevatorsales.ElevatorSalesInfo;
import com.gzunicorn.hibernate.basedata.markingitems.MarkingItems;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractdetail.EntrustContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractmaster.EntrustContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractquotedetail.EntrustContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractquotemaster.EntrustContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class SearchReturningMaintainDetailAction extends DispatchAction {
Log log = LogFactory.getLog(SearchReturningMaintainDetailAction.class);
	
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

		request.setAttribute("navigator.location","��ѯ >> �طÿͻ�ά����ϸ");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session, "searchReturningMaintainDetailList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fSearchReturningMaintainDetail");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		cache.updateTable(table);
		table.setSortColumn("billNo");
		cache.updateTable(table);
		//table.setIsAscending(true);
		//cache.updateTable(table);
		
		int location = 0;//��ǰҳ��
		if (action.equals(ServeTableForm.NAVIGATE)
				|| action.equals(ServeTableForm.SORT)) {
			cache.loadForm(tableForm);
			location = table.getFrom();
		} else {
			table.setFrom(0);
			location = 0;
		}
		cache.saveForm(tableForm);
		request.setAttribute("location", location);
		

		String billNo = tableForm.getProperty("billNo");// ά����ˮ��
		String attn = tableForm.getProperty("attn");// ������
		String maintDivision = tableForm.getProperty("maintDivision");// ά���ֲ�
		String maintContractNo = tableForm.getProperty("maintContractNo");// ��ͬ��

		String salesContractNo=tableForm.getProperty("salesContractNo");//���ۺ�ͬ��
		String contracttype=tableForm.getProperty("contracttype");//��ͬ����
		String salesContractNoStr="";
		//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
		List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
		if(maintDivision == null || maintDivision.equals("")){
			Map map = (Map)maintDivisionList.get(0);
			maintDivision = (String)map.get("grcid");
		}
		
		String wbBillnos=request.getParameter("wbBillnos1");
		
		if(wbBillnos==null || wbBillnos.trim().equals("")){
			wbBillnos=tableForm.getProperty("wbBillnos1");
		}else{
			tableForm.setProperty("wbBillnos1", wbBillnos);
		}
		String wtBillnos=request.getParameter("wtBillnos");
		
		if(wtBillnos==null || wtBillnos.trim().equals("")){
			wtBillnos=tableForm.getProperty("wtBillnos");
		}else{
			tableForm.setProperty("wtBillnos", wtBillnos);
		}
		
//		if(wbBillnos!=null && !wbBillnos.equals("")){
//			wbBillnos="'"+wbBillnos.replace("|", "'")+"'";
//		}
		String orderby="";
		
		Session hs = null;
		Connection conn = null;
		try {

			hs = HibernateUtil.getSession();
			
			if (table.getIsAscending()) {
				orderby= "order by "+ table.getSortColumn() +" asc";
			} else {
				orderby= "order by "+ table.getSortColumn() +" desc";
			}
			
			String sql="exec sp_SearchReturningMaintainDetail '"+billNo+"','"+attn+"','"+maintDivision+"','"+maintContractNo+"','"+
			salesContractNo+"','"+wbBillnos+"','"+wtBillnos+"','"+contracttype+"','"+orderby+"'";
			
			System.out.println(sql);
            
//			if (salesContractNo != null && !salesContractNo.equals("")) {
//				String salesContractNohql = "select distinct ecd.entrustContractMaster.billNo from EntrustContractDetail ecd where ecd.salesContractNo like '%"+salesContractNo.trim()+"%'";
//				List salesContractNoList=hs.createQuery(salesContractNohql).list();
//			   if(salesContractNoList!=null&&salesContractNoList.size()>0){
//				   for(int i=0;i<salesContractNoList.size();i++){
//					   salesContractNoStr+=i==salesContractNoList.size()-1?"'"+salesContractNoList.get(i)+"'":"'"+salesContractNoList.get(i)+"',";
//				   }
//			   }
//			
//			}
//			String hql = "select e,b.username,c.comname"+
//						" from EntrustContractMaster e,Loginuser b,Company c" + 
//						" where e.attn = b.userid and e.maintDivision = c.comid"+
//						" and e.auditStatus='Y'"+
//						" and e.r1 in('ZB','XB')"+
//						" and e.billNo not in(select wbBillno from ReturningMaintainDetail)";
//			
//			if(wbBillnos!=null && !wbBillnos.equals("")){
//				hql+=" and e.billNo not in ("+wbBillnos.replace("|", "'")+")";
//			}
//			if (billNo != null && !billNo.equals("")) {
//				hql += " and e.billNo like '%"+billNo.trim()+"%'";
//			}
//			if (attn != null && !attn.equals("")) {
//				hql += " and (e.attn like '%"+attn.trim()+"%' or b.username like '%"+attn.trim()+"%')";
//			}
//			if (maintDivision != null && !maintDivision.equals("")) {
//				hql += " and e.maintDivision like '"+maintDivision.trim()+"'";
//			}
//			if (maintContractNo != null && !maintContractNo.equals("")) {
//				hql += " and e.entrustContractNo like '%"+maintContractNo.trim()+"%'";
//			}
//			if(salesContractNo != null && !salesContractNo.equals("")){
//				if (salesContractNoStr != null && !salesContractNoStr.equals("")) {
//					hql += " and e.billNo in ("+salesContractNoStr+")";
//				}else{
//					hql += " and e.billNo = ''";
//				}
//			}
//
//			
//			if (table.getIsAscending()) {
//				hql += " order by "+ table.getSortColumn() +" asc";
//			} else {
//				hql += " order by "+ table.getSortColumn() +" desc";
//			}

//			query = hs.createSQLQuery(sql);
//			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;
//
//			// �ó���һҳ�����һ����¼����;
//			query.setFirstResult(table.getFrom()); // pagefirst
//			query.setMaxResults(table.getLength());
//
//			cache.check(table);
//
//			List list = query.list();
//			List returningMaintainDetailList = new ArrayList();
//			for (Object object : list) {
//				Object[] objs = (Object[])object;
//				Map map = BeanUtils.describe(objs[0]); // MaintContractMaster
//				map.put("attn", String.valueOf(objs[1])); // ����������
//				map.put("maintDivision", String.valueOf(objs[2])); // �ֲ�����
//				returningMaintainDetailList.add(map);
//			}

			DataOperation op = new DataOperation();
			conn = (Connection) hs.connection();
			op.setCon(conn);
			
			List returningMaintainDetailList = new ArrayList();
			List returningMaintainDetailLis1t = new ArrayList();
			
			returningMaintainDetailList = op.queryToList(sql);
			
			if(returningMaintainDetailList!=null){
				
				int totalCnt = returningMaintainDetailList.size();
				int expireCnt = 0; //δ�鵵�������������г��ڵĵ�
				
				int navigatelen = table.getLength();//��ҳ����
				int location1 = (Integer) request.getAttribute("location");//��ǰҳ��
				
				int start = location1;
	            int end = location1 + navigatelen;
	            if (totalCnt<end) {
					end = totalCnt;
				}
	            
	            for (int i = start ; i < end ; i ++){
	            	HashMap map = (HashMap) returningMaintainDetailList.get(i);           		
	            	if((i>=table.getFrom())&&(i<(table.getFrom() + table.getLength()))){
	            		returningMaintainDetailLis1t.add(map);	
	    			}
	            }	
	            
//	            NumberFormat numberFormat = NumberFormat.getInstance();
//				numberFormat.setMaximumFractionDigits(2);
//				String percent  = "0%";
//				if(totalCnt!=0){
//					percent = numberFormat.format((float)expireCnt/(float)totalCnt*100) + "%";
//				}
				table.setVolume(totalCnt);
				table.addAll(returningMaintainDetailLis1t);
			}
			
			session.setAttribute("searchReturningMaintainDetailList", table);
			// �ֲ��������б�
			request.setAttribute("maintDivisionList", maintDivisionList);

		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				hs.close();
			} catch (Exception hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		forward = mapping.findForward("searchReturningMaintainDetailList");
		
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

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		request.setAttribute("navigator.location","ί�к�ͬ��Ϣ >> �鿴");

		ActionForward forward = null;
		String id = (String) dform.get("id");
		String isOpen=request.getParameter("isOpen");
		Session hs = null;
		EntrustContractMaster master  = null;
		List maintContractDetailList = null;
		List detailList=new ArrayList();
		
		List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// �������������б�
		
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				if (id != null && !id.equals("")) {			
					try {
						hs = HibernateUtil.getSession();
						
						//ת����������
						id=new String(id.getBytes("ISO-8859-1"),"gbk");
						
						Query query = hs.createQuery("from EntrustContractMaster where entrustContractNo = '"+id.trim()+"'");
						List list = query.list();
						if (list != null && list.size() > 0) {

							// ����Ϣ
							master = (EntrustContractMaster) list.get(0);															
							master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid",master.getAuditOperid()));//�����
							master.setMaintDivision(bd.getName("Company","comname","comid",master.getMaintDivision()));
							master.setMaintStation(bd.getName("Storageid", "storagename", "storageid", master.getMaintStation()));
							master.setAttn(bd.getName(hs, "Loginuser","username", "userid",master.getAttn()));// ������
							master.setOperId(bd.getName("Loginuser", "username", "userid", master.getOperId()));
							master.setContractNatureOf(bd.getName_Sql("Pulldown", "pullname", "pullid", master.getContractNatureOf()));
							dform.set("id",id);
							// ά��վ����
							request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation()));
							
							// �׷���λ��Ϣ								
							MaintContractMaster mcm=null;
							Customer companyA =null;
							List eList=hs.createQuery("from MaintContractMaster where billNo='"+master.getMaintBillNo()+"'").list();
							if(eList!=null && eList.size()>0){
								mcm=(MaintContractMaster) eList.get(0);
								companyA = (Customer) hs.get(Customer.class,mcm.getCompanyId());
							}else{
								companyA=new  Customer();
							}
							request.setAttribute("companyA",companyA);
							
							// �ҷ�����λ��Ϣ
							Customer companyB = (Customer) hs.get(Customer.class,master.getCompanyId2());
						
							String sql = "from EntrustContractDetail where billNo = '"+master.getBillNo()+"'";					
							query = hs.createQuery(sql);	
							maintContractDetailList = query.list();				
							for (Object object : maintContractDetailList) {
								EntrustContractDetail detail = (EntrustContractDetail) object;
								detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
							}
							// ��ϸ�б�
		                    request.setAttribute("entrustContractMasterBean", master);	
							request.setAttribute("entrustContractDetailList", maintContractDetailList);
							request.setAttribute("companyB",companyB);
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
				
				if (master == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"display.recordnotfounterror"));
				}
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

			request.setAttribute("display", "yes");
			request.setAttribute("isOpen", isOpen);
			//request.setAttribute("elevatorSaleBean", elevatorSalesInfo);
			forward = mapping.findForward("searchReturningMaintainDetailDisplay");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
}
