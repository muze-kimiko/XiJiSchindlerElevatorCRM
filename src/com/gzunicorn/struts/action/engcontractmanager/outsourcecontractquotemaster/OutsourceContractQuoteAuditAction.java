package com.gzunicorn.struts.action.engcontractmanager.outsourcecontractquotemaster;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SqlBeanUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractquotemaster.EntrustContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractquoteprocess.EntrustContractQuoteProcess;
import com.gzunicorn.hibernate.engcontractmanager.lostelevatorreport.LostElevatorReport;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.outsourcecontractquotemaster.OutsourceContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.outsourcecontractquoteprocess.OutsourceContractQuoteProcess;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractdetail.ServicingContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractmaster.ServicingContractMaster;
import com.gzunicorn.hibernate.infomanager.elevatorarchivesinfo.ElevatorArchivesInfo;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class OutsourceContractQuoteAuditAction extends DispatchAction {

	Log log = LogFactory.getLog(OutsourceContractQuoteAuditAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "outsourcecontractquoteaudit", null);
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
		
		request.setAttribute("navigator.location","ά��ί�б������ >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session, "outsourceContractQuoteAuditList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fOutsourceContractQuoteAudit");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		cache.updateTable(table);
		table.setSortColumn("billNo");
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
		String companyName = tableForm.getProperty("companyName");// 
		String status = tableForm.getProperty("status");// 
		String maintDivision = tableForm.getProperty("maintDivision");// ����ά��վ
		String maintContractNo=tableForm.getProperty("maintContractNo");
//		String submitType = tableForm.getProperty("submitType");// �ύ��־
		//String auditStatus = tableForm.getProperty("auditStatus");// ���״̬

		
//		if(submitType == null || submitType.equals("")){
//			submitType = "Y";
//			tableForm.setProperty("submitType", "Y");
//		}
		/*if(auditStatus == null){
			auditStatus = "N";
			tableForm.setProperty("auditStatus", "N");
		}*/
		//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
		List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
		if(maintDivision == null || maintDivision.equals("")){
			Map map = (Map)maintDivisionList.get(0);
			maintDivision = (String)map.get("grcid");
		}
		
		Session hs = null;
		SQLQuery query = null;
		try {
			//��ѯά�����ں�ͬ   ���ݺ�ͬ����������ǰ3��������
			/*String today=DateUtil.getNowTime("yyyy-MM-dd");
			String datestr=DateUtil.getDate(today, "MM", 3);
			tableForm.setProperty("hiddatestr",datestr);*/
			
			hs = HibernateUtil.getSession();
			
			String sql = "select a.*,isnull(b.companyName,'') companyName,c.username from"+
				" OutsourceContractQuoteMaster a left outer join Customer b on a.companyId=b.companyId,Loginuser c "+
				"where a.operId=c.userid and a.submitType='Y'";
			
			if (billNo != null && !billNo.equals("")) {
				sql += " and a.billNo like '%"+billNo.trim()+"%'";
			}
			if (companyName != null && !companyName.equals("")) {
				sql += " and b.companyName like '%"+companyName.trim()+"%'";
			}
			if(maintContractNo!=null && !"".equals(maintContractNo)){
				sql+=" and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and a.maintDivision like '"+maintDivision.trim()+"'";
			}
			if (status != null && !status.equals("")) {
				sql += " and a.status like '%"+status.trim()+"%'";
			}
			/*if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and a.maintDivision like '"+maintDivision.trim()+"'";
			}*/
//			if (submitType != null && !submitType.equals("")) {
//				sql += " and submitType like '"+submitType.trim()+"'";
//			}
			/*if (auditStatus != null && !auditStatus.equals("")) {
				sql += " and a.auditStatus like '"+auditStatus.trim()+"'";
			}*/
			
			String order = " order by ";
			order += table.getSortColumn();
			
			if (table.getIsAscending()) {
				sql += order + " desc";
			} else {
				sql += order + " asc";
			}
			
			query = hs.createSQLQuery(sql);
			query.addEntity("a",OutsourceContractQuoteMaster.class);
			query.addScalar("companyName");
			query.addScalar("username");
			
			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

			// �ó���һҳ�����һ����¼����;
			query.setFirstResult(table.getFrom()); // pagefirst
			query.setMaxResults(table.getLength());

			cache.check(table);

			List list = query.list();
			List outsourceContractQuoteAuditList = new ArrayList();
			for (Object object : list) {
				Object[] objs = (Object[])object;
				OutsourceContractQuoteMaster master = (OutsourceContractQuoteMaster) objs[2];
				//master.setCompanyId(bd.getName_Sql("Customer", "companyName", "companyId", master.getCompanyId()));
				master.setCompanyId(objs[0].toString());
				master.setOperId(objs[1].toString());
				master.setR1(bd.getName_Sql("ViewFlowStatus", "typename", "typeid", String.valueOf(master.getStatus())));
				master.setMaintDivision(bd.getName("Company","comname","comid",master.getMaintDivision()));
				outsourceContractQuoteAuditList.add(master);
			}

			table.addAll(outsourceContractQuoteAuditList);
			session.setAttribute("outsourceContractQuoteAuditList", table);
			// ����״̬�������б�
			request.setAttribute("processStatusList", bd.getProcessStatusList());
			// ��ȡ��������
			request.setAttribute("flowname", WorkFlowConfig.getProcessDefine("outsourcecontractquotemasterProcessName"));
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
		forward = mapping.findForward("outsourceContractQuoteAuditList");
		
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
		
		request.setAttribute("navigator.location","ά��ί�б������ >> �鿴");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		//DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id = request.getParameter("id");
		
		Session hs = null;
		OutsourceContractQuoteMaster master=null;
		ServicingContractMaster maint=null;
		ServicingContractDetail detail=null;
		Customer customer=null;
		List detailList=new ArrayList();
		
		if (id != null && !"".equals(id)) {
			try {
				hs = HibernateUtil.getSession();
				master=(OutsourceContractQuoteMaster) hs.get(OutsourceContractQuoteMaster.class, id.trim());
				if (master!=null) {
					master.setR1(bd.getName("Loginuser", "username", "userid", master.getOperId()));
					
					customer=(Customer) hs.get(Customer.class, master.getCompanyId());
					
					maint=(ServicingContractMaster) hs.get(ServicingContractMaster.class, master.getWgBillno());
					if(maint!=null){
						maint.setMaintDivision(bd.getName("Company", "comname", "comid", maint.getMaintDivision()));
						maint.setMaintStation(bd.getName("Storageid", "storagename", "storageid", maint.getMaintStation()));
					}
					
					String hql="select wgRowid from OutsourceContractQuoteDetail where billNo='"+master.getBillNo()+"'";
					List list=hs.createQuery(hql).list();
					if(list!=null && list.size()>0){
						for(int i=0;i<list.size();i++){
							detail=(ServicingContractDetail) hs.get(ServicingContractDetail.class, Integer.valueOf(list.get(i).toString()));
							detailList.add(detail);
						}
					}
					//����������Ϣ
					List processApproveList = hs.createQuery("from OutsourceContractQuoteProcess where billNo = '"+ master.getBillNo() + "' order by itemId").list();
					
					for (Object object : processApproveList) {
						OutsourceContractQuoteProcess process = (OutsourceContractQuoteProcess) object;
						process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
					}
					request.setAttribute("processApproveList",processApproveList);
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
	
			request.setAttribute("quoteBean", master);
			request.setAttribute("contractBean", maint);
			request.setAttribute("customer", customer);
			request.setAttribute("detailList", detailList);
			request.setAttribute("display", "yes");
			forward = mapping.findForward("outsourceContractQuoteAuditDisplay");
		}		
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}

	// �������̹���
			public ActionForward toReStartProcess(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) {

				ActionErrors errors = new ActionErrors();
				ViewLoginUserInfo userInfo = (ViewLoginUserInfo) request.getSession().getAttribute(SysConfig.LOGIN_USER_INFO);

				String id = request.getParameter("id");
				
				Session hs = null;
				Transaction tx = null;
				OutsourceContractQuoteMaster master = null;
				if (id != null) {
					try {
						hs = HibernateUtil.getSession();
						tx = hs.beginTransaction();
						
						master = (OutsourceContractQuoteMaster) hs.get(OutsourceContractQuoteMaster.class, id);
						if (master == null) {
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
						}
						
						// ��������Ϣ
						master.setSubmitType("N"); //�ύ��־
						master.setStatus(new Integer(WorkFlowConfig.State_NoStart)); // ����״̬,δ����
						master.setTokenId(new Long(0));// ��������
						master.setProcessName("");// ��������
						hs.save(master);
						
						// �����������������Ϣ
						OutsourceContractQuoteProcess process = new OutsourceContractQuoteProcess();
						process.setBillNo(master.getBillNo());//������ˮ��
						process.setTaskId(new Integer(0));//�����
						process.setTaskName("��������");//��������
						process.setTokenId(new Long(0));//��������
						process.setUserId(userInfo.getUserID());//������
						process.setDate1(CommonUtil.getToday());//��������
						process.setTime1(CommonUtil.getTodayTime());//����ʱ��
						process.setApproveResult("��������");
						process.setApproveRem("������������");
						hs.save(process);
						
						tx.commit();
					} catch (Exception e1) {
						e1.printStackTrace();				
						try{
							tx.rollback();
						} catch (HibernateException e2){
							e2.printStackTrace();
						}
						
					} finally {
						try {
							hs.close();
						} catch (HibernateException hex) {
							log.error(hex.getMessage());
							DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
						}
					}
				}

				if (!errors.isEmpty()) {
					saveErrors(request, errors);
				}

				return mapping.findForward("returnList");
			}

}
