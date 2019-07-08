package com.gzunicorn.struts.action.engcontractmanager.maintcontractquote;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquoteprocess.MaintContractQuoteProcess;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class MaintContractQuoteAuditAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractQuoteAuditAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "maintcontractquoteaudit", null);
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

		request.setAttribute("navigator.location", "ά������������� >> ��ѯ�б�");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session,"maintContractQuoteAuditList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fMaintContractQuoteAudit");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		cache.updateTable(table);
		table.setSortColumn("billNo");
		table.setIsAscending(false);
		cache.updateTable(table);

		if (action.equals(ServeTableForm.NAVIGATE) || action.equals(ServeTableForm.SORT)) {
			cache.loadForm(tableForm);
		} else if (action.equals("Submit")) {
			cache.loadForm(tableForm);
		} else {
			table.setFrom(0);
		}
		cache.saveForm(tableForm);

		String billNo = tableForm.getProperty("billNo");// �׷���λ����
		String companyName = tableForm.getProperty("companyName");// �׷���λ����
		String status = tableForm.getProperty("status");// ����״̬
		String maintDivision = tableForm.getProperty("maintDivision");// �׷���λid
		String submitType = tableForm.getProperty("submitType");// �ύ��־
		String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
		String elevatorNo = tableForm.getProperty("elevatorNo");// ���ݱ��
		String isscht = tableForm.getProperty("isscht");//�Ƿ����ɺ�ͬ
		
		//Ĭ����ʾ���ύ��¼
		if (submitType == null || submitType.equals("")) {
			submitType = "Y"; // ���ύ
			tableForm.setProperty("submitType", submitType);
		}	
		
		//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
		List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
		if(maintDivision == null || maintDivision.equals("")){
			Map map = (Map)maintDivisionList.get(0);
			maintDivision = (String)map.get("grcid");
		}

		Session hs = null;
		SQLQuery query = null;
		try {
			String roleid=userInfo.getRoleID();
			String maintStation="%";
			//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ
			if(roleid!=null && roleid.trim().equals("A03")){
				maintStation=userInfo.getStorageId();
			}
			
			hs = HibernateUtil.getSession();

			String sql = "select a.*,b.username,c.comname,e.typename,s.storagename "+
					" from MaintContractQuoteMaster a," +
					" Loginuser b,Company c,ViewFlowStatus e,StorageID s" + 
					" where a.attn = b.userid"+
					" and a.maintDivision = c.comid"+
					" and a.maintStation = s.storageid"+
					" and a.status = e.typeid"+
					" and a.maintStation like '"+maintStation+"'";
			
			if (isscht != null && !isscht.equals("")) {
				//���ɺ�ͬ ��
				if("Y".equals(isscht)){
					sql += " and a.billno in(select isnull(quoteBillNo,'-') from MaintContractMaster)";
				}else if("N".equals(isscht)){
					//���ɺ�ͬ ��
					sql += " and a.billno not in(select isnull(quoteBillNo,'-') from MaintContractMaster)";
				}
			}
			
			if (billNo != null && !billNo.equals("")) {
				sql += " and a.billNo like '%"+billNo.trim()+"%'";
			}	
			if (companyName != null && !companyName.equals("")) {
				sql += " and a.companyName like '%" + companyName.trim() + "%'";
			}
			if (status != null && !status.equals("")) {
				sql += " and a.status = '" + Integer.valueOf(status) + "'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and c.comid like '" + maintDivision.trim() + "'";
			}
			if (submitType != null && !submitType.equals("")) {
				sql += " and a.submitType like '" + submitType.trim() + "'";
			}
			if (salesContractNo != null && !salesContractNo.equals("")) {
				sql += " and a.billNo in(select distinct billNo from MaintContractQuoteDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
			}
			if (elevatorNo != null && !elevatorNo.equals("")) {
				sql += " and a.billNo in(select distinct billNo from MaintContractQuoteDetail where elevatorNo like '%"+elevatorNo.trim()+"%')";
			}
			
			if (table.getIsAscending()) {
				sql += " order by " + table.getSortColumn() + " asc";
			} else {
				sql += " order by " + table.getSortColumn() + " desc";
			}
			
			//System.out.println(sql);
			
			query = hs.createSQLQuery(sql);					
			query.addEntity("a",MaintContractQuoteMaster.class);
			query.addScalar("username");
			query.addScalar("comname");
			query.addScalar("typename");
			query.addScalar("storagename");
			
			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

			// �ó���һҳ�����һ����¼����;
			query.setFirstResult(table.getFrom()); // pagefirst
			query.setMaxResults(table.getLength());

			cache.check(table);

			List list = query.list();
			List maintContractQuoteAuditList = new ArrayList();
			for (Object object : list) {
				Object[] objs = (Object[])object;
				Map map = BeanUtils.describe(objs[4]); // MaintContractQuoteMaster
				map.put("attnName", String.valueOf(objs[0])); // ����������
				map.put("maintDivisionName", String.valueOf(objs[1])); // �ֲ�����
				map.put("statusName", String.valueOf(objs[2])); // ����״̬
				map.put("storagename", String.valueOf(objs[3])); // ά��վ����
				
				//���ɺ�ͬ ��
				if("Y".equals(isscht)){
					map.put("iscontract", "��");
				}else if("N".equals(isscht)){
					//���ɺ�ͬ ��
					map.put("iscontract", "��");
				}else{
					String billno=(String)map.get("billNo");
					String sqlmas="select quoteBillNo from MaintContractMaster where quoteBillNo='"+billno.trim()+"'";
					List ecmlist=hs.createSQLQuery(sqlmas).list();
					if(ecmlist!=null && ecmlist.size()>0){
						map.put("iscontract", "��");
					}else{
						map.put("iscontract", "��");
					}
				}
				
				maintContractQuoteAuditList.add(map);
			}

			table.addAll(maintContractQuoteAuditList);
			session.setAttribute("maintContractQuoteAuditList", table);
			// �ֲ��������б�
			request.setAttribute("maintDivisionList",Grcnamelist1.getgrcnamelist(userInfo.getUserID()));
			// ����״̬�������б�
			request.setAttribute("processStatusList", bd.getProcessStatusList());
			// ��ȡ��������
			request.setAttribute("flowname", WorkFlowConfig.getProcessDefine("enginequotemasterProcessName"));
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
		forward = mapping.findForward("maintContractQuoteAuditList");

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
		request.setAttribute("navigator.location",
				messages.getMessage(locale, navigation));
	}

	/**
	 * ����鿴�ķ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		request.setAttribute("navigator.location", "ά������������� >> �鿴");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;

		String id = request.getParameter("id");

		Session hs = null;
		List maintContractQuoteDetailList = null;

		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from MaintContractQuoteMaster where billNo = '"+ id + "'");
				List list = query.list();
				if (list != null && list.size() > 0) {

					// ����Ϣ
					MaintContractQuoteMaster bean = (MaintContractQuoteMaster) list.get(0);
					List pdlist=bd.getPullDownAllList("MaintContractQuoteMaster_PaymentMethodApply");
					String pmaname=bd.getOptionName(bean.getPaymentMethodApply(), pdlist);
					bean.setR4(pmaname);
					//��ͬ������������
					String ccastrname="";
					String ccastr=bean.getContractContentApply();
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
					bean.setContractContentApply(ccastrname);
					
					request.setAttribute("attnName", bd.getName(hs, "Loginuser","username", "userid",bean.getAttn())); //����������
					request.setAttribute("maintDivisionName", bd.getName(hs, "Company", "comname", "comid",bean.getMaintDivision())); //ά���ֲ�����					
					request.setAttribute("maintStationName", bd.getName(hs, "Storageid","storageName", "storageID", bean.getMaintStation())); //ά��վ����
					request.setAttribute("maintContractQuoteBean", bean);

					// ������Ϣ��ϸ�б�
					query = hs.createQuery("from MaintContractQuoteDetail where billNo = '"+ id + "'");
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
					maintContractQuoteDetailList = query.list();
					for (Object object : maintContractQuoteDetailList) {
						MaintContractQuoteDetail detail = (MaintContractQuoteDetail) object;
						detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
						detail.setR4(bd.getName("Pulldown", "pullname", "id.pullid",detail.getSignWay()));//ǩ��ʽ
					}
					request.setAttribute("maintContractQuoteDetailList",maintContractQuoteDetailList);
					
					//����������Ϣ
					query = hs.createQuery("from MaintContractQuoteProcess where billNo = '"+ id + "' order by itemId");
					List processApproveList = query.list();
					for (Object object : processApproveList) {
						MaintContractQuoteProcess process = (MaintContractQuoteProcess) object;
						process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
					}
					request.setAttribute("processApproveList",processApproveList);
					
					
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				//��ȡ�Ѿ��ϴ��ĸ���
				List filelst=this.FileinfoList(hs, id.trim(), "MaintContractQuoteMaster");
				request.setAttribute("updatefileList", filelst);
				
				request.setAttribute("ContractContentList", bd.getPullDownList("MaintContractQuoteMaster_ContractContentApply"));
				
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
			forward = mapping.findForward("maintContractQuoteAuditDisplay");
		}

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
		MaintContractQuoteMaster master = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				master = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, id);
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
				MaintContractQuoteProcess process = new MaintContractQuoteProcess();
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
	/**
	 * ��ȡ���ϴ������б�
	 */
	public List FileinfoList(Session hs, String MaterSid, String BusTable)
	  {
	    List rt = new ArrayList();
	    Connection con = null;
	    try {
	      con = hs.connection();
	      String sql = "select a.*,b.username as UploaderName  from  ContractFileinfo a ,loginuser b " +
	      		"where a.Uploader = b.userid and a.MaterSid = '" + MaterSid + "'  and a.BusTable = '" + BusTable + "'";
	      DataOperation op = new DataOperation();
	      op.setCon(con);
	      rt = op.queryToList(sql);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return rt;
	  }
}
