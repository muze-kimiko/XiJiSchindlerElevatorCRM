package com.gzunicorn.struts.action.engcontractmanager.lostelevatorreport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
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
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;

import com.gzunicorn.hibernate.engcontractmanager.ContractFileinfo.ContractFileinfo;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractmaster.EntrustContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.lostelevatorreport.LostElevatorReport;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class LostElevatorReportAction extends DispatchAction {

	Log log = LogFactory.getLog(LostElevatorReportAction.class);
	
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
		
		String name = request.getParameter("method");
		
		if(!"toDisplayRecord".equals(name) && !"toDownloadFileRecord".equals(name)){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "lostelevatorreport", null);
			/** **********�����û�Ȩ�޹���*********** */
		}

		// Set default method is toSearchRecord
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
		
		request.setAttribute("navigator.location","ά����ͬ�˱����� >> ��ѯ�б�");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "lostElevatorReportList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fLostElevatorReport");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("jnlno");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			}else if (action.equals("Submit")) {
				cache.loadForm(tableForm);//������ʷ��ѯ����
			}else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String jnlno = tableForm.getProperty("jnlno");// ��ˮ��
			String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
			String projectName = tableForm.getProperty("projectName");// 
			String maintDivision = tableForm.getProperty("maintDivision");// ����ά��վ			
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			String auditStatus = tableForm.getProperty("auditStatus");// ���״̬

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
				
				//��ѯά�����ں�ͬ   ���ݺ�ͬ����������ǰ3��������
				/*String today=DateUtil.getNowTime("yyyy-MM-dd");
				String datestr=DateUtil.getDate(today, "MM", 3);
				tableForm.setProperty("hiddatestr",datestr);*/

				String maintStation="%";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					maintStation=userInfo.getStorageId();
				}
				

				String sql = "select a,b.comname,c.pullname,s.storagename " +
						" from LostElevatorReport a,Company b,Pulldown c,Storageid s"+
						" where a.maintDivision=b.comid and a.contractNatureOf=c.id.pullid " +
						" and c.id.typeflag='LostElevatorReport_ContractNatureOf'"+
						" and a.maintStation = s.storageid"+
						" and a.maintStation like '"+maintStation+"'";
				
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
				if (submitType != null && !submitType.equals("")) {
					sql += " and a.submitType like '"+submitType.trim()+"'";
				}
				if (auditStatus != null && !auditStatus.equals("")) {
					sql += " and isnull(a.auditStatus,'N') like '"+auditStatus.trim()+"'";
				}
				
				if (table.getIsAscending()) {
					sql += "order by a."+table.getSortColumn()+ " desc";
				} else {
					sql += "order by a."+table.getSortColumn()+ " asc";
				}
				
				query = hs.createQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List lostElevatorReportList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					LostElevatorReport report = (LostElevatorReport) objs[0];
					report.setMaintDivision(objs[1].toString());
					report.setContractNatureOf(objs[2].toString());
					report.setMaintStation(objs[3].toString());
					
					lostElevatorReportList.add(report);
				}

				table.addAll(lostElevatorReportList);
				session.setAttribute("lostElevatorReportList", table);
				
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
			forward = mapping.findForward("lostElevatorReportList");
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
		
		request.setAttribute("navigator.location","ά����ͬ���� >> ��ѯ�б� ");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "lostElevatorReportNextList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fLostElevatorReportNext");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("contractEdate");
			table.setIsAscending(true);
			cache.updateTable(table);

			
			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String billNo = tableForm.getProperty("billNo");
			String maintContractNo=tableForm.getProperty("maintContractNo");
			String maintDivision = tableForm.getProperty("maintDivision");
			String companyID = tableForm.getProperty("companyID");
			String projectName = tableForm.getProperty("projectName");// ��Ŀ����
			String salesContractNo = tableForm.getProperty("salesContractNo");//���ۺ�ͬ��
						
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
				
				String maintStation="%";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					maintStation=userInfo.getStorageId();
				}

				String sql = "select a,b.comname,c.companyName,d.username,s.storagename " +
						"from MaintContractMaster a,Company b,Customer c,Loginuser d,Storageid s "+
						"where a.maintDivision=b.comid and a.companyId=c.companyId " +
						"and a.attn=d.userid and a.maintStation=s.storageid"+
						" and a.contractStatus in ('ZB','XB')" + 
						" and a.auditStatus = 'Y'" +
						" and a.taskSubFlag = 'Y'" +
						" and a.maintStation like '"+maintStation+"'" +
						" and a.billNo not in(select billNo from LostElevatorReport) ";
				
				if (billNo != null && !billNo.equals("")) {
					sql += " and a.billNo like '%"+billNo.trim()+"%'";
				}
				if(maintContractNo!=null && !maintContractNo.equals("")){
					sql+=" and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
				}
				if (companyID != null && !companyID.equals("")) {
					sql += " and c.companyName like '%"+companyID.trim()+"%'";
				}				
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and a.maintDivision like '"+maintDivision.trim()+"'";
				}
				
				if (salesContractNo != null && !salesContractNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
				}
				if (projectName != null && !projectName.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractDetail where projectName like '%"+projectName.trim()+"%')";
				}
				
				if (table.getIsAscending()) {
					sql += " order by a."+ table.getSortColumn() +" asc";
				} else {
					sql += " order by a."+ table.getSortColumn() +" desc";
				}
				
				//System.out.println(">>>"+sql);
				
				query = hs.createQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List lostElevatorReportNextList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					MaintContractMaster master=(MaintContractMaster)objs[0];
					master.setMaintDivision(String.valueOf(objs[1]));
					master.setCompanyId(objs[2].toString());
					master.setAttn(objs[3].toString());
					master.setMaintStation(objs[4].toString());
					lostElevatorReportNextList.add(master);
				}

				table.addAll(lostElevatorReportNextList);
				session.setAttribute("lostElevatorReportNextList", table);
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
			forward = mapping.findForward("lostElevatorReportNextList");
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
		
		request.setAttribute("navigator.location","ά����ͬ�˱����� >> �鿴");
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
				String upsql="update LostElevatorReport set workisdisplay='"+workisdisplay+"' where jnlno='"+id+"'";
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

		String isOpen=request.getParameter("isOpen");
		String isopenshow=request.getParameter("isopenshow");
		
		request.setAttribute("display", "yes");
		request.setAttribute("isOpen", isOpen);
		request.setAttribute("isopenshow", isopenshow);
		
		String typejsp= request.getParameter("typejsp");
		if(typejsp!=null){
			request.setAttribute("typejsp", typejsp);
		}
		
		forward = mapping.findForward("lostElevatorReportDisplay");
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

		request.setAttribute("navigator.location","ά����ͬ�˱����� >> ���");
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		String billNo=request.getParameter("billNo");
		LostElevatorReport report=new LostElevatorReport();
		MaintContractMaster master=new MaintContractMaster();
		String dividion="",station="",status="",projectName="";
		int num=0;
		
		Session hs = null;
		Query query = null;
		try {
			hs = HibernateUtil.getSession();
			String sql="select a,b.comname,c.storagename "+/*,d.pullname*/
			"from MaintContractMaster a,Company b,Storageid c "+//,Pulldown d
			"where a.maintDivision=b.comid and a.maintStation=c.storageid"+ //and a.contractStatus=d.id.pullid
			" and a.billNo='"+billNo+"'";
			List list=hs.createQuery(sql).list();
			if(list!=null && list.size()>0){
				for(Object object : list){
					Object[] objs=(Object[])object;
					master=(MaintContractMaster) objs[0];
					dividion=objs[1].toString();
					station=objs[2].toString();
//					status=objs[3].toString();
				}

				//�Ų�ʱ��ʷ�ĵ��ݣ��˱��ĵ���
				List numList=hs.createSQLQuery("select billNo,count(*) num from MaintContractDetail "
						+ "where billNo='"+billNo+"' and isnull(isSurrender,'N')='N' and isnull(elevatorStatus,'')=''"
						+ " group by billNo").list();
				if(numList!=null && numList.size()>0){
					for(Object object : numList){
						Object[] objs=(Object[])object;
						num=Integer.valueOf(objs[1].toString());
					}
				}
				
				List nameList=hs.createQuery("select distinct projectName from MaintContractDetail where billNo='"+billNo+"'").list();
				if(nameList!=null && nameList.size()>0){
					for(int i=0;i<nameList.size();i++){
						projectName+=i==nameList.size()-1 ? nameList.get(i).toString() : nameList.get(i).toString()+"��";
					}
				}
				List entrustList=hs.createQuery("from EntrustContractMaster where maintContractNo='"+master.getMaintContractNo()+"'").list();
				if(entrustList!=null && entrustList.size()>0){
					EntrustContractMaster entrust=(EntrustContractMaster) entrustList.get(0);
					report.setContractNatureOf(entrust.getContractNatureOf());
				}else{
					report.setContractNatureOf(master.getContractNatureOf()+master.getMainMode());
				}
				report.setBillNo(master.getBillNo());
				report.setMaintContractNo(master.getMaintContractNo());
				report.setMaintDivision(master.getMaintDivision());
				report.setMaintStation(master.getMaintStation());
//				report.setContractNatureOf(master.getContractStatus());
				report.setEleNum(num);
				report.setProjectName(projectName);
				report.setLostElevatorDate(CommonUtil.getToday());
				String[] a={"a.id.typeflag='LostElevatorReport_ContractNatureOf'"};
				status=bd.getName("Pulldown", "pullname", "id.pullid", report.getContractNatureOf(),a);
			}
			
			request.setAttribute("lostElevatorReportBean", report);
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e1) {
			e1.printStackTrace();
		} /*catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} */ catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		// ά���ֲ�����
		request.setAttribute("maintDivisionName", dividion);
		
		request.setAttribute("maintStationName", station);
		
		request.setAttribute("contractNatureOfName", status);
		
		request.setAttribute("causeAnalysisList", bd.getPullDownList("LostElevatorReport_CauseAnalysis"));
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		return mapping.findForward("lostElevatorReportAdd");
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

		//��ֹ���ظ��ύ
		if(isTokenValid(request, true)){
			addOrUpdate(form,request,response,errors);// �������޸ļ�¼
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
		
		request.setAttribute("navigator.location","ά����ͬ�˱����� >> �޸�");	
		//DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		display(form, request, errors, "");
		
		request.setAttribute("causeAnalysisList", bd.getPullDownList("LostElevatorReport_CauseAnalysis"));// ǩ��ʽ�������б�	

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		//��ҳ����������
		String isclosework=request.getParameter("isclosework");
		if(isclosework!=null && isclosework.equals("Y")){
			request.setAttribute("isclosework", isclosework);
		}
			
		forward = mapping.findForward("lostElevatorReportModify");
		
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
		
		addOrUpdate(form,request,response,errors);// �������޸ļ�¼
		
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
	 * �ύ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws HibernateException
	 */
	public ActionForward toReferRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		refer(form,request,errors,request.getParameter("id")); //�ύ

		if (!errors.isEmpty()){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.submitToAudit.failed")); //��ʾ���ύ���ʧ�ܣ���
		} else {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.submitToAudit.succeed")); //��ʾ���ύ��˳ɹ�����
		}
		
		if (!errors.isEmpty()){
			this.saveErrors(request, errors);
		}

		return mapping.findForward("returnList");

	}
	

	/**
	 * ɾ����¼
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
			LostElevatorReport master = (LostElevatorReport) hs.get(LostElevatorReport.class, id);
			if (master != null) {
				hs.delete(master);
				//ɾ�����ݿ⸽����Ϣ
				String sqldel="delete from ContractFileinfo where MaterSid='"+id+"' and BusTable='LostElevatorReport'";
				hs.connection().prepareStatement(sqldel).execute();
				
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
		
		return response;
	}
	
	public void display(ActionForm form, HttpServletRequest request ,ActionErrors errors ,String flag){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;		
		String id = request.getParameter("id");
		/*if(id==null || "".equals(id)){
			id=(String) dform.get("id");
		}*/
		if(id==null || "".equals(id)){
			id=(String) request.getAttribute("id");
		}
		
		Session hs = null;
		LostElevatorReport report=new LostElevatorReport();
	
		if (id != null && !id.equals("")) {			
			try {
				hs = HibernateUtil.getSession();
				String sql="select a,b.comname,c.storagename "+
				"from LostElevatorReport a,Company b,Storageid c "+ 
				"where a.maintDivision=b.comid and a.maintStation=c.storageid "+ 
				" and a.jnlno='"+id+"'";
				Query query = hs.createQuery(sql);
				List list = query.list();
				String division="",station="",nature="",cause="";
				if (list != null && list.size() > 0) {
					for(Object object : list){
						Object[] objs=(Object[])object;
						report=(LostElevatorReport) objs[0];
						division=objs[1].toString();
						station=objs[2].toString();
						String[] a={"a.id.typeflag='LostElevatorReport_ContractNatureOf'"};
						nature=bd.getName("Pulldown", "pullname", "id.pullid", report.getContractNatureOf(), a);
						String[] b={"a.id.typeflag='LostElevatorReport_CauseAnalysis'"};
						cause=bd.getName("Pulldown", "pullname", "id.pullid", report.getCauseAnalysis(), b);
					}
					dform.set("id", report.getJnlno());
					if("display".equals(flag)){
						report.setHfOperid(bd.getName("Loginuser", "username", "userid", report.getHfOperid()));
						report.setAuditOperid(bd.getName("Loginuser", "username", "userid", report.getAuditOperid()));
						report.setAuditOperid2(bd.getName("Loginuser", "username", "userid", report.getAuditOperid2()));
						report.setAuditOperid3(bd.getName("Loginuser", "username", "userid", report.getAuditOperid3()));
						report.setOperId(bd.getName("Loginuser", "username", "userid", report.getOperId()));
						report.setMaintDivision(division);
						report.setMaintStation(station);
						report.setContractNatureOf(nature);
						report.setCauseAnalysis(cause);
					}else{
						request.setAttribute("maintDivisionName", division);
						request.setAttribute("maintStationName", station);
						request.setAttribute("contractNatureOfName", nature);
						request.setAttribute("causeName", cause);
					}
					request.setAttribute("lostElevatorReportBean", report);
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				
				//��ȡ�Ѿ��ϴ��ĸ���
				List filelst=this.FileinfoList(hs, id.trim(), "LostElevatorReport");
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
			
		}		
		
	}
	


	/**
	 * �������ݷ���
	 * @param form
	 * @param request
	 * @return ActionErrors
	 */
	public void addOrUpdate(ActionForm form, HttpServletRequest request,HttpServletResponse response,ActionErrors errors) {
		
		DynaActionForm dform = (DynaActionForm) form;

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		LostElevatorReport master = null;
		String id = (String) dform.get("id");
		String billNo = null;
		//String submitType=null;

		Session hs = null;
		Transaction tx = null;
			//System.out.println(id);
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			
			if (id != null && !id.equals("")) { // �޸�		
				master = (LostElevatorReport) hs.get(LostElevatorReport.class, id);
				billNo = master.getJnlno();
				//submitType=master.getSubmitType();
			} else { // ����
				master = new LostElevatorReport();	
				
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				billNo = CommonUtil.getBillno(year,"LostElevatorReport", 1)[0];// ������ˮ��		
				//submitType="N";
			}
			
			BeanUtils.populate(master, dform.getMap()); // ������������ֵ
			request.setAttribute("id", billNo);
			master.setJnlno(billNo);// ��ˮ��
			master.setOperId(userInfo.getUserID());// ¼����
			master.setOperDate(CommonUtil.getNowTime());// ¼��ʱ��
			//master.setSubmitType(submitType);
			master.setAuditStatus(null);
			master.setAuditDate("");
			master.setAuditOperid("");
			master.setAuditRem("");
			master.setIsCharge("");//�Ƿ�ۿ�
			master.setAuditStatus2(null);
			master.setAuditDate2("");
			master.setAuditOperid2("");
			master.setAuditRem2("");
			master.setAuditStatus3(null);
			master.setAuditDate3("");
			master.setAuditOperid3("");
			master.setAuditRem3("");
			master.setWorkisdisplay(null);
			master.setWorkisdisplay2(null);
			master.setWorkisdisplay3(null);
			
			String fhrem=master.getFhRem();
			if(fhrem!=null && !fhrem.trim().equals("")){
				master.setHfRemLast(fhrem);//��һ�λطý��
			}
			master.setHfDate("");
			master.setHfOperid("");
			master.setFhRem("");//�طý��
			
			String isreturn = request.getParameter("isreturn");
			if (isreturn != null && isreturn.equals("Y")) {
				master.setSubmitType("Y");
			}else{
				master.setSubmitType("N");
			}
			
			hs.save(master);
			
			// �����ļ�
			String path = "LostElevatorReport.file.upload.folder";
			String tableName = "LostElevatorReport";//���� ά����ͬ�˱����ݱ���
			String userid = userInfo.getUserID();

			List fileInfoList = this.saveFile(dform,request,response, path, billNo);
			boolean istrue=this.saveFileInfo(hs,fileInfoList,tableName,billNo, userid);
			
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
		
	public void refer(ActionForm form, HttpServletRequest request,ActionErrors errors, String id){

		if(id != null && !id.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			LostElevatorReport master = null;				
			
			try {
				
				hs = HibernateUtil.getSession();		
				tx = hs.beginTransaction();
				
				master = (LostElevatorReport) hs.get(LostElevatorReport.class, id);
				master.setSubmitType("Y");
				master.setAuditStatus(null);
				master.setAuditDate("");
				master.setAuditOperid("");
				master.setAuditRem("");
				master.setIsCharge("");//�Ƿ�ۿ�
				master.setAuditStatus2(null);
				master.setAuditDate2("");
				master.setAuditOperid2("");
				master.setAuditRem2("");
				master.setAuditStatus3(null);
				master.setAuditDate3("");
				master.setAuditOperid3("");
				master.setAuditRem3("");
				master.setWorkisdisplay(null);
				master.setWorkisdisplay2(null);
				master.setWorkisdisplay3(null);
				
				String fhrem=master.getFhRem();
				if(fhrem!=null && !fhrem.trim().equals("")){
					master.setHfRemLast(fhrem);//��һ�λطý��
				}
				master.setHfDate("");
				master.setHfOperid("");
				master.setFhRem("");
				
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
	/**
	 * ajax ���� �ֲ���ά��վ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void toAjaxDeleteRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session hs=null;
		Transaction tx = null;
		String jnlnostr=request.getParameter("jnlnostr");

		String isdele="Y";
		try {
			hs=HibernateUtil.getSession();
			if(jnlnostr!=null && !"".equals(jnlnostr)){
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
				
				LostElevatorReport master = (LostElevatorReport) hs.get(LostElevatorReport.class, jnlnostr);
				if (master != null) {
					hs.delete(master);
				}
				
				tx.commit();
			}
		} catch (Exception e) {
			isdele="N";
			e.printStackTrace();
		} finally{
			hs.close();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		sb.append("<rows>");
		sb.append("<cols name='isdele' value='"+isdele+"'>").append("</cols>");
		sb.append("</rows>");
		sb.append("</root>");

		response.setHeader("Content-Type","text/html; charset=GBK");
		response.setCharacterEncoding("gbk"); 
		response.setContentType("text/xml;charset=gbk");
		response.getWriter().write(sb.toString());
	}
	

	/**
	 * �����ϴ��ļ�
	 */
	public List saveFile(ActionForm form,HttpServletRequest request, HttpServletResponse response,
			String path,String billno){
		List returnList = new ArrayList();
		int filenum=0;
		int fileCount=0;

		path =  PropertiesUtil.getProperty(path).trim();//�ϴ�Ŀ¼ 

		 FormFile formFile = null;
		 Fileinfo file=null;
		 if (form.getMultipartRequestHandler() != null) {
			 Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			 if (hash != null) {
				 
				 Iterator it = hash.values().iterator();
				 HandleFile hf = new HandleFileImpA();
				 
				 while (it.hasNext()) {
					 
					 fileCount++;
					 formFile = (FormFile) it.next();
					 if (formFile != null) {
						 try {
							 String today=DateUtil.getCurDate();
							 String time=DateUtil.getDateTime("yyyyMMddHHmmss");
							 String newfilename=time+"_"+fileCount+"_"+formFile.getFileName();
							 Map map = new HashMap();
							 map.put("oldfilename", formFile.getFileName());
							 map.put("newfilename",newfilename);
							 map.put("filepath", today.substring(0,7)+"/");
							 map.put("filesize", new Double(formFile.getFileSize()));
							 map.put("fileformat",formFile.getContentType());
							 map.put("rem","");

							// �����ļ����ļ�ϵͳ
							hf.createFile(formFile.getInputStream(),path+today.substring(0,7)+"/", newfilename);
							returnList.add(map);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
					 }
				 }
			 }
		 }
		return returnList;
	}
	
	/**
	 * �����ļ���Ϣ�����ݿ�
	 */
	public boolean saveFileInfo(Session hs,List fileInfoList,String tableName,String billno,String userid){
		boolean saveFlag = true;
		ContractFileinfo file=null;
		if(null != fileInfoList && !fileInfoList.isEmpty()){
			
			try {
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					 String oldfilename =(String) map.get("oldfilename");
					 String newfilename =(String) map.get("newfilename");
					 String filepath =(String) map.get("filepath");
					 Double filesize =(Double) map.get("filesize");
					 String fileformat =(String) map.get("fileformat");
					 String rem =(String) map.get("rem");
					 
					 file = new ContractFileinfo();
					 file.setMaterSid(billno);
					 file.setBusTable(tableName);
					 file.setOldFileName(oldfilename);
					 file.setNewFileName(newfilename);
					 file.setFilePath(filepath);
					 file.setFileSize(filesize);
					 file.setFileFormat(fileformat);
					 file.setUploadDate(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
					 file.setUploader(userid);
					 file.setRemarks(rem);
					 
					 hs.save(file);
					 hs.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
			}
		}
		return saveFlag;
	}
	/**
	 * ɾ���ļ�
	 */
	public ActionForward toDelFileRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Session hs = null;
		Transaction tx = null;
		String id = request.getParameter("filesid");
		String delsql="";
		List list=null;
		
		String folder = PropertiesUtil.getProperty("LostElevatorReport.file.upload.folder");
		
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if(id!=null && id.length()>0){
				
				String sql="select a from ContractFileinfo a where a.fileSid='"+id.trim()+"'";
				list=hs.createQuery(sql).list();	//ȡ�ļ���Ϣ
				hs.flush();
				
				delsql="delete from ContractFileinfo where fileSid='"+id.trim()+"'";
				hs.connection().prepareStatement(delsql).execute();
				hs.flush();
				
				HandleFile hf = new HandleFileImpA();
				if(list!=null && !list.isEmpty()){
					ContractFileinfo fp=(ContractFileinfo)list.get(0);
				
					String newfilename=fp.getNewFileName();
					String filepath=fp.getFilePath();
					String localPath = folder+filepath+newfilename;
					hf.delFile(localPath);//ɾ�������е��ļ�
				}
			}
			
	        response.setContentType("text/xml; charset=UTF-8");
	      
			//�������������
	        PrintWriter out = response.getWriter();
	        //������֤��������ͬ��������Ϣ	       
	        out.println("<response>");  
	        int b=list.size();
			if(b==0){
				out.println("<res>" + "N" + "</res>");
			}else{
				out.println("<res>" + "Y" + "</res>");
			}
			 out.println("</response>");
		     out.close();
		     	
		     tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(hs!=null){
					hs.close();
				}
			} catch (HibernateException hex) {
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}	
		return null;
	}
	/**
	 * �����ļ�
	 */
	public ActionForward toDownloadFileRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		/*
		 * ȡ���ļ�:�ļ�id+�ļ�·��+�ļ���+�� �ļ�id=ͨ��formbeanȡ�� �ļ�·��=ͨ��ȡ�������ļ��ķ����õ�
		 * �ļ�����=ͨ�����ݿ�õ� ��=io
		 */
		Session hs = null;

		String filesid =request.getParameter("filesid");//��ˮ��

		String localPath="";
		String oldname="";
		
		String folder = PropertiesUtil.getProperty("LostElevatorReport.file.upload.folder");
		
		try {
			hs = HibernateUtil.getSession();
			String sqlfile="select a from ContractFileinfo a where a.fileSid='"+filesid+"'";
			List list2=hs.createQuery(sqlfile).list();

			if(list2!=null && list2.size()>0){
				ContractFileinfo fp=(ContractFileinfo)list2.get(0);
				
				String filepath=fp.getFilePath();
				String newnamefile=fp.getNewFileName();
				oldname=fp.getOldFileName();
				localPath = folder+filepath+newnamefile;
			}
		
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
	
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(oldname, "utf-8"));
	
			fis = new FileInputStream(localPath);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
	
			int bytesRead = 0;
			byte[] buffer = new byte[5 * 1024];
			while ((bytesRead = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
				bos.flush();
			}
			if (fos != null) {fos.close();}
			if (bos != null) {bos.close();}
			if (fis != null) {fis.close();}
			if (bis != null) {bis.close();}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {				
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	
		return null;
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


