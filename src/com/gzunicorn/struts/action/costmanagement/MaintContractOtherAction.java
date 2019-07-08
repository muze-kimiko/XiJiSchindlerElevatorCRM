package com.gzunicorn.struts.action.costmanagement;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
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
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.JSONUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SqlBeanUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.basedata.elevatorsales.ElevatorSalesInfo;
import com.gzunicorn.hibernate.contractpaymentmanage.contractpaymentmanage.ContractPaymentManage;
import com.gzunicorn.hibernate.custregistervisitplan.customervisitplandetail.CustomerVisitPlanDetail;
import com.gzunicorn.hibernate.custregistervisitplan.customervisitplanmaster.CustomerVisitPlanMaster;
import com.gzunicorn.hibernate.engcontractmanager.ContractFileinfo.ContractFileinfo;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.maintcontractother.MaintContractOther;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.struts.action.query.SearchElevatorSaleAction;
import com.gzunicorn.workflow.bo.JbpmExtBridge;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class MaintContractOtherAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractOtherAction.class);
	
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
	 * ά����ͬҵ���֧������
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
		
		if(!"toDisplayRecord".equals(name)){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "maintcontractother", null);
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
	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ҵ���֧������ >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		HashMap hmap=new HashMap();
		if (tableForm.getProperty("genReport") != null
				&& tableForm.getProperty("genReport").equals("Y")) {
			try {
				//����excel
				response = toExcelRecord(form,request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = mapping.findForward("exportExcel");
			tableForm.setProperty("genReport","");

		}else{
			HTMLTableCache cache = new HTMLTableCache(session, "maintContractOtherList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fMaintContractOther");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("a.r9");
			table.setIsAscending(false);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			}else if (action.equals("Submit")) {
				cache.loadForm(tableForm);
			}else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String billNo = tableForm.getProperty("billNo");// ��ˮ��
			String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
			String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
			String maintdivision = tableForm.getProperty("maintdivision");// ����ά���ֲ�
			String maintstation = tableForm.getProperty("maintstation");// ����ά��վ
			String invoicename = tableForm.getProperty("invoiceName");// ��Ʊ����
			
			//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			if(maintdivision == null || maintdivision.equals("")){
				Map map = (Map)maintDivisionList.get(0);
				maintdivision = (String)map.get("grcid");
			}
			
			Session hs = null;
			SQLQuery query = null;
			try {

				hs = HibernateUtil.getSession();
				//֧��������ڴ��� 2016-08-05�ĺ�ͬ
				String sql = "select a.billNo,a.maintContractNo,"
						+ "c.comname,d.invoiceName,s.storagename,"
						+ "a.otherFee,isnull(a.r1,'') as r1,"
						+ "(select isnull(SUM(mco.paymoney),0) from MaintContractOther mco where mco.billno=a.billno) as paymoney "
						+ "from MaintContractMaster a,Company c,Customer d,storageid s "
						+ "where a.maintDivision = c.comid "
						+ " and a.maintStation = s.storageid "
						+ " and a.companyId = d.companyId "
						+ " and isnull(a.OtherFee,0)>0 ";
						//+ " and a.AuditDate>='2016-08-05' ";
				if (maintdivision != null && !maintdivision.equals("")) {
					sql += " and a.maintDivision like '"+maintdivision.trim()+"'";
				}
				if (maintstation != null && !maintstation.equals("")) {
					sql += " and a.maintStation like '"+maintstation.trim()+"'";
				}
				if (billNo != null && !billNo.equals("")) {
					sql += " and a.billNo like '%"+billNo.trim()+"%'";
				}
				if (maintContractNo != null && !maintContractNo.equals("")) {
					sql += " and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
				}
				if (invoicename != null && !invoicename.equals("")) {
					sql += " and d.invoiceName like '%"+invoicename.trim()+"%'";
				}
				if (salesContractNo != null && !salesContractNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
				}

				if (table.getIsAscending()) {
					sql += " order by "+table.getSortColumn()+ " asc";
				} else {
					sql += " order by "+table.getSortColumn()+" desc";
				}
				
				//System.out.println(sql);
				
				query = hs.createSQLQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;
                
				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List maintContractList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map master=new HashMap();
					master.put("billno",String.valueOf(objs[0]));
					master.put("maintcontractno",String.valueOf(objs[1]));
					master.put("maintdivision", String.valueOf(objs[2]));
					master.put("maintstation", String.valueOf(objs[4]));
					master.put("invoicename", String.valueOf(objs[3]));
					master.put("r9", String.valueOf(objs[6]));
					
					double otherfee=Double.parseDouble(String.valueOf(objs[5]));
					double paymoney=Double.parseDouble(String.valueOf(objs[7]));
					master.put("otherfee", otherfee);
					master.put("nototherfee", otherfee-paymoney);
					
					maintContractList.add(master);
				}

				table.addAll(maintContractList);
				session.setAttribute("maintContractOtherList", table);
				
				//�ֲ��������б�
				//List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
				request.setAttribute("maintDivisionList", maintDivisionList);
				//ά��վ������
				List mainStationList=null;
				if(maintdivision!=null && !maintdivision.trim().equals("%")){
					String hql="select a from Storageid a where a.comid like '"+maintdivision+"' " +
							"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
					mainStationList=hs.createQuery(hql).list();
				}else{
					String hql="select a from Storageid a where a.comid like '"+userInfo.getComID()+"' " +
							"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
					mainStationList=hs.createQuery(hql).list();
				}
				 Storageid storid=new Storageid();
				 storid.setStorageid("%");
				 storid.setStoragename("ȫ��");
				 mainStationList.add(0,storid);
				 
				 request.setAttribute("mainStationList", mainStationList);
				
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
			forward = mapping.findForward("maintContractOtherList");
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
		
		request.setAttribute("navigator.location","ҵ���֧������ >> �鿴");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		String billno=request.getParameter("id");
		
		Session hs = null;
		if(billno!=null && !billno.trim().equals("")){
			try{
				hs = HibernateUtil.getSession();
				
				//����Ϣ
				String sql="select a.billNo,a.maintContractNo,c.comname,d.invoicename,s.storagename,a.otherFee," +
						"(select isnull(SUM(mco.paymoney),0) from MaintContractOther mco where mco.billno=a.billno) as paymoney " +
						"from MaintContractMaster a,Company c,Customer d,storageid s where a.maintDivision = c.comid " +
						"and a.maintStation = s.storageid  and a.companyId = d.companyId  and a.BillNo='"+billno+"'";
				ResultSet rs=hs.connection().prepareStatement(sql).executeQuery();

				HashMap master=new HashMap();
				if(rs.next()){
					master.put("billno",rs.getString("billNo"));
					master.put("maintcontractno",rs.getString("maintContractNo"));
					master.put("maintdivision", rs.getString("comname"));
					master.put("maintstation", rs.getString("storagename"));
					master.put("invoicename", rs.getString("invoicename"));
					
					double otherfee=Double.parseDouble(rs.getString("otherFee"));
					double paymoney=Double.parseDouble(rs.getString("paymoney"));
					master.put("otherfee", otherfee);
					master.put("nototherfee", otherfee-paymoney);
					
				}
				request.setAttribute("masterhmap", master);
				
				//��ϸ
				String sqlde="select a,b.username from MaintContractOther a,Loginuser b " +
						"where a.operid=b.userid and a.billno='"+billno+"'";
				List mcolist=hs.createQuery(sqlde).list();
				if(mcolist!=null && mcolist.size()>0){
					List relist=new ArrayList();
					MaintContractOther mco=null;
					for(int i=0;i<mcolist.size();i++){
						Object[] objs=(Object[])mcolist.get(i);
						mco=(MaintContractOther)objs[0];
						String username=(String)objs[1];
						mco.setOperid(username);
						
						relist.add(mco);
					}
					
					request.setAttribute("mcolist", relist);
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
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		request.setAttribute("display", "yes");

		forward = mapping.findForward("maintContractOtherDisplay");
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
		
		request.setAttribute("navigator.location","ҵ���֧������ >> �� ��");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String billno=(String)dform.get("id");
		if(billno==null || billno.trim().equals("")){
			billno = request.getParameter("id");
		}
		
		//��ʼ������
		if (request.getAttribute("error") == null || request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		
		Session hs = null;
		if(billno!=null && !billno.trim().equals("")){
			try{
				hs = HibernateUtil.getSession();
				String curdate=DateUtil.getNowTime("yyyy-MM-dd");
				String curdatetime=DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss");
				
				dform.set("paydate", curdate);
				//����Ϣ
				String sql="select a.billNo,a.maintContractNo,c.comname,d.invoicename,s.storagename,a.otherFee," +
						"(select isnull(SUM(mco.paymoney),0) from MaintContractOther mco where mco.billno=a.billno) as paymoney " +
						"from MaintContractMaster a,Company c,Customer d,storageid s where a.maintDivision = c.comid " +
						"and a.maintStation = s.storageid  and a.companyId = d.companyId  and a.BillNo='"+billno+"'";
				ResultSet rs=hs.connection().prepareStatement(sql).executeQuery();

				HashMap master=new HashMap();
				if(rs.next()){
					master.put("billno",rs.getString("billNo"));
					master.put("maintcontractno",rs.getString("maintContractNo"));
					master.put("maintdivision", rs.getString("comname"));
					master.put("maintstation", rs.getString("storagename"));
					master.put("invoicename", rs.getString("invoicename"));
					
					double otherfee=Double.parseDouble(rs.getString("otherFee"));
					double paymoney=Double.parseDouble(rs.getString("paymoney"));
					master.put("otherfee", otherfee);
					master.put("nototherfee", otherfee-paymoney);
				}
				master.put("operid", userInfo.getUserName());
				master.put("operdate", curdatetime);
				request.setAttribute("masterhmap", master);
				
				//��ϸ
				String sqlde="select a,b.username from MaintContractOther a,Loginuser b " +
						"where a.operid=b.userid and a.billno='"+billno+"'";
				List mcolist=hs.createQuery(sqlde).list();
				if(mcolist!=null && mcolist.size()>0){
					List relist=new ArrayList();
					MaintContractOther mco=null;
					for(int i=0;i<mcolist.size();i++){
						Object[] objs=(Object[])mcolist.get(i);
						mco=(MaintContractOther)objs[0];
						String username=(String)objs[1];
						mco.setOperid(username);
						
						relist.add(mco);
					}
					
					request.setAttribute("mcolist", relist);
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
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		forward = mapping.findForward("maintContractOtherModify");
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
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		Session hs = null;
		Transaction tx = null;
		
		String billno=(String)dform.get("billno");
		String paydate=(String)dform.get("paydate");
		Double paymoney=(Double)dform.get("paymoney");
		String rem=(String)dform.get("rem");
		
		try {
			
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			String curdatetime=DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss");
			
			MaintContractOther mco=new MaintContractOther();
			mco.setBillno(billno);
			mco.setPaydate(paydate);
			mco.setPaymoney(paymoney);
			mco.setRem(rem);
			mco.setOperid(userInfo.getUserID());
			mco.setOperdate(curdatetime);
			hs.save(mco);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (HibernateException e2) {
				log.error(e2.getMessage());
			}
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		String isreturn = request.getParameter("isreturn");
		if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			forward = mapping.findForward("returnList");
		} else {
			if (errors.isEmpty()) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			} else {
				request.setAttribute("error", "Yes");
			}
			
			dform.set("id",billno);
			forward = mapping.findForward("returnModify");			
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
	public ActionForward toPrepareDeleteRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ҵ���֧������ >> ɾ��");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		DynaActionForm dform = (DynaActionForm) form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		String billno=(String)dform.get("id");
		if(billno==null || billno.trim().equals("")){
			billno = request.getParameter("id");
		}
		
		Session hs = null;
		if(billno!=null && !billno.trim().equals("")){
			try{
				hs = HibernateUtil.getSession();
				
				//����Ϣ
				String sql="select a.billNo,a.maintContractNo,c.comname,d.invoicename,s.storagename,a.otherFee," +
						"(select isnull(SUM(mco.paymoney),0) from MaintContractOther mco where mco.billno=a.billno) as paymoney " +
						"from MaintContractMaster a,Company c,Customer d,storageid s where a.maintDivision = c.comid " +
						"and a.maintStation = s.storageid  and a.companyId = d.companyId  and a.BillNo='"+billno+"'";
				ResultSet rs=hs.connection().prepareStatement(sql).executeQuery();

				HashMap master=new HashMap();
				if(rs.next()){
					master.put("billno",rs.getString("billNo"));
					master.put("maintcontractno",rs.getString("maintContractNo"));
					master.put("maintdivision", rs.getString("comname"));
					master.put("maintstation", rs.getString("storagename"));
					master.put("invoicename", rs.getString("invoicename"));
					
					double otherfee=Double.parseDouble(rs.getString("otherFee"));
					double paymoney=Double.parseDouble(rs.getString("paymoney"));
					master.put("otherfee", otherfee);
					master.put("nototherfee", otherfee-paymoney);
					
				}
				request.setAttribute("masterhmap", master);
				
				//��ϸ
				String sqlde="select a,b.username from MaintContractOther a,Loginuser b " +
						"where a.operid=b.userid and a.billno='"+billno+"'";
				List mcolist=hs.createQuery(sqlde).list();
				if(mcolist!=null && mcolist.size()>0){
					List relist=new ArrayList();
					MaintContractOther mco=null;
					for(int i=0;i<mcolist.size();i++){
						Object[] objs=(Object[])mcolist.get(i);
						mco=(MaintContractOther)objs[0];
						String username=(String)objs[1];
						mco.setOperid(username);
						
						relist.add(mco);
					}
					
					request.setAttribute("mcolist", relist);
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
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		forward = mapping.findForward("maintContractOtherDelete");
		return forward;
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

		ActionErrors errors = new ActionErrors();				
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		Session hs = null;
		Transaction tx = null;
		
		String billno=(String)dform.get("billno");
		String[] hidrowid=request.getParameterValues("hidrowid");
		try {
			
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();

			String rowids="";
			if(hidrowid!=null && hidrowid.length>0){
				for(int i=0;i<hidrowid.length;i++){
					if(i==hidrowid.length-1){
						rowids+=hidrowid[i];
					}else{
						rowids+=hidrowid[i]+",";
					}
				}
			}
			String sqldel="delete from MaintContractOther where billno='"+billno+"' ";
			if(!rowids.equals("")){
				sqldel+=" and rowid not in("+rowids+")";
			}
			hs.connection().prepareStatement(sqldel).executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (HibernateException e2) {
				log.error(e2.getMessage());
			}
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
		}finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		String isreturn = request.getParameter("isreturn");
		if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			forward = mapping.findForward("returnList");
		} else {
			if (errors.isEmpty()) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			} else {
				request.setAttribute("error", "Yes");
			}
			
			dform.set("id",billno);
			forward = mapping.findForward("returnDelete");			
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	
	public HttpServletResponse toExcelRecord(ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("ҵ���֧������");
        
		ServeTableForm tableForm = (ServeTableForm) form;
		
		String billNo = tableForm.getProperty("billNo");// ��ˮ��
		String maintContractNo = tableForm.getProperty("maintContractNo");// ά����ͬ��
		String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
		String maintdivision = tableForm.getProperty("maintdivision");// ����ά���ֲ�
		String maintstation = tableForm.getProperty("maintstation");// ����ά��վ
		String invoicename = tableForm.getProperty("invoiceName");// ��Ʊ����

		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select a.billNo,a.maintContractNo,c.comname,d.invoicename,s.storagename,a.otherFee,a.r1," +
					"(select isnull(SUM(mco.paymoney),0) from MaintContractOther mco where mco.billno=a.billno) as paymoney," +
					"mo.paydate,mo.paymoney,mo.rem,mo.operid,mo.operdate,l.username,a.ContractSDate,a.ContractEDate " +
					"from MaintContractMaster a " +
					"left join MaintContractOther mo on a.BillNo=mo.billno " +
					"left join loginuser l on mo.operid=l.UserID," +
					"Company c,Customer d,storageid s " +
					"where a.maintDivision = c.comid " +
					"and a.maintStation = s.storageid  and a.companyId = d.companyId  " +
					"and isnull(a.OtherFee,0)>0 ";
					//"and a.AuditDate>='2016-08-05' ";
			if (maintdivision != null && !maintdivision.equals("")) {
				sql += " and a.maintDivision like '"+maintdivision.trim()+"'";
			}
			if (maintstation != null && !maintstation.equals("")) {
				sql += " and a.maintStation like '"+maintstation.trim()+"'";
			}
			if (billNo != null && !billNo.equals("")) {
				sql += " and a.billNo like '%"+billNo.trim()+"%'";
			}
			if (maintContractNo != null && !maintContractNo.equals("")) {
				sql += " and a.maintContractNo like '%"+maintContractNo.trim()+"%'";
			}
			if (invoicename != null && !invoicename.equals("")) {
				sql += " and d.invoicename like '%"+invoicename.trim()+"%'";
			}
			if (salesContractNo != null && !salesContractNo.equals("")) {
				sql += " and a.billNo in(select distinct billNo from MaintContractDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
			}
			sql += " order by a.r1 desc";

			ResultSet rs=hs.connection().prepareStatement(sql).executeQuery();
			List maintContractList = new ArrayList();
			while(rs.next()){
				Map master=new HashMap();
				master.put("billno",rs.getString("billNo"));
				master.put("maintcontractno",rs.getString("maintcontractno"));
				master.put("maintdivision", rs.getString("comname"));
				master.put("maintstation", rs.getString("storagename"));
				master.put("invoicename", rs.getString("invoicename"));
				master.put("r1", rs.getString("r1"));
				
				double otherfee=rs.getDouble("otherfee");
				double paymoney=rs.getDouble("paymoney");
				master.put("otherfee", String.valueOf(otherfee));
				master.put("nototherfee", String.valueOf(otherfee-paymoney));

				master.put("paydate",rs.getString("paydate"));
				master.put("paymoney", rs.getString("paymoney"));
				master.put("rem", rs.getString("rem"));
				master.put("operid", rs.getString("operid"));
				master.put("operdate", rs.getString("operdate"));
				master.put("username", rs.getString("username"));
				master.put("contractsdate", rs.getString("contractsdate"));
				master.put("contractedate", rs.getString("contractedate"));
				
				maintContractList.add(master);
			} 
   
			String[] titlename={"���","��ˮ��","ά���ֲ�","ά��վ","ά����ͬ��","��ͬ��ʼ����","��ͬ��������","��Ʊ����","ҵ����ܶ�","δ֧�����",
					"֧������","֧�����","��ע","¼����","¼������"};
			String[] titleid={"r1","billno","maintdivision","maintstation","maintcontractno","contractsdate","contractedate",
					"invoicename","otherfee","nototherfee","paydate","paymoney","rem","username","operdate"};
			
	        //������Ԫ����ʽ
	        XSSFCellStyle cs = wb.createCellStyle();
	        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
	        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
	        XSSFFont f  = wb.createFont();
	        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
	        cs.setFont(f);
	        
	        //��������
	        XSSFRow row0 = sheet.createRow( 0);
	        XSSFCell cell0 = null;
			for(int i=0;i<titlename.length;i++){
				cell0 = row0.createCell((short)i);
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(titlename[i]);
				cell0.setCellStyle(cs);
			}
			//��������
			if (maintContractList != null && maintContractList.size()>0) {
				XSSFRow row = null;
				XSSFCell cell =null;
				for (int j = 0; j < maintContractList.size(); j++) {
					HashMap map=(HashMap) maintContractList.get(j);
					// ����Excel�У���0�п�ʼ
					row = sheet.createRow(j+1);
					for(int c=0;c<titleid.length;c++){
					    cell = row.createCell((short)c);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					    if(titleid[c].equals("otherfee") || titleid[c].equals("nototherfee") || titleid[c].equals("paymoney")){
					    	
					    	String valstr=(String)map.get(titleid[c]);
					    	if(valstr!=null && !valstr.trim().equals("") && !valstr.equals("NULL")){
					    		cell.setCellValue(Double.parseDouble(valstr));
					    	}else{
					    		cell.setCellValue(valstr);
					    	}
					    	
					    }else{
					    	cell.setCellValue((String)map.get(titleid[c]));
					    }
					}
				}
			}
			
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
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("ҵ���֧������", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
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
	public void toStorageIDList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session hs=null;
		String comid=request.getParameter("comid");
		response.setHeader("Content-Type","text/html; charset=GBK");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs=HibernateUtil.getSession();
			if(comid!=null && !"".equals(comid)){
				String hql="select a from Storageid a where a.comid='"+comid+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0){
					sb.append("<rows>");
					for(int i=0;i<list.size();i++){
					Storageid sid=(Storageid)list.get(i);
					sb.append("<cols name='"+sid.getStoragename()+"' value='"+sid.getStorageid()+"'>").append("</cols>");
					}
					sb.append("</rows>");
								
					
				  }
			 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		finally{
			hs.close();
		}
		sb.append("</root>");
		
		response.setCharacterEncoding("gbk"); 
		response.setContentType("text/xml;charset=gbk");
		response.getWriter().write(sb.toString());
	}

	 
	
}	