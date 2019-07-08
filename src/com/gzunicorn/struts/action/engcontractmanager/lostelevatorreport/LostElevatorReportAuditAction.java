package com.gzunicorn.struts.action.engcontractmanager.lostelevatorreport;

import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.engcontractmanager.lostelevatorreport.LostElevatorReport;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

/**
 * ά����ͬ�˱���Ա���
 * @author Lijun
 *
 */
public class LostElevatorReportAuditAction extends DispatchAction {

	Log log = LogFactory.getLog(LostElevatorReportAuditAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "lostelevatorreportaudit", null);
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
		
		request.setAttribute("navigator.location","ά����ͬ�˱���� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HTMLTableCache cache = new HTMLTableCache(session, "lostElevatorReportAuditList");

		DefaultHTMLTable table = new DefaultHTMLTable();
		table.setMapping("fLostElevatorReportAudit");
		table.setLength(SysConfig.HTML_TABLE_LENGTH);
		cache.updateTable(table);
		table.setSortColumn("jnlno");
		table.setIsAscending(true);
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
		String projectName = tableForm.getProperty("projectName");// �׷���λ
		String maintDivision = tableForm.getProperty("maintDivision");// ����ά��վ			
//		String submitType = tableForm.getProperty("submitType");// �ύ��־
		String auditStatus = tableForm.getProperty("auditStatus");// ���״̬
		if(auditStatus==null || auditStatus.trim().equals("")){
			auditStatus="N";
			tableForm.setProperty("auditStatus",auditStatus);
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
			//��ѯά�����ں�ͬ   ���ݺ�ͬ����������ǰ3��������
			/*String today=DateUtil.getNowTime("yyyy-MM-dd");
			String datestr=DateUtil.getDate(today, "MM", 3);
			tableForm.setProperty("hiddatestr",datestr);*/
			
			hs = HibernateUtil.getSession();
			
			String sql = "select a,b.comname,c.pullname,s.storagename " +
					" from LostElevatorReport a,Company b,Pulldown c,Storageid s"+
					" where a.maintDivision=b.comid and a.contractNatureOf=c.id.pullid and a.submitType='Y' "+
					" and a.maintStation = s.storageid"+
					//" and (isnull(a.isCharge,'')='Y' or isnull(a.auditStatus3,'N')='Y')"+ //�ۿ� ���� �ܲ������ͨ��
					" and isnull(a.auditStatus3,'N')='Y'"+ // �ܲ������ͨ��
					" and c.id.typeflag='LostElevatorReport_ContractNatureOf' and isnull(auditStatus2,'N')='Y' ";
			
			if (jnlno != null && !jnlno.equals("")) {
				sql += " and a.jnlno like '%"+jnlno.trim()+"%'";
			}
			if (maintContractNo != null && !maintContractNo.equals("")) {
				sql += " and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
			}
			if (projectName != null && !projectName.equals("")) {
				sql += " and a.projectName like '%"+projectName.trim()+"%'";
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				sql += " and a.maintDivision like '"+maintDivision.trim()+"'";
			}
			if (auditStatus != null && !auditStatus.equals("")) {
				sql += " and isnull(a.auditStatus,'N') like '"+auditStatus.trim()+"'";
			}
			
			String order = " order by ";
			order += table.getSortColumn();
			
			if (table.getIsAscending()) {
				sql += order + " desc";
			} else {
				sql += order + " asc";
			}
			
			query = hs.createQuery(sql);
			table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

			// �ó���һҳ�����һ����¼����;
			query.setFirstResult(table.getFrom()); // pagefirst
			query.setMaxResults(table.getLength());

			cache.check(table);

			List list = query.list();
			List lostElevatorReportAuditList = new ArrayList();
			for (Object object : list) {
				Object[] objs = (Object[])object;
				LostElevatorReport master = (LostElevatorReport) objs[0];
				master.setMaintDivision(objs[1].toString());
				master.setContractNatureOf(objs[2].toString());
				master.setMaintStation(objs[3].toString());
				
				lostElevatorReportAuditList.add(master);
			}

			table.addAll(lostElevatorReportAuditList);
			session.setAttribute("lostElevatorReportAuditList", table);
			// �ֲ��������б�
			request.setAttribute("maintDivisionList", maintDivisionList);

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
		forward = mapping.findForward("lostElevatorReportAuditList");
		
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
		
		request.setAttribute("navigator.location","ά����ͬ�˱���� >> �鿴");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		//DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String id = request.getParameter("id");
		
		Session hs = null;
		LostElevatorReport report=null;
	
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				String sql="select a,b.comname,c.storagename "+
					"from LostElevatorReport a,Company b,Storageid c "+
					"where a.maintDivision=b.comid and a.maintStation=c.storageid "+ 
					" and a.jnlno='"+id+"'";
				Query query = hs.createQuery(sql);
				List list = query.list();
				if (list != null && list.size() > 0) {
					for(Object object : list){
						Object[] objs=(Object[])object;
						report=(LostElevatorReport) objs[0];
						report.setMaintDivision(objs[1].toString());
						report.setMaintStation(objs[2].toString());
						String[] a={"a.id.typeflag='LostElevatorReport_ContractNatureOf'"};
						report.setContractNatureOf(bd.getName("Pulldown", "pullname", "id.pullid", report.getContractNatureOf(), a));
						String[] b={"a.id.typeflag='LostElevatorReport_CauseAnalysis'"};
						report.setCauseAnalysis(bd.getName("Pulldown", "pullname", "id.pullid", report.getCauseAnalysis(), b));
					}
					report.setOperId(bd.getName("Loginuser", "username", "userid", report.getOperId()));
					report.setAuditOperid2(bd.getName("Loginuser", "username", "userid", report.getAuditOperid2()));
					report.setAuditOperid3(bd.getName("Loginuser", "username", "userid", report.getAuditOperid3()));
					report.setHfOperid(bd.getName("Loginuser", "username", "userid", report.getHfOperid()));
					
					if(report.getAuditStatus()==null || report.getAuditStatus().equals("N")){
						report.setAuditDate(CommonUtil.getNowTime());
						report.setAuditOperid(userInfo.getUserName());
						report.setAuditStatus("N");
					}else{
						report.setAuditOperid(bd.getName("Loginuser", "username", "userid", report.getAuditOperid()));
					}
					
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				
				//��ȡ�Ѿ��ϴ��ĸ���
				LostElevatorReportAction ler=new LostElevatorReportAction();
				List filelst=ler.FileinfoList(hs, id.trim(), "LostElevatorReport");
				request.setAttribute("updatefileList", filelst);
				
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

			request.setAttribute("lostElevatorReportBean", report);
			request.setAttribute("display", "yes");
			forward = mapping.findForward("lostElevatorReportAuditDisplay");
		}		
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
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
		
		if(isTokenValid(request, true)){
			
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			DynaActionForm dform = (DynaActionForm) form;				
			String id = request.getParameter("id"); 		
			String auditStatus = String.valueOf(dform.get("auditStatus")); // ���״̬
			String submitType = "N".equals(auditStatus) ? "R" : "Y"; // �Ƿ񲵻�
			String auditRem = String.valueOf(dform.get("auditRem")); // ������
			
			if(id != null && !id.equals("")){
				
				Session hs = null;
				Transaction tx = null;
				Query query = null;
				
				String taskSubFlag="";
				LostElevatorReport report = null;						
				try {
					
					hs = HibernateUtil.getSession();		
					tx = hs.beginTransaction();
					report=(LostElevatorReport) hs.get(LostElevatorReport.class, id);
					report.setSubmitType(submitType);
					report.setAuditDate(CommonUtil.getNowTime());
					report.setAuditOperid(userInfo.getUserID());
					report.setAuditRem(auditRem);
					report.setAuditStatus(submitType);
					
					if("Y".equals(submitType)){
						
						//System.out.println(">>>1.��ά����ͬ�ĺ�ͬ״̬����Ϊ�˱���");
						MaintContractMaster master=(MaintContractMaster) hs.get(MaintContractMaster.class, report.getBillNo());
						master.setContractStatus("TB");
						hs.save(master);
						
						taskSubFlag=master.getTaskSubFlag();//�´��־
						
						//�޸�ά����ͬ��ϸ����Ϊ�˱����ų�����ʷ�ĵ��ݡ�
						List<MaintContractDetail> detailList=hs.createQuery("from MaintContractDetail "
								+ "where billNo='"+report.getBillNo()+"' and isnull(elevatorStatus,'')=''").list();
						if(detailList!=null && detailList.size()>0){
							//System.out.println(">>>2.��ά����ͬ��ϸ��ʵ�ʽ�����������Ϊ�˱����ڡ�");
							for(MaintContractDetail detail : detailList){
								detail.setIsSurrender("Y");
								detail.setSurrenderUser(report.getOperId());
								detail.setSurrenderDate(CommonUtil.getNowTime());
								detail.setRealityEdate(report.getLostElevatorDate());
								hs.save(detail);
							}
						}
					}
					
					hs.save(report);
					
					tx.commit();
					
					//System.out.println(">>>3.�����˱�����ɾ�����ݱ����ƻ���");
					if("Y".equals(submitType) && "Y".equals(taskSubFlag)){
						CommonUtil.deleMaintenanceWorkPlan("",report.getBillNo(), report.getLostElevatorDate(), userInfo, errors);
					}
					
				} catch (Exception e) {				
					e.printStackTrace();
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
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("auditing.succeed")); //��ʾ����˳ɹ�����
				}
			}
		
		}else{
			saveToken(request);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("navigator.submit.error"));
		}
		
		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnList");

	}

}
