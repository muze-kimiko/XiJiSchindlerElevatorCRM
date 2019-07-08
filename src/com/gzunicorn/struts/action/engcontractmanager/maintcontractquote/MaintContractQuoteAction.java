package com.gzunicorn.struts.action.engcontractmanager.maintcontractquote;

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
import java.util.Arrays;
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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.JSONUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;

import com.gzunicorn.hibernate.basedata.personnelmanage.PersonnelManageMaster;
import com.gzunicorn.hibernate.engcontractmanager.ContractFileinfo.ContractFileinfo;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotedetail.MaintContractQuoteDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquoteprocess.MaintContractQuoteProcess;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.workflow.bo.JbpmExtBridge;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class MaintContractQuoteAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintContractQuoteAction.class);
	
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
		if(!"toDelFileRecord".equals(name) && !"toDownloadFileRecord".equals(name) ){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,
					SysRightsUtil.NODE_ID_FORWARD + "maintcontractquote", null);
			/** **********�����û�Ȩ�޹���*********** */
		}

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
		
		request.setAttribute("navigator.location","ά�������������  >> ��ѯ�б�");		
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
			HTMLTableCache cache = new HTMLTableCache(session, "maintContractQuoteList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fMaintContractQuote");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("billNo");
			table.setIsAscending(false);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else if (action.equals("Submit")) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);
			
			String billNo = tableForm.getProperty("billNo");// ������ˮ��
			String companyName = tableForm.getProperty("companyName");// �׷���λ����
			String status = tableForm.getProperty("status");// ����״̬
			String maintDivision = tableForm.getProperty("maintDivision");// �׷���λid
			String submitType = tableForm.getProperty("submitType");// �ύ��־
			String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
			String elevatorNo = tableForm.getProperty("elevatorNo");// ���ݱ��
			String iscont = tableForm.getProperty("iscont");// ���ɺ�ͬ
			
			//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			if(maintDivision == null || maintDivision.equals("")){
				Map map = (Map)maintDivisionList.get(0);
				maintDivision = (String)map.get("grcid");
			}
			
			Session hs = null;
			SQLQuery query = null;
			try {

				hs = HibernateUtil.getSession();
				
				String maintStation="%";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					maintStation=userInfo.getStorageId();
				}
				
				String sql = "select a.*,b.username,c.comname,e.typename,s.storagename,"
						+ "isnull((select distinct quoteBillNo from MaintContractMaster where quoteBillNo=a.BillNo),'') as quoteBillNo"+
						" from MaintContractQuoteMaster a,Loginuser b,Company c,ViewFlowStatus e,StorageID s" + 
						" where a.attn = b.userid"+
						" and a.maintDivision = c.comid"+
						" and a.maintStation = s.storageid"+
						" and a.status = e.typeid" +
						" and a.maintStation like '"+maintStation+"'";

				if (billNo != null && !billNo.equals("")) {
					sql += " and a.billNo like '%"+billNo.trim()+"%'";
				}	
				if (companyName != null && !companyName.equals("")) {
					sql += " and a.companyName like '%"+companyName.trim()+"%'";
				}				
				if (status != null && !status.equals("")) {
					sql += " and a.status = '"+Integer.valueOf(status)+"'";
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					sql += " and c.comid like '"+maintDivision.trim()+"'";
				}	
				if (submitType != null && !submitType.equals("")) {
					sql += " and a.submitType like '"+submitType.trim()+"'";
				}

				if (salesContractNo != null && !salesContractNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractQuoteDetail where salesContractNo like '%"+salesContractNo.trim()+"%')";
				}
				if (elevatorNo != null && !elevatorNo.equals("")) {
					sql += " and a.billNo in(select distinct billNo from MaintContractQuoteDetail where elevatorNo like '%"+elevatorNo.trim()+"%')";
				}
				if (iscont != null && !iscont.equals("")) {
					if(iscont.equals("Y")){
						sql += " and a.billNo in(select distinct isnull(m.quoteBillNo,'')  from MaintContractMaster m,MaintContractQuoteMaster mm where m.quoteBillNo=mm.BillNo)";
					}else{
						sql += " and a.billNo not in(select distinct isnull(m.quoteBillNo,'')  from MaintContractMaster m,MaintContractQuoteMaster mm where m.quoteBillNo=mm.BillNo)";
						
					}
				}
				
				if (table.getIsAscending()) {
					sql += " order by "+ table.getSortColumn() +" asc";
				} else {
					sql += " order by "+ table.getSortColumn() +" desc";
				}
				
				//System.out.println(sql);
				
				query = hs.createSQLQuery(sql);					
				query.addEntity("a",MaintContractQuoteMaster.class);
				query.addScalar("username");
				query.addScalar("comname");
				query.addScalar("typename");
				query.addScalar("storagename");
				query.addScalar("quoteBillNo");
				
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List list = query.list();
				List maintContractQuoteList = new ArrayList();
				for (Object object : list) {
					Object[] objs = (Object[])object;
					Map map = BeanUtils.describe(objs[5]); // MaintContractQuoteMaster
					map.put("attnName", String.valueOf(objs[0])); // ����������
					map.put("maintDivisionName", String.valueOf(objs[1])); // �ֲ�����
					map.put("statusName", String.valueOf(objs[2])); // ����״̬
					map.put("storagename", String.valueOf(objs[3])); // ά��վ����
					
					String quoteBillNo=String.valueOf(objs[4]);
					if(quoteBillNo!=null && !quoteBillNo.trim().equals("")){
						map.put("iscontract", "��");
					}else{
						map.put("iscontract", "��");
					}
					/**
					String billno=(String)map.get("billNo");
					String sqlmas="select quoteBillNo from MaintContractMaster where quoteBillNo='"+billno.trim()+"'";
					List ecmlist=hs.createSQLQuery(sqlmas).list();
					if(ecmlist!=null && ecmlist.size()>0){
						map.put("iscontract", "��");
					}else{
						map.put("iscontract", "��");
					}
					*/
					maintContractQuoteList.add(map);
				}

				table.addAll(maintContractQuoteList);
				session.setAttribute("maintContractQuoteList", table);
				// �ֲ��������б�
				request.setAttribute("maintDivisionList", maintDivisionList);
				// ����״̬�������б�
				request.setAttribute("processStatusList", bd.getProcessStatusList());

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
			forward = mapping.findForward("maintContractQuoteList");
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
		
		request.setAttribute("navigator.location","ά�������������  >> �鿴");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		
		Session hs = null;
		List maintContractQuoteDetailList = null;

		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from MaintContractQuoteMaster where billNo = '"+id.trim()+"'");
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
					query = hs.createQuery("from MaintContractQuoteDetail where billNo = '"+id+"'");
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
					maintContractQuoteDetailList = query.list();
					for (Object object : maintContractQuoteDetailList) {
						MaintContractQuoteDetail detail = (MaintContractQuoteDetail)object;
						detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
						detail.setR4(bd.getName("Pulldown", "pullname", "id.pullid",detail.getSignWay()));//ǩ��ʽ
					}
					request.setAttribute("maintContractQuoteDetailList", maintContractQuoteDetailList);
					
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
				
				//��ȡ�����׼���۵����ϵ��
				request.setAttribute("returnhmap", bd.getCoefficientMap());
				
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
			
			//lijun add ��ҳת����ת�鿴�󣬲���Ҫ�ٴγ�������ҳ
			String workisdisplay=request.getParameter("workisdisplay");
			if(workisdisplay!=null && workisdisplay.equals("Y")){
				request.setAttribute("workisdisplay", workisdisplay);
				Session hs2 = null;
				Transaction tx2 = null;
				try {
					hs2 = HibernateUtil.getSession();
					tx2 = hs2.beginTransaction();
					
					String upsql="update MaintContractQuoteMaster set workisdisplay='"+workisdisplay+"' where billNo='"+id+"'";
					hs2.connection().prepareStatement(upsql).executeUpdate();
					
					tx2.commit();
				} catch (Exception e1) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
					try {
						tx2.rollback();
					} catch (HibernateException e2) {
						log.error(e2.getMessage());
						DebugUtil.print(e2, "Hibernate Transaction rollback error!");
					}
					e1.printStackTrace();
					log.error(e1.getMessage());
					DebugUtil.print(e1, "Hibernate region Insert error!");
				} finally {
					try {
						hs2.close();
					} catch (HibernateException hex) {
						log.error(hex.getMessage());
						DebugUtil.print(hex, "Hibernate close error!");
					}
				}
			}

			request.setAttribute("display", "yes");
			forward = mapping.findForward("maintContractQuoteDisplay");
		}		
		
		
				
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
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

		request.setAttribute("navigator.location","ά�������������  >> ���");
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) { 
			dform.initialize(mapping);
		}
		MaintContractQuoteMaster maintContractQuoteBean = new MaintContractQuoteMaster();
		try {
			maintContractQuoteBean.setAttn(userInfo.getUserID()); //�����˴���
			maintContractQuoteBean.setApplyDate(CommonUtil.getNowTime()); //����ʱ��
			maintContractQuoteBean.setMaintDivision(userInfo.getComID()); //ά���ֲ�����
			maintContractQuoteBean.setQuoteSignWay("ZB"); //����ǩ��ʽ ��ǩ
			
			//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ
			String roleid=userInfo.getRoleID();
			if(roleid!=null && roleid.trim().equals("A03")){
				maintContractQuoteBean.setR2(userInfo.getStorageName());
				maintContractQuoteBean.setMaintStation(userInfo.getStorageId());
				maintContractQuoteBean.setR1(roleid);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("PaymentMethodList", bd.getPullDownList("MaintContractQuoteMaster_PaymentMethodApply"));
		request.setAttribute("ContractContentList", bd.getPullDownList("MaintContractQuoteMaster_ContractContentApply"));
		request.setAttribute("attnName", userInfo.getUserName()); //����������
		request.setAttribute("maintDivisionName", userInfo.getComName()); //ά���ֲ�����
		request.setAttribute("maintContractQuoteBean", maintContractQuoteBean);
		
		request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));// ����ά��վ�б�
		
		//��ȡ�����׼���۵����ϵ��
		request.setAttribute("returnhmap", bd.getCoefficientMap());
		
		saveToken(request); //�������ƣ���ֹ���ظ��ύ
		
		return mapping.findForward("maintContractQuoteAdd");
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
		
		request.setAttribute("navigator.location","ά�������������  >> �޸�");	
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);

		String id = (String) dform.get("id");

		Session hs = null;
		List maintContractQuoteDetailList = null;
		if (id != null && !id.equals("")) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from MaintContractQuoteMaster where billNo = :billNo");
				query.setString("billNo", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					
					//����Ϣ
					MaintContractQuoteMaster maintContractQuoteBean = (MaintContractQuoteMaster) list.get(0);
					request.setAttribute("attnName", userInfo.getUserName()); //����������
					request.setAttribute("maintDivisionName", userInfo.getComName()); //ά���ֲ�����
					maintContractQuoteBean.setApplyDate(CommonUtil.getNowTime()); //��������
					maintContractQuoteBean.setAttn(userInfo.getUserID());//������
					
					//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ
					String roleid=userInfo.getRoleID();
					if(roleid!=null && roleid.trim().equals("A03")){
						maintContractQuoteBean.setR2(bd.getName(hs, "Storageid","storageName", "storageID", maintContractQuoteBean.getMaintStation()));
						maintContractQuoteBean.setR1(roleid);
					}
					
					request.setAttribute("maintContractQuoteBean", maintContractQuoteBean);	
					
					// ������Ϣ��ϸ�б�
					query = hs.createQuery("from MaintContractQuoteDetail where billNo = '"+id+"'");
					List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
					maintContractQuoteDetailList = query.list();
					for (Object object : maintContractQuoteDetailList) {
						MaintContractQuoteDetail detail = (MaintContractQuoteDetail)object;
						detail.setElevatorType(bd.getOptionName(detail.getElevatorType(), elevatorTypeList));
						detail.setR4(bd.getName("Pulldown", "pullname", "id.pullid",detail.getSignWay()));//ǩ��ʽ
					}					
					request.setAttribute("maintContractQuoteDetailList", maintContractQuoteDetailList);
						
					// ����ά��վ�б�	
					request.setAttribute("maintStationList", bd.getMaintStationList(userInfo.getComID()));						
				} else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}

				//��ȡ�Ѿ��ϴ��ĸ���
				List filelst=this.FileinfoList(hs, id.trim(), "MaintContractQuoteMaster");
				request.setAttribute("updatefileList", filelst);
				
				request.setAttribute("PaymentMethodList", bd.getPullDownList("MaintContractQuoteMaster_PaymentMethodApply"));
				request.setAttribute("ContractContentList", bd.getPullDownList("MaintContractQuoteMaster_ContractContentApply"));
				
				//��ȡ�����׼���۵����ϵ��
				request.setAttribute("returnhmap", bd.getCoefficientMap());
				
			}catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					hs.close();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			forward = mapping.findForward("maintContractQuoteModify");
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
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
	
	public ActionForward toReferRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, HibernateException {
		
		ActionErrors errors = new ActionErrors();
		
		String id = request.getParameter("id"); 
		
		refer(form,request,errors,id); //�ύ

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
			
			String id = (String) dform.get("id");
			MaintContractQuoteMaster master = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, id);
			if (master != null) {
				hs.createQuery("delete from MaintContractQuoteDetail where billno='"+id+"'").executeUpdate();
				hs.createQuery("delete from MaintContractQuoteProcess where billno='"+id+"'").executeUpdate();
				hs.delete(master);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));
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

		String name = tableForm.getProperty("name");// ����
		String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
		String startDates = tableForm.getProperty("startDates");// ��ʼ�볧����
		String startDatee = tableForm.getProperty("startDatee");// �����볧����
		String enabledFlag = tableForm.getProperty("enabledFlag");// ���ñ�־
		Session hs = null;
		XSSFWorkbook wb = new XSSFWorkbook();

		try {
			hs = HibernateUtil.getSession();

			Criteria criteria = hs.createCriteria(PersonnelManageMaster.class);
			if (contractNo != null && !contractNo.equals("")) {
				criteria.add(Restrictions.like("contractNo", "%" + contractNo.trim() + "%"));
			}
			if (name != null && !name.equals("")) {
				criteria.add(Restrictions.like("name", "%" + name.trim() + "%"));
			}
			if (startDates != null && !startDates.equals("")) {
				criteria.add(Restrictions.ge("startDate", startDates.trim()));
			}
			if (startDatee != null && !startDatee.equals("")) {
				criteria.add(Restrictions.le("startDate", startDatee.trim()));
			}
			if (enabledFlag != null && !enabledFlag.equals("")) {
				criteria.add(Restrictions.eq("enabledFlag", enabledFlag));
			}

			criteria.addOrder(Order.asc("billno"));

			List roleList = criteria.list();

			XSSFSheet sheet = wb.createSheet("ά�������������Ϣ");
			
			Locale locale = this.getLocale(request);
			MessageResources messages = getResources(request);
			
			String[] columnNames = { "billno", "name", "education", "idCardNo",
					"familyAddress", "phone", "contractProperties",
					"contractNo", "startDate", "endDate", "years", "sector",
					"workAddress", "jobTitle", "level", "enabledFlag", "rem",
					"operId", "operDate" };

			if (roleList != null && !roleList.isEmpty()) {
				int l = roleList.size();
				XSSFRow row0 = sheet.createRow( 0);
								
				for (int i = 0; i < columnNames.length; i++) {
					XSSFCell cell0 = row0.createCell((short)i);
					cell0.setCellValue(messages.getMessage(locale,"personnelManage." + columnNames[i]));
				}
				
				Class<?> superClazz = PersonnelManageMaster.class.getSuperclass();
				for (int i = 0; i < l; i++) {
					PersonnelManageMaster master = (PersonnelManageMaster) roleList.get(i);
					// ����Excel�У���0�п�ʼ
					XSSFRow row = sheet.createRow( i+1);					
					for (int j = 0; j < columnNames.length; j++) {
						// ����Excel��
						XSSFCell cell = row.createCell((short)j);
						cell.setCellValue(columnNames[j]);						
					}
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
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("ά�������������Ϣ", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
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
		
		MaintContractQuoteMaster master = null;
		MaintContractQuoteDetail detail = null;
		String id = (String) dform.get("id");
		String billNo = null;
		Session hs = null;
		Transaction tx = null;
		try {
		hs = HibernateUtil.getSession();
		tx = hs.beginTransaction();
		
		if (id != null && !id.equals("")) { // �޸�		
			hs.createQuery("delete from MaintContractQuoteDetail where billNo='"+id+"'").executeUpdate();		
			master = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, id);
			billNo = master.getBillNo();
			
		}else{ // ����
			master = new MaintContractQuoteMaster();
			String todayDate = CommonUtil.getToday();
			String year = todayDate.substring(2,4);
			billNo = CommonUtil.getBillno(year,"MaintContractQuoteMaster", 1)[0];// ������ˮ��		
			
		}
	
		if(!"Y".equals(master.getSubmitType())){
			
			String ccastr="";
			String[] ccarr=request.getParameterValues("contractContentApply");
			if(ccarr!=null && ccarr.length>0){
				ccastr=Arrays.toString(ccarr);
				ccastr=ccastr.replaceAll("\\[", "");
				ccastr=ccastr.replaceAll("\\]", "");
			}
			
			BeanUtils.copyProperties(master, dform); // ������������ֵ
			master.setBillNo(billNo);// ��ˮ��
			master.setSubmitType("N");// �ύ��־
			master.setStatus(new Integer(WorkFlowConfig.State_NoStart));// ����״̬
			master.setOperId(userInfo.getUserID());// ¼����
			master.setOperDate(CommonUtil.getNowTime());// ¼��ʱ��
			master.setContractContentApply(ccastr);
			
			hs.save(master);
			String ccapply=master.getContractContentApply();//��ͬ�������������
			
			// ��ͬ��ϸ
			String details = (String) dform.get("maintContractQuoteDetails");
			List list = JSONUtil.jsonToList(details, "details");
			List elevatorTypeList = bd.getPullDownList("ElevatorSalesInfo_type");// ���������б�
			
			for (Object object : list) {
				detail = new MaintContractQuoteDetail();					
				BeanUtils.copyProperties(detail, object);
				detail.setBillNo(billNo);
				detail.setNum(1);
				detail.setElevatorType(bd.getOptionId(detail.getElevatorType(), elevatorTypeList));//�ѵ�����������ת��Ϊ����	
				
				//��ͬ������������������������ί���ҷ����Ᵽ������ôǩ��ʽ�޸�Ϊ�����Ᵽ��
				if(ccapply.indexOf("100")>-1){
					detail.setSignWay("XQ");
				}
				hs.save(detail);				
			}
			
		}
		
		// �����ļ�
		String path = "MaintContractQuoteMaster.file.upload.folder";
		String tableName = "MaintContractQuoteMaster";//���� ά�����������
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
				String isreturn = request.getParameter("isreturn");
				if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
					refer(form,request,errors,master.getBillNo()); //�ύ
				}
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}
		
	}
		
	public void refer(ActionForm form, HttpServletRequest request,ActionErrors errors,String id){
		
		HttpSession httpsession = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)httpsession.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		if(id != null && !id.equals("")){
			
			Session hs = null;
			Transaction tx = null;
			JbpmExtBridge jbpmExtBridge=null;
			String userid = userInfo.getUserID(); //��ǰ��¼�û�id
			MaintContractQuoteMaster master = null;				
			
			try {
				hs = HibernateUtil.getSession();

				master = (MaintContractQuoteMaster) hs.get(MaintContractQuoteMaster.class, id);
				
				if(!"Y".equals(master.getSubmitType())){
					tx = hs.beginTransaction();
					
					String processDefineID = Grcnamelist1.getProcessDefineID("enginequotemaster", master.getAttn());// ���̻���id
					if(processDefineID == null || processDefineID.equals("")){
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.string","<font color='red'>δ��������������Ϣ������������</font>"));
						throw new Exception();
					}
								
					/**=============== ����������ʵ����ʼ ===================**/
					HashMap paraMap = new HashMap();
					jbpmExtBridge=new JbpmExtBridge();
					ProcessBean pd = null;		
					pd = jbpmExtBridge.getPb();

			    	pd.addAppointActors("");// ����̬��ӵ�����������
			    	//Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "ά���ֲ������", master.getOperId());// ��������
					//Grcnamelist1.setJbpmAuditopers_class(pd, processDefineID, PropertiesUtil.getProperty("MaintStationManagerJbpm"), userInfo.getComID(), master.getMaintStation());// ��������
			    	Grcnamelist1.setJbpmAuditopers_roleid(pd,"Y",master.getMaintDivision());
			    	
					pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,id,"",paraMap);
					/**==================== ���̽��� =======================**/
					
					master.setSubmitType("Y");// �ύ��־
					master.setProcessName(pd.getNodename());// ��������
					master.setStatus(pd.getStatus()); //����״̬
					master.setTokenId(pd.getToken());//��������
					hs.save(master);
					
					tx.commit();
				}
				
			} catch (Exception e) {				
				try {
					tx.rollback();
				} catch (HibernateException e3) {
					e3.printStackTrace();
				}
				if (jbpmExtBridge != null) {
					jbpmExtBridge.setRollBack();
				}
				e.printStackTrace();
			} finally {
				hs.close();
				if(jbpmExtBridge!=null){
					jbpmExtBridge.close();
				}
				
			}
			
		}
		
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
		
		String folder = PropertiesUtil.getProperty("MaintContractQuoteMaster.file.upload.folder");
		
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
		
		String folder = PropertiesUtil.getProperty("MaintContractQuoteMaster.file.upload.folder");
		
		try {
			hs = HibernateUtil.getSession();
			String sqlfile="select a from ContractFileinfo a where a.fileSid='"+filesid+"'";
			List list2=hs.createQuery(sqlfile).list();

			if(list2!=null && list2.size()>0){
				ContractFileinfo fp=(ContractFileinfo)list2.get(0);
				
				String filepath=fp.getFilePath();
				String newnamefile=fp.getNewFileName();
				oldname=fp.getOldFileName();
				String root=folder;//�ϴ�Ŀ¼
				localPath = root+filepath+newnamefile;
				
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
		
	
}
