package com.gzunicorn.struts.action.engcontractmanager.maintcontractdelay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelaydetail.MaintContractDelayDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelaymaster.MaintContractDelayMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelayprocess.MaintContractDelayProcess;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.infomanager.elevatorarchivesinfo.ElevatorArchivesInfo;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class MaintContractDelayAuditAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractDelayAuditAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "maintcontractdelayaudit", null);
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
		
		request.setAttribute("navigator.location","ά����ͬ�ӱ���� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session, "maintContractDelayAuditList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fMaintContractDelayAudit");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		cache.updateTable(table);
		table.setSortColumn("jnlno");
		table.setIsAscending(false);
		cache.updateTable(table);

		if (action.equals(ServeTableForm.NAVIGATE)
				|| action.equals(ServeTableForm.SORT)) {
			cache.loadForm(tableForm);
		} else {
			table.setFrom(0);
		}
		cache.saveForm(tableForm);
		
		String jnlno = tableForm.getProperty("jnlno");// ��ˮ��
		String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
		String companyName = tableForm.getProperty("companyName");// �׷���λ
		String operId = tableForm.getProperty("operId");// ¼����
		String maintDivision = tableForm.getProperty("maintDivision");// �����ֲ�	
		//String submitType = tableForm.getProperty("submitType");// �ύ��־
		//String status = tableForm.getProperty("status");// ���״̬
		String auditStatus = tableForm.getProperty("auditStatus");// ���״̬

		if(auditStatus == null || auditStatus.equals("")){
			auditStatus = "N";
			tableForm.setProperty("auditStatus", "N");
		}
		
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
			
			String[] colNames = {
					"a.jnlno as jnlno",
					"a.billno as billno",
					"f.maintContractNo as maintContractNo",
					"f.contractSdate as contractSdate",
					"f.contractEdate as contractEdate",
					"b.username as operName",
					"a.submitType as submitType",
					"a.auditStatus as auditStatus",
					/*"a.processName as processName",	
					"d.typename as statusName",*/								
					"e.storagename as maintStationName",
					"c.comname as maintDivisionName",	
					"a.status as status",
					"a.tokenId as tokenId",
					"g.companyName as companyName"
				};
			
			String sql = "select "+StringUtils.join(colNames, ",")+
					" from MaintContractDelayMaster a,Loginuser b,Company c," +/*ViewFlowStatus d,*/
					" Storageid e,MaintContractMaster f,Customer g" + 
					" where a.operId = b.userid"+
					" and b.grcid = c.comid"+
					" and f.companyId = g.companyId"+
					" and a.maintStation = e.storageid"+
					" and a.billno = f.billNo"+
					" and a.submitType = 'Y'";
			
			if (jnlno != null && !jnlno.equals("")) {
				sql += " and jnlno like '%"+jnlno.trim()+"%'";
			}
			if (maintContractNo != null && !maintContractNo.equals("")) {
				sql += " and maintContractNo like '%"+maintContractNo.trim()+"%'";
			}
			if (companyName != null && !companyName.equals("")) {
				sql += " and companyName like '%"+companyName.trim()+"%'";
			}
			if (operId != null && !operId.equals("")) {
				sql += " and username like '%"+operId.trim()+"%'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and maintDivision like '"+maintDivision.trim()+"'";
			}
			if(auditStatus!=null && !auditStatus.equals("")){
				sql+=" and a.auditStatus like '"+auditStatus.trim()+"'";
			}
			if (table.getIsAscending()) {
				sql += " order by "+ table.getSortColumn() +" asc";
			} else {
				sql += " order by "+ table.getSortColumn() +" desc";
			}
			
			query = hs.createSQLQuery(sql);
			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

			// �ó���һҳ�����һ����¼����;
			query.setFirstResult(table.getFrom()); // pagefirst
			query.setMaxResults(table.getLength());

			cache.check(table);

			List list = query.list();
			List maintContractDelayAuditList = new ArrayList();
			for (Object object : list) {
				Object[] objs = (Object[])object;
				Map<String,String> map = new HashMap<String,String>();
				for(int i=0; i<colNames.length; i++){
					map.put(colNames[i].split(" as ")[1].trim(), String.valueOf(objs[i]));
				}
				maintContractDelayAuditList.add(map);
			}

			table.addAll(maintContractDelayAuditList);
			session.setAttribute("maintContractDelayAuditList", table);

			// ����״̬�������б�
			request.setAttribute("processStatusList", bd.getProcessStatusList());
			// �ֲ��������б�
			request.setAttribute("maintDivisionList", maintDivisionList);
			// ά��վ�������б�
			request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));			
			// ��ȡ��������
			//request.setAttribute("flowname", WorkFlowConfig.getProcessDefine("enginequotemasterProcessName"));

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
			
		
		return mapping.findForward("toList");
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
		
		request.setAttribute("navigator.location","ά����ͬ�ӱ���� >> �鿴");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		display(form, request, errors, "display");
		
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
				String upsql="update MaintContractDelayMaster set workisdisplay2='"+workisdisplay+"' where jnlno='"+id+"'";
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

		forward = mapping.findForward("toDisplay");
		return forward;
	}
	
	/**
	 * ��˷���
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
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
/*		if(isTokenValid(request, true)){*/
			
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			DynaActionForm dform = (DynaActionForm) form;				
			String id = request.getParameter("id");
			String auditStatus = String.valueOf(dform.get("auditStatus")); // ���״̬
			String submitType = "N".equals(auditStatus) ? "R" : "Y"; // �Ƿ񲵻�
			String auditRem = String.valueOf(dform.get("auditRem")); // ������
			String[] rowids = request.getParameterValues("rowid"); // ��ͬ������ϸ�к�
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				Query query = null;
				
				MaintContractDelayMaster master = null;						
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();
	
					master=(MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
					master.setSubmitType(submitType); // �ύ��־
					master.setWorkisdisplay(null);
					master.setWorkisdisplay2(null);
					if("Y".equals(submitType)){
						String auditdate=CommonUtil.getNowTime();
						master.setAuditStatus(auditStatus); // ���״̬
						master.setAuditOperid(userInfo.getUserID()); // �����
						master.setAuditDate(auditdate); // ���ʱ��
						master.setAuditRem(auditRem); // ������
						List<MaintContractDelayDetail> list=hs.createQuery("from MaintContractDelayDetail where jnlno='"+master.getJnlno()+"'").list();
						if(list!=null && list.size()>0){
							for(MaintContractDelayDetail detail : list){
								hs.createQuery("update MaintContractDetail set "
										//+ "mainEdate='"+detail.getDelayEdate()+"',"//������ά����������
										+ "delayEDate='"+detail.getDelayEdate()+"' "
										+ "where rowid='"+detail.getRowid()+"'").executeUpdate();
							}
						}
					}
					
					hs.save(master);
					
					tx.commit();
				} catch (Exception e) {				
					e.printStackTrace();
					tx.rollback();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
				} finally {
					if(hs != null){
						hs.close();				
					}				
				}
				
			} else {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("maintContract.recordnotfounterror"));
			}	
			
			if(errors.isEmpty()){			
				if("R".equals(submitType)){
					//��ʾ����ͬ���سɹ�!��
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("contract.toreback.success")); 
				}else{
					//������ҵ�ƻ�
					for (String rowid : rowids) {
						CommonUtil.toMaintenanceWorkPlan(rowid, null, userInfo, errors);
					}
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("auditing.succeed")); //��ʾ����˳ɹ�����
				}
			}
		
		/*}else{
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		}*/
		
		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnList");

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
		MaintContractDelayMaster master = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				master = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
				
				if (master != null && (master.getStatus() == 1 || master.getStatus() == 2)) {// ����״̬Ϊ��ֹ��ͨ����ʱ��

					// ��������Ϣ
					master.setSubmitType("N"); //�ύ��־
					master.setStatus(new Integer(WorkFlowConfig.State_NoStart)); // ����״̬,δ����
					master.setTokenId(new Long(0));// ��������
					master.setProcessName("");// ��������
					hs.save(master);
					
					// �����������������Ϣ
					MaintContractDelayProcess process = new MaintContractDelayProcess();
					process.setJnlno(master.getJnlno());//������ˮ��
					process.setTaskId(new Integer(0));//�����
					process.setTaskName("��������");//��������
					process.setTokenId(new Long(0));//��������
					process.setUserId(userInfo.getUserID());//������
					process.setDate1(CommonUtil.getToday());//��������
					process.setTime1(CommonUtil.getTodayTime());//����ʱ��
					process.setApproveResult("��������");
					process.setApproveRem("������������");
					hs.save(process);
					
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				
				tx.commit();
			} catch (Exception e1) {
				e1.printStackTrace();		
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.reStartProcess.failed"));// ��������ʧ��
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

		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.reStartProcess.succeed"));// �������̳ɹ�
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return mapping.findForward("returnList");
	}
	
	
	public void display(ActionForm form, HttpServletRequest request ,ActionErrors errors ,String flag){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		String id = request.getParameter("id");

		String billNo = null;
		Session hs = null;
		List maintContractDetailList = null;

		try {
			hs = HibernateUtil.getSession();
			//ά����ͬ�ӱ�����
			MaintContractDelayMaster delayMaster = (MaintContractDelayMaster) hs.get(MaintContractDelayMaster.class, id);
			if(delayMaster.getAuditStatus()==null || delayMaster.getAuditStatus().equals("N")){
				delayMaster.setAuditDate(CommonUtil.getNowTime());
				delayMaster.setAuditOperid(userInfo.getUserName());
				
			}else{
				delayMaster.setAuditOperid(bd.getName(hs, "Loginuser","username", "userid",delayMaster.getAuditOperid()));
			}
			request.setAttribute("delayrem", delayMaster.getRem());
			
			String jnlno = delayMaster.getJnlno(); //�ӱ���ˮ��
			billNo = delayMaster != null ? delayMaster.getBillno() : id;// ά����ͬ������ˮ��

			Query query = hs.createQuery("from MaintContractMaster where billNo = '"+billNo+"'");
			List list = query.list();
			if (list != null && list.size() > 0) {
				// �ӱ�����Ϣ
				request.setAttribute("delayMaster", delayMaster);

				// ά����ͬ��������Ϣ
				MaintContractMaster master = (MaintContractMaster) list.get(0);															
				master.setAttn(bd.getName(hs, "Loginuser","username", "userid",master.getAttn()));// ������
				master.setAuditOperid(bd.getName(hs, "Loginuser", "username", "userid",master.getAuditOperid()));// �����
				master.setTaskUserId(bd.getName(hs, "Loginuser", "username", "userid",master.getTaskUserId()));// �´���	
				master.setMaintDivision(bd.getName(hs, "Company", "comname", "comid",master.getMaintDivision()));// ά���ֲ�														
				master.setOperId(bd.getName(hs, "Loginuser","username", "userid",master.getOperId()));
				request.setAttribute("maintStationName", bd.getName(hs, "Storageid", "storagename", "storageid", master.getMaintStation()));//����ά��վ
				
				//���ʽ
				String pmastr=master.getPaymentMethod();
				List pdlist=bd.getPullDownAllList("MaintContractQuoteMaster_PaymentMethodApply");
				String pmaname=bd.getOptionName(pmastr, pdlist);
				master.setR4(pmaname);
				//maintContractBean.setPaymentMethod(pmastr);//���ʽ
				//��ͬ������������
				String ccastrname="";
				String ccastr=master.getContractTerms();
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
				master.setR5(ccastrname);
				
				// �׷���λ��Ϣ
				Customer companyA = (Customer) hs.get(Customer.class,master.getCompanyId());															
				// �ҷ�����λ��Ϣ
				Customer companyB = (Customer) hs.get(Customer.class,master.getCompanyId2());
									
				// ά���ӱ�������ϸ�б�
				String sql = "select a,a.realityEdate from MaintContractDetail a where a.billNo = '"+billNo+"'";
				if(delayMaster != null){
					sql = "select a,b.delayEdate from MaintContractDetail a,MaintContractDelayDetail b" +
							" where a.rowid = b.rowid" +
							" and b.jnlno = '"+delayMaster.getJnlno()+"'" + 
							" and a.assignedMainStation like '"+delayMaster.getMaintStation()+"'";
				}
				
				if(delayMaster != null){
					request.setAttribute("username", bd.getName(hs, "Loginuser","username", "userid",delayMaster.getOperId()));
					request.setAttribute("daytime", delayMaster.getOperDate());
				}
				
				query = hs.createQuery(sql);	
				list = query.list();
				maintContractDetailList = new ArrayList();
				List signWayList = bd.getPullDownList("MaintContractDetail_SignWay");// ǩ��ʽ�����б�
				List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// �������������б�
				for (Object object : list) {
					Object[] objs = (Object[]) object;
					MaintContractDetail detail = (MaintContractDetail) objs[0];
					detail.setRealityEdate((String) objs[1]);
					detail.setSignWay(bd.getOptionName(detail.getSignWay(), signWayList));
					detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
					maintContractDetailList.add(detail);
				}
				
				/*//����������Ϣ
				query = hs.createQuery("from MaintContractDelayProcess where jnlno = '"+ jnlno + "' order by itemId");
				List processApproveList = query.list();
				for (Object object : processApproveList) {
					MaintContractDelayProcess process = (MaintContractDelayProcess) object;
					process.setUserId(bd.getName(hs, "Loginuser", "username", "userid", process.getUserId()));
				}
				request.setAttribute("processApproveList",processApproveList);*/
				
				request.setAttribute("delayBean", delayMaster);
				request.setAttribute("maintContractBean", master);
				request.setAttribute("companyA",companyA);
				request.setAttribute("companyB",companyB);
				request.setAttribute("maintContractDetailList", maintContractDetailList);
				request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));// ά��վ�����б�
				// ��ȡ��������
				request.setAttribute("flowname", WorkFlowConfig.getProcessDefine("enginequotemasterProcessName"));
				
						
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
