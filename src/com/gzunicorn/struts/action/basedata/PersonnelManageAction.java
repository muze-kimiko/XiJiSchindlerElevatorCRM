package com.gzunicorn.struts.action.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
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
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

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
import com.gzunicorn.hibernate.basedata.certificateexam.CertificateExam;
import com.gzunicorn.hibernate.basedata.jobhistory.JobHistory;
import com.gzunicorn.hibernate.basedata.personnelmanage.PersonnelManageMaster;
import com.gzunicorn.hibernate.basedata.toolreceive.ToolReceive;
import com.gzunicorn.hibernate.basedata.traininghistory.TrainingHistory;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class PersonnelManageAction extends DispatchAction {

	Log log = LogFactory.getLog(PersonnelManageAction.class);
	
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
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "personnelmanage", null);
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
		
		request.setAttribute("navigator.location","���¹��� >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
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
			HTMLTableCache cache = new HTMLTableCache(session, "personnelManageList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fPersonnelManage");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("name");
			table.setIsAscending(true);
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
			
			String ygid = tableForm.getProperty("ygid");// ����
			String name = tableForm.getProperty("name");// ����
			String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
			//String startDates = tableForm.getProperty("startDates");// ��ʼ�볧����
			//String startDatee = tableForm.getProperty("startDatee");// �����볧����
			String enabledFlag = tableForm.getProperty("enabledFlag");// ���ñ�־
			String maintdivision = tableForm.getProperty("maintdivision");// ����ά����		
			String maintstation = tableForm.getProperty("maintstation");// ����ά��վ	
			Session hs = null;

			try {

				hs = HibernateUtil.getSession();
				
				//��һ�ν���ҳ��ʱ���ݵ�½�˳�ʼ������ά���ֲ�
				List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
				if(maintdivision == null || maintdivision.equals("")){
					Map map = (Map)maintDivisionList.get(0);
					maintdivision = (String)map.get("grcid");
				}
				request.setAttribute("maintDivisionList", maintDivisionList);
				//ά��վ������
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				
				List mainStationList=new ArrayList();
				//ά��վ������  A03=ά������,A48=ά��վ��Ա,ά��վ�� A49 
				if(vmlist!=null && vmlist.size()>0){
					if(maintstation == null || maintstation.trim().equals("")){
						maintstation=userInfo.getStorageId();
					}
					
					String hql="select a from Storageid a where "
							+ "(a.storageid= '"+userInfo.getStorageId()+"' or "
							+ "a.storageid in(select parentstorageid from Storageid a where a.storageid= '"+userInfo.getStorageId()+"')) "
							+ "and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";	
					mainStationList=hs.createQuery(hql).list();
					
				}else{
					String hql="select a from Storageid a where a.comid like '"+maintdivision+"' " +
							"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
					mainStationList=hs.createQuery(hql).list();
				  
					 Storageid storid=new Storageid();
					 storid.setStorageid("%");
					 storid.setStoragename("ȫ��");
					 mainStationList.add(0,storid);
				}
				request.setAttribute("mainStationList", mainStationList);
				
				
//				List mainStationList=new ArrayList();
//				if(maintdivision!=null && !maintdivision.equals("") && !maintdivision.equals("%")){
//					String hql="select a from Storageid a,Company b "
//							+ "where a.comid like '"+maintdivision+"' and a.comid=b.comid and b.comtype in('0','1') "
//							+ "and a.storagetype in('0','1') and a.parentstorageid='0' and a.enabledflag='Y' "
//							+ "order by storagetype,storageid";
//					mainStationList=hs.createQuery(hql).list();
//				}
//				 Storageid storid=new Storageid();
//				 storid.setStorageid("%");
//				 storid.setStoragename("ȫ��");
//				 mainStationList.add(0,storid);
//
//				 request.setAttribute("mainStationList", mainStationList);

				Criteria criteria = hs.createCriteria(PersonnelManageMaster.class);
				if (maintdivision != null && !maintdivision.equals("") && !maintdivision.equals("%")) {
					maintdivision=bd.getName("Company","comname","comid",maintdivision); //�����ֲ� Sector
					criteria.add(Expression.like("sector", "%" + maintdivision.trim() + "%"));
				}
				if (maintstation != null && !maintstation.equals("") && !maintstation.equals("%")) {
//					maintstation=bd.getName("Storageid","storagename","storageid",maintstation); //��������
//					criteria.add(Expression.like("maintStation", "%" + maintstation.trim() + "%"));
					
					String sql = "select a.StorageName from Storageid a where a.StorageID like '%"+maintstation+"%'";
					List mList = hs.createSQLQuery(sql).list();
					
					criteria.add(Expression.in("maintStation", mList));
				}
				if (ygid != null && !ygid.equals("")) {
					criteria.add(Expression.like("ygid", "%" + ygid.trim() + "%"));
				}
				if (name != null && !name.equals("")) {
					criteria.add(Expression.like("name", "%" + name.trim() + "%"));
				}
				if (contractNo != null && !contractNo.equals("")) {
					criteria.add(Expression.like("contractNo", "%" + contractNo.trim() + "%"));
				}
//				if (startDates != null && !startDates.equals("")) {
//					criteria.add(Expression.ge("startDate", startDates.trim()));
//				}
//				if (startDatee != null && !startDatee.equals("")) {
//					criteria.add(Expression.le("startDate", startDatee.trim()));
//				}
				if (enabledFlag != null && !enabledFlag.equals("")) {
					criteria.add(Expression.eq("enabledFlag", enabledFlag));
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

				List personnelManageList = criteria.list();

				table.addAll(personnelManageList);
				session.setAttribute("personnelManageList", table);

			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (Exception e1) {

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
			forward = mapping.findForward("personnelManageList");
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
		
		request.setAttribute("navigator.location","���¹��� >> �鿴");
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		
		String id = (String) dform.get("id");
		Session hs = null;
		List jobHistoryList = null;
		List trainingHistoryList = null;
		List certificateExamList = null;
		List ToolReceiveList=null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from PersonnelManageMaster p where p.billno = :billno");
				query.setString("billno", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					PersonnelManageMaster personnelManageBean = (PersonnelManageMaster) list.get(0);
					request.setAttribute("personnelManageBean", personnelManageBean);
					
					query = hs.createQuery("from JobHistory p where p.billno = '"+id+"'");
					jobHistoryList = query.list();
					
					query = hs.createQuery("from TrainingHistory p where p.billno = '"+id+"'");
					trainingHistoryList = query.list();
					
					query = hs.createQuery("from CertificateExam p where p.billno = '"+id+"'");
					certificateExamList = query.list();
					
					query = hs.createQuery("from ToolReceive p where p.billno = '"+id+"'");
					ToolReceiveList = query.list();
					
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
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
			forward = mapping.findForward("personnelManageDisplay");
		}
		
		request.setAttribute("jobHistoryList", jobHistoryList);// ��λ��ʷ�б�
		request.setAttribute("trainingHistoryList", trainingHistoryList);// ��ѵ��ʷ�б�
		request.setAttribute("certificateExamList", certificateExamList);// ֤�鿼���б�
		request.setAttribute("ToolReceiveList", ToolReceiveList);// ������ȡ�����б�

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

		request.setAttribute("navigator.location","���¹��� >> ���");

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		PersonnelManageMaster master = new PersonnelManageMaster();
		master.setSex("��");
		master.setEnabledFlag("Y");
		request.setAttribute("personnelManageBean", master);
		request.setAttribute("personnelManageJobLeval", bd.getPullDownList("PersonnelManageMaster_JobLeval"));
		request.setAttribute("personnelManageLevel", bd.getPullDownList("PersonnelManageMaster_Level"));
		
		
		return mapping.findForward("personnelManageAdd");
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
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String ygid = (String) dform.get("ygid");
		
		Session hs = null;
		Transaction tx = null;
		PersonnelManageMaster master = null;
		JobHistory jobHistory = null;
		TrainingHistory trainingHistory = null;
		CertificateExam certificateExam = null;
		ToolReceive toolReceive=null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();

			String sqlup="from PersonnelManageMaster where ygid='"+ygid+"'";
			List sqlist=hs.createQuery(sqlup).list();
			if(ygid!=null && !ygid.trim().equals("") && sqlist!=null && sqlist.size()>0){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>Ա�������Ѿ����ڣ�����ʧ�ܣ�</font>"));
			}else{
				master = new PersonnelManageMaster();
	
				BeanUtils.copyProperties(master, dform); // ��������ֵ
				
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				String billno = CommonUtil.getBillno(year,"PersonnelManageMaster", 1)[0];// ������ˮ��	
				
				master.setBillno(billno);// ��ˮ��
				master.setOperId(userInfo.getUserID());// ¼����
				master.setOperDate(todayDate);// ¼��ʱ��
				
				hs.save(master);
				
				// ��λ��ʷ
				String[] sdate = request.getParameterValues("sdate1");// �ڸ�ʱ���
				String[] edate = request.getParameterValues("edate1");
				String[] workAddress = request.getParameterValues("workAddress1");// ������ַ
				String[] jobTitle = request.getParameterValues("jobTitle1");// ��λ����
				String[] level = request.getParameterValues("level1");// ����
				String[] revisionDate = request.getParameterValues("revisionDate1");// ��������
				
				if(sdate != null && sdate.length > 0){
					for (int i = 0; i < sdate.length; i++) {
						jobHistory = new JobHistory();
						jobHistory.setBillno(billno);
						jobHistory.setSdate(sdate[i]);				
						jobHistory.setEdate(edate[i]);
						jobHistory.setWorkAddress(workAddress[i]);
						jobHistory.setJobTitle(jobTitle[i]);
						jobHistory.setLevel(level[i]);
						jobHistory.setRevisionDate(revisionDate[i]);
						hs.save(jobHistory);
					}
				}
				
				// ��ѵ��ʷ
				String[] straDate = request.getParameterValues("straDate1");// ��ѵ����
				String[] etraDate = request.getParameterValues("etraDate1");
				String[] traCourse = request.getParameterValues("traCourse1");// ��ѵ�γ�
				String[] lesson = request.getParameterValues("lesson1");// ��ʱ
				String[] theoreticalResults = request.getParameterValues("theoreticalResults1");// ���۳ɼ�
				String[] practicalResults = request.getParameterValues("practicalResults1");// ʵ�ٳɼ�
				String[] perforResults = request.getParameterValues("perforResults1");// ���ֳɼ�
				String[] traAssess = request.getParameterValues("traAssess1");// ��ѵ����
				String[] totalScore = request.getParameterValues("totalScore1");// �ܷ�
				String[] rating = request.getParameterValues("rating1");// �ȼ�
				
				if(straDate != null && straDate.length > 0){
					for (int i = 0; i < straDate.length; i++) {
						trainingHistory = new TrainingHistory();
						trainingHistory.setBillno(billno);
						trainingHistory.setStraDate(straDate[i]);
						trainingHistory.setEtraDate(etraDate[i]);
						trainingHistory.setTraCourse(traCourse[i]);
						
						if(lesson[i]!=null && !lesson[i].trim().equals("")){
							trainingHistory.setLesson(Double.valueOf(lesson[i]));		
						}
						if(theoreticalResults[i]!=null && !theoreticalResults[i].trim().equals("")){
							trainingHistory.setTheoreticalResults(Double.valueOf(theoreticalResults[i]));
						}
						if(practicalResults[i]!=null && !practicalResults[i].trim().equals("")){
							trainingHistory.setPracticalResults(Double.valueOf(practicalResults[i]));
						}
						if(perforResults[i]!=null && !perforResults[i].trim().equals("")){
							trainingHistory.setPerforResults(Double.valueOf(perforResults[i]));
						}
						if(totalScore[i]!=null && !totalScore[i].trim().equals("")){
							trainingHistory.setTotalScore(Double.valueOf(totalScore[i]));
						}
						
						trainingHistory.setTraAssess(traAssess[i]);
						trainingHistory.setRating(rating[i]);
						
						hs.save(trainingHistory);
					}
				}
							
				// ֤�鿼�� 
				String[] certificateNo = request.getParameterValues("certificateNo1");// ֤���
				String[] certificateName = request.getParameterValues("certificateName1");// ֤������
				String[] certificateProperty = request.getParameterValues("certificateProperty1");// ֤������
				String[] startDate = request.getParameterValues("startDate1");// ��ʼ����
				String[] endDate = request.getParameterValues("endDate1");// ʧЧ����
				String[] issuingAuthority1 = request.getParameterValues("issuingAuthority1");// ��֤����
				String[] isExpense1 = request.getParameterValues("isExpense1");//�Ƿ���
				String[] isCharging1 = request.getParameterValues("isCharging1");//�Ƿ�۷�
				String[] certificateExt1 = request.getParameterValues("certificateExt1");//֤��ȥ��
				String[] rem0 = request.getParameterValues("rem0");// ��ע
				
				if(certificateNo != null && certificateNo.length > 0){
					for (int i = 0; i < certificateNo.length; i++) {
						certificateExam = new CertificateExam();
						certificateExam.setBillno(billno);
						certificateExam.setCertificateNo(certificateNo[i]);
						certificateExam.setCertificateName(certificateName[i]);
						certificateExam.setCertificateProperty(certificateProperty[i]);
						certificateExam.setStartDate(startDate[i]);
						certificateExam.setEndDate(endDate[i]);
						
						certificateExam.setIssuingAuthority(issuingAuthority1[i]);
						certificateExam.setIsExpense(isExpense1[i]);
						certificateExam.setIsCharging(isCharging1[i]);
						certificateExam.setCertificateExt(certificateExt1[i]);
						certificateExam.setRem(rem0[i]);
						
						hs.save(certificateExam);
					}
				}
				
				//������ȡ����
//				String[] toolId1 = request.getParameterValues("toolId1");// ���߱��
//				String[] toolName1 = request.getParameterValues("toolName1");// ��������
//				String[] toolParam1 = request.getParameterValues("toolParam1");// �����ͺ�
//				String[] toolnum1 = request.getParameterValues("toolnum1");// ����
//				String[] operName1 = request.getParameterValues("operName1");// ������
//				String[] operDate1 = request.getParameterValues("operDate1");//��ȡ����
//				String[] isCharge1 = request.getParameterValues("isCharge1");//�Ƿ��շ�
//				String[] isLiquidation1 = request.getParameterValues("isLiquidation1");//�Ƿ�����
//				String[] rem1 = request.getParameterValues("rem1");// ��ע
				
				List fileList=this.savePicter(form,request,response, billno);//���渽����Ӳ��

				if(fileList != null && fileList.size() > 0){
					int listlen=fileList.size();
					for (int i = 0; i <fileList.size(); i++) {
						listlen--;
						HashMap hmap=(HashMap)fileList.get(listlen);

						toolReceive = new ToolReceive();
						
						toolReceive.setBillno(billno);
						toolReceive.setToolId((String)hmap.get("toolId1"));
						toolReceive.setToolName((String)hmap.get("toolName1"));
						toolReceive.setToolParam((String)hmap.get("toolParam1"));
						
						String toolnum1=(String)hmap.get("toolnum1");
						if(toolnum1!=null && !toolnum1.trim().equals("")){
							toolReceive.setToolnum(Double.valueOf(toolnum1));
						}
						
						toolReceive.setOperName((String)hmap.get("operName1"));
						toolReceive.setOperDate((String)hmap.get("operDate1"));
						toolReceive.setIsCharge((String)hmap.get("isCharge1"));
						toolReceive.setIsLiquidation((String)hmap.get("isLiquidation1"));
						toolReceive.setRem((String)hmap.get("rem1"));
						
						String filename=(String)hmap.get("oldfilename");
						if(filename!=null && !filename.trim().equals("")){
							toolReceive.setR2(filename);//ԭ��������
							toolReceive.setR3((String)hmap.get("newfilename"));//�ϴ��󸽼�����
						}
						hs.save(toolReceive);
					}
				}
				
				tx.commit();
			}
		} catch (Exception e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("personnelManage.insert.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Insert error!");
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
		
		request.setAttribute("navigator.location","���¹��� >> �޸�");	
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		String id = null;
		if (dform.get("isreturn") != null
				&& ((String) dform.get("isreturn")).equals("N")) {
			id = (String) dform.get("billno");
		} else {
			id = (String) dform.get("id");
		}
		String error = (String) request.getAttribute("error");
		Session hs = null;
		List jobHistoryList = null;
		List trainingHistoryList = null;
		List certificateExamList = null;
		List ToolReceiveList=null;
		if (id != null) {
				try {
					hs = HibernateUtil.getSession();
					Query query = hs.createQuery("from PersonnelManageMaster p where p.billno = :billno");
					query.setString("billno", id);
					List list = query.list();
					if (list != null && list.size() > 0) {
						PersonnelManageMaster personnelManageBean = (PersonnelManageMaster) list.get(0);
						request.setAttribute("personnelManageBean", personnelManageBean);	

						query = hs.createQuery("from JobHistory p where p.billno = '"+id+"'");
						jobHistoryList = query.list();
						
						query = hs.createQuery("from TrainingHistory p where p.billno = '"+id+"'");
						trainingHistoryList = query.list();
						
						query = hs.createQuery("from CertificateExam p where p.billno = '"+id+"'");
						certificateExamList = query.list();
						
						query = hs.createQuery("from ToolReceive p where p.billno = '"+id+"'");
						ToolReceiveList = query.list();
						
					} else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
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
						DebugUtil
								.print(hex, "HibernateUtil Hibernate Session ");
					}
				}
			forward = mapping.findForward("personnelManageModify");
		}
		
		request.setAttribute("jobHistoryList", jobHistoryList);// ��λ��ʷ�б�
		request.setAttribute("trainingHistoryList", trainingHistoryList);// ��ѵ��ʷ�б�
		request.setAttribute("certificateExamList", certificateExamList);// ֤�鿼���б�
		request.setAttribute("ToolReceiveList", ToolReceiveList);//
		request.setAttribute("personnelManageJobLeval", bd.getPullDownList("PersonnelManageMaster_JobLeval"));
		request.setAttribute("personnelManageLevel", bd.getPullDownList("PersonnelManageMaster_Level"));

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	
	/**
	 * �޸�
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
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs = null;
		Transaction tx = null;
		
		String id = (String) dform.get("id");
		String billno = (String) dform.get("billno");
		String ygid = (String) dform.get("ygid");
		String oldygid = (String) dform.get("oldygid");
		
		JobHistory jobHistory = null;
		TrainingHistory trainingHistory = null;
		CertificateExam certificateExam = null;		
		ToolReceive toolReceive=null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			String sqlup="from PersonnelManageMaster where ygid='"+ygid+"' and ygid not in('"+oldygid+"')";
			List sqlist=hs.createQuery(sqlup).list();
			if(ygid!=null && !ygid.trim().equals("") && sqlist!=null && sqlist.size()>0){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>Ա�������Ѿ����ڣ�����ʧ�ܣ�</font>"));
			}else{
			
				PersonnelManageMaster master = (PersonnelManageMaster) hs.get(PersonnelManageMaster.class, id);
				if (id != null && billno!=null && id.equals(billno)) {
					hs.createQuery("delete from JobHistory where billno='"+id+"'").executeUpdate();
					hs.createQuery("delete from TrainingHistory where billno='"+id+"'").executeUpdate();
					hs.createQuery("delete from CertificateExam where billno='"+id+"'").executeUpdate();
					//hs.createQuery("delete from ToolReceive where billno='"+id+"'").executeUpdate();

					//hs.delete(master);
					//billno = master.getBillno();
					//master = new PersonnelManageMaster();
				}
				
				BeanUtils.copyProperties(master, dform); // ������������ֵ
				master.setBillno(billno);
				master.setOperId(userInfo.getUserID()); // ¼����
				master.setOperDate(CommonUtil.getToday()); // ¼��ʱ��
				
				hs.save(master);
				
				// ��λ��ʷ
				String[] sdate = request.getParameterValues("sdate1");// �ڸ�ʱ���
				String[] edate = request.getParameterValues("edate1");
				String[] workAddress = request.getParameterValues("workAddress1");// ������ַ
				String[] jobTitle = request.getParameterValues("jobTitle1");// ��λ����
				String[] level = request.getParameterValues("level1");// ����
				String[] revisionDate = request.getParameterValues("revisionDate1");// ��������
	
				if (sdate != null && sdate.length>0) {
					for (int i = 0; i < sdate.length; i++) {
						jobHistory = new JobHistory();
						jobHistory.setBillno(billno);
						jobHistory.setSdate(sdate[i]);
						jobHistory.setEdate(edate[i]);
						jobHistory.setWorkAddress(workAddress[i]);
						jobHistory.setJobTitle(jobTitle[i]);
						jobHistory.setLevel(level[i]);
						jobHistory.setRevisionDate(revisionDate[i]);
						hs.save(jobHistory);
					}
				}
	
				// ��ѵ��ʷ
				String[] straDate = request.getParameterValues("straDate1");// ��ѵ����
				String[] etraDate = request.getParameterValues("etraDate1");
				String[] traCourse = request.getParameterValues("traCourse1");// ��ѵ�γ�
				String[] lesson = request.getParameterValues("lesson1");// ��ʱ
				String[] theoreticalResults = request.getParameterValues("theoreticalResults1");// ���۳ɼ�
				String[] practicalResults = request.getParameterValues("practicalResults1");// ʵ�ٳɼ�
				String[] perforResults = request.getParameterValues("perforResults1");// ���ֳɼ�
				String[] traAssess = request.getParameterValues("traAssess1");// ��ѵ����
				String[] totalScore = request.getParameterValues("totalScore1");// �ܷ�
				String[] rating = request.getParameterValues("rating1");// �ȼ�
				
				if(straDate != null && straDate.length > 0){
					for (int i = 0; i < straDate.length; i++) {
						trainingHistory = new TrainingHistory();
						trainingHistory.setBillno(billno);
						trainingHistory.setStraDate(straDate[i]);
						trainingHistory.setEtraDate(etraDate[i]);
						trainingHistory.setTraCourse(traCourse[i]);
						
						if(lesson[i]!=null && !lesson[i].trim().equals("")){
							trainingHistory.setLesson(Double.valueOf(lesson[i]));	
						}
						if(theoreticalResults[i]!=null && !theoreticalResults[i].trim().equals("")){
							trainingHistory.setTheoreticalResults(Double.valueOf(theoreticalResults[i]));
						}
						if(practicalResults[i]!=null && !practicalResults[i].trim().equals("")){
							trainingHistory.setPracticalResults(Double.valueOf(practicalResults[i]));
						}
						if(perforResults[i]!=null && !perforResults[i].trim().equals("")){
							trainingHistory.setPerforResults(Double.valueOf(perforResults[i]));
						}
						if(totalScore[i]!=null && !totalScore[i].trim().equals("")){
							trainingHistory.setTotalScore(Double.valueOf(totalScore[i]));
						}
						
						trainingHistory.setTraAssess(traAssess[i]);
						trainingHistory.setRating(rating[i]);
						
						hs.save(trainingHistory);
					}
				}
							
				// ֤�鿼�� 
				String[] certificateNo = request.getParameterValues("certificateNo1");// ֤���
				String[] certificateName = request.getParameterValues("certificateName1");// ֤������
				String[] certificateProperty = request.getParameterValues("certificateProperty1");// ֤������
				String[] startDate = request.getParameterValues("startDate1");// ��ʼ����
				String[] endDate = request.getParameterValues("endDate1");// ʧЧ����
				String[] issuingAuthority1 = request.getParameterValues("issuingAuthority1");// ��֤����
				String[] isExpense1 = request.getParameterValues("isExpense1");//�Ƿ���
				String[] isCharging1 = request.getParameterValues("isCharging1");//�Ƿ�۷�
				String[] certificateExt1 = request.getParameterValues("certificateExt1");//֤��ȥ��
				String[] rem0 = request.getParameterValues("rem0");// ��ע
				
				if(certificateNo != null && certificateNo.length > 0){
					for (int i = 0; i < certificateNo.length; i++) {
						certificateExam = new CertificateExam();
						certificateExam.setBillno(billno);
						certificateExam.setCertificateNo(certificateNo[i]);
						certificateExam.setCertificateName(certificateName[i]);
						certificateExam.setCertificateProperty(certificateProperty[i]);
						certificateExam.setStartDate(startDate[i]);
						certificateExam.setEndDate(endDate[i]);
						
						certificateExam.setIssuingAuthority(issuingAuthority1[i]);
						certificateExam.setIsExpense(isExpense1[i]);
						certificateExam.setIsCharging(isCharging1[i]);
						certificateExam.setCertificateExt(certificateExt1[i]);
						certificateExam.setRem(rem0[i]);
						
						hs.save(certificateExam);
					}
				}
				
				//������ȡ����
//				String[] toolId1 = request.getParameterValues("toolId1");// ���߱��
//				String[] toolName1 = request.getParameterValues("toolName1");// ��������
//				String[] toolParam1 = request.getParameterValues("toolParam1");// �����ͺ�
//				String[] toolnum1 = request.getParameterValues("toolnum1");// ����
//				String[] operName1 = request.getParameterValues("operName1");// ������
//				String[] operDate1 = request.getParameterValues("operDate1");//��ȡ����
//				String[] isCharge1 = request.getParameterValues("isCharge1");//�Ƿ��շ�
//				String[] isLiquidation1 = request.getParameterValues("isLiquidation1");//�Ƿ�����
//				String[] rem1 = request.getParameterValues("rem1");// ��ע
				
				List fileList=this.savePicter(form,request,response, billno);//���渽����Ӳ��
				String numnostr="";
				if(fileList != null && fileList.size() > 0){
					for (int i = 0; i < fileList.size(); i++) {
						HashMap hmap=(HashMap)fileList.get(i);
						
						String numno=(String)hmap.get("numno");
						if(numno!=null && !numno.trim().equals("")){
							toolReceive= (ToolReceive) hs.get(ToolReceive.class, Integer.parseInt(numno));
						}else{
							toolReceive = new ToolReceive();
						}
						
						toolReceive.setBillno(billno);
						toolReceive.setToolId((String)hmap.get("toolId1"));
						toolReceive.setToolName((String)hmap.get("toolName1"));
						toolReceive.setToolParam((String)hmap.get("toolParam1"));
						
						String toolnum1=(String)hmap.get("toolnum1");
						if(toolnum1!=null && !toolnum1.trim().equals("")){
							toolReceive.setToolnum(Double.valueOf(toolnum1));
						}
						
						toolReceive.setOperName((String)hmap.get("operName1"));
						toolReceive.setOperDate((String)hmap.get("operDate1"));
						toolReceive.setIsCharge((String)hmap.get("isCharge1"));
						toolReceive.setIsLiquidation((String)hmap.get("isLiquidation1"));
						toolReceive.setRem((String)hmap.get("rem1"));
						
						String filename=(String)hmap.get("oldfilename");
						if(filename!=null && !filename.trim().equals("")){
							toolReceive.setR2(filename);//ԭ��������
							toolReceive.setR3((String)hmap.get("newfilename"));//�ϴ��󸽼�����
						}
						hs.save(toolReceive);
						//hs.flush();
						
						numnostr+=toolReceive.getNumNo()+",";
					}
					//ɾ��ҳ�治���ڣ����ݿ���ڵļ�¼
					if(numnostr!=null && !numnostr.trim().equals("")){
						numnostr=numnostr.substring(0,numnostr.length()-1);
						String delsql="delete from ToolReceive where billno='"+id+"' and numNo not in("+numnostr+")";
						//System.out.println(delsql);
						hs.createQuery(delsql).executeUpdate();
					}
				}
				
				tx.commit();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("personnelManage.update.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
			}
			log.error(e2.getMessage());
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
			PersonnelManageMaster master = (PersonnelManageMaster) hs.get(PersonnelManageMaster.class, id);
			if (master != null) {
				hs.createQuery("delete from JobHistory where billno='"+id+"'").executeUpdate();
				hs.createQuery("delete from TrainingHistory where billno='"+id+"'").executeUpdate();
				hs.createQuery("delete from CertificateExam where billno='"+id+"'").executeUpdate();
				hs.createQuery("delete from ToolReceive where billno='"+id+"'").executeUpdate();
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

		ServeTableForm tableForm = (ServeTableForm) form;
		
		
		String maintdivision = tableForm.getProperty("maintdivision");// ����ά����		
		String maintstation = tableForm.getProperty("maintstation");// ����ά��վ	
		if (maintdivision != null && !maintdivision.equals("") && !maintdivision.equals("%")) {
			maintdivision=bd.getName("Company","comname","comid",maintdivision); //�����ֲ� Sector
		}else{
			maintdivision="%";
		}
		if (maintstation != null && !maintstation.equals("") && !maintstation.equals("%")) {
			maintstation=bd.getName("Storageid","storagename","storageid",maintstation); //��������
		}else{
			maintstation="%";
		}
		String ygid = tableForm.getProperty("ygid");// ����
		if(ygid==null || ygid.trim().equals("")){
			ygid="%";
		}
		String name = tableForm.getProperty("name");// ����
		if(name==null || name.trim().equals("")){
			name="%";
		}
		String contractNo = tableForm.getProperty("contractNo");// ��ͬ��
		if(contractNo==null || contractNo.trim().equals("")){
			contractNo="%";
		}
//		String startDates = tableForm.getProperty("startDates");// ��ʼ�볧����
//		if(startDates==null || startDates.trim().equals("")){
//			startDates="0000-00-00";
//		}
//		String startDatee = tableForm.getProperty("startDatee");// �����볧����
//		if(startDatee==null || startDatee.trim().equals("")){
//			startDatee="9999-99-99";
//		}
		String enabledFlag = tableForm.getProperty("enabledFlag");// ���ñ�־
		if(enabledFlag==null || enabledFlag.trim().equals("")){
			enabledFlag="%";
		}
		
		//�������� ��1=Ա������������2=��ѵ����������3=֤�鵵��������4=������ȡ����������
		String exceltype = tableForm.getProperty("exceltype");
		
		Session hs = null;
		XSSFWorkbook wb = new XSSFWorkbook();
		String sheetname="Ա������";
		try {
			hs = HibernateUtil.getSession();
			
			String[] titlename={"Ա������","Ա������","�Ա�","ѧ��","���֤����","��ͥ���ڵ�","��ϵ�绰","���õ绰","��ͬ��",
					"�볧����","��ְ����","�볧����","����","�����ص�","��λ����","����",
					"������","�����ڹ���","���ý�������","�Ƿ�פ��","��������","��ע","��λ����","�����ֲ�","��������","���ñ�־"};
			String[] titleid={"ygid","Name","Sex","Education","IdCardNo","FamilyAddress","phone","phone2","ContractNo",
					"StartDate","EndDate","Years","Sector","WorkAddress","JobLeval","hubie",
					"Probation","Probationgz","ProbationEdate","iszhuwai","BirthDate","Rem","Level",
					"sector","maintstation","EnabledFlag"};
			
			String sql0="select a.* from PersonnelManageMaster a where 1=1 ";
			String sql="and a.ygid like '%"+ygid.trim()+"%' "
					+ "and a.name like '%"+name.trim()+"%' "
					+ "and a.contractNo like '%"+contractNo.trim()+"%' "
					//+ "and isnull(a.startDate,'0000-00-00')>='"+startDates.trim()+"' "
					//+ "and isnull(a.startDate,'9999-99-99')<='"+startDatee.trim()+"' "
					+ "and a.enabledFlag like '"+enabledFlag.trim()+"' "
					+ "and a.sector like '%"+maintdivision.trim()+"%' "
					+ "and a.maintstation like '%"+maintstation.trim()+"%' "
					+ "order by a.billno";
			
			if(exceltype.equals("2")){
				sheetname="��ѵ����";
				sql0="select a.ygid,a.name,a.sector,a.maintstation,a.EnabledFlag,b.* from PersonnelManageMaster a,TrainingHistory b where a.billno=b.billno ";
				titlename=new String[]{"Ա������","Ա������","�����ֲ�","��������","���ñ�־","��ѵ���ڿ�ʼ","��ѵ���ڽ���","��ѵ�γ�","��ʱ",
						"���۳ɼ�","ʵ�ٳɼ�","���ֳɼ�","��ѵ����/��ע","�ܷ�","�ȼ�"};
				titleid=new String[]{"ygid","Name","sector","maintstation","EnabledFlag","STraDate","ETraDate","TraCourse","Lesson",
						"TheoreticalResults","PracticalResults","PerforResults","TraAssess","TotalScore","Rating"};
			}else if(exceltype.equals("3")){
				sheetname="֤�鵵��";
				sql0="select a.ygid,a.name,a.sector,a.maintstation,a.EnabledFlag,b.* from PersonnelManageMaster a,CertificateExam b where a.billno=b.billno ";
				titlename=new String[]{"Ա������","Ա������","�����ֲ�","��������","���ñ�־","֤���","֤������","֤������","��ʼ����","ʧЧ����",
						"��֤����","�Ƿ���","�Ƿ�۷�","֤��ȥ��","��ע"};
				titleid=new String[]{"ygid","Name","sector","maintstation","EnabledFlag","CertificateNo","CertificateName","CertificateProperty","StartDate","EndDate",
						"IssuingAuthority","IsExpense","IsCharging","CertificateExt","Rem"};
			}else if(exceltype.equals("4")){
				sheetname="������ȡ����";
				sql0="select a.ygid,a.name,a.sector,a.maintstation,a.EnabledFlag,b.* from PersonnelManageMaster a,ToolReceive b where a.billno=b.billno ";
				titlename=new String[]{"Ա������","Ա������","�����ֲ�","��������","���ñ�־","���߱��","��������","�����ͺ�","����","������","��ȡ����","�Ƿ��շ�","�Ƿ�����","��ע"};
				titleid=new String[]{"ygid","Name","sector","maintstation","EnabledFlag","ToolID","ToolName","ToolParam","Toolnum","operName","operDate","isCharge","isLiquidation","Rem"};
			}
			
			//System.out.println("exceltype>>>"+exceltype);
			//System.out.println(">>>"+sql0+sql);

	        XSSFSheet sheet = wb.createSheet(sheetname);
	        
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
			XSSFRow row = null;
			XSSFCell cell =null;
			ResultSet rs=hs.connection().prepareStatement(sql0+sql).executeQuery();
			int num=0;
			while(rs.next()){
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(num+1);
				for(int c=0;c<titleid.length;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);  
				    if(titleid[c].equals("Probationgz") 
				    		|| titleid[c].equals("TotalScore") || titleid[c].equals("PerforResults") 
				    		||titleid[c].equals("PracticalResults") || titleid[c].equals("TheoreticalResults") 
				    		|| titleid[c].equals("Lesson") || titleid[c].equals("Toolnum")){
				    	cell.setCellValue(rs.getDouble(titleid[c]));
				    }else{
				    	String valstr=rs.getString(titleid[c]);
				    	//[�Ƿ�פ��,���ñ�־,�Ƿ���,�Ƿ�۷� ,�Ƿ�����]
				    	if(titleid[c].equals("EnabledFlag")
				    			|| titleid[c].equals("IsExpense") || titleid[c].equals("IsCharging")
				    			|| titleid[c].equals("isLiquidation")){
				    		if(valstr!=null && !valstr.trim().equals("") && valstr.trim().equals("Y")){
				    			valstr="��";
				    		}else if(valstr!=null && !valstr.trim().equals("") && valstr.trim().equals("N")){
				    			valstr="��";
				    		}
				    	}
				    	//�Ƿ��շ� [Y�����շ�,N��δ�շ�,M����˾����]
				    	if(titleid[c].equals("isCharge")){
				    		if(valstr!=null && !valstr.trim().equals("") && valstr.trim().equals("Y")){
				    			valstr="���շ�";
				    		}else if(valstr!=null && !valstr.trim().equals("") && valstr.trim().equals("N")){
				    			valstr="δ�շ�";
				    		}else if(valstr!=null && !valstr.trim().equals("") && valstr.trim().equals("M")){
				    			valstr="��˾����";
				    		}else if(valstr!=null && !valstr.trim().equals("") && valstr.trim().equals("X")){
				    			valstr="�ֳ�����";
				    		}
				    	}

				    	cell.setCellValue(valstr);
				    }
				}
				num++;
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
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode(sheetname, "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	
	/**
	 * ��ö���ָ����get����������ִ�и�get�������ֵ
	 * @param object javabean����
	 * @param superClazz object���࣬����û����Ӧget����ʱ�봫��object�ĸ���
	 * @param fieldName ������
	 * @return ActionForward
	 */
	private String getValue(Object object, Class<?> superClazz, String fieldName){
		String value = null;	
		String methodName = "get" + fieldName.replaceFirst(fieldName.substring(0, 1),fieldName.substring(0, 1).toUpperCase());
		try {
			Method method = superClazz.getMethod(methodName);
			value = method.invoke(object, null) + "";
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return value;

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
				String hql="select a from Storageid a,Company b "
						+ "where a.comid like '"+comid+"' and a.comid=b.comid and b.comtype in('0','1') "
						+ "and a.storagetype in('0','1') and a.parentstorageid='0' and a.enabledflag='Y' "
						+ "order by storagetype,storageid";
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
	
	public ActionForward toDownloadFileDispose(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		String numno=request.getParameter("numno");
		String folder = PropertiesUtil.getProperty("ToolReceive.file.upload.folder");//���渽��·��

		Session hs = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		try {
			hs = HibernateUtil.getSession();
			
			String sql="from ToolReceive where numNo="+numno;
			List tlist=hs.createQuery(sql).list();
			if(tlist!=null && tlist.size()>0){
				
				ToolReceive tre=(ToolReceive)tlist.get(0);
				String fileOldName=tre.getR2();
				String filename=tre.getR3();
				
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileOldName, "utf-8"));
		
				fis = new FileInputStream(folder+filename);
				bis = new BufferedInputStream(fis);
				fos = response.getOutputStream();
				bos = new BufferedOutputStream(fos);
		
				int bytesRead = 0;
				byte[] buffer = new byte[5 * 1024];
				while ((bytesRead = bis.read(buffer)) != -1) {
					bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
					bos.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fos != null) {fos.close();}
			if (bos != null) {bos.close();}
			if (fis != null) {fis.close();}
			if (bis != null) {bis.close();}
			
			if (hs!=null){hs.close();}
		}
		return null;
	}
	/**
	 * �ϴ����渽��
	 * @param form
	 * @param request
	 * @param response
	 * @param folder
	 * @param billno
	 * @return
	 */
	public List savePicter(ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String billno) {
		
		List returnList = new ArrayList();
		String folder = PropertiesUtil.getProperty("ToolReceive.file.upload.folder");//���渽��·��
		int fileCount=0;
		
		 FormFile formFile = null;
		 if (form.getMultipartRequestHandler() != null) {
			 Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			 if (hash != null) {
				 
				 String[] numno = request.getParameterValues("numno");
				 String[] toolId1 = request.getParameterValues("toolId1");// ���߱��
				 String[] toolName1 = request.getParameterValues("toolName1");// ��������
				 String[] toolParam1 = request.getParameterValues("toolParam1");// �����ͺ�
				 String[] toolnum1 = request.getParameterValues("toolnum1");// ����
				 String[] operName1 = request.getParameterValues("operName1");// ������
				 String[] operDate1 = request.getParameterValues("operDate1");//��ȡ����
				 String[] isCharge1 = request.getParameterValues("isCharge1");//�Ƿ��շ�
				 String[] isLiquidation1 = request.getParameterValues("isLiquidation1");//�Ƿ�����
				 String[] rem1 = request.getParameterValues("rem1");// ��ע
				 
				 if(toolId1 != null){
					 fileCount=toolId1.length;
				 }
				 
				 Iterator it = hash.values().iterator();
				 HandleFile hf = new HandleFileImpA();
				 while (it.hasNext()) {
					 Map map = new HashMap();
					 fileCount--;
					 formFile = (FormFile) it.next();
					 if (formFile != null) {
						 try {
							 map.put("numno", numno[fileCount]);
							 map.put("toolId1", toolId1[fileCount]);
							 map.put("toolName1", toolName1[fileCount]);
							 map.put("toolParam1", toolParam1[fileCount]);
							 map.put("toolnum1", toolnum1[fileCount]);
							 map.put("operName1", operName1[fileCount]);
							 map.put("operDate1", operDate1[fileCount]);
							 map.put("isCharge1", isCharge1[fileCount]);
							 map.put("isLiquidation1", isLiquidation1[fileCount]);
							 map.put("rem1", rem1[fileCount]);
							 
							 if(!formFile.getFileName().equals("")){
								 String newfilename=DateUtil.getNowTime("yyyyMMddHHmmss")+"_"+formFile.getFileName();
								 
								 map.put("oldfilename", formFile.getFileName());
								 map.put("newfilename", newfilename);
	
								 hf.createFile(formFile.getInputStream(),folder, newfilename);
							 }else{
								 map.put("oldfilename", "");
								 map.put("newfilename", "");
							 }
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
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public ActionForward toPrepareImportRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String flag = request.getParameter("flag");
		String str = "";
		if ("U".equals(flag)) {
			str="Ա����������";
		}else if ("P".equals(flag)) {
			str="��ѵ��������";
		}else if ("Z".equals(flag)) {
			str="֤�鵵������";
		}else if ("T".equals(flag)) {
			str="������ȡ��������";
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("navigator.location","���¹��� >> "+str);
		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}
		return mapping.findForward("importImport");
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
	
	public ActionForward toImportRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		FormFile file = (FormFile) dform.get("file"); //��ȡ�ϴ��ļ�
		String fileName = file.getFileName();
		String fileFromt=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()); //��ȡ�ϴ��ļ��ĺ�׺��
		String flag = request.getParameter("flag");
		if ("".equals(flag)) {
			flag = request.getParameter("pflag");
		}

		request.setAttribute("pflag", flag);
		
		InputStream in = null;
		Session hs = null;
		Transaction tx = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmtdel=null;
		
		StringBuffer reStr = new StringBuffer(); //���󷵻���Ϣ
	
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if (fileFromt!=null && fileFromt.equals("xlsx")) {//excel 2007
				
				in = file.getInputStream();
				XSSFWorkbook wb = new XSSFWorkbook(in);
				XSSFSheet sheet = wb.getSheetAt(0);
				XSSFRow row = null;			
				
				int rowSum = sheet.getLastRowNum()+1; //�������
				
				String userid=userInfo.getUserID();
				String today=CommonUtil.getNowTime();
				String todayDate = CommonUtil.getToday();
				String year = todayDate.substring(2,4);
				
				List implist=new ArrayList();
				List implist1=new ArrayList();
				HashMap hmap=null;
				if ("U".equals(flag)) {//Ա����������
					for(int rowNum = 1; rowNum < rowSum; rowNum++){					
						row = sheet.getRow(rowNum);	
						
					    String ygid=cellValueToString(row, 0, reStr);//Ա������
					    String name=cellValueToStringNull(row, 1, reStr);//Ա������
					    String sex=cellValueToString(row, 2, reStr);//�Ա�
					    String idCardNo=cellValueToStringNull(row, 3, reStr);//���֤����
					    String education=cellValueToStringNull(row, 4, reStr);//ѧ��
					    String phone=cellValueToStringNull(row, 5, reStr);//��ϵ�绰
					    String familyAddress=cellValueToStringNull(row, 6, reStr);//��ͥ���ڵ�
					    String birthDate=cellValueToStringNull(row, 7, reStr);//��������
					    String phone2=cellValueToStringNull(row, 8, reStr);//���õ绰
					    String contractNo=cellValueToString(row, 9, reStr);//��ͬ��
					    String startDate=cellValueToString(row, 10, reStr);//�볧����
					    String years=cellValueToString(row, 11, reStr);//�볧����
					    String endDate=cellValueToString(row, 12, reStr);//��ְ����
					    String workAddress=cellValueToString(row, 13, reStr);//�����ص�
					    String sector=cellValueToString(row, 14, reStr);//�����ֲ�
					    String maintStation=cellValueToString(row, 15, reStr);//��������
					    String hubie=cellValueToStringNull(row, 16, reStr);//����
					    String iszhuwai=cellValueToStringNull(row, 17, reStr);//�Ƿ�פ��
					    String jobLeval=cellValueToStringNull(row, 18, reStr);//��λ����
					    String level=cellValueToString(row, 19, reStr);//��λ����
					    String probation=cellValueToStringNull(row, 20, reStr);//������
					    Double probationgz=cellValueToDouble(row, 21, reStr);//�����ڹ���
					    String probationEdate=cellValueToString(row, 22, reStr);//���ý�������
					    String r3=cellValueToString(row, 23, reStr);//��ȡ��ȫñ����
					    String r1=cellValueToString(row, 24, reStr);//�ͱ�Ь
					    String r2=cellValueToString(row, 25, reStr);//��װ��
					    String rem=cellValueToString(row, 26, reStr);//��ע
					    
//					    String sqlup="from PersonnelManageMaster where ygid='"+ygid+"'";
//						List sqlist=hs.createQuery(sqlup).list();
//						if(ygid!=null && !ygid.trim().equals("") && sqlist!=null && sqlist.size()>0){
//							reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(0) + "��)Ա�������Ѿ�����;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
//						}

					    if(reStr != null && reStr.length() > 0){
							break;
						}
					    
					    hmap=new HashMap();
					    hmap.put("ygid", ygid);
					    hmap.put("name", name);
					    hmap.put("sex", sex);
					    hmap.put("idCardNo", idCardNo);
					    hmap.put("education", education);
					    hmap.put("phone", phone);
					    hmap.put("familyAddress", familyAddress);
					    hmap.put("birthDate", birthDate);
					    hmap.put("phone2", phone2);
					    hmap.put("contractNo", contractNo);
					    hmap.put("startDate", startDate);
					    hmap.put("years", years);
					    hmap.put("endDate", endDate);
					    hmap.put("workAddress", workAddress);
					    hmap.put("sector", sector);
					    hmap.put("maintStation", maintStation);
					    hmap.put("hubie", hubie);
					    hmap.put("iszhuwai", iszhuwai);
					    hmap.put("jobLeval", jobLeval);
					    hmap.put("level", level);
					    hmap.put("probation", probation);
					    hmap.put("probationgz", probationgz);
					    hmap.put("probationEdate", probationEdate);
					    hmap.put("r3", r3);
					    hmap.put("r1", r1);
					    hmap.put("r2", r2);
					    hmap.put("rem", rem);
					    implist1.add(hmap);
					}
					
					if(reStr == null || reStr.length() == 0){
						PersonnelManageMaster master =null;
						for(int i=0;i<implist1.size();i++){
							hmap=(HashMap)implist1.get(i);
							String enabledFlag="Y";//���ñ�־
							
							String sqlup="from PersonnelManageMaster where idCardNo='"+(String)hmap.get("idCardNo")+"'";
							List sqlist=hs.createQuery(sqlup).list();
							if(sqlist!=null && sqlist.size()>0){
								master = (PersonnelManageMaster) sqlist.get(0);
								
							}else{
								String billno = CommonUtil.getBillno(year,"PersonnelManageMaster", 1)[0];// ������ˮ��
								master = new PersonnelManageMaster();
								master.setBillno(billno); 
							}

							master.setYgid((String)hmap.get("ygid"));
							master.setName((String)hmap.get("name"));
							master.setSex((String)hmap.get("sex"));
							master.setIdCardNo((String)hmap.get("idCardNo"));
							master.setEducation((String)hmap.get("education"));
							master.setPhone((String)hmap.get("phone"));
							master.setFamilyAddress((String)hmap.get("familyAddress"));
							master.setBirthDate((String)hmap.get("birthDate"));
							master.setPhone2((String)hmap.get("phone2"));
							master.setContractNo((String)hmap.get("contractNo"));
							master.setStartDate((String)hmap.get("startDate"));
							master.setYears((String)hmap.get("years"));
							master.setEndDate((String)hmap.get("endDate"));
							master.setWorkAddress((String)hmap.get("workAddress"));
							master.setSector((String)hmap.get("sector"));
							master.setMaintStation((String)hmap.get("maintStation"));
							master.setHubie((String)hmap.get("hubie"));
							master.setIszhuwai((String)hmap.get("iszhuwai"));
							master.setJobLeval((String)hmap.get("jobLeval"));
							master.setLevel((String)hmap.get("level"));
							master.setProbation((String)hmap.get("probation"));
							master.setProbationgz((Double)hmap.get("probationgz"));
							master.setProbationEdate((String)hmap.get("probationEdate"));
							master.setR3((String)hmap.get("r3"));
							master.setR1((String)hmap.get("r1"));
							master.setR2((String)hmap.get("r2"));
							master.setRem((String)hmap.get("rem"));
							master.setEnabledFlag(enabledFlag);
							master.setOperId(userid);
							master.setOperDate(today);
							
							implist.add(master);
						}

					}
					
				}else if ("P".equals(flag)) {//��ѵ��������
					for(int rowNum = 1; rowNum < rowSum; rowNum++){					
						row = sheet.getRow(rowNum);
						
						String ygid=cellValueToStringNull(row, 0, reStr);//Ա������
						String straDate = cellValueToStringNull(row, 1, reStr);// ��ѵ����
						String etraDate = cellValueToStringNull(row, 2, reStr);
						String traCourse = cellValueToStringNull(row, 3, reStr);// ��ѵ�γ�
						Double lesson = cellValueToDouble(row, 4, reStr);// ��ʱ
						Double theoreticalResults = cellValueToDouble(row, 5, reStr);// ���۳ɼ�
						Double practicalResults = cellValueToDouble(row, 6, reStr);// ʵ�ٳɼ�
						Double perforResults = cellValueToDouble(row, 7, reStr,"");// ���ֳɼ�
						String traAssess = cellValueToString(row, 8, reStr);// ��ѵ����
						Double totalScore = cellValueToDouble(row, 9, reStr,"");// �ܷ�
						String rating = cellValueToString(row, 10, reStr);// �ȼ�
						
						if(reStr != null && reStr.length() > 0){
							break;
						}
						
						String billno = bd.getName_Sql("PersonnelManageMaster", "Billno", "ygid", ygid);
						if(!"".equals(billno)){
							TrainingHistory history = new TrainingHistory();
							history.setBillno(billno);
							history.setStraDate(straDate);
							history.setEtraDate(etraDate);
							history.setTraCourse(traCourse);
							history.setLesson(lesson);
							history.setTheoreticalResults(theoreticalResults);
							history.setPracticalResults(practicalResults);
							history.setPerforResults(perforResults);
							history.setTraAssess(traAssess);
							history.setTotalScore(totalScore);
							history.setRating(rating);
							
							implist.add(history);
						}else{
							reStr.append(ygid+" Ա�����벻���ڣ�����ʧ�ܡ�");
							break;
						}
					}
				}else if ("Z".equals(flag)) {//֤�鵵������
					for(int rowNum = 1; rowNum < rowSum; rowNum++){					
						row = sheet.getRow(rowNum);
						
						String ygid=cellValueToStringNull(row, 0, reStr);//Ա������
						String certificateNo = cellValueToStringNull(row, 1, reStr);// ֤���
						String certificateName = cellValueToStringNull(row, 2, reStr);// ֤������
						String certificateProperty = cellValueToStringNull(row, 3, reStr);// ֤������
						String startDate = cellValueToStringNull(row, 4, reStr);// ��ʼ����
						String endDate = cellValueToStringNull(row, 5, reStr);// ʧЧ����
						String issuingAuthority1 = cellValueToStringNull(row, 6, reStr);// ��֤����
						String isExpense1 = cellValueToStringNull(row, 7, reStr);//�Ƿ���
						String isCharging1 = cellValueToStringNull(row, 8, reStr);//�Ƿ�۷�
						String certificateExt1 = cellValueToString(row, 9, reStr);//֤��ȥ��
						String rem0 = cellValueToString(row, 10, reStr);// ��ע
						
						
						if(reStr != null && reStr.length() > 0){
							break;
						}
						//System.out.println(ygid);
						String billno = bd.getName_Sql("PersonnelManageMaster", "Billno", "ygid", ygid);
						if(!"".equals(billno)){
							CertificateExam exam = new CertificateExam();
							exam.setBillno(billno);
							exam.setCertificateNo(certificateNo);
							exam.setCertificateName(certificateName);
							exam.setCertificateProperty(certificateProperty);
							exam.setStartDate(startDate);
							exam.setEndDate(endDate);
							exam.setIssuingAuthority(issuingAuthority1);
							exam.setIsExpense(isExpense1);
							exam.setIsCharging(isCharging1);
							exam.setCertificateExt(certificateExt1);
							exam.setRem(rem0);
							
							implist.add(exam);
						}else{
							reStr.append(ygid+" Ա�����벻���ڣ�����ʧ�ܡ�");
							break;
						}
					}
				}else if ("T".equals(flag)) {//������ȡ��������
					for(int rowNum = 1; rowNum < rowSum; rowNum++){					
						row = sheet.getRow(rowNum);
						
						String ygid=cellValueToStringNull(row, 0, reStr);//Ա������
						String toolId = cellValueToString(row, 1, reStr);// ���߱��
						String toolName = cellValueToStringNull(row, 2, reStr);// ��������
						String toolParam = cellValueToString(row, 3, reStr);// �����ͺ�
						Double toolnum = cellValueToDouble(row, 4, reStr,"");// ����
						String operName = cellValueToStringNull(row, 5, reStr);// ������
						String operDate = cellValueToStringNull(row, 6, reStr);//��ȡ����
						String isCharge = cellValueToStringNull(row, 7, reStr);//�Ƿ��շ�
						String isLiquidation = cellValueToStringNull(row, 8, reStr,"");//�Ƿ�����
						String rem1 = cellValueToString(row, 9, reStr);// ��ע
						
						if ("��˾����".equals(isCharge)) {
							isCharge="M";
						}else if ("�ֳ�����".equals(isCharge)) {
							isCharge="X";
						}else if ("���շ�".equals(isCharge)) {
							isCharge="Y";
						}else if ("δ�շ�".equals(isCharge)) {
							isCharge="N";
						}else {
							reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+getCellChar(7)+"��)��д�ĸ�ʽ����;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						
						if(reStr != null && reStr.length() > 0){
							break;
						}
						
						String billno = bd.getName_Sql("PersonnelManageMaster", "Billno", "ygid", ygid);
						if(!"".equals(billno)){
							ToolReceive receive = new ToolReceive();
							receive.setBillno(billno);
							receive.setToolId(toolId);
							receive.setToolName(toolName);
							receive.setToolParam(toolParam);
							receive.setToolnum(toolnum);
							receive.setOperName(operName);
							receive.setOperDate(operDate);
							receive.setIsCharge(isCharge);
							receive.setIsLiquidation(isLiquidation);
							receive.setRem(rem1);
							
							implist.add(receive);
						}else{
							reStr.append(ygid+" Ա�����벻���ڣ�����ʧ�ܡ�");
							break;
						}
					}
				}
				
				if(reStr == null || reStr.length() == 0){
					for(int i=0;i<implist.size();i++){
						if ("P".equals(flag)) {
							TrainingHistory h = (TrainingHistory) implist.get(i);
							hs.save(h);
						}else if ("Z".equals(flag)) {
							CertificateExam e = (CertificateExam) implist.get(i);
							hs.save(e);
						}else if ("T".equals(flag)) {
							ToolReceive t = (ToolReceive) implist.get(i);
							hs.save(t);
						}else if ("U".equals(flag)) {
							PersonnelManageMaster m = (PersonnelManageMaster) implist.get(i);
							hs.save(m);
						}
						hs.flush();
					}
					
					tx.commit();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","�ϴ��ɹ���"));
				}else {
					request.setAttribute("reStr", reStr);//���󷵻���Ϣ
				}
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","�ϴ�ʧ�ܣ�"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = mapping.findForward("returnImport");
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return forward;
	}
	
	/**
	 * ���ص�Ԫ���ַ���ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellValueToStringNull(XSSFRow row, int cellNum, StringBuffer reStr){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) { 
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)����Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = String.valueOf((int) cell.getNumericCellValue());
		}else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ�ַ���;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();
	}
	
	public String cellValueToStringNull(XSSFRow row, int cellNum, StringBuffer reStr,String check){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) { 
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)����Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = isYN(cell.getStringCellValue());
			if ("".equals(value)) {
				reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)������д�ǻ��߷�;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
			}
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = String.valueOf((int) cell.getNumericCellValue());
		}else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ�ַ���;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();
	}
	
	/**
	 * ���ص�Ԫ���ַ���ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellValueToString(XSSFRow row, int cellNum, StringBuffer reStr){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) { 
			value = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = String.valueOf((int) cell.getNumericCellValue());
		}else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ�ַ���;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();
	}
	
	/**
	 * ���ص�Ԫ�񸡵���
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public Double cellValueToDouble(XSSFRow row, int cellNum, StringBuffer reStr){
		Double value = 0d;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = (Double) cell.getNumericCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ��ֵ;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value;
	}
	
	public Double cellValueToDouble(XSSFRow row, int cellNum, StringBuffer reStr,String flag){
		Double value = 0d;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = (Double) cell.getNumericCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ��ֵ;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value;
	}
	
	/**
	 * ���ص�Ԫ������ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public int cellValueToInt(XSSFRow row, int cellNum, StringBuffer reStr){
		int value = 0;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = (int) cell.getNumericCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ��ֵ;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value;
	}
	
	/**
	 * ����Ԫ������ת��Ϊ��д��ĸ
	 * @param cellNum ���� 
	 * @return char
	 */
	public char getCellChar(int cellNum){
		return (char) (cellNum + 65);
	}
	
	public String isYN(String value) {
		if ("��".equals(value)) {
			value="Y";
		}else if ("��".equals(value)) {
			value="N";
		}else {
			value="";
		}
		return value;
	}
}
